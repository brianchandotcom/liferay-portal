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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Set;

/**
 * @author Marco Leo
 */
public class ObjectFieldSettingsValidationException extends PortalException {

	public ObjectFieldSettingsValidationException() {
	}

	public ObjectFieldSettingsValidationException(String msg) {
		super(msg);
	}

	public ObjectFieldSettingsValidationException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public ObjectFieldSettingsValidationException(Throwable throwable) {
		super(throwable);
	}

	public static class MustSetValidValue
		extends ObjectFieldSettingsValidationException {

		public MustSetValidValue(
			String objectFieldName, String setting, String value) {

			super(
				String.format(
					"The value %s of setting %s is not valid for field %s",
					value, setting, objectFieldName));
		}

		public MustSetValidValue(
			String objectFieldName, String setting, Throwable throwable,
			String value) {

			super(
				String.format(
					"The value %s of setting %s is not valid for field %s",
					value, setting, objectFieldName),
				throwable);
		}

	}

	public static class NotAllowedSettings
		extends ObjectFieldSettingsValidationException {

		public NotAllowedSettings(
			String objectFieldName, Set<String> settings) {

			super(
				String.format(
					"The following settings are not allowed for field %s: %s",
					objectFieldName,
					StringUtil.merge(settings, StringPool.COMMA_AND_SPACE)));
		}

	}

	public static class RequiredSettings
		extends ObjectFieldSettingsValidationException {

		public RequiredSettings(String objectFieldName, Set<String> settings) {
			super(
				String.format(
					"The following settings are required for field %s: %s",
					objectFieldName,
					StringUtil.merge(settings, StringPool.COMMA_AND_SPACE)));
		}

	}

}