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

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectDefinitionNameException extends PortalException {

	public static class MustBeAnAllowedModifiableSystemObjectDefinitionName
		extends ObjectDefinitionNameException {

		public MustBeAnAllowedModifiableSystemObjectDefinitionName() {
			super("Name not allowed for a modifiable system object definition");
		}

	}

	public static class MustBeginWithUpperCaseLetter
		extends ObjectDefinitionNameException {

		public MustBeginWithUpperCaseLetter() {
			super("The first character of a name must be an upper case letter");
		}

	}

	public static class MustBeLessThan41Characters
		extends ObjectDefinitionNameException {

		public MustBeLessThan41Characters() {
			super("Name must be less than 41 characters");
		}

	}

	public static class MustNotBeDuplicate
		extends ObjectDefinitionNameException {

		public MustNotBeDuplicate(String name) {
			super("Duplicate name " + name);
		}

	}

	public static class MustNotBeNull extends ObjectDefinitionNameException {

		public MustNotBeNull() {
			super("Name is null");
		}

	}

	public static class MustNotStartWithCAndUnderscoreForSystemObject
		extends ObjectDefinitionNameException {

		public MustNotStartWithCAndUnderscoreForSystemObject() {
			super("System object definition names must not start with \"C_\"");
		}

	}

	public static class MustOnlyContainLettersAndDigits
		extends ObjectDefinitionNameException {

		public MustOnlyContainLettersAndDigits() {
			super("Name must only contain letters and digits");
		}

	}

	public static class MustStartWithCAndUnderscoreForCustomObject
		extends ObjectDefinitionNameException {

		public MustStartWithCAndUnderscoreForCustomObject() {
			super("Custom object definition names must start with \"C_\"");
		}

	}

	private ObjectDefinitionNameException(String msg) {
		super(msg);
	}

}