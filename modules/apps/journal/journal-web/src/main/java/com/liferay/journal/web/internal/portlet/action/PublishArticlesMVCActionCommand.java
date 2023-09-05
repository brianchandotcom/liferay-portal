/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.portlet.action.ExportImportChangesetMVCActionCommandHelper;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Szimko
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/publish_articles"
	},
	service = MVCActionCommand.class
)
public class PublishArticlesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String[] articleIds = ParamUtil.getStringValues(
			actionRequest, "rowIdsJournalArticle");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		Changeset.Builder builder = Changeset.create();

		for (String articleId : articleIds) {
			JournalArticle journalArticle = _fetchArticle(groupId, articleId);

			builder = builder.addStagedModel(() -> journalArticle);
		}

		Changeset changeset = builder.build();

		_exportImportChangesetMVCActionCommandHelper.publish(
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

	private StagedModelDataHandler<JournalArticle>
		_getStagedModelDataHandler() {

		return (StagedModelDataHandler<JournalArticle>)
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				JournalArticle.class.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PublishArticlesMVCActionCommand.class);

	@Reference
	private ExportImportChangesetMVCActionCommandHelper
		_exportImportChangesetMVCActionCommandHelper;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}