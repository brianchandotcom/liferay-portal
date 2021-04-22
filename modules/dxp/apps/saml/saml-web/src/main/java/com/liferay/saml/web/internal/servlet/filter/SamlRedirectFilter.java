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

package com.liferay.saml.web.internal.servlet.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.saml.constants.SamlCommandQueryConstants;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	immediate = true,
	property = {
		"before-filter=Auto Login Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=SSO SAML Redirect Filter",
		"url-pattern=/c/portal/saml/redirect/slo",
		"url-pattern=/c/portal/saml/redirect/sso"
	},
	service = Filter.class
)
public class SamlRedirectFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled() {
		return _samlProviderConfigurationHelper.isEnabled();
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

		String pathInfo = httpServletRequest.getPathInfo();

		StringBundler sb = null;

		if (pathInfo.endsWith("sso")) {
			if (!_samlProviderConfigurationHelper.isRoleIdp()) {
				return;
			}

			sb = new StringBundler(4);

			sb.append(_portal.getRelativeHomeURL(httpServletRequest));

			sb.append(SamlCommandQueryConstants.WEB_SSO);
		}
		else {
			sb = new StringBundler(4);

			sb.append(_portal.getRelativeHomeURL(httpServletRequest));

			sb.append(SamlCommandQueryConstants.SLO);
		}

		sb.append('&');
		sb.append(httpServletRequest.getQueryString());

		httpServletResponse.sendRedirect(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlRedirectFilter.class);

	@Reference
	private Portal _portal;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}