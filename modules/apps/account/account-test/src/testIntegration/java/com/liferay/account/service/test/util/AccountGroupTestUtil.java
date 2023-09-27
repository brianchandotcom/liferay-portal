/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.test.util;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Albert Lee
 */
public class AccountGroupTestUtil {

	public static AccountGroup addAccountGroup(
			AccountGroupLocalService accountGroupLocalService,
			String description, String name)
		throws Exception {

		return accountGroupLocalService.addAccountGroup(
			TestPropsValues.getUserId(), description, name,
			new ServiceContext());
	}

}