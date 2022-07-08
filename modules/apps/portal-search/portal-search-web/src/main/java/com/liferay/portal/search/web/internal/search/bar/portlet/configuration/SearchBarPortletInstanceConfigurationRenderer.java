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

package com.liferay.portal.search.web.internal.search.bar.portlet.configuration;

import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.search.rest.configuration.SearchSuggestionsCompanyConfiguration;
import com.liferay.portal.search.web.internal.search.bar.portlet.display.context.SearchBarPortletInstanceConfigurationDisplayContext;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = {
		"com.liferay.portal.search.rest.configuration.SearchSuggestionsCompanyConfiguration",
		"com.liferay.portal.search.web.internal.search.bar.portlet.configuration.SearchBarPortletInstanceConfiguration"
	},
	immediate = true, service = ConfigurationFormRenderer.class
)
public class SearchBarPortletInstanceConfigurationRenderer
	implements ConfigurationFormRenderer {

	@Override
	public String getPid() {
		return SearchBarPortletInstanceConfiguration.class.getName();
	}

	@Override
	public Map<String, Object> getRequestParameters(
		HttpServletRequest httpServletRequest) {

		return HashMapBuilder.<String, Object>put(
			"displayStyle",
			ParamUtil.getString(httpServletRequest, "displayStyle")
		).put(
			"displayStyleGroupId",
			ParamUtil.getLong(httpServletRequest, "displayStyleGroupId")
		).put(
			"enableSuggestions",
			ParamUtil.getBoolean(httpServletRequest, "enableSuggestions")
		).put(
			"suggestionsContributorConfigurations",
			ParamUtil.getStringValues(
				httpServletRequest, "suggestionsContributorConfiguration")
		).put(
			"suggestionsDisplayThreshold",
			ParamUtil.getInteger(
				httpServletRequest, "suggestionsDisplayThreshold")
		).build();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		SearchBarPortletInstanceConfigurationDisplayContext
			searchBarPortletInstanceConfigurationDisplayContext =
				new SearchBarPortletInstanceConfigurationDisplayContext();

		searchBarPortletInstanceConfigurationDisplayContext.setDisplayStyle(
			_searchBarPortletInstanceConfiguration.displayStyle());
		searchBarPortletInstanceConfigurationDisplayContext.
			setDisplayStyleGroupId(
				_searchBarPortletInstanceConfiguration.displayStyleGroupId());
		searchBarPortletInstanceConfigurationDisplayContext.
			setEnableSuggestions(
				_searchBarPortletInstanceConfiguration.enableSuggestions());
		searchBarPortletInstanceConfigurationDisplayContext.
			setSuggestionsConfigurationVisible(
				_searchSuggestionsCompanyConfiguration.
					enableSuggestionsEndpoint());
		searchBarPortletInstanceConfigurationDisplayContext.
			setSuggestionsContributorConfigurations(
				_searchBarPortletInstanceConfiguration.
					suggestionsContributorConfigurations());
		searchBarPortletInstanceConfigurationDisplayContext.
			setSuggestionsDisplayThreshold(
				_searchBarPortletInstanceConfiguration.
					suggestionsDisplayThreshold());

		httpServletRequest.setAttribute(
			SearchBarPortletInstanceConfigurationDisplayContext.class.getName(),
			searchBarPortletInstanceConfigurationDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/search/bar/portlet_instance_configuration.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_searchBarPortletInstanceConfiguration =
			ConfigurableUtil.createConfigurable(
				SearchBarPortletInstanceConfiguration.class, properties);

		_searchSuggestionsCompanyConfiguration =
			ConfigurableUtil.createConfigurable(
				SearchSuggestionsCompanyConfiguration.class, properties);
	}

	@Reference
	private JSPRenderer _jspRenderer;

	private volatile SearchBarPortletInstanceConfiguration
		_searchBarPortletInstanceConfiguration;
	private volatile SearchSuggestionsCompanyConfiguration
		_searchSuggestionsCompanyConfiguration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.search.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}