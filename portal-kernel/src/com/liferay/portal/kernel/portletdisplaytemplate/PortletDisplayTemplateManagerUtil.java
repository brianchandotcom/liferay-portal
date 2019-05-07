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

package com.liferay.portal.kernel.portletdisplaytemplate;

import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public class PortletDisplayTemplateManagerUtil {

	public static DDMTemplate getDDMTemplate(
		long groupId, long classNameId, String displayStyle,
		boolean useDefault) {

		return _portletDisplayTemplateManager.getDDMTemplate(
			groupId, classNameId, displayStyle, useDefault);
	}

	public static String getDisplayStyle(String ddmTemplateKey) {
		return _portletDisplayTemplateManager.getDisplayStyle(ddmTemplateKey);
	}

	public static Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language) {

		return _portletDisplayTemplateManager.getTemplateVariableGroups(
			language);
	}

	public static String renderDDMTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, DDMTemplate ddmTemplate,
			List<?> entries, Map<String, Object> contextObjects)
		throws Exception {

		return _portletDisplayTemplateManager.renderDDMTemplate(
			httpServletRequest, httpServletResponse, ddmTemplate, entries,
			contextObjects);
	}

	public static String renderDDMTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long templateId,
			List<?> entries, Map<String, Object> contextObjects)
		throws Exception {

		return _portletDisplayTemplateManager.renderDDMTemplate(
			httpServletRequest, httpServletResponse, templateId, entries,
			contextObjects);
	}

	private static volatile PortletDisplayTemplateManager
		_portletDisplayTemplateManager =
			ServiceProxyFactory.newServiceTrackedInstance(
				PortletDisplayTemplateManager.class,
				PortletDisplayTemplateManagerUtil.class,
				"_portletDisplayTemplateManager", false);

}