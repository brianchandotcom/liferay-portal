/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class RequiredObjectFieldException extends PortalException {

	public RequiredObjectFieldException() {
		super("At least one object field must be added");

		_messageKey = "at-least-one-object-field-must-be-added";
	}

	public RequiredObjectFieldException(String message, String messageKey) {
		super(message);

		_messageKey = messageKey;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustNotDeleteObjectFieldUniqueCompositeKey
		extends RequiredObjectFieldException {

		public MustNotDeleteObjectFieldUniqueCompositeKey() {
			super(
				StringBundler.concat(
					"This object field cannot be deleted as it is used in a ",
					"composite unique key validation. To remove this object ",
					"field you must first delete the associated unique ",
					"composite key validation."),
				StringBundler.concat(
					"this-object-field-cannot-be-deleted-as-it-is-used-in-a-",
					"composite-unique-key-validation.-to-remove-this-object-",
					"field-you-must-first-delete-the-associated-unique-",
					"composite-key-validation"));
		}

	}

	private final String _messageKey;

}