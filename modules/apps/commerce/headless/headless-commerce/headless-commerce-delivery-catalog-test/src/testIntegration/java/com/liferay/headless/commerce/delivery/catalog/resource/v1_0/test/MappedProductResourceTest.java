/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.delivery.catalog.client.pagination.Page;
import com.liferay.headless.commerce.delivery.catalog.client.pagination.Pagination;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class MappedProductResourceTest
	extends BaseMappedProductResourceTestCase {

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

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();
	}

	@Override
	@Test
	public void testGetChannelProductMappedProductsPage() throws Exception {
		super.testGetChannelProductMappedProductsPage();

		_testGetChannelProductMappedProductsPageWithUnitOfMeasure();
	}

	@Override
	protected MappedProduct randomMappedProduct() throws Exception {
		return new MappedProduct() {
			{
				id = RandomTestUtil.randomLong();
				productExternalReferenceCode =
					_cProduct.getExternalReferenceCode();
				productId = _cProduct.getCProductId();
				purchasable = RandomTestUtil.randomBoolean();
				quantity = RandomTestUtil.randomInt();
				replacementMessage = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				sequence = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				sku = _cpInstance.getSku();
				skuExternalReferenceCode =
					_cpInstance.getExternalReferenceCode();
				skuId = _cpInstance.getCPInstanceId();
				thumbnail = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected MappedProduct
			testGetChannelProductMappedProductsPage_addMappedProduct(
				Long channelId, Long productId, MappedProduct mappedProduct)
		throws Exception {

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryLocalService.addCSDiagramEntry(
				_user.getUserId(), _cpDefinition.getCPDefinitionId(),
				mappedProduct.getSkuId(), mappedProduct.getProductId(), true,
				mappedProduct.getQuantity(), mappedProduct.getSequence(),
				mappedProduct.getSku(), _serviceContext);

		_csDiagramEntries.add(csDiagramEntry);

		return new MappedProduct() {
			{
				id = csDiagramEntry.getCSDiagramEntryId();
				productExternalReferenceCode =
					mappedProduct.getProductExternalReferenceCode();
				productId = mappedProduct.getProductId();
				purchasable = _cpInstance.isPurchasable();
				sequence = csDiagramEntry.getSequence();
				sku = _cpInstance.getSku();
				skuExternalReferenceCode =
					_cpInstance.getExternalReferenceCode();
				skuId = _cpInstance.getCPInstanceId();
			}
		};
	}

	@Override
	protected Long testGetChannelProductMappedProductsPage_getChannelId()
		throws Exception {

		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Long testGetChannelProductMappedProductsPage_getProductId()
		throws Exception {

		return _cpDefinition.getCProductId();
	}

	private void _testGetChannelProductMappedProductsPageWithUnitOfMeasure()
		throws Exception {

		String unitOfMeasureKey = RandomTestUtil.randomString();

		CPTestUtil.addCPInstanceUnitOfMeasure(
			testGroup.getGroupId(), _cpInstance.getCPInstanceId(),
			unitOfMeasureKey, BigDecimal.ONE, _cpInstance.getSku());

		Long channelId = testGetChannelProductMappedProductsPage_getChannelId();
		Long productId = testGetChannelProductMappedProductsPage_getProductId();

		testGetChannelProductMappedProductsPage_addMappedProduct(
			channelId, productId, randomMappedProduct());

		Page<MappedProduct> page =
			mappedProductResource.getChannelProductMappedProductsPage(
				channelId, productId, null, null, null, Pagination.of(1, 10),
				null);

		Assert.assertTrue(page.getTotalCount() > 0);

		for (MappedProduct mappedProduct : page.getItems()) {
			SkuUnitOfMeasure[] skuUnitOfMeasures =
				mappedProduct.getSkuUnitOfMeasures();

			Assert.assertEquals(
				Arrays.toString(skuUnitOfMeasures), 1,
				skuUnitOfMeasures.length);
			Assert.assertEquals(
				unitOfMeasureKey, skuUnitOfMeasures[0].getKey());
		}
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

	@DeleteAfterTestRun
	private final List<CSDiagramEntry> _csDiagramEntries = new ArrayList<>();

	@Inject
	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}