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

package com.liferay.layout.admin.web.internal.info.item.provider;

import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.admin.web.internal.info.item.LayoutInfoItemFields;
import com.liferay.layout.admin.web.internal.util.InfoFieldUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class LayoutInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<Layout> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(Layout layout) {
		return InfoItemFieldValues.builder(
		).infoFieldValue(
			new InfoFieldValue<>(
				LayoutInfoItemFields.nameInfoField,
				InfoLocalizedValue.<String>builder(
				).defaultLocale(
					LocaleUtil.fromLanguageId(layout.getDefaultLanguageId())
				).values(
					layout.getNameMap()
				).build())
		).infoFieldValues(
			_getLayoutInfoFieldValues(layout)
		).infoItemReference(
			new InfoItemReference(Layout.class.getName(), layout.getPlid())
		).build();
	}

	private InfoLocalizedValue<String> _getInfoLocalizedValue(
		JSONObject jsonObject, String name, String defaultLanguageId) {

		for (String processorKey : jsonObject.keySet()) {
			JSONObject processorJSONObject = jsonObject.getJSONObject(
				processorKey);

			if (!processorJSONObject.has(name)) {
				continue;
			}

			JSONObject valuesJSONObject = processorJSONObject.getJSONObject(
				name);

			Map<Locale, String> valuesMap = new HashMap<>();

			for (String languageId : valuesJSONObject.keySet()) {
				if (!languageId.equals("config") &&
					!languageId.equals("defaultValue")) {

					valuesMap.put(
						LocaleUtil.fromLanguageId(languageId),
						valuesJSONObject.getString(languageId));
				}
			}

			valuesMap.computeIfAbsent(
				LocaleUtil.fromLanguageId(defaultLanguageId),
				locale -> valuesJSONObject.getString("defaultValue"));

			return InfoLocalizedValue.<String>builder(
			).values(
				valuesMap
			).build();
		}

		return InfoLocalizedValue.singleValue(StringPool.BLANK);
	}

	private List<InfoFieldValue<Object>> _getLayoutInfoFieldValues(
		Layout layout) {

		try {
			List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

			InfoFieldUtil.forEachInfoField(
				_fragmentRendererController, layout,
				(name, infoField, unsafeSupplier) -> infoFieldValues.add(
					new InfoFieldValue<>(
						infoField,
						_getInfoLocalizedValue(
							unsafeSupplier.get(), name,
							layout.getDefaultLanguageId()))));

			return infoFieldValues;
		}
		catch (JSONException jsonException) {
			return ReflectionUtil.throwException(jsonException);
		}
	}

	@Reference
	private FragmentRendererController _fragmentRendererController;

}