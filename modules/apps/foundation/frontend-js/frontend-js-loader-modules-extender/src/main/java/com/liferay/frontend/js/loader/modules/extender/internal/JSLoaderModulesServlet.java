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

import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.utils.log.Logger;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

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
@Designate(ocd = Details.class)
public class JSLoaderModulesServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		_componentContext.enableComponent(
			JSLoaderModulesPortalWebResources.class.getName());
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext, Details details)
		throws Exception {

		_details = details;

		_logger = new Logger(componentContext.getBundleContext());

		_componentContext = componentContext;
	}

	protected JSLoaderModulesTracker getJSLoaderModulesTracker() {
		return _jsLoaderModulesTracker;
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		response.setContentType(Details.CONTENT_TYPE);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		PrintWriter printWriter = new PrintWriter(servletOutputStream, true);

		printWriter.println("(function() {");
		printWriter.println("Liferay.PATHS = {");

		String delimiter = "";
		Set<String> processedNames = new HashSet<>();

		Collection<JSLoaderModule> jsLoaderModules =
			_jsLoaderModulesTracker.getJSLoaderModules();

		for (JSLoaderModule jsLoaderModule : jsLoaderModules) {
			printWriter.write(delimiter);
			printWriter.write("'");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write('@');
			printWriter.write(jsLoaderModule.getVersion());
			printWriter.write("': '");
			printWriter.write(PortalUtil.getPathProxy());
			printWriter.write(jsLoaderModule.getContextPath());
			printWriter.write("'");

			if (!processedNames.contains(jsLoaderModule.getName())) {
				processedNames.add(jsLoaderModule.getName());

				printWriter.println(",");
				printWriter.write("'");
				printWriter.write(jsLoaderModule.getName());
				printWriter.write("': '");
				printWriter.write(PortalUtil.getPathProxy());
				printWriter.write(jsLoaderModule.getContextPath());
				printWriter.write("'");
			}

			delimiter = ",\n";
		}

		printWriter.println("\n};");
		printWriter.println("Liferay.MODULES = {");

		delimiter = "";
		processedNames.clear();

		for (JSLoaderModule jsLoaderModule : jsLoaderModules) {
			String unversionedConfiguration =
				jsLoaderModule.getUnversionedConfiguration();

			if (unversionedConfiguration.length() == 0) {
				continue;
			}

			if (!processedNames.contains(jsLoaderModule.getName())) {
				processedNames.add(jsLoaderModule.getName());

				printWriter.write(delimiter);
				printWriter.write(unversionedConfiguration);

				delimiter = ",\n";
			}

			String versionedConfiguration =
				jsLoaderModule.getVersionedConfiguration();

			if (versionedConfiguration.length() > 0) {
				printWriter.write(delimiter);
				printWriter.write(versionedConfiguration);

				delimiter = ",\n";
			}
		}

		printWriter.println("\n};");
		printWriter.println("Liferay.MAPS = {");

		delimiter = "";
		processedNames.clear();

		for (JSLoaderModule jsLoaderModule : jsLoaderModules) {
			if (processedNames.contains(jsLoaderModule.getName())) {
				continue;
			}

			processedNames.add(jsLoaderModule.getName());

			printWriter.write(delimiter);
			printWriter.write("'");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write("': '");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write('@');
			printWriter.write(jsLoaderModule.getVersion());
			printWriter.write("'");

			delimiter = ",\n";

			String unversionedMapsConfiguration =
				jsLoaderModule.getUnversionedMapsConfiguration();

			if (!unversionedMapsConfiguration.equals("")) {
				printWriter.write(delimiter);
				printWriter.write(unversionedMapsConfiguration);
			}
		}

		printWriter.println("\n};");

		printWriter.println(
			"Liferay.EXPOSE_GLOBAL = " + _details.exposeGlobal() + ";\n");

		printWriter.println("}());");

		printWriter.close();
	}

	protected void setDetails(Details details) {
		_details = details;
	}

	@Reference(unbind = "-")
	protected void setJSLoaderModulesTracker(
		JSLoaderModulesTracker jsLoaderModulesTracker) {

		_jsLoaderModulesTracker = jsLoaderModulesTracker;
	}

	private ComponentContext _componentContext;
	private volatile Details _details;
	private JSLoaderModulesTracker _jsLoaderModulesTracker;
	private Logger _logger;

}