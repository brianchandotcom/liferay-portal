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
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.After;
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
@Sync
public class CommercePriceListIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);

		_indexer = _indexerRegistry.getIndexer(CommercePriceList.class);
	}

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(), 0);
	}

	@After
	public void tearDown() throws Exception {
		_commercePriceListLocalService.deleteCommercePriceLists(
			_company.getCompanyId());
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
			CommerceCurrencyTestUtil.addCommerceCurrency(
				_company.getCompanyId());

		User defaultUser = _company.getDefaultUser();

		CommerceCatalog commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			_company.getCompanyId(), _group.getGroupId(),
			defaultUser.getUserId(), commerceCurrency.getCode());

		_commercePriceListLocalService.addCommercePriceList(
			null, commerceCatalog.getGroupId(), _user.getUserId(),
			commerceCurrency.getCommerceCurrencyId(), true,
			CommercePriceListConstants.TYPE_PRICE_LIST, 0, false,
			RandomTestUtil.randomString(), 0, 1, 1, 2018, 3, 4, 0, 0, 0, 0, 0,
			true,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		List<CommercePriceList> commercePriceLists =
			_commercePriceListLocalService.getCommercePriceLists(
				new long[] {commerceCatalog.getGroupId()},
				_company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commercePriceLists.toString(), 3, commercePriceLists.size());

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setEntryClassNames(
			new String[] {CommercePriceList.class.getName()});
		searchContext.setGroupIds(new long[] {commerceCatalog.getGroupId()});

		Hits hits = _indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), 1, hits.getLength());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static Company _company;
	private static Indexer<CommercePriceList> _indexer;

	@Inject
	private static IndexerRegistry _indexerRegistry;

	private static User _user;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	private Group _group;

}