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
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.ProductShippingConfiguration;
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
public class ProductShippingConfigurationResourceTest
	extends BaseProductShippingConfigurationResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductByExternalReferenceCodeShippingConfiguration()
		throws Exception {

		super.testGetProductByExternalReferenceCodeShippingConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductIdShippingConfiguration() throws Exception {
		super.testGetProductIdShippingConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeShippingConfiguration()
		throws Exception {

		super.
			testGraphQLGetProductByExternalReferenceCodeShippingConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeShippingConfigurationNotFound()
		throws Exception {

		super.
			testGraphQLGetProductByExternalReferenceCodeShippingConfigurationNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductIdShippingConfiguration()
		throws Exception {

		super.testGraphQLGetProductIdShippingConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductIdShippingConfigurationNotFound()
		throws Exception {

		super.testGraphQLGetProductIdShippingConfigurationNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testPatchProductByExternalReferenceCodeShippingConfiguration()
		throws Exception {

		super.testPatchProductByExternalReferenceCodeShippingConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testPatchProductIdShippingConfiguration() throws Exception {
		super.testPatchProductIdShippingConfiguration();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"depth", "freeShipping", "height", "shippable",
			"shippingExtraPrice", "shippingSeparately", "weight", "width"
		};
	}

	@Override
	protected ProductShippingConfiguration
			randomPatchProductShippingConfiguration()
		throws Exception {

		return new ProductShippingConfiguration() {
			{
				depth = BigDecimal.valueOf(RandomTestUtil.nextDouble());
				freeShipping = RandomTestUtil.randomBoolean();
				height = BigDecimal.valueOf(RandomTestUtil.nextDouble());
				shippable = RandomTestUtil.randomBoolean();
				shippingExtraPrice = BigDecimal.valueOf(
					RandomTestUtil.nextDouble());
				shippingSeparately = RandomTestUtil.randomBoolean();
				weight = BigDecimal.valueOf(RandomTestUtil.nextDouble());
				width = BigDecimal.valueOf(RandomTestUtil.nextDouble());
			}
		};
	}

	@Override
	protected ProductShippingConfiguration randomProductShippingConfiguration()
		throws Exception {

		return new ProductShippingConfiguration() {
			{
				depth = BigDecimal.valueOf(_cpDefinition.getDepth());
				freeShipping = _cpDefinition.isFreeShipping();
				height = BigDecimal.valueOf(_cpDefinition.getHeight());
				shippable = _cpDefinition.isShippable();
				shippingExtraPrice = BigDecimal.valueOf(
					_cpDefinition.getShippingExtraPrice());
				shippingSeparately = _cpDefinition.isShipSeparately();
				weight = BigDecimal.valueOf(_cpDefinition.getWeight());
				width = BigDecimal.valueOf(_cpDefinition.getWidth());
			}
		};
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

}