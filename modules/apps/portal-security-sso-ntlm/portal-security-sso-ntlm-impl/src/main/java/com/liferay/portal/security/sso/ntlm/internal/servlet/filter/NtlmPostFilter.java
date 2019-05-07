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

package com.liferay.portal.security.sso.ntlm.internal.servlet.filter;

import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration;
import com.liferay.portal.security.sso.ntlm.constants.NtlmConstants;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;

import jcifs.util.Base64;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * A fix for LPS-3795 relating to IE6 (Internet Explorer 6) handling of HTTP
 * POSTs and NTLM authentication.
 *
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration",
	immediate = true,
	property = {
		"after-filter=SSO Ntlm Filter", "servlet-context-name=",
		"servlet-filter-name=SSO Ntlm Post Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class NtlmPostFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (!_browserSniffer.isIe(httpServletRequest)) {
			return false;
		}

		String method = httpServletRequest.getMethod();

		if (!method.equals(HttpMethods.POST)) {
			return false;
		}

		long companyId = _portalInstancesLocalService.getCompanyId(
			httpServletRequest);

		try {
			NtlmConfiguration ntlmConfiguration =
				_configurationProvider.getConfiguration(
					NtlmConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, NtlmConstants.SERVICE_NAME));

			return ntlmConfiguration.enabled();
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);
		}

		return false;
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

		String authorization = GetterUtil.getString(
			httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));

		if (authorization.startsWith("NTLM ")) {
			byte[] src = Base64.decode(authorization.substring(5));

			if (src[8] == 1) {
				Type1Message type1 = new Type1Message(src);

				Type2Message type2 = new Type2Message(type1, new byte[8], null);

				authorization = Base64.encode(type2.toByteArray());

				httpServletResponse.setHeader(
					HttpHeaders.WWW_AUTHENTICATE, "NTLM " + authorization);

				httpServletResponse.setStatus(
					HttpServletResponse.SC_UNAUTHORIZED);
				httpServletResponse.setContentLength(0);

				httpServletResponse.flushBuffer();

				return;
			}
		}

		processFilter(
			NtlmPostFilter.class.getName(), httpServletRequest,
			httpServletResponse, filterChain);
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private static final Log _log = LogFactoryUtil.getLog(NtlmPostFilter.class);

	@Reference
	private BrowserSniffer _browserSniffer;

	private ConfigurationProvider _configurationProvider;

	@Reference
	private NtlmFilter _ntlmFilter;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}