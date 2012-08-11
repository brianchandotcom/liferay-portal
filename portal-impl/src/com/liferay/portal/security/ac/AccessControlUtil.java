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
import com.liferay.portal.security.auth.verifier.AuthVerifierResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AccessControlManager is responsible for creating authentication and
 * authorization contexts. It use AuthVerifierPipeline for fetching user from
 * servlet request. <br />
 * <br/>
 * Should be used in this order:
 * <ol><li>
 * {@link #initAccessControlContext(HttpServletRequest, HttpServletResponse)}
 * to create AuthenticationContext. AuthenticationContext is then accessible
 * from {@link #getAccessControlContext()}, internally saved as a ThreadLocal.
 * </li>
 *
 * <li>{@link #verifyRequest()} to obtain user from request and update
 * AuthenticationContext with verifier specific settings.</li>
 *
 * <li>{@link #initContextUser(long userId)} } to init all
 * authorization related ThreadLocals in the portal. Parameter userId
 * is available in {@link AccessControlContext#getVerificationResult()}</li>
 * </ol>
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
		return _accessControlContextThreadLocal.get();
	}

	public static void initAccessControlContext(
		HttpServletRequest request, HttpServletResponse response) {

		getAccessControl().initAccessControlContext(request, response);
	}

	public static void initContextUser(long userId) throws AuthException {
		getAccessControl().initContextUser(userId);
	}

	public static void setAccessControlContext(
		AccessControlContext accessControlContext) {

		_accessControlContextThreadLocal.set(accessControlContext);
	}

	public static AuthVerifierResult.State verifyRequest()
		throws SystemException, PortalException {

		return getAccessControl().verifyRequest();
	}

	public void setAccessControl(AccessControl accessControl) {
		_accessControl = accessControl;
	}

	private static ThreadLocal<AccessControlContext>
		_accessControlContextThreadLocal =
			new AutoResetThreadLocal<AccessControlContext>(
				AccessControlUtil.class +
					"._accessControlContextThreadLocal");

	private static AccessControl _accessControl;

}