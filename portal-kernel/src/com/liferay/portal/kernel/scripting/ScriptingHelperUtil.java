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

package com.liferay.portal.kernel.scripting;

import java.util.HashMap;
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

		Map<String, Object> portletObjects = new HashMap<>();

		portletObjects.put("portletConfig", portletConfig);
		portletObjects.put("portletContext", portletContext);
		portletObjects.put("preferences", portletRequest.getPreferences());

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