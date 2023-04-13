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
import com.liferay.headless.delivery.dto.v1_0.util.ContentValueUtil;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Map;

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
			HashMapBuilder.<String, Map<String, String>>put(
				"createBatch",
				addAction(
					KBActionKeys.ADD_KB_ARTICLE, kbArticle.getResourcePrimKey(),
					"postKnowledgeBaseArticleKnowledgeBaseAttachmentBatch",
					kbArticle.getUserId(), KBConstants.RESOURCE_NAME_ADMIN,
					kbArticle.getGroupId())
			).build(),
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
			getSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode(
				Long siteId, String knowledgeBaseArticleExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		KBArticle kbArticle =
			_kbArticleService.getLatestKBArticleByExternalReferenceCode(
				siteId, knowledgeBaseArticleExternalReferenceCode);

		return _toKnowledgeBaseAttachment(
			_portletFileRepository.getPortletFileEntryByExternalReferenceCode(
				externalReferenceCode, kbArticle.getGroupId()));
	}

	@Override
	public KnowledgeBaseAttachment
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				Long knowledgeBaseArticleId, MultipartBody multipartBody)
		throws Exception {

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		if (binaryFile == null) {
			throw new BadRequestException("No file found in body");
		}

		KBArticle kbArticle = _kbArticleService.getLatestKBArticle(
			knowledgeBaseArticleId, WorkflowConstants.STATUS_APPROVED);

		return _toKnowledgeBaseAttachment(
			_portletFileRepository.addPortletFileEntry(
				_getKnowledgeBaseAttachmentExternalReferenceCode(multipartBody),
				kbArticle.getGroupId(), contextUser.getUserId(),
				KBArticle.class.getName(), kbArticle.getClassPK(),
				KBConstants.SERVICE_NAME, kbArticle.getAttachmentsFolderId(),
				binaryFile.getInputStream(), binaryFile.getFileName(),
				binaryFile.getContentType(), false));
	}

	@Override
	public KnowledgeBaseAttachment
			postSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode(
				Long siteId, String knowledgeBaseArticleExternalReferenceCode,
				String externalReferenceCode, MultipartBody multipartBody)
		throws Exception {

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		if (binaryFile == null) {
			throw new BadRequestException("No file found in body");
		}

		KBArticle kbArticle =
			_kbArticleService.getLatestKBArticleByExternalReferenceCode(
				siteId, knowledgeBaseArticleExternalReferenceCode);

		return _toKnowledgeBaseAttachment(
			_portletFileRepository.addPortletFileEntry(
				externalReferenceCode, kbArticle.getGroupId(),
				contextUser.getUserId(), KBArticle.class.getName(),
				kbArticle.getClassPK(), KBConstants.SERVICE_NAME,
				kbArticle.getAttachmentsFolderId(), binaryFile.getInputStream(),
				binaryFile.getFileName(), binaryFile.getFileName(), false));
	}

	@Override
	public KnowledgeBaseAttachment
			putSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode(
				Long siteId, String knowledgeBaseArticleExternalReferenceCode,
				String externalReferenceCode, MultipartBody multipartBody)
		throws Exception {

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		if (binaryFile == null) {
			throw new BadRequestException("No file found in body");
		}

		KBArticle kbArticle =
			_kbArticleService.getLatestKBArticleByExternalReferenceCode(
				siteId, knowledgeBaseArticleExternalReferenceCode);

		try {
			FileEntry portletFileEntry =
				_portletFileRepository.
					getPortletFileEntryByExternalReferenceCode(
						externalReferenceCode, kbArticle.getGroupId());

			_portletFileRepository.deletePortletFileEntry(
				portletFileEntry.getFileEntryId());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return _toKnowledgeBaseAttachment(
			_portletFileRepository.addPortletFileEntry(
				externalReferenceCode, kbArticle.getGroupId(),
				contextUser.getUserId(), KBArticle.class.getName(),
				kbArticle.getClassPK(), KBConstants.SERVICE_NAME,
				kbArticle.getAttachmentsFolderId(), binaryFile.getInputStream(),
				binaryFile.getFileName(), binaryFile.getFileName(), false));
	}

	private String _getKnowledgeBaseAttachmentExternalReferenceCode(
			MultipartBody multipartBody)
		throws Exception {

		KnowledgeBaseAttachment knowledgeBaseAttachment =
			multipartBody.getValueAsInstance(
				"knowledgeBaseAttachment", KnowledgeBaseAttachment.class);

		if (knowledgeBaseAttachment == null) {
			return null;
		}

		return knowledgeBaseAttachment.getExternalReferenceCode();
	}

	private KnowledgeBaseAttachment _toKnowledgeBaseAttachment(
			FileEntry fileEntry)
		throws Exception {

		return new KnowledgeBaseAttachment() {
			{
				contentUrl = _portletFileRepository.getPortletFileEntryURL(
					null, fileEntry, null);
				contentValue = ContentValueUtil.toContentValue(
					"contentValue", fileEntry::getContentStream,
					contextUriInfo);
				encodingFormat = fileEntry.getMimeType();
				externalReferenceCode = fileEntry.getExternalReferenceCode();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KnowledgeBaseAttachmentResourceImpl.class);

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private PortletFileRepository _portletFileRepository;

}