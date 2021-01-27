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

package com.liferay.frontend.js.module.launcher;

import java.io.Writer;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public interface JSModuleLauncher {

	public void appendPortletScript(
		HttpServletRequest httpServletRequest, String portletId,
		Collection<JSModuleDependency> jsModuleDependencies,
		String javascriptCode);

	public void appendScript(
		HttpServletRequest httpServletRequest,
		Collection<JSModuleDependency> jsModuleDependencies,
		String javascriptCode);

	/**
	 * Check if a JavaScript module identified by name must be loaded using
	 * {@link JSModuleLauncher} (instead of AMD Loader, for example).
	 *
	 * @review
	 */
	public boolean isValidModule(String moduleName);

	public void writeModuleInvocation(
		Writer writer, String javascriptModule, String... parameters);

	public void writeScript(
		Writer writer, Collection<JSModuleDependency> jsModuleDependencies,
		String javascriptCode);

}