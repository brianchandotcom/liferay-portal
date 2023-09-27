/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

/**
 * @author Drew Brokke
 */
public interface AccountEntryEmailAddressValidator {

	public String[] getBlockedDomains();

	public String[] getValidDomains();

	public boolean isBlockedDomain(String domainOrEmailAddress);

	public boolean isEmailAddressDomainValidationEnabled();

	public boolean isValidDomain(String domainOrEmailAddress);

	public boolean isValidDomainFormat(String domain);

	public boolean isValidDomainStrict(String domainOrEmailAddress);

	public boolean isValidEmailAddressFormat(String emailAddress);

}