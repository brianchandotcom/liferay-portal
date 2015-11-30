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

package com.liferay.dynamic.data.mapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTrackerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public class DDMDataProviderInstanceImpl
	extends DDMDataProviderInstanceBaseImpl {

	public List<KeyValuePair> getData() throws PortalException {
		DDMDataProvider ddmDataProvider =
			DDMDataProviderTrackerUtil.getDDMDataProvider(getType());

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProvider.getSettings());

		DDMFormValues ddmFormValues =
			DDMFormValuesJSONDeserializerUtil.deserialize(
				ddmForm, getDefinition());

		DDMDataProviderContext ddmDataProviderContext =
			createDDMDataProviderContext(ddmFormValues);

		return ddmDataProvider.getData(ddmDataProviderContext);
	}

	protected DDMDataProviderContext createDDMDataProviderContext(
		DDMFormValues ddmFormValues) {

		Map<String, Object> properties = new HashMap<>();

		Locale defaultLocale = ddmFormValues.getDefaultLocale();

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			Value value = ddmFormFieldValue.getValue();

			properties.put(
				ddmFormFieldValue.getName(), value.getString(defaultLocale));
		}

		return new DDMDataProviderContext(properties);
	}

}