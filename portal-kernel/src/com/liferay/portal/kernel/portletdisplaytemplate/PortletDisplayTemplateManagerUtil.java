/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portletdisplaytemplate;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public class PortletDisplayTemplateManagerUtil {

	public static String renderDDMTemplate(
			long classNameId, Map<String, Object> contextObjects,
			String ddmTemplateKey, List<?> entries, long groupId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, boolean useDefault)
		throws Exception {

		return _portletDisplayTemplateManager.renderDDMTemplate(
			classNameId, contextObjects, ddmTemplateKey, entries, groupId,
			httpServletRequest, httpServletResponse, useDefault);
	}

	private static volatile PortletDisplayTemplateManager
		_portletDisplayTemplateManager =
			ServiceProxyFactory.newServiceTrackedInstance(
				PortletDisplayTemplateManager.class,
				PortletDisplayTemplateManagerUtil.class,
				"_portletDisplayTemplateManager", false);

}