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

package com.liferay.portal.kernel.security.access.control;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.internal.security.access.control.AllowedIPAddressesValidator;
import com.liferay.portal.kernel.internal.security.access.control.AllowedIPAddressesValidatorFactory;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlUtil {

	public static AccessControl getAccessControl() {
		return _accessControl;
	}

	public static AccessControlContext getAccessControlContext() {
		return _accessControlContext.get();
	}

	public static void initAccessControlContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Map<String, Object> settings) {

		_accessControl.initAccessControlContext(
			httpServletRequest, httpServletResponse, settings);
	}

	public static void initContextUser(long userId) throws AuthException {
		_accessControl.initContextUser(userId);
	}

	public static boolean isAccessAllowed(
		HttpServletRequest httpServletRequest, Set<String> hostsAllowed) {

		if (hostsAllowed.isEmpty()) {
			return true;
		}

		String remoteAddr = httpServletRequest.getRemoteAddr();

		for (String hostAllowed : hostsAllowed) {
			AllowedIPAddressesValidator allowedIPAddressesValidator =
				AllowedIPAddressesValidatorFactory.create(hostAllowed);

			if (allowedIPAddressesValidator.isAllowedIPAddress(remoteAddr)) {
				return true;
			}
		}

		Set<String> computerAddresses = PortalUtil.getComputerAddresses();

		if (computerAddresses.contains(remoteAddr) &&
			hostsAllowed.contains(_SERVER_IP)) {

			return true;
		}

		return false;
	}

	public static void setAccessControlContext(
		AccessControlContext accessControlContext) {

		_accessControlContext.set(accessControlContext);
	}

	public static AuthVerifierResult.State verifyRequest()
		throws PortalException {

		return _accessControl.verifyRequest();
	}

	private AccessControlUtil() {
	}

	private static final String _SERVER_IP = "SERVER_IP";

	private static volatile AccessControl _accessControl =
		ServiceProxyFactory.newServiceTrackedInstance(
			AccessControl.class, AccessControlUtil.class, "_accessControl",
			false, true);
	private static final ThreadLocal<AccessControlContext>
		_accessControlContext = new CentralizedThreadLocal<>(
			AccessControlUtil.class + "._accessControlContext");

}