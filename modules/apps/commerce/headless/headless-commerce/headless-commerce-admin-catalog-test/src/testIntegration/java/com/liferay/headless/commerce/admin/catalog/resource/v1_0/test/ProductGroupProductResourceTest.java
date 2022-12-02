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
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductGroupProduct;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class ProductGroupProductResourceTest
	extends BaseProductGroupProductResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_commercePricingClass =
			_commercePricingClassLocalService.addCommercePricingClass(
				_user.getUserId(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				ServiceContextTestUtil.getServiceContext(
					testCompany.getCompanyId(), testGroup.getGroupId(),
					_user.getUserId()));
	}

	@Override
	protected ProductGroupProduct randomProductGroupProduct() throws Exception {
		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpInstances.add(cpInstance);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		_cpDefinitions.add(cpDefinition);

		CProduct cProduct = cpDefinition.getCProduct();

		_cProducts.add(cProduct);

		return new ProductGroupProduct() {
			{
				id = RandomTestUtil.randomLong();
				productExternalReferenceCode =
					cProduct.getExternalReferenceCode();
				productId = cProduct.getCProductId();
			}
		};
	}

	@Override
	protected ProductGroupProduct
			testDeleteProductGroupProduct_addProductGroupProduct()
		throws Exception {

		return productGroupProductResource.
			postProductGroupIdProductGroupProduct(
				_commercePricingClass.getCommercePricingClassId(),
				randomProductGroupProduct());
	}

	@Override
	protected ProductGroupProduct
			testGetProductGroupByExternalReferenceCodeProductGroupProductsPage_addProductGroupProduct(
				String externalReferenceCode,
				ProductGroupProduct productGroupProduct)
		throws Exception {

		return productGroupProductResource.
			postProductGroupByExternalReferenceCodeProductGroupProduct(
				externalReferenceCode, productGroupProduct);
	}

	@Override
	protected String
			testGetProductGroupByExternalReferenceCodeProductGroupProductsPage_getExternalReferenceCode()
		throws Exception {

		return _commercePricingClass.getExternalReferenceCode();
	}

	@Override
	protected ProductGroupProduct
			testGetProductGroupIdProductGroupProductsPage_addProductGroupProduct(
				Long id, ProductGroupProduct productGroupProduct)
		throws Exception {

		return productGroupProductResource.
			postProductGroupIdProductGroupProduct(id, productGroupProduct);
	}

	@Override
	protected Long testGetProductGroupIdProductGroupProductsPage_getId()
		throws Exception {

		return _commercePricingClass.getCommercePricingClassId();
	}

	@Override
	protected ProductGroupProduct
			testGraphQLProductGroupProduct_addProductGroupProduct()
		throws Exception {

		return productGroupProductResource.
			postProductGroupIdProductGroupProduct(
				_commercePricingClass.getCommercePricingClassId(),
				randomProductGroupProduct());
	}

	@Override
	protected ProductGroupProduct
			testPostProductGroupByExternalReferenceCodeProductGroupProduct_addProductGroupProduct(
				ProductGroupProduct productGroupProduct)
		throws Exception {

		return productGroupProductResource.
			postProductGroupByExternalReferenceCodeProductGroupProduct(
				_commercePricingClass.getExternalReferenceCode(),
				productGroupProduct);
	}

	@Override
	protected ProductGroupProduct
			testPostProductGroupIdProductGroupProduct_addProductGroupProduct(
				ProductGroupProduct productGroupProduct)
		throws Exception {

		return productGroupProductResource.
			postProductGroupIdProductGroupProduct(
				_commercePricingClass.getCommercePricingClassId(),
				productGroupProduct);
	}

	@Inject
	private static CommercePricingClassLocalService
		_commercePricingClassLocalService;

	@DeleteAfterTestRun
	private CommercePricingClass _commercePricingClass;

	@DeleteAfterTestRun
	private final List<CPDefinition> _cpDefinitions = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<CPInstance> _cpInstances = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<CProduct> _cProducts = new ArrayList<>();

	@DeleteAfterTestRun
	private User _user;

}