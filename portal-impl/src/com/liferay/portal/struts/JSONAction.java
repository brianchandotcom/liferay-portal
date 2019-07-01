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

package com.liferay.portal.struts;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.SharedSessionServletRequest;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;
import com.liferay.portal.util.PropsValues;

import java.io.OutputStream;

import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ming-Gih Lam
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
public abstract class JSONAction implements Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (rerouteExecute(httpServletRequest, httpServletResponse)) {
			return null;
		}

		String callback = ParamUtil.getString(httpServletRequest, "callback");

		String json = null;

		try {
			checkAuthToken(httpServletRequest);

			json = getJSON(httpServletRequest, httpServletResponse);

			if (Validator.isNotNull(callback)) {
				StringBundler sb = new StringBundler(5);

				sb.append("/**/");
				sb.append(callback);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(json);
				sb.append(StringPool.CLOSE_PARENTHESIS);

				json = sb.toString();
			}
		}
		catch (PrincipalException pe) {
			_log.error(pe.getMessage());

			PortalUtil.sendError(
				HttpServletResponse.SC_FORBIDDEN, pe, httpServletRequest,
				httpServletResponse);

			return null;
		}
		catch (SecurityException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(se.getMessage());
			}

			json = JSONFactoryUtil.serializeThrowable(se);
		}
		catch (Exception e) {
			_log.error(e.getMessage());

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e,
				httpServletRequest, httpServletResponse);

			return null;
		}

		boolean refresh = ParamUtil.getBoolean(httpServletRequest, "refresh");

		if (refresh) {
			return actionMapping.getActionForward(
				ActionConstants.COMMON_REFERER);
		}
		else if (Validator.isNotNull(json)) {
			httpServletResponse.setCharacterEncoding(StringPool.UTF8);
			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
			httpServletResponse.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);

			try (OutputStream outputStream =
					httpServletResponse.getOutputStream()) {

				byte[] bytes = json.getBytes(StringPool.UTF8);

				outputStream.write(bytes);
			}
		}

		return null;
	}

	public abstract String getJSON(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected void checkAuthToken(HttpServletRequest httpServletRequest)
		throws PortalException {

		String authType = GetterUtil.getString(
			httpServletRequest.getAuthType());

		// Support for the legacy JSON API at /c/portal/json_service

		if (AccessControlUtil.getAccessControlContext() == null) {
			if (authType.equals(HttpServletRequest.BASIC_AUTH) ||
				authType.equals(HttpServletRequest.DIGEST_AUTH)) {

				return;
			}
		}
		else {

			// The new web service should only check auth tokens when the user
			// is authenticated using portal session cookies

			if (!authType.equals(HttpServletRequest.FORM_AUTH)) {
				return;
			}
		}

		if (PropsValues.JSON_SERVICE_AUTH_TOKEN_ENABLED &&
			!AccessControlUtil.isAccessAllowed(
				httpServletRequest, _hostsAllowed)) {

			AuthTokenUtil.checkCSRFToken(
				httpServletRequest, getCSRFOrigin(httpServletRequest));
		}
	}

	protected String getCSRFOrigin(HttpServletRequest httpServletRequest) {
		return ClassUtil.getClassName(this);
	}

	protected String getReroutePath() {
		return null;
	}

	protected boolean rerouteExecute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String reroutePath = getReroutePath();

		if (Validator.isNull(reroutePath)) {
			return false;
		}

		String requestServletContextName = ParamUtil.getString(
			httpServletRequest, "servletContextName");

		if (Validator.isNull(requestServletContextName)) {
			return false;
		}

		ServletContext servletContext = _servletContext;

		if (servletContext == null) {
			servletContext = (ServletContext)httpServletRequest.getAttribute(
				WebKeys.CTX);
		}

		String servletContextName = GetterUtil.getString(
			servletContext.getServletContextName());

		if (servletContextName.equals(requestServletContextName)) {
			return false;
		}

		ServletContext requestServletContext = ServletContextPool.get(
			requestServletContextName);

		if (requestServletContext == null) {
			return false;
		}

		RequestDispatcher requestDispatcher =
			requestServletContext.getRequestDispatcher(reroutePath);

		if (requestDispatcher == null) {
			return false;
		}

		requestDispatcher.forward(
			new SharedSessionServletRequest(httpServletRequest, true),
			httpServletResponse);

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(JSONAction.class);

	private final Set<String> _hostsAllowed = SetUtil.fromArray(
		PropsValues.JSON_SERVICE_AUTH_TOKEN_HOSTS_ALLOWED);
	private ServletContext _servletContext;

}