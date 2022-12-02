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
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductConfiguration;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
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

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					_cpDefinition.getCPDefinitionId());

		_cProduct = _cpDefinition.getCProduct();
	}

	@Override
	@Test
	public void testGetProductByExternalReferenceCodeConfiguration()
		throws Exception {

		ProductConfiguration postProductConfiguration =
			randomProductConfiguration();

		ProductConfiguration getProductConfiguration =
			productConfigurationResource.
				getProductByExternalReferenceCodeConfiguration(
					_cProduct.getExternalReferenceCode());

		assertEquals(postProductConfiguration, getProductConfiguration);
		assertValid(getProductConfiguration);
	}

	@Test
	public void testGetProductIdConfiguration() throws Exception {
		ProductConfiguration postProductConfiguration =
			randomProductConfiguration();

		ProductConfiguration getProductConfiguration =
			productConfigurationResource.getProductIdConfiguration(
				_cProduct.getCProductId());

		assertEquals(postProductConfiguration, getProductConfiguration);
		assertValid(getProductConfiguration);
	}

	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeConfiguration()
		throws Exception {

		ProductConfiguration productConfiguration =
			randomProductConfiguration();

		String externalReferenceCode =
			"\"" + _cProduct.getExternalReferenceCode() + "\"";

		Assert.assertTrue(
			equals(
				productConfiguration,
				ProductConfiguration.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productByExternalReferenceCodeConfiguration",
								HashMapBuilder.<String, Object>put(
									"externalReferenceCode",
									externalReferenceCode
								).build(),
								getGraphQLFields())),
						"JSONObject/data",
						"Object/productByExternalReferenceCodeConfiguration"))));
	}

	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeConfigurationNotFound()
		throws Exception {

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productByExternalReferenceCodeConfiguration",
						HashMapBuilder.<String, Object>put(
							"externalReferenceCode",
							"\"" + RandomTestUtil.randomString() + "\""
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Override
	@Test
	public void testGraphQLGetProductIdConfiguration() throws Exception {
		ProductConfiguration productConfiguration =
			randomProductConfiguration();

		Assert.assertTrue(
			equals(
				productConfiguration,
				ProductConfiguration.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productIdConfiguration",
								HashMapBuilder.<String, Object>put(
									"id", _cProduct.getCProductId()
								).build(),
								getGraphQLFields())),
						"JSONObject/data", "Object/productIdConfiguration"))));
	}

	@Override
	@Test
	public void testGraphQLGetProductIdConfigurationNotFound()
		throws Exception {

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productIdConfiguration",
						HashMapBuilder.<String, Object>put(
							"id", RandomTestUtil.randomLong()
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchProductByExternalReferenceCodeConfiguration()
		throws Exception {

		ProductConfiguration postProductConfiguration =
			randomProductConfiguration();

		ProductConfiguration randomPatchProductConfiguration =
			randomPatchProductConfiguration();

		productConfigurationResource.
			patchProductByExternalReferenceCodeConfiguration(
				_cProduct.getExternalReferenceCode(),
				randomPatchProductConfiguration);

		ProductConfiguration expectedProductConfiguration =
			postProductConfiguration.clone();

		BeanTestUtil.copyProperties(
			randomPatchProductConfiguration, expectedProductConfiguration);

		ProductConfiguration getProductConfiguration =
			productConfigurationResource.
				getProductByExternalReferenceCodeConfiguration(
					_cProduct.getExternalReferenceCode());

		assertEquals(expectedProductConfiguration, getProductConfiguration);
		assertValid(getProductConfiguration);
	}

	@Test
	public void testPatchProductIdConfiguration() throws Exception {
		ProductConfiguration postProductConfiguration =
			randomProductConfiguration();

		ProductConfiguration randomPatchProductConfiguration =
			randomPatchProductConfiguration();

		productConfigurationResource.patchProductIdConfiguration(
			_cProduct.getCProductId(), randomPatchProductConfiguration);

		ProductConfiguration expectedProductConfiguration =
			postProductConfiguration.clone();

		BeanTestUtil.copyProperties(
			randomPatchProductConfiguration, expectedProductConfiguration);

		ProductConfiguration getProductConfiguration =
			productConfigurationResource.getProductIdConfiguration(
				_cProduct.getCProductId());

		assertEquals(expectedProductConfiguration, getProductConfiguration);
		assertValid(getProductConfiguration);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"allowBackOrder", "inventoryEngine", "maxOrderQuantity",
			"minOrderQuantity", "multipleOrderQuantity"
		};
	}

	@Override
	protected ProductConfiguration randomPatchProductConfiguration()
		throws Exception {

		return new ProductConfiguration() {
			{
				allowBackOrder = RandomTestUtil.randomBoolean();
				displayAvailability = RandomTestUtil.randomBoolean();
				displayStockQuantity = RandomTestUtil.randomBoolean();
				inventoryEngine = RandomTestUtil.randomString();
				lowStockAction = RandomTestUtil.randomString();
				maxOrderQuantity = RandomTestUtil.randomInt();
				minOrderQuantity = RandomTestUtil.randomInt();
				minStockQuantity = RandomTestUtil.randomInt();
				multipleOrderQuantity = RandomTestUtil.randomInt();
			}
		};
	}

	@Override
	protected ProductConfiguration randomProductConfiguration()
		throws Exception {

		return new ProductConfiguration() {
			{
				allowBackOrder = _cpDefinitionInventory.isBackOrders();
				displayAvailability =
					_cpDefinitionInventory.isDisplayAvailability();
				displayStockQuantity =
					_cpDefinitionInventory.isDisplayStockQuantity();
				inventoryEngine =
					_cpDefinitionInventory.getCPDefinitionInventoryEngine();
				lowStockAction = _cpDefinitionInventory.getLowStockActivity();
				maxOrderQuantity = _cpDefinitionInventory.getMaxOrderQuantity();
				minOrderQuantity = _cpDefinitionInventory.getMinOrderQuantity();
				minStockQuantity = _cpDefinitionInventory.getMinStockQuantity();
				multipleOrderQuantity =
					_cpDefinitionInventory.getMultipleOrderQuantity();
			}
		};
	}

	@Inject
	private static CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPDefinitionInventory _cpDefinitionInventory;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}