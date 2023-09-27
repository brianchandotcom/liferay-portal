/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;

/**
 * @author Scott Lee
 */
public class UserLockoutException extends PortalException {

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

	private UserLockoutException(String msg) {
		super(msg);
	}

}