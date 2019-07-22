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

import com.liferay.data.engine.rest.internal.rule.function.v1_0.util.BaseDataRuleFunctionTest;
import com.liferay.data.engine.rest.internal.rule.function.v1_0.util.constants.DataDefinitionRuleConstants;
import com.liferay.data.engine.spi.rule.function.DataRuleFunction;
import com.liferay.data.engine.spi.rule.function.DataRuleFunctionResult;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcelo Mello
 */
public class EmailAddressDataRuleFunctionTest extends BaseDataRuleFunctionTest {

	@Test
	public void testInvalidEmailAddress1() {
		dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(fieldName, "TEXT");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			getDataRuleFunctionResult();

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(
			DataDefinitionRuleConstants.EMAIL_ADDRESS_IS_INVALID,
			dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testInvalidEmailAddress2() {
		dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(fieldName, "TEXT,test@liferay.com");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			getDataRuleFunctionResult();

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(
			DataDefinitionRuleConstants.EMAIL_ADDRESS_IS_INVALID,
			dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testMultipleEmailAddresses() {
		dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(fieldName, "test1@liferay.com,test2@liferay.com");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			getDataRuleFunctionResult();

		Assert.assertTrue(dataRuleFunctionResult.isValid());
		Assert.assertNull(dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testNullEmailAddress() {
		dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(fieldName, null);
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			getDataRuleFunctionResult();

		Assert.assertFalse(dataRuleFunctionResult.isValid());
		Assert.assertEquals(
			DataDefinitionRuleConstants.EMAIL_ADDRESS_IS_INVALID,
			dataRuleFunctionResult.getErrorCode());
	}

	@Test
	public void testSingleEmailAddress() {
		dataRecord.setDataRecordValues(
			new HashMap() {
				{
					put(fieldName, "test@liferay.com");
				}
			});

		DataRuleFunctionResult dataRuleFunctionResult =
			getDataRuleFunctionResult();

		Assert.assertTrue(dataRuleFunctionResult.isValid());
		Assert.assertNull(dataRuleFunctionResult.getErrorCode());
	}

	@Override
	protected DataRuleFunction getDataRuleFunction() {
		return new EmailAddressDataRuleFunction();
	}

	@Override
	protected String getFieldType() {
		return "text";
	}

}