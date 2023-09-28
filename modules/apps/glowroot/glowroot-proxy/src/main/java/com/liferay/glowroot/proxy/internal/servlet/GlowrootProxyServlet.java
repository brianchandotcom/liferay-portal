/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.glowroot.proxy.internal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.util.Portal;

import java.io.Serializable;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
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
	property = {
		"osgi.http.whiteboard.context.path=/",
		"osgi.http.whiteboard.servlet.pattern=/glowroot/*",
		"servlet.init.targetUri=http://localhost:4000/o/glowroot"
	},
	service = Servlet.class
)
public class GlowrootProxyServlet extends ProxyServlet implements Serializable {

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

			GzipEncodingRequestWrapper wrapper = new GzipEncodingRequestWrapper(
				httpServletRequest);

			super.service(wrapper, httpServletResponse);
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

	private static final Log _log = LogFactoryUtil.getLog(
		GlowrootProxyServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	private static class GzipEncodingRequestWrapper
		extends HttpServletRequestWrapper {

		public GzipEncodingRequestWrapper(
			HttpServletRequest httpServletRequest) {

			super(httpServletRequest);
		}

		@Override
		public String getHeader(String name) {
			if ("Accept-Encoding".equalsIgnoreCase(name)) {
				return null;
			}

			return super.getHeader(name);
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			if (_headerNameSet == null) {
				_headerNameSet = new HashSet<>();
				Enumeration<String> wrappedHeaderNameEnumeration =
					super.getHeaderNames();

				while (wrappedHeaderNameEnumeration.hasMoreElements()) {
					String headerName =
						wrappedHeaderNameEnumeration.nextElement();

					if (!"Accept-Encoding".equalsIgnoreCase(headerName)) {
						_headerNameSet.add(headerName);
					}
				}
			}

			return Collections.enumeration(_headerNameSet);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			if ("Accept-Encoding".equalsIgnoreCase(name)) {
				return Collections.emptyEnumeration();
			}

			return super.getHeaders(name);
		}

		private Set<String> _headerNameSet;

	}

}