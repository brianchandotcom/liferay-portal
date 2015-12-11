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

package com.liferay.mentions.web.servlet.taglib.ui;

import com.liferay.mentions.constants.MentionsWebKeys;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true, property = {"service.ranking:Integer=90"},
	service = FormNavigatorEntry.class
)
public class MentionsSitesFormNavigatorEntry
	extends BaseMentionsFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_SITES_SOCIAL;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES;
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		Group liveGroup = (Group)request.getAttribute("site.liveGroup");

		UnicodeProperties typeSettingsProperties = null;

		if (liveGroup != null) {
			typeSettingsProperties = liveGroup.getTypeSettingsProperties();
		}
		else {
			typeSettingsProperties = new UnicodeProperties();
		}

		boolean groupMentionsEnabled = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("mentionsEnabled"), true);

		request.setAttribute(
			MentionsWebKeys.GROUP_MENTIONS_ENABLED, groupMentionsEnabled);

		super.include(request, response);
	}

	@Override
	public boolean isVisible(User user, Object formModelBean) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		HttpServletRequest request = themeDisplay.getRequest();

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(themeDisplay.getCompanyId(), true);

		return PrefsParamUtil.getBoolean(
			companyPortletPreferences, request, "mentionsEnabled", true);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.mentions.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/sites_admin/mentions.jsp";
	}

}