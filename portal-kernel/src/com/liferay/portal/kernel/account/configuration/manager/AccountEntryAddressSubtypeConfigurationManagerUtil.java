/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.account.configuration.manager;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.Objects;

/**
 * @author Mátyás Wollner
 */
public class AccountEntryAddressSubtypeConfigurationManagerUtil {

	public static String
		getAddressSubtypeListTypeDefinitionExternalReferenceCode(
			long companyId, String name) {

		AccountEntryAddressSubtypeConfigurationManager
			accountEntryAddressSubtypeConfigurationManager =
				_accountEntryAddressSubtypeConfigurationManagerSnapshot.get();

		if (accountEntryAddressSubtypeConfigurationManager == null) {
			return null;
		}

		if (Objects.equals(name, "billing")) {
			return accountEntryAddressSubtypeConfigurationManager.
				getBillingAddressSubtypeListTypeDefinitionExternalReferenceCode(
					companyId);
		}
		else if (Objects.equals(name, "billing-and-shipping")) {
			return accountEntryAddressSubtypeConfigurationManager.
				getBillingAndShippingAddressSubtypeListTypeDefinitionExternalReferenceCode(
					companyId);
		}
		else if (Objects.equals(name, "shipping")) {
			return accountEntryAddressSubtypeConfigurationManager.
				getShippingAddressSubtypeListTypeDefinitionExternalReferenceCode(
					companyId);
		}

		return null;
	}

	private static final Snapshot
		<AccountEntryAddressSubtypeConfigurationManager>
			_accountEntryAddressSubtypeConfigurationManagerSnapshot =
				new Snapshot<>(
					AccountEntryAddressSubtypeConfigurationManagerUtil.class,
					AccountEntryAddressSubtypeConfigurationManager.class);

}