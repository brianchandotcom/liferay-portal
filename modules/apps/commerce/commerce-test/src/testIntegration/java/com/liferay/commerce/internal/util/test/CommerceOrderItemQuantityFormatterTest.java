/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.util.CommerceOrderItemQuantityFormatter;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian I. Kim
 */
@RunWith(Arquillian.class)
@Sync
public class CommerceOrderItemQuantityFormatterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testParsePriceBigDecimal() throws Exception {
		String expectedParsedQuantity = "1234567.890";

		Assert.assertEquals(
			expectedParsedQuantity,
			_commerceOrderItemQuantityFormatter.parse(
				null, "1,234,567.890", LocaleUtil.ITALY
			).toString());
		Assert.assertEquals(
			expectedParsedQuantity,
			_commerceOrderItemQuantityFormatter.parse(
				null, "1.234.567,890", LocaleUtil.ITALY
			).toString());
		Assert.assertEquals(
			expectedParsedQuantity,
			_commerceOrderItemQuantityFormatter.parse(
				null, "1234567,890", LocaleUtil.ITALY
			).toString());
		Assert.assertEquals(
			expectedParsedQuantity,
			_commerceOrderItemQuantityFormatter.parse(
				null, "1234567.890", LocaleUtil.ITALY
			).toString());
		Assert.assertNotEquals(
			expectedParsedQuantity,
			_commerceOrderItemQuantityFormatter.parse(
				null, "1,234,0", LocaleUtil.ITALY
			).toString());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private CommerceOrderItemQuantityFormatter
		_commerceOrderItemQuantityFormatter;

}