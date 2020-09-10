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

package com.liferay.commerce.price.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceRequest;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.test.util.CommerceTaxTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.TestCommerceContext;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Alberti
 */
@RunWith(Arquillian.class)
public class CommerceProductPriceCalculationWithTaxV2Test {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(), 0);

		_commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_company.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_commerceTaxMethod = CommerceTaxTestUtil.addCommerceByAddressTaxMethod(
			_user.getUserId(), _commerceChannel.getGroupId(), true);
	}

	@After
	public void tearDown() throws Exception {
		_commercePriceListLocalService.deleteCommercePriceLists(
			_company.getCompanyId());
	}

	@Test
	public void testGetGrossProductPriceFromGrossPriceList() throws Exception {
		long cpTaxCategoryId = CommerceTaxTestUtil.addTaxCategoryId(
			_user.getGroupId());

		double rate = Double.valueOf(RandomTestUtil.randomInt(0, 40));

		CommerceTaxTestUtil.setCommerceMethodTaxRate(
			_user.getUserId(), _commerceChannel.getGroupId(), cpTaxCategoryId,
			_commerceTaxMethod.getCommerceTaxMethodId(), rate);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				RandomTestUtil.randomString(), _commerceCurrency.getCode(),
				LocaleUtil.US.getDisplayLanguage(), null, _serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		commercePriceList.setNetPrice(false);

		CommercePriceListLocalServiceUtil.updateCommercePriceList(
			commercePriceList);

		CPInstance cpInstance =
			CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
				catalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		cpDefinition.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition);

		BigDecimal price = new BigDecimal("111");

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), "", price);

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		int quantity = 1;

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(quantity);
		commerceProductPriceRequest.setSecure(false);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(null);
		commerceProductPriceRequest.setCalculateTax(true);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		_assertCommerceProductPrice(
			quantity, rate, price, true, commerceProductPrice,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));
	}

	@Test
	public void testGetGrossProductPriceFromNetPriceList() throws Exception {
		long cpTaxCategoryId = CommerceTaxTestUtil.addTaxCategoryId(
			_user.getGroupId());

		double rate = Double.valueOf(RandomTestUtil.randomInt(0, 40));

		CommerceTaxTestUtil.setCommerceMethodTaxRate(
			_user.getUserId(), _commerceChannel.getGroupId(), cpTaxCategoryId,
			_commerceTaxMethod.getCommerceTaxMethodId(), rate);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				RandomTestUtil.randomString(), _commerceCurrency.getCode(),
				LocaleUtil.US.getDisplayLanguage(), null, _serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				catalog.getGroupId(), 0.0);

		CPInstance cpInstance =
			CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
				catalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		cpDefinition.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition.setTaxExempt(false);

		_cpDefinitionLocalService.updateCPDefinition(cpDefinition);

		BigDecimal price = new BigDecimal("111");

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), "", price);

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		int quantity = 1;

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(
			cpInstance.getCPInstanceId());
		commerceProductPriceRequest.setQuantity(quantity);
		commerceProductPriceRequest.setSecure(false);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(null);
		commerceProductPriceRequest.setCalculateTax(true);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		_assertCommerceProductPrice(
			quantity, rate, price, false, commerceProductPrice,
			RoundingMode.valueOf(_commerceCurrency.getRoundingMode()));
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private void _assertCommerceProductPrice(
		int quantity, double rate, BigDecimal price, boolean includeTax,
		CommerceProductPrice commerceProductPrice, RoundingMode roundingMode) {

		BigDecimal taxRate = BigDecimal.valueOf(rate);

		CommerceMoney unitPriceMoney = commerceProductPrice.getUnitPrice();

		BigDecimal unitPrice = unitPriceMoney.getPrice();

		CommerceMoney unitPriceMoneyWithTaxAmount =
			commerceProductPrice.getUnitPriceWithTaxAmount();

		Assert.assertEquals(
			unitPriceMoney.isEmpty(), unitPriceMoneyWithTaxAmount.isEmpty());

		BigDecimal unitPriceWithTaxAmount =
			unitPriceMoneyWithTaxAmount.getPrice();

		BigDecimal expectedUnitPrice =
			CommerceTaxTestUtil.getPriceWithTaxAmount(
				unitPrice, taxRate, roundingMode);

		Assert.assertEquals(
			expectedUnitPrice.stripTrailingZeros(),
			unitPriceWithTaxAmount.stripTrailingZeros());

		CommerceMoney promoPriceMoney =
			commerceProductPrice.getUnitPromoPrice();

		BigDecimal promoPrice = promoPriceMoney.getPrice();

		CommerceMoney promoPriceMoneyWithTaxAmount =
			commerceProductPrice.getUnitPromoPriceWithTaxAmount();

		Assert.assertEquals(
			promoPriceMoney.isEmpty(), promoPriceMoneyWithTaxAmount.isEmpty());

		if (!promoPriceMoney.isEmpty()) {
			BigDecimal promoPriceWithTaxAmount =
				promoPriceMoneyWithTaxAmount.getPrice();

			BigDecimal expectedPromoPrice =
				CommerceTaxTestUtil.getPriceWithTaxAmount(
					promoPrice, taxRate, roundingMode);

			Assert.assertEquals(
				expectedPromoPrice.stripTrailingZeros(),
				promoPriceWithTaxAmount.stripTrailingZeros());
		}

		CommerceMoney finaPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finaPriceMoney.getPrice();

		CommerceMoney finaPriceMoneyWithTaxAmount =
			commerceProductPrice.getFinalPriceWithTaxAmount();

		BigDecimal finalPriceWithTaxAmount =
			finaPriceMoneyWithTaxAmount.getPrice();

		BigDecimal expectedFinalPrice =
			CommerceTaxTestUtil.getPriceWithTaxAmount(
				finalPrice, taxRate, roundingMode);

		Assert.assertEquals(
			expectedFinalPrice.stripTrailingZeros(),
			finalPriceWithTaxAmount.stripTrailingZeros());

		BigDecimal expectedPrice = price;

		if (includeTax) {
			expectedPrice = CommerceTaxTestUtil.getPriceWithoutTaxAmount(
				price, taxRate, roundingMode);
		}

		expectedPrice = expectedPrice.multiply(BigDecimal.valueOf(quantity));

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());
	}

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Inject
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@DeleteAfterTestRun
	private CommerceTaxMethod _commerceTaxMethod;

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	private Group _group;
	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}