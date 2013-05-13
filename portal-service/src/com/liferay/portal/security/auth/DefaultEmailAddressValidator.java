/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Shinn Lok
 */
public class DefaultEmailAddressValidator implements EmailAddressValidator {

	@Override
	public boolean validate(long companyId, String emailAddress) {
		if (!Validator.isEmailAddress(emailAddress) ||
			emailAddress.startsWith("postmaster@") ||
			emailAddress.startsWith("root@")) {

			return false;
		}
		else {
			return true;
		}
	}

}