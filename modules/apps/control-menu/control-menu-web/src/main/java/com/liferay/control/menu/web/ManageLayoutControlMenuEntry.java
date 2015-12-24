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

package com.liferay.control.menu.web;

import com.liferay.control.menu.BaseControlMenuEntry;
import com.liferay.control.menu.ControlMenuEntry;
import com.liferay.control.menu.constants.ControlMenuCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"control.menu.category.key=" + ControlMenuCategoryKeys.TOOLS,
		"service.ranking:Integer=200"
	},
	service = ControlMenuEntry.class
)
public class ManageLayoutControlMenuEntry
	extends BaseControlMenuEntry implements ControlMenuEntry {

	@Override
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> data = super.getData(request);

		data.put("qa-id", "edit");

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		data.put("title", getLabel(themeDisplay.getLocale()));

		return data;
	}

	@Override
	public String getIconCssClass(HttpServletRequest request) {
		return "cog";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "edit");
	}

	@Override
	public String getURL(HttpServletRequest request) {
		String portletId = PortletProviderUtil.getPortletId(
			Layout.class.getName(), PortletProvider.Action.EDIT);

		PortletURL editPageURL = PortalUtil.getControlPanelPortletURL(
			request, portletId, PortletRequest.RENDER_PHASE);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		editPageURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		editPageURL.setParameter("selPlid", String.valueOf(layout.getPlid()));
		editPageURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));
		editPageURL.setParameter("treeId", "layoutsTree");
		editPageURL.setParameter("viewLayout", Boolean.TRUE.toString());

		return editPageURL.toString();
	}

	@Override
	public boolean isShow(HttpServletRequest request) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return false;
		}

		if (!(themeDisplay.isShowLayoutTemplatesIcon() ||
			  themeDisplay.isShowPageSettingsIcon())) {

			return false;
		}

		return super.isShow(request);
	}

}