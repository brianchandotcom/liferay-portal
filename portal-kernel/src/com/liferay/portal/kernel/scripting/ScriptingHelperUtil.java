/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scripting;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Shuyang Zhou
 */
public class ScriptingHelperUtil {

	public static Map<String, Object> getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Map<String, Object> portletObjects = HashMapBuilder.<String, Object>put(
			"portletConfig", portletConfig
		).put(
			"portletContext", portletContext
		).put(
			"preferences", portletRequest.getPreferences()
		).build();

		if (portletRequest instanceof ActionRequest) {
			portletObjects.put("actionRequest", portletRequest);
		}
		else if (portletRequest instanceof RenderRequest) {
			portletObjects.put("renderRequest", portletRequest);
		}
		else if (portletRequest instanceof ResourceRequest) {
			portletObjects.put("resourceRequest", portletRequest);
		}
		else {
			portletObjects.put("portletRequest", portletRequest);
		}

		if (portletResponse instanceof ActionResponse) {
			portletObjects.put("actionResponse", portletResponse);
		}
		else if (portletResponse instanceof RenderResponse) {
			portletObjects.put("renderResponse", portletResponse);
		}
		else if (portletResponse instanceof ResourceResponse) {
			portletObjects.put("resourceResponse", portletResponse);
		}
		else {
			portletObjects.put("portletResponse", portletResponse);
		}

		portletObjects.put(
			"userInfo", portletRequest.getAttribute(PortletRequest.USER_INFO));

		return portletObjects;
	}

}