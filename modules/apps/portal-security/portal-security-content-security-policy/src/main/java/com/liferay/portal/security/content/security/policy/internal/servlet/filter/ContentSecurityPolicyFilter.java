/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.servlet.filter;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.content.security.policy.internal.ContentSecurityPolicyNonceManager;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfiguration;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

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

		if (!FeatureFlagManagerUtil.isEnabled("LPS-134060")) {
			return false;
		}

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			_getContentSecurityPolicyConfiguration(httpServletRequest);

		return contentSecurityPolicyConfiguration.enabled();
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		if (_isExcludedURIPath(httpServletRequest)) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);

			return;
		}

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			_getContentSecurityPolicyConfiguration(httpServletRequest);

		String policy = contentSecurityPolicyConfiguration.policy();

		if (Validator.isNull(policy)) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);

			return;
		}

		String nonce = _contentSecurityPolicyNonceManager.ensureNonce(
			httpServletRequest);

		try {
			_contentSecurityPolicyNonceManager.setTLSNonce(nonce);

			policy = StringUtil.replace(policy, "[$NONCE$]", "nonce-" + nonce);

			httpServletResponse.setHeader("Content-Security-Policy", policy);

			PrintWriter printWriter = httpServletResponse.getWriter();

			ContentSecurityPolicyHttpServletResponse
				contentSecurityPolicyHttpServletResponse =
					new ContentSecurityPolicyHttpServletResponse(
						httpServletResponse);

			filterChain.doFilter(
				httpServletRequest, contentSecurityPolicyHttpServletResponse);

			String content =
				contentSecurityPolicyHttpServletResponse.getContent();

			content = content.replaceAll(
				"<(?i)link ", "<link nonce=\"" + nonce + "\" ");
			content = content.replaceAll(
				"<(?i)link>", "<link nonce=\"" + nonce + "\">");
			content = content.replaceAll(
				"<(?i)script ", "<script nonce=\"" + nonce + "\" ");
			content = content.replaceAll(
				"<(?i)script>", "<script nonce=\"" + nonce + "\">");
			content = content.replaceAll(
				"<(?i)style ", "<style nonce=\"" + nonce + "\" ");
			content = content.replaceAll(
				"<(?i)style>", "<style nonce=\"" + nonce + "\">");

			printWriter.write(content);

			printWriter.close();

			httpServletResponse.setContentLength(content.length());
		}
		finally {
			_contentSecurityPolicyNonceManager.removeTLSNonce();
		}
	}

	private ContentSecurityPolicyConfiguration
		_getContentSecurityPolicyConfiguration(
			HttpServletRequest httpServletRequest) {

		try {
			long groupId = _portal.getScopeGroupId(httpServletRequest);

			if (groupId > 0) {
				return _configurationProvider.getGroupConfiguration(
					ContentSecurityPolicyConfiguration.class, groupId);
			}

			return _configurationProvider.getCompanyConfiguration(
				ContentSecurityPolicyConfiguration.class,
				_portal.getCompanyId(httpServletRequest));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private boolean _isExcludedURIPath(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		if (Validator.isNull(requestURI)) {
			return false;
		}

		for (String internallyExcludedPath : _INTERNALLY_EXCLUDED_PATHS) {
			if (Validator.isNotNull(internallyExcludedPath) &&
				requestURI.startsWith(
					StringUtil.toLowerCase(internallyExcludedPath))) {

				return true;
			}
		}

		requestURI = StringUtil.toLowerCase(requestURI);

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			_getContentSecurityPolicyConfiguration(httpServletRequest);

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

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private ContentSecurityPolicyNonceManager
		_contentSecurityPolicyNonceManager;

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