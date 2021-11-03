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
public class ObjectFieldNameException extends PortalException {

	public ObjectFieldNameException() {
	}

	public ObjectFieldNameException(String msg) {
		super(msg);
	}

	public ObjectFieldNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectFieldNameException(Throwable throwable) {
		super(throwable);
	}

	public static class MustBeginWithLowerCaseLetter
		extends ObjectFieldNameException {

		public MustBeginWithLowerCaseLetter() {
			super("The first character of a name must be a lower case letter");
		}

	}

	public static class MustBeLessThan41Characters
		extends ObjectFieldNameException {

		public MustBeLessThan41Characters() {
			super("Names must be less than 41 characters");
		}

	}

	public static class MustNotBeDuplicate extends ObjectFieldNameException {

		public MustNotBeDuplicate(String name) {
			super("Duplicate name " + name);
		}

	}

	public static class MustNotBeNull extends ObjectFieldNameException {

		public MustNotBeNull() {
			super("Name is null");
		}

	}

	public static class MustNotBeReserved extends ObjectFieldNameException {

		public MustNotBeReserved(String name) {
			super("Reserved name " + name);
		}

	}

	public static class MustOnlyContainLettersAndDigits
		extends ObjectFieldNameException {

		public MustOnlyContainLettersAndDigits() {
			super("Name must only contain letters and digits");
		}

	}

}