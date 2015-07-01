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

import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

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

		PortletDisplayTemplateManager manager =
			_getPortletDisplayTemplateManager();

		return manager.getDDMTemplate(
			groupId, classNameId, displayStyle, useDefault);
	}

	public static long getDDMTemplateGroupId(long groupId) {
		PortletDisplayTemplateManager manager =
			_getPortletDisplayTemplateManager();

		return manager.getDDMTemplateGroupId(groupId);
	}

	public static List<TemplateHandler> getPortletDisplayTemplateHandlers() {
		PortletDisplayTemplateManager manager =
			_getPortletDisplayTemplateManager();

		return manager.getPortletDisplayTemplateHandlers();
	}

	public static Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language) {

		PortletDisplayTemplateManager manager =
			_getPortletDisplayTemplateManager();

		return manager.getTemplateVariableGroups(language);
	}

	public static String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			long templateId, List<?> entries, 
			Map<String, Object> contextObjects)
		throws Exception {

		PortletDisplayTemplateManager manager =
			_getPortletDisplayTemplateManager();

		return manager.renderDDMTemplate(
			request, response, templateId, entries, contextObjects);
	}

	private static PortletDisplayTemplateManager
		_getPortletDisplayTemplateManager() {

		return _instance._serviceTracker.getService();
	}

	private PortletDisplayTemplateManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PortletDisplayTemplateManager.class);

		_serviceTracker.open();
	}

	private static final PortletDisplayTemplateManagerUtil _instance =
		new PortletDisplayTemplateManagerUtil();

	private final
		ServiceTracker<PortletDisplayTemplateManager,
			PortletDisplayTemplateManager> _serviceTracker;

}