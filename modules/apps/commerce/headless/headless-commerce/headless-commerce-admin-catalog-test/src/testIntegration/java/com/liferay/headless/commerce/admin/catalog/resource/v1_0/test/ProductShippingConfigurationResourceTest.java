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
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductShippingConfiguration;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
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

		_cProduct = _cpDefinition.getCProduct();
	}

	@Override
	@Test
	public void testGetProductByExternalReferenceCodeShippingConfiguration()
		throws Exception {

		ProductShippingConfiguration postProductShippingConfiguration =
			randomProductShippingConfiguration();

		ProductShippingConfiguration getProductShippingConfiguration =
			productShippingConfigurationResource.
				getProductByExternalReferenceCodeShippingConfiguration(
					_cProduct.getExternalReferenceCode());

		assertEquals(
			postProductShippingConfiguration, getProductShippingConfiguration);
		assertValid(getProductShippingConfiguration);
	}

	@Test
	public void testGetProductIdShippingConfiguration() throws Exception {
		ProductShippingConfiguration postProductShippingConfiguration =
			randomProductShippingConfiguration();

		ProductShippingConfiguration getProductShippingConfiguration =
			productShippingConfigurationResource.
				getProductIdShippingConfiguration(_cProduct.getCProductId());

		assertEquals(
			postProductShippingConfiguration, getProductShippingConfiguration);
		assertValid(getProductShippingConfiguration);
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeShippingConfiguration()
		throws Exception {

		ProductShippingConfiguration productShippingConfiguration =
			randomProductShippingConfiguration();

		String externalReferenceCode =
			"\"" + _cProduct.getExternalReferenceCode() + "\"";

		Assert.assertTrue(
			equals(
				productShippingConfiguration,
				ProductShippingConfiguration.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productByExternalReferenceCodeShippingConfiguration",
								HashMapBuilder.<String, Object>put(
									"externalReferenceCode",
									externalReferenceCode
								).build(),
								getGraphQLFields())),
						"JSONObject/data",
						"Object/productByExternalReferenceCodeShippingConfiguration"))));
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeShippingConfigurationNotFound()
		throws Exception {

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productByExternalReferenceCodeShippingConfiguration",
						HashMapBuilder.<String, Object>put(
							"externalReferenceCode",
							"\"" + RandomTestUtil.randomString() + "\""
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGraphQLGetProductIdShippingConfiguration()
		throws Exception {

		ProductShippingConfiguration productShippingConfiguration =
			randomProductShippingConfiguration();

		Assert.assertTrue(
			equals(
				productShippingConfiguration,
				ProductShippingConfiguration.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productIdShippingConfiguration",
								HashMapBuilder.<String, Object>put(
									"id", _cProduct.getCProductId()
								).build(),
								getGraphQLFields())),
						"JSONObject/data",
						"Object/productIdShippingConfiguration"))));
	}

	@Test
	public void testGraphQLGetProductIdShippingConfigurationNotFound()
		throws Exception {

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productIdShippingConfiguration",
						HashMapBuilder.<String, Object>put(
							"id", RandomTestUtil.randomLong()
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchProductByExternalReferenceCodeShippingConfiguration()
		throws Exception {

		ProductShippingConfiguration postProductShippingConfiguration =
			randomProductShippingConfiguration();

		ProductShippingConfiguration randomPatchProductShippingConfiguration =
			randomPatchProductShippingConfiguration();

		productShippingConfigurationResource.
			patchProductByExternalReferenceCodeShippingConfiguration(
				_cProduct.getExternalReferenceCode(),
				randomPatchProductShippingConfiguration);

		ProductShippingConfiguration expectedProductShippingConfiguration =
			postProductShippingConfiguration.clone();

		BeanTestUtil.copyProperties(
			randomPatchProductShippingConfiguration,
			expectedProductShippingConfiguration);

		ProductShippingConfiguration getProductShippingConfiguration =
			productShippingConfigurationResource.
				getProductByExternalReferenceCodeShippingConfiguration(
					_cProduct.getExternalReferenceCode());

		assertEquals(
			expectedProductShippingConfiguration,
			getProductShippingConfiguration);
		assertValid(getProductShippingConfiguration);
	}

	@Test
	public void testPatchProductIdShippingConfiguration() throws Exception {
		ProductShippingConfiguration postProductShippingConfiguration =
			randomProductShippingConfiguration();

		ProductShippingConfiguration randomPatchProductShippingConfiguration =
			randomPatchProductShippingConfiguration();

		productShippingConfigurationResource.
			patchProductIdShippingConfiguration(
				_cProduct.getCProductId(),
				randomPatchProductShippingConfiguration);

		ProductShippingConfiguration expectedProductShippingConfiguration =
			postProductShippingConfiguration.clone();

		BeanTestUtil.copyProperties(
			randomPatchProductShippingConfiguration,
			expectedProductShippingConfiguration);

		ProductShippingConfiguration getProductShippingConfiguration =
			productShippingConfigurationResource.
				getProductIdShippingConfiguration(_cProduct.getCProductId());

		assertEquals(
			expectedProductShippingConfiguration,
			getProductShippingConfiguration);
		assertValid(getProductShippingConfiguration);
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

	@DeleteAfterTestRun
	private CProduct _cProduct;

}