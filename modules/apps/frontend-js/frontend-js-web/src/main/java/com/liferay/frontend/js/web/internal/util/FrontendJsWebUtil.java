/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.util;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

/**
 * @author Iván Zaera Avellón
 */
public class FrontendJsWebUtil {

	public static String getBaseURL(
		HttpServletRequest httpServletRequest, Portal portal) {

		if (_baseURL != null) {
			return _baseURL;
		}

		StringBundler sb = new StringBundler(2);

		try {
			sb.append(portal.getCDNHost(httpServletRequest));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		sb.append(portal.getPathProxy());

		_baseURL = sb.toString();

		return _baseURL;
	}

	public static FrontendCachingConfiguration getFrontendCachingConfiguration(
		long companyId, ConfigurationProvider configurationProvider) {

		try {
			return configurationProvider.getCompanyConfiguration(
				FrontendCachingConfiguration.class, companyId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to get frontend caching configuration for ",
						"company ", companyId, ", using defaults instead"),
					exception);
			}

			return ConfigurableUtil.createConfigurable(
				FrontendCachingConfiguration.class, new HashMap<>());
		}
	}

	public static String getPortalContextPath(Portal portal) {
		if (_portalContextPath != null) {
			return _portalContextPath;
		}

		String contextPath = portal.getPathContext();

		String proxyPath = portal.getPathProxy();

		_portalContextPath = contextPath.substring(proxyPath.length());

		return _portalContextPath;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendJsWebUtil.class);

	private static volatile String _baseURL;
	private static volatile String _portalContextPath;

}