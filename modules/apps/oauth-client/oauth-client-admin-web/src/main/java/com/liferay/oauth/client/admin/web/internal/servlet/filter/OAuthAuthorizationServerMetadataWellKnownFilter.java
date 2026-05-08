/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.servlet.filter;

import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Serves OAuth 2.0 Authorization Server Metadata (RFC 8414) at the
 * spec-mandated host-root location. The path suffix after
 * <code>/.well-known/oauth-authorization-server</code> is appended to the
 * request authority to reconstruct the issuer URL, which is then looked up
 * in the persisted authorization server metadata. With no suffix, the first
 * enabled record for the company is returned.
 *
 * @author Jorge García Jiménez
 */
@Component(
	property = {
		"before-filter=Virtual Host Filter", "dispatcher=REQUEST",
		"servlet-context-name=",
		"servlet-filter-name=OAuth Authorization Server Metadata Well-Known Filter",
		"url-pattern=/.well-known/oauth-authorization-server",
		"url-pattern=/.well-known/oauth-authorization-server/*"
	},
	service = Filter.class
)
public class OAuthAuthorizationServerMetadataWellKnownFilter
	extends BaseFilter {

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		long companyId = CompanyThreadLocal.getCompanyId();

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-63415")) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		_setCORSHeaders(httpServletResponse);

		if (Objects.equals(httpServletRequest.getMethod(), "OPTIONS")) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);

			return;
		}

		if (!Objects.equals(httpServletRequest.getMethod(), "GET")) {
			httpServletResponse.setHeader("Allow", "GET, OPTIONS");
			httpServletResponse.sendError(
				HttpServletResponse.SC_METHOD_NOT_ALLOWED);

			return;
		}

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata = _resolve(
			companyId, httpServletRequest);

		if ((oAuthClientASLocalMetadata == null) ||
			!oAuthClientASLocalMetadata.isLocalWellKnownEnabled()) {

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		httpServletResponse.setCharacterEncoding(StringPool.UTF8);
		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setHeader(
			HttpHeaders.CACHE_CONTROL, "public, max-age=300");
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(
			httpServletResponse,
			oAuthClientASLocalMetadata.getOAuthASMetadataJSON());

		httpServletResponse.flushBuffer();
	}

	private OAuthClientASLocalMetadata _resolve(
		long companyId, HttpServletRequest httpServletRequest) {

		String requestURI = httpServletRequest.getRequestURI();

		int index = requestURI.indexOf(_WELL_KNOWN_PATH);

		if (index < 0) {
			return _oAuthClientASLocalMetadataLocalService.
				fetchOAuthClientASLocalMetadata(companyId, true, null);
		}

		String issuerPath = requestURI.substring(
			index + _WELL_KNOWN_PATH.length());

		if (issuerPath.isEmpty()) {
			return _oAuthClientASLocalMetadataLocalService.
				fetchOAuthClientASLocalMetadata(companyId, true, null);
		}

		String issuer = _portal.getPortalURL(httpServletRequest) + issuerPath;

		return _oAuthClientASLocalMetadataLocalService.
			fetchOAuthClientASLocalMetadata(companyId, issuer);
	}

	private void _setCORSHeaders(HttpServletResponse httpServletResponse) {
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader(
			"Access-Control-Allow-Methods", "GET, OPTIONS");
		httpServletResponse.setHeader(
			"Access-Control-Allow-Headers", "Authorization, Content-Type");
		httpServletResponse.setHeader("Access-Control-Max-Age", "300");
	}

	private static final String _WELL_KNOWN_PATH =
		"/.well-known/oauth-authorization-server";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthAuthorizationServerMetadataWellKnownFilter.class);

	@Reference
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

	@Reference
	private Portal _portal;

}