/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.validator;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Objects;

import org.apache.commons.validator.routines.DomainValidator;

/**
 * @author Drew Brokke
 */
public class AccountEntryDomainValidator {

	public AccountEntryDomainValidator(
		String[] blockedDomains, DomainValidator domainValidator,
		boolean emailAddressDomainValidationEnabled, String[] validDomains) {

		Objects.requireNonNull(domainValidator);

		_blockedDomains = blockedDomains;
		_domainValidator = domainValidator;
		_emailAddressDomainValidationEnabled =
			emailAddressDomainValidationEnabled;
		_validDomains = validDomains;
	}

	public String[] getBlockedDomains() {
		return _blockedDomains;
	}

	public String[] getValidDomains() {
		return _validDomains;
	}

	public boolean isBlockedDomain(String domain) {
		if (ArrayUtil.contains(_blockedDomains, domain)) {
			return true;
		}

		return false;
	}

	public boolean isEmailAddressDomainValidationEnabled() {
		return _emailAddressDomainValidationEnabled;
	}

	public boolean isValidDomain(String domain) {
		if (!isBlockedDomain(domain) &&
			(!_emailAddressDomainValidationEnabled ||
			 ArrayUtil.isEmpty(_validDomains) ||
			 ArrayUtil.contains(_validDomains, domain))) {

			return true;
		}

		return false;
	}

	public boolean isValidDomainFormat(String domain) {
		return _domainValidator.isValid(domain);
	}

	public boolean isValidDomainStrict(String domain) {
		if (!isBlockedDomain(domain) && _emailAddressDomainValidationEnabled &&
			ArrayUtil.contains(_validDomains, domain)) {

			return true;
		}

		return false;
	}

	private final String[] _blockedDomains;
	private final DomainValidator _domainValidator;
	private final boolean _emailAddressDomainValidationEnabled;
	private final String[] _validDomains;

}