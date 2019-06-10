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
import com.liferay.data.engine.rest.client.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.client.resource.v1_0.DataRecordResource;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataRecordCollectionTestUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataRecordResourceTest extends BaseDataRecordResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_ddmStructure = DataDefinitionTestUtil.addDDMStructure(testGroup);

		_ddlRecordSet = DataRecordCollectionTestUtil.addRecordSet(
			_ddmStructure, testGroup, _resourceLocalService);
		_irrelevantDDLRecordSet = DataRecordCollectionTestUtil.addRecordSet(
			_ddmStructure, irrelevantGroup, _resourceLocalService);
	}

	@Test
	public void testGetDataRecordWithInvalidId() throws Exception {
		assertHttpResponseStatusCode(
			404,
			DataRecordResource.getDataRecordHttpResponse(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testPostDataRecordWithInvalidDataRecordCollection()
		throws Exception {

		assertHttpResponseStatusCode(
			404,
			DataRecordResource.postDataRecordCollectionDataRecordHttpResponse(
				RandomTestUtil.randomLong(), randomDataRecord()));
	}

	@Test
	public void testPostDataRecordWithoutMatchingDataDefinitionFields()
		throws Exception {

		DataRecord dataRecord = _createDataRecord("Wrong Field");

		assertHttpResponseStatusCode(
			400,
			DataRecordResource.postDataRecordCollectionDataRecordHttpResponse(
				dataRecord.getDataRecordCollectionId(), dataRecord));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataRecordCollectionId", "dataRecordValues"};
	}

	@Override
	protected DataRecord randomDataRecord() throws Exception {
		return _createDataRecord("MyText");
	}

	@Override
	protected DataRecord randomIrrelevantDataRecord() throws Exception {
		DataRecord randomIrrelevantDataRecord = randomDataRecord();

		randomIrrelevantDataRecord.setDataRecordCollectionId(
			_irrelevantDDLRecordSet.getRecordSetId());

		return randomIrrelevantDataRecord;
	}

	@Override
	protected DataRecord testDeleteDataRecord_addDataRecord() throws Exception {
		return DataRecordResource.postDataRecordCollectionDataRecord(
			_ddlRecordSet.getRecordSetId(), randomDataRecord());
	}

	@Override
	protected DataRecord testGetDataRecord_addDataRecord() throws Exception {
		return DataRecordResource.postDataRecordCollectionDataRecord(
			_ddlRecordSet.getRecordSetId(), randomDataRecord());
	}

	@Override
	protected DataRecord
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				Long dataLayoutId, DataRecord dataRecord)
		throws Exception {

		long dataRecordCollectionId = _ddlRecordSet.getRecordSetId();

		if (dataLayoutId == _irrelevantDDLRecordSet.getDDMStructureId()) {
			dataRecordCollectionId = _irrelevantDDLRecordSet.getRecordSetId();
		}

		return DataRecordResource.postDataRecordCollectionDataRecord(
			dataRecordCollectionId, randomDataRecord());
	}

	@Override
	protected Long
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId()
		throws Exception {

		return _ddlRecordSet.getRecordSetId();
	}

	@Override
	protected DataRecord testPostDataRecordCollectionDataRecord_addDataRecord(
			DataRecord dataRecord)
		throws Exception {

		return DataRecordResource.postDataRecordCollectionDataRecord(
			dataRecord.getDataRecordCollectionId(), dataRecord);
	}

	@Override
	protected DataRecord testPutDataRecord_addDataRecord() throws Exception {
		return DataRecordResource.postDataRecordCollectionDataRecord(
			_ddlRecordSet.getRecordSetId(), randomDataRecord());
	}

	private DataRecord _createDataRecord(String fieldName) {
		return new DataRecord() {
			{
				dataRecordCollectionId = _ddlRecordSet.getRecordSetId();
				dataRecordValues = new HashMap<String, Object>() {
					{
						put(fieldName, RandomTestUtil.randomString());
					}
				};
			}
		};
	}

	private DDLRecordSet _ddlRecordSet;
	private DDMStructure _ddmStructure;
	private DDLRecordSet _irrelevantDDLRecordSet;

	@Inject
	private ResourceLocalService _resourceLocalService;

}