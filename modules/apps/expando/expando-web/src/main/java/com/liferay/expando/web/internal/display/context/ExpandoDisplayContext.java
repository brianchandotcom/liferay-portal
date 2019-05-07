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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

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
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						PortletResponse portletResponse =
							(PortletResponse)_httpServletRequest.getAttribute(
								JavaConstants.JAVAX_PORTLET_RESPONSE);

						dropdownItem.setHref(
							StringBundler.concat(
								"javascript:", portletResponse.getNamespace(),
								"deleteCustomFields();"));

						dropdownItem.setIcon("trash");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public CreationMenu getCreationMenu() throws PortalException {
		return new CreationMenu() {
			{
				addDropdownItem(
					dropdownItem -> {
						PortletResponse portletResponse =
							(PortletResponse)_httpServletRequest.getAttribute(
								JavaConstants.JAVAX_PORTLET_RESPONSE);

						LiferayPortletResponse liferayPortletResponse =
							PortalUtil.getLiferayPortletResponse(
								portletResponse);

						String modelResource = ParamUtil.getString(
							_httpServletRequest, "modelResource");

						dropdownItem.setHref(
							liferayPortletResponse.createRenderURL(), "mvcPath",
							"/edit/select_field_type.jsp", "redirect",
							PortalUtil.getCurrentURL(_httpServletRequest),
							"modelResource", modelResource);

						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "add-custom-field"));
					});
			}
		};
	}

	public List<NavigationItem> getNavigationItems(final String label) {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, label));
					});
			}
		};
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