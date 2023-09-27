/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		AccountGroup accountGroup = addAccountGroup(groupId);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		return accountGroup;
	}

	public static AccountGroup addAccountGroup(long groupId)
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

		return AccountGroupLocalServiceUtil.updateAccountGroup(accountGroup);
	}

}