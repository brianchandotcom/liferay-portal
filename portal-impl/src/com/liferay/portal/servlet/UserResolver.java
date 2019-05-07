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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalInstances;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Spasic
 */
public class UserResolver {

	public UserResolver(HttpServletRequest httpServletRequest)
		throws PortalException {

		long companyId = ParamUtil.getLong(httpServletRequest, "companyId");
		User user = null;

		String remoteUser = httpServletRequest.getRemoteUser();

		long userId = GetterUtil.getLong(remoteUser);

		if (userId == 0) {
			remoteUser = null;
		}

		if (remoteUser != null) {
			PrincipalThreadLocal.setName(remoteUser);

			user = UserLocalServiceUtil.getUserById(userId);

			if (companyId == 0) {
				companyId = user.getCompanyId();
			}
		}
		else {
			if (companyId == 0) {
				companyId = PortalInstances.getCompanyId(httpServletRequest);
			}

			if (companyId != 0) {
				user = UserLocalServiceUtil.getDefaultUser(companyId);
			}
		}

		_companyId = companyId;
		_user = user;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public User getUser() {
		return _user;
	}

	private final long _companyId;
	private final User _user;

}