/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Tancredi Covioli
 */
public interface AccountEntryValidator {

	public String getKey();

	public AccountEntryValidatorResult validate(
			Locale locale, long accountEntryId)
		throws PortalException;

	public default AccountEntryValidatorResult validate(
			Locale locale, long accountEntryId, long commerceOrderId)
		throws PortalException {

		return validate(locale, accountEntryId);
	}

}