/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.configuration;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ContentSecurityPolicyConfigurationUtil {

	public static ContentSecurityPolicyConfiguration
		getContentSecurityPolicyConfiguration(
			ConfigurationProvider configurationProvider,
			HttpServletRequest httpServletRequest, Portal portal) {

		try {
			long groupId = portal.getScopeGroupId(httpServletRequest);

			if (groupId > 0) {
				return configurationProvider.getGroupConfiguration(
					ContentSecurityPolicyConfiguration.class, groupId);
			}

			return configurationProvider.getCompanyConfiguration(
				ContentSecurityPolicyConfiguration.class,
				portal.getCompanyId(httpServletRequest));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

}
