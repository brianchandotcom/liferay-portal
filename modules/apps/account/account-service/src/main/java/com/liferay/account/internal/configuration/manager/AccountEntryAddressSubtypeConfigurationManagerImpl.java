/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.configuration.manager;

import com.liferay.account.configuration.AccountEntryAddressSubtypeConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.account.configuration.manager.AccountEntryAddressSubtypeConfigurationManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mátyás Wollner
 */
@Component(service = AccountEntryAddressSubtypeConfigurationManager.class)
public class AccountEntryAddressSubtypeConfigurationManagerImpl
	implements AccountEntryAddressSubtypeConfigurationManager {

	@Override
	public String
		getBillingAddressSubtypeListTypeDefinitionExternalReferenceCode(
			long companyId) {

		try {
			AccountEntryAddressSubtypeConfiguration
				accountEntryAddressSubtypeConfiguration =
					_configurationProvider.getCompanyConfiguration(
						AccountEntryAddressSubtypeConfiguration.class,
						companyId);

			return accountEntryAddressSubtypeConfiguration.
				billingAddressSubtypeListTypeDefinitionExternalReferenceCode();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	@Override
	public String
		getBillingAndShippingAddressSubtypeListTypeDefinitionExternalReferenceCode(
			long companyId) {

		try {
			AccountEntryAddressSubtypeConfiguration
				accountEntryAddressSubtypeConfiguration =
					_configurationProvider.getCompanyConfiguration(
						AccountEntryAddressSubtypeConfiguration.class,
						companyId);

			return accountEntryAddressSubtypeConfiguration.
				billingAndShippingAddressSubtypeListTypeDefinitionExternalReferenceCode();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	@Override
	public String
		getShippingAddressSubtypeListTypeDefinitionExternalReferenceCode(
			long companyId) {

		try {
			AccountEntryAddressSubtypeConfiguration
				accountEntryAddressSubtypeConfiguration =
					_configurationProvider.getCompanyConfiguration(
						AccountEntryAddressSubtypeConfiguration.class,
						companyId);

			return accountEntryAddressSubtypeConfiguration.
				shippingAddressSubtypeListTypeDefinitionExternalReferenceCode();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryAddressSubtypeConfigurationManagerImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}