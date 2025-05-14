/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectActionDescriptionException extends PortalException {

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustBeLessThan4000Characters
		extends ObjectActionDescriptionException {

		public MustBeLessThan4000Characters() {
			super(
				Arrays.asList(4000, "description"),
				"Description must be less than 4000 characters",
				"only-x-characters-are-allowed-in-the-x-field");
		}

	}

	private ObjectActionDescriptionException(
		List<Object> arguments, String message, String messageKey) {

		super(message);

		_arguments = arguments;
		_messageKey = messageKey;
	}

	private final List<Object> _arguments;
	private final String _messageKey;

}