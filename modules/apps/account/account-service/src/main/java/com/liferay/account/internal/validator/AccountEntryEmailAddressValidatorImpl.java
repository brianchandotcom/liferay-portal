/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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