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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.service.ServiceContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public interface CommerceAccountHelper {

	public AccountEntryUserRel addAccountEntryUserRel(
			long accountEntryId, long userId, long[] roleIds,
			ServiceContext serviceContext)
		throws PortalException;

	public AccountEntryUserRel addAccountEntryUserRel(
			long commerceAccountId, long commerceAccountUserId,
			ServiceContext serviceContext)
		throws PortalException;

	public void addAccountEntryUserRels(
			long accountEntryId, long[] userIds, String[] emailAddresses,
			long[] roleIds, ServiceContext serviceContext)
		throws PortalException;

	public void addDefaultRoles(long userId) throws PortalException;

	public int countUserCommerceAccounts(
			long userId, long commerceChannelGroupId)
		throws PortalException;

	public String[] getAccountEntryTypes(long commerceChannelGroupId)
		throws ConfigurationException;

	public String getAccountManagementPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public long[] getCommerceAccountGroupIds(long commerceAccountId);

	public int getCommerceSiteType(long commerceChannelGroupId)
		throws ConfigurationException;

	public AccountEntry getCurrentAccountEntry(
			long groupId, HttpServletRequest httpServletRequest)
		throws PortalException;

	public long[] getUserCommerceAccountIds(
			long userId, long commerceChannelGroupId)
		throws PortalException;

	public void setCurrentCommerceAccount(
			HttpServletRequest httpServletRequest, long groupId,
			long commerceAccountId)
		throws PortalException;

	public Integer toAccountEntryStatus(Boolean commerceAccountActive);

	public String toAccountEntryType(int commerceAccountType);

	public String[] toAccountEntryTypes(int commerceSiteType);

}