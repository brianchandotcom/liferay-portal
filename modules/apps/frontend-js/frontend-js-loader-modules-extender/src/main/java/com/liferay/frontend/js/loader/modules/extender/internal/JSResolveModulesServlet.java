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

import com.liferay.frontend.js.loader.modules.extender.internal.resolution.JSModulesResolution;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.JSModulesResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.URLDecoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.js.loader.modules.extender.internal.JSResolveModulesServlet",
		"osgi.http.whiteboard.servlet.pattern=/js_resolve_modules",
		"service.ranking:Integer=" + Details.MAX_VALUE_LESS_1K
	},
	service = {JSResolveModulesServlet.class, Servlet.class}
)
public class JSResolveModulesServlet extends HttpServlet {

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		List<String> moduleNames = _getModuleNames(request);

		JSModulesResolution context = _jsModulesResolver.resolve(moduleNames);

		response.setCharacterEncoding(StringPool.UTF8);
		response.setContentType(ContentTypes.APPLICATION_JSON);

		PrintWriter printWriter = new PrintWriter(
			response.getOutputStream(), true);

		printWriter.write(_jsonFactory.looseSerializeDeep(context));

		printWriter.close();
	}

	private List<String> _getModuleNames(HttpServletRequest request)
		throws IOException {

		String method = request.getMethod();

		String[] modules;

		if (method.equals("GET")) {
			modules = ParamUtil.getStringValues(request, "modules");
		}
		else {
			String body = StringUtil.read(request.getInputStream());

			body = URLDecoder.decode(body, request.getCharacterEncoding());

			body = body.substring(8);

			modules = body.split(StringPool.COMMA);
		}

		if (modules != null) {
			return Arrays.asList(modules);
		}

		return Collections.emptyList();
	}

	@Reference
	private JSModulesResolver _jsModulesResolver;

	@Reference
	private JSONFactory _jsonFactory;

}