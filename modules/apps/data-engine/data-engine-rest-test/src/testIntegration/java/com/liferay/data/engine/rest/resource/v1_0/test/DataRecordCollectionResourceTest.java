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

package com.liferay.data.engine.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataRecordCollectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class DataRecordCollectionResourceTest
	extends BaseDataRecordCollectionResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_dataDefinition = DataDefinitionTestUtil.postSiteDataDefinition(
			testGroup.getGroupId(),
			DataDefinitionTestUtil.createDataDefinition(
				"MyText", RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), testGroup.getGroupId()));
		_irrelevantDataDefinition =
			DataDefinitionTestUtil.postSiteDataDefinition(
				irrelevantGroup.getGroupId(),
				DataDefinitionTestUtil.createDataDefinition(
					"MyText", RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					irrelevantGroup.getGroupId()));
	}

	@Override
	@Test
	public void testGetDataDefinitionDataRecordCollectionsPage()
		throws Exception {

		super.testGetDataDefinitionDataRecordCollectionsPage();

		_testGetDataDefinitionDataRecordCollectionsPage(
			"!@#description", "!@#d", "name");
		_testGetDataDefinitionDataRecordCollectionsPage(
			"CoLLeCTion dEsCrIpTiOn", "COLLECTION", "name");
		_testGetDataDefinitionDataRecordCollectionsPage(
			"definition", "abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789");
		_testGetDataDefinitionDataRecordCollectionsPage(
			"description", "π€†", "π€† name");
	}

	@Override
	@Test
	public void testGetSiteDataRecordCollectionsPage() throws Exception {
		super.testGetSiteDataRecordCollectionsPage();

		_testGetSiteDataRecordCollectionsPage("!@#description", "!@#d", "name");
		_testGetSiteDataRecordCollectionsPage(
			"CoLLeCTion dEsCrIpTiOn", "COLLECTION", "name");
		_testGetSiteDataRecordCollectionsPage(
			"definition", "abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789");
		_testGetSiteDataRecordCollectionsPage("description", "π€†", "π€† name");
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteDataRecordCollection() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDataRecordCollection() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteDataRecordCollection() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteDataRecordCollectionsPage() {
	}

	@Override
	@Test
	public void testPostDataDefinitionDataRecordCollection() throws Exception {
		super.testPostDataDefinitionDataRecordCollection();

		assertHttpResponseStatusCode(
			404,
			dataRecordCollectionResource.
				postDataDefinitionDataRecordCollectionHttpResponse(
					0L, randomDataRecordCollection()));
	}

	@Ignore
	@Override
	@Test
	public void testPostDataRecordCollectionDataRecordCollectionPermission()
		throws Exception {

		super.testPostDataRecordCollectionDataRecordCollectionPermission();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteDataRecordCollectionPermission() throws Exception {
		super.testPostSiteDataRecordCollectionPermission();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataDefinitionId", "name"};
	}

	@Override
	protected DataRecordCollection randomDataRecordCollection() {
		return DataRecordCollectionTestUtil.createDataRecordCollection(
			_dataDefinition.getId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), testGroup.getGroupId());
	}

	@Override
	protected DataRecordCollection randomIrrelevantDataRecordCollection()
		throws Exception {

		DataRecordCollection randomIrrelevantDataRecordCollection =
			super.randomIrrelevantDataRecordCollection();

		randomIrrelevantDataRecordCollection.setDataDefinitionId(
			_irrelevantDataDefinition.getId());

		return randomIrrelevantDataRecordCollection;
	}

	@Override
	protected DataRecordCollection
			testDeleteDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_dataDefinition.getId(), randomDataRecordCollection());
	}

	@Override
	protected Long
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId()
		throws Exception {

		return _dataDefinition.getId();
	}

	@Override
	protected DataRecordCollection
			testGetDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_dataDefinition.getId(), randomDataRecordCollection());
	}

	@Override
	protected DataRecordCollection
			testGetSiteDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_dataDefinition.getId(), randomDataRecordCollection());
	}

	@Override
	protected DataRecordCollection
			testGetSiteDataRecordCollectionsPage_addDataRecordCollection(
				Long siteId, DataRecordCollection dataRecordCollection)
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				dataRecordCollection.getDataDefinitionId(),
				dataRecordCollection);
	}

	@Override
	protected DataRecordCollection
			testPostDataDefinitionDataRecordCollection_addDataRecordCollection(
				DataRecordCollection dataRecordCollection)
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				dataRecordCollection.getDataDefinitionId(),
				dataRecordCollection);
	}

	@Override
	protected DataRecordCollection
			testPutDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_dataDefinition.getId(), randomDataRecordCollection());
	}

	private void _testGetDataDefinitionDataRecordCollectionsPage(
			String description, String keywords, String name)
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId();

		DataRecordCollection dataRecordCollection =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId,
				DataRecordCollectionTestUtil.createDataRecordCollection(
					_dataDefinition.getId(), description, name,
					testGroup.getGroupId()));

		Page<DataRecordCollection> page =
			dataRecordCollectionResource.
				getDataDefinitionDataRecordCollectionsPage(
					dataDefinitionId, keywords, Pagination.of(1, 2));

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecordCollection),
			(List<DataRecordCollection>)page.getItems());
		assertValid(page);

		dataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollection.getId());
	}

	private void _testGetSiteDataRecordCollectionsPage(
			String description, String keywords, String name)
		throws Exception {

		Long siteId = testGetSiteDataRecordCollectionsPage_getSiteId();

		DataRecordCollection dataRecordCollection =
			testGetSiteDataRecordCollectionsPage_addDataRecordCollection(
				siteId,
				DataRecordCollectionTestUtil.createDataRecordCollection(
					_dataDefinition.getId(), description, name,
					testGroup.getGroupId()));

		Page<DataRecordCollection> page =
			dataRecordCollectionResource.getSiteDataRecordCollectionsPage(
				siteId, keywords, Pagination.of(1, 2));

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecordCollection),
			(List<DataRecordCollection>)page.getItems());
		assertValid(page);

		dataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollection.getId());
	}

	private DataDefinition _dataDefinition;
	private DataDefinition _irrelevantDataDefinition;

}