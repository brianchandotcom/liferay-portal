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
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductSubscriptionConfiguration;
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
public class ProductSubscriptionConfigurationResourceTest
	extends BaseProductSubscriptionConfigurationResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();
	}

	@Test
	public void testGetProductByExternalReferenceCodeSubscriptionConfiguration()
		throws Exception {

		ProductSubscriptionConfiguration postProductSubscriptionConfiguration =
			randomProductSubscriptionConfiguration();

		ProductSubscriptionConfiguration getProductSubscriptionConfiguration =
			productSubscriptionConfigurationResource.
				getProductByExternalReferenceCodeSubscriptionConfiguration(
					_cProduct.getExternalReferenceCode());

		assertEquals(
			postProductSubscriptionConfiguration,
			getProductSubscriptionConfiguration);
		assertValid(getProductSubscriptionConfiguration);
	}

	@Test
	public void testGetProductIdSubscriptionConfiguration() throws Exception {
		ProductSubscriptionConfiguration postProductSubscriptionConfiguration =
			randomProductSubscriptionConfiguration();

		ProductSubscriptionConfiguration getProductSubscriptionConfiguration =
			productSubscriptionConfigurationResource.
				getProductIdSubscriptionConfiguration(
					_cProduct.getCProductId());

		assertEquals(
			postProductSubscriptionConfiguration,
			getProductSubscriptionConfiguration);
		assertValid(getProductSubscriptionConfiguration);
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeSubscriptionConfiguration()
		throws Exception {

		ProductSubscriptionConfiguration productSubscriptionConfiguration =
			randomProductSubscriptionConfiguration();

		String externalReferenceCode =
			"\"" + _cProduct.getExternalReferenceCode() + "\"";

		Assert.assertTrue(
			equals(
				productSubscriptionConfiguration,
				ProductSubscriptionConfiguration.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productByExternalReferenceCodeSubscriptionConfiguration",
								HashMapBuilder.<String, Object>put(
									"externalReferenceCode",
									externalReferenceCode
								).build(),
								getGraphQLFields())),
						"JSONObject/data",
						"Object/productByExternalReferenceCodeSubscriptionConfiguration"))));
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeSubscriptionConfigurationNotFound()
		throws Exception {

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productByExternalReferenceCodeSubscriptionConfiguration",
						HashMapBuilder.<String, Object>put(
							"externalReferenceCode",
							"\"" + RandomTestUtil.randomString() + "\""
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGraphQLGetProductIdSubscriptionConfiguration()
		throws Exception {

		ProductSubscriptionConfiguration productSubscriptionConfiguration =
			randomProductSubscriptionConfiguration();

		Assert.assertTrue(
			equals(
				productSubscriptionConfiguration,
				ProductSubscriptionConfiguration.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productIdSubscriptionConfiguration",
								HashMapBuilder.<String, Object>put(
									"id", _cProduct.getCProductId()
								).build(),
								getGraphQLFields())),
						"JSONObject/data",
						"Object/productIdSubscriptionConfiguration"))));
	}

	@Test
	public void testGraphQLGetProductIdSubscriptionConfigurationNotFound()
		throws Exception {

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productIdSubscriptionConfiguration",
						HashMapBuilder.<String, Object>put(
							"id", RandomTestUtil.randomLong()
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchProductByExternalReferenceCodeSubscriptionConfiguration()
		throws Exception {

		ProductSubscriptionConfiguration postProductSubscriptionConfiguration =
			randomProductSubscriptionConfiguration();

		ProductSubscriptionConfiguration
			randomPatchProductSubscriptionConfiguration =
				randomPatchProductSubscriptionConfiguration();

		productSubscriptionConfigurationResource.
			patchProductByExternalReferenceCodeSubscriptionConfiguration(
				_cProduct.getExternalReferenceCode(),
				randomPatchProductSubscriptionConfiguration);

		ProductSubscriptionConfiguration
			expectedProductSubscriptionConfiguration =
				postProductSubscriptionConfiguration.clone();

		BeanTestUtil.copyProperties(
			randomPatchProductSubscriptionConfiguration,
			expectedProductSubscriptionConfiguration);

		ProductSubscriptionConfiguration getProductSubscriptionConfiguration =
			productSubscriptionConfigurationResource.
				getProductByExternalReferenceCodeSubscriptionConfiguration(
					_cProduct.getExternalReferenceCode());

		assertEquals(
			expectedProductSubscriptionConfiguration,
			getProductSubscriptionConfiguration);
		assertValid(getProductSubscriptionConfiguration);
	}

	@Test
	public void testPatchProductIdSubscriptionConfiguration() throws Exception {
		ProductSubscriptionConfiguration postProductSubscriptionConfiguration =
			randomProductSubscriptionConfiguration();

		ProductSubscriptionConfiguration
			randomPatchProductSubscriptionConfiguration =
				randomPatchProductSubscriptionConfiguration();

		productSubscriptionConfigurationResource.
			patchProductIdSubscriptionConfiguration(
				_cProduct.getCProductId(),
				randomPatchProductSubscriptionConfiguration);

		ProductSubscriptionConfiguration
			expectedProductSubscriptionConfiguration =
				postProductSubscriptionConfiguration.clone();

		BeanTestUtil.copyProperties(
			randomPatchProductSubscriptionConfiguration,
			expectedProductSubscriptionConfiguration);

		ProductSubscriptionConfiguration getProductSubscriptionConfiguration =
			productSubscriptionConfigurationResource.
				getProductIdSubscriptionConfiguration(
					_cProduct.getCProductId());

		assertEquals(
			expectedProductSubscriptionConfiguration,
			getProductSubscriptionConfiguration);
		assertValid(getProductSubscriptionConfiguration);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"enable", "length", "numberOfLength"};
	}

	@Override
	protected ProductSubscriptionConfiguration
			randomPatchProductSubscriptionConfiguration()
		throws Exception {

		return new ProductSubscriptionConfiguration() {
			{
				enable = RandomTestUtil.randomBoolean();
				length = RandomTestUtil.randomInt();
				numberOfLength = RandomTestUtil.randomLong();
			}
		};
	}

	@Override
	protected ProductSubscriptionConfiguration
			randomProductSubscriptionConfiguration()
		throws Exception {

		return new ProductSubscriptionConfiguration() {
			{
				enable = _cpDefinition.isSubscriptionEnabled();
				length = _cpDefinition.getSubscriptionLength();
				numberOfLength = _cpDefinition.getMaxSubscriptionCycles();
				subscriptionType =
					ProductSubscriptionConfiguration.SubscriptionType.create(
						_cpDefinition.getSubscriptionType());
				subscriptionTypeSettings =
					_cpDefinition.getSubscriptionTypeSettingsProperties();
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