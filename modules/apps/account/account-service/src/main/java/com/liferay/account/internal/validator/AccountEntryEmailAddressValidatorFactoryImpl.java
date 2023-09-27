/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.validator;

import com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration;
import com.liferay.account.validator.AccountEntryEmailAddressValidator;
import com.liferay.account.validator.AccountEntryEmailAddressValidatorFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = AccountEntryEmailAddressValidatorFactory.class)
public class AccountEntryEmailAddressValidatorFactoryImpl
	implements AccountEntryEmailAddressValidatorFactory {

	@Override
	public AccountEntryEmailAddressValidator create(long companyId) {
		return create(companyId, _EMPTY_STRING_ARRAY);
	}

	@Override
	public AccountEntryEmailAddressValidator create(
		long companyId, String[] validDomains) {

		AccountEntryEmailDomainsConfiguration
			accountEntryEmailDomainsConfiguration =
				_getAccountEntryEmailDomainsConfiguration(companyId);

		return create(
			StringUtil.split(
				accountEntryEmailDomainsConfiguration.blockedEmailDomains(),
				StringPool.NEW_LINE),
			companyId, accountEntryEmailDomainsConfiguration.customTLDs(),
			accountEntryEmailDomainsConfiguration.enableEmailDomainValidation(),
			validDomains);
	}

	@Override
	public AccountEntryEmailAddressValidator create(
		String[] blockedDomains, long companyId, String[] customTLDs,
		boolean emailAddressDomainValidationEnabled, String[] validDomains) {

		DomainValidator domainValidator = _domainValidatorFactory.create(
			customTLDs);

		return new AccountEntryEmailAddressValidatorImpl(
			new AccountEntryDomainValidator(
				blockedDomains, domainValidator,
				emailAddressDomainValidationEnabled, validDomains),
			companyId, _emailAddressValidator,
			new EmailValidator(false, false, domainValidator));
	}

	private AccountEntryEmailDomainsConfiguration
		_getAccountEntryEmailDomainsConfiguration(long companyId) {

		try {
			return _configurationProvider.getCompanyConfiguration(
				AccountEntryEmailDomainsConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);

			return ConfigurableUtil.createConfigurable(
				AccountEntryEmailDomainsConfiguration.class,
				Collections.emptyMap());
		}
	}

	private static final String[] _EMPTY_STRING_ARRAY = new String[0];

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryEmailAddressValidatorFactoryImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DomainValidatorFactory _domainValidatorFactory;

	@Reference
	private EmailAddressValidator _emailAddressValidator;

}