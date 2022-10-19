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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.portlet.action.ExportImportChangesetMVCActionCommand;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.web.internal.util.JournalUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/publish_article"
	},
	service = MVCActionCommand.class
)
public class PublishArticleMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String articleId = ParamUtil.getString(actionRequest, "articleId");

		Changeset.Builder builder = Changeset.create();

		JournalArticle journalArticle = _fetchArticle(groupId, articleId);

		Changeset changeset = builder.addStagedModel(
			() -> journalArticle
		).addMultipleStagedModel(
			() -> _getJournalArticleVersions(journalArticle)
		).build();

		_exportImportChangesetMVCActionCommand.processPublishAction(
			actionRequest, actionResponse, changeset);
	}

	private JournalArticle _fetchArticle(long groupId, String articleId) {
		JournalArticle journalArticle =
			_journalArticleLocalService.fetchArticle(groupId, articleId);

		StagedModelDataHandler<JournalArticle> stagedModelDataHandler =
			_getStagedModelDataHandler();

		try {
			JournalArticle latestApprovedJournalArticle =
				_journalArticleLocalService.getArticle(
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

	private List<StagedModel> _getJournalArticleVersions(
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
			_journalArticleLocalService.getArticles(
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

	private StagedModelDataHandler<JournalArticle>
		_getStagedModelDataHandler() {

		return (StagedModelDataHandler<JournalArticle>)
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				JournalArticle.class.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PublishArticleMVCActionCommand.class);

	@Reference
	private ExportImportChangesetMVCActionCommand
		_exportImportChangesetMVCActionCommand;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}