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

package com.liferay.portal.kernel.exception;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class UserEmailAddressException extends PortalException {

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

		public MustNotBeDuplicate(
			long companyId, long userId, String emailAddress) {

			super(
				String.format(
					"User %s cannot be created because a user with company " +
						"%s and email address %s is already in use",
					userId, companyId, emailAddress));

			this.companyId = companyId;
			this.userId = userId;
			this.emailAddress = emailAddress;
		}

		public MustNotBeDuplicate(long companyId, String emailAddress) {
			super(
				String.format(
					"A user with company %s and email address %s is already " +
						"in use",
					companyId, emailAddress));

			this.companyId = companyId;
			this.emailAddress = emailAddress;
			userId = 0;
		}

		public final long companyId;
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

	private UserEmailAddressException(String msg) {
		super(msg);
	}

}