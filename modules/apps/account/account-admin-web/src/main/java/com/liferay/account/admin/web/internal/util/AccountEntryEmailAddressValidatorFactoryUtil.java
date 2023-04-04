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