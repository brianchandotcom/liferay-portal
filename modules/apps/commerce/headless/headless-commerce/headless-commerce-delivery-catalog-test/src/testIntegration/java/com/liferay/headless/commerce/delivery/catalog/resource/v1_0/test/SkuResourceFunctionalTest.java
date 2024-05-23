/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.price.list.CommercePriceListTestUtil;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.delivery.catalog.client.resource.v1_0.SkuResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class SkuResourceFunctionalTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_skuResource.setContextCompany(testCompany);

		SkuResource.Builder builder = SkuResource.builder();

		skuResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();

		User user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			user.getUserId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				testCompany.getCompanyId());

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			testGroup.getCompanyId(), testGroup.getGroupId(), user.getUserId(),
			commerceCurrency.getCode());
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testAllowMultiplePriceEntriesInTheSamePriceList()
		throws Exception {

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_commerceCatalog.getGroupId(), false,
				CommercePriceListConstants.TYPE_PRICE_LIST, 1.0);

		Calendar calendar = new GregorianCalendar();

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
				RandomTestUtil.randomString(), cpDefinition.getCProductId(),
				cpInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), true, null, null,
				null, null, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR) + 1, calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE), false, BigDecimal.ONE, false,
				BigDecimal.ONE, StringPool.BLANK, _serviceContext);

		_serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
			RandomTestUtil.randomString(), cpDefinition.getCProductId(),
			cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), true, null, null, null,
			null, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR) - 1, calendar.get(Calendar.HOUR),
			calendar.get(Calendar.MINUTE), false, BigDecimal.TEN, false,
			BigDecimal.TEN, StringPool.BLANK, _serviceContext);

		Sku channelProductSku = skuResource.getChannelProductSku(
			_commerceChannel.getCommerceChannelId(),
			cpDefinition.getCProductId(), cpInstance.getCPInstanceId(), -1L);

		Price price = channelProductSku.getPrice();

		BigDecimal commercePriceEntryPrice = commercePriceEntry.getPrice();

		Assert.assertTrue(
			Objects.equals(
				price.getPrice(), commercePriceEntryPrice.doubleValue()));
	}

	@Test
	public void testAllowMultiplePriceEntriesInTheSamePromotion()
		throws Exception {

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommercePriceList catalogBaseCommercePriceList =
			CommercePriceListLocalServiceUtil.
				getCatalogBaseCommercePriceListByType(
					_commerceCatalog.getGroupId(),
					CommercePriceListConstants.TYPE_PRICE_LIST);

		Calendar calendar = new GregorianCalendar();

		CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
			RandomTestUtil.randomString(), cpDefinition.getCProductId(),
			cpInstance.getCPInstanceUuid(),
			catalogBaseCommercePriceList.getCommercePriceListId(), true, null,
			null, null, null, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR) + 1, calendar.get(Calendar.HOUR),
			calendar.get(Calendar.MINUTE), false, new BigDecimal(100), false,
			BigDecimal.ONE, StringPool.BLANK, _serviceContext);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_commerceCatalog.getGroupId(), false,
				CommercePriceListConstants.TYPE_PROMOTION, 1.0);

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
				RandomTestUtil.randomString(), cpDefinition.getCProductId(),
				cpInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), true, null, null,
				null, null, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR) + 1, calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE), false, BigDecimal.ONE, false,
				BigDecimal.ONE, StringPool.BLANK, _serviceContext);

		_serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
			RandomTestUtil.randomString(), cpDefinition.getCProductId(),
			cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), true, null, null, null,
			null, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR) - 1, calendar.get(Calendar.HOUR),
			calendar.get(Calendar.MINUTE), false, BigDecimal.TEN, false,
			BigDecimal.TEN, StringPool.BLANK, _serviceContext);

		Sku channelProductSku = skuResource.getChannelProductSku(
			_commerceChannel.getCommerceChannelId(),
			cpDefinition.getCProductId(), cpInstance.getCPInstanceId(), -1L);

		Price price = channelProductSku.getPrice();

		BigDecimal commercePriceEntryPrice = commercePriceEntry.getPrice();

		Assert.assertTrue(
			Objects.equals(
				price.getPromoPrice(), commercePriceEntryPrice.doubleValue()));
	}

	protected SkuResource skuResource;
	protected Company testCompany;
	protected Group testGroup;

	private CommerceCatalog _commerceCatalog;
	private CommerceChannel _commerceChannel;
	private ServiceContext _serviceContext;

	@Inject
	private
		com.liferay.headless.commerce.delivery.catalog.resource.v1_0.SkuResource
			_skuResource;

}