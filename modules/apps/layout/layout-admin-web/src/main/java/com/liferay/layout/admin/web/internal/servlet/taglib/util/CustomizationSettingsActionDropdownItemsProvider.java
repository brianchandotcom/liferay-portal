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

package com.liferay.layout.admin.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Diego Hu
 */
public class CustomizationSettingsActionDropdownItemsProvider {

	public CustomizationSettingsActionDropdownItemsProvider(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_layoutTypePortlet = _themeDisplay.getLayoutTypePortlet();
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "toggleCustomizedViewMessage");
				dropdownItem.putData(
					"toggleCustomizedViewMessageURL",
					HttpComponentsUtil.addParameter(
						PortletURLBuilder.create(
							PortletURLFactoryUtil.create(
								_httpServletRequest,
								LayoutAdminPortletKeys.GROUP_PAGES,
								PortletRequest.ACTION_PHASE)
						).setActionName(
							"/layout_admin/toggle_customized_view"
						).buildString(),
						"customized_view",
						!_layoutTypePortlet.isCustomizedView()));
				dropdownItem.setLabel(_getCustomizedViewMessage());
			}
		).add(
			_layoutTypePortlet::isCustomizedView,
			dropdownItem -> {
				dropdownItem.putData("action", "resetCustomizationView");
				dropdownItem.putData(
					"resetCustomizationViewURL",
					PortletURLBuilder.create(
						PortletURLFactoryUtil.create(
							_httpServletRequest,
							LayoutAdminPortletKeys.GROUP_PAGES,
							PortletRequest.ACTION_PHASE)
					).setActionName(
						"/layout_admin/reset_customization_view"
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "reset-my-customizations"));
			}
		).build();
	}

	private String _getCustomizedViewMessage() {
		if (!_layoutTypePortlet.isCustomizedView()) {
			return LanguageUtil.get(
				_httpServletRequest, "view-my-customized-page");
		}
		else if (_layoutTypePortlet.isDefaultUpdated()) {
			return LanguageUtil.get(
				_httpServletRequest,
				"the-defaults-for-the-current-page-have-been-updated-click-" +
					"here-to-see-them");
		}

		return LanguageUtil.get(
			_httpServletRequest, "view-page-without-my-customizations");
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutTypePortlet _layoutTypePortlet;
	private final ThemeDisplay _themeDisplay;

}