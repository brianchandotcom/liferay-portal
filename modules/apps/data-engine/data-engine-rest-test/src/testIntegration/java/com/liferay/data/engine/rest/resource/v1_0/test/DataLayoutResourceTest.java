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
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcelo Mello
 */
@RunWith(Arquillian.class)
public class DataLayoutResourceTest extends BaseDataLayoutResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_ddmStructure = DataDefinitionTestUtil.addDDMStructure(testGroup);
		_irrelevantDDMStructure = DataDefinitionTestUtil.addDDMStructure(
			irrelevantGroup);
	}

	@Test
	public void testPostDataDefinitionDataLayout_addMultiplesDataLayout()
		throws Exception {

		DataLayout randomDataLayout = randomDataLayout();

		DataLayout postDataLayout1 =
			testPostDataDefinitionDataLayout_addDataLayout(randomDataLayout);

		DataLayout postDataLayout2 =
			testPostDataDefinitionDataLayout_addDataLayout(randomDataLayout);

		DataLayout postDataLayout3 =
			testPostDataDefinitionDataLayout_addDataLayout(randomDataLayout);

		assertEquals(randomDataLayout, postDataLayout1);
		assertEquals(randomDataLayout, postDataLayout2);
		assertEquals(randomDataLayout, postDataLayout3);

		assertValid(postDataLayout1);
		assertValid(postDataLayout2);
		assertValid(postDataLayout3);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataDefinitionId", "name"};
	}

	@Override
	protected DataLayout randomDataLayout() {
		return new DataLayout() {
			{
				dataDefinitionId = _ddmStructure.getStructureId();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				defaultLanguageId = "en_US";
				id = RandomTestUtil.randomLong();
				name = new HashMap<String, Object>() {
					{
						put("en_US", RandomTestUtil.randomString());
					}
				};
			}
		};
	}

	@Override
	protected DataLayout randomIrrelevantDataLayout() {
		DataLayout dataLayout = randomDataLayout();

		dataLayout.setDataDefinitionId(
			_irrelevantDDMStructure.getStructureId());

		return dataLayout;
	}

	@Override
	protected DataLayout testDeleteDataLayout_addDataLayout() throws Exception {
		return dataLayoutResource.postDataDefinitionDataLayout(
			_ddmStructure.getStructureId(), randomDataLayout());
	}

	@Override
	protected Long testGetDataDefinitionDataLayoutsPage_getDataDefinitionId()
		throws Exception {

		return _ddmStructure.getStructureId();
	}

	@Override
	protected DataLayout testGetDataLayout_addDataLayout() throws Exception {
		return dataLayoutResource.postDataDefinitionDataLayout(
			_ddmStructure.getStructureId(), randomDataLayout());
	}

	@Override
	protected DataLayout testGetSiteDataLayoutPage_addDataLayout(
			Long siteId, DataLayout dataLayout)
		throws Exception {

		return dataLayoutResource.postDataDefinitionDataLayout(
			dataLayout.getDataDefinitionId(), dataLayout);
	}

	@Override
	protected DataLayout testPutDataLayout_addDataLayout() throws Exception {
		return dataLayoutResource.postDataDefinitionDataLayout(
			_ddmStructure.getStructureId(), randomDataLayout());
	}

	private DDMStructure _ddmStructure;
	private DDMStructure _irrelevantDDMStructure;

}