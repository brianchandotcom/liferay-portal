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

package com.liferay.portlet.display.template;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Eduardo Garcia
 */
public class PortletDisplayTemplateUtil {

	public static DDMTemplate fetchDDMTemplate(
		long groupId, String displayStyle) {

		return getPortletDisplayTemplate().fetchDDMTemplate(
			groupId, displayStyle);
	}

	public static long getDDMTemplateGroupId(long groupId) {
		return getPortletDisplayTemplate().getDDMTemplateGroupId(groupId);
	}

	public static String getDDMTemplateKey(String displayStyle) {
		return getPortletDisplayTemplate().getDDMTemplateKey(displayStyle);
	}

	@Deprecated
	@SuppressWarnings("deprecation")
	public static String getDDMTemplateUuid(String displayStyle) {
		return getPortletDisplayTemplate().getDDMTemplateUuid(displayStyle);
	}

	public static DDMTemplate getDefaultPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId) {

		return getPortletDisplayTemplate().
			getDefaultPortletDisplayTemplateDDMTemplate(groupId, classNameId);
	}

	public static String getDisplayStyle(String ddmTemplateKey) {
		return getPortletDisplayTemplate().getDisplayStyle(ddmTemplateKey);
	}

	public static PortletDisplayTemplate getPortletDisplayTemplate() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletDisplayTemplate.class);

		return _serviceTracker.getService();
	}

	public static DDMTemplate getPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId, String displayStyle) {

		return getPortletDisplayTemplate().getPortletDisplayTemplateDDMTemplate(
			groupId, classNameId, displayStyle);
	}

	public static DDMTemplate getPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId, String displayStyle,
		boolean useDefault) {

		return getPortletDisplayTemplate().getPortletDisplayTemplateDDMTemplate(
			groupId, classNameId, displayStyle, useDefault);
	}

	@Deprecated
	@SuppressWarnings("deprecation")
	public static long getPortletDisplayTemplateDDMTemplateId(
		long groupId, String displayStyle) {

		return getPortletDisplayTemplate().
			getPortletDisplayTemplateDDMTemplateId(groupId, displayStyle);
	}

	public static List<TemplateHandler> getPortletDisplayTemplateHandlers() {
		return getPortletDisplayTemplate().getPortletDisplayTemplateHandlers();
	}

	public static Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language) {

		return getPortletDisplayTemplate().getTemplateVariableGroups(language);
	}

	public static String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			DDMTemplate ddmTemplate, List<?> entries)
		throws Exception {

		return getPortletDisplayTemplate().renderDDMTemplate(
			request, response, ddmTemplate, entries);
	}

	public static String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			DDMTemplate ddmTemplate, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception {

		return getPortletDisplayTemplate().renderDDMTemplate(
			request, response, ddmTemplate, entries, contextObjects);
	}

	public static String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			long ddmTemplateId, List<?> entries)
		throws Exception {

		return getPortletDisplayTemplate().renderDDMTemplate(
			request, response, ddmTemplateId, entries);
	}

	public static String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			long ddmTemplateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception {

		return getPortletDisplayTemplate().renderDDMTemplate(
			request, response, ddmTemplateId, entries, contextObjects);
	}

	private static final ServiceTracker
		<PortletDisplayTemplate, PortletDisplayTemplate> _serviceTracker =
			ServiceTrackerFactory.open(
				FrameworkUtil.getBundle(PortletDisplayTemplateUtil.class),
				PortletDisplayTemplate.class);

}