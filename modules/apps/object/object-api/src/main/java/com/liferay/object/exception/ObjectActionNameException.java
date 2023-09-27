/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectActionNameException extends PortalException {

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustBeLessThan41Characters
		extends ObjectActionNameException {

		public MustBeLessThan41Characters() {
			super(
				Arrays.asList(41, "name"),
				"Name must be less than 41 characters",
				"only-x-characters-are-allowed-in-the-x-field");
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

	private ObjectActionNameException(
		List<Object> arguments, String message, String messageKey) {

		super(message);

		_arguments = arguments;
		_messageKey = messageKey;
	}

	private ObjectActionNameException(String message, String messageKey) {
		super(message);

		_messageKey = messageKey;
	}

	private List<Object> _arguments;
	private final String _messageKey;

}