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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

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
		PortalRuntimePermission.checkGetBeanProperty(AccessControlUtil.class);

		return _instance._serviceTracker.getService();
	}

	public static AccessControlContext getAccessControlContext() {
		PortalRuntimePermission.checkGetBeanProperty(
			AccessControlUtil.class, "accessControlContext");

		return _accessControlContext.get();
	}

	public static void initAccessControlContext(
		HttpServletRequest request, HttpServletResponse response,
		Map<String, Object> settings) {

		getAccessControl().initAccessControlContext(
			request, response, settings);
	}

	public static void initContextUser(long userId) throws AuthException {
		getAccessControl().initContextUser(userId);
	}

	public static boolean isAccessAllowed(
		HttpServletRequest request, Set<String> hostsAllowed) {

		if (hostsAllowed.isEmpty()) {
			return true;
		}

		String remoteAddr = request.getRemoteAddr();

		if (hostsAllowed.contains(remoteAddr)) {
			return true;
		}

		String computerAddress = PortalUtil.getComputerAddress();

		if (computerAddress.equals(remoteAddr) &&
			hostsAllowed.contains(_SERVER_IP)) {

			return true;
		}

		return false;
	}

	public static void setAccessControlContext(
		AccessControlContext accessControlContext) {

		PortalRuntimePermission.checkSetBeanProperty(
			AccessControlUtil.class, "accessControlContext");

		_accessControlContext.set(accessControlContext);
	}

	public static AuthVerifierResult.State verifyRequest()
		throws PortalException {

		return getAccessControl().verifyRequest();
	}

	private AccessControlUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(AccessControl.class);

		_serviceTracker.open();
	}

	private static final String _SERVER_IP = "SERVER_IP";

	private static final AccessControlUtil _instance = new AccessControlUtil();

	private static final ThreadLocal<AccessControlContext>
		_accessControlContext = new AutoResetThreadLocal<>(
			AccessControlUtil.class + "._accessControlContext");

	private final ServiceTracker<?, AccessControl> _serviceTracker;

}