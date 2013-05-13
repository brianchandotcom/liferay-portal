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

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;

import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Michael C. Han
 */
public class ScriptingProxyBean extends BaseProxyBean implements Scripting {

	@Override
	public void addScriptingExecutor(
		String language, ScriptingExecutor scriptingExecutor) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void clearCache(String language) {
		throw new UnsupportedOperationException();
	}

	public Map<String, Object> eval(
		Set<String> allowedClasses, Map<String, Object> inputObjects,
		Set<String> outputNames, String language, String script,
		ClassLoader... classLoaders) {

		throw new UnsupportedOperationException();
	}

	public void exec(
		Set<String> allowedClasses, Map<String, Object> inputObjects,
		String language, String script, ClassLoader... classLoaders) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> getSupportedLanguages() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setScriptingExecutors(
		Map<String, ScriptingExecutor> scriptingExecutors) {

		throw new UnsupportedOperationException();
	}

}