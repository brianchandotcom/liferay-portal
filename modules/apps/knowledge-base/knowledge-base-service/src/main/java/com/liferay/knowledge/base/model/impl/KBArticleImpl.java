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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
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
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new RepositoryModelTitleComparator<>(true));
	}

	@Override
	public long getAttachmentsFolderId() throws PortalException {
		if (_attachmentsFolderId > 0) {
			return _attachmentsFolderId;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			getGroupId(), KBConstants.SERVICE_NAME, serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			PortalUtil.getValidUserId(getCompanyId(), getUserId()),
			repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(getResourcePrimKey()), serviceContext);

		_attachmentsFolderId = folder.getFolderId();

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
			return LanguageUtil.get(locale, "home");
		}

		if (getParentResourceClassNameId() == getClassNameId()) {
			KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(
				getParentResourcePrimKey(), status);

			return kbArticle.getTitle();
		}

		KBFolder kbFolder = KBFolderServiceUtil.getKBFolder(
			getParentResourcePrimKey());

		return kbFolder.getName();
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

	private long _attachmentsFolderId;
	private long _classNameId;

}