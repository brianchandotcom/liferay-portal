/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Marco Leo
 */
public class RequiredObjectFieldException extends PortalException {

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustNotDeleteObjectField
		extends RequiredObjectFieldException {

		public MustNotDeleteObjectField(String objectFieldName) {
			super(
				Collections.singletonList(objectFieldName),
				String.format(
					"The object field \"%s\" cannot be deleted",
					objectFieldName),
				"the-object-field-x-cannot-be-deleted");
		}

	}

	public static class MustNotDeleteObjectFieldCompositeKey
		extends RequiredObjectFieldException {

		public MustNotDeleteObjectFieldCompositeKey(String objectFieldName) {
			super(
				Collections.singletonList(objectFieldName),
				String.format(
					"The object field \"%s\" cannot be deleted because it is " +
						"used in a unique composite key validation",
					objectFieldName),
				"the-object-field-x-cannot-be-deleted-because-it-is-used-in-" +
					"a-unique-composite-key-validation");
		}

	}

	public static class MustNotDeleteObjectFieldPublishedObjectDefinition
		extends RequiredObjectFieldException {

		public MustNotDeleteObjectFieldPublishedObjectDefinition(
			String objectFieldName) {

			super(
				Collections.singletonList(objectFieldName),
				String.format(
					"The object field \"%s\" cannot be deleted because it is " +
						"the only custom object field of the published " +
							"object definition",
					objectFieldName),
				"the-object-field-x-cannot-be-deleted-because-it-is-the-only-" +
					"custom-object-field-of-the-published-object-definition");
		}

	}

	public static class MustNotDeleteObjectFieldRelationship
		extends RequiredObjectFieldException {

		public MustNotDeleteObjectFieldRelationship(
			String objectDefinitionName, String objectFieldName) {

			super(
				Arrays.asList(objectFieldName, objectDefinitionName),
				String.format(
					"The object field \"%s\" cannot be deleted because it is " +
						"the only custom object field of the published " +
							"object definition \"%s\"",
					objectFieldName, objectDefinitionName),
				"the-object-field-x-cannot-be-deleted-because-it-is-the-only-" +
					"custom-object-field-of-the-published-object-definition-x");
		}

	}

	private RequiredObjectFieldException(
		List<Object> arguments, String message, String messageKey) {

		super(message);

		_arguments = arguments;
		_messageKey = messageKey;
	}

	private final List<Object> _arguments;
	private final String _messageKey;

}