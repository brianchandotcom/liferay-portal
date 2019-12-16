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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.field.type.BaseFieldType;
import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertiesUtil;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.data.domain=number",
		"data.engine.field.type.description=numeric-field-type-description",
		"data.engine.field.type.display.order:Integer=7",
		"data.engine.field.type.group=basic",
		"data.engine.field.type.icon=caret-double",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/Numeric/Numeric.es",
		"data.engine.field.type.label=numeric-field-type-label"
	},
	service = FieldType.class
)
public class NumericFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			fieldTypeTracker, jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put("dataType", jsonObject.getString("dataType"));
		customProperties.put(
			"placeholder",
			LocalizedValueUtil.toLocalizedValues(
				jsonObject.getJSONObject("placeholder")));
		customProperties.put(
			"tooltip",
			LocalizedValueUtil.toLocalizedValues(
				jsonObject.getJSONObject("tooltip")));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "numeric";
	}

	@Override
	public JSONObject toJSONObject(
			FieldTypeTracker fieldTypeTracker,
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(
			fieldTypeTracker, spiDataDefinitionField);

		jsonObject.put(
			"dataType",
			MapUtil.getString(
				spiDataDefinitionField.getCustomProperties(), "dataType")
		).put(
			"placeholder",
			LocalizedValueUtil.toJSONObject(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(),
					"placeholder"))
		).put(
			"tooltip",
			LocalizedValueUtil.toJSONObject(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(), "tooltip"))
		);

		return jsonObject;
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		context.put(
			"dataType",
			MapUtil.getString(
				spiDataDefinitionField.getCustomProperties(), "dataType",
				"decimal"));
		context.put(
			"placeholder",
			MapUtil.getString(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(),
					"placeholder"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put("symbols", _getSymbols(httpServletRequest));
		context.put(
			"tooltip",
			MapUtil.getString(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(),
					"placeholder"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"value",
			_format(
				MapUtil.getString(
					spiDataDefinitionField.getCustomProperties(), "value"),
				httpServletRequest));
	}

	private String _format(
		Object value, HttpServletRequest httpServletRequest) {

		if (Validator.isNull(value) ||
			StringUtil.equals((String)value, "NaN")) {

			return StringPool.BLANK;
		}

		DecimalFormat decimalFormat = _getDecimalFormat(httpServletRequest);

		return decimalFormat.format(GetterUtil.getNumber(value));
	}

	private DecimalFormat _getDecimalFormat(
		HttpServletRequest httpServletRequest) {

		DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance(
			httpServletRequest.getLocale());

		decimalFormat.setGroupingUsed(false);
		decimalFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
		decimalFormat.setParseBigDecimal(true);

		return decimalFormat;
	}

	private Map<String, String> _getSymbols(
		HttpServletRequest httpServletRequest) {

		DecimalFormat decimalFormat = _getDecimalFormat(httpServletRequest);

		DecimalFormatSymbols decimalFormatSymbols =
			decimalFormat.getDecimalFormatSymbols();

		return new HashMap<String, String>() {
			{
				put(
					"decimalSymbol",
					String.valueOf(decimalFormatSymbols.getDecimalSeparator()));
				put(
					"thousandsSeparator",
					String.valueOf(
						decimalFormatSymbols.getGroupingSeparator()));
			}
		};
	}

}