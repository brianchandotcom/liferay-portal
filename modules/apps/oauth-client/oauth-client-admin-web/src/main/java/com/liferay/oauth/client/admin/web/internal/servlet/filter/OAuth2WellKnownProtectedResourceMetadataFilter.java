/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.servlet.filter;

import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataLocalService;
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
 * @author Jorge García Jiménez
 */
@Component(
	property = {
		"before-filter=Virtual Host Filter", "dispatcher=REQUEST",
		"servlet-context-name=",
		"servlet-filter-name=OAuth 2 Well-Known Protected Resource Metadata Filter",
		"url-pattern=/.well-known/oauth-protected-resource",
		"url-pattern=/.well-known/oauth-protected-resource/*"
	},
	service = Filter.class
)
public class OAuth2WellKnownProtectedResourceMetadataFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return FeatureFlagManagerUtil.isEnabled(
			CompanyThreadLocal.getCompanyId(), "LPD-63415");
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		httpServletResponse.setHeader(
			"Access-Control-Allow-Headers", "Authorization, Content-Type");
		httpServletResponse.setHeader(
			"Access-Control-Allow-Methods", "GET, HEAD, OPTIONS");
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Max-Age", "300");

		String method = httpServletRequest.getMethod();

		if (Objects.equals(method, "OPTIONS")) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);

			return;
		}

		if (!Objects.equals(method, "GET") && !Objects.equals(method, "HEAD")) {
			httpServletResponse.setHeader("Allow", "GET, HEAD, OPTIONS");
			httpServletResponse.sendError(
				HttpServletResponse.SC_METHOD_NOT_ALLOWED);

			return;
		}

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			_resolveOAuthClientPRLocalMetadata(
				CompanyThreadLocal.getCompanyId(), httpServletRequest);

		if ((oAuthClientPRLocalMetadata == null) ||
			!oAuthClientPRLocalMetadata.isLocalWellKnownEnabled()) {

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		httpServletResponse.setCharacterEncoding(StringPool.UTF8);
		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setHeader(
			HttpHeaders.CACHE_CONTROL, "public, max-age=300");
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		if (Objects.equals(method, "GET")) {
			ServletResponseUtil.write(
				httpServletResponse,
				oAuthClientPRLocalMetadata.getMetadataJSON());
		}

		httpServletResponse.flushBuffer();
	}

	private OAuthClientPRLocalMetadata _resolveOAuthClientPRLocalMetadata(
		long companyId, HttpServletRequest httpServletRequest) {

		String requestURI = httpServletRequest.getRequestURI();

		int index = requestURI.indexOf(_WELL_KNOWN_PATH);

		if (index < 0) {
			return _oAuthClientPRLocalMetadataLocalService.
				fetchOAuthClientPRLocalMetadata(companyId, true, null);
		}

		String resourcePath = requestURI.substring(
			index + _WELL_KNOWN_PATH.length());

		String protectedResourceURI =
			_portal.getPortalURL(httpServletRequest) +
				requestURI.substring(0, index) + resourcePath;

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			_oAuthClientPRLocalMetadataLocalService.
				fetchOAuthClientPRLocalMetadata(
					companyId, protectedResourceURI);

		if (oAuthClientPRLocalMetadata != null) {
			return oAuthClientPRLocalMetadata;
		}

		if (protectedResourceURI.endsWith(StringPool.SLASH)) {
			oAuthClientPRLocalMetadata =
				_oAuthClientPRLocalMetadataLocalService.
					fetchOAuthClientPRLocalMetadata(
						companyId,
						protectedResourceURI.substring(
							0, protectedResourceURI.length() - 1));
		}
		else {
			oAuthClientPRLocalMetadata =
				_oAuthClientPRLocalMetadataLocalService.
					fetchOAuthClientPRLocalMetadata(
						companyId, protectedResourceURI + StringPool.SLASH);
		}

		if (oAuthClientPRLocalMetadata != null) {
			return oAuthClientPRLocalMetadata;
		}

		if (resourcePath.isEmpty() || resourcePath.equals(StringPool.SLASH)) {
			return _oAuthClientPRLocalMetadataLocalService.
				fetchOAuthClientPRLocalMetadata(companyId, true, null);
		}

		return null;
	}

	private static final String _WELL_KNOWN_PATH =
		"/.well-known/oauth-protected-resource";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2WellKnownProtectedResourceMetadataFilter.class);

	@Reference
	private OAuthClientPRLocalMetadataLocalService
		_oAuthClientPRLocalMetadataLocalService;

	@Reference
	private Portal _portal;

}