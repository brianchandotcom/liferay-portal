/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.account.constants.AccountEntryValidatorConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tancredi Covioli
 */
public class AccountEntryValidatorResultTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBuilder() {
		AccountEntryValidatorResult accountEntryValidatorResult =
			AccountEntryValidatorResult.builder(
				"key"
			).build();

		Assert.assertEquals("key", accountEntryValidatorResult.getKey());
		Assert.assertEquals(
			StringPool.BLANK, accountEntryValidatorResult.getActionLabel());
		Assert.assertEquals(
			StringPool.BLANK, accountEntryValidatorResult.getActionURL());
		Assert.assertEquals(
			StringPool.BLANK, accountEntryValidatorResult.getResultMessage());
		Assert.assertEquals(
			Collections.emptyMap(),
			accountEntryValidatorResult.getAdditionalProps());
		Assert.assertEquals(
			AccountEntryValidatorConstants.RESULT_SUCCESS,
			accountEntryValidatorResult.getResultStatus());
		Assert.assertTrue(accountEntryValidatorResult.isValid());

		Map<String, String> additionalProps = HashMapBuilder.put(
			"field", "value"
		).build();

		accountEntryValidatorResult = AccountEntryValidatorResult.builder(
			"key"
		).actionLabel(
			"actionLabel"
		).actionURL(
			"actionURL"
		).additionalProps(
			additionalProps
		).resultMessage(
			"resultMessage"
		).resultStatus(
			AccountEntryValidatorConstants.RESULT_WARNING
		).build();

		Assert.assertEquals("key", accountEntryValidatorResult.getKey());
		Assert.assertEquals(
			"actionLabel", accountEntryValidatorResult.getActionLabel());
		Assert.assertEquals(
			"actionURL", accountEntryValidatorResult.getActionURL());
		Assert.assertEquals(
			additionalProps, accountEntryValidatorResult.getAdditionalProps());
		Assert.assertEquals(
			"resultMessage", accountEntryValidatorResult.getResultMessage());
		Assert.assertEquals(
			AccountEntryValidatorConstants.RESULT_WARNING,
			accountEntryValidatorResult.getResultStatus());
		Assert.assertTrue(accountEntryValidatorResult.isValid());
	}

	@Test
	public void testIsValid() {
		AccountEntryValidatorResult accountEntryValidatorResult =
			AccountEntryValidatorResult.builder(
				"key"
			).resultStatus(
				AccountEntryValidatorConstants.RESULT_FAILURE
			).build();

		Assert.assertFalse(accountEntryValidatorResult.isValid());

		accountEntryValidatorResult = AccountEntryValidatorResult.builder(
			"key"
		).resultStatus(
			AccountEntryValidatorConstants.RESULT_MANUAL
		).build();

		Assert.assertTrue(
			AccountEntryValidatorConstants.RESULT_MANUAL,
			accountEntryValidatorResult.isValid());

		accountEntryValidatorResult = AccountEntryValidatorResult.builder(
			"key"
		).resultStatus(
			AccountEntryValidatorConstants.RESULT_SUCCESS
		).build();

		Assert.assertTrue(
			AccountEntryValidatorConstants.RESULT_SUCCESS,
			accountEntryValidatorResult.isValid());

		accountEntryValidatorResult = AccountEntryValidatorResult.builder(
			"key"
		).resultStatus(
			AccountEntryValidatorConstants.RESULT_WARNING
		).build();

		Assert.assertTrue(
			AccountEntryValidatorConstants.RESULT_WARNING,
			accountEntryValidatorResult.isValid());
	}

}