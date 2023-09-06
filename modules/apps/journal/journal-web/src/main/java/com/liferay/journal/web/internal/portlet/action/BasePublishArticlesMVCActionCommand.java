/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.exportimport.changeset.portlet.action.ExportImportChangesetMVCActionCommandHelper;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.web.internal.util.JournalUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Szimko
 */
public abstract class BasePublishArticlesMVCActionCommand
	extends BaseMVCActionCommand {

	protected JournalArticle fetchArticle(long groupId, String articleId) {
		JournalArticle journalArticle = journalArticleLocalService.fetchArticle(
			groupId, articleId);

		StagedModelDataHandler<JournalArticle> stagedModelDataHandler =
			_getStagedModelDataHandler();

		try {
			JournalArticle latestApprovedJournalArticle =
				journalArticleLocalService.getArticle(
					journalArticle.getGroupId(), journalArticle.getArticleId());

			if (ArrayUtil.contains(
					stagedModelDataHandler.getExportableStatuses(),
					latestApprovedJournalArticle.getStatus())) {

				return latestApprovedJournalArticle;
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to get journal article by group ", groupId,
						" and article ID ", articleId),
					portalException);
			}
		}

		return null;
	}

	protected List<StagedModel> getJournalArticleVersions(
		JournalArticle journalArticle) {

		boolean includeVersionHistory = JournalUtil.isIncludeVersionHistory();

		if (!includeVersionHistory) {
			return Collections.emptyList();
		}

		List<StagedModel> stagedModels = new ArrayList<>();

		StagedModelDataHandler<JournalArticle> stagedModelDataHandler =
			_getStagedModelDataHandler();

		List<JournalArticle> journalArticles = new ArrayList<>();

		journalArticles.addAll(
			journalArticleLocalService.getArticles(
				journalArticle.getGroupId(), journalArticle.getArticleId()));

		for (JournalArticle curJournalArticle : journalArticles) {
			if (ArrayUtil.contains(
					stagedModelDataHandler.getExportableStatuses(),
					curJournalArticle.getStatus())) {

				stagedModels.add(curJournalArticle);
			}
		}

		return stagedModels;
	}

	@Reference
	protected ExportImportChangesetMVCActionCommandHelper
		exportImportChangesetMVCActionCommandHelper;

	@Reference
	protected JournalArticleLocalService journalArticleLocalService;

	private StagedModelDataHandler<JournalArticle>
		_getStagedModelDataHandler() {

		return (StagedModelDataHandler<JournalArticle>)
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				JournalArticle.class.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasePublishArticlesMVCActionCommand.class);

}