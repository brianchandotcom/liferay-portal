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

package com.liferay.frontend.js.loader.modules.extender.internal.servlet;

import com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details",
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.js.loader.modules.extender.internal.servlet.JSLoaderConfigServlet",
		"osgi.http.whiteboard.servlet.pattern=/js_loader_config",
		"service.ranking:Integer=" + Details.MAX_VALUE_LESS_1K
	},
	service = {JSLoaderConfigServlet.class, Servlet.class}
)
public class JSLoaderConfigServlet extends HttpServlet {

	public long getLastModified() {
		return _lastModified;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_details = ConfigurableUtil.createConfigurable(
			Details.class, properties);

		_lastModified = System.currentTimeMillis();
	}

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		if (!_isStale()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Serving cached content for /js_loader_config");
			}

			_writeResponse(httpServletResponse, _objectValuePair.getValue());

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Generating content for /js_loader_config");
		}

		StringBundler sb = new StringBundler(11);

		sb.append("(function() {Liferay.EXPLAIN_RESOLUTIONS=");
		sb.append(_details.explainResolutions());
		sb.append(";Liferay.EXPOSE_GLOBAL=");
		sb.append(_details.exposeGlobal());
		sb.append(";Liferay.LOG_LEVEL=\"");
		sb.append(_details.logLevel());
		sb.append("\";Liferay.RESOLVE_PATH=\"");

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		String url = absolutePortalURLBuilder.forWhiteboard(
			"/js_resolve_modules"
		).build();

		sb.append(url);

		sb.append("\";Liferay.WAIT_TIMEOUT=");
		sb.append(_details.waitTimeout() * 1000);
		sb.append(";}());");

		String content = sb.toString();

		_objectValuePair = new ObjectValuePair<>(getLastModified(), content);

		_writeResponse(httpServletResponse, content);
	}

	private boolean _isStale() {
		if (getLastModified() > _objectValuePair.getKey()) {
			return true;
		}

		return false;
	}

	private void _writeResponse(
			HttpServletResponse httpServletResponse, String content)
		throws IOException {

		httpServletResponse.setContentType(Details.CONTENT_TYPE);

		PrintWriter printWriter = new PrintWriter(
			httpServletResponse.getOutputStream(), true);

		printWriter.write(content);

		printWriter.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSLoaderConfigServlet.class);

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private volatile Details _details;
	private volatile long _lastModified;
	private volatile ObjectValuePair<Long, String> _objectValuePair =
		new ObjectValuePair<>(0L, null);

}