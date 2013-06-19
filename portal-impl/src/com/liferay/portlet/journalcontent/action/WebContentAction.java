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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.io.OutputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Raymond Augé
 */
public class WebContentAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if (groupId < 1) {
			groupId = GetterUtil.getLong(
				portletPreferences.getValue("groupId", null));
		}

		String articleId = ParamUtil.getString(actionRequest, "articleId");
		String ddmTemplateKey = ParamUtil.getString(
			actionRequest, "ddmTemplateKey");

		if (Validator.isNull(articleId)) {
			articleId = GetterUtil.getString(
				portletPreferences.getValue("articleId", null));
			ddmTemplateKey = GetterUtil.getString(
				portletPreferences.getValue("ddmTemplateKey", null));
		}

		String viewMode = ParamUtil.getString(actionRequest, "viewMode");
		String languageId = LanguageUtil.getLanguageId(actionRequest);
		int page = ParamUtil.getInteger(actionRequest, "page", 1);

		String xmlRequest = PortletRequestUtil.toXML(
			actionRequest, actionResponse);

		if ((groupId > 0) && Validator.isNotNull(articleId)) {
			JournalContentUtil.getDisplay(
				groupId, articleId, ddmTemplateKey, viewMode, languageId,
				themeDisplay, page, xmlRequest);
		}
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		String contentType = ParamUtil.getString(
			resourceRequest, "contentType");

		if (Validator.isNotNull(contentType)) {
			resourceResponse.setContentType(contentType);
		}

		if (resourceRequest.getResourceID() != null) {
			super.serveResource(
				actionMapping, actionForm, portletConfig, resourceRequest,
				resourceResponse);
		}
		else {
			PortletPreferences portletPreferences =
				resourceRequest.getPreferences();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(resourceRequest, "groupId");

			if (groupId < 1) {
				groupId = GetterUtil.getLong(
					portletPreferences.getValue("groupId", null));
			}

			String articleId = ParamUtil.getString(
				resourceRequest, "articleId");
			String ddmTemplateKey = ParamUtil.getString(
				resourceRequest, "ddmTemplateKey");

			if (Validator.isNull(articleId)) {
				articleId = GetterUtil.getString(
					portletPreferences.getValue("articleId", null));
				ddmTemplateKey = GetterUtil.getString(
					portletPreferences.getValue("ddmTemplateKey", null));
			}

			String viewMode = ParamUtil.getString(resourceRequest, "viewMode");
			String languageId = LanguageUtil.getLanguageId(resourceRequest);
			int page = ParamUtil.getInteger(resourceRequest, "page", 1);
			String xmlRequest = PortletRequestUtil.toXML(
				resourceRequest, resourceResponse);

			JournalArticleDisplay articleDisplay = null;

			if ((groupId > 0) && Validator.isNotNull(articleId)) {
				articleDisplay = JournalContentUtil.getDisplay(
					groupId, articleId, ddmTemplateKey, viewMode, languageId,
					themeDisplay, page, xmlRequest);
			}

			if (articleDisplay != null) {
				OutputStream os = resourceResponse.getPortletOutputStream();

				try {
					String content = articleDisplay.getContent();

					byte[] bytes = content.getBytes(StringPool.UTF8);

					os.write(bytes);
				}
				finally {
					os.close();
				}
			}
		}
	}

}