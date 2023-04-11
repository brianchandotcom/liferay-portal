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

package com.liferay.commerce.test.util;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalServiceUtil;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Riccardo Alberti
 */
public class CommerceAccountGroupTestUtil {

	public static AccountGroup addAccountEntryToAccountGroup(
			long groupId, AccountEntry accountEntry)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		AccountGroup accountGroup =
			AccountGroupLocalServiceUtil.addAccountGroup(
				serviceContext.getUserId(), null, RandomTestUtil.randomString(),
				serviceContext);

		accountGroup.setExternalReferenceCode(null);
		accountGroup.setDefaultAccountGroup(false);
		accountGroup.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		accountGroup.setExpandoBridgeAttributes(serviceContext);

		accountGroup = AccountGroupLocalServiceUtil.updateAccountGroup(
			accountGroup);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		return accountGroup;
	}

}