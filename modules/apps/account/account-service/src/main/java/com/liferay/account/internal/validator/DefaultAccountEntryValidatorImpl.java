/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.validator;

import com.liferay.account.internal.configuration.validator.DefaultAccountEntryValidatorConfiguration;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.validator.AccountEntryValidator;
import com.liferay.account.validator.AccountEntryValidatorResult;
import com.liferay.account.validator.BaseAccountEntryValidator;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tancredi Covioli
 */
@Component(
	configurationPid = "com.liferay.account.internal.configuration.validator.DefaultAccountEntryValidatorConfiguration",
	property = {
		"account.entry.validator.key=" + DefaultAccountEntryValidatorImpl.KEY,
		"account.entry.validator.priority:Integer=10"
	},
	service = AccountEntryValidator.class
)
public class DefaultAccountEntryValidatorImpl
	extends BaseAccountEntryValidator {

	public static final String KEY = "default";

	@Override
	public String getKey(
		AccountEntry accountEntry, Map<String, Object> context) {

		return String.valueOf(accountEntry.getAccountEntryId());
	}

	@Override
	protected AccountEntryValidatorResult doValidate(
			AccountEntry accountEntry, Map<String, Object> context)
		throws PortalException {

		return AccountEntryValidatorResult.builder(
			getKey(accountEntry, context)
		).message(
			"an-error-occurred"
		).resultFailed(
			"an-error-occurred"
		).build();
	}

	@Override
	protected DefaultAccountEntryValidatorConfiguration getConfiguration(
			AccountEntry accountEntry, Map<String, Object> context)
		throws ConfigurationException {

		return _configurationProvider.getCompanyConfiguration(
			DefaultAccountEntryValidatorConfiguration.class,
			accountEntry.getCompanyId());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}