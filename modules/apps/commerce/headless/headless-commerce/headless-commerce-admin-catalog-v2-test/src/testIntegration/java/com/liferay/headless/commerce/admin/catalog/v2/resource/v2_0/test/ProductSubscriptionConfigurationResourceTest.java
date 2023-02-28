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
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.ProductSubscriptionConfiguration;
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
public class ProductSubscriptionConfigurationResourceTest
	extends BaseProductSubscriptionConfigurationResourceTestCase {

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
	public void testGetProductByExternalReferenceCodeSubscriptionConfiguration()
		throws Exception {

		super.testGetProductByExternalReferenceCodeSubscriptionConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductIdSubscriptionConfiguration() throws Exception {
		super.testGetProductIdSubscriptionConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeSubscriptionConfiguration()
		throws Exception {

		super.
			testGraphQLGetProductByExternalReferenceCodeSubscriptionConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeSubscriptionConfigurationNotFound()
		throws Exception {

		super.
			testGraphQLGetProductByExternalReferenceCodeSubscriptionConfigurationNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductIdSubscriptionConfiguration()
		throws Exception {

		super.testGraphQLGetProductIdSubscriptionConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductIdSubscriptionConfigurationNotFound()
		throws Exception {

		super.testGraphQLGetProductIdSubscriptionConfigurationNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testPatchProductByExternalReferenceCodeSubscriptionConfiguration()
		throws Exception {

		super.
			testPatchProductByExternalReferenceCodeSubscriptionConfiguration();
	}

	@Ignore
	@Override
	@Test
	public void testPatchProductIdSubscriptionConfiguration() throws Exception {
		super.testPatchProductIdSubscriptionConfiguration();
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
				subscriptionType = SubscriptionType.create(
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

}