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
import com.liferay.data.engine.spi.rule.function.DataRuleFunction;
import com.liferay.data.engine.spi.rule.function.DataRuleFunctionResult;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;

/**
 * @author Marcelo Mello
 */
public class BaseDataRuleFunctionTest {

	@Before
	public void setUp() {
		dataRecord = new DataRecord();
	}

	protected DataRuleFunction getDataRuleFunction() {
		return null;
	}

	protected DataRuleFunctionResult getDataRuleFunctionResult() {
		return getDataRuleFunctionResult(new HashMap<>());
	}

	protected DataRuleFunctionResult getDataRuleFunctionResult(
		Map<String, Object> dataDefinitionRuleParameters) {

		DataRuleFunction dataRuleFunction = getDataRuleFunction();

		DataDefinitionField dataDefinitionField = _randomDataDefinitionField();

		return dataRuleFunction.validate(
			dataDefinitionRuleParameters,
			DataDefinitionFieldUtil.toSPIDataDefinitionField(
				dataDefinitionField),
			dataRecord.getDataRecordValues(
			).get(
				dataDefinitionField.getName()
			));
	}

	protected String getFieldType() {
		return null;
	}

	protected DataRecord dataRecord;
	protected final String fieldName = "name";

	private DataDefinitionField _randomDataDefinitionField() {
		DataDefinitionField dataDefinitionField = new DataDefinitionField() {
			{
				id = RandomTestUtil.randomLong();
				indexable = false;
				label = new HashMap();
				localizable = false;
				name = fieldName;
				repeatable = false;
				tip = new HashMap();
			}
		};

		dataDefinitionField.setFieldType(getFieldType());

		return dataDefinitionField;
	}

}