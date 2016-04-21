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

package com.liferay.knowledge.base.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.knowledge.base.service.util.KBArticleAttachmentsUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class KBArticleImpl extends KBArticleBaseImpl {

	public KBArticleImpl() {
	}

	@Override
	public List<Long> getAncestorResourcePrimaryKeys() throws PortalException {
		List<Long> ancestorResourcePrimaryKeys = new ArrayList<>();

		ancestorResourcePrimaryKeys.add(getResourcePrimKey());

		KBArticle kbArticle = this;

		while (!kbArticle.isRoot()) {
			kbArticle = kbArticle.getParentKBArticle();

			if (kbArticle == null) {
				break;
			}

			ancestorResourcePrimaryKeys.add(kbArticle.getResourcePrimKey());
		}

		return ancestorResourcePrimaryKeys;
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries() throws PortalException {
		return PortletFileRepositoryUtil.getPortletFileEntries(
			getGroupId(), getAttachmentsFolderId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public long getAttachmentsFolderId() throws PortalException {
		if (_attachmentsFolderId > 0) {
			return _attachmentsFolderId;
		}

		_attachmentsFolderId = KBArticleAttachmentsUtil.getFolderId(
			getGroupId(), getUserId(), getResourcePrimKey());

		return _attachmentsFolderId;
	}

	@Override
	public long getClassNameId() {
		if (_classNameId == 0) {
			_classNameId = PortalUtil.getClassNameId(
				KBArticleConstants.getClassName());
		}

		return _classNameId;
	}

	@Override
	public long getClassPK() {
		if (isApproved()) {
			return getResourcePrimKey();
		}

		return getKbArticleId();
	}

	@Override
	public KBArticle getParentKBArticle() throws PortalException {
		long parentResourcePrimKey = getParentResourcePrimKey();

		if ((parentResourcePrimKey <= 0) ||
			(getParentResourceClassNameId() != getClassNameId())) {

			return null;
		}

		return KBArticleLocalServiceUtil.getLatestKBArticle(
			parentResourcePrimKey, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getParentTitle(Locale locale, int status)
		throws PortalException {

		if (isRoot()) {
			return "(" + LanguageUtil.get(locale, "none") + ")";
		}

		if (getParentResourceClassNameId() == getClassNameId()) {
			KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(
				getParentResourcePrimKey(), status);

			return kbArticle.getTitle();
		}
		else {
			KBFolder kbFolder = KBFolderServiceUtil.getKBFolder(
				getParentResourcePrimKey());

			return kbFolder.getName();
		}
	}

	@Override
	public boolean isFirstVersion() {
		if (getVersion() == KBArticleConstants.DEFAULT_VERSION) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isResourceMain() {
		return isMain();
	}

	@Override
	public boolean isRoot() {
		if (getParentResourcePrimKey() ==
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(KBArticleImpl.class);

	private long _attachmentsFolderId;
	private long _classNameId;

}