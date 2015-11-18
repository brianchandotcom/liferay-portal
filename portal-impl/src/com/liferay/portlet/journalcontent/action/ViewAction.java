/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journalcontent.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ViewAction extends WebContentAction {

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long articleGroupId = PrefsParamUtil.getLong(
			portletPreferences, renderRequest, "groupId",
			themeDisplay.getScopeGroupId());

		String articleId = PrefsParamUtil.getString(
			portletPreferences, renderRequest, "articleId");
		String ddmTemplateKey = PrefsParamUtil.getString(
			portletPreferences, renderRequest, "ddmTemplateKey");

		String viewMode = ParamUtil.getString(renderRequest, "viewMode");
		String languageId = LanguageUtil.getLanguageId(renderRequest);
		int page = ParamUtil.getInteger(renderRequest, "page", 1);
		String xmlRequest = PortletRequestUtil.toXML(
			renderRequest, renderResponse);

		JournalArticle article = null;
		JournalArticleDisplay articleDisplay = null;

		if ((articleGroupId > 0) && Validator.isNotNull(articleId)) {
			try {
				article = JournalArticleLocalServiceUtil.getArticle(
					articleGroupId, articleId);

				double version = article.getVersion();

				articleDisplay = JournalContentUtil.getDisplay(
					articleGroupId, articleId, version, ddmTemplateKey,
					viewMode, languageId, themeDisplay, page, xmlRequest);
			}
			catch (Exception e) {
				renderRequest.removeAttribute(WebKeys.JOURNAL_ARTICLE);

				articleDisplay = JournalContentUtil.getDisplay(
					articleGroupId, articleId, ddmTemplateKey, viewMode,
					languageId, themeDisplay, page, xmlRequest);
			}
		}

		if (article != null) {
			renderRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, article);
		}

		if (articleDisplay != null) {
			renderRequest.setAttribute(
				WebKeys.JOURNAL_ARTICLE_DISPLAY, articleDisplay);
		}
		else {
			renderRequest.removeAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);
		}

		return actionMapping.findForward("portlet.journal_content.view");
	}

}