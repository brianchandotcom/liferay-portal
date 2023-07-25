/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Murilo Stodolni
 */
public class ObjectFolderNameException extends PortalException {

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustBeLessThan41Characters
		extends ObjectFolderNameException {

		public MustBeLessThan41Characters() {
			super(
				"Name must be less than 41 characters",
				"only-41-characters-are-allowed");
		}

	}

	public static class MustNotBeDuplicate extends ObjectFolderNameException {

		public MustNotBeDuplicate(String name) {
			super(
				"Duplicate name " + name,
				"this-name-is-already-in-use-try-another-one");
		}

	}

	public static class MustNotBeNull extends ObjectFolderNameException {

		public MustNotBeNull() {
			super("Name is null", "name-is-required");
		}

	}

	public static class MustOnlyContainLettersAndDigits
		extends ObjectFolderNameException {

		public MustOnlyContainLettersAndDigits() {
			super(
				"Name must only contain letters and digits",
				"name-must-only-contain-letters-and-digits");
		}

	}

	private ObjectFolderNameException(String message, String messageKey) {
		super(message);

		_messageKey = messageKey;
	}

	private final String _messageKey;

}