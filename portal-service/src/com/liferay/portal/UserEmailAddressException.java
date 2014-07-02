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
	public UserEmailAddressException() {
		super();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	public UserEmailAddressException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
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
				"The user email name addresses for user " + user.getUserId() +
					" are not equal: " + emailAddress1 + " vs " +
						emailAddress2);

			_user = user;
			_emailAddress1 = emailAddress1;
			_emailAddress2 = emailAddress2;
		}

		public String getEmailAddress2() {
			return _emailAddress2;
		}

		public String getEmailAddress1() {
			return _emailAddress1;
		}

		public User getUser() {
			return _user;
		}

		private String _emailAddress2;
		private String _emailAddress1;
		private User _user;

	}

	public static class MustNotBeDuplicate extends UserEmailAddressException {

		public MustNotBeDuplicate(long userId, String emailAddress) {
			super(
				"User email address " + emailAddress + " cannot be set for " +
					" user " + userId + " because it is already being used");

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
		private long _userId;

	}

	public static class MustNotBeNull extends UserEmailAddressException {

		public MustNotBeNull() {
			super("User email address must not be null");
		}

		public MustNotBeNull(String fullName) {
			super(
				"User email address must not be null for the full name " +
					fullName);
		}
	}

	public static class MustValidate extends UserEmailAddressException {

		public MustValidate(
			String emailAddress, EmailAddressValidator emailAddressValidator) {

			super(
				"User email name address " + emailAddress + " does not " +
					"validate with " +
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

		private String _emailAddress;
		private EmailAddressValidator _emailAddressValidator;

	}

}