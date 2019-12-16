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
import org.junit.Test;

/**
 * @author Marcelo Mello
 */
public class EmailAddressDataRuleFunctionTest {

	@Test
	public void testInvalidEmailAddress1() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put("fieldName", "TEXT");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(_ERROR_CODE, dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testInvalidEmailAddress2() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put("fieldName", "TEXT,test@liferay.com");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(_ERROR_CODE, dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testMultipleEmailAddresses() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put("fieldName", "test1@liferay.com,test2@liferay.com");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertTrue(dataRuleFunctionResult.isValid());
		Assert.assertNull(dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testNullEmailAddress() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put("fieldName", null);
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(_ERROR_CODE, dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testSingleEmailAddress() {
		_dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put("fieldName", "test@liferay.com");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionTestUtil.validateDataRuleFunction(
				_dataRecord, _dataRuleFunction, _FIELD_TYPE);

		Assert.assertTrue(dataRuleFunctionResult.isValid());
		Assert.assertNull(dataRuleFunctionResult.getErrorCode());
	}

	private static final String _ERROR_CODE = "email-address-is-invalid";

	private static final String _FIELD_TYPE = "text";

	private final DataRecord _dataRecord = new DataRecord();
	private final DataRuleFunction _dataRuleFunction =
		new EmailAddressDataRuleFunction();

}