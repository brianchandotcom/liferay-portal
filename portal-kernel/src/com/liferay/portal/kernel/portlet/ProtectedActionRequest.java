/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.servlet.ProtectedPrincipal;

import java.security.Principal;

import javax.portlet.ActionRequest;
import javax.portlet.filter.ActionRequestWrapper;

/**
 * @author Brian Wing Shun Chan
 */
public class ProtectedActionRequest extends ActionRequestWrapper {

	public ProtectedActionRequest(
		ActionRequest actionRequest, String remoteUser) {

		super(actionRequest);

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