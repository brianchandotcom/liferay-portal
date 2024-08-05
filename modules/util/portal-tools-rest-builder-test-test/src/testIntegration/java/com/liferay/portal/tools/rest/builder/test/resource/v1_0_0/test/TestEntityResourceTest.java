/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.TestEntity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class TestEntityResourceTest extends BaseTestEntityResourceTestCase {

	@Override
	@Test
	public void testGetTestEntity() throws Exception {
		int initialCount = testEntityResource.getTestEntityCount();
		TestEntity testEntity = testEntityResource.postTestEntity(
			randomTestEntity());

		Assert.assertEquals(
			testEntity, testEntityResource.getTestEntity((long)initialCount));
	}

	@Override
	@Test
	public void testGetTestEntityCount() throws Exception {
		int initialCount = testEntityResource.getTestEntityCount();

		testEntityResource.postTestEntity(randomTestEntity());

		Assert.assertEquals(
			Integer.valueOf(initialCount + 1),
			testEntityResource.getTestEntityCount());
	}

	@Override
	@Test
	public void testPostReservedWord() throws Exception {
		testEntityResource.postReservedWord(true);
	}

	@Override
	protected TestEntity testGetTestEntitiesPage_addTestEntity(
			TestEntity testEntity)
		throws Exception {

		return testEntityResource.postTestEntity(testEntity);
	}

	@Override
	protected TestEntity testPostTestEntity_addTestEntity(TestEntity testEntity)
		throws Exception {

		return testGetTestEntitiesPage_addTestEntity(testEntity);
	}

}