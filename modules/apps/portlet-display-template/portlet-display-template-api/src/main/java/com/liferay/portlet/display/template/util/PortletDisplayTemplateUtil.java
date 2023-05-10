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

package com.liferay.portlet.display.template.util;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lily Chi
 */
public class PortletDisplayTemplateUtil {

	public static long getDDMTemplateGroupId(long groupId) {
		PortletDisplayTemplate portletDisplayTemplate =
			_portletDisplayTemplateSnapshot.get();

		return portletDisplayTemplate.getDDMTemplateGroupId(groupId);
	}

	public static String getDisplayStyle(String ddmTemplateKey) {
		PortletDisplayTemplate portletDisplayTemplate =
			_portletDisplayTemplateSnapshot.get();

		return portletDisplayTemplate.getDisplayStyle(ddmTemplateKey);
	}

	public static PortletDisplayTemplate getPortletDisplayTemplate() {
		return _portletDisplayTemplateSnapshot.get();
	}

	public static DDMTemplate getPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId, String displayStyle,
		boolean useDefault) {

		PortletDisplayTemplate portletDisplayTemplate =
			_portletDisplayTemplateSnapshot.get();

		return portletDisplayTemplate.getPortletDisplayTemplateDDMTemplate(
			groupId, classNameId, displayStyle, useDefault);
	}

	public static List<TemplateHandler> getPortletDisplayTemplateHandlers() {
		PortletDisplayTemplate portletDisplayTemplate =
			_portletDisplayTemplateSnapshot.get();

		return portletDisplayTemplate.getPortletDisplayTemplateHandlers();
	}

	public static String renderDDMTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long ddmTemplateId,
			List<?> entries, Map<String, Object> contextObjects)
		throws Exception {

		PortletDisplayTemplate portletDisplayTemplate =
			_portletDisplayTemplateSnapshot.get();

		return portletDisplayTemplate.renderDDMTemplate(
			httpServletRequest, httpServletResponse, ddmTemplateId, entries,
			contextObjects);
	}

	private static final Snapshot<PortletDisplayTemplate>
		_portletDisplayTemplateSnapshot = new Snapshot<>(
			PortletDisplayTemplateUtil.class, PortletDisplayTemplate.class);

}