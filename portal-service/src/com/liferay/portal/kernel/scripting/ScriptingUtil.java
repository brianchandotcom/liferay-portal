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

package com.liferay.portal.kernel.scripting;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ClassLoaderPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptingUtil {

	public static void clearCache(String language) throws ScriptingException {
		getScripting().clearCache(language);
	}

	public static Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script,
			ClassLoader... classLoaders)
		throws ScriptingException {

		return getScripting().eval(
			allowedClasses, inputObjects, outputNames, language, script,
			toServletContextNames(classLoaders));
	}

	public static void exec(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			String language, String script, ClassLoader... classLoaders)
		throws ScriptingException {

		getScripting().exec(
			allowedClasses, inputObjects, language, script,
			toServletContextNames(classLoaders));
	}

	public static Map<String, Object> getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Map<String, Object> objects = new HashMap<String, Object>();

		objects.put("portletConfig", portletConfig);
		objects.put("portletContext", portletContext);
		objects.put("preferences", portletRequest.getPreferences());

		if (portletRequest instanceof ActionRequest) {
			objects.put("actionRequest", portletRequest);
		}
		else if (portletRequest instanceof RenderRequest) {
			objects.put("renderRequest", portletRequest);
		}
		else if (portletRequest instanceof ResourceRequest) {
			objects.put("resourceRequest", portletRequest);
		}
		else {
			objects.put("portletRequest", portletRequest);
		}

		if (portletResponse instanceof ActionResponse) {
			objects.put("actionResponse", portletResponse);
		}
		else if (portletResponse instanceof RenderResponse) {
			objects.put("renderResponse", portletResponse);
		}
		else if (portletResponse instanceof ResourceResponse) {
			objects.put("resourceResponse", portletResponse);
		}
		else {
			objects.put("portletResponse", portletResponse);
		}

		objects.put(
			"userInfo", portletRequest.getAttribute(PortletRequest.USER_INFO));

		return objects;
	}

	public static Scripting getScripting() {
		PortalRuntimePermission.checkGetBeanProperty(ScriptingUtil.class);

		return _scripting;
	}

	public static Set<String> getSupportedLanguages() {
		return getScripting().getSupportedLanguages();
	}

	public void setScripting(Scripting scripting) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_scripting = scripting;
	}

	private static String[] toServletContextNames(ClassLoader[] classLoaders) {
		String[] servletContextNames = new String[classLoaders.length];

		for (int i = 0; i < classLoaders.length; i++) {
			servletContextNames[i] = ClassLoaderPool.getContextName(
				classLoaders[i]);
		}

		return servletContextNames;
	}

	private static Scripting _scripting;

}