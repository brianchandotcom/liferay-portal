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

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.rest.internal.strategy.DefaultMapToDDMFormValuesConverterStrategy;
import com.liferay.data.engine.rest.internal.strategy.MapToDDMFormValuesConverterStrategy;
import com.liferay.data.engine.rest.internal.strategy.NestedFieldsSupportMapToDDMFormValuesConverterStrategy;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public class MapToDDMFormValuesConverterUtil {

	public static DDMFormValues toDDMFormValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm, Locale locale) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		if (locale == null) {
			Set<Locale> availableLocales = ddmForm.getAvailableLocales();

			Stream<Locale> stream = availableLocales.stream();

			stream.forEach(ddmFormValues::addAvailableLocale);

			ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());
		}
		else {
			ddmFormValues.addAvailableLocale(locale);

			ddmFormValues.setDefaultLocale(locale);
		}

		if (MapUtil.isEmpty(dataRecordValues)) {
			return ddmFormValues;
		}

		MapToDDMFormValuesConverterStrategy
			mapToDDMFormValuesConverterStrategy = null;

		Set<String> keySet = dataRecordValues.keySet();

		Iterator<String> iterator = keySet.iterator();

		String key = iterator.next();

		if (key.contains("_INSTANCE_")) {
			mapToDDMFormValuesConverterStrategy =
				NestedFieldsSupportMapToDDMFormValuesConverterStrategy.
					getInstance();
		}
		else {
			mapToDDMFormValuesConverterStrategy =
				DefaultMapToDDMFormValuesConverterStrategy.getInstance();
		}

		mapToDDMFormValuesConverterStrategy.setDDMFormFieldValues(
			dataRecordValues, ddmForm, ddmFormValues, locale);

		return ddmFormValues;
	}

}