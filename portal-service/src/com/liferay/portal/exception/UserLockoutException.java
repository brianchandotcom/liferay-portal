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

			this.fullUserDN = fullUserDN;
			this.ldapMessage = ldapMessage;
		}

		public final String fullUserDN;
		public final String ldapMessage;

	}

	public static class PasswordPolicyLockout extends UserLockoutException {

		public PasswordPolicyLockout(User user, PasswordPolicy passwordPolicy) {
			super(
				String.format(
					"User %s was locked on %s by password policy %s and will " +
						"be automatically unlocked on %s",
					user.getUserId(), user.getLockoutDate(),
					passwordPolicy.getName(),
					user.getUnlockDate(passwordPolicy)));

			this.user = user;
			this.passwordPolicy = passwordPolicy;
		}

		public final PasswordPolicy passwordPolicy;
		public final User user;

	}

}