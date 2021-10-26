/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.development.web.internal.portlet.action;

import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.development.web.internal.constants.MVCActionCommandNames;
import com.liferay.search.experiences.development.web.internal.constants.SXPDevelopmentPortletKeys;
import com.liferay.search.experiences.development.web.internal.importer.WikipediaArticlesImporter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPDevelopmentPortletKeys.SEARCH_EXPERIENCES_DEVELOPMENT,
		"mvc.command.name=" + MVCActionCommandNames.IMPORT_WIKIPEDIA_ARTICLES
	},
	service = MVCActionCommand.class
)
public class ImportWikipediaArticlesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		ImportMVCActionCommandUtil.doProcessAction(
			actionRequest, this::_doProcessAction);
	}

	private int _doProcessAction(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		WikipediaArticlesImporter wikipediaArticlesImporter =
			WikipediaArticlesImporter.builder(
				_journalArticleLocalService
			).groupIds(
				StringUtil.split(
					ParamUtil.getString(
						actionRequest, "groupIds",
						String.valueOf(themeDisplay.getScopeGroupId())),
					0L)
			).languageId(
				ParamUtil.getString(actionRequest, "languageId", "en_US")
			).limit(
				ParamUtil.getInteger(actionRequest, "count", 100)
			).portletRequest(
				actionRequest
			).seeds(
				StringUtil.split(
					ParamUtil.getString(actionRequest, "wikiArticles"))
			).userIds(
				StringUtil.split(
					ParamUtil.getString(
						actionRequest, "userIds",
						String.valueOf(themeDisplay.getUserId())),
					0L)
			).wikiLanguage(
				ParamUtil.getString(actionRequest, "wikiLanguage", "en")
			).build();

		wikipediaArticlesImporter.importWikipediaArticles();

		return wikipediaArticlesImporter.getCounter();
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}