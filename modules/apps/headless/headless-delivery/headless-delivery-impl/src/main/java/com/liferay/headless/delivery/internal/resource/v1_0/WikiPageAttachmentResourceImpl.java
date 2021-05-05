/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.delivery.dto.v1_0.WikiPageAttachment;
import com.liferay.headless.delivery.dto.v1_0.util.ContentValueUtil;
import com.liferay.headless.delivery.resource.v1_0.WikiPageAttachmentResource;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/wiki-page-attachment.properties",
	scope = ServiceScope.PROTOTYPE, service = WikiPageAttachmentResource.class
)
public class WikiPageAttachmentResourceImpl
	extends BaseWikiPageAttachmentResourceImpl {

	@Override
	public void deleteWikiPageAttachment(Long wikiPageAttachmentId)
		throws Exception {

		_portletFileRepository.deletePortletFileEntry(wikiPageAttachmentId);
	}

	@Override
	public WikiPageAttachment getWikiPageAttachment(Long wikiPageAttachmentId)
		throws Exception {

		return _toWikiPageAttachment(
			_portletFileRepository.getPortletFileEntry(wikiPageAttachmentId));
	}

	@Override
	public Page<WikiPageAttachment> getWikiPageWikiPageAttachmentsPage(
			Long wikiPageId)
		throws Exception {

		WikiPage wikiPage = _wikiPageLocalService.getPageByPageId(wikiPageId);

		_wikiPageModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), wikiPage,
			ActionKeys.VIEW);

		return Page.of(
			transform(
				wikiPage.getAttachmentsFileEntries(),
				this::_toWikiPageAttachment));
	}

	@Override
	public WikiPageAttachment postWikiPageWikiPageAttachment(
			Long wikiPageId, MultipartBody multipartBody)
		throws Exception {

		WikiPage wikiPage = _wikiPageLocalService.getPageByPageId(wikiPageId);

		_wikiPageModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), wikiPage,
			ActionKeys.UPDATE);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		if (binaryFile == null) {
			throw new BadRequestException("No file found in body");
		}

		Folder folder = wikiPage.addAttachmentsFolder();

		return _toWikiPageAttachment(
			_portletFileRepository.addPortletFileEntry(
				wikiPage.getGroupId(), contextUser.getUserId(),
				WikiPage.class.getName(), wikiPage.getResourcePrimKey(),
				WikiConstants.SERVICE_NAME, folder.getFolderId(),
				binaryFile.getInputStream(), binaryFile.getFileName(),
				binaryFile.getFileName(), false));
	}

	private WikiPageAttachment _toWikiPageAttachment(FileEntry fileEntry)
		throws Exception {

		return new WikiPageAttachment() {
			{
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
				contentValue = ContentValueUtil.toContentValue(
					"contentValue", fileEntry::getContentStream,
					Optional.of(contextUriInfo));
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiPage)")
	private ModelResourcePermission<WikiPage> _wikiPageModelResourcePermission;

}