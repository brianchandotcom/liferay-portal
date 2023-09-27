/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.net.ConnectException;

/**
 * @author Preston Crary
 */
public class StagingGroupServiceTunnelUtil {

	public static void checkRemoteStagingGroup(
			HttpPrincipal httpPrincipal, long remoteGroupId)
		throws PortalException {

		_invoke(
			httpPrincipal,
			new MethodHandler(
				_checkRemoteStagingGroupMethodKey, remoteGroupId));
	}

	public static String getGroupDisplayURL(
			HttpPrincipal httpPrincipal, long remoteGroupId,
			boolean privateLayout, boolean secureConnection)
		throws PortalException {

		return (String)_invoke(
			httpPrincipal,
			new MethodHandler(
				_getGroupDisplayURLMethodKey, remoteGroupId, privateLayout,
				secureConnection));
	}

	private static Object _invoke(
			HttpPrincipal httpPrincipal, MethodHandler methodHandler)
		throws PortalException {

		try {
			return TunnelUtil.invoke(httpPrincipal, methodHandler);
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			if (!(exception instanceof ConnectException)) {
				_log.error(exception);
			}

			throw new SystemException(exception);
		}
	}

	private StagingGroupServiceTunnelUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingGroupServiceTunnelUtil.class);

	private static final MethodKey _checkRemoteStagingGroupMethodKey =
		new MethodKey(
			GroupServiceUtil.class, "checkRemoteStagingGroup", long.class);
	private static final MethodKey _getGroupDisplayURLMethodKey = new MethodKey(
		GroupServiceUtil.class, "getGroupDisplayURL", long.class, boolean.class,
		boolean.class);

}