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
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class CommercePriceListLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddCommercePriceList1() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price List"
		).given(
			"A site (group)"
		).and(
			"A currency code expressed with 3-letter ISO 4217 format"
		).and(
			"The name of the new list"
		).when(
			"The name of the Price Lists"
		).and(
			"The currency code is checked against the input data"
		).then(
			"The result should be a new Price List on the given site"
		);

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		_assertPriceListAttributes(currency, name, commercePriceList);
	}

	@Test
	public void testAddCommercePriceList2() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price List with an external reference code"
		).given(
			"A site (group)"
		).and(
			"A currency code expressed with 3-letter ISO 4217 format"
		).and(
			"The name of the new list"
		).and(
			"The external reference code (externalReferenceCode)"
		).when(
			"The name of the Price Lists"
		).and(
			"The currency code"
		).and(
			"The external reference code are checked against the input data"
		).then(
			"The result should be a new Price List on the given site"
		);

		Currency currency = Currency.getInstance(Locale.US);
		String externalReferenceCode = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null,
				externalReferenceCode);

		_assertPriceListAttributes(currency, name, commercePriceList);
		Assert.assertThat(
			externalReferenceCode,
			equalTo(commercePriceList.getExternalReferenceCode()));
	}

	@Test
	public void testUpsertCommercePriceList1() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price List"
		).given(
			"A site (group)"
		).and(
			"A currency code expressed with 3-letter ISO 4217 format"
		).and(
			"The name of the new list"
		).when(
			"The external reference code (externalReferenceCode)"
		).and(
			"(commercePriceListId) is not used in the method invocation"
		).then(
			"The result should be a new Price List on the given site"
		);

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.upsertCommercePriceList(
				_group.getGroupId(), 0L, currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		_assertPriceListAttributes(currency, name, commercePriceList);
	}

	@Test
	public void testUpsertCommercePriceList2() throws Exception {
		frutillaRule.scenario(
			"Update an existing Price List"
		).given(
			"A site (group)"
		).and(
			"A currency code expressed with 3-letter ISO 4217 format"
		).and(
			"The name of the list"
		).when(
			"The external reference code (externalReferenceCode) is used"
		).and(
			"(commercePriceListId) is not used in the method invocation"
		).then(
			"The result should be the updated Price List with the given " +
				"(externalReferenceCode) on the given site"
		);

		Currency currency = Currency.getInstance(Locale.US);
		String externalReferenceCode = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();

		CommercePriceListTestUtil.addCommercePriceList(
			_group.getGroupId(), currency.getCurrencyCode(), name,
			RandomTestUtil.randomDouble(), true, null, null,
			externalReferenceCode);

		Currency updatedCurrency = Currency.getInstance(Locale.UK);
		String updatedName = RandomTestUtil.randomString();

		CommercePriceListTestUtil.upsertCommercePriceList(
			_group.getGroupId(), 0L, updatedCurrency.getCurrencyCode(),
			updatedName, RandomTestUtil.randomDouble(), true, null, null,
			externalReferenceCode);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchByExternalReferenceCode(
				externalReferenceCode);

		_assertPriceListAttributes(
			updatedCurrency, updatedName, commercePriceList);
	}

	@Test
	public void testUpsertCommercePriceList3() throws Exception {
		frutillaRule.scenario(
			"Update an existing Price List"
		).given(
			"A site (group)"
		).and(
			"A currency code expressed with 3-letter ISO 4217 format"
		).and(
			"The name of the new list"
		).when(
			"The external reference code (externalReferenceCode) is not used"
		).and(
			"(commercePriceListId) is used in the method invocation"
		).then(
			"The result should be the updated Price List with the given " +
				"(commercePriceListId) on the given site"
		);

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_group.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		long commercePriceListId = commercePriceList.getCommercePriceListId();

		Currency updatedCurrency = Currency.getInstance(Locale.UK);
		String updatedName = RandomTestUtil.randomString();

		CommercePriceListTestUtil.upsertCommercePriceList(
			_group.getGroupId(), commercePriceListId,
			updatedCurrency.getCurrencyCode(), updatedName,
			RandomTestUtil.randomDouble(), true, null, null, null);

		CommercePriceList updatedCommercePriceList =
			_commercePriceListLocalService.getCommercePriceList(
				commercePriceListId);

		_assertPriceListAttributes(
			updatedCurrency, updatedName, updatedCommercePriceList);
	}

	@Test(expected = NoSuchCurrencyException.class)
	public void testUpsertCommercePriceList4() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price List"
		).given(
			"A site (group)"
		).and(
			"A non-existent currency code on the site"
		).and(
			"The name of the new list"
		).when(
			"The external reference code (externalReferenceCode)"
		).and(
			"(commercePriceListId) is not used in the method invocation"
		).then(
			"CommercePriceListCurrencyException should be thrown"
		);

		Currency currency = Currency.getInstance("KGS");
		String name = RandomTestUtil.randomString();

		CommercePriceListTestUtil.upsertCommercePriceList(
			_group.getGroupId(), 0L, currency.getCurrencyCode(), name,
			RandomTestUtil.randomDouble(), true, null, null, null);
	}

	@Test
	public void testUpsertCommercePriceList5() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price List"
		).given(
			"A site (group)"
		).and(
			"A currency code expressed with 3-letter ISO 4217 format"
		).and(
			"The name of the new list"
		).when(
			"The external reference code (externalReferenceCode) is not used"
		).and(
			"A non-existent but greater than 0 (commercePriceListId) is used " +
				"in the method invocation"
		).then(
			"The result should be a new Price List on the given site"
		);

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.upsertCommercePriceList(
				_group.getGroupId(), 1L, currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		_assertPriceListAttributes(currency, name, commercePriceList);
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private static void _assertPriceListAttributes(
			Currency currency, String name, CommercePriceList commercePriceList)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commercePriceList.getCommerceCurrency();

		String currencyCode = commerceCurrency.getCode();

		Assert.assertThat(
			currency, equalTo(Currency.getInstance(currencyCode)));

		Assert.assertThat(name, equalTo(commercePriceList.getName()));
	}

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@DeleteAfterTestRun
	private Group _group;

}