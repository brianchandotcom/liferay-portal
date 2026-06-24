/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.servlet.filter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.content.security.policy.internal.ContentSecurityPolicyNonceManager;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfiguration;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfigurationUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	property = {
		"after-filter=Portal CORS Servlet Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Content Security Policy Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class ContentSecurityPolicyFilter extends BasePortalFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (CompanyThreadLocal.getCompanyId() == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Content security policy will not be applied to this " +
						"request for company ID 0");
			}

			return false;
		}

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			ContentSecurityPolicyConfigurationUtil.
				setContentSecurityPolicyConfiguration(
					_configurationProvider, httpServletRequest, _portal);

		if (!contentSecurityPolicyConfiguration.enabled() ||
			Validator.isNull(contentSecurityPolicyConfiguration.policy()) ||
			_isExcludedLayoutEditMode(httpServletRequest) ||
			_isExcludedURIPath(
				contentSecurityPolicyConfiguration, httpServletRequest)) {

			return false;
		}

		return true;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		String nonce = _contentSecurityPolicyNonceManager.setNonce(
			httpServletRequest);

		try {
			httpServletResponse.setContentType("text/html; charset=UTF-8");

			ContentSecurityPolicyConfiguration
				contentSecurityPolicyConfiguration =
					ContentSecurityPolicyConfigurationUtil.
						getContentSecurityPolicyConfiguration(
							httpServletRequest);

			String policy = contentSecurityPolicyConfiguration.policy();

			policy = StringUtil.replace(policy, "[$NONCE$]", "nonce-" + nonce);

			if (contentSecurityPolicyConfiguration.reportOnly()) {
				httpServletResponse.setHeader(
					"Content-Security-Policy-Report-Only", policy);
			}
			else {
				httpServletResponse.setHeader(
					"Content-Security-Policy", policy);
			}

			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
		finally {
			_contentSecurityPolicyNonceManager.cleanUpNonce(httpServletRequest);
		}
	}

	private String _getFriendlyURL(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		if (Validator.isNull(requestURI)) {
			return null;
		}

		// Match the friendly URL servlet mapping. A virtual host request only
		// reaches the "/web/<group>/<layout>" form once it is forwarded; the
		// original dispatch does not match and is deferred below. The i18n
		// language prefix is already stripped before the request is forwarded.

		for (String mapping :
				new String[] {
					_portal.getPathFriendlyURLPrivateGroup(),
					_portal.getPathFriendlyURLPrivateUser(),
					_portal.getPathFriendlyURLPublic()
				}) {

			if (requestURI.startsWith(mapping + StringPool.SLASH)) {
				return requestURI;
			}
		}

		return null;
	}

	private PermissionChecker _getPermissionChecker(
			HttpServletRequest httpServletRequest)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			return permissionChecker;
		}

		User user = _portal.getUser(httpServletRequest);

		if (user == null) {
			return null;
		}

		return _permissionCheckerFactory.create(user);
	}

	private boolean _isExcludedLayoutEditMode(
		HttpServletRequest httpServletRequest) {

		// The content security policy breaks the layout editor, which uses eval
		// and inline styles via CKEditor 4. Exclude it only for a genuine edit
		// mode render of a layout the user can update, so appending
		// "p_l_mode=edit" to any URL cannot disable the policy.

		if (!Constants.EDIT.equals(
				httpServletRequest.getParameter("p_l_mode"))) {

			return false;
		}

		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

		if (layout == null) {
			String friendlyURL = _getFriendlyURL(httpServletRequest);

			if (friendlyURL == null) {

				// The friendly URL is not resolvable yet (for example a virtual
				// host request before it is forwarded to its
				// "/web/<group>/<layout>" form). Defer the decision so a strict
				// header is not committed on this dispatch; the forwarded
				// dispatch resolves the layout and makes the final decision.

				return true;
			}

			long plid = _portal.getPlidFromFriendlyURL(
				CompanyThreadLocal.getCompanyId(), friendlyURL);

			if (plid != LayoutConstants.DEFAULT_PLID) {
				layout = _layoutLocalService.fetchLayout(plid);
			}
		}

		if (layout == null) {
			return false;
		}

		try {
			PermissionChecker permissionChecker = _getPermissionChecker(
				httpServletRequest);

			if (permissionChecker == null) {
				return false;
			}

			return _layoutPermission.containsLayoutUpdatePermission(
				permissionChecker, layout);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return false;
		}
	}

	private boolean _isExcludedURIPath(
		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration,
		HttpServletRequest httpServletRequest) {

		String requestURI = httpServletRequest.getRequestURI();

		if (Validator.isNotNull(requestURI)) {
			requestURI = StringUtil.toLowerCase(requestURI);

			for (String internallyExcludedPath : _INTERNALLY_EXCLUDED_PATHS) {
				if (Validator.isNotNull(internallyExcludedPath) &&
					requestURI.startsWith(
						StringUtil.toLowerCase(internallyExcludedPath))) {

					return true;
				}
			}
		}

		requestURI = GetterUtil.getString(
			httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_SERVLET_FORWARD_REQUEST_URI),
			requestURI);

		if (Validator.isNull(requestURI)) {
			return false;
		}

		requestURI = StringUtil.toLowerCase(requestURI);

		for (String excludedPath :
				contentSecurityPolicyConfiguration.excludedPaths()) {

			if (Validator.isNotNull(excludedPath) &&
				requestURI.startsWith(StringUtil.toLowerCase(excludedPath))) {

				return true;
			}
		}

		return false;
	}

	private static final String[] _INTERNALLY_EXCLUDED_PATHS = {
		"/group/", "/user/", "/web/"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		ContentSecurityPolicyFilter.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private ContentSecurityPolicyNonceManager
		_contentSecurityPolicyNonceManager;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	private static class ContentSecurityPolicyHttpServletResponse
		extends HttpServletResponseWrapper {

		public ContentSecurityPolicyHttpServletResponse(
			HttpServletResponse httpServletResponse) {

			super(httpServletResponse);

			_byteArrayOutputStream = new ByteArrayOutputStream(
				httpServletResponse.getBufferSize());
		}

		@Override
		public void flushBuffer() throws IOException {
			super.flushBuffer();

			if (_printWriter != null) {
				_printWriter.flush();
			}
			else if (_servletOutputStream != null) {
				_servletOutputStream.flush();
			}
		}

		public String getContent() throws IOException {
			if (_printWriter != null) {
				_printWriter.close();
			}
			else if (_servletOutputStream != null) {
				_servletOutputStream.close();
			}

			return _byteArrayOutputStream.toString(getCharacterEncoding());
		}

		@Override
		public ServletOutputStream getOutputStream() {
			if (_printWriter != null) {
				throw new IllegalStateException(
					"Get writer has already been called");
			}

			if (_servletOutputStream == null) {
				_servletOutputStream = new ServletOutputStream() {

					@Override
					public void close() throws IOException {
						_byteArrayOutputStream.close();
					}

					@Override
					public void flush() throws IOException {
						_byteArrayOutputStream.flush();
					}

					@Override
					public boolean isReady() {
						return _servletOutputStream.isReady();
					}

					@Override
					public void setWriteListener(WriteListener writeListener) {
						_servletOutputStream.setWriteListener(writeListener);
					}

					@Override
					public void write(int b) {
						_byteArrayOutputStream.write(b);
					}

				};
			}

			return _servletOutputStream;
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (_servletOutputStream != null) {
				throw new IllegalStateException(
					"Get output stream has already been called");
			}

			if (_printWriter == null) {
				_printWriter = new PrintWriter(
					new OutputStreamWriter(
						_byteArrayOutputStream, getCharacterEncoding()));
			}

			return _printWriter;
		}

		private final ByteArrayOutputStream _byteArrayOutputStream;
		private PrintWriter _printWriter;
		private ServletOutputStream _servletOutputStream;

	}

}