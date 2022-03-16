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

package com.liferay.object.internal.field.business.type.util;

import com.liferay.object.exception.ObjectFieldSettingsValidationException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Carolina Barbosa
 */
public class ObjectFieldSettingsValidationUtil {

	public static void validate(
			Set<String> allowedSettings, String objectFieldName,
			Map<String, String> objectFieldSettings,
			Set<String> requiredSettings)
		throws PortalException {

		_checkAllowedSettings(
			allowedSettings, objectFieldName, objectFieldSettings);
		_checkRequiredSettings(
			objectFieldName, objectFieldSettings, requiredSettings);
	}

	private static void _checkAllowedSettings(
			Set<String> allowedSettings, String objectFieldName,
			Map<String, String> objectFieldSettings)
		throws PortalException {

		Set<String> settings = new HashSet<>(objectFieldSettings.keySet());

		settings.removeAll(allowedSettings);

		if (!settings.isEmpty()) {
			throw new ObjectFieldSettingsValidationException.NotAllowedSettings(
				objectFieldName, settings);
		}
	}

	private static void _checkRequiredSettings(
			String objectFieldName, Map<String, String> objectFieldSettings,
			Set<String> requiredSettings)
		throws PortalException {

		Set<String> settings = new HashSet<>();

		for (String requiredSetting : requiredSettings) {
			if (Validator.isNull(objectFieldSettings.get(requiredSetting))) {
				settings.add(requiredSetting);
			}
		}

		if (!settings.isEmpty()) {
			throw new ObjectFieldSettingsValidationException.RequiredSettings(
				objectFieldName, settings);
		}
	}

}