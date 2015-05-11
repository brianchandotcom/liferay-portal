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
import com.liferay.portal.security.auth.FullNameValidator;

/**
 * @author Drew Brokke
 */
public class ContactNameException extends PortalException {

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public ContactNameException() {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public ContactNameException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public ContactNameException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public ContactNameException(Throwable cause) {
		super(cause);
	}

	public static class MustHaveFirstName extends ContactNameException {

		public MustHaveFirstName() {
			super("First name is required for contacts");
		}

	}

	public static class MustHaveLastName extends ContactNameException {

		public MustHaveLastName() {
			super("Last name is required for contacts");
		}

	}

	public static class MustHaveValidFullName extends ContactNameException {

		public MustHaveValidFullName(FullNameValidator fullNameValidator) {
			super(
					String.format(
						"Contact full name must validate with %s",
						fullNameValidator.getClass().getName()));

			this.fullNameValidator = fullNameValidator;
		}

		public final FullNameValidator fullNameValidator;

	}

}