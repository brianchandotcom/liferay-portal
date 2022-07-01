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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class KBAdminNavigationDisplayContext {

	public KBAdminNavigationDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public List<NavigationItem> getInfoPanelNavigationItems() {
		return ListUtil.fromArray(
			NavigationItemBuilder.setActive(
				true
			).setHref(
				() -> {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)_httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return themeDisplay.getURLCurrent();
				}
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "details")
			).build());
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String mvcPath = ParamUtil.getString(_httpServletRequest, "mvcPath");

		return NavigationItemListBuilder.add(
			() -> PortletPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
				portletDisplay.getId(), KBActionKeys.VIEW),
			NavigationItemBuilder.setActive(
				() -> {
					if (!mvcPath.equals("/admin/view_suggestions.jsp") &&
						!mvcPath.equals("/admin/view_templates.jsp")) {

						return true;
					}

					return false;
				}
			).setHref(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/view.jsp"
				).buildString()
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "articles")
			).build()
		).add(
			() -> AdminPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), KBActionKeys.VIEW_KB_TEMPLATES),
			NavigationItemBuilder.setActive(
				() -> {
					if (mvcPath.equals("/admin/view_templates.jsp")) {
						return true;
					}

					return false;
				}
			).setHref(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/view_templates.jsp"
				).buildString()
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "templates")
			).build()
		).add(
			() -> AdminPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), KBActionKeys.VIEW_SUGGESTIONS),
			NavigationItemBuilder.setActive(
				() -> {
					if (mvcPath.equals("/admin/view_suggestions.jsp")) {
						return true;
					}

					return false;
				}
			).setHref(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/view_suggestions.jsp"
				).buildString()
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "suggestions")
			).build()
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}