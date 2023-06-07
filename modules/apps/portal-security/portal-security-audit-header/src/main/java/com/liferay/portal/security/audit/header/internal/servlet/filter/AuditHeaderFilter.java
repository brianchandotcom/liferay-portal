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

package com.liferay.portal.security.audit.header.internal.servlet.filter;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.audit.header.internal.configuration.AuditHeaderConfiguration;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(
	property = {
		"after-filter=Portal CORS Servlet Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Audit Header Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class AuditHeaderFilter extends BasePortalFilter {

	public static final String REQUEST_PARAMETER_X_LIFERAY_REQUEST =
		"X-Liferay-Request";

	public static final String REQUEST_PARAMETER_X_LIFERAY_REQUEST_COMPANY =
		"X-Liferay-Request-Company";

	public static final String REQUEST_PARAMETER_X_LIFERAY_REQUEST_SITE =
		"X-Liferay-Request-Site";

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-177196")) {
			return false;
		}

		AuditHeaderConfiguration auditHeaderConfiguration =
			_getAuditHeaderConfiguration(httpServletRequest);

		if (auditHeaderConfiguration.enabledMALU() ||
			auditHeaderConfiguration.enabledAPV() ||
			auditHeaderConfiguration.enabledScope()) {

			return true;
		}

		return false;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		AuditHeaderConfiguration auditHeaderConfiguration =
			_getAuditHeaderConfiguration(httpServletRequest);

		Boolean enabledMALU = auditHeaderConfiguration.enabledMALU();

		if (enabledMALU) {
			long userId = _portal.getUserId(httpServletRequest);

			if (userId > 0) {
				long companyId = _portal.getCompanyId(httpServletRequest);
				long groupId = _portal.getScopeGroupId(httpServletRequest);

				String header = String.valueOf(
					companyId
				).concat(
					String.valueOf(userId)
				).concat(
					String.valueOf(groupId)
				);

				String digest = DigesterUtil.digestBase64(header);

				httpServletResponse.setHeader(
					REQUEST_PARAMETER_X_LIFERAY_REQUEST, digest);
			}
		}

		Boolean enabledAPV = auditHeaderConfiguration.enabledAPV();

		if (enabledAPV) {
			long userId = _portal.getUserId(httpServletRequest);

			if (userId == 0) {
				httpServletResponse.setHeader(
					REQUEST_PARAMETER_X_LIFERAY_REQUEST, "anonymous");
			}
		}

		Boolean enabledScope = auditHeaderConfiguration.enabledScope();

		if (enabledScope) {
			long userId = _portal.getUserId(httpServletRequest);

			if (userId > 0) {
				long companyId = _portal.getCompanyId(httpServletRequest);
				long groupId = _portal.getScopeGroupId(httpServletRequest);

				httpServletResponse.setHeader(
					REQUEST_PARAMETER_X_LIFERAY_REQUEST_COMPANY,
					String.valueOf(companyId));

				httpServletResponse.setHeader(
					REQUEST_PARAMETER_X_LIFERAY_REQUEST_SITE,
					String.valueOf(groupId));
			}
		}
	}

	private AuditHeaderConfiguration _getAuditHeaderConfiguration(
		HttpServletRequest httpServletRequest) {

		try {
			return _configurationProvider.getCompanyConfiguration(
				AuditHeaderConfiguration.class,
				_portal.getCompanyId(httpServletRequest));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

}