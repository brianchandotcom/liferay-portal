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

package com.liferay.nested.portlets.web.portlet;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.nested.portlets.web.configuration.NestedPortletsConfiguration;
import com.liferay.nested.portlets.web.upgrade.NestedPortletWebUpgrade;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTemplateConstants;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.nested.portlets.web.configuration.NestedPortletsConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-nested-portlets",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=1",
		"com.liferay.portlet.single-page-application=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Nested Portlets",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = Portlet.class
)
public class NestedPortletsPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String layoutTemplateId = portletPreferences.getValue(
			"layoutTemplateId",
			_nestedPortletsConfiguration.layoutTemplateDefault());

		String templateId = StringPool.BLANK;
		String templateContent = StringPool.BLANK;

		Map<String, String> columnIds = new HashMap<>();

		if (Validator.isNotNull(layoutTemplateId)) {
			Theme theme = themeDisplay.getTheme();

			LayoutTemplate layoutTemplate =
				LayoutTemplateLocalServiceUtil.getLayoutTemplate(
					layoutTemplateId, false, theme.getThemeId());

			String content = layoutTemplate.getContent();

			Matcher processColumnMatcher = _processColumnPattern.matcher(
				content);

			while (processColumnMatcher.find()) {
				String columnId = processColumnMatcher.group(2);

				if (Validator.isNotNull(columnId)) {
					columnIds.put(
						columnId,
						renderResponse.getNamespace() + StringPool.UNDERLINE +
							columnId);
				}
			}

			processColumnMatcher.reset();

			StringBundler sb = new StringBundler(4);

			sb.append(theme.getThemeId());
			sb.append(LayoutTemplateConstants.CUSTOM_SEPARATOR);
			sb.append(renderResponse.getNamespace());
			sb.append(layoutTemplateId);

			templateId = sb.toString();

			content = processColumnMatcher.replaceAll("$1\\${$2}$3");

			Matcher columnIdMatcher = _columnIdPattern.matcher(content);

			templateContent = columnIdMatcher.replaceAll(
				"$1" + renderResponse.getNamespace() + "$2$3");
		}

		checkLayout(themeDisplay.getLayout(), columnIds.values());

		renderRequest.setAttribute(
			NestedPortletsConfiguration.TEMPLATE_ID, templateId);
		renderRequest.setAttribute(
			NestedPortletsConfiguration.TEMPLATE_CONTENT, templateContent);

		Map<String, Object> vmVariables =
			(Map<String, Object>)renderRequest.getAttribute(
				WebKeys.VM_VARIABLES);

		if (vmVariables != null) {
			vmVariables.putAll(columnIds);
		}
		else {
			renderRequest.setAttribute(WebKeys.VM_VARIABLES, columnIds);
		}

		renderRequest.setAttribute(
			NestedPortletsConfiguration.class.getName(),
			_nestedPortletsConfiguration);

		super.include(viewTemplate, renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_nestedPortletsConfiguration = Configurable.createConfigurable(
			NestedPortletsConfiguration.class, properties);
	}

	protected void checkLayout(Layout layout, Collection<String> columnIds) {
		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String[] layoutColumnIds = StringUtil.split(
			typeSettingsProperties.get(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS));

		boolean updateColumnIds = false;

		for (String columnId : columnIds) {
			String portletIds = typeSettingsProperties.getProperty(columnId);

			if (Validator.isNotNull(portletIds) &&
				!ArrayUtil.contains(layoutColumnIds, columnId)) {

				layoutColumnIds = ArrayUtil.append(layoutColumnIds, columnId);

				updateColumnIds = true;
			}
		}

		if (updateColumnIds) {
			typeSettingsProperties.setProperty(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS,
				StringUtil.merge(layoutColumnIds));

			layout.setTypeSettingsProperties(typeSettingsProperties);

			try {
				LayoutLocalServiceUtil.updateLayout(
					layout.getGroupId(), layout.getLayoutId(),
					layout.getTypeSettings());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	@Reference(unbind = "-")
	protected void setNestedPortletWebUpgrade(
		NestedPortletWebUpgrade nestedPortletWebUpgrade) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NestedPortletsPortlet.class);

	private static final Pattern _columnIdPattern = Pattern.compile(
		"([<].*?id=[\"'])([^ ]*?)([\"'].*?[>])", Pattern.DOTALL);
	private static final Pattern _processColumnPattern = Pattern.compile(
		"(processColumn[(]\")(.*?)(\"(?:, *\"(?:.*?)\")?[)])", Pattern.DOTALL);

	private volatile NestedPortletsConfiguration _nestedPortletsConfiguration;

}