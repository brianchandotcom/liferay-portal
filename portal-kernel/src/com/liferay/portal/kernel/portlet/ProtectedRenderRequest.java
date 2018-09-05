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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.servlet.ProtectedPrincipal;

import java.security.Principal;

import javax.portlet.RenderRequest;
import javax.portlet.filter.RenderRequestWrapper;

/**
 * @author Brian Wing Shun Chan
 */
public class ProtectedRenderRequest extends RenderRequestWrapper {

	public ProtectedRenderRequest(
		RenderRequest renderRequest, String remoteUser) {

		super(renderRequest);

		_remoteUser = remoteUser;

		if (remoteUser != null) {
			_userPrincipal = new ProtectedPrincipal(remoteUser);
		}
		else {
			_userPrincipal = null;
		}
	}

	@Override
	public String getRemoteUser() {
		if (_remoteUser != null) {
			return _remoteUser;
		}

		return super.getRemoteUser();
	}

	@Override
	public Principal getUserPrincipal() {
		if (_userPrincipal != null) {
			return _userPrincipal;
		}

		return super.getUserPrincipal();
	}

	private final String _remoteUser;
	private final Principal _userPrincipal;

}