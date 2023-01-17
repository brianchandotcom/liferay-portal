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

import com.liferay.diff.exception.CompareVersionsException;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.journal.web.internal.portlet.JournalPortlet;
import com.liferay.journal.web.internal.util.JournalHelperUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/compare_versions"
	},
	service = MVCRenderCommand.class
)
public class CompareVersionsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			_compareVersions(renderRequest, renderResponse);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return "/compare_versions.jsp";
	}

	private void _compareVersions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(renderRequest, "groupId");
		String articleId = ParamUtil.getString(renderRequest, "articleId");

		String sourceArticleId = ParamUtil.getString(
			renderRequest, "sourceVersion");

		int index = sourceArticleId.lastIndexOf(
			JournalPortlet.VERSION_SEPARATOR);

		if (index != -1) {
			sourceArticleId = sourceArticleId.substring(
				index + JournalPortlet.VERSION_SEPARATOR.length());
		}

		double sourceVersion = GetterUtil.getDouble(sourceArticleId);

		String targetArticleId = ParamUtil.getString(
			renderRequest, "targetVersion");

		index = targetArticleId.lastIndexOf(JournalPortlet.VERSION_SEPARATOR);

		if (index != -1) {
			targetArticleId = targetArticleId.substring(
				index + JournalPortlet.VERSION_SEPARATOR.length());
		}

		double targetVersion = GetterUtil.getDouble(targetArticleId);

		if ((sourceVersion == 0) && (targetVersion == 0)) {
			List<JournalArticle> sourceArticles =
				JournalArticleServiceUtil.getArticlesByArticleId(
					groupId, articleId, 0, 1,
					new ArticleVersionComparator(false));

			JournalArticle sourceArticle = sourceArticles.get(0);

			sourceVersion = sourceArticle.getVersion();

			List<JournalArticle> targetArticles =
				JournalArticleServiceUtil.getArticlesByArticleId(
					groupId, articleId, 0, 1,
					new ArticleVersionComparator(true));

			JournalArticle targetArticle = targetArticles.get(0);

			targetVersion = targetArticle.getVersion();
		}

		if (sourceVersion > targetVersion) {
			double tempVersion = targetVersion;

			targetVersion = sourceVersion;
			sourceVersion = tempVersion;
		}

		String languageId = _getLanguageId(
			renderRequest, groupId, articleId, sourceVersion, targetVersion);

		String diffHtmlResults = null;

		try {
			diffHtmlResults = JournalHelperUtil.diffHtml(
				groupId, articleId, sourceVersion, targetVersion, languageId,
				new PortletRequestModel(renderRequest, renderResponse),
				themeDisplay);
		}
		catch (CompareVersionsException compareVersionsException) {
			renderRequest.setAttribute(
				WebKeys.DIFF_VERSION, compareVersionsException.getVersion());
		}

		renderRequest.setAttribute(WebKeys.DIFF_HTML_RESULTS, diffHtmlResults);
		renderRequest.setAttribute(WebKeys.SOURCE_VERSION, sourceVersion);
		renderRequest.setAttribute(WebKeys.TARGET_VERSION, targetVersion);
	}

	private String _getLanguageId(
			RenderRequest renderRequest, long groupId, String articleId,
			double sourceVersion, double targetVersion)
		throws Exception {

		JournalArticle sourceArticle =
			JournalArticleLocalServiceUtil.fetchArticle(
				groupId, articleId, sourceVersion);

		JournalArticle targetArticle =
			JournalArticleLocalServiceUtil.fetchArticle(
				groupId, articleId, targetVersion);

		Set<Locale> locales = new HashSet<>();

		for (String locale : sourceArticle.getAvailableLanguageIds()) {
			locales.add(LocaleUtil.fromLanguageId(locale));
		}

		for (String locale : targetArticle.getAvailableLanguageIds()) {
			locales.add(LocaleUtil.fromLanguageId(locale));
		}

		String languageId = ParamUtil.get(
			renderRequest, "languageId", targetArticle.getDefaultLanguageId());

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		if (!locales.contains(locale)) {
			languageId = targetArticle.getDefaultLanguageId();
		}

		renderRequest.setAttribute(WebKeys.AVAILABLE_LOCALES, locales);
		renderRequest.setAttribute(WebKeys.LANGUAGE_ID, languageId);

		return languageId;
	}

}