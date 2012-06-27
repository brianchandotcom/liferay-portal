/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
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
			classLoaders);
	}

	public static void exec(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			String language, String script, ClassLoader... classLoaders)
		throws ScriptingException {

		getScripting().exec(
			allowedClasses, inputObjects, language, script, classLoaders);
	}

	public static Map<String, Object> getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return getScripting().getPortletObjects(
			portletConfig, portletContext, portletRequest, portletResponse);
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

	private static Scripting _scripting;

}