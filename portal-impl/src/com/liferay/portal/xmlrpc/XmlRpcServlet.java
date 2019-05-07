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

package com.liferay.portal.xmlrpc;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.kernel.xmlrpc.Response;
import com.liferay.portal.kernel.xmlrpc.XmlRpcConstants;
import com.liferay.portal.kernel.xmlrpc.XmlRpcException;
import com.liferay.portal.kernel.xmlrpc.XmlRpcUtil;
import com.liferay.portal.util.PortalInstances;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class XmlRpcServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		PortalUtil.sendError(
			HttpServletResponse.SC_NOT_FOUND,
			new IllegalArgumentException("The GET method is not supported"),
			httpServletRequest, httpServletResponse);
	}

	@Override
	protected void doPost(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		Response xmlRpcResponse = null;

		try {
			long companyId = PortalInstances.getCompanyId(httpServletRequest);

			String token = getToken(httpServletRequest);

			InputStream is = httpServletRequest.getInputStream();

			String xml = StringUtil.read(is);

			Tuple methodTuple = XmlRpcParser.parseMethod(xml);

			String methodName = (String)methodTuple.getObject(0);
			Object[] args = (Object[])methodTuple.getObject(1);

			xmlRpcResponse = invokeMethod(companyId, token, methodName, args);
		}
		catch (IOException ioe) {
			xmlRpcResponse = XmlRpcUtil.createFault(
				XmlRpcConstants.NOT_WELL_FORMED, "XML is not well formed");

			if (_log.isDebugEnabled()) {
				_log.debug(ioe, ioe);
			}
		}
		catch (XmlRpcException xre) {
			_log.error(xre, xre);
		}

		if (xmlRpcResponse == null) {
			xmlRpcResponse = XmlRpcUtil.createFault(
				XmlRpcConstants.SYSTEM_ERROR, "Unknown error occurred");
		}

		httpServletResponse.setCharacterEncoding(StringPool.UTF8);
		httpServletResponse.setContentType(ContentTypes.TEXT_XML);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		try {
			ServletResponseUtil.write(
				httpServletResponse, xmlRpcResponse.toXml());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			httpServletResponse.setStatus(
				HttpServletResponse.SC_PRECONDITION_FAILED);
		}
	}

	protected String getToken(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getPathInfo();

		return HttpUtil.fixPath(token);
	}

	protected Response invokeMethod(
			long companyId, String token, String methodName, Object[] arguments)
		throws XmlRpcException {

		Method method = XmlRpcMethodUtil.getMethod(token, methodName);

		if (method == null) {
			return XmlRpcUtil.createFault(
				XmlRpcConstants.REQUESTED_METHOD_NOT_FOUND,
				"Requested method not found");
		}

		if (!method.setArguments(arguments)) {
			return XmlRpcUtil.createFault(
				XmlRpcConstants.INVALID_METHOD_PARAMETERS,
				"Method arguments are invalid");
		}

		return method.execute(companyId);
	}

	private static final Log _log = LogFactoryUtil.getLog(XmlRpcServlet.class);

}