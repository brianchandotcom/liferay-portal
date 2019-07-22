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

package com.liferay.data.engine.rest.internal.rule.function.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.internal.rule.function.v1_0.util.DataRuleFunctionTestUtil;
import com.liferay.data.engine.rule.function.DataRuleFunction;
import com.liferay.data.engine.rule.function.DataRuleFunctionResult;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcelo Mello
 */
public class MatchExpressionDataRuleFunctionTest {

	@Before
	public void setUp() {
		_dataRecord = new DataRecord();
	}

	@Test
	public void testInvalidMatch() {
		Map<String, Object> dataDefinitionRuleParameters = new HashMap() {
			{
				put(_EXPRESSION, "\\S+@\\S+\\.\\S+");
			}
		};

		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(DataRuleFunctionTestUtil.FIELD_NAME, "test@liferay");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				dataDefinitionRuleParameters, _dataRecord, _dataRuleFunction,
				_FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(
			_VALUE_MUST_MATCH_EXPRESSION,
			dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testInvalidRegex() {
		Map<String, Object> dataDefinitionRuleParameters = new HashMap() {
			{
				put(_EXPRESSION, "\\\\\\\\S+[@\\\\S+\\\\.\\\\S+");
			}
		};

		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(DataRuleFunctionTestUtil.FIELD_NAME, "test@liferay");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				dataDefinitionRuleParameters, _dataRecord, _dataRuleFunction,
				_FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(
			_VALUE_MUST_MATCH_EXPRESSION,
			dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testValidMatch() {
		Map<String, Object> dataDefinitionRuleParameters = new HashMap() {
			{
				put(_EXPRESSION, "\\S+@\\S+\\.\\S+");
			}
		};

		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(
						DataRuleFunctionTestUtil.FIELD_NAME,
						"test@liferay.com");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				dataDefinitionRuleParameters, _dataRecord, _dataRuleFunction,
				_FIELD_TYPE);

		Assert.assertTrue(dataRuleFunctionResult.isValid());
		Assert.assertNull(dataRuleFunctionResult.getErrorCode());
	}

	private static final String _EXPRESSION = "expression";

	private static final String _FIELD_TYPE = "text";

	private static final String _VALUE_MUST_MATCH_EXPRESSION =
		"value-must-match-expression";

	private DataRecord _dataRecord;
	private final DataRuleFunction _dataRuleFunction =
		new MatchExpressionDataRuleFunction();

}