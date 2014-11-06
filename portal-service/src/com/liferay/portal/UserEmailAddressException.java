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

	public UserEmailAddressException(Throwable cause) {
		super(cause);
	}

	public static class MustBeEqual extends UserEmailAddressException {

		public MustBeEqual(
			User user, String emailAddress1, String emailAddress2) {

			super(
				String.format(
					"Email address 1 %s and email address %2 for user %s " +
						"must be equal",
					emailAddress1, emailAddress2, user.getUserId()));

			_user = user;
			_emailAddress1 = emailAddress1;
			_emailAddress2 = emailAddress2;
		}

		public String getEmailAddress1() {
			return _emailAddress1;
		}

		public String getEmailAddress2() {
			return _emailAddress2;
		}

		public User getUser() {
			return _user;
		}

		private final String _emailAddress1;
		private final String _emailAddress2;
		private final User _user;

	}

	public static class MustNotBeDuplicate extends UserEmailAddressException {

		public MustNotBeDuplicate(long userId, String emailAddress) {
			super(
				String.format(
					"Email address %s must not be duplicate but is already " +
						"used by user %s",
					emailAddress, userId));

			_userId = userId;
			_emailAddress = emailAddress;
		}

		public String getEmailAddress() {
			return _emailAddress;
		}

		public long getUserId() {
			return _userId;
		}

		private String _emailAddress;
		private final long _userId;

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

	public static class MustValidate extends UserEmailAddressException {

		public MustValidate(
			String emailAddress, EmailAddressValidator emailAddressValidator) {

			super(
				String.format(
					"Email name address %s must validate with %s", emailAddress,
					ClassUtil.getClassName(emailAddressValidator)));

			_emailAddress = emailAddress;
			_emailAddressValidator = emailAddressValidator;
		}

		public String getEmailAddress() {
			return _emailAddress;
		}

		public EmailAddressValidator getEmailAddressValidator() {
			return _emailAddressValidator;
		}

		private String _emailAddress;
		private final EmailAddressValidator _emailAddressValidator;

	}

}