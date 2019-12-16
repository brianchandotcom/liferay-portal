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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Map;
import java.util.Optional;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionFieldUtil {

	public static Object getLocalizedDefaultValue(
		Map<String, Object> defaultValue, String languageId) {

		try {
			return JSONFactoryUtil.createJSONArray(
				MapUtil.getString(defaultValue, languageId));
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return MapUtil.getString(defaultValue, languageId);
		}
	}

	public static DataDefinitionField toDataDefinitionField(
		SPIDataDefinitionField spiDataDefinitionField) {

		return new DataDefinitionField() {
			{
				customProperties = spiDataDefinitionField.getCustomProperties();
				defaultValue = spiDataDefinitionField.getDefaultValue();
				fieldType = spiDataDefinitionField.getFieldType();
				id = spiDataDefinitionField.getId();
				indexable = spiDataDefinitionField.getIndexable();
				indexType = IndexType.create(
					spiDataDefinitionField.getIndexType());
				label = spiDataDefinitionField.getLabel();
				localizable = spiDataDefinitionField.getLocalizable();
				name = spiDataDefinitionField.getName();
				nestedDataDefinitionFields = TransformUtil.transform(
					spiDataDefinitionField.getNestedSPIDataDefinitionFields(),
					DataDefinitionFieldUtil::toDataDefinitionField,
					DataDefinitionField.class);
				readOnly = spiDataDefinitionField.getReadOnly();
				repeatable = spiDataDefinitionField.getRepeatable();
				required = spiDataDefinitionField.getRequired();
				showLabel = spiDataDefinitionField.getShowLabel();
				tip = spiDataDefinitionField.getTip();
				visible = spiDataDefinitionField.getVisible();
			}
		};
	}

	public static SPIDataDefinitionField toSPIDataDefinitionField(
		DataDefinitionField dataDefinitionField) {

		SPIDataDefinitionField spiDataDefinitionField =
			new SPIDataDefinitionField();

		spiDataDefinitionField.setCustomProperties(
			dataDefinitionField.getCustomProperties());
		spiDataDefinitionField.setDefaultValue(
			dataDefinitionField.getDefaultValue());
		spiDataDefinitionField.setFieldType(dataDefinitionField.getFieldType());
		spiDataDefinitionField.setId(
			GetterUtil.getLong(dataDefinitionField.getId()));
		spiDataDefinitionField.setIndexable(
			GetterUtil.getBoolean(dataDefinitionField.getIndexable()));
		spiDataDefinitionField.setIndexType(
			Optional.ofNullable(
				dataDefinitionField.getIndexType()
			).map(
				DataDefinitionField.IndexType::getValue
			).orElseGet(
				() -> StringPool.BLANK
			));
		spiDataDefinitionField.setLabel(dataDefinitionField.getLabel());
		spiDataDefinitionField.setLocalizable(
			GetterUtil.getBoolean(dataDefinitionField.getLocalizable()));
		spiDataDefinitionField.setName(dataDefinitionField.getName());

		if (!ArrayUtil.isEmpty(
				dataDefinitionField.getNestedDataDefinitionFields())) {

			spiDataDefinitionField.setNestedSPIDataDefinitionFields(
				TransformUtil.transform(
					dataDefinitionField.getNestedDataDefinitionFields(),
					DataDefinitionFieldUtil::toSPIDataDefinitionField,
					SPIDataDefinitionField.class));
		}

		spiDataDefinitionField.setReadOnly(
			GetterUtil.getBoolean(dataDefinitionField.getReadOnly()));
		spiDataDefinitionField.setRepeatable(
			GetterUtil.getBoolean(dataDefinitionField.getRepeatable()));
		spiDataDefinitionField.setRequired(
			GetterUtil.getBoolean(dataDefinitionField.getRequired()));
		spiDataDefinitionField.setShowlabel(
			GetterUtil.getBoolean(dataDefinitionField.getShowLabel(), true));
		spiDataDefinitionField.setTip(dataDefinitionField.getTip());
		spiDataDefinitionField.setVisible(
			GetterUtil.getBoolean(dataDefinitionField.getVisible(), true));

		return spiDataDefinitionField;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionFieldUtil.class);

}