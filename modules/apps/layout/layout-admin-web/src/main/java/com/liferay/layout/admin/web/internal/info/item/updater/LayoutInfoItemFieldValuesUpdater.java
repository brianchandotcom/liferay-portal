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

package com.liferay.layout.admin.web.internal.info.item.updater;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "item.class.name=com.liferay.portal.kernel.model.Layout",
	service = InfoItemFieldValuesUpdater.class
)
public class LayoutInfoItemFieldValuesUpdater
	implements InfoItemFieldValuesUpdater<Layout> {

	@Override
	public Layout updateFromInfoItemFieldValues(
		Layout layout, InfoItemFieldValues infoItemFieldValues) {

		layout.setDescriptionMap(
			_getFieldMap(
				"description", infoItemFieldValues,
				layout.getDescriptionMap()));
		layout.setNameMap(
			_getFieldMap("name", infoItemFieldValues, layout.getNameMap()));
		layout.setTitleMap(
			_getFieldMap("title", infoItemFieldValues, layout.getTitleMap()));

		return _layoutLocalService.updateLayout(layout);
	}

	private Map<Locale, String> _getFieldMap(
		String fieldName, InfoItemFieldValues infoItemFieldValues,
		Map<Locale, String> initialValue) {

		Map<Locale, String> fieldMap = new HashMap<>(initialValue);

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(fieldName);

		InfoLocalizedValue<Object> infoLocalizedValue =
			(InfoLocalizedValue)infoFieldValue.getValue();

		for (Locale locale : infoLocalizedValue.getAvailableLocales()) {
			fieldMap.put(locale, (String)infoLocalizedValue.getValue(locale));
		}

		return fieldMap;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}