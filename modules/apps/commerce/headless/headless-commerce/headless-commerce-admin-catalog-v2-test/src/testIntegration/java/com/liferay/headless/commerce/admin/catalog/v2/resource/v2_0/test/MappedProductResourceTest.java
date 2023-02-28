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
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.MappedProduct;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.odata.entity.EntityField;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class MappedProductResourceTest
	extends BaseMappedProductResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"productExternalReferenceCode", "productId", "quantity", "sku",
			"skuExternalReferenceCode", "skuId"
		};
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		try {
			return super.getEntityFields();
		}
		catch (NullPointerException nullPointerException) {
			Map<String, EntityField> entityFieldsMap = new HashMap<>();

			return entityFieldsMap.values();
		}
	}

	@Override
	protected MappedProduct randomMappedProduct() throws Exception {
		return new MappedProduct() {
			{
				id = RandomTestUtil.randomLong();
				productExternalReferenceCode =
					_cProduct.getExternalReferenceCode();
				productId = _cProduct.getCProductId();
				quantity = RandomTestUtil.randomInt();
				sequence = RandomTestUtil.randomString();
				sku = _cpInstance.getSku();
				skuExternalReferenceCode =
					_cpInstance.getExternalReferenceCode();
				skuId = _cpInstance.getCPInstanceId();
			}
		};
	}

	@Override
	protected MappedProduct randomPatchMappedProduct() throws Exception {
		return new MappedProduct() {
			{
				productExternalReferenceCode =
					_cProduct.getExternalReferenceCode();
				productId = _cProduct.getCProductId();
				quantity = RandomTestUtil.randomInt();
				sku = _cpInstance.getSku();
				skuExternalReferenceCode =
					_cpInstance.getExternalReferenceCode();
				skuId = _cpInstance.getCPInstanceId();
			}
		};
	}

	@Override
	protected MappedProduct testDeleteMappedProduct_addMappedProduct()
		throws Exception {

		return mappedProductResource.postProductIdMappedProduct(
			_cProduct.getCProductId(), randomMappedProduct());
	}

	@Override
	protected MappedProduct testGetMappedProduct_addMappedProduct()
		throws Exception {

		return mappedProductResource.postProductIdMappedProduct(
			_cProduct.getCProductId(), randomMappedProduct());
	}

	@Override
	protected MappedProduct
			testGetProductByExternalReferenceCodeMappedProductBySequence_addMappedProduct()
		throws Exception {

		return mappedProductResource.postProductIdMappedProduct(
			_cProduct.getCProductId(), randomMappedProduct());
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeMappedProductBySequence_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected MappedProduct
			testGetProductByExternalReferenceCodeMappedProductsPage_addMappedProduct(
				String externalReferenceCode, MappedProduct mappedProduct)
		throws Exception {

		return mappedProductResource.
			postProductByExternalReferenceCodeMappedProduct(
				externalReferenceCode, mappedProduct);
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeMappedProductsPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected MappedProduct
			testGetProductIdMappedProductBySequence_addMappedProduct()
		throws Exception {

		return mappedProductResource.postProductIdMappedProduct(
			_cProduct.getCProductId(), randomMappedProduct());
	}

	@Override
	protected Long testGetProductIdMappedProductBySequence_getId(
			MappedProduct mappedProduct)
		throws Exception {

		return mappedProduct.getProductId();
	}

	@Override
	protected MappedProduct testGetProductIdMappedProductsPage_addMappedProduct(
			Long productId, MappedProduct mappedProduct)
		throws Exception {

		return mappedProductResource.postProductIdMappedProduct(
			productId, mappedProduct);
	}

	@Override
	protected Long testGetProductIdMappedProductsPage_getId() throws Exception {
		return _cProduct.getCProductId();
	}

	@Override
	protected String
			testGraphQLGetProductByExternalReferenceCodeMappedProductBySequence_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected Long testGraphQLGetProductIdMappedProductBySequence_getId(
			MappedProduct mappedProduct)
		throws Exception {

		return mappedProduct.getProductId();
	}

	@Override
	protected MappedProduct testGraphQLMappedProduct_addMappedProduct()
		throws Exception {

		return mappedProductResource.postProductIdMappedProduct(
			_cProduct.getCProductId(), randomMappedProduct());
	}

	@Override
	protected MappedProduct testPatchMappedProduct_addMappedProduct()
		throws Exception {

		return mappedProductResource.postProductIdMappedProduct(
			_cProduct.getCProductId(), randomMappedProduct());
	}

	@Override
	protected MappedProduct
			testPostProductByExternalReferenceCodeMappedProduct_addMappedProduct(
				MappedProduct mappedProduct)
		throws Exception {

		return mappedProductResource.
			postProductByExternalReferenceCodeMappedProduct(
				_cProduct.getExternalReferenceCode(), mappedProduct);
	}

	@Override
	protected MappedProduct testPostProductIdMappedProduct_addMappedProduct(
			MappedProduct mappedProduct)
		throws Exception {

		return mappedProductResource.postProductIdMappedProduct(
			_cProduct.getCProductId(), mappedProduct);
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}