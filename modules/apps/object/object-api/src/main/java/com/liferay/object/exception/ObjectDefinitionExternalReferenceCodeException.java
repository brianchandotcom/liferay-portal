/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Selton Guedes
 */
public class ObjectDefinitionExternalReferenceCodeException
	extends PortalException {

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class
		ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode
			extends ObjectDefinitionExternalReferenceCodeException {

		public ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode(
			String externalReferenceCode) {

			super(
				Collections.singletonList(externalReferenceCode),
				StringBundler.concat(
					"Forbidden unmodifiable system object definition external ",
					"reference code ", externalReferenceCode),
				"the-external-reference-code-x-is-not-allowed-for-system-" +
					"object-definitions");
		}

	}

	public static class MustBeLessThan75Characters
		extends ObjectDefinitionExternalReferenceCodeException {

		public MustBeLessThan75Characters() {
			super(
				Arrays.asList(75, "external-reference-code"),
				"External reference code must be less than 75 characters",
				"only-x-characters-are-allowed-in-the-x-field");
		}

	}

	public static class MustNotStartWithPrefix
		extends ObjectDefinitionExternalReferenceCodeException {

		public MustNotStartWithPrefix() {
			super(
				Collections.singletonList(
					ObjectDefinitionConstants.
						EXTERNAL_REFERENCE_CODE_PREFIX_SYSTEM_OBJECT_DEFINITION),
				"The prefix L_ is reserved", "the-prefix-x-is-reserved");
		}

	}

	private ObjectDefinitionExternalReferenceCodeException(
		List<Object> arguments, String message, String messageKey) {

		super(message);

		_arguments = arguments;
		_messageKey = messageKey;
	}

	private ObjectDefinitionExternalReferenceCodeException(
		String message, String messageKey) {

		super(message);

		_messageKey = messageKey;
	}

	private List<Object> _arguments;
	private final String _messageKey;

}