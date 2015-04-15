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

package com.liferay.nested.portlets.web.portlet.action;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.nested.portlets.web.configuration.NestedPortletsConfiguration;
import com.liferay.nested.portlets.web.constants.NestedPortletsPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Jorge Ferrer
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.nested.portlets.web.configuration.NestedPortletsConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"javax.portlet.name=" + NestedPortletsPortletKeys.NESTED_PORTLETS
	},
	service = ConfigurationAction.class
)
public class NestedPortletsConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String layoutTemplateId = getParameter(
			actionRequest, "layoutTemplateId");

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		String oldLayoutTemplateId = portletPreferences.getValue(
			"layoutTemplateId",
			_nestedPortletsConfiguration.layoutTemplateDefault());

		if (!oldLayoutTemplateId.equals(layoutTemplateId)) {
			reorganizeNestedColumns(
				actionRequest, portletResource, layoutTemplateId,
				oldLayoutTemplateId);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		renderRequest.setAttribute(
			NestedPortletsConfiguration.class.getName(),
			_nestedPortletsConfiguration);

		return super.render(portletConfig, renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_nestedPortletsConfiguration = Configurable.createConfigurable(
			NestedPortletsConfiguration.class, properties);
	}

	protected List<String> getColumnNames(String content, String portletId) {
		Matcher matcher = _pattern.matcher(content);

		Set<String> columnIds = new HashSet<>();

		while (matcher.find()) {
			if (Validator.isNotNull(matcher.group(1))) {
				columnIds.add(matcher.group(1));
			}
		}

		Set<String> columnNames = new LinkedHashSet<>();

		for (String columnId : columnIds) {
			if (!columnId.contains(portletId)) {
				columnNames.add(
					PortalUtil.getPortletNamespace(portletId) +
						StringPool.UNDERLINE + columnId);
			}
		}

		return new ArrayList<>(columnNames);
	}

	protected void reorganizeNestedColumns(
			ActionRequest actionRequest, String portletResource,
			String newLayoutTemplateId, String oldLayoutTemplateId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();
		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();
		Theme theme = themeDisplay.getTheme();

		LayoutTemplate newLayoutTemplate =
			LayoutTemplateLocalServiceUtil.getLayoutTemplate(
				newLayoutTemplateId, false, theme.getThemeId());

		List<String> newColumns = getColumnNames(
			newLayoutTemplate.getContent(), portletResource);

		LayoutTemplate oldLayoutTemplate =
			LayoutTemplateLocalServiceUtil.getLayoutTemplate(
				oldLayoutTemplateId, false, theme.getThemeId());

		List<String> oldColumns = getColumnNames(
			oldLayoutTemplate.getContent(), portletResource);

		layoutTypePortlet.reorganizePortlets(newColumns, oldColumns);

		layoutTypePortlet.setStateMax(StringPool.BLANK);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	private static final Pattern _pattern = Pattern.compile(
		"processColumn[(]\"(.*?)\"(?:, *\"(?:.*?)\")?[)]", Pattern.DOTALL);

	private volatile NestedPortletsConfiguration _nestedPortletsConfiguration;

}