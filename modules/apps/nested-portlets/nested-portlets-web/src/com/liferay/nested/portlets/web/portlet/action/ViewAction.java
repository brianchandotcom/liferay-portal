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

import com.liferay.nested.portlets.web.portlet.util.NestedPortletUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
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

import org.osgi.service.component.annotations.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Berentey Zsolt
 * @author Jorge Ferrer
 * @author Raymond Augé
 * @author Jesper Weissglas
 * @author Peter Fellwock
 */
@Component(
		immediate = true,
		property = {
			"action.command.name=view",
			"javax.portlet.name=com_liferay_nested_portlets_web_portlet_NestedPortletsPortlet"
		},
		service = ActionCommand.class
	)
public class ViewAction implements ActionCommand {

	@Override
	public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences portletPreferences = portletRequest.getPreferences();

		String layoutTemplateId = 
			NestedPortletUtil.getLayoutTemplateId(portletPreferences);

		String velocityTemplateId = StringPool.BLANK;
		String velocityTemplateContent = StringPool.BLANK;

		Map<String, String> columnIds = new HashMap<String, String>();

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
						portletResponse.getNamespace() + StringPool.UNDERLINE +
							columnId);
				}
			}

			processColumnMatcher.reset();

			StringBundler sb = new StringBundler(4);

			sb.append(theme.getThemeId());
			sb.append(LayoutTemplateConstants.CUSTOM_SEPARATOR);
			sb.append(portletResponse.getNamespace());
			sb.append(layoutTemplateId);

			velocityTemplateId = sb.toString();

			content = processColumnMatcher.replaceAll("$1\\${$2}$3");

			Matcher columnIdMatcher = _columnIdPattern.matcher(content);

			velocityTemplateContent = columnIdMatcher.replaceAll(
				"$1" + portletResponse.getNamespace() + "$2$3");
		}

		checkLayout(themeDisplay.getLayout(), columnIds.values());

		portletRequest.setAttribute(
			WebKeys.NESTED_PORTLET_VELOCITY_TEMPLATE_ID, velocityTemplateId);
		portletRequest.setAttribute(
			WebKeys.NESTED_PORTLET_VELOCITY_TEMPLATE_CONTENT,
			velocityTemplateContent);

		@SuppressWarnings("unchecked")
		Map<String, Object> vmVariables =
			(Map<String, Object>)portletRequest.getAttribute(
				WebKeys.VM_VARIABLES);

		if (vmVariables != null) {
			vmVariables.putAll(columnIds);
		}
		else {
			portletRequest.setAttribute(WebKeys.VM_VARIABLES, columnIds);
		}

		 //actionMapping.findForward("portlet.nested_portlets.view");

		ActionResponse actionResponse = (ActionResponse)portletResponse;

		actionResponse.setRenderParameter("mvcPath", "/view.jsp");

		return true;
	}

	protected void checkLayout(Layout layout, Collection<String> columnIds) {
		UnicodeProperties properties = layout.getTypeSettingsProperties();

		String[] layoutColumnIds = StringUtil.split(
			properties.get(LayoutTypePortletConstants.NESTED_COLUMN_IDS));

		boolean updateColumnIds = false;

		for (String columnId : columnIds) {
			String portletIds = properties.getProperty(columnId);

			if (Validator.isNotNull(portletIds) &&
				!ArrayUtil.contains(layoutColumnIds, columnId)) {

				layoutColumnIds = ArrayUtil.append(layoutColumnIds, columnId);

				updateColumnIds = true;
			}
		}

		if (updateColumnIds) {
			properties.setProperty(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS,
				StringUtil.merge(layoutColumnIds));

			layout.setTypeSettingsProperties(properties);

			try {
				LayoutLocalServiceUtil.updateLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(), layout.getTypeSettings());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ViewAction.class);

	private static Pattern _columnIdPattern = Pattern.compile(
		"([<].*?id=[\"'])([^ ]*?)([\"'].*?[>])", Pattern.DOTALL);
	private static Pattern _processColumnPattern = Pattern.compile(
		"(processColumn[(]\")(.*?)(\"(?:, *\"(?:.*?)\")?[)])", Pattern.DOTALL);

}