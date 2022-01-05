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

package com.liferay.commerce.price.list.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class CommercePriceListAccountRelTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(group.getCompanyId());

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(), commerceCurrency.getCode(),
				LocaleUtil.US.getDisplayLanguage(),
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		_commercePriceList = CommercePriceListTestUtil.addCommercePriceList(
			commerceCatalog.getGroupId(), false,
			CommercePriceListConstants.TYPE_PRICE_LIST, 1.0);

		_commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				TestPropsValues.getUserId());
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _commercePriceListAccountRelLocalService.
			addCommercePriceListAccountRel(
				TestPropsValues.getUserId(),
				_commercePriceList.getCommercePriceListId(),
				_commerceAccount.getCommerceAccountId(), 0,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Inject
	private static CommerceAccountLocalService _commerceAccountLocalService;

	@Inject
	private static CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private static CommercePriceListAccountRelLocalService
		_commercePriceListAccountRelLocalService;

	private CommerceAccount _commerceAccount;
	private CommercePriceList _commercePriceList;

}