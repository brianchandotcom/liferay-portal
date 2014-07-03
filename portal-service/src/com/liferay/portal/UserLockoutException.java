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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.User;

import java.util.Date;

/**
 * @author Scott Lee
 */
public class UserLockoutException extends PortalException {

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	public UserLockoutException() {
		super();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	public UserLockoutException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	public UserLockoutException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	public UserLockoutException(Throwable cause) {
		super(cause);
	}

	public static class LDAPLockout extends UserLockoutException {
		public LDAPLockout(String fullDN, String message) {
			super(
				String.format(
					"User %s has been locked out of LDAP with the following " +
						"message: %s",
					fullDN, message));

			_fullDN = fullDN;
			_message = message;
		}

		private String _fullDN;
		private String _message;

	}

	public static class PasswordPolicyLockout extends UserLockoutException {
		public PasswordPolicyLockout(User user, PasswordPolicy passwordPolicy) {
			super(
				String.format(
					"User %s has been locked out of the system at %s by " +
						"password policy %s and will automatically be " +
						"unlocked at %s",
					user.getUserId(), user.getLockoutDate(),
					passwordPolicy.getName(),
					DateUtil.newDate(
						user.getLockoutDate().getTime() +
							passwordPolicy.getLockoutDuration() * 1000)));

			_user = user;
			_passwordPolicy = passwordPolicy;
		}

		private PasswordPolicy _passwordPolicy;
		private User _user;

	}

}