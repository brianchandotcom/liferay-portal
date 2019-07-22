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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcelo Mello
 */
public class EmptyDataRuleFunctionTest {

	@Before
	public void setUp() {
		_dataRecord = new DataRecord();
	}

	@Test
	public void testEmptyFieldName() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(" ", "value");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(
			_VALUE_MUST_NOT_BE_EMPTY, dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testEmptyValue() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(DataRuleFunctionTestUtil.FIELD_NAME, " ");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(
			_VALUE_MUST_NOT_BE_EMPTY, dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testMultipleValues() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(DataRuleFunctionTestUtil.FIELD_NAME, "text1");
					put(DataRuleFunctionTestUtil.FIELD_NAME, "text2");
					put(DataRuleFunctionTestUtil.FIELD_NAME, "text3");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertTrue(dataRuleFunctionResult.isValid());
		Assert.assertNull(dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testNotEmptyValue() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(DataRuleFunctionTestUtil.FIELD_NAME, "text");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertTrue(dataRuleFunctionResult.isValid());
		Assert.assertNull(dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testNullValue() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(DataRuleFunctionTestUtil.FIELD_NAME, null);
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(
			_VALUE_MUST_NOT_BE_EMPTY, dataRuleFunctionResult.getErrorCode());
	}

	private static final String _FIELD_TYPE = "text";

	private static final String _VALUE_MUST_NOT_BE_EMPTY =
		"value-must-not-be-empty";

	private DataRecord _dataRecord;
	private final DataRuleFunction _dataRuleFunction =
		new EmptyDataRuleFunction();

}