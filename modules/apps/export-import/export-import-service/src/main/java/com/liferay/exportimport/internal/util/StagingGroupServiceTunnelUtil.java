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
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			if (!(e instanceof ConnectException)) {
				_log.error(e, e);
			}

			throw new SystemException(e);
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