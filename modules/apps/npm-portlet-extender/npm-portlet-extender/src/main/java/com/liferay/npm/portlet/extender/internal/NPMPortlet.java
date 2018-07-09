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

package com.liferay.npm.portlet.extender.internal;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ray Augé
 * @author Iván Zaera Avellón
 */
public class NPMPortlet extends MVCPortlet {

	public NPMPortlet(String name, String version) {
		_name = name;
		_version = version;
	}

	@Override
	public void render(RenderRequest request, RenderResponse response) {
		try {
			PrintWriter writer = response.getWriter();

			String portletElementId = "npm-portlet-" + response.getNamespace();

			_writeContainerDiv(writer, portletElementId);

			_writeScript(
				writer, portletElementId, response.getNamespace(),
				request.getContextPath());

			writer.flush();
		}
		catch (IOException ioe) {
			_logger.error("Unable to render HTML output", ioe);
		}
	}

	private void _writeContainerDiv(
		PrintWriter writer, String portletElementId) {

		writer.print("<div");

		writer.print(" ");

		writer.print("id=");
		writer.print("\"");
		writer.print(portletElementId);
		writer.print("\"");

		writer.print("></div>");
	}

	private void _writeParameters(
		PrintWriter writer, String portletElementId, String portletNamespace,
		String contextPath) {

		writer.print("{");

		writer.print("portletNamespace: ");
		writer.print("\"");
		writer.print(portletNamespace);
		writer.print("\"");

		writer.print(",");

		writer.print("contextPath: ");
		writer.print("\"");
		writer.print(contextPath);
		writer.print("\"");

		writer.print(",");

		writer.print("portletElementId: ");
		writer.print("\"");
		writer.print(portletElementId);
		writer.print("\"");

		writer.print("}");
	}

	private void _writeScript(
		PrintWriter writer, String portletElementId, String portletNamespace,
		String contextPath) {

		writer.println("<script type=\"text/javascript\">");

		writer.print("Liferay.Loader.require(");

		writer.print("\"");
		writer.print(_name);
		writer.print("@");
		writer.print(_version);
		writer.print("\"");

		writer.print(",");

		writer.print("function(module) {");

		writer.print("module.default(");

		_writeParameters(
			writer, portletElementId, portletNamespace, contextPath);

		writer.print(");");

		writer.print("}");

		writer.print(");");

		writer.print("</script>");
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		NPMPortlet.class);

	private final String _name;
	private final String _version;

}