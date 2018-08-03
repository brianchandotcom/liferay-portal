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

package com.liferay.frontend.js.bundle.config.extender.internal;

import com.liferay.frontend.js.bundle.config.extender.internal.JSBundleConfigTracker.JSConfig;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.minifier.MinifierUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;

import java.util.Collection;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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

/**
 * @author Carlos Sierra Andrés
 * @author Chema Balsas
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.js.bundle.config.extender.internal.JSBundleConfigServlet",
		"osgi.http.whiteboard.servlet.pattern=/js_bundle_config",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = {JSBundleConfigServlet.class, Servlet.class}
)
public class JSBundleConfigServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		_componentContext.enableComponent(
			JSBundleConfigPortalWebResources.class.getName());
	}

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_logger = new Logger(componentContext.getBundleContext());

		_componentContext = componentContext;
	}

	protected JSBundleConfigTracker getJSBundleConfigTracker() {
		return _jsBundleConfigTracker;
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		StringWriter stringWriter = new StringWriter();

		PrintWriter printWriter = new PrintWriter(stringWriter);

		Collection<JSConfig> jsConfigs = _jsBundleConfigTracker.getJSConfigs();

		if (!jsConfigs.isEmpty()) {
			printWriter.println("(function() {");

			for (JSConfig jsConfig : jsConfigs) {
				URL url = jsConfig.getURL();

				try (InputStream inputStream = url.openStream()) {
					printWriter.println("try {");

					ServletContext servletContext =
						jsConfig.getServletContext();

					printWriter.println(
						StringBundler.concat(
							"var MODULE_PATH = '", _portal.getPathProxy(),
							servletContext.getContextPath(), "';"));

					printWriter.print(StringUtil.read(inputStream));

					printWriter.println("} catch (error) {");
					printWriter.println("console.error(error);");
					printWriter.println("}");
				}
				catch (Exception e) {
					_logger.log(Logger.LOG_ERROR, "Unable to open resource", e);
				}
			}

			printWriter.println("}());");
		}

		printWriter.close();

		_writeResponse(response, stringWriter.toString());
	}

	@Reference(unbind = "-")
	protected void setJSBundleConfigTracker(
		JSBundleConfigTracker jsBundleConfigTracker) {

		_jsBundleConfigTracker = jsBundleConfigTracker;
	}

	private void _writeResponse(HttpServletResponse response, String content)
		throws IOException {

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT_UTF8);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		PrintWriter printWriter = new PrintWriter(servletOutputStream, true);

		printWriter.write(
			MinifierUtil.minifyJavaScript("/o/js_bundle_config", content));

		printWriter.close();
	}

	private ComponentContext _componentContext;
	private JSBundleConfigTracker _jsBundleConfigTracker;
	private Logger _logger;

	@Reference
	private Portal _portal;

}