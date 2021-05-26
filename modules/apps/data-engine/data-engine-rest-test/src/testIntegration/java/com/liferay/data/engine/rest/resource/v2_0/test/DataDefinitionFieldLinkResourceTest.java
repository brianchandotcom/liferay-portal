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
import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionFieldLink;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.client.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataDefinitionTestUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class DataDefinitionFieldLinkResourceTest
	extends BaseDataDefinitionFieldLinkResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dataDefinition =
			DataDefinitionTestUtil.addDataDefinitionWithDataLayout(
				testGroup.getGroupId());

		DataDefinitionResource.Builder builder =
			DataDefinitionResource.builder();

		_dataDefinitionResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@Override
	protected DataDefinitionFieldLink randomDataDefinitionFieldLink()
		throws Exception {

		DataDefinition dataDefinition = DataDefinition.toDTO(
			DataDefinitionTestUtil.read("data-definition-fieldset.json"));

		DataLayout dataLayout = _dataDefinition.getDefaultDataLayout();

		DataDefinitionField[] dataDefinitionFields =
			dataDefinition.getDataDefinitionFields();

		DataDefinitionField dataDefinitionField = dataDefinitionFields[0];

		dataDefinitionField.setCustomProperties(
			HashMapBuilder.<String, Object>put(
				"ddmStructureId", _dataDefinition.getId()
			).put(
				"ddmStructureLayoutId", dataLayout.getId()
			).build());

		DataDefinitionFieldLink dataDefinitionFieldLink =
			new DataDefinitionFieldLink();

		dataDefinitionFieldLink.setDataDefinition(dataDefinition);
		dataDefinitionFieldLink.setDataLayouts(new DataLayout[] {dataLayout});

		return dataDefinitionFieldLink;
	}

	@Override
	protected DataDefinitionFieldLink
			testGetDataDefinitionDataDefinitionFieldLinkPage_addDataDefinitionFieldLink(
				Long dataDefinitionId,
				DataDefinitionFieldLink dataDefinitionFieldLink)
		throws Exception {

		_dataDefinitionResource.postDataDefinitionByContentType(
			"test", dataDefinitionFieldLink.getDataDefinition());

		return dataDefinitionFieldLink;
	}

	@Override
	protected Long
			testGetDataDefinitionDataDefinitionFieldLinkPage_getDataDefinitionId()
		throws Exception {

		return _dataDefinition.getId();
	}

	private DataDefinition _dataDefinition;
	private DataDefinitionResource _dataDefinitionResource;

}