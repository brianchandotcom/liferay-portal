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
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.RelatedProduct;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class RelatedProductResourceTest
	extends BaseRelatedProductResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();
	}

	@Ignore
	@Override
	@Test
	public void testDeleteRelatedProduct() throws Exception {
		super.testDeleteRelatedProduct();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteRelatedProduct() throws Exception {
		super.testGraphQLDeleteRelatedProduct();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"priority", "productExternalReferenceCode", "productId", "type"
		};
	}

	@Override
	protected RelatedProduct randomRelatedProduct() throws Exception {
		return new RelatedProduct() {
			{
				id = RandomTestUtil.randomLong();
				priority = RandomTestUtil.randomDouble();
				productExternalReferenceCode =
					_cProduct.getExternalReferenceCode();
				productId = _cProduct.getCProductId();
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected RelatedProduct testDeleteRelatedProduct_addRelatedProduct()
		throws Exception {

		return relatedProductResource.postProductIdRelatedProduct(
			_cProduct.getCProductId(), randomRelatedProduct());
	}

	@Override
	protected RelatedProduct
			testGetProductByExternalReferenceCodeRelatedProductsPage_addRelatedProduct(
				String externalReferenceCode, RelatedProduct relatedProduct)
		throws Exception {

		return relatedProductResource.
			postProductByExternalReferenceCodeRelatedProduct(
				externalReferenceCode, relatedProduct);
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeRelatedProductsPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected RelatedProduct
			testGetProductIdRelatedProductsPage_addRelatedProduct(
				Long id, RelatedProduct relatedProduct)
		throws Exception {

		return relatedProductResource.postProductIdRelatedProduct(
			id, relatedProduct);
	}

	@Override
	protected Long testGetProductIdRelatedProductsPage_getId()
		throws Exception {

		return _cProduct.getCProductId();
	}

	@Override
	protected RelatedProduct testGetRelatedProduct_addRelatedProduct()
		throws Exception {

		return relatedProductResource.postProductIdRelatedProduct(
			_cProduct.getCProductId(), randomRelatedProduct());
	}

	@Override
	protected RelatedProduct testGraphQLRelatedProduct_addRelatedProduct()
		throws Exception {

		return relatedProductResource.postProductIdRelatedProduct(
			_cProduct.getCProductId(), randomRelatedProduct());
	}

	@Override
	protected RelatedProduct
			testPostProductByExternalReferenceCodeRelatedProduct_addRelatedProduct(
				RelatedProduct relatedProduct)
		throws Exception {

		return relatedProductResource.
			postProductByExternalReferenceCodeRelatedProduct(
				_cProduct.getExternalReferenceCode(), relatedProduct);
	}

	@Override
	protected RelatedProduct testPostProductIdRelatedProduct_addRelatedProduct(
			RelatedProduct relatedProduct)
		throws Exception {

		return relatedProductResource.postProductIdRelatedProduct(
			_cProduct.getCProductId(), relatedProduct);
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}