/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.request.filter;

import com.liferay.osb.faro.engine.client.util.TokenUtil;
import com.liferay.osb.faro.web.internal.annotations.TokenAuthentication;
import com.liferay.osb.faro.web.internal.util.ServletRequestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Method;

import java.util.Objects;

import javax.annotation.Priority;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Geyson Silva
 */
@Priority(Priorities.AUTHENTICATION)
public class TokenAuthenticationFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		Method method = _resourceInfo.getResourceMethod();

		if (!method.isAnnotationPresent(TokenAuthentication.class)) {
			return;
		}

		String faroBackendSecuritySignature = _httpServletRequest.getHeader(
			_ASAH_SECURITY_SIGNATURE_HEADER);

		String originalURL = ServletRequestUtil.getOriginalURL(
			_httpServletRequest);

		if (Objects.equals(
				faroBackendSecuritySignature,
				DigestUtils.sha256Hex(
					TokenUtil.getOSBAsahSecurityToken() + originalURL))) {

			return;
		}

		_logInvalidRequest(faroBackendSecuritySignature, _httpServletRequest);

		containerRequestContext.abortWith(
			Response.status(
				Response.Status.UNAUTHORIZED
			).build());
	}

	private void _logInvalidRequest(
		String faroBackendSecuritySignature,
		HttpServletRequest httpServletRequest) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"%s attempted to access %s with an invalid security " +
						"signature %s",
					httpServletRequest.getRemoteAddr(),
					httpServletRequest.getRequestURI(),
					faroBackendSecuritySignature));
		}
	}

	private static final String _ASAH_SECURITY_SIGNATURE_HEADER =
		"OSB-Asah-Faro-Backend-Security-Signature";

	private static final Log _log = LogFactoryUtil.getLog(
		TokenAuthenticationFilter.class);

	@Context
	private HttpServletRequest _httpServletRequest;

	@Context
	private ResourceInfo _resourceInfo;

}