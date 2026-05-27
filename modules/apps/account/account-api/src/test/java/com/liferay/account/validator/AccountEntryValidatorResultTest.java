/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.account.constants.AccountEntryValidatorResultConstants;
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
	public void testBuilderWithAllValues() {
		Map<String, String> additionalProps = HashMapBuilder.put(
			"field", "value"
		).build();

		AccountEntryValidatorResult accountEntryValidatorResult =
			AccountEntryValidatorResult.builder(
				"key"
			).action(
				"actionLabel", "actionURL"
			).additionalProps(
				additionalProps
			).resultMessage(
				"resultMessage"
			).resultStatus(
				AccountEntryValidatorResultConstants.WARNING
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
			AccountEntryValidatorResultConstants.WARNING,
			accountEntryValidatorResult.getResultStatus());

		Assert.assertTrue(accountEntryValidatorResult.isValid());
	}

	@Test
	public void testBuilderWithKey() {
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
			AccountEntryValidatorResultConstants.SUCCESS,
			accountEntryValidatorResult.getResultStatus());

		Assert.assertTrue(accountEntryValidatorResult.isValid());
	}

	@Test
	public void testIsValid() {
		for (String resultStatus :
				new String[] {
					AccountEntryValidatorResultConstants.MANUAL,
					AccountEntryValidatorResultConstants.SUCCESS,
					AccountEntryValidatorResultConstants.WARNING
				}) {

			AccountEntryValidatorResult accountEntryValidatorResult =
				AccountEntryValidatorResult.builder(
					"key"
				).resultStatus(
					resultStatus
				).build();

			Assert.assertTrue(
				resultStatus, accountEntryValidatorResult.isValid());
		}

		AccountEntryValidatorResult accountEntryValidatorResult =
			AccountEntryValidatorResult.builder(
				"key"
			).resultStatus(
				AccountEntryValidatorResultConstants.FAILURE
			).build();

		Assert.assertFalse(accountEntryValidatorResult.isValid());
	}

}