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

package com.liferay.commerce.price.list.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyServiceUtil;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.search.test.util.HitsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommercePriceListIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_indexer = _indexerRegistry.getIndexer(CommercePriceList.class);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testSearch() throws Exception {
		frutillaRule.scenario(
			"Test price list search"
		).given(
			"I add a price list"
		).when(
			"I search for price lists"
		).then(
			"The result will be 'only one', the price list added"
		);

		CommerceCurrency commerceCurrency =
			CommerceCurrencyServiceUtil.fetchPrimaryCommerceCurrency(
				_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.addCommercePriceList(
				commerceCurrency.getCommerceCurrencyId(),
				RandomTestUtil.randomString(), 0, 1, 1, 2018, 3, 4, 0, 0, 0, 0,
				0, true, serviceContext);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setEntryClassNames(
			new String[] {CommercePriceList.class.getName()});
		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Hits hits = _indexer.search(searchContext);

		Document document = HitsAssert.assertOnlyOne(hits);

		Assert.assertEquals(
			String.valueOf(commercePriceList.getCommercePriceListId()),
			document.get(Field.ENTRY_CLASS_PK));
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static Indexer<CommercePriceList> _indexer;

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@DeleteAfterTestRun
	private Group _group;

}