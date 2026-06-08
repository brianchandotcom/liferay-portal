/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.virtual.host;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.virtual.host.configuration.provider.SiteVirtualHostConfigurationProviderUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.NavigableMap;
import java.util.Objects;

/**
 * @author Dante Wang
 */
public class SiteVirtualHostUtil {

	public static boolean isRestricted(
		HttpServletRequest httpServletRequest, Group group) {

		long companyId = group.getCompanyId();

		if (!SiteVirtualHostConfigurationProviderUtil.isStrictModeEnabled(
				companyId)) {

			return false;
		}

		LayoutSet publicLayoutSet = group.getPublicLayoutSet();

		NavigableMap<String, String> virtualHostnames =
			publicLayoutSet.getVirtualHostnames();

		if (virtualHostnames.isEmpty()) {
			return false;
		}

		LayoutSet requestLayoutSet = (LayoutSet)httpServletRequest.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if ((requestLayoutSet != null) &&
			(requestLayoutSet.getGroupId() == group.getGroupId())) {

			return false;
		}

		if (SiteVirtualHostConfigurationProviderUtil.
				isDefaultInstanceURLBypassAllowed(companyId)) {

			try {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);

				if (Objects.equals(
						PortalUtil.getHost(httpServletRequest),
						company.getVirtualHostname())) {

					return false;
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteVirtualHostUtil.class);

}