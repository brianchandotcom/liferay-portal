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
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class DataListViewResourceTest extends BaseDataListViewResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_dataDefinition = DataDefinitionTestUtil.addDataDefinition(
			testGroup.getGroupId());
		_irrelevantDataDefinition = DataDefinitionTestUtil.addDataDefinition(
			irrelevantGroup.getGroupId());
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteDataListView() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDataListView() {
	}

	@Override
	protected DataListView randomDataListView() throws Exception {
		DataListView dataListView = super.randomDataListView();

		dataListView.setFieldNames(
			new String[] {RandomTestUtil.randomString()});

		return dataListView;
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
	protected DataListView testPutDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	private DataDefinition _dataDefinition;
	private DataDefinition _irrelevantDataDefinition;

}