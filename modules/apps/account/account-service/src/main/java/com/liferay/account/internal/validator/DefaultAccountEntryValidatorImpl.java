/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.validator;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.validator.AccountEntryValidator;
import com.liferay.account.validator.AccountEntryValidatorResult;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tancredi Covioli
 */
@Component(
	property = {
		"account.entry.validator.key=" + DefaultAccountEntryValidatorImpl.KEY,
		"account.entry.validator.priority:Integer=10"
	},
	service = AccountEntryValidator.class
)
public class DefaultAccountEntryValidatorImpl implements AccountEntryValidator {

	public static final String KEY = "default";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public AccountEntryValidatorResult validate(
			Locale locale, AccountEntry accountEntry,
			Map<String, Object> context)
		throws PortalException {

		return new AccountEntryValidatorResult(true);
	}

}