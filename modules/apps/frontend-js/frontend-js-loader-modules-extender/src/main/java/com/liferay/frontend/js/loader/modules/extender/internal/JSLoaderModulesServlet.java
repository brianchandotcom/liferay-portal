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

package com.liferay.frontend.js.loader.modules.extender.internal;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.js.loader.modules.extender.internal.JSLoaderModulesServlet",
		"osgi.http.whiteboard.servlet.pattern=/js_loader_modules",
		"service.ranking:Integer=" + Details.MAX_VALUE_LESS_1K
	},
	service = {JSLoaderModulesServlet.class, Servlet.class}
)
public class JSLoaderModulesServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		_componentContext.enableComponent(
			JSLoaderModulesPortalWebResources.class.getName());
	}

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_details = ConfigurableUtil.createConfigurable(
			Details.class, properties);

		_componentContext = componentContext;
	}

	protected JSLoaderModulesTracker getJSLoaderModulesTracker() {
		return _jsLoaderModulesTracker;
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		StringWriter stringWriter = new StringWriter();

		PrintWriter printWriter = new PrintWriter(stringWriter);

		printWriter.println("(function() {");

		_writePaths(printWriter);

		printWriter.println(
			"Liferay.EXPLAIN_RESOLUTIONS = " + _details.explainResolutions() +
				";\n");

		printWriter.println(
			"Liferay.EXPOSE_GLOBAL = " + _details.exposeGlobal() + ";\n");

		printWriter.println(
			"Liferay.WAIT_TIMEOUT = " + (_details.waitTimeout() * 1000) +
				";\n");

		printWriter.println("}());");

		printWriter.close();

		_writeResponse(response, stringWriter.toString());
	}

	protected void setDetails(Details details) {
		_details = details;
	}

	@Reference(unbind = "-")
	protected void setJSLoaderModulesTracker(
		JSLoaderModulesTracker jsLoaderModulesTracker) {

		_jsLoaderModulesTracker = jsLoaderModulesTracker;
	}

	@Reference(unbind = "-")
	protected void setNPMRegistry(NPMRegistry npmRegistry) {
		_npmRegistry = npmRegistry;
	}

	private void _writePaths(PrintWriter printWriter) {
		printWriter.write("var O=\"");
		printWriter.write(_portal.getPathModule());
		printWriter.write("/js/resolved-module/");
		printWriter.write("\";\n");

		printWriter.println("Liferay.PATHS = {");

		String delimiter = "";
		Set<String> processedNames = new HashSet<>();

		for (JSLoaderModule jsLoaderModule :
				_jsLoaderModulesTracker.getJSLoaderModules()) {

			printWriter.write(delimiter);
			printWriter.write("\"");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write("@");
			printWriter.write(jsLoaderModule.getVersion());
			printWriter.write("\": \"");
			printWriter.write(_portal.getPathProxy());
			printWriter.write(jsLoaderModule.getContextPath());
			printWriter.write("\"");

			if (!processedNames.contains(jsLoaderModule.getName())) {
				processedNames.add(jsLoaderModule.getName());

				printWriter.println(",");
				printWriter.write("\"");
				printWriter.write(jsLoaderModule.getName());
				printWriter.write("\": \"");
				printWriter.write(_portal.getPathProxy());
				printWriter.write(jsLoaderModule.getContextPath());
				printWriter.write("\"");
			}

			delimiter = ",\n";
		}

		for (JSPackage resolvedJSPackage :
				_npmRegistry.getResolvedJSPackages()) {

			printWriter.write(delimiter);
			printWriter.write("\"");
			printWriter.write(resolvedJSPackage.getResolvedId());
			printWriter.write("\":O+\"");
			printWriter.write(resolvedJSPackage.getResolvedId());
			printWriter.write("\"");

			delimiter = ",\n";
		}

		printWriter.println("\n};");
	}

	private void _writeResponse(HttpServletResponse response, String content)
		throws IOException {

		response.setContentType(Details.CONTENT_TYPE);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		PrintWriter printWriter = new PrintWriter(servletOutputStream, true);

		printWriter.write(_minifier.minify("/o/js_loader_modules", content));

		printWriter.close();
	}

	private ComponentContext _componentContext;
	private volatile Details _details;
	private JSLoaderModulesTracker _jsLoaderModulesTracker;

	@Reference
	private Minifier _minifier;

	private NPMRegistry _npmRegistry;

	@Reference
	private Portal _portal;

}