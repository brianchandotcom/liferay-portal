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

package com.liferay.product.navigation.site.administration.internal.display.context;

import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class ContentPanelCategoryDisplayContext {

	public ContentPanelCategoryDisplayContext(PortletRequest portletRequest) {
		_portletRequest = portletRequest;
	}

	public DropdownItemList getScopesDropdownItemList() throws Exception {
		final ThemeDisplay themeDisplay =
			(ThemeDisplay)_portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		final PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		final PanelCategoryHelper panelCategoryHelper =
			(PanelCategoryHelper)_portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_HELPER);

		String portletId = themeDisplay.getPpid();

		if (Validator.isNull(portletId) ||
			!panelCategoryHelper.containsPortlet(
				portletId, PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
				permissionChecker, themeDisplay.getSiteGroup())) {

			portletId = panelCategoryHelper.getFirstPortletId(
				PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
				permissionChecker, themeDisplay.getSiteGroup());
		}

		final PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			_portletRequest, themeDisplay.getSiteGroup(), portletId, 0, 0,
			PortletRequest.RENDER_PHASE);

		final String itemLabel = LanguageUtil.get(
			themeDisplay.getLocale(), "default-scope");

		List<Layout> scopeLayouts = LayoutLocalServiceUtil.getScopeGroupLayouts(
			themeDisplay.getSiteGroupId());

		DropdownItemList dropdownItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(portletURL.toString());
						dropdownItem.setLabel(itemLabel);
					});
			}
		};

		for (Layout curScopeLayout : scopeLayouts) {
			Group scopeGroup = curScopeLayout.getScopeGroup();

			if (Validator.isNull(portletId) ||
				!panelCategoryHelper.containsPortlet(
					portletId, PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
					permissionChecker, scopeGroup)) {

				portletId = panelCategoryHelper.getFirstPortletId(
					PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
					permissionChecker, scopeGroup);
			}

			if (Validator.isNull(portletId)) {
				continue;
			}

			PortletURL layoutItemPortletURL =
				PortalUtil.getControlPanelPortletURL(
					_portletRequest, scopeGroup, portletId, 0, 0,
					PortletRequest.RENDER_PHASE);

			String layoutItemLabel = LanguageUtil.get(
				themeDisplay.getLocale(),
				HtmlUtil.escape(
					curScopeLayout.getName(themeDisplay.getLocale())));

			dropdownItems.add(
				dropdownItem -> {
					dropdownItem.setHref(layoutItemPortletURL.toString());
					dropdownItem.setLabel(layoutItemLabel);
				});
		}

		return dropdownItems;
	}

	private final PortletRequest _portletRequest;

}