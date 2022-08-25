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

import com.liferay.account.validator.AccountEntryEmailValidator;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * @author Drew Brokke
 */
public class AccountEntryEmailValidatorImpl
	implements AccountEntryEmailValidator {

	public AccountEntryEmailValidatorImpl(
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
	public boolean isBlockedDomain(String domainOrEmail) {
		return _accountEntryDomainValidator.isBlockedDomain(
			_toDomain(domainOrEmail));
	}

	@Override
	public boolean isEmailDomainValidationEnabled() {
		return _accountEntryDomainValidator.isEmailDomainValidationEnabled();
	}

	@Override
	public boolean isValidDomain(String domainOrEmail) {
		return _accountEntryDomainValidator.isValidDomain(
			_toDomain(domainOrEmail));
	}

	@Override
	public boolean isValidDomainFormat(String domain) {
		return _accountEntryDomainValidator.isValidDomainFormat(domain);
	}

	@Override
	public boolean isValidDomainStrict(String domainOrEmail) {
		return _accountEntryDomainValidator.isValidDomainStrict(
			_toDomain(domainOrEmail));
	}

	@Override
	public boolean isValidEmailFormat(String email) {
		if (_emailValidator.isValid(email) &&
			_emailAddressValidator.validate(_companyId, email)) {

			return true;
		}

		return false;
	}

	private String _toDomain(String email) {
		if (Validator.isDomain(email)) {
			return email;
		}

		String normalized = StringUtil.toLowerCase(StringUtil.trim(email));

		int index = normalized.indexOf(CharPool.AT);

		if (index <= 0) {
			return email;
		}

		return normalized.substring(index + 1);
	}

	private final AccountEntryDomainValidator _accountEntryDomainValidator;
	private final long _companyId;
	private final EmailAddressValidator _emailAddressValidator;
	private final EmailValidator _emailValidator;

}