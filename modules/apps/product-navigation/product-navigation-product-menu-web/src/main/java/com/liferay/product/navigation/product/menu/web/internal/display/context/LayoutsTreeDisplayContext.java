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

package com.liferay.product.navigation.product.menu.web.internal.display.context;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;
import com.liferay.product.navigation.product.menu.web.internal.constants.ProductNavigationProductMenuWebKeys;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class LayoutsTreeDisplayContext {

	public LayoutsTreeDisplayContext(
		LiferayPortletRequest liferayPortletRequest) {

		_liferayPortletRequest = liferayPortletRequest;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAddLayoutURL(boolean privateLayout) {
		PortletURL addLayoutURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		addLayoutURL.setParameter(
			"mvcPath", "/select_layout_page_template_entry.jsp");
		addLayoutURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		addLayoutURL.setParameter("backURL", _themeDisplay.getURLCurrent());
		addLayoutURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getSiteGroupId()));
		addLayoutURL.setParameter(
			"privateLayout", String.valueOf(privateLayout));

		return addLayoutURL.toString();
	}

	public String getAdministrationPortletURL() {
		PortletURL administrationPortletURL =
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE);

		administrationPortletURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());

		return administrationPortletURL.toString();
	}

	public String getConfigureLayoutSetURL(boolean privatePages) {
		PortletURL configureLayoutSetURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		configureLayoutSetURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout_set");
		configureLayoutSetURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		configureLayoutSetURL.setParameter(
			"backURL", _themeDisplay.getURLCurrent());
		configureLayoutSetURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		configureLayoutSetURL.setParameter(
			"privateLayout", String.valueOf(privatePages));

		return configureLayoutSetURL.toString();
	}

	public Map<String, Object> getLayoutFinderData() {
		LiferayPortletURL findLayoutsURL = PortletURLFactoryUtil.create(
			_liferayPortletRequest,
			ProductNavigationProductMenuPortletKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU,
			PortletRequest.RESOURCE_PHASE);

		findLayoutsURL.setResourceID("/product_menu/find_layouts");

		return HashMapBuilder.<String, Object>put(
			"administrationPortletNamespace",
			PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES)
		).put(
			"administrationPortletURL", getAdministrationPortletURL()
		).put(
			"findLayoutsURL", findLayoutsURL.toString()
		).put(
			"namespace", getNamespace()
		).put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		).build();
	}

	public String getNamespace() {
		return PortalUtil.getPortletNamespace(
			ProductNavigationProductMenuPortletKeys.
				PRODUCT_NAVIGATION_PRODUCT_MENU);
	}

	public Map<String, Object> getPageTypeSelectorData() {
		return HashMapBuilder.<String, Object>put(
			"addLayoutURL", getAddLayoutURL(isPrivateLayout())
		).put(
			"configureLayoutSetURL", getConfigureLayoutSetURL(isPrivateLayout())
		).put(
			"namespace", getNamespace()
		).put(
			"privateLayout", isPrivateLayout()
		).build();
	}

	public boolean isPrivateLayout() {
		Layout layout = _themeDisplay.getLayout();

		return GetterUtil.getBoolean(
			SessionClicks.get(
				PortalUtil.getHttpServletRequest(_liferayPortletRequest),
				getNamespace() +
					ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT,
				"false"),
			layout.isPrivateLayout());
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final ThemeDisplay _themeDisplay;

}