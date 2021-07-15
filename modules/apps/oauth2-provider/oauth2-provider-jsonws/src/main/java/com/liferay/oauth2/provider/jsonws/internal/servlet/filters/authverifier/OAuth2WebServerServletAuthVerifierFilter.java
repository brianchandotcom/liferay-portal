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

package com.liferay.oauth2.provider.jsonws.internal.servlet.filters.authverifier;

import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierConfiguration;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.authverifier.AuthVerifierFilter;

import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = {
		"before-filter=Auto Login Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=OAuth2 Web Server Servlet Auth Verifier Filter",
		"url-pattern=/c/portal/fragment/*",
		"url-pattern=/c/portal/layout_page_template/*",
		"url-pattern=/documents/*", "url-pattern=/image/*"
	},
	service = Filter.class
)
public class OAuth2WebServerServletAuthVerifierFilter
	extends AuthVerifierFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		String authorization = httpServletRequest.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isBlank(authorization) ||
			!StringUtil.startsWith(authorization, "Bearer")) {

			return false;
		}

		return super.isFilterEnabled(httpServletRequest, httpServletResponse);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> propertiesMap) {

		AuthVerifierConfiguration authVerifierConfiguration =
			new AuthVerifierConfiguration();

		authVerifierConfiguration.setAuthVerifierClassName(
			"OAuth2JSONWSAuthVerifier");

		Properties properties = new Properties();

		properties.put(
			"urls.includes",
			StringUtil.merge((Object[])propertiesMap.get("url-pattern"), ","));

		authVerifierConfiguration.setProperties(properties);

		_serviceRegistration = bundleContext.registerService(
			AuthVerifierConfiguration.class, authVerifierConfiguration,
			new HashMapDictionary<>());
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Override
	protected void processFilter(
			String logName, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		AccessControlThreadLocal.setRemoteAccess(true);

		try {
			super.processFilter(
				logName, httpServletRequest, httpServletResponse, filterChain);
		}
		finally {
			AccessControlThreadLocal.setRemoteAccess(remoteAccess);
		}
	}

	private ServiceRegistration<AuthVerifierConfiguration> _serviceRegistration;

}