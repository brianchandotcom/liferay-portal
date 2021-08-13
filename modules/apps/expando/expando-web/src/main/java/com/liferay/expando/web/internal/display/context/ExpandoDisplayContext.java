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

package com.liferay.expando.web.internal.display.context;

import com.liferay.expando.constants.ExpandoPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ExpandoDisplayContext {

	public ExpandoDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteCustomFields");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"deleteExpandosURL",
			() -> {
				PortletResponse portletResponse =
					(PortletResponse)_httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE);

				return PortletURLBuilder.createActionURL(
					PortalUtil.getLiferayPortletResponse(portletResponse)
				).setActionName(
					"deleteExpandos"
				).buildString();
			}
		).build();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				PortletResponse portletResponse =
					(PortletResponse)_httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE);

				LiferayPortletResponse liferayPortletResponse =
					PortalUtil.getLiferayPortletResponse(portletResponse);

				String modelResource = ParamUtil.getString(
					_httpServletRequest, "modelResource");

				dropdownItem.setHref(
					liferayPortletResponse.createRenderURL(), "mvcPath",
					"/edit/select_field_type.jsp", "redirect",
					PortalUtil.getCurrentURL(_httpServletRequest),
					"modelResource", modelResource);

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "add-custom-field"));
			}
		).build();
	}

	public List<NavigationItem> getNavigationItems(String label) {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, label));
			}
		).build();
	}

	public boolean showCreationMenu() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), ExpandoPortletKeys.EXPANDO,
			ActionKeys.ADD_EXPANDO);
	}

	private final HttpServletRequest _httpServletRequest;

}