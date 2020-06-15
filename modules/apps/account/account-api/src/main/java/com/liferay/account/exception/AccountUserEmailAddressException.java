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

package com.liferay.account.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Pei-Jung Lan
 */
public class AccountUserEmailAddressException extends PortalException {

	public AccountUserEmailAddressException() {
	}

	public AccountUserEmailAddressException(String msg) {
		super(msg);
	}

	public AccountUserEmailAddressException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AccountUserEmailAddressException(Throwable cause) {
		super(cause);
	}

	public static class MustHaveValidDomain
		extends AccountUserEmailAddressException {

		public MustHaveValidDomain(String emailAddress, String validDomains) {
			super(
				String.format(
					"Email address %s must have one of the valid domains: %s",
					emailAddress, validDomains));

			this.emailAddress = emailAddress;
			this.validDomains = validDomains;
		}

		public final String emailAddress;
		public final String validDomains;

	}

}