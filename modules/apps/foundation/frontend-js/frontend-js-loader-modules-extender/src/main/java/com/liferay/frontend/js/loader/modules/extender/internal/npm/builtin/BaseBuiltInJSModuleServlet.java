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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A base abstract class to implement servlets that return Javascript modules
 * tracked by the {@link com.liferay.frontend.js.loader.modules.extender.internal.npm.NPMRegistry}.
 * @author Adolfo Pérez
 */
public abstract class BaseBuiltInJSModuleServlet extends HttpServlet {

	/**
	 * This is a template method that must be implemented by subclasses to
	 * lookup the requested module.
	 * @param moduleName the name of a module
	 * @return the {@link JSModule} object describing the module
	 */
	protected abstract JSModule getJSModule(String moduleName);

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String pathInfo = request.getPathInfo();

		String identifier = pathInfo.substring(1);

		String moduleName = ModuleNameUtil.toModuleName(identifier);

		JSModule jsModule = getJSModule(moduleName);

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT_UTF8);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		try (InputStream inputStream = jsModule.getInputStream()) {
			StreamUtil.transfer(inputStream, servletOutputStream, false);
		}
		catch (Exception e) {
			response.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				"Unable to read file");
		}
	}

}