/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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
 * @author Daniel Szimko
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/publish_articles"
	},
	service = MVCActionCommand.class
)
public class PublishArticlesMVCActionCommand
	extends BasePublishArticlesMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String[] articleIds = ParamUtil.getStringValues(
			actionRequest, "rowIdsJournalArticle");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		Changeset.Builder builder = Changeset.create();

		for (String articleId : articleIds) {
			JournalArticle journalArticle = fetchArticle(groupId, articleId);

			builder = builder.addStagedModel(
				() -> journalArticle
			).addMultipleStagedModel(
				() -> getJournalArticleVersions(journalArticle)
			);
		}

		Changeset changeset = builder.build();

		exportImportChangesetMVCActionCommandHelper.publish(
			actionRequest, actionResponse, changeset);
	}

}