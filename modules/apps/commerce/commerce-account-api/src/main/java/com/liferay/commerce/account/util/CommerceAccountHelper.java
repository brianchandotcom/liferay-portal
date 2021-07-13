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

package com.liferay.commerce.account.util;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public interface CommerceAccountHelper {

	public int countUserCommerceAccounts(
			long userId, long commerceChannelGroupId)
		throws PortalException;

	public String getAccountManagementPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public long[] getCommerceAccountGroupIds(long commerceAccountId);

	public CommerceAccount getCurrentCommerceAccount(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public CommerceAccount getCurrentCommerceAccount(
			long groupId, HttpServletRequest httpServletRequest)
		throws PortalException;

	public long[] getUserCommerceAccountIds(
			long userId, long commerceChannelGroupId)
		throws PortalException;

	public void setCurrentCommerceAccount(
			HttpServletRequest httpServletRequest, long groupId,
			long commerceAccountId)
		throws PortalException;

}