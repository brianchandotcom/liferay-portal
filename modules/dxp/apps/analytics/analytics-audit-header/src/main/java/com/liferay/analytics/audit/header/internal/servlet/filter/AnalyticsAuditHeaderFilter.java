/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.audit.header.internal.servlet.filter;

import com.liferay.analytics.audit.header.internal.configuration.AnalyticsAuditHeaderConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.Portal;

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
	configurationPid = "com.liferay.analytics.audit.header.internal.configuration.AnalyticsAuditHeaderConfiguration",
	property = {
		"after-filter=Portal CORS Servlet Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Audit Header Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class AnalyticsAuditHeaderFilter
	extends BaseFilter implements TryFilter {

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

		Boolean enabledMALU = _analyticsAuditHeaderConfiguration.enabledMALU();

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

		Boolean enabledAPV = _analyticsAuditHeaderConfiguration.enabledAPV();

		if (enabledAPV) {
			long userId = _portal.getUserId(httpServletRequest);

			if (userId == 0) {
				httpServletResponse.setHeader(
					REQUEST_PARAMETER_X_LIFERAY_REQUEST, "anonymous");
			}
		}

		Boolean enabledScope =
			_analyticsAuditHeaderConfiguration.enabledScope();

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

	@Override
	public boolean isFilterEnabled() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-177196") ||
			!(_analyticsAuditHeaderConfiguration.enabledMALU() ||
			  _analyticsAuditHeaderConfiguration.enabledAPV() ||
			  _analyticsAuditHeaderConfiguration.enabledScope())) {

			return false;
		}

		return true;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_analyticsAuditHeaderConfiguration =
			ConfigurableUtil.createConfigurable(
				AnalyticsAuditHeaderConfiguration.class, properties);
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsAuditHeaderFilter.class);

	private AnalyticsAuditHeaderConfiguration
		_analyticsAuditHeaderConfiguration;

	@Reference
	private Portal _portal;

}