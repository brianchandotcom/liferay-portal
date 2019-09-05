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

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class KBFolderImpl extends KBFolderBaseImpl {

	public KBFolderImpl() {
	}

	@Override
	public List<Long> getAncestorKBFolderIds() throws PortalException {
		List<Long> ancestorFolderIds = new ArrayList<>();

		ancestorFolderIds.add(getKbFolderId());

		KBFolder kbFolder = this;

		while (!kbFolder.isRoot()) {
			kbFolder = kbFolder.getParentKBFolder();

			if (kbFolder == null) {
				break;
			}

			ancestorFolderIds.add(kbFolder.getKbFolderId());
		}

		return ancestorFolderIds;
	}

	@Override
	public long getClassNameId() {
		if (_classNameId == 0) {
			_classNameId = PortalUtil.getClassNameId(
				KBFolderConstants.getClassName());
		}

		return _classNameId;
	}

	@Override
	public KBFolder getParentKBFolder() throws PortalException {
		long parentKBFolderId = getParentKBFolderId();

		if (parentKBFolderId <= KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return null;
		}

		return KBFolderLocalServiceUtil.getKBFolder(parentKBFolderId);
	}

	@Override
	public String getParentTitle(Locale locale) throws PortalException {
		KBFolder parentKBFolder = getParentKBFolder();

		if (parentKBFolder == null) {
			return LanguageUtil.get(locale, "home");
		}

		return parentKBFolder.getName();
	}

	@Override
	public boolean isEmpty() throws PortalException {
		int kbArticlesCount = KBArticleServiceUtil.getKBArticlesCount(
			getGroupId(), getKbFolderId(), WorkflowConstants.STATUS_APPROVED);

		if (kbArticlesCount > 0) {
			return false;
		}

		int kbFoldersCount = KBFolderServiceUtil.getKBFoldersCount(
			getGroupId(), getKbFolderId());

		if (kbFoldersCount > 0) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isRoot() {
		if (getParentKBFolderId() ==
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return true;
		}

		return false;
	}

	private long _classNameId;

}