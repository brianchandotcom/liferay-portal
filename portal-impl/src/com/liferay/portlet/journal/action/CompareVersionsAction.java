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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.diff.CompareVersionsException;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.comparator.ArticleVersionComparator;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Eudaldo Alonso
 */
public class CompareVersionsAction extends PortletAction {

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(resourceRequest, "groupId");
		String articleId = ParamUtil.getString(resourceRequest, "articleId");
		double sourceVersion = ParamUtil.getDouble(
			resourceRequest, "filterSourceVersion");
		double targetVersion = ParamUtil.getDouble(
			resourceRequest, "filterTargetVersion");
		String languageId = ParamUtil.getString(resourceRequest, "languageId");

		String diffHtmlResults = null;

		try {
			diffHtmlResults = JournalUtil.diffHtml(
				groupId, articleId, sourceVersion, targetVersion, languageId,
				new PortletRequestModel(resourceRequest, resourceResponse),
				themeDisplay);
		}
		catch (CompareVersionsException cve) {
			resourceRequest.setAttribute(
				WebKeys.DIFF_VERSION, cve.getVersion());
		}

		resourceRequest.setAttribute(
			WebKeys.DIFF_HTML_RESULTS, diffHtmlResults);

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/taglib/ui/diff_version_comparator/diff_html.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

}