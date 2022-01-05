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
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Currency;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class CommercePriceEntryTableReferenceDefinitionTest
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

		_cpInstance = CPTestUtil.addCPInstance(group.getGroupId());

		List<CommerceCatalog> commerceCatalogs =
			_commerceCatalogLocalService.getCommerceCatalogs(
				group.getCompanyId(), true);

		CommerceCatalog commerceCatalog = commerceCatalogs.get(0);

		Currency currency = Currency.getInstance(LocaleUtil.US);

		_commercePriceList = CommercePriceListTestUtil.addCommercePriceList(
			null, commerceCatalog.getGroupId(), currency.getCurrencyCode(),
			RandomTestUtil.randomString(), RandomTestUtil.randomDouble(), true,
			null, null);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return CommercePriceEntryTestUtil.addCommercePriceEntry(
			null, _cpInstance.getCPInstanceId(),
			_commercePriceList.getCommercePriceListId(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble());
	}

	@Inject
	private static CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommercePriceList _commercePriceList;
	private CPInstance _cpInstance;

}