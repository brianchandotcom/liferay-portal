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

package com.liferay.data.engine.rest.internal.rule.function.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionFieldUtil;
import com.liferay.data.engine.rule.function.DataRuleFunction;
import com.liferay.data.engine.rule.function.DataRuleFunctionResult;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcelo Mello
 */
public class DataRuleFunctionTestUtil {

	public static DataRuleFunctionResult validateDataRuleFunction(
		DataRecord dataRecord, DataRuleFunction dataRuleFunction,
		String fieldType) {

		return validateDataRuleFunction(
			new HashMap<>(), dataRecord, dataRuleFunction, fieldType);
	}

	public static DataRuleFunctionResult validateDataRuleFunction(
		Map<String, Object> dataDefinitionRuleParameters, DataRecord dataRecord,
		DataRuleFunction dataRuleFunction, String fieldType) {

		DataDefinitionField dataDefinitionField = _randomDataDefinitionField(
			fieldType);

		return dataRuleFunction.validate(
			dataDefinitionRuleParameters,
			DataDefinitionFieldUtil.toSPIDataDefinitionField(
				dataDefinitionField),
			dataRecord.getDataRecordValues(
			).get(
				dataDefinitionField.getName()
			));
	}

	private static DataDefinitionField _randomDataDefinitionField(
		String fieldType) {

		DataDefinitionField dataDefinitionField = new DataDefinitionField() {
			{
				id = RandomTestUtil.randomLong();
				indexable = false;
				label = new HashMap();
				localizable = false;
				name = "fieldName";
				repeatable = false;
				tip = new HashMap();
			}
		};

		dataDefinitionField.setFieldType(fieldType);

		return dataDefinitionField;
	}

}