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

package com.liferay.data.engine.rest.resource.v2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.client.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.client.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.resource.v2_0.DataListViewResource;
import com.liferay.data.engine.rest.client.resource.v2_0.DataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataDefinitionTestUtil;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataRecordCollectionTestUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class DataRecordResourceTest extends BaseDataRecordResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dataDefinition = DataDefinitionTestUtil.addDataDefinition(
			DataDefinition.toDTO(
				DataDefinitionTestUtil.read("data-definition.json")),
			testGroup.getGroupId());

		DataRecordCollectionResource.Builder builder =
			DataRecordCollectionResource.builder();

		DataRecordCollectionResource dataRecordCollectionResource =
			builder.authentication(
				"test@liferay.com", "test"
			).locale(
				LocaleUtil.getDefault()
			).build();

		_dataRecordCollection =
			dataRecordCollectionResource.getDataDefinitionDataRecordCollection(
				_dataDefinition.getId());

		_irrelevantDDLRecordSet = DataRecordCollectionTestUtil.addRecordSet(
			_dataDefinition, irrelevantGroup, _resourceLocalService);
	}

	@Override
	@Test
	public void testGetDataRecordCollectionDataRecordExport() throws Exception {
		DataRecord dataRecord = testGetDataRecord_addDataRecord();

		assertHttpResponseStatusCode(
			200,
			dataRecordResource.
				getDataRecordCollectionDataRecordExportHttpResponse(
					dataRecord.getDataRecordCollectionId(),
					Pagination.of(1, 2)));
	}

	@Override
	@Test
	public void testGetDataRecordCollectionDataRecordsPage() throws Exception {
		super.testGetDataRecordCollectionDataRecordsPage();

		// Retrieve data records according to fixed filters

		DataListViewResource.Builder builder = DataListViewResource.builder();

		DataListViewResource dataListViewResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();

		DataListView dataListView =
			dataListViewResource.postDataDefinitionDataListView(
				_dataDefinition.getId(),
				new DataListView() {
					{
						appliedFilters =
							LinkedHashMapBuilder.<String, Object>put(
								"SingleSelection", new String[] {"Car"}
							).build();
						dataDefinitionId = _dataDefinition.getId();
						fieldNames = new String[] {"SingleSelection"};
					}
				});

		Long dataRecordCollectionId =
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId();

		DataRecord dataRecord =
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				dataRecordCollectionId,
				new DataRecord() {
					{
						dataRecordCollectionId = _dataRecordCollection.getId();
						dataRecordValues = HashMapBuilder.<String, Object>put(
							"SingleSelection",
							HashMapBuilder.put(
								"en_US", new String[] {"Car"}
							).build()
						).build();
					}
				});

		testGetDataRecordCollectionDataRecordsPage_addDataRecord(
			dataRecordCollectionId,
			new DataRecord() {
				{
					dataRecordCollectionId = _dataRecordCollection.getId();
					dataRecordValues = HashMapBuilder.<String, Object>put(
						"SingleSelection",
						HashMapBuilder.put(
							"en_US", new String[] {"Boat"}
						).build()
					).build();
				}
			});

		Page<DataRecord> page =
			dataRecordResource.getDataRecordCollectionDataRecordsPage(
				testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId(),
				dataListView.getId(), null, Pagination.of(1, 2), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecord), (List<DataRecord>)page.getItems());
	}

	@Override
	@Test
	public void testPostDataRecordCollectionDataRecord() throws Exception {
		super.testPostDataRecordCollectionDataRecord();

		assertHttpResponseStatusCode(
			404,
			dataRecordResource.postDataRecordCollectionDataRecordHttpResponse(
				RandomTestUtil.randomLong(), randomDataRecord()));
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
			_dataRecordCollection.getId(), randomDataRecord());
	}

	@Override
	protected Long testGetDataDefinitionDataRecordsPage_getDataDefinitionId()
		throws Exception {

		return _dataDefinition.getId();
	}

	@Override
	protected DataRecord testGetDataRecord_addDataRecord() throws Exception {
		return dataRecordResource.postDataRecordCollectionDataRecord(
			_dataRecordCollection.getId(), randomDataRecord());
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

		return _dataRecordCollection.getId();
	}

	@Override
	protected DataRecord testGraphQLDataRecord_addDataRecord()
		throws Exception {

		return dataRecordResource.postDataRecordCollectionDataRecord(
			_dataRecordCollection.getId(), randomDataRecord());
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
			_dataRecordCollection.getId(), randomDataRecord());
	}

	private DataRecord _createDataRecord(String fieldName) {
		return new DataRecord() {
			{
				dataRecordCollectionId = _dataRecordCollection.getId();
				dataRecordValues = HashMapBuilder.<String, Object>put(
					fieldName,
					HashMapBuilder.put(
						"en_US",
						new String[] {
							RandomTestUtil.randomString(),
							RandomTestUtil.randomString()
						}
					).build()
				).build();
			}
		};
	}

	private DataDefinition _dataDefinition;
	private DataRecordCollection _dataRecordCollection;
	private DDLRecordSet _irrelevantDDLRecordSet;

	@Inject
	private ResourceLocalService _resourceLocalService;

}