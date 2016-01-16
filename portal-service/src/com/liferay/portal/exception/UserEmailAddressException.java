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

package com.liferay.portal.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.EmailAddressValidator;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class UserEmailAddressException extends PortalException {

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserEmailAddressException() {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserEmailAddressException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserEmailAddressException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserEmailAddressException(Throwable cause) {
		super(cause);
	}

	public static class MustBeEqual extends UserEmailAddressException {

		public MustBeEqual(
			User user, String emailAddress1, String emailAddress2) {

			super(
				String.format(
					"Email address 1 %s and email address 2 %s for user %s " +
						"must be equal",
					emailAddress1, emailAddress2, user.getUserId()));

			this.user = user;
			this.emailAddress1 = emailAddress1;
			this.emailAddress2 = emailAddress2;
		}

		public final String emailAddress1;
		public final String emailAddress2;
		public final User user;

	}

	public static class MustNotBeDuplicate extends UserEmailAddressException {

		public MustNotBeDuplicate(long userId, String emailAddress) {
			super(
				String.format(
					"Email address %s must not be duplicate but is already " +
						"used by user %s",
					emailAddress, userId));

			this.userId = userId;
			this.emailAddress = emailAddress;
		}

		public String emailAddress;
		public final long userId;

	}

	public static class MustNotBeNull extends UserEmailAddressException {

		public MustNotBeNull() {
			super("Email address must not be null");
		}

		public MustNotBeNull(String fullName) {
			super(
				String.format(
					"Email address must not be null for the full name %s",
					fullName));
		}

	}

	public static class MustNotBePOP3User extends UserEmailAddressException {

		public MustNotBePOP3User(String emailAddress) {
			super(
				String.format(
					"Email address %s must not be the one used to connect to " +
						"the POP3 server",
					emailAddress));

			this.emailAddress = emailAddress;
		}

		public final String emailAddress;

	}

	public static class MustNotBeReserved extends UserEmailAddressException {

		public MustNotBeReserved(
			String emailAddress, String[] reservedEmailAddresses) {

			super(
				String.format(
					"Email address %s must not be a reserved one such as: %s",
					emailAddress, StringUtil.merge(reservedEmailAddresses)));

			this.emailAddress = emailAddress;
			this.reservedEmailAddresses = reservedEmailAddresses;
		}

		public final String emailAddress;
		public final String[] reservedEmailAddresses;

	}

	public static class MustNotUseCompanyMx extends UserEmailAddressException {

		public MustNotUseCompanyMx(String emailAddress) {
			super(
				String.format(
					"Email address %s must not use the MX of the company or " +
						"one of the associated mail host names",
					emailAddress));

			this.emailAddress = emailAddress;
		}

		public final String emailAddress;

	}

	public static class MustValidate extends UserEmailAddressException {

		public MustValidate(
			String emailAddress, EmailAddressValidator emailAddressValidator) {

			super(
				String.format(
					"Email name address %s must validate with %s", emailAddress,
					ClassUtil.getClassName(emailAddressValidator)));

			this.emailAddress = emailAddress;
			this.emailAddressValidator = emailAddressValidator;
		}

		public String emailAddress;
		public final EmailAddressValidator emailAddressValidator;

	}

}