/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.glowroot.proxy.internal.servlet;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mitre.dsmiley.httpproxy.ProxyServlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fabian Bouché
 */
@Component(
	enabled = false,
	property = {
		"osgi.http.whiteboard.context.path=/",
		"osgi.http.whiteboard.servlet.pattern=/glowroot/*",
		"servlet.init.targetUri=" + GlowrootProxyServlet.DEFAULT_TARGET_URI
	},
	service = Servlet.class
)
public class GlowrootProxyServlet extends ProxyServlet implements Serializable {

	public static final String DEFAULT_TARGET_URI =
		"http://localhost:4000/o/glowroot";

	@Override
	protected String getConfigParam(String key) {
		String configParam = super.getConfigParam(key);

		if (P_TARGET_URI.equals(key) &&
			DEFAULT_TARGET_URI.equals(configParam)) {

			String pathContext = _portal.getPathContext();

			if (!pathContext.isEmpty()) {
				configParam =
					"http://localhost:4000" + pathContext + "/o/glowroot";

				_updateGlowrootContextPath();
			}
		}

		return configParam;
	}

	@Override
	protected void service(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			PermissionChecker permissionChecker = _getPermissionChecker(
				httpServletRequest);

			if (!permissionChecker.isOmniadmin()) {
				throw new PrincipalException.MustBeCompanyAdmin(
					permissionChecker.getUserId());
			}

			super.service(
				new GzipEncodingRequestWrapper(httpServletRequest),
				httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private PermissionChecker _getPermissionChecker(
			HttpServletRequest httpServletRequest)
		throws Exception {

		User user = _portal.getUser(httpServletRequest);

		if (user == null) {
			throw new PrincipalException.MustBeAuthenticated(0);
		}

		return _permissionCheckerFactory.create(user);
	}

	private JSONObject _loadBackendAdminWebJSON() {
		try {
			return _jsonFactory.createJSONObject(
				_http.URLtoString(_BACKEND_ADMIN_WEB_URI));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to load " + _BACKEND_ADMIN_WEB_URI, exception);
			}

			return null;
		}
	}

	private void _updateGlowrootContextPath() {
		JSONObject jsonObject = _loadBackendAdminWebJSON();

		if (jsonObject == null) {
			return;
		}

		try {
			JSONObject configJSONObject = jsonObject.getJSONObject("config");

			String contextPath = configJSONObject.getString("contextPath");

			if (Objects.equals(contextPath, "/o/glowroot")) {
				configJSONObject.put(
					"contextPath", _portal.getPathContext() + "/o/glowroot");
			}

			Http.Options options = new Http.Options();

			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
			options.setLocation(_BACKEND_ADMIN_WEB_URI);
			options.setMethod(Http.Method.POST);
			options.setBody(
				configJSONObject.toString(), ContentTypes.APPLICATION_JSON,
				StandardCharsets.UTF_8.name());

			_http.URLtoString(options);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final String _BACKEND_ADMIN_WEB_URI =
		DEFAULT_TARGET_URI + "/backend/admin/web";

	private static final Log _log = LogFactoryUtil.getLog(
		GlowrootProxyServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	private static class GzipEncodingRequestWrapper
		extends HttpServletRequestWrapper {

		@Override
		public String getHeader(String name) {
			if (StringUtil.equalsIgnoreCase("Accept-Encoding", name)) {
				return null;
			}

			return super.getHeader(name);
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			if (_headerNameSet == null) {
				_headerNameSet = new HashSet<>();

				Enumeration<String> enumeration = super.getHeaderNames();

				while (enumeration.hasMoreElements()) {
					String headerName = enumeration.nextElement();

					if (!StringUtil.equalsIgnoreCase(
							"Accept-Encoding", headerName)) {

						_headerNameSet.add(headerName);
					}
				}
			}

			return Collections.enumeration(_headerNameSet);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			if (StringUtil.equalsIgnoreCase("Accept-Encoding", name)) {
				return Collections.emptyEnumeration();
			}

			return super.getHeaders(name);
		}

		private GzipEncodingRequestWrapper(
			HttpServletRequest httpServletRequest) {

			super(httpServletRequest);
		}

		private Set<String> _headerNameSet;

	}

}