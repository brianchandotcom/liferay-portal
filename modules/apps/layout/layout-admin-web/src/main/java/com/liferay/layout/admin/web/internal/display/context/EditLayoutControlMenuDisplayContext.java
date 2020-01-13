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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditLayoutControlMenuDisplayContext {

	public EditLayoutControlMenuDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getDropdownItems() {
		return new DropdownItemList() {
			{
				Layout layout = _themeDisplay.getLayout();

				if (layout.getClassNameId() == PortalUtil.getClassNameId(
						Layout.class)) {

					layout = LayoutLocalServiceUtil.fetchLayout(
						layout.getClassPK());
				}

				PortletURL editPageURL = PortalUtil.getControlPanelPortletURL(
					_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
					PortletRequest.RENDER_PHASE);

				editPageURL.setParameter(
					"mvcRenderCommandName", "/layout/edit_layout");
				editPageURL.setParameter(
					"redirect", _themeDisplay.getURLCurrent());
				editPageURL.setParameter(
					"backURL", _themeDisplay.getURLCurrent());

				if (layout.isSystem()) {
					editPageURL.setParameter(
						"portletResource",
						LayoutPageTemplateAdminPortletKeys.
							LAYOUT_PAGE_TEMPLATES);
				}

				editPageURL.setParameter(
					"groupId", String.valueOf(layout.getGroupId()));
				editPageURL.setParameter(
					"selPlid", String.valueOf(layout.getPlid()));
				editPageURL.setParameter(
					"privateLayout", String.valueOf(layout.isPrivateLayout()));

				add(
					dropdownItem -> {
						dropdownItem.setHref(editPageURL.toString());

						ResourceBundle resourceBundle =
							ResourceBundleUtil.getBundle(
								_themeDisplay.getLocale(), getClass());

						dropdownItem.setLabel(
							HtmlUtil.escape(
								LanguageUtil.get(
									resourceBundle, "configure-page")));
					});
			}
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}