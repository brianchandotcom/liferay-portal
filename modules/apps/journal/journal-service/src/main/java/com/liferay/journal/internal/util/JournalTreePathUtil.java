/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.util;

import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.persistence.JournalFolderPersistence;
import com.liferay.journal.util.comparator.FolderIdComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.tree.TreeModelTasksAdapter;
import com.liferay.portal.kernel.tree.TreePathUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Lance Ji
 */
public class JournalTreePathUtil {

	public static void rebuildTree(
			long companyId, long parentFolderId, String parentTreePath,
			JournalFolderPersistence journalFolderPersistence,
			JournalArticleLocalService journalArticleLocalService)
		throws PortalException {

		TreePathUtil.rebuildTree(
			companyId, parentFolderId, parentTreePath,
			new TreeModelTasksAdapter<JournalFolder>() {

				@Override
				public List<JournalFolder> findTreeModels(
					long previousId, long companyId, long parentPrimaryKey,
					int size) {

					return journalFolderPersistence.findByGtF_C_P_NotS(
						previousId, companyId, parentPrimaryKey,
						WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
						size, new FolderIdComparator(true));
				}

				@Override
				public void rebuildDependentModelsTreePaths(
						long parentPrimaryKey, String treePath)
					throws PortalException {

					journalArticleLocalService.setTreePaths(
						parentPrimaryKey, treePath, false);
				}

			});
	}

}