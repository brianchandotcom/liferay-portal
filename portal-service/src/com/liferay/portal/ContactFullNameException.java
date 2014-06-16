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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.FullNameValidator;

/**
 * @author Amos Fong
 */
public class ContactFullNameException extends PortalException {

	public ContactFullNameException() {
		super();
	}

	public ContactFullNameException(String msg) {
		super(msg);
	}

	public ContactFullNameException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ContactFullNameException(Throwable cause) {
		super(cause);
	}

	public static class MustValidate extends ContactFullNameException {

		public MustValidate(
			String firstName, String middleName, String lastName,
			FullNameValidator fullNameValidator) {

			super(
				"First name " + firstName + ", middle name=" + middleName +
					" and last name=" + lastName + " are not valid using " +
						"validator " + fullNameValidator.getClass().getName());

			_firstName = firstName;
			_middleName = middleName;
			_lastName = lastName;
			_fullNameValidator = fullNameValidator;
		}

		public String getFirstName() {
			return _firstName;
		}

		public String getMiddleName() {
			return _middleName;
		}

		public String getLastName() {
			return _lastName;
		}

		public FullNameValidator getFullNameValidator() {
			return _fullNameValidator;
		}

		private final String _firstName;
		private final String _middleName;
		private final String _lastName;
		private final FullNameValidator _fullNameValidator;
	}

}