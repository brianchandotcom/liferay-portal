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