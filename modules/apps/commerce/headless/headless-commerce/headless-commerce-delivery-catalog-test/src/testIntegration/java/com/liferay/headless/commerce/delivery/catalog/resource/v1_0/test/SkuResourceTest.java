/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.price.list.CommercePriceEntryTestUtil;
import com.liferay.commerce.test.util.price.list.CommercePriceListTestUtil;
import com.liferay.commerce.test.util.price.list.CommerceTierPriceEntryTestUtil;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.TierPrice;
import com.liferay.headless.commerce.delivery.catalog.client.pagination.Page;
import com.liferay.headless.commerce.delivery.catalog.client.pagination.Pagination;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@FeatureFlags("COMMERCE-11287")
@RunWith(Arquillian.class)
public class SkuResourceTest extends BaseSkuResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		_cpDefinition = CPTestUtil.addCPDefinition(
			testGroup.getGroupId(), "simple", false, false);

		_cpDefinitionOptionRel = CPTestUtil.addCPDefinitionOptionRel(
			testGroup.getGroupId(), _cpDefinition.getCPDefinitionId(), true,
			10);
	}

	@Override
	@Test
	public void testGetChannelProductSkusPage() throws Exception {
		super.testGetChannelProductSkusPage();

		_testGetChannelProductSkusPageWithUnitOfMeasure();
		_testGetChannelProductSkusPageWithUnitOfMeasurePrice();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetChannelProductSku() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetChannelProductSkuNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testPostChannelProductSku() {
	}

	@Override
	protected Sku testGetChannelProductSku_addSku() throws Exception {
		return _addCPInstance(randomSku());
	}

	@Override
	protected Long testGetChannelProductSku_getChannelId() throws Exception {
		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Sku testGetChannelProductSkusPage_addSku(
			Long channelId, Long productId, Sku sku)
		throws Exception {

		return _addCPInstance(sku);
	}

	@Override
	protected Long testGetChannelProductSkusPage_getChannelId()
		throws Exception {

		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Long testGetChannelProductSkusPage_getProductId()
		throws Exception {

		return _cpDefinition.getCProductId();
	}

	@Override
	protected Long testGraphQLGetChannelProductSku_getChannelId()
		throws Exception {

		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Sku testGraphQLSku_addSku() throws Exception {
		return _addCPInstance(randomSku());
	}

	@Override
	protected Sku testPostChannelProductSkuBySkuOption_addSku(Sku sku)
		throws Exception {

		return _addCPInstance(sku);
	}

	private Sku _addCPInstance(Sku sku) throws Exception {
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			_cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

		CPInstance cpInstance = _cpInstanceLocalService.addCPInstance(
			RandomTestUtil.randomString(), _cpDefinition.getCPDefinitionId(),
			_cpDefinition.getGroupId(), sku.getSku(), sku.getGtin(),
			sku.getManufacturerPartNumber(), sku.getPurchasable(),
			HashMapBuilder.put(
				_cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
				() -> {
					CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
						cpDefinitionOptionValueRels.get(_cpInstances.size());

					return Collections.singletonList(
						cpDefinitionOptionValueRel.
							getCPDefinitionOptionValueRelId());
				}
			).build(),
			sku.getWidth(), sku.getHeight(), sku.getDepth(), sku.getWeight(),
			BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
			sku.getPublished(), 1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true, false,
			false, 1, null, null, 0, false, 1, null, null, 0,
			RandomTestUtil.randomString(), false, null, 0, 0, 0, 0,
			_serviceContext);

		_cpInstances.add(cpInstance);

		return new Sku() {
			{
				depth = cpInstance.getDepth();
				displayDate = cpInstance.getDisplayDate();
				expirationDate = cpInstance.getExpirationDate();
				gtin = cpInstance.getGtin();
				height = cpInstance.getHeight();
				id = cpInstance.getCPInstanceId();
				manufacturerPartNumber = cpInstance.getManufacturerPartNumber();
				maxOrderQuantity = BigDecimal.ZERO;
				minOrderQuantity = BigDecimal.ZERO;
				neverExpire = true;
				productId = _cpDefinition.getCProductId();
				published = cpInstance.isPublished();
				purchasable = cpInstance.isPurchasable();
				sku = cpInstance.getSku();
				weight = cpInstance.getWeight();
				width = cpInstance.getWidth();
			}
		};
	}

	private void _addCPInstanceUnitOfMeasure(Sku sku, boolean active)
		throws Exception {

		_cpInstanceUnitOfMeasureLocalService.addCPInstanceUnitOfMeasure(
			_user.getUserId(), sku.getId(), active, BigDecimal.ONE,
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomInt(0, 5), true, RandomTestUtil.nextDouble(),
			BigDecimal.ONE, sku.getSku());
	}

	private void _testGetChannelProductSkusPageWithUnitOfMeasure()
		throws Exception {

		Long channelId = testGetChannelProductSkusPage_getChannelId();
		Long productId = testGetChannelProductSkusPage_getProductId();

		Sku sku1 = testGetChannelProductSkusPage_addSku(
			channelId, productId, randomSku());

		_addCPInstanceUnitOfMeasure(sku1, true);
		_addCPInstanceUnitOfMeasure(sku1, true);
		_addCPInstanceUnitOfMeasure(sku1, false);

		Sku sku2 = testGetChannelProductSkusPage_addSku(
			channelId, productId, randomSku());

		_addCPInstanceUnitOfMeasure(sku2, true);

		Sku sku3 = testGetChannelProductSkusPage_addSku(
			channelId, productId, randomSku());

		Page<Sku> page = skuResource.getChannelProductSkusPage(
			channelId, productId, null, Pagination.of(1, 10));

		for (Sku sku : page.getItems()) {
			SkuUnitOfMeasure[] skuUnitOfMeasures = sku.getSkuUnitOfMeasures();

			if (Objects.equals(sku.getId(), sku1.getId())) {
				Assert.assertEquals(
					Arrays.toString(skuUnitOfMeasures), 2,
					skuUnitOfMeasures.length);
			}

			if (Objects.equals(sku.getId(), sku2.getId())) {
				Assert.assertEquals(
					Arrays.toString(skuUnitOfMeasures), 1,
					skuUnitOfMeasures.length);
			}

			if (Objects.equals(sku.getId(), sku3.getId())) {
				Assert.assertEquals(
					Arrays.toString(skuUnitOfMeasures), 0,
					skuUnitOfMeasures.length);
			}
		}
	}

	private void _testGetChannelProductSkusPageWithUnitOfMeasurePrice()
		throws Exception {

		Long channelId = testGetChannelProductSkusPage_getChannelId();
		Long productId = testGetChannelProductSkusPage_getProductId();

		Sku sku1 = testGetChannelProductSkusPage_addSku(
			channelId, productId, randomSku());

		CommerceCatalog commerceCatalog = _cpDefinition.getCommerceCatalog();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				commerceCatalog.getGroupId(), false, "price-list",
				RandomTestUtil.nextDouble());

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			sku1.getId());

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				null, productId, cpInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), BigDecimal.TEN);

		CommerceTierPriceEntryTestUtil.addCommerceTierPriceEntry(
			commercePriceEntry.getCommercePriceEntryId(), 10, 10, 1, null);
		CommerceTierPriceEntryTestUtil.addCommerceTierPriceEntry(
			commercePriceEntry.getCommercePriceEntryId(), 12, 12, 1, null);

		_addCPInstanceUnitOfMeasure(sku1, true);

		Sku sku2 = testGetChannelProductSkusPage_addSku(
			channelId, productId, randomSku());

		_addCPInstanceUnitOfMeasure(sku2, true);

		Page<Sku> page = skuResource.getChannelProductSkusPage(
			channelId, productId, null, Pagination.of(1, 10));

		for (Sku sku : page.getItems()) {
			SkuUnitOfMeasure[] skuUnitOfMeasures = sku.getSkuUnitOfMeasures();

			if (Objects.equals(sku.getId(), sku1.getId())) {
				Assert.assertEquals(
					Arrays.toString(skuUnitOfMeasures), 1,
					skuUnitOfMeasures.length);

				SkuUnitOfMeasure skuUnitOfMeasure = skuUnitOfMeasures[0];

				Price skuUnitOfMeasurePrice = skuUnitOfMeasure.getPrice();

				Assert.assertNotNull(skuUnitOfMeasurePrice);
				Assert.assertTrue(
					BigDecimalUtil.eq(
						commercePriceEntry.getPrice(),
						BigDecimal.valueOf(skuUnitOfMeasurePrice.getPrice())));

				TierPrice[] tierPrices = skuUnitOfMeasure.getTierPrices();

				Assert.assertEquals(
					Arrays.toString(tierPrices), 2, tierPrices.length);
			}

			if (Objects.equals(sku.getId(), sku2.getId())) {
				Assert.assertEquals(
					Arrays.toString(skuUnitOfMeasures), 1,
					skuUnitOfMeasures.length);

				SkuUnitOfMeasure skuUnitOfMeasure = skuUnitOfMeasures[0];

				Assert.assertNull(skuUnitOfMeasure.getPrice());
				Assert.assertNull(skuUnitOfMeasure.getTierPrices());
			}
		}
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPDefinitionOptionRel _cpDefinitionOptionRel;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@DeleteAfterTestRun
	private final List<CPInstance> _cpInstances = new ArrayList<>();

	@Inject
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}