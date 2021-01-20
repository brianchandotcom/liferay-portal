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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public interface JSModuleLauncher {

	public void appendPortletScript(
		HttpServletRequest httpServletRequest, String portletId,
		String javascriptModule, String javascriptVariable,
		String javascriptCode);

	public void appendScript(
		HttpServletRequest httpServletRequest, String javascriptModule,
		String javascriptVariable, String javascriptCode);

	public void writePortletScript(
		Writer writer, String portletId, String javascriptModule,
		String javascriptVariable, String javascriptCode);

	public void writeScript(
		Writer writer, String javascriptModule, String javascriptVariable,
		String javascriptCode);

}