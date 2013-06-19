/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.nestedportlets.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Berentey Zsolt
 * @author Jorge Ferrer
 * @author Raymond Augé
 * @author Jesper Weissglas
 */
public class ViewAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Portlet portlet = (Portlet)renderRequest.getAttribute(
			WebKeys.RENDER_PORTLET);

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				renderRequest, portlet.getPortletId());

		String layoutTemplateId = portletPreferences.getValue(
			"layoutTemplateId",
			PropsValues.NESTED_PORTLETS_LAYOUT_TEMPLATE_DEFAULT);

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

			velocityTemplateId = sb.toString();

			content = processColumnMatcher.replaceAll("$1\\${$2}$3");

			Matcher columnIdMatcher = _columnIdPattern.matcher(content);

			velocityTemplateContent = columnIdMatcher.replaceAll(
				"$1" + renderResponse.getNamespace() + "$2$3");
		}

		checkLayout(themeDisplay.getLayout(), columnIds.values());

		renderRequest.setAttribute(
			WebKeys.NESTED_PORTLET_VELOCITY_TEMPLATE_ID, velocityTemplateId);
		renderRequest.setAttribute(
			WebKeys.NESTED_PORTLET_VELOCITY_TEMPLATE_CONTENT,
			velocityTemplateContent);

		Map<String, Object> vmVariables =
			(Map<String, Object>)renderRequest.getAttribute(
				WebKeys.VM_VARIABLES);

		if (vmVariables != null) {
			vmVariables.putAll(columnIds);
		}
		else {
			renderRequest.setAttribute(WebKeys.VM_VARIABLES, columnIds);
		}

		return actionMapping.findForward("portlet.nested_portlets.view");
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