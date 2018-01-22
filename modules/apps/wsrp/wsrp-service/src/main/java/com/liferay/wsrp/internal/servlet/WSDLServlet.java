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

package com.liferay.wsrp.internal.servlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wsrp.constants.Constants;
import com.liferay.wsrp.model.WSRPProducer;
import com.liferay.wsrp.service.WSRPProducerLocalService;

import java.io.IOException;

import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "=(osgi.http.whiteboard.context.name=wsrp-service)",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME + "=com.liferay.wsrp.servlet.WSDLServlet",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=/wsdl/*"
	},
	service = Servlet.class
)
public class WSDLServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		try {
			String content = getContent(request);

			response.setContentType(ContentTypes.TEXT_XML);

			ServletResponseUtil.write(response, content);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}

			response.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	protected String getContent(HttpServletRequest request) throws Exception {
		String path = GetterUtil.getString(request.getPathInfo());

		ServletContext servletContext = getServletContext();

		if (_paths.contains(path)) {
			String content = StringUtil.read(
				servletContext.getResourceAsStream("/WEB-INF/wsdl" + path));

			return replaceLocations(request, content);
		}

		StringBuffer sb = request.getRequestURL();

		String url = sb.toString();

		int pos = url.lastIndexOf(StringPool.SLASH);

		String wsrpProducerUuid = url.substring(pos + 1);

		WSRPProducer wsrpProducer = null;

		if (Validator.isNumber(wsrpProducerUuid)) {
			long wsrpProducerId = GetterUtil.getLong(wsrpProducerUuid);

			wsrpProducer = _wsrpProducerLocalService.getWSRPProducer(
				wsrpProducerId);

			wsrpProducerUuid = wsrpProducer.getUuid();
		}
		else {
			wsrpProducer = _wsrpProducerLocalService.getWSRPProducer(
				wsrpProducerUuid);
		}

		String version = GetterUtil.getString(
			wsrpProducer.getVersion(), Constants.WSRP_V2);

		String content = StringUtil.read(
			servletContext.getResourceAsStream(
				"/META-INF/wsdl/wsrp-" + version + "-service.wsdl"));

		content = replaceLocations(request, content);

		return StringUtil.replace(
			content,
			new String[] {"http://my.service:8080", "${wsrpProducerUuid}"},
			new String[] {getURL(request), wsrpProducerUuid});
	}

	protected String getURL(HttpServletRequest request) {
		String hostname = ParamUtil.getString(
			request, "hostname", request.getServerName());
		String port = ParamUtil.getString(
			request, "port", Integer.toString(request.getServerPort()));
		String protocol = ParamUtil.getString(
			request, "protocol", request.getScheme());

		StringBundler sb = new StringBundler(6);

		sb.append(protocol);
		sb.append(Http.PROTOCOL_DELIMITER);
		sb.append(hostname);
		sb.append(StringPool.COLON);
		sb.append(port);
		sb.append(_portal.getPathContext(request));

		return sb.toString();
	}

	protected String replaceLocations(
		HttpServletRequest request, String content) {

		String url = getURL(request) + "/wsdl";

		return StringUtil.replace(
			content,
			new String[] {"location=\"wsrp-", "schemaLocation=\"wsrp-"},
			new String[] {
				"location=\"" + url + "/wsrp-",
				"schemaLocation=\"" + url + "/wsrp-"
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(WSDLServlet.class);

	private static final Set<String> _paths = SetUtil.fromArray(
		new String[] {
			"/wsrp-1.0-bindings.wsdl", "/wsrp-1.0-interfaces.wsdl",
			"/wsrp-1.0-service.wsdl", "/wsrp-1.0-types.xsd",
			"/wsrp-2.0-bindings.wsdl", "/wsrp-2.0-extra.xsd",
			"/wsrp-2.0-interfaces.wsdl", "/wsrp-2.0-service.wsdl",
			"/wsrp-2.0-types.xsd"
		});

	@Reference
	private Portal _portal;

	@Reference
	private WSRPProducerLocalService _wsrpProducerLocalService;

}