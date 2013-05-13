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

package com.liferay.portlet.portletdisplaytemplate.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

/**
 * @author Eduardo Garcia
 */
public class PortletDisplayTemplateUtil {

	public static DDMTemplate fetchDDMTemplate(
		long groupId, String displayStyle) {

		return getPortletDisplayTemplate().fetchDDMTemplate(
			groupId, displayStyle);
	}

	public static long getDDMTemplateGroupId(ThemeDisplay themeDisplay) {
		return getPortletDisplayTemplate().getDDMTemplateGroupId(themeDisplay);
	}

	public static PortletDisplayTemplate getPortletDisplayTemplate() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletDisplayTemplate.class);

		return _portletDisplayTemplate;
	}

	public static long getPortletDisplayTemplateDDMTemplateId(
		ThemeDisplay themeDisplay, String displayStyle) {

		return
			getPortletDisplayTemplate().getPortletDisplayTemplateDDMTemplateId(
				themeDisplay, displayStyle);
	}

	public static Map<String, TemplateVariableGroup>
		getTemplateVariableGroups(String language) {

		return getPortletDisplayTemplate().getTemplateVariableGroups(language);
	}

	public static String renderDDMTemplate(
			PageContext pageContext, long ddmTemplateId, List<?> entries)
		throws Exception {

		return getPortletDisplayTemplate().renderDDMTemplate(
			pageContext, ddmTemplateId, entries);
	}

	public static String renderDDMTemplate(
			PageContext pageContext, long ddmTemplateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception {

		return getPortletDisplayTemplate().renderDDMTemplate(
			pageContext, ddmTemplateId, entries, contextObjects);
	}

	public void setPortletDisplayTemplate(
		PortletDisplayTemplate portletDisplayTemplate) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletDisplayTemplate = portletDisplayTemplate;
	}

	private static PortletDisplayTemplate _portletDisplayTemplate;

}