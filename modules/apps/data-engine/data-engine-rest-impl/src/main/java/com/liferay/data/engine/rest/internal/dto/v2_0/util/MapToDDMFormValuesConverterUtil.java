/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public class MapToDDMFormValuesConverterUtil {

	public static DDMFormValues toDDMFormValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm, Locale locale) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		if (locale == null) {
			for (Locale availableLocale : ddmForm.getAvailableLocales()) {
				ddmFormValues.addAvailableLocale(availableLocale);
			}

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