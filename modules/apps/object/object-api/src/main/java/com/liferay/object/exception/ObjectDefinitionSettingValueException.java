/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collections;
import java.util.List;

/**
 * @author Pedro Tavares
 */
public class ObjectDefinitionSettingValueException extends PortalException {

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class InvalidValue
		extends ObjectDefinitionSettingValueException {

		public InvalidValue(
			String objectDefinitionName, String objectDefinitionSettingName,
			String objectDefinitionSettingValue) {

			super(
				String.format(
					"The value %s of setting \"%s\" is invalid for object " +
						"definition \"%s\"",
					objectDefinitionSettingValue, objectDefinitionSettingName,
					objectDefinitionName));
		}

	}

	public static class StandaloneObjectEntriesAlreadyExist
		extends ObjectDefinitionSettingValueException {

		public StandaloneObjectEntriesAlreadyExist(
			String objectDefinitionName) {

			super(
				String.format(
					"Standalone object entries already exist for object " +
						"definition %s",
					objectDefinitionName),
				Collections.singletonList(objectDefinitionName),
				"standalone-object-entries-already-exist-for-object-" +
					"definition-x");
		}

	}

	private ObjectDefinitionSettingValueException(String msg) {
		super(msg);
	}

	private ObjectDefinitionSettingValueException(
		String msg, List<Object> arguments, String messageKey) {

		super(msg);

		_arguments = arguments;
		_messageKey = messageKey;
	}

	private List<Object> _arguments;
	private String _messageKey;

}