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
import com.liferay.data.engine.rest.client.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.HashMap;

import org.junit.Before;
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

		_ddmStructure = DataDefinitionTestUtil.addDDMStructure(testGroup);
		_irrelevantDDMStructure = DataDefinitionTestUtil.addDDMStructure(
			irrelevantGroup);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataDefinitionId", "name"};
	}

	@Override
	protected DataRecordCollection randomDataRecordCollection() {
		return new DataRecordCollection() {
			{
				dataDefinitionId = _ddmStructure.getStructureId();
				dataRecordCollectionKey = RandomTestUtil.randomString();
				name = new HashMap<String, Object>() {
					{
						put("en_US", RandomTestUtil.randomString());
					}
				};
				siteId = testGroup.getGroupId();
			}
		};
	}

	@Override
	protected DataRecordCollection randomIrrelevantDataRecordCollection()
		throws Exception {

		DataRecordCollection randomIrrelevantDataRecordCollection =
			super.randomIrrelevantDataRecordCollection();

		randomIrrelevantDataRecordCollection.setDataDefinitionId(
			_irrelevantDDMStructure.getStructureId());

		return randomIrrelevantDataRecordCollection;
	}

	@Override
	protected DataRecordCollection
			testDeleteDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(), randomDataRecordCollection());
	}

	@Override
	protected Long
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId()
		throws Exception {

		return _ddmStructure.getStructureId();
	}

	@Override
	protected DataRecordCollection
			testGetDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(), randomDataRecordCollection());
	}

	@Override
	protected DataRecordCollection
			testGetSiteDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(), randomDataRecordCollection());
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
				_ddmStructure.getStructureId(), randomDataRecordCollection());
	}

	private DDMStructure _ddmStructure;
	private DDMStructure _irrelevantDDMStructure;

}