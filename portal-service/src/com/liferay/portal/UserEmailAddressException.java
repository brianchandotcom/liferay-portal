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
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.security.auth.EmailAddressValidator;

/**
 * @author Brian Wing Shun Chan
 */
public class UserEmailAddressException extends PortalException {

	@Deprecated
	public UserEmailAddressException() {
		super();
	}

	public UserEmailAddressException(String msg) {
		super(msg);
	}

	public UserEmailAddressException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UserEmailAddressException(Throwable cause) {
		super(cause);
	}

	public static class MustBeEqual extends UserEmailAddressException {

		public MustBeEqual(String emailAddress1, String emailAddress2) {
			super(
				"Email address 1, " + emailAddress1 + " does not equal email " +
					"address 2, " + emailAddress2);

			_emailAddress1 = emailAddress1;
			_emailAddress2 = emailAddress2;
		}

		public String getEmailAddress1() {
			return _emailAddress1;
		}

		public String getEmailAddress2() {
			return _emailAddress2;
		}

		private final String _emailAddress1;
		private final String _emailAddress2;
	}

	public static class MustNotBeNull extends UserEmailAddressException {

		public MustNotBeNull() {
			super("Email address must not be null");
		}
	}

	public static class MustValidate extends UserEmailAddressException {

		public MustValidate(
			String emailAddress, EmailAddressValidator emailAddressValidator) {

			super(
				"Invalid email address " + emailAddress + " according to " +
					ClassUtil.getClassName(emailAddressValidator));

			_emailAddress = emailAddress;
			_emailAddressValidator = emailAddressValidator;
		}

		public String getEmailAddress() {
			return _emailAddress;
		}

		public EmailAddressValidator getEmailAddressValidator() {
			return _emailAddressValidator;
		}

		private final String _emailAddress;
		private final EmailAddressValidator _emailAddressValidator;
	}

}