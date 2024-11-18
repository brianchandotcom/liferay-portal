/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductConfiguration;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class ProductConfigurationResourceTest
	extends BaseProductConfigurationResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpDefinition = CPTestUtil.addCPDefinition(
			testGroup.getGroupId(), "simple", true, false);

		_cProduct = _cpDefinition.getCProduct();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductByExternalReferenceCodeConfiguration()
		throws Exception {

		super.testGetProductByExternalReferenceCodeConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductIdConfiguration() throws Exception {
		super.testGetProductIdConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeConfiguration()
		throws Exception {

		super.testGraphQLGetProductByExternalReferenceCodeConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeConfigurationNotFound()
		throws Exception {

		super.
			testGraphQLGetProductByExternalReferenceCodeConfigurationNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductIdConfiguration() throws Exception {
		super.testGraphQLGetProductIdConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductIdConfigurationNotFound()
		throws Exception {

		super.testGraphQLGetProductIdConfigurationNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testPatchProductByExternalReferenceCodeConfiguration()
		throws Exception {

		super.testPatchProductByExternalReferenceCodeConfiguration();
	}

	@Override
	@Test
	public void testPatchProductIdConfiguration() throws Exception {
		ProductConfiguration randomProductConfiguration =
			randomProductConfiguration();

		productConfigurationResource.patchProductIdConfiguration(
			_cProduct.getCProductId(), randomProductConfiguration);

		ProductConfiguration productConfiguration =
			productConfigurationResource.getProductIdConfiguration(
				_cProduct.getCProductId());

		Assert.assertEquals(productConfiguration, randomProductConfiguration);
	}

	@Override
	protected ProductConfiguration randomProductConfiguration() {
		return new ProductConfiguration() {
			{
				allowBackOrder = RandomTestUtil.randomBoolean();
				allowedOrderQuantities = new BigDecimal[0];
				availabilityEstimateId = 0L;
				inventoryEngine = RandomTestUtil.randomString();
				lowStockAction = RandomTestUtil.randomString();
				maxOrderQuantity = BigDecimal.ONE;
				minOrderQuantity = BigDecimal.ONE;
				minStockQuantity = BigDecimal.ONE;
				multipleOrderQuantity = BigDecimal.ONE;
			}
		};
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}