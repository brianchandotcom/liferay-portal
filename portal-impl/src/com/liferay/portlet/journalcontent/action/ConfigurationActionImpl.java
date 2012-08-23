/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.layoutconfiguration.util.xml.PortletLogic;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Douglas Wong
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String[] extensions = actionRequest.getParameterValues("extensions");

		setPreference(actionRequest, "extensions", extensions);

		super.processAction(portletConfig, actionRequest, actionResponse);

		if (SessionErrors.isEmpty(actionRequest)) {
			updateContentSearch(actionRequest);

			updateLayout(actionRequest);
		}
	}

	protected String getArticleId(PortletRequest portletRequest) {
		String articleId = getParameter(portletRequest, "articleId");

		return articleId.toUpperCase();
	}

	protected String getRuntimePortletId(String xml) throws Exception {
		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		String instanceId = rootElement.attributeValue("instance");
		String portletId = rootElement.attributeValue("name");

		if (Validator.isNotNull(instanceId)) {
			portletId = PortletConstants.assemblePortletId(
				portletId, instanceId);
		}

		return portletId;
	}

	protected String getRuntimePortletIds(String content) throws Exception {
		StringBundler sb = new StringBundler();

		for (int index = 0;;) {
			index = content.indexOf(PortletLogic.OPEN_TAG, index);

			if (index == -1) {
				break;
			}

			int close1 = content.indexOf(PortletLogic.CLOSE_1_TAG, index);
			int close2 = content.indexOf(PortletLogic.CLOSE_2_TAG, index);

			int closeIndex = -1;

			if ((close2 == -1) || ((close1 != -1) && (close1 < close2))) {
				closeIndex = close1 + PortletLogic.CLOSE_1_TAG.length();
			}
			else {
				closeIndex = close2 + PortletLogic.CLOSE_2_TAG.length();
			}

			if (closeIndex == -1) {
				break;
			}

			if (sb.length() > 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(
				getRuntimePortletId(content.substring(index, closeIndex)));

			index = closeIndex;
		}

		if (sb.length() == 0) {
			return null;
		}

		return sb.toString();
	}

	protected String getRuntimePortletIds(
			ThemeDisplay themeDisplay, String articleId)
		throws Exception {

		JournalArticle journalArticle = null;

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			themeDisplay.getCompanyId());

		try {
			journalArticle = JournalArticleLocalServiceUtil.getDisplayArticle(
				themeDisplay.getScopeGroupId(), articleId);
		}
		catch (NoSuchArticleException nsae) {
		}

		if (journalArticle == null) {
			try {
				journalArticle =
					JournalArticleLocalServiceUtil.getDisplayArticle(
						companyGroup.getGroupId(), articleId);
			}
			catch (NoSuchArticleException nsae) {
				return null;
			}
		}

		String portletIds = getRuntimePortletIds(journalArticle.getContent());

		if (Validator.isNotNull(journalArticle.getTemplateId())) {
			JournalTemplate journalTemplate = null;

			try {
				journalTemplate = JournalTemplateLocalServiceUtil.getTemplate(
					themeDisplay.getScopeGroupId(),
					journalArticle.getTemplateId());
			}
			catch (NoSuchTemplateException nste) {
				journalTemplate = JournalTemplateLocalServiceUtil.getTemplate(
					companyGroup.getGroupId(), journalArticle.getTemplateId());
			}

			portletIds = StringUtil.add(
				portletIds, getRuntimePortletIds(journalTemplate.getXsl()));
		}

		return portletIds;
	}

	protected void updateContentSearch(PortletRequest portletRequest)
		throws Exception {

		String articleId = getArticleId(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String portletResource = ParamUtil.getString(
			portletRequest, "portletResource");

		JournalContentSearchLocalServiceUtil.updateContentSearch(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			portletResource, articleId, true);
	}

	protected void updateLayout(PortletRequest portletRequest)
		throws Exception {

		String articleId = getArticleId(portletRequest);

		if (Validator.isNull(articleId)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String portletResource = ParamUtil.getString(
			portletRequest, "portletResource");

		layoutTypePortlet.setPortletIds(
			LayoutTypePortletConstants.RUNTIME_COLUMN_PREFIX +
				portletResource,
			getRuntimePortletIds(themeDisplay, articleId));

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

}