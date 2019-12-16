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
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataRecordCollectionTestUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
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

	@Ignore
	@Override
	@Test
	public void testGetDataRecordCollectionDataRecordExport() throws Exception {
		super.testGetDataRecordCollectionDataRecordExport();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteDataRecord() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDataRecord() {
	}

	@Override
	@Test
	public void testPostDataRecordCollectionDataRecord() throws Exception {
		super.testPostDataRecordCollectionDataRecord();

		assertHttpResponseStatusCode(
			404,
			dataRecordResource.postDataRecordCollectionDataRecordHttpResponse(
				RandomTestUtil.randomLong(), randomDataRecord()));

		DataRecord dataRecord = _createDataRecord(
			RandomTestUtil.randomString());

		assertHttpResponseStatusCode(
			400,
			dataRecordResource.postDataRecordCollectionDataRecordHttpResponse(
				dataRecord.getDataRecordCollectionId(), dataRecord));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataRecordCollectionId", "dataRecordValues"};
	}

	@Override
	protected DataRecord randomDataRecord() {
		return _createDataRecord("MyText");
	}

	@Override
	protected DataRecord randomIrrelevantDataRecord() throws Exception {
		DataRecord randomIrrelevantDataRecord =
			super.randomIrrelevantDataRecord();

		randomIrrelevantDataRecord.setDataRecordCollectionId(
			_irrelevantDDLRecordSet.getRecordSetId());

		return randomIrrelevantDataRecord;
	}

	@Override
	protected DataRecord testDeleteDataRecord_addDataRecord() throws Exception {
		return dataRecordResource.postDataRecordCollectionDataRecord(
			_ddlRecordSet.getRecordSetId(), randomDataRecord());
	}

	@Override
	protected Long testGetDataDefinitionDataRecordsPage_getDataDefinitionId()
		throws Exception {

		return _ddmStructure.getStructureId();
	}

	@Override
	protected DataRecord testGetDataRecord_addDataRecord() throws Exception {
		return dataRecordResource.postDataRecordCollectionDataRecord(
			_ddlRecordSet.getRecordSetId(), randomDataRecord());
	}

	@Override
	protected DataRecord
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				Long dataLayoutId, DataRecord dataRecord)
		throws Exception {

		return dataRecordResource.postDataRecordCollectionDataRecord(
			dataRecord.getDataRecordCollectionId(), dataRecord);
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

		return dataRecordResource.postDataRecordCollectionDataRecord(
			dataRecord.getDataRecordCollectionId(), dataRecord);
	}

	@Override
	protected DataRecord testPutDataRecord_addDataRecord() throws Exception {
		return dataRecordResource.postDataRecordCollectionDataRecord(
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