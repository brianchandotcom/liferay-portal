/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.ChildTestEntity1;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.ChildTestEntity2;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.TestEntity;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class TestEntityResourceTest extends BaseTestEntityResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testBatchEngineDeleteImportTask() throws Exception {
		super.testBatchEngineDeleteImportTask();
	}

	@Ignore
	@Override
	@Test
	public void testDeleteTestEntityBatch() throws Exception {
		super.testDeleteTestEntityBatch();
	}

	@Ignore
	@Override
	@Test
	public void testGetTestEntitiesPageWithFilterDateTimeEquals()
		throws Exception {

		super.testGetTestEntitiesPageWithFilterDateTimeEquals();
	}

	@Ignore
	@Override
	@Test
	public void testGetTestEntitiesPageWithFilterStringContains()
		throws Exception {

		super.testGetTestEntitiesPageWithFilterStringContains();
	}

	@Ignore
	@Override
	@Test
	public void testGetTestEntitiesPageWithFilterStringEquals()
		throws Exception {

		super.testGetTestEntitiesPageWithFilterStringEquals();
	}

	@Ignore
	@Override
	@Test
	public void testGetTestEntitiesPageWithFilterStringStartsWith()
		throws Exception {

		super.testGetTestEntitiesPageWithFilterStringStartsWith();
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

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteTestEntity() throws Exception {
		super.testGraphQLDeleteTestEntity();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetTestEntitiesPage() throws Exception {
		super.testGraphQLGetTestEntitiesPage();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetTestEntity() throws Exception {
		super.testGraphQLGetTestEntity();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetTestEntityNotFound() throws Exception {
		super.testGraphQLGetTestEntityNotFound();
	}

	@Override
	@Test
	public void testPatchTestEntity() throws Exception {
		super.testPatchTestEntity();

		ChildTestEntity1 postChildTestEntity1 = new ChildTestEntity1();

		postChildTestEntity1.setProperty1(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
		postChildTestEntity1.setType(
			TestEntity.Type.create("ChildTestEntity1"));

		postChildTestEntity1 =
			(ChildTestEntity1)testEntityResource.postTestEntity(
				postChildTestEntity1);

		// Patch child test entity 1

		ChildTestEntity1 randomPatchChildTestEntity1 = new ChildTestEntity1();

		randomPatchChildTestEntity1.setProperty1(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
		randomPatchChildTestEntity1.setType(
			TestEntity.Type.create("ChildTestEntity1"));

		ChildTestEntity1 patchChildTestEntity1 =
			(ChildTestEntity1)testEntityResource.patchTestEntity(
				postChildTestEntity1.getId(),
				testPatchTestEntity_getOptionalParameter(),
				randomPatchChildTestEntity1);

		ChildTestEntity1 expectedPatchChildTestEntity1 =
			postChildTestEntity1.clone();

		BeanTestUtil.copyProperties(
			randomPatchChildTestEntity1, expectedPatchChildTestEntity1);

		ChildTestEntity1 getChildTestEntity1 =
			(ChildTestEntity1)testEntityResource.getTestEntity(
				patchChildTestEntity1.getId());

		assertEquals(expectedPatchChildTestEntity1, getChildTestEntity1);
		assertValid(getChildTestEntity1);

		// Patch child test entity 2

		ChildTestEntity2 randomPatchChildTestEntity2 = new ChildTestEntity2();

		randomPatchChildTestEntity2.setProperty2(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
		randomPatchChildTestEntity2.setType(
			TestEntity.Type.create("ChildTestEntity2"));

		ChildTestEntity2 patchChildTestEntity2 =
			(ChildTestEntity2)testEntityResource.patchTestEntity(
				postChildTestEntity1.getId(),
				testPatchTestEntity_getOptionalParameter(),
				randomPatchChildTestEntity2);

		ChildTestEntity2 expectedPatchChildTestEntity2 = new ChildTestEntity2();

		BeanTestUtil.copyProperties(
			postChildTestEntity1, expectedPatchChildTestEntity2);

		BeanTestUtil.copyProperties(
			randomPatchChildTestEntity2, expectedPatchChildTestEntity2);

		ChildTestEntity2 getChildTestEntity2 =
			(ChildTestEntity2)testEntityResource.getTestEntity(
				patchChildTestEntity2.getId());

		assertEquals(expectedPatchChildTestEntity2, getChildTestEntity2);
		assertValid(getChildTestEntity2);
	}

	@Override
	@Test
	public void testPostReservedWord() throws Exception {
		testEntityResource.postReservedWord(true);
	}

	@Ignore
	@Test
	public void testPostTestEntityMultipartBulk() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testVulcanCRUDItemDelegateGetItem() throws Exception {
		super.testVulcanCRUDItemDelegateGetItem();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"property1", "property2"};
	}

	@Override
	protected TestEntity testDeleteTestEntity_addTestEntity() throws Exception {
		return testGetTestEntitiesPage_addTestEntity(randomTestEntity());
	}

	@Override
	protected TestEntity testGetTestEntitiesPage_addTestEntity(
			TestEntity testEntity)
		throws Exception {

		return testEntityResource.postTestEntity(testEntity);
	}

	@Override
	protected TestEntity testPatchTestEntity_addTestEntity() throws Exception {
		return testGetTestEntitiesPage_addTestEntity(randomTestEntity());
	}

	@Override
	protected Long testPatchTestEntity_getOptionalParameter() {
		return RandomTestUtil.nextLong();
	}

	@Override
	protected TestEntity testPostTestEntity_addTestEntity(TestEntity testEntity)
		throws Exception {

		return testGetTestEntitiesPage_addTestEntity(testEntity);
	}

	@Override
	protected TestEntity testPutTestEntity_addTestEntity() throws Exception {
		return testGetTestEntitiesPage_addTestEntity(randomTestEntity());
	}

	@Override
	protected Long testPutTestEntity_getOptionalParameter() {
		return RandomTestUtil.nextLong();
	}

}