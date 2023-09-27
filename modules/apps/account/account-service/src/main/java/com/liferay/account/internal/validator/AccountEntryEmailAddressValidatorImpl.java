/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.validator;

import com.liferay.account.validator.AccountEntryEmailAddressValidator;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * @author Drew Brokke
 */
public class AccountEntryEmailAddressValidatorImpl
	implements AccountEntryEmailAddressValidator {

	public AccountEntryEmailAddressValidatorImpl(
		AccountEntryDomainValidator accountEntryDomainValidator, long companyId,
		EmailAddressValidator emailAddressValidator,
		EmailValidator emailValidator) {

		_accountEntryDomainValidator = accountEntryDomainValidator;
		_companyId = companyId;
		_emailAddressValidator = emailAddressValidator;
		_emailValidator = emailValidator;
	}

	@Override
	public String[] getBlockedDomains() {
		return _accountEntryDomainValidator.getBlockedDomains();
	}

	@Override
	public String[] getValidDomains() {
		return _accountEntryDomainValidator.getValidDomains();
	}

	@Override
	public boolean isBlockedDomain(String domainOrEmailAddress) {
		return _accountEntryDomainValidator.isBlockedDomain(
			_toDomain(domainOrEmailAddress));
	}

	@Override
	public boolean isEmailAddressDomainValidationEnabled() {
		return _accountEntryDomainValidator.
			isEmailAddressDomainValidationEnabled();
	}

	@Override
	public boolean isValidDomain(String domainOrEmailAddress) {
		return _accountEntryDomainValidator.isValidDomain(
			_toDomain(domainOrEmailAddress));
	}

	@Override
	public boolean isValidDomainFormat(String domain) {
		return _accountEntryDomainValidator.isValidDomainFormat(domain);
	}

	@Override
	public boolean isValidDomainStrict(String domainOrEmailAddress) {
		return _accountEntryDomainValidator.isValidDomainStrict(
			_toDomain(domainOrEmailAddress));
	}

	@Override
	public boolean isValidEmailAddressFormat(String emailAddress) {
		if (_emailValidator.isValid(emailAddress) &&
			_emailAddressValidator.validate(_companyId, emailAddress)) {

			return true;
		}

		return false;
	}

	private String _toDomain(String emailAddress) {
		if (Validator.isDomain(emailAddress)) {
			return emailAddress;
		}

		String normalized = StringUtil.toLowerCase(
			StringUtil.trim(emailAddress));

		int index = normalized.indexOf(CharPool.AT);

		if (index <= 0) {
			return emailAddress;
		}

		return normalized.substring(index + 1);
	}

	private final AccountEntryDomainValidator _accountEntryDomainValidator;
	private final long _companyId;
	private final EmailAddressValidator _emailAddressValidator;
	private final EmailValidator _emailValidator;

}