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

/**
 * @author Scott Lee
 */
public class UserLockoutException extends PortalException {

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserLockoutException() {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserLockoutException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserLockoutException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserLockoutException(Throwable cause) {
		super(cause);
	}

	public static class LDAPLockout extends UserLockoutException {

		public LDAPLockout(String fullUserDN, String ldapMessage) {
			super(
				String.format(
					"User %s is locked out of a required LDAP server: %s",
					fullUserDN, ldapMessage));

			_fullUserDN = fullUserDN;
			_ldapMessage = ldapMessage;
		}

		public String getFullUserDN() {
			return _fullUserDN;
		}

		public String getLDAPMessage() {
			return _ldapMessage;
		}

		private final String _fullUserDN;
		private final String _ldapMessage;

	}

	public static class PasswordPolicyLockout extends UserLockoutException {

		public PasswordPolicyLockout(User user, PasswordPolicy passwordPolicy) {
			super(
				String.format(
					"User %s was locked on %s by password policy %s and will " +
						"be automatically unlocked on %s",
					user.getUserId(), user.getLockoutDate(),
					passwordPolicy.getName(),
					DateUtil.newDate(
						user.getLockoutDate().getTime() +
							passwordPolicy.getLockoutDuration() * 1000)));

			_user = user;
			_passwordPolicy = passwordPolicy;
		}

		public PasswordPolicy getPasswordPolicy() {
			return _passwordPolicy;
		}

		public User getUser() {
			return _user;
		}

		private final PasswordPolicy _passwordPolicy;
		private final User _user;

	}

}