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

package com.liferay.object.field.business.type;

import com.liferay.object.exception.ObjectFieldSettingsValidationException;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcela Cunha
 */
public interface ObjectFieldBusinessType {

	public default Set<String> getAllowedSettings() {
		return Collections.emptySet();
	}

	public String getDBType();

	public String getDDMFormFieldTypeName();

	public default String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	public String getLabel(Locale locale);

	public String getName();

	public default Map<String, Object> getProperties(
		ObjectField objectField,
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		return Collections.emptyMap();
	}

	public default Set<String> getRequiredSettings() {
		return Collections.emptySet();
	}

	public default boolean isVisible() {
		return true;
	}

	public default void validateObjectFieldSettings(
			String objectFieldName, Map<String, String> objectFieldSettings)
		throws PortalException {

		Set<String> settings = new HashSet<>(objectFieldSettings.keySet());

		settings.removeAll(getAllowedSettings());
		settings.removeAll(getRequiredSettings());

		if (!settings.isEmpty()) {
			throw new ObjectFieldSettingsValidationException.NotAllowedSettings(
				objectFieldName, settings);
		}

		settings = new HashSet<>();

		for (String requiredSetting : getRequiredSettings()) {
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