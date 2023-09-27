/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

/**
 * @author Drew Brokke
 */
public interface AccountEntryEmailAddressValidatorFactory {

	public AccountEntryEmailAddressValidator create(long companyId);

	public AccountEntryEmailAddressValidator create(
		long companyId, String[] validDomains);

	public AccountEntryEmailAddressValidator create(
		String[] blockedDomains, long companyId, String[] customTLDs,
		boolean emailAddressDomainValidationEnabled, String[] validDomains);

}