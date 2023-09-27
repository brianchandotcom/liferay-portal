/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Set;

/**
 * @author Marco Leo
 */
public class ObjectFieldSettingValueException extends PortalException {

	public static class InvalidValue extends ObjectFieldSettingValueException {

		public InvalidValue(
			String objectFieldName, String objectFieldSettingName,
			String objectFieldSettingValue) {

			super(
				String.format(
					"The value %s of setting %s is invalid for object field %s",
					objectFieldSettingValue, objectFieldSettingName,
					objectFieldName));
		}

		public InvalidValue(
			String objectFieldName, String objectFieldSettingName,
			String objectFieldSettingValue, Throwable throwable) {

			super(
				String.format(
					"The value %s of setting %s is invalid for object field %s",
					objectFieldSettingValue, objectFieldSettingName,
					objectFieldName),
				throwable);
		}

	}

	public static class MissingRequiredValues
		extends ObjectFieldSettingValueException {

		public MissingRequiredValues(
			String objectFieldName, Set<String> objectFieldSettingsNames) {

			super(
				String.format(
					"The settings %s are required for object field %s",
					StringUtil.merge(
						objectFieldSettingsNames, StringPool.COMMA_AND_SPACE),
					objectFieldName));
		}

	}

	public static class MustBeLessThan256Characters
		extends ObjectFieldSettingValueException {

		public MustBeLessThan256Characters() {
			super("Storage folder path must be less than 256 characters");
		}

	}

	public static class UnmodifiableValue
		extends ObjectFieldSettingValueException {

		public UnmodifiableValue(String objectFieldSettingName) {
			super(
				String.format(
					"The value of setting %s is unmodifiable when object " +
						"definition is published",
					objectFieldSettingName));
		}

	}

	private ObjectFieldSettingValueException(String msg) {
		super(msg);
	}

	private ObjectFieldSettingValueException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}