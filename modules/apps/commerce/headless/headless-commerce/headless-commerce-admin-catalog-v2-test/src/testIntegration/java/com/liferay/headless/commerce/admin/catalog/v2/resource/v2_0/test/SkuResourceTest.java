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

package com.liferay.headless.commerce.admin.catalog.v2.resource.v2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.Sku;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
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

		for (CPInstance cpInstance :
				_cpInstanceLocalService.getCPInstances(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			_cpInstanceLocalService.deleteCPInstance(cpInstance);
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"depth", "discontinued", "gtin", "height", "manufacturerPartNumber",
			"published", "purchasable", "sku", "unspsc", "weight", "width"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"catalogId"};
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
	protected Sku testPatchSku_addSku() throws Exception {
		return skuResource.postProductIdSku(
			_cProduct.getCProductId(), randomSku());
	}

	@Override
	protected Sku testPatchSkuByExternalReferenceCode_addSku()
		throws Exception {

		return skuResource.postProductByExternalReferenceCodeSku(
			_cProduct.getExternalReferenceCode(), randomSku());
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

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}