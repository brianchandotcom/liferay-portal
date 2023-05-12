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

package com.liferay.commerce.price.list.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.List;

import org.frutilla.FrutillaRule;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 * @author Ethan Bustad
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommercePriceEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@Before
	public void setUp() throws Exception {
		User guestUser = _company.getGuestUser();

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), guestUser.getUserId(), 0);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_company.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		_commercePriceListLocalService.deleteCommercePriceLists(
			_company.getCompanyId());
	}

	@Test
	public void testAddCommercePriceEntry1() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price Entry"
		).given(
			"A Price List"
		).and(
			"A (SKU) CpInstance in a random CpDefinition"
		).and(
			"The SKU of the new entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).when(
			"The SKU (cpInstance) of the Price Entry"
		).and(
			"The price"
		).and(
			"The promo price are checked against the input data"
		).then(
			"The result should be a new Price Entry on the Price List"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		String name = RandomTestUtil.randomString();

		CommerceCatalog commerceCatalog = cpInstance.getCommerceCatalog();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, commerceCatalog.getGroupId(), _commerceCurrency.getCode(),
				name, RandomTestUtil.randomDouble(), true, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				null, cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), price, promoPrice);

		_assertPriceEntryAttributes(
			cpInstance, price, promoPrice, commercePriceEntry);
	}

	@Test
	public void testAddCommercePriceEntry2() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price Entry"
		).given(
			"A Price List"
		).and(
			"A (SKU) CpInstance in a random CpDefinition"
		).and(
			"The SKU of the new entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).when(
			"The SKU (cpInstance) of the Price Entry"
		).and(
			"The price"
		).and(
			"The promo price"
		).and(
			"The external reference code are checked against the input data"
		).then(
			"The result should be a new Price Entry on the Price List"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		String name = RandomTestUtil.randomString();

		CommerceCatalog commerceCatalog = cpInstance.getCommerceCatalog();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, commerceCatalog.getGroupId(), _commerceCurrency.getCode(),
				name, RandomTestUtil.randomDouble(), true, null, null);

		String externalReferenceCode = RandomTestUtil.randomString();
		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				externalReferenceCode, cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), price, promoPrice);

		_assertPriceEntryAttributes(
			cpInstance, price, promoPrice, commercePriceEntry);
		Assert.assertThat(
			externalReferenceCode,
			CoreMatchers.equalTo(
				commercePriceEntry.getExternalReferenceCode()));
	}

	@Test
	public void testAddOrUpdateCommercePriceEntry1() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price Entry"
		).given(
			"A Price List"
		).and(
			"A (SKU) CpInstance in a random CpDefinition"
		).and(
			"The SKU of the new entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).when(
			"The SKU (cpInstance) of the Price Entry"
		).and(
			"The price"
		).and(
			"The promo price are checked against the input data"
		).and(
			"commercePriceEntryId"
		).and(
			"the external references codes (externalReferenceCode, " +
				"skuExternalReferenceCode) are not used"
		).then(
			"The result should be a new Price Entry on the Price List"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(), name,
				RandomTestUtil.randomDouble(), true, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addOrUpdateCommercePriceEntry(
				null, 0L, cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), null, price,
				promoPrice);

		_assertPriceEntryAttributes(
			cpInstance, price, promoPrice, commercePriceEntry);
	}

	@Test
	public void testAddOrUpdateCommercePriceEntry2() throws Exception {
		frutillaRule.scenario(
			"Updating a new Price Entry"
		).given(
			"A Price List"
		).and(
			"A (SKU) CpInstance in a random CpDefinition"
		).and(
			"The SKU of the entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).when(
			"The SKU (cpInstance) of the Price Entry"
		).and(
			"The price"
		).and(
			"The promo price are checked against the input data"
		).and(
			"commercePriceEntryId"
		).and(
			"skuExternalReferenceCode are not used"
		).and(
			"the external references codes (externalReferenceCode) is used"
		).then(
			"The result should be the updated Price Entry on the Price List"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(), name,
				RandomTestUtil.randomDouble(), true, null, null);

		String externalReferenceCode = RandomTestUtil.randomString();
		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntryTestUtil.addOrUpdateCommercePriceEntry(
			externalReferenceCode, 0L, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), null, price,
			promoPrice);

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, _group.getCompanyId());

		_assertPriceEntryAttributes(
			cpInstance, price, promoPrice, commercePriceEntry);
	}

	@Test
	public void testAddOrUpdateCommercePriceEntry3() throws Exception {
		frutillaRule.scenario(
			"Updating a new Price Entry"
		).given(
			"A Price List"
		).and(
			"A (SKU) CpInstance in a random CpDefinition"
		).and(
			"The SKU of the entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).when(
			"The SKU (cpInstance) of the Price Entry"
		).and(
			"The price"
		).and(
			"The promo price are checked against the input data"
		).and(
			"externalReferenceCode"
		).and(
			"skuExternalReferenceCode are not used"
		).and(
			"commercePriceEntryId is used"
		).then(
			"The result should be the updated Price Entry on the Price List"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(), name,
				RandomTestUtil.randomDouble(), true, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				null, cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), price, promoPrice);

		long commercePriceEntryId =
			commercePriceEntry.getCommercePriceEntryId();

		double updatedPrice = RandomTestUtil.randomDouble();
		double updatedPromoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntryTestUtil.addOrUpdateCommercePriceEntry(
			null, commercePriceEntryId, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), null, updatedPrice,
			updatedPromoPrice);

		CommercePriceEntry updatedCommercePriceEntry =
			_commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_assertPriceEntryAttributes(
			cpInstance, updatedPrice, updatedPromoPrice,
			updatedCommercePriceEntry);
	}

	@Test
	public void testAddOrUpdateCommercePriceEntry4() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price Entry on a Price List where the same SKU is " +
				"already present in another entry"
		).given(
			"A Price List"
		).and(
			"A (SKU) CpInstance in a random CpDefinition"
		).and(
			"The SKU of the entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).when(
			"The SKU (cpInstance) of the Price Entry is used already"
		).and(
			"externalReferenceCode"
		).and(
			"skuExternalReferenceCode are not used"
		).and(
			"commercePriceEntryId is not used either"
		).then(
			"The result should be a DuplicateCommercePriceEntryException"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(), name,
				RandomTestUtil.randomDouble(), true, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			null, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), price, promoPrice);

		CommercePriceEntryTestUtil.addOrUpdateCommercePriceEntry(
			null, 0L, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), null, price,
			promoPrice);
	}

	@Test(expected = NoSuchCPInstanceException.class)
	public void testAddOrUpdateCommercePriceEntry5() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price Entry on a Price List where the referred SKU " +
				"does not exist"
		).given(
			"A Price List"
		).and(
			"A non-existent CpInstanceId"
		).and(
			"The SKU of the entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).when(
			"The SKU is not present in Commerce"
		).and(
			"externalReferenceCode"
		).and(
			"skuExternalReferenceCode are not used"
		).and(
			"commercePriceEntryId is used not used either"
		).then(
			"The result should be a NoSuchCPInstanceException"
		);

		long cpInstanceId = RandomTestUtil.randomInt();

		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(), name,
				RandomTestUtil.randomDouble(), true, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntryTestUtil.addOrUpdateCommercePriceEntry(
			null, 0L, cpInstanceId, commercePriceList.getCommercePriceListId(),
			null, price, promoPrice);
	}

	@Test
	public void testFetchCommercePriceEntry1() throws Exception {
		frutillaRule.scenario(
			"Fetching a Price Entry"
		).given(
			"A Price List"
		).and(
			"A Price Entry on that Price List"
		).when(
			"The SKU (cpInstance) of the Price Entry"
		).and(
			"The ID of the Price List are used to fetch a Price Entry"
		).then(
			"The result should be the given Price Entry"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommerceCatalog commerceCatalog = cpInstance.getCommerceCatalog();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, commerceCatalog.getGroupId(), _commerceCurrency.getCode(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				RandomTestUtil.randomString(), cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(),
				RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble());

		CommercePriceEntry fetchedCommercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid());

		Assert.assertThat(
			commercePriceEntry.getCommercePriceEntryId(),
			CoreMatchers.equalTo(
				fetchedCommercePriceEntry.getCommercePriceEntryId()));
	}

	@Test
	public void testFetchCommercePriceEntry2() throws Exception {
		frutillaRule.scenario(
			"Fetching a Price Entry"
		).given(
			"A Price List"
		).when(
			"A random SKU (cpInstance) is used to fetch a Price Entry"
		).then(
			"The result should be null"
		);

		String cpInstanceUuid = RandomTestUtil.randomString();

		List<CommerceCatalog> commerceCatalogs =
			CommerceCatalogLocalServiceUtil.getCommerceCatalogs(
				_group.getCompanyId(), true);

		CommerceCatalog commerceCatalog = commerceCatalogs.get(0);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, commerceCatalog.getGroupId(), _commerceCurrency.getCode(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		Assert.assertNull(
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(), cpInstanceUuid));
	}

	@Test
	public void testFetchCommercePriceEntry3() throws Exception {
		frutillaRule.scenario(
			"Fetching a Price Entry"
		).given(
			"A parent Price List"
		).and(
			"A child Price List"
		).and(
			"A Price Entry on the parent Price List"
		).when(
			"The SKU (cpInstance) of the Price Entry"
		).and(
			"The ID of the child Price List"
		).and(
			"A true useAncestors value are used to fetch a Price Entry"
		).then(
			"The result should be the given Price Entry"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommercePriceList parentCommercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CommercePriceList childCommercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(),
				parentCommercePriceList.getCommercePriceListId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				RandomTestUtil.randomString(), cpInstance.getCPInstanceId(),
				parentCommercePriceList.getCommercePriceListId(),
				RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble());

		CommercePriceEntry fetchedCommercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				childCommercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid(), true);

		Assert.assertThat(
			commercePriceEntry.getCommercePriceEntryId(),
			CoreMatchers.equalTo(
				fetchedCommercePriceEntry.getCommercePriceEntryId()));
	}

	@Test
	public void testFetchCommercePriceEntry4() throws Exception {
		frutillaRule.scenario(
			"Fetching a Price Entry"
		).given(
			"A parent Price List"
		).and(
			"A child Price List"
		).and(
			"A Price Entry on the parent Price List"
		).and(
			"A Price Entry on the child Price List for the same SKU"
		).when(
			"The SKU (cpInstance) of the Price Entries"
		).and(
			"The ID of the child Price List"
		).and(
			"A true useAncestors value are used to fetch a Price Entry"
		).then(
			"The result should be the child Price Entry"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommercePriceList parentCommercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CommercePriceList childCommercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(),
				parentCommercePriceList.getCommercePriceListId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			RandomTestUtil.randomString(), cpInstance.getCPInstanceId(),
			parentCommercePriceList.getCommercePriceListId(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble());

		CommercePriceEntry childCommercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				RandomTestUtil.randomString(), cpInstance.getCPInstanceId(),
				childCommercePriceList.getCommercePriceListId(),
				RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble());

		CommercePriceEntry fetchedCommercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				childCommercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid(), true);

		Assert.assertThat(
			childCommercePriceEntry.getCommercePriceEntryId(),
			CoreMatchers.equalTo(
				fetchedCommercePriceEntry.getCommercePriceEntryId()));
	}

	@Test
	public void testFetchCommercePriceEntry5() throws Exception {
		frutillaRule.scenario(
			"Fetching a Price Entry"
		).given(
			"A parent Price List"
		).and(
			"A child Price List"
		).and(
			"A Price Entry on the parent Price List"
		).when(
			"The SKU (cpInstance) of the Price Entry"
		).and(
			"The ID of the child Price List"
		).and(
			"A false useAncestors value are used to fetch a Price Entry"
		).then(
			"The result should be null"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommercePriceList parentCommercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CommercePriceList childCommercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(),
				parentCommercePriceList.getCommercePriceListId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			RandomTestUtil.randomString(), cpInstance.getCPInstanceId(),
			parentCommercePriceList.getCommercePriceListId(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble());

		CommercePriceEntry fetchedCommercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				childCommercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid(), false);

		Assert.assertNull(fetchedCommercePriceEntry);
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private void _assertPriceEntryAttributes(
			CPInstance cpInstance, double price, double promoPrice,
			CommercePriceEntry commercePriceEntry)
		throws Exception {

		CPInstance actualCPInstance = commercePriceEntry.getCPInstance();

		Assert.assertThat(
			cpInstance.getCPInstanceId(),
			CoreMatchers.equalTo(actualCPInstance.getCPInstanceId()));

		BigDecimal actualPrice = commercePriceEntry.getPrice();
		BigDecimal actualPromoPrice = commercePriceEntry.getPromoPrice();

		Assert.assertThat(
			price, CoreMatchers.equalTo(actualPrice.doubleValue()));
		Assert.assertThat(
			promoPrice, CoreMatchers.equalTo(actualPromoPrice.doubleValue()));
	}

	private static Company _company;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	private Group _group;

}