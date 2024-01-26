/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.servlet.filter;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.content.security.policy.internal.ContentSecurityPolicyNonceManager;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfiguration;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfigurationUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			ContentSecurityPolicyConfigurationUtil.
				setContentSecurityPolicyConfiguration(
					_configurationProvider, httpServletRequest, _portal);

		if (!contentSecurityPolicyConfiguration.enabled() ||
			Validator.isNull(contentSecurityPolicyConfiguration.policy()) ||
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
			ContentSecurityPolicyConfiguration
				contentSecurityPolicyConfiguration =
					ContentSecurityPolicyConfigurationUtil.
						getContentSecurityPolicyConfiguration(
							httpServletRequest);

			String policy = contentSecurityPolicyConfiguration.policy();

			policy = StringUtil.replace(policy, "[$NONCE$]", "nonce-" + nonce);

			httpServletResponse.setHeader("Content-Security-Policy", policy);

			PrintWriter printWriter = httpServletResponse.getWriter();

			ContentSecurityPolicyHttpServletResponse
				contentSecurityPolicyHttpServletResponse =
					new ContentSecurityPolicyHttpServletResponse(
						httpServletResponse);

			filterChain.doFilter(
				httpServletRequest, contentSecurityPolicyHttpServletResponse);

			String nonceAttribute = "nonce=\"" + nonce + "\"";
			String escapedNonceAttribute = "nonce=\\\"" + nonce + "\\\"";

			String content =
				contentSecurityPolicyHttpServletResponse.getContent();

			content = content.replaceAll(
				"<(?i)link ", "<link " + nonceAttribute + " ");
			content = content.replaceAll(
				"<(?i)link>", "<link " + nonceAttribute + "");
			content = content.replaceAll(
				"<(?i)style ", "<style " + nonceAttribute + " ");
			content = content.replaceAll(
				"<(?i)style>", "<style " + nonceAttribute + ">");

			Pattern pattern = Pattern.compile(
				"\\{.*nonce=\".{" + nonce.length() + "}\".*\\}");

			Matcher matcher = pattern.matcher(content);

			while (matcher.find()) {
				String matcherGroup = matcher.group();

				String[] matcherArray = StringUtil.split(
					matcherGroup, nonceAttribute);

				StringBundler sb = new StringBundler(
					(matcherArray.length * 2) - 1);

				int open = 0;
				boolean overwrite = false;

				for (int i = 0; i < (matcherArray.length - 1); i++) {
					open += StringUtil.count(
						matcherArray[i], CharPool.OPEN_CURLY_BRACE);
					open -= StringUtil.count(
						matcherArray[i], CharPool.CLOSE_CURLY_BRACE);

					sb.append(matcherArray[i]);

					if (open > 0) {
						overwrite = true;

						sb.append(escapedNonceAttribute);
					}
					else {
						sb.append(nonceAttribute);
					}
				}

				if (overwrite) {
					sb.append(matcherArray[matcherArray.length - 1]);

					content = StringUtil.replace(
						content, matcherGroup, sb.toString());
				}
			}

			printWriter.write(content);

			printWriter.close();

			httpServletResponse.setContentLength(content.length());
		}
		finally {
			_contentSecurityPolicyNonceManager.cleanUpNonce(httpServletRequest);
		}
	}

	private boolean _isExcludedURIPath(
		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration,
		HttpServletRequest httpServletRequest) {

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