/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balazs Breier
 */
@RunWith(Arquillian.class)
public class CommerceDiscountExpirationDateTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testViewExpiredDiscountEntry() throws Exception {
		CommerceDiscount commerceDiscount1 =
			_commerceDiscountLocalService.addCommerceDiscount(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				CommerceDiscountConstants.TARGET_PRODUCT_GROUPS, false, null,
				true, BigDecimal.ONE, CommerceDiscountConstants.LEVEL_L1,
				BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO,
				BigDecimal.ZERO, CommerceDiscountConstants.TYPE_PERCENTAGE, 0,
				false, true, 1, 1, 2020, 1, 1, 1, 1, 2020, 1, 1, false,
				ServiceContextTestUtil.getServiceContext());

		commerceDiscount1.setExpirationDate(RandomTestUtil.nextDate());

		commerceDiscount1 =
			_commerceDiscountLocalService.updateCommerceDiscount(
				commerceDiscount1);

		CommerceDiscount commerceDiscount2 =
			_commerceDiscountLocalService.addCommerceDiscount(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				CommerceDiscountConstants.TARGET_PRODUCT_GROUPS, false, null,
				true, BigDecimal.ONE, CommerceDiscountConstants.LEVEL_L1,
				BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO,
				BigDecimal.ZERO, CommerceDiscountConstants.TYPE_PERCENTAGE, 0,
				false, true, 1, 1, 2020, 1, 1, 1, 1, 2020, 1, 1, false,
				ServiceContextTestUtil.getServiceContext());

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, 10);

		commerceDiscount2.setExpirationDate(calendar.getTime());

		commerceDiscount2 =
			_commerceDiscountLocalService.updateCommerceDiscount(
				commerceDiscount2);

		UnsafeRunnable<Exception> unsafeRunnable =
			_schedulerJobConfiguration.getJobExecutorUnsafeRunnable();

		unsafeRunnable.run();

		commerceDiscount1 = _commerceDiscountLocalService.getCommerceDiscount(
			commerceDiscount1.getCommerceDiscountId());

		Assert.assertTrue(commerceDiscount1.isExpired());

		commerceDiscount2 = _commerceDiscountLocalService.getCommerceDiscount(
			commerceDiscount2.getCommerceDiscountId());

		Assert.assertFalse(commerceDiscount2.isExpired());
	}

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@Inject(
		filter = "component.name=com.liferay.commerce.discount.internal.scheduler.CheckCommerceDiscountSchedulerJobConfiguration"
	)
	private SchedulerJobConfiguration _schedulerJobConfiguration;

}