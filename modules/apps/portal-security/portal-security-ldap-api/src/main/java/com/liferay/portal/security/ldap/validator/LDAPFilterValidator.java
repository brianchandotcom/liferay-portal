/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.validator;

import com.liferay.petra.string.StringBundler;

/**
 * @author Vilmos Papp
 */
public interface LDAPFilterValidator {

	public boolean isValid(String filter);

	public default void validate(String filter) throws LDAPFilterException {
		if (!isValid(filter)) {
			throw new LDAPFilterException("Invalid filter " + filter);
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public default void validate(String filter, String filterPropertyName)
		throws LDAPFilterException {

		if (!isValid(filter)) {
			throw new LDAPFilterException(
				StringBundler.concat(
					"Invalid filter ", filter, " defined by ",
					filterPropertyName));
		}
	}

}