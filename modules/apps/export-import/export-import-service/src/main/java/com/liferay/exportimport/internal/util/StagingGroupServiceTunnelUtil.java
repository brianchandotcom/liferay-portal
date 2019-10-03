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

		try {
			MethodHandler methodHandler = new MethodHandler(
				_checkRemoteStagingGroupMethodKey, remoteGroupId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof PortalException) {
					throw (PortalException)e;
				}

				throw new SystemException(e);
			}
		}
		catch (SystemException se) {
			if (se.getCause() instanceof ConnectException) {
				_log.error("Connection error: " + se.getMessage());

				if (_log.isDebugEnabled()) {
					_log.debug(se, se);
				}
			}
			else {
				_log.error(se, se);
			}

			throw se;
		}
	}

	public static String getGroupDisplayURL(
			HttpPrincipal httpPrincipal, long remoteGroupId,
			boolean privateLayout, boolean secureConnection)
		throws PortalException {

		String groupDisplayURL = null;

		try {
			MethodHandler methodHandler = new MethodHandler(
				_getGroupDisplayURLMethodKey, remoteGroupId, privateLayout,
				secureConnection);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof PortalException) {
					throw (PortalException)e;
				}

				throw new SystemException(e);
			}

			groupDisplayURL = (String)returnObj;
		}
		catch (SystemException se) {
			if (se.getCause() instanceof ConnectException) {
				_log.error("Connection error: " + se.getMessage());

				if (_log.isDebugEnabled()) {
					_log.debug(se, se);
				}
			}
			else {
				_log.error(se, se);
			}

			throw se;
		}

		return groupDisplayURL;
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