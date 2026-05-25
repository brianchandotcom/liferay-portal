/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.account.configuration.BaseAccountEntryValidatorConfiguration;
import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import java.util.Map;

/**
 * @author Tancredi Covioli
 */
public abstract class BaseAccountEntryValidator
	implements AccountEntryValidator {

	@Override
	public final AccountEntryValidatorResult validate(
			AccountEntry accountEntry, Map<String, Object> context)
		throws PortalException {

		// TODO Check the history using getKey(...) and the duration via
		// getConfiguration(accountEntry, context). Write the result in the
		// object history using the key from getKey() after doValidate(...)
		// returns.

		return doValidate(accountEntry, context);
	}

	protected abstract AccountEntryValidatorResult doValidate(
			AccountEntry accountEntry, Map<String, Object> context)
		throws PortalException;

	protected abstract BaseAccountEntryValidatorConfiguration getConfiguration(
			AccountEntry accountEntry, Map<String, Object> context)
		throws ConfigurationException;

}