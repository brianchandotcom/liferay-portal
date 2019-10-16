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