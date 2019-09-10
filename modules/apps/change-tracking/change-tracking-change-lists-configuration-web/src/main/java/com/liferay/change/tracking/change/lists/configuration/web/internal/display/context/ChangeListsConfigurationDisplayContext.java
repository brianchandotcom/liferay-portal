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

package com.liferay.change.tracking.change.lists.configuration.web.internal.display.context;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Máté Thurzó
 * @author Samuel Trong Tran
 */
public class ChangeListsConfigurationDisplayContext {

	public ChangeListsConfigurationDisplayContext(
		CTPreferencesLocalService ctPreferencesLocalService,
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_ctPreferencesLocalService = ctPreferencesLocalService;
		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				_themeDisplay.getCompanyId(), 0);

		if (ctPreferences == null) {
			_changeListsEnabled = false;
		}
		else {
			_changeListsEnabled = true;
		}
	}

	public String getActionURL() {
		PortletURL actionURL = _renderResponse.createActionURL();

		if (Objects.equals(getNavigation(), "global-settings")) {
			actionURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/change_lists/update_global_change_lists_configuration");
		}
		else {
			actionURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/change_lists/update_user_change_lists_configuration");
		}

		return actionURL.toString();
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		if (isChangeListsEnabled()) {
			_navigation = ParamUtil.getString(
				_httpServletRequest, "navigation", "global-settings");
		}
		else {
			_navigation = "global-settings";
		}

		return _navigation;
	}

	public List<NavigationItem> getViewNavigationItems() {
		return new NavigationItemList() {
			{
				String navigation = getNavigation();

				add(
					navigationItem -> {
						navigationItem.setActive(
							navigation.equals("global-settings"));
						navigationItem.setHref(
							_renderResponse.createRenderURL(), "navigation",
							"global-settings");
						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "global-settings"));
					});

				if (isChangeListsEnabled()) {
					add(
						navigationItem -> {
							navigationItem.setActive(
								navigation.equals("user-settings"));
							navigationItem.setHref(
								_renderResponse.createRenderURL(), "navigation",
								"user-settings");
							navigationItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "user-settings"));
						});
				}
			}
		};
	}

	public boolean isChangeListsEnabled() {
		return _changeListsEnabled;
	}

	public boolean isRequireConfirmationEnabled() {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				_themeDisplay.getCompanyId(), _themeDisplay.getUserId());

		if (ctPreferences == null) {
			return false;
		}

		return ctPreferences.isConfirmationEnabled();
	}

	private final boolean _changeListsEnabled;
	private final CTPreferencesLocalService _ctPreferencesLocalService;
	private final HttpServletRequest _httpServletRequest;
	private String _navigation;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}