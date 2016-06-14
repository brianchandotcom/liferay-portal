/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.knowledge.base.web.upload;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.permission.KBArticlePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.InputStream;

/**
 * @author Roberto Díaz
 */
public class KBUploadHandler extends BaseUploadHandler {

	public KBUploadHandler(long resourcePrimKey) {
		_resourcePrimKey = resourcePrimKey;
	}

	protected FileEntry addFileEntry(
			long userId, long groupId, long folderId, String fileName,
			String contentType, InputStream inputStream, long size,
			ServiceContext serviceContext)
		throws PortalException {

		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			_resourcePrimKey, WorkflowConstants.STATUS_APPROVED);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			kbArticle.getGroupId(), userId, KBArticle.class.getName(),
			kbArticle.getClassPK(), KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
			kbArticle.getAttachmentsFolderId(), inputStream, fileName,
			contentType, false);
	}

	protected void checkPermission(
			long groupId, long folderId, PermissionChecker permissionChecker)
		throws PortalException {

		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			_resourcePrimKey, WorkflowConstants.STATUS_APPROVED);

		KBArticlePermission.check(
			permissionChecker, kbArticle, KBActionKeys.UPDATE);
	}

	protected FileEntry fetchFileEntry(
			long userId, long groupId, long folderId, String fileName)
		throws PortalException {

		try {
			KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
				_resourcePrimKey, WorkflowConstants.STATUS_APPROVED);

			return PortletFileRepositoryUtil.getPortletFileEntry(
				groupId, kbArticle.getAttachmentsFolderId(), fileName);
		}
		catch (PortalException pe) {
			return null;
		}
	}

	protected String getParameterName() {
		return "imageSelectorFileName";
	}

	protected void validateFile(String fileName, String contentType, long size)
		throws PortalException {
	}

	private final long _resourcePrimKey;

}