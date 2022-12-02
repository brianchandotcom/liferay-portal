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

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class SkuResourceTest extends BaseSkuResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();

		_cpInstanceLocalService.deleteCPInstances(
			_cpDefinition.getCPDefinitionId());
	}

	@Ignore
	@Override
	@Test
	public void testGetSkusPage() throws Exception {
		super.testGetSkusPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSkusPageWithPagination() throws Exception {
		super.testGetSkusPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSkusPageWithSortInteger() throws Exception {
		super.testGetSkusPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteSku() throws Exception {
		super.testGraphQLDeleteSku();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSkusPage() throws Exception {
		super.testGraphQLGetSkusPage();
	}

	@Override
	@Test
	public void testPatchSku() throws Exception {
		Sku postSku = skuResource.postProductIdSku(
			_cProduct.getCProductId(), randomSku());

		Sku randomPatchSku = randomPatchSku();

		skuResource.patchSku(postSku.getId(), randomPatchSku);

		Sku expectedSku = postSku.clone();

		BeanTestUtil.copyProperties(randomPatchSku, expectedSku);

		Sku getSku = skuResource.getSku(postSku.getId());

		assertEquals(expectedSku, getSku);
		assertValid(getSku);
	}

	@Override
	@Test
	public void testPatchSkuByExternalReferenceCode() throws Exception {
		Sku postSku = skuResource.postProductByExternalReferenceCodeSku(
			_cProduct.getExternalReferenceCode(), randomSku());

		Sku randomPatchSku = randomPatchSku();

		skuResource.patchSkuByExternalReferenceCode(
			postSku.getExternalReferenceCode(), randomPatchSku);

		Sku expectedSku = postSku.clone();

		BeanTestUtil.copyProperties(randomPatchSku, expectedSku);

		Sku getSku = skuResource.getSku(postSku.getId());

		assertEquals(expectedSku, getSku);
		assertValid(getSku);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"depth", "discontinued", "gtin", "height", "manufacturerPartNumber",
			"published", "purchasable", "sku", "unspsc", "weight", "width"
		};
	}

	@Override
	protected Sku randomSku() throws Exception {
		return new Sku() {
			{
				depth = RandomTestUtil.randomDouble();
				discontinued = false;
				discontinuedDate = RandomTestUtil.nextDate();
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				gtin = StringUtil.toLowerCase(RandomTestUtil.randomString());
				height = RandomTestUtil.randomDouble();
				id = RandomTestUtil.randomLong();
				inventoryLevel = RandomTestUtil.randomInt();
				manufacturerPartNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				neverExpire = true;
				productId = RandomTestUtil.randomLong();
				published = true;
				purchasable = true;
				replacementSkuExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				replacementSkuId = null;
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				unspsc = StringUtil.toLowerCase(RandomTestUtil.randomString());
				weight = RandomTestUtil.randomDouble();
				width = RandomTestUtil.randomDouble();
			}
		};
	}

	@Override
	protected Sku testDeleteSku_addSku() throws Exception {
		return skuResource.postProductIdSku(
			_cProduct.getCProductId(), randomSku());
	}

	@Override
	protected Sku testDeleteSkuByExternalReferenceCode_addSku()
		throws Exception {

		return skuResource.postProductByExternalReferenceCodeSku(
			_cProduct.getExternalReferenceCode(), randomSku());
	}

	@Override
	protected Sku testGetProductByExternalReferenceCodeSkusPage_addSku(
			String externalReferenceCode, Sku sku)
		throws Exception {

		return skuResource.postProductByExternalReferenceCodeSku(
			externalReferenceCode, sku);
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeSkusPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected Sku testGetProductIdSkusPage_addSku(Long id, Sku sku)
		throws Exception {

		return skuResource.postProductIdSku(id, sku);
	}

	@Override
	protected Long testGetProductIdSkusPage_getId() throws Exception {
		return _cProduct.getCProductId();
	}

	@Override
	protected Sku testGetSku_addSku() throws Exception {
		return skuResource.postProductIdSku(
			_cProduct.getCProductId(), randomSku());
	}

	@Override
	protected Sku testGetSkuByExternalReferenceCode_addSku() throws Exception {
		return skuResource.postProductByExternalReferenceCodeSku(
			_cProduct.getExternalReferenceCode(), randomSku());
	}

	@Override
	protected Sku testGetSkusPage_addSku(Sku sku) throws Exception {
		return skuResource.postProductIdSku(_cProduct.getCProductId(), sku);
	}

	@Override
	protected Sku testGraphQLSku_addSku() throws Exception {
		return skuResource.postProductIdSku(
			_cProduct.getCProductId(), randomSku());
	}

	@Override
	protected Sku testPostProductByExternalReferenceCodeSku_addSku(Sku sku)
		throws Exception {

		return skuResource.postProductByExternalReferenceCodeSku(
			_cProduct.getExternalReferenceCode(), sku);
	}

	@Override
	protected Sku testPostProductIdSku_addSku(Sku sku) throws Exception {
		return skuResource.postProductIdSku(_cProduct.getCProductId(), sku);
	}

	@Inject
	private static CPInstanceLocalService _cpInstanceLocalService;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}