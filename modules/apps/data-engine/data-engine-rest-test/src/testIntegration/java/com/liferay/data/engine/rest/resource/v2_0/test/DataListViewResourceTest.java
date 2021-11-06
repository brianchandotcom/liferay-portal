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
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataDefinitionTestUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class DataListViewResourceTest extends BaseDataListViewResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dataDefinition = DataDefinitionTestUtil.addDataDefinition(
			testGroup.getGroupId());
		_irrelevantDataDefinition = DataDefinitionTestUtil.addDataDefinition(
			irrelevantGroup.getGroupId());
	}

	@Override
	@Test
	public void testGraphQLGetDataListView() throws Exception {
		DataListView dataListView = testGraphQLDataListView_addDataListView();

		JSONObject dataListViewJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(
				new GraphQLField(
					"dataListView",
					HashMapBuilder.<String, Object>put(
						"dataListViewId", dataListView.getId()
					).build(),
					getGraphQLFields())),
			"JSONObject/data", "JSONObject/dataListView");

		Assert.assertEquals(
			GetterUtil.getLong(dataListView.getDataDefinitionId()),
			dataListViewJSONObject.getLong("dataDefinitionId"));
		Assert.assertEquals(
			GetterUtil.getString(
				ArrayUtil.getValue(dataListView.getFieldNames(), 0)),
			JSONUtil.getValueAsString(
				dataListViewJSONObject, "JSONArray/fieldNames", "Object/0"));
		Assert.assertEquals(
			GetterUtil.getString(dataListView.getSortField()),
			dataListViewJSONObject.getString("sortField"));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataDefinitionId", "fieldNames", "sortField"};
	}

	@Override
	protected DataListView randomDataListView() throws Exception {
		DataListView dataListView = super.randomDataListView();

		dataListView.setDataDefinitionId(_dataDefinition.getId());
		dataListView.setFieldNames(
			new String[] {RandomTestUtil.randomString()});

		return dataListView;
	}

	@Override
	protected DataListView
			testDeleteDataDefinitionDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	@Override
	protected DataListView testDeleteDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	@Override
	protected Long testGetDataDefinitionDataListViewsPage_getDataDefinitionId()
		throws Exception {

		return _dataDefinition.getId();
	}

	@Override
	protected Long
			testGetDataDefinitionDataListViewsPage_getIrrelevantDataDefinitionId()
		throws Exception {

		return _irrelevantDataDefinition.getId();
	}

	@Override
	protected DataListView testGetDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	@Override
	protected DataListView testGraphQLDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	@Override
	protected DataListView testPutDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	private DataDefinition _dataDefinition;
	private DataDefinition _irrelevantDataDefinition;

}