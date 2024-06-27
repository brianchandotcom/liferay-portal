/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.model.impl;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.exception.NoSuchFolderException;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBFolderModel;
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
import java.util.function.Function;

/**
 * @author Brian Wing Shun Chan
 */
public class KBFolderImpl extends KBFolderBaseImpl {

	@Override
	public List<Long> getAncestorKBFolderIds() throws PortalException {
		return _getAncestors(KBFolderModel::getKbFolderId);
	}

	@Override
	public List<KBFolder> getAncestorKBFolders() throws PortalException {
		return _getAncestors(Function.identity());
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(KBFolderConstants.getClassName());
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

	private <T> List<T> _getAncestors(Function<KBFolder, T> function)
		throws PortalException {

		List<T> ancestors = new ArrayList<>();

		KBFolder kbFolder = this;

		while (!kbFolder.isRoot()) {
			try {
				kbFolder = kbFolder.getParentKBFolder();

				ancestors.add(function.apply(kbFolder));
			}
			catch (NoSuchFolderException noSuchFolderException) {
				if (kbFolder.isInTrash()) {
					break;
				}

				throw noSuchFolderException;
			}
		}

		return ancestors;
	}

}