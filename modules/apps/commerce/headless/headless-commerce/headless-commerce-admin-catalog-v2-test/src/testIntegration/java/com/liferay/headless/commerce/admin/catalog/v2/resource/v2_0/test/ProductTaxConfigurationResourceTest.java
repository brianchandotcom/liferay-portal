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
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.ProductTaxConfiguration;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class ProductTaxConfigurationResourceTest
	extends BaseProductTaxConfigurationResourceTestCase {

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
	public void testGetProductByExternalReferenceCodeTaxConfiguration()
		throws Exception {

		super.testGetProductByExternalReferenceCodeTaxConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeTaxConfiguration()
		throws Exception {

		super.testGraphQLGetProductByExternalReferenceCodeTaxConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testPatchProductByExternalReferenceCodeTaxConfiguration()
		throws Exception {

		super.testPatchProductByExternalReferenceCodeTaxConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testPatchProductIdTaxConfiguration() throws Exception {
		super.testPatchProductIdTaxConfiguration();
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"taxable"};
	}

	@Override
	protected ProductTaxConfiguration randomPatchProductTaxConfiguration()
		throws Exception {

		return new ProductTaxConfiguration() {
			{
				id = 0L;
				taxable = RandomTestUtil.randomBoolean();
			}
		};
	}

	@Override
	protected ProductTaxConfiguration
			testGetProductByExternalReferenceCodeTaxConfiguration_addProductTaxConfiguration()
		throws Exception {

		return _getCPTaxCategory();
	}

	@Override
	protected ProductTaxConfiguration
			testGetProductIdTaxConfiguration_addProductTaxConfiguration()
		throws Exception {

		return _getCPTaxCategory();
	}

	@Override
	protected ProductTaxConfiguration
			testGraphQLProductTaxConfiguration_addProductTaxConfiguration()
		throws Exception {

		return _getCPTaxCategory();
	}

	@Override
	protected ProductTaxConfiguration
			testPatchProductByExternalReferenceCodeTaxConfiguration_addProductTaxConfiguration()
		throws Exception {

		return _getCPTaxCategory();
	}

	@Override
	protected ProductTaxConfiguration
			testPatchProductIdTaxConfiguration_addProductTaxConfiguration()
		throws Exception {

		return _getCPTaxCategory();
	}

	private ProductTaxConfiguration _getCPTaxCategory() throws Exception {
		CPTaxCategory cpTaxCategory = _cpDefinition.getCPTaxCategory();

		return new ProductTaxConfiguration() {
			{
				id = _cProduct.getCProductId();
				taxable = !_cpDefinition.isTaxExempt();

				setTaxCategory(
					() -> {
						if (cpTaxCategory != null) {
							return cpTaxCategory.getName();
						}

						return null;
					});
			}
		};
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}