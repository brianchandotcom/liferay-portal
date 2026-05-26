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
			AccountEntry accountEntry, Map<String, Object> additionalProps)
		throws PortalException {

		return doValidate(accountEntry, additionalProps);
	}

	protected abstract AccountEntryValidatorResult doValidate(
			AccountEntry accountEntry, Map<String, Object> additionalProps)
		throws PortalException;

	protected abstract BaseAccountEntryValidatorConfiguration getConfiguration(
			AccountEntry accountEntry, Map<String, Object> additionalProps)
		throws ConfigurationException;

}