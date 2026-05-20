/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Locale;

/**
 * @author Tancredi Covioli
 */
public interface AccountEntryValidatorRegistry {

	public AccountEntryValidator getAccountEntryValidator(String key);

	public List<AccountEntryValidator> getAccountEntryValidators();

	public boolean isValid(Locale locale, long accountEntryId)
		throws PortalException;

	public boolean isValid(
			Locale locale, long accountEntryId, long commerceOrderId)
		throws PortalException;

	public List<AccountEntryValidatorResult> validate(
			Locale locale, long accountEntryId)
		throws PortalException;

	public List<AccountEntryValidatorResult> validate(
			Locale locale, long accountEntryId, long commerceOrderId)
		throws PortalException;

}