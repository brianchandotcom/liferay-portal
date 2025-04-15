/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.constants.CommerceCurrencyConstants;
import com.liferay.commerce.currency.exception.CommerceCurrencyRateException;
import com.liferay.commerce.currency.exception.DuplicateCommerceCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.test.util.TestExchangeRateProvider;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommerceCurrencyLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_user.getCompanyId(), _user.getGroupId(), _user.getUserId());

		_serviceContext.setLanguageId("en_US");
	}

	@Test
	public void testAddCommerceCurrency() throws Exception {
		AssertUtils.assertFailure(
			DuplicateCommerceCurrencyException.class, null,
			() -> {
				_commerceCurrency =
					_commerceCurrencyLocalService.addCommerceCurrency(
						null, _user.getUserId(), RandomTestUtil.randomString(3),
						RandomTestUtil.randomLocaleStringMap(),
						RandomTestUtil.randomString(3), BigDecimal.ONE,
						LocalizationUtil.getLocalizationMap(
							CommerceCurrencyConstants.DECIMAL_FORMAT_PATTERN),
						2, 2, "HALF_EVEN", false, 0.0, false);

				_commerceCurrencies.add(_commerceCurrency);

				_commerceCurrencyLocalService.addCommerceCurrency(
					null, _user.getUserId(), _commerceCurrency.getCode(),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomString(3), BigDecimal.ONE,
					LocalizationUtil.getLocalizationMap(
						CommerceCurrencyConstants.DECIMAL_FORMAT_PATTERN),
					2, 2, "HALF_EVEN", false, 0.0, false);
			});

		AssertUtils.assertFailure(
			CommerceCurrencyRateException.class, null,
			() -> _commerceCurrencyLocalService.addCommerceCurrency(
				null, _user.getUserId(), RandomTestUtil.randomString(3),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(3), BigDecimal.ZERO,
				LocalizationUtil.getLocalizationMap(
					CommerceCurrencyConstants.DECIMAL_FORMAT_PATTERN),
				2, 2, "HALF_EVEN", false, 0.0, false));
	}

	@Test
	public void testUpdateCommerceCurrency() throws Exception {
		_commerceCurrency = _commerceCurrencyLocalService.addCommerceCurrency(
			null, _user.getUserId(), RandomTestUtil.randomString(3),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(3), BigDecimal.ONE,
			LocalizationUtil.getLocalizationMap(
				CommerceCurrencyConstants.DECIMAL_FORMAT_PATTERN),
			2, 2, "HALF_EVEN", false, 0.0, false);

		_commerceCurrencies.add(_commerceCurrency);

		AssertUtils.assertFailure(
			CommerceCurrencyRateException.class, null,
			() -> _commerceCurrencyLocalService.updateCommerceCurrency(
				_commerceCurrency.getExternalReferenceCode(),
				_commerceCurrency.getCommerceCurrencyId(),
				_commerceCurrency.getNameMap(), _commerceCurrency.getCode(),
				BigDecimal.ZERO, _commerceCurrency.getFormatPatternMap(),
				_commerceCurrency.getMaxFractionDigits(),
				_commerceCurrency.getMinFractionDigits(),
				_commerceCurrency.getRoundingMode(),
				_commerceCurrency.isPrimary(), _commerceCurrency.getPriority(),
				_commerceCurrency.isActive(), _serviceContext));

		_commerceCurrency = _commerceCurrencyLocalService.addCommerceCurrency(
			null, _user.getUserId(), "FAIL",
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(3), BigDecimal.ONE,
			LocalizationUtil.getLocalizationMap(
				CommerceCurrencyConstants.DECIMAL_FORMAT_PATTERN),
			2, 2, "HALF_EVEN", false, 0.0, true);

		_commerceCurrencies.add(_commerceCurrency);

		BigDecimal oldExchangeRate = _commerceCurrency.getRate();

		_commerceCurrencyLocalService.updateExchangeRate(
			_commerceCurrency.getCommerceCurrencyId(),
			TestExchangeRateProvider.NAME);

		_commerceCurrency = _commerceCurrencyLocalService.getCommerceCurrency(
			_commerceCurrency.getCommerceCurrencyId());

		Assert.assertEquals(oldExchangeRate, _commerceCurrency.getRate());
	}

	private static User _user;

	private final List<CommerceCurrency> _commerceCurrencies =
		new ArrayList<>();
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	private ServiceContext _serviceContext;

}