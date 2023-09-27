/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.security.ldap.validator.LDAPFilterException;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;

import java.util.Collections;

/**
 * @author Tomas Polesovsky
 */
public class SafeLdapFilterFactory {

	public static SafeLdapFilter fromUnsafeFilter(
			String unsafeFilter, LDAPFilterValidator ldapFilterValidator)
		throws LDAPFilterException {

		ldapFilterValidator.validate(unsafeFilter);

		return new SafeLdapFilter(
			new StringBundler(unsafeFilter), Collections.emptyList());
	}

}