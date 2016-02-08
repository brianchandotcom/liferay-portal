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

import com.liferay.portal.kernel.security.auth.FullNameValidator;
import com.liferay.portal.kernel.util.ClassUtil;

/**
 * @author Drew Brokke
 */
public class ContactNameException extends PortalException {

	public static class MustHaveFirstName extends ContactNameException {

		public MustHaveFirstName() {
			super("Contacts must have a first name");
		}

	}

	public static class MustHaveLastName extends ContactNameException {

		public MustHaveLastName() {
			super("Contacts must have a last name");
		}

	}

	public static class MustHaveMiddleName extends ContactNameException {

		public MustHaveMiddleName() {
			super("Contacts must have a middle name");
		}

	}

	public static class MustHaveValidFullName extends ContactNameException {

		public MustHaveValidFullName(FullNameValidator fullNameValidator) {
			super(
				String.format(
					"Contact full name must validate with %s",
					ClassUtil.getClassName(fullNameValidator)));

			this.fullNameValidator = fullNameValidator;
		}

		public final FullNameValidator fullNameValidator;

	}

	private ContactNameException(String msg) {
		super(msg);
	}

}