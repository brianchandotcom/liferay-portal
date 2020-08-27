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

package com.liferay.commerce.account.model.impl;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountUserRelImpl extends CommerceAccountUserRelBaseImpl {

	public CommerceAccountUserRelImpl() {
	}

	@Override
	public User getUser() throws PortalException {
		return UserLocalServiceUtil.getUser(getCommerceAccountUserId());
	}

	@Override
	public List<UserGroupRole> getUserGroupRoles() throws PortalException {
		CommerceAccount commerceAccount =
			CommerceAccountLocalServiceUtil.getCommerceAccount(
				getCommerceAccountId());

		return UserGroupRoleLocalServiceUtil.getUserGroupRoles(
			getCommerceAccountUserId(),
			commerceAccount.getCommerceAccountGroupId());
	}

}