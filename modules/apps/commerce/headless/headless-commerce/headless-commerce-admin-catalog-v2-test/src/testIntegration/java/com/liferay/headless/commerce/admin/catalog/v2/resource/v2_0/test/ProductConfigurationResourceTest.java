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
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.ProductConfiguration;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
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

	@Ignore
	@Override
	@Test
	public void testPatchProductIdConfiguration() throws Exception {
		super.testPatchProductIdConfiguration();
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

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPDefinitionInventory _cpDefinitionInventory;

	@Inject
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

}