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

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;

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
@MessagingProxy(mode = ProxyMode.SYNC)
public interface Scripting {

	public void addScriptionExecutor(
		String language, ScriptingExecutor scriptingExecutor);

	public void clearCache(String language) throws ScriptingException;

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script,
			ClassLoader... classLoaders)
		throws ScriptingException;

	public void exec(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			String language, String script, ClassLoader... classLoaders)
		throws ScriptingException;

	public Map<String, Object> getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse);

	public Set<String> getSupportedLanguages();

	public void setScriptingExecutors(
		Map<String, ScriptingExecutor> scriptingExecutors);

}