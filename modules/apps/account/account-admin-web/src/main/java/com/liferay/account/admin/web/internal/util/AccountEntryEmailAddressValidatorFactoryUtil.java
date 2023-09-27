/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.util;

import com.liferay.account.validator.AccountEntryEmailAddressValidator;
import com.liferay.account.validator.AccountEntryEmailAddressValidatorFactory;
import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Drew Brokke
 */
public class AccountEntryEmailAddressValidatorFactoryUtil {

	public static AccountEntryEmailAddressValidator create(
		long companyId, String[] validDomains) {

		AccountEntryEmailAddressValidatorFactory
			accountEntryEmailAddressValidatorFactory =
				_accountEntryEmailAddressValidatorFactorySnapshot.get();

		return accountEntryEmailAddressValidatorFactory.create(
			companyId, validDomains);
	}

	private static final Snapshot<AccountEntryEmailAddressValidatorFactory>
		_accountEntryEmailAddressValidatorFactorySnapshot = new Snapshot<>(
			AccountEntryEmailAddressValidatorFactoryUtil.class,
			AccountEntryEmailAddressValidatorFactory.class);

}