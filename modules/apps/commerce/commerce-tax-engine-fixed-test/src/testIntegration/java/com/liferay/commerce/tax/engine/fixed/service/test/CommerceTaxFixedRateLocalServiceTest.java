/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateLocalService;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.test.util.CommerceTaxTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.math.BigDecimal;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian I. KIm
 */
@RunWith(Arquillian.class)
public class CommerceTaxFixedRateLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = NoSuchCPTaxCategoryException.class)
	public void testAddCommerceTaxFixedRateWithNoCPTaxCategory()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(group.getCompanyId());

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			group.getGroupId(), commerceCurrency.getCode());

		User user = UserTestUtil.addUser();

		CommerceTaxMethod commerceTaxMethod =
			CommerceTaxTestUtil.addFixedTaxCommerceTaxMethod(
				user.getUserId(), commerceChannel.getGroupId(), false);

		_commerceTaxFixedRateLocalService.addCommerceTaxFixedRate(
			user.getUserId(), commerceChannel.getGroupId(),
			commerceTaxMethod.getCommerceTaxMethodId(), 0,
			BigDecimal.TEN.doubleValue());
	}

	@Inject
	private CommerceTaxFixedRateLocalService _commerceTaxFixedRateLocalService;

}