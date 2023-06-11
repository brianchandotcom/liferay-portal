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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.audit.header.internal.configuration.AuditHeaderConfiguration;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(
	configurationPid = "com.liferay.portal.security.audit.header.internal.configuration.AuditHeaderConfiguration",
	property = {
		"after-filter=Portal CORS Servlet Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Audit Header Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class AuditHeaderFilter extends BaseFilter implements TryFilter {

	public static final String REQUEST_PARAMETER_X_LIFERAY_REQUEST =
		"X-Liferay-Request";

	public static final String REQUEST_PARAMETER_X_LIFERAY_REQUEST_COMPANY =
		"X-Liferay-Request-Company";

	public static final String REQUEST_PARAMETER_X_LIFERAY_REQUEST_SITE =
		"X-Liferay-Request-Site";

	@Override
	public Object doFilterTry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-177196") ||
			!(_auditHeaderConfiguration.enabledMALU() ||
			  _auditHeaderConfiguration.enabledAPV() ||
			  _auditHeaderConfiguration.enabledScope())) {

			return false;
		}

		Boolean enabledMALU = _auditHeaderConfiguration.enabledMALU();

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

		Boolean enabledAPV = _auditHeaderConfiguration.enabledAPV();

		if (enabledAPV) {
			long userId = _portal.getUserId(httpServletRequest);

			if (userId == 0) {
				httpServletResponse.setHeader(
					REQUEST_PARAMETER_X_LIFERAY_REQUEST, "anonymous");
			}
		}

		Boolean enabledScope = _auditHeaderConfiguration.enabledScope();

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

		return true;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_auditHeaderConfiguration = ConfigurableUtil.createConfigurable(
			AuditHeaderConfiguration.class, properties);
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AuditHeaderFilter.class);

	private AuditHeaderConfiguration _auditHeaderConfiguration;

	@Reference
	private Portal _portal;

}