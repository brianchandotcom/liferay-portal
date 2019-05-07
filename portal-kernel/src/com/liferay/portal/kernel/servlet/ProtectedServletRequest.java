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

package com.liferay.portal.kernel.servlet;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ProtectedServletRequest
	extends PersistentHttpServletRequestWrapper {

	public ProtectedServletRequest(
		HttpServletRequest httpServletRequest, String remoteUser) {

		this(httpServletRequest, remoteUser, null);
	}

	public ProtectedServletRequest(
		HttpServletRequest httpServletRequest, String remoteUser,
		String authType) {

		super(httpServletRequest);

		if (remoteUser == null) {
			throw new NullPointerException("Remote user is null");
		}

		if (httpServletRequest instanceof ProtectedServletRequest) {
			ProtectedServletRequest parentRequest =
				(ProtectedServletRequest)httpServletRequest;

			setRequest(parentRequest.getRequest());
		}

		_remoteUser = remoteUser;
		_authType = authType;

		_userPrincipal = new ProtectedPrincipal(remoteUser);
	}

	@Override
	public String getAuthType() {
		if (_authType == null) {
			return super.getAuthType();
		}

		return _authType;
	}

	@Override
	public String getRemoteUser() {
		return _remoteUser;
	}

	@Override
	public Principal getUserPrincipal() {
		return _userPrincipal;
	}

	private final String _authType;
	private final String _remoteUser;
	private final Principal _userPrincipal;

}