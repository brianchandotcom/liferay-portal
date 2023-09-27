/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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