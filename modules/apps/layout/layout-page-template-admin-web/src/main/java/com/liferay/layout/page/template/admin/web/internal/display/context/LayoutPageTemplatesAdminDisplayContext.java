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

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplatesAdminDisplayContext {

	public LayoutPageTemplatesAdminDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			_liferayPortletRequest);

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<NavigationItem> getNavigationItems() {
		Group group = _themeDisplay.getScopeGroup();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		return new NavigationItemList() {
			{
				if (!(stagingGroupHelper.isLocalLiveGroup(group) ||
					  stagingGroupHelper.isRemoteLiveGroup(group))) {

					add(
						navigationItem -> {
							navigationItem.setActive(
								Objects.equals(getTabs1(), "master-pages"));
							navigationItem.setHref(
								getPortletURL(), "tabs1", "master-pages");
							navigationItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "masters"));
						});

					add(
						navigationItem -> {
							navigationItem.setActive(
								Objects.equals(getTabs1(), "page-templates"));
							navigationItem.setHref(
								getPortletURL(), "tabs1", "page-templates");
							navigationItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "page-templates"));
						});
				}

				if (!group.isCompany() &&
					!(stagingGroupHelper.isLocalLiveGroup(group) ||
					  stagingGroupHelper.isRemoteLiveGroup(group))) {

					add(
						navigationItem -> {
							navigationItem.setActive(
								Objects.equals(
									getTabs1(), "display-page-templates"));
							navigationItem.setHref(
								getPortletURL(), "tabs1",
								"display-page-templates");
							navigationItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									"display-page-templates"));
						});
				}
			}
		};
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", getTabs1());

		return portletURL;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(
			_liferayPortletRequest, "tabs1", "master-pages");

		return _tabs1;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;

}