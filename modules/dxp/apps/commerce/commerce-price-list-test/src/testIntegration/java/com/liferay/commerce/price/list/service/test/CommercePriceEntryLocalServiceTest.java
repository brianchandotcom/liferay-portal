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

import static org.hamcrest.CoreMatchers.equalTo;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.price.list.exception.DuplicateCommercePriceEntryException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.math.BigDecimal;

import java.util.Currency;
import java.util.Locale;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class CommercePriceEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
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

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), null, price,
				promoPrice);

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

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		String externalReferenceCode = RandomTestUtil.randomString();
		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(),
				externalReferenceCode, price, promoPrice);

		_assertPriceEntryAttributes(
			cpInstance, price, promoPrice, commercePriceEntry);
		Assert.assertThat(
			externalReferenceCode,
			equalTo(commercePriceEntry.getExternalReferenceCode()));
	}

	@Test
	public void testUpsertCommercePriceEntry1() throws Exception {
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

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.upsertCommercePriceEntry(
				0L, cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), null, null, price,
				promoPrice);

		_assertPriceEntryAttributes(
			cpInstance, price, promoPrice, commercePriceEntry);
	}

	@Test
	public void testUpsertCommercePriceEntry2() throws Exception {
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

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		String externalReferenceCode = RandomTestUtil.randomString();
		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntryTestUtil.upsertCommercePriceEntry(
			0L, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), externalReferenceCode,
			null, price, promoPrice);

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchByExternalReferenceCode(
				externalReferenceCode);

		_assertPriceEntryAttributes(
			cpInstance, price, promoPrice, commercePriceEntry);
	}

	@Test
	public void testUpsertCommercePriceEntry3() throws Exception {
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

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), null, price,
				promoPrice);

		long commercePriceEntryId =
			commercePriceEntry.getCommercePriceEntryId();

		double updatedPrice = RandomTestUtil.randomDouble();
		double updatedPromoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntryTestUtil.upsertCommercePriceEntry(
			commercePriceEntryId, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), null, null,
			updatedPrice, updatedPromoPrice);

		CommercePriceEntry updatedCommercePriceEntry =
			_commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_assertPriceEntryAttributes(
			cpInstance, updatedPrice, updatedPromoPrice,
			updatedCommercePriceEntry);
	}

	@Test(expected = DuplicateCommercePriceEntryException.class)
	public void testUpsertCommercePriceEntry4() throws Exception {
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

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), null, price,
			promoPrice);

		CommercePriceEntryTestUtil.upsertCommercePriceEntry(
			0L, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), null, null, price,
			promoPrice);
	}

	@Test(expected = NoSuchCPInstanceException.class)
	public void testUpsertCommercePriceEntry5() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price Entry on a Price List where the referred SKU " +
				"doesn't exist"
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

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		long cpInstanceId = RandomTestUtil.randomInt();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		CommercePriceEntryTestUtil.upsertCommercePriceEntry(
			0L, cpInstanceId, commercePriceList.getCommercePriceListId(), null,
			null, price, promoPrice);
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private static void _assertPriceEntryAttributes(
			CPInstance cpInstance, double price, double promoPrice,
			CommercePriceEntry commercePriceEntry)
		throws PortalException {

		CPInstance actualCpInstance = commercePriceEntry.getCPInstance();

		Assert.assertThat(
			cpInstance.getCPInstanceId(),
			equalTo(actualCpInstance.getCPInstanceId()));

		BigDecimal actualPrice = commercePriceEntry.getPrice();
		BigDecimal actualPromoPrice = commercePriceEntry.getPromoPrice();

		Assert.assertThat(price, equalTo(actualPrice.doubleValue()));
		Assert.assertThat(promoPrice, equalTo(actualPromoPrice.doubleValue()));
	}

	@Inject
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

}