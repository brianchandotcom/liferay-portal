/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ac;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.AuthVerifierResult;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlUtil {

	public static AccessControl getAccessControl() {
		if (_accessControl == null) {
			_accessControl = new AccessControlImpl();
		}

		return _accessControl;
	}

	public static AccessControlContext getAccessControlContext() {
		return _accessControlContext.get();
	}

	public static void initAccessControlContext(
		HttpServletRequest request, HttpServletResponse response,
		Map<String, Object> initConfiguration) {

		getAccessControl().initAccessControlContext(
			request, response, initConfiguration);
	}

	public static void initContextUser(long userId) throws AuthException {
		getAccessControl().initContextUser(userId);
	}

	public static void setAccessControlContext(
		AccessControlContext accessControlContext) {

		_accessControlContext.set(accessControlContext);
	}

	public static AuthVerifierResult.State verifyRequest()
		throws PortalException, SystemException {

		return getAccessControl().verifyRequest();
	}

	public void setAccessControl(AccessControl accessControl) {
		_accessControl = accessControl;
	}

	private static AccessControl _accessControl;
	private static ThreadLocal<AccessControlContext> _accessControlContext =
		new AutoResetThreadLocal<AccessControlContext>(
			AccessControlUtil.class + "._accessControlContext");

}