/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.validhostname;

import com.liferay.portal.kernel.exception.NoSuchVirtualHostException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalInstances;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * This filter checks the host name is valid. It should not be disabled because
 * it also sets the company ID in the request so that subsequent calls in the
 * thread have the company ID properly set. This filter should also always be
 * the first filter in the list of filters.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class ValidHostNameFilter extends BasePortalFilter implements TryFilter {

	@Override
	public Object doFilterTry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		// Company ID needs to always be called here so that it is properly set
		// in subsequent calls

		try {
			PortalInstances.getCompanyId(
				httpServletRequest,
				_isVirtualHostsStrictAccess(httpServletRequest));
		}
		catch (NoSuchVirtualHostException noSuchVirtualHostException) {
			_log.error(noSuchVirtualHostException);

			httpServletRequest.setAttribute(
				WebKeys.UNKNOWN_VIRTUAL_HOST, Boolean.TRUE);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return null;
		}

		String serverName = httpServletRequest.getServerName();

		if ((httpServletRequest.getDispatcherType() != DispatcherType.ERROR) &&
			!PortalUtil.isValidPortalDomain(
				PortalUtil.getDefaultCompanyId(), serverName)) {

			throw new RuntimeException("Invalid host name " + serverName);
		}

		return null;
	}

	@Override
	public boolean isFilterEnabled() {
		return _FILTER_ENABLED;
	}

	private boolean _isVirtualHostsStrictAccess(
		HttpServletRequest httpServletRequest) {

		if (!PropsValues.VIRTUAL_HOSTS_STRICT_ACCESS ||
			((httpServletRequest.getDispatcherType() == DispatcherType.ERROR) &&
			 GetterUtil.getBoolean(
				 httpServletRequest.getAttribute(
					 WebKeys.UNKNOWN_VIRTUAL_HOST)))) {

			return false;
		}

		return true;
	}

	private static final boolean _FILTER_ENABLED = true;

	private static final Log _log = LogFactoryUtil.getLog(
		ValidHostNameFilter.class);

}