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

package com.liferay.nested.portlets.web.display.context;

import com.liferay.nested.portlets.web.configuration.NestedPortletsConfiguration;
import com.liferay.nested.portlets.web.configuration.NestedPortletsPortletInstanceConfiguration;
import com.liferay.portal.kernel.configuration.module.ConfigurationException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.plugin.PluginUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class NestedPortletsDisplayContext {

	public NestedPortletsDisplayContext(
			HttpServletRequest request,
			NestedPortletsConfiguration nestedPortletsConfiguration)
		throws ConfigurationException {

		_request = request;
		_nestedPortletsConfiguration = nestedPortletsConfiguration;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_nestedPortletsPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				NestedPortletsPortletInstanceConfiguration.class);
	}

	public String getLayoutTemplateId() {
		if (_layoutTemplateId != null) {
			return _layoutTemplateId;
		}

		_layoutTemplateId =
			_nestedPortletsPortletInstanceConfiguration.layoutTemplateId();

		if (Validator.isNull(_layoutTemplateId)) {
			_layoutTemplateId =
				_nestedPortletsConfiguration.layoutTemplateDefault();
		}

		return _layoutTemplateId;
	}

	public List<LayoutTemplate> getLayoutTemplates() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<LayoutTemplate> layoutTemplates =
			LayoutTemplateLocalServiceUtil.getLayoutTemplates(
				themeDisplay.getThemeId());

		layoutTemplates = PluginUtil.restrictPlugins(
			layoutTemplates, themeDisplay.getUser());

		final List<String> unSupportedLayoutTemplateIds =
			getUnsupportedLayoutTemplateIds();

		return ListUtil.filter(
			layoutTemplates,
			new PredicateFilter<LayoutTemplate>() {

				@Override
				public boolean filter(LayoutTemplate layoutTemplate) {
					return !unSupportedLayoutTemplateIds.contains(
						layoutTemplate.getLayoutTemplateId());
				}

			});
	}

	public boolean isPortletSetupShowBorders() {
		if (_portletSetupShowBorders != null) {
			return _portletSetupShowBorders;
		}

		_portletSetupShowBorders =
			_nestedPortletsPortletInstanceConfiguration.
				portletSetupShowBorders();

		if (Validator.isNull(_portletSetupShowBorders)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_portletSetupShowBorders = GetterUtil.getBoolean(
				themeDisplay.getThemeSetting(
					"portlet-setup-show-borders-default"),
				true);
		}

		return _portletSetupShowBorders;
	}

	protected List<String> getUnsupportedLayoutTemplateIds() {
		return ListUtil.fromArray(
			_nestedPortletsConfiguration.layoutTemplatesUnsupported());
	}

	private String _layoutTemplateId;
	private final NestedPortletsConfiguration _nestedPortletsConfiguration;
	private final NestedPortletsPortletInstanceConfiguration
		_nestedPortletsPortletInstanceConfiguration;
	private Boolean _portletSetupShowBorders;
	private final HttpServletRequest _request;

}