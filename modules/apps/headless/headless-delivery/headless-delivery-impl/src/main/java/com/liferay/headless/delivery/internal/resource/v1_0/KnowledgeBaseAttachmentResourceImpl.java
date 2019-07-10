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
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/knowledge-base-attachment.properties",
	scope = ServiceScope.PROTOTYPE,
	service = KnowledgeBaseAttachmentResource.class
)
public class KnowledgeBaseAttachmentResourceImpl
	extends BaseKnowledgeBaseAttachmentResourceImpl {

	@Override
	public void deleteKnowledgeBaseAttachment(Long knowledgeBaseAttachmentId)
		throws Exception {

		_portletFileRepository.deletePortletFileEntry(
			knowledgeBaseAttachmentId);
	}

	@Override
	public Page<KnowledgeBaseAttachment>
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
				Long knowledgeBaseArticleId)
		throws Exception {

		KBArticle kbArticle = _kbArticleService.getLatestKBArticle(
			knowledgeBaseArticleId, WorkflowConstants.STATUS_APPROVED);

		return Page.of(
			transform(
				kbArticle.getAttachmentsFileEntries(),
				this::_toKnowledgeBaseAttachment));
	}

	@Override
	public KnowledgeBaseAttachment getKnowledgeBaseAttachment(
			Long knowledgeBaseAttachmentId)
		throws Exception {

		return _toKnowledgeBaseAttachment(
			_portletFileRepository.getPortletFileEntry(
				knowledgeBaseAttachmentId));
	}

	@Override
	public KnowledgeBaseAttachment
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				Long knowledgeBaseArticleId, MultipartBody multipartBody)
		throws Exception {

		KBArticle kbArticle = _kbArticleService.getLatestKBArticle(
			knowledgeBaseArticleId, WorkflowConstants.STATUS_APPROVED);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		if (binaryFile == null) {
			throw new BadRequestException("No file found in body");
		}

		return _toKnowledgeBaseAttachment(
			_portletFileRepository.addPortletFileEntry(
				kbArticle.getGroupId(), contextUser.getUserId(),
				KBArticle.class.getName(), kbArticle.getClassPK(),
				KBConstants.SERVICE_NAME, kbArticle.getAttachmentsFolderId(),
				binaryFile.getInputStream(), binaryFile.getFileName(),
				binaryFile.getFileName(), false));
	}

	private KnowledgeBaseAttachment _toKnowledgeBaseAttachment(
			FileEntry fileEntry)
		throws Exception {

		return new KnowledgeBaseAttachment() {
			{
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
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
	private KBArticleService _kbArticleService;

	@Reference
	private PortletFileRepository _portletFileRepository;

}