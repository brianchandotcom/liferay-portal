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
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Brian Wing Shun Chan
 */
public class ProtectedServletRequest extends HttpServletRequestWrapper {

	public ProtectedServletRequest(
		HttpServletRequest request, String remoteUser) {

		this(request, remoteUser, null);
	}

	public ProtectedServletRequest(
		HttpServletRequest request, String remoteUser, String authType) {

		super(request);

		if (request instanceof ProtectedServletRequest) {
			ProtectedServletRequest parentRequest =
				(ProtectedServletRequest)request;

			setRequest(parentRequest.getRequest());
		}

		_remoteUser = remoteUser;

		if (remoteUser != null) {
			_userPrincipal = new ProtectedPrincipal(remoteUser);
		}
		else {
			_userPrincipal = null;
		}

		_authType = authType;
	}

	@Override
	public String getAuthType() {
		if (_authType == null) {
			return super.getAuthType();
		}

		if (_authType.equals(HttpServletRequest.BASIC_AUTH)) {
			return HttpServletRequest.BASIC_AUTH;
		}
		else if (_authType.equals(HttpServletRequest.CLIENT_CERT_AUTH)) {
			return HttpServletRequest.CLIENT_CERT_AUTH;
		}
		else if (_authType.equals(HttpServletRequest.DIGEST_AUTH)) {
			return HttpServletRequest.DIGEST_AUTH;
		}
		else if (_authType.equals(HttpServletRequest.FORM_AUTH)) {
			return HttpServletRequest.FORM_AUTH;
		}

		return _authType;
	}

	@Override
	public String getRemoteUser() {
		if (_remoteUser != null) {
			return _remoteUser;
		}
		else {
			return super.getRemoteUser();
		}
	}

	@Override
	public Principal getUserPrincipal() {
		if (_userPrincipal != null) {
			return _userPrincipal;
		}
		else {
			return super.getUserPrincipal();
		}
	}

	private String _authType;
	private final String _remoteUser;
	private final Principal _userPrincipal;

}