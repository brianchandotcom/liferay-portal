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
public class ObjectActionNameException extends PortalException {

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustBeLessThan41Characters
		extends ObjectActionNameException {

		public MustBeLessThan41Characters() {
			super(
				"Name must be less than 41 characters",
				"only-41-characters-are-allowed");
		}

	}

	public static class MustNotBeDuplicate extends ObjectActionNameException {

		public MustNotBeDuplicate(String name) {
			super(
				"Duplicate name " + name,
				"this-name-is-already-in-use-try-another-one");
		}

	}

	public static class MustNotBeNull extends ObjectActionNameException {

		public MustNotBeNull() {
			super("Name is null", "name-is-required");
		}

	}

	public static class MustOnlyContainLettersAndDigits
		extends ObjectActionNameException {

		public MustOnlyContainLettersAndDigits() {
			super(
				"Name must only contain letters and digits",
				"name-must-only-contain-letters-and-digits");
		}

	}

	private ObjectActionNameException(String message, String messageKey) {
		super(message);

		_messageKey = messageKey;
	}

	private final String _messageKey;

}