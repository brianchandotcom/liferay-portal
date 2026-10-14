/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.responsive.image.ResponsiveImageRequest;
import com.liferay.responsive.image.configuration.ResponsiveImageConfiguration;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageConfigurationHelper {

	public ResponsiveImageConfigurationHelper(
		ConfigurationProvider configurationProvider, Portal portal) {

		_configurationProvider = configurationProvider;
		_portal = portal;
	}

	public long getCompanyId(ResponsiveImageRequest responsiveImageRequest) {
		if (responsiveImageRequest != null) {
			HttpServletRequest httpServletRequest =
				responsiveImageRequest.getHttpServletRequest();

			if (httpServletRequest != null) {
				long companyId = _portal.getCompanyId(httpServletRequest);

				if (companyId > 0) {
					return companyId;
				}
			}
		}

		Long companyId = CompanyThreadLocal.getCompanyId();

		if ((companyId != null) && (companyId > 0)) {
			return companyId;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Unable to determine a company, falling back to the system " +
					"configuration");
		}

		return 0;
	}

	public long getGroupId(ResponsiveImageRequest responsiveImageRequest) {
		if (responsiveImageRequest == null) {
			return 0;
		}

		long groupId = responsiveImageRequest.getGroupId();

		if (groupId > 0) {
			return groupId;
		}

		HttpServletRequest httpServletRequest =
			responsiveImageRequest.getHttpServletRequest();

		if (httpServletRequest != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (themeDisplay != null) {
				groupId = themeDisplay.getScopeGroupId();

				if (groupId > 0) {
					return groupId;
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			groupId = serviceContext.getScopeGroupId();

			if (groupId > 0) {
				return groupId;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Unable to determine a site, falling back to the company " +
					"configuration");
		}

		return 0;
	}

	public ResponsiveImageConfiguration getResponsiveImageConfiguration(
		long companyId, long groupId) {

		try {
			if (groupId > 0) {
				return _configurationProvider.getGroupConfiguration(
					ResponsiveImageConfiguration.class, groupId);
			}

			if (companyId > 0) {
				return _configurationProvider.getCompanyConfiguration(
					ResponsiveImageConfiguration.class, companyId);
			}

			return _configurationProvider.getSystemConfiguration(
				ResponsiveImageConfiguration.class);
		}
		catch (ConfigurationException configurationException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to read configuration for group ", groupId,
						" and company ", companyId),
					configurationException);
			}

			return null;
		}
	}

	public String getScopeKey(long companyId, long groupId) {
		if (groupId > 0) {
			return StringBundler.concat(
				ExtendedObjectClassDefinition.Scope.GROUP.getValue(),
				StringPool.POUND, groupId);
		}

		if (companyId > 0) {
			return StringBundler.concat(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue(),
				StringPool.POUND, companyId);
		}

		return ExtendedObjectClassDefinition.Scope.SYSTEM.getValue();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResponsiveImageConfigurationHelper.class);

	private final ConfigurationProvider _configurationProvider;
	private final Portal _portal;

}