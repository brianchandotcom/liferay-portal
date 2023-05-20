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

package com.liferay.portal.security.content.security.policy.internal.servlet.filter;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfiguration;
import com.liferay.portal.security.content.security.policy.internal.constants.ContentSecurityPolicyConstants;
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

		PrintWriter printWriter = httpServletResponse.getWriter();

		ContentSecurityPolicyNonceIncludeServletResponse
			contentSecurityPolicyNonceIncludeServletResponse =
				new ContentSecurityPolicyNonceIncludeServletResponse(
					httpServletResponse);

		filterChain.doFilter(
			httpServletRequest,
			contentSecurityPolicyNonceIncludeServletResponse);

		String responseBody =
			contentSecurityPolicyNonceIncludeServletResponse.
				getResponseBodyAsString();

		String nonce = _generateNonce();

		responseBody = responseBody.replaceAll(
			"<(?i)link ", "<link nonce=\"" + nonce + "\" ");
		responseBody = responseBody.replaceAll(
			"<(?i)link>", "<link nonce=\"" + nonce + "\">");
		responseBody = responseBody.replaceAll(
			"<(?i)script ", "<script nonce=\"" + nonce + "\" ");
		responseBody = responseBody.replaceAll(
			"<(?i)script>", "<script nonce=\"" + nonce + "\">");
		responseBody = responseBody.replaceAll(
			"<(?i)style ", "<style nonce=\"" + nonce + "\" ");
		responseBody = responseBody.replaceAll(
			"<(?i)style>", "<style nonce=\"" + nonce + "\">");

		printWriter.write(responseBody);

		printWriter.close();

		policy = StringUtil.replace(
			policy,
			ContentSecurityPolicyConstants.CONTENT_SECURITY_POLICY_NONCE_TOKEN,
			ContentSecurityPolicyConstants.
				CONTENT_SECURITY_POLICY_NONCE_HEADER_PREFIX + nonce);

		httpServletResponse.setContentLength(responseBody.length());
		httpServletResponse.setHeader(
			ContentSecurityPolicyConstants.CONTENT_SECURITY_POLICY_HEADER,
			policy);
	}

	private String _generateNonce() {
		SecureRandom secureRandom = new SecureRandom();

		byte[] bytes = new byte[16];

		secureRandom.nextBytes(bytes);

		return Base64.encode(bytes);
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

		requestURI = StringUtil.toLowerCase(requestURI);

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			_getContentSecurityPolicyConfiguration(httpServletRequest);

		String[] excludedURIPaths =
			contentSecurityPolicyConfiguration.excludedURIPaths();

		for (String s : excludedURIPaths) {
			if (Validator.isNotNull(s) &&
				requestURI.startsWith(StringUtil.toLowerCase(s))) {

				return true;
			}
		}

		return false;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

	private static class ContentSecurityPolicyNonceIncludeServletResponse
		extends HttpServletResponseWrapper {

		public ContentSecurityPolicyNonceIncludeServletResponse(
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

		@Override
		public ServletOutputStream getOutputStream() {
			if (_printWriter != null) {
				throw new IllegalStateException(
					"getWriter() has already been called on this response");
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

		public String getResponseBodyAsString() throws IOException {
			if (_printWriter != null) {
				_printWriter.close();
			}
			else if (_servletOutputStream != null) {
				_servletOutputStream.close();
			}

			return _byteArrayOutputStream.toString(getCharacterEncoding());
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (_servletOutputStream != null) {
				throw new IllegalStateException(
					"getOutputStream() has already been called on this " +
						"response");
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