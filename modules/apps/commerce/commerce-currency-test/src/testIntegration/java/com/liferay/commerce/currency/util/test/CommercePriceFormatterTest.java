/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.math.BigDecimal;

import org.hamcrest.CustomMatcher;
import org.hamcrest.Matcher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommercePriceFormatterTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_price = new BigDecimal(1234560.78);
	}

	@Test
	public void testFormatBigDecimal() throws Exception {
		String regex = "^(\\d*,?\\.?)*\\d\\d$";

		String formattedPrice = _commercePriceFormatter.format(
			_price, LocaleUtil.getDefault());

		Assert.assertTrue(formattedPrice.matches(regex));
	}

	@Test
	public void testFormatCurrencyBigDecimalLocaleFR() throws Exception {
		String regexFR = "^\\d{1,3}(.\\d{3})*,\\d\\d\\s[" + _SYMBOLS + "]$";

		_commerceCurrency.setFormatPattern("###,##0.00 $", LocaleUtil.FRANCE);

		String formattedPrice = _commercePriceFormatter.format(
			_commerceCurrency, _price, LocaleUtil.FRANCE);

		Matcher<String> regexMatcher = new CustomMatcher<String>(
			"Matches regex " + regexFR) {

			public boolean matches(Object object) {
				String s = GetterUtil.getString(object);

				return s.matches(regexFR);
			}

		};

		Assert.assertThat(
			"Formatted price does not match expected pattern", formattedPrice,
			regexMatcher);
	}

	@Test
	public void testFormatCurrencyBigDecimalLocaleIT() throws Exception {
		String regexIT = "^[" + _SYMBOLS + "]\\s\\d{1,3}(\\.\\d{3})*,\\d\\d$";

		_commerceCurrency.setFormatPattern("$ ###,##0.00", LocaleUtil.ITALY);

		String formattedPrice = _commercePriceFormatter.format(
			_commerceCurrency, _price, LocaleUtil.ITALY);

		Matcher<String> regexMatcher = new CustomMatcher<String>(
			"Matches regex " + regexIT) {

			public boolean matches(Object object) {
				String s = GetterUtil.getString(object);

				return s.matches(regexIT);
			}

		};

		Assert.assertThat(
			"Formatted price does not match expected pattern", formattedPrice,
			regexMatcher);
	}

	@Test
	public void testFormatCurrencyBigDecimalLocaleUS() throws Exception {
		String regexUS = "^[" + _SYMBOLS + "]\\d{1,3}(\\,\\d{3})*\\.\\d\\d$";

		_commerceCurrency.setFormatPattern("$###,##0.00", LocaleUtil.US);

		String formattedPrice = _commercePriceFormatter.format(
			_commerceCurrency, _price, LocaleUtil.US);

		Matcher<String> regexMatcher = new CustomMatcher<String>(
			"Matches regex " + regexUS) {

			public boolean matches(Object object) {
				String s = GetterUtil.getString(object);

				return s.matches(regexUS);
			}

		};

		Assert.assertThat(
			"Formatted price does not match expected pattern", formattedPrice,
			regexMatcher);
	}

	private static final String _SYMBOLS = "€$¥£R$₹";

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommercePriceFormatter _commercePriceFormatter;

	private Group _group;
	private BigDecimal _price;

}