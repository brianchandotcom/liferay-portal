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

package com.liferay.portal.search.web.internal.search.bar.portlet.action;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletDisplayContext;
import com.liferay.portal.search.web.internal.search.bar.portlet.configuration.SearchBarPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.search.bar.portlet.helper.SearchBarPrecedenceHelper;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SearchBarPortletKeys.SEARCH_BAR,
	service = ConfigurationAction.class
)
public class SearchBarConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			new SearchBarPortletDisplayContext();

		SearchBarPortletInstanceConfiguration
			searchBarPortletInstanceConfiguration =
				_getSearchBarPortletInstanceConfiguration(
					themeDisplay.getPortletDisplay());

		long displayStyleGroupId =
			searchBarPortletInstanceConfiguration.displayStyleGroupId();

		if (displayStyleGroupId <= 0) {
			displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		searchBarPortletDisplayContext.setDisplayStyleGroupId(
			displayStyleGroupId);
		searchBarPortletDisplayContext.setDisplayWarningIgnoredConfiguration(
			searchBarPrecedenceHelper.isDisplayWarningIgnoredConfiguration(
				themeDisplay, true));
		searchBarPortletDisplayContext.setSearchBarPortletInstanceConfiguration(
			searchBarPortletInstanceConfiguration);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, searchBarPortletDisplayContext);

		return "/search/bar/configuration.jsp";
	}

	@Reference
	protected SearchBarPrecedenceHelper searchBarPrecedenceHelper;

	private SearchBarPortletInstanceConfiguration
		_getSearchBarPortletInstanceConfiguration(
			PortletDisplay portletDisplay) {

		try {
			return portletDisplay.getPortletInstanceConfiguration(
				SearchBarPortletInstanceConfiguration.class);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

}