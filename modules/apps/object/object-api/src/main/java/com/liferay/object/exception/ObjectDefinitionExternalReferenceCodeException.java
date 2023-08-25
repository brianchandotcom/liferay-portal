/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Selton Guedes
 */
public class ObjectDefinitionExternalReferenceCodeException
	extends PortalException {

	public static class
	ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode
		extends ObjectDefinitionExternalReferenceCodeException {

		public ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode(
			String externalReferenceCode) {

			super(
				StringBundler.concat(
					"Forbidden unmodifiable system object definition external ",
					"reference code ", externalReferenceCode),
				StringBundler.concat(
					"forbidden-unmodifiable-system-object-definition-external-reference-code ",
					externalReferenceCode));
		}

	}

	public static class MustBeLessThan75Characters
		extends ObjectDefinitionExternalReferenceCodeException {

		public MustBeLessThan75Characters(int characterLimit) {
			super(
				String.format(
					"ERC must be less than %d characters", characterLimit),
				"only-x-characters-are-allowed");
		}
	}

	public static class MustNotStartWithPrefix
		extends ObjectDefinitionExternalReferenceCodeException {

		public MustNotStartWithPrefix(String prefix) {
			super(
				String.format("The prefix %s is reserved for Liferay", prefix),
				"the-prefix-x-is-reserved-for-liferay");
		}

	}

	private ObjectDefinitionExternalReferenceCodeException(
		String msg, String messageKey) {

		super(msg);

		_messageKey = messageKey;
	}

	private final String _messageKey;

}