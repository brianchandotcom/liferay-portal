/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.TestObject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class TestObjectResourceTest extends BaseTestObjectResourceTestCase {

	@Override
	@Test
	public void testGetTestObject() throws Exception {
		int initialCount = testObjectResource.getTestObjectCount();
		TestObject testObject = testObjectResource.postTestObject(
			randomTestObject());

		Assert.assertEquals(
			testObject, testObjectResource.getTestObject((long)initialCount));
	}

	@Override
	@Test
	public void testGetTestObjectCount() throws Exception {
		int initialCount = testObjectResource.getTestObjectCount();

		testObjectResource.postTestObject(randomTestObject());

		Assert.assertEquals(
			Integer.valueOf(initialCount + 1),
			testObjectResource.getTestObjectCount());
	}

	@Override
	@Test
	public void testPostReservedWord() throws Exception {
		testObjectResource.postReservedWord(true);
	}

	@Override
	protected TestObject testGetTestObjectsPage_addTestObject(
			TestObject testObject)
		throws Exception {

		return testObjectResource.postTestObject(testObject);
	}

	@Override
	protected TestObject testPostTestObject_addTestObject(TestObject testObject)
		throws Exception {

		return testGetTestObjectsPage_addTestObject(testObject);
	}

}