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
import com.liferay.portlet.dynamicdatamapping.DDMTemplate;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
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

	public static void exportDDMTemplateStagedModel(
			PortletDataContext portletDataContext, String portletId,
			DDMTemplate ddmTemplate)
		throws PortletDataException {

		PortletDisplayTemplateManager portletDisplayTemplateManager =
			_instance._serviceTracker.getService();

		portletDisplayTemplateManager.exportDDMTemplateStagedModel(
			portletDataContext, portletId, ddmTemplate);
	}

	public static DDMTemplate getDDMTemplate(
		long groupId, long classNameId, String displayStyle,
		boolean useDefault) {

		PortletDisplayTemplateManager portletDisplayTemplateManager =
			_getPortletDisplayTemplateManager();

		return portletDisplayTemplateManager.getDDMTemplate(
			groupId, classNameId, displayStyle, useDefault);
	}

	public static long getDDMTemplateGroupId(long groupId) {
		PortletDisplayTemplateManager portletDisplayTemplateManager =
			_getPortletDisplayTemplateManager();

		return portletDisplayTemplateManager.getDDMTemplateGroupId(groupId);
	}

	public static Class<?> getDDMTemplateStagedModelClass() {
		PortletDisplayTemplateManager portletDisplayTemplateManager =
			_getPortletDisplayTemplateManager();

		return portletDisplayTemplateManager.getDDMTemplateStagedModelClass();
	}

	public static String getDisplayStyle(String ddmTemplateKey) {
		PortletDisplayTemplateManager portletDisplayTemplateManager =
			_getPortletDisplayTemplateManager();

		return portletDisplayTemplateManager.getDisplayStyle(ddmTemplateKey);
	}

	public static List<TemplateHandler> getPortletDisplayTemplateHandlers() {
		PortletDisplayTemplateManager portletDisplayTemplateManager =
			_getPortletDisplayTemplateManager();

		return portletDisplayTemplateManager.
			getPortletDisplayTemplateHandlers();
	}

	public static Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language) {

		PortletDisplayTemplateManager portletDisplayTemplateManager =
			_getPortletDisplayTemplateManager();

		return portletDisplayTemplateManager.getTemplateVariableGroups(
			language);
	}

	public static String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			long templateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception {

		PortletDisplayTemplateManager portletDisplayTemplateManager =
			_getPortletDisplayTemplateManager();

		return portletDisplayTemplateManager.renderDDMTemplate(
			request, response, templateId, entries, contextObjects);
	}

	private static PortletDisplayTemplateManager
		_getPortletDisplayTemplateManager() {

		PortletDisplayTemplateManager portletDisplayTemplateManager =
			_instance._serviceTracker.getService();

		if (portletDisplayTemplateManager == null) {
			return _dummyPortletDisplayTemplateManagerImpl;
		}

		return portletDisplayTemplateManager;
	}

	private PortletDisplayTemplateManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PortletDisplayTemplateManager.class);

		_serviceTracker.open();
	}

	private static final PortletDisplayTemplateManagerUtil _instance =
		new PortletDisplayTemplateManagerUtil();

	private static final DummyPortletDisplayTemplateManagerImpl
		_dummyPortletDisplayTemplateManagerImpl =
			new DummyPortletDisplayTemplateManagerImpl();

	private final ServiceTracker
		<PortletDisplayTemplateManager, PortletDisplayTemplateManager>
			_serviceTracker;

}