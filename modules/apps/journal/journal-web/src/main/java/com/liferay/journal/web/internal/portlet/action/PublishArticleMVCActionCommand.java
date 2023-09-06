/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Zoltan Csaszi
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/publish_article"
	},
	service = MVCActionCommand.class
)
public class PublishArticleMVCActionCommand
	extends BasePublishArticlesMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String articleId = ParamUtil.getString(actionRequest, "articleId");

		Changeset.Builder builder = Changeset.create();

		JournalArticle journalArticle = fetchArticle(groupId, articleId);

		Changeset changeset = builder.addStagedModel(
			() -> journalArticle
		).addMultipleStagedModel(
			() -> getJournalArticleVersions(journalArticle)
		).build();

		exportImportChangesetMVCActionCommandHelper.publish(
			actionRequest, actionResponse, changeset);
	}

}