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

package com.liferay.dynamic.data.mapping.util;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormInstanceFactory {

	public static <T> T create(Class<T> clazz, DDMFormValues ddmFormValues) {
		return create(clazz, ddmFormValues, ddmFormValues.getDefaultLocale());
	}

	public static <T> T create(
		Class<T> clazz, DDMFormValues ddmFormValues, Locale locale) {

		DDMForm ddmForm = DDMFormFactory.create(clazz);

		if (!ddmForm.equals(ddmFormValues.getDDMForm())) {
			throw new IllegalArgumentException(
				"DDM form values does not match with the given class " + clazz);
		}

		DDMFormInstanceFactoryHelper ddmFormInstanceFactoryHelper =
			new DDMFormInstanceFactoryHelper(clazz, ddmFormValues, locale);

		Map<String, Object> properties =
			ddmFormInstanceFactoryHelper.getProperties();

		return Configurable.createConfigurable(clazz, properties);
	}

}