/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Spasic
 */
public class UserResolver {

	public UserResolver(HttpServletRequest request)
		throws PortalException, SystemException {

		_companyId = ParamUtil.getLong(request, "companyId");

		String remoteUser = request.getRemoteUser();

		long userId = GetterUtil.getLong(remoteUser);

		if (userId == 0) {
			remoteUser = null;
		}

		if (remoteUser != null) {
			PrincipalThreadLocal.setName(remoteUser);

			_user = UserLocalServiceUtil.getUserById(userId);

			if (_companyId == 0) {
				_companyId = _user.getCompanyId();
			}
		}
		else {
			if (_companyId == 0) {
				_companyId = PortalInstances.getCompanyId(request);
			}

			if (_companyId != 0) {
				_user = UserLocalServiceUtil.getDefaultUser(_companyId);
			}
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public User getUser() {
		return _user;
	}

	private long _companyId;
	private User _user;

}