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
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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

	@Override
	@Test
	public void testGetDataDefinitionDataLayoutsPage() throws Exception {
		super.testGetDataDefinitionDataLayoutsPage();

		Page<DataLayout> page =
			dataLayoutResource.getDataDefinitionDataLayoutsPage(
				testGetDataDefinitionDataLayoutsPage_getDataDefinitionId(),
				"layout", Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		_testGetDataDefinitionDataLayoutsPage("!@#l", "!@#layout");
		_testGetDataDefinitionDataLayoutsPage("FORM", "FoRmSLaYoUt");
		_testGetDataDefinitionDataLayoutsPage(
			"abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789");
		_testGetDataDefinitionDataLayoutsPage("form layout", "form layout");
		_testGetDataDefinitionDataLayoutsPage("layo", "form layout");
		_testGetDataDefinitionDataLayoutsPage("π€† layout", "π€† layout");
	}

	@Override
	@Test
	public void testGetSiteDataLayoutPage() throws Exception {
		super.testGetSiteDataLayoutPage();

		Page<DataLayout> page = dataLayoutResource.getSiteDataLayoutPage(
			testGetSiteDataLayoutPage_getSiteId(), "form layout",
			Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		_testGetSiteDataLayoutPage("!@#la", "!@#layout");
		_testGetSiteDataLayoutPage("FORM", "FoRmSLaYoUt");
		_testGetSiteDataLayoutPage(
			"abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789");
		_testGetSiteDataLayoutPage("form layout", "form layout");
		_testGetSiteDataLayoutPage("layo", "form layout");
		_testGetSiteDataLayoutPage("π€†", "π€† layout");
	}

	@Override
	@Test
	public void testPostDataDefinitionDataLayout() throws Exception {
		super.testPostDataDefinitionDataLayout();

		// Multiple data layouts with the same data definition

		for (int i = 0; i < 3; i++) {
			DataLayout randomDataLayout = randomDataLayout();

			DataLayout postDataLayout =
				testPostDataDefinitionDataLayout_addDataLayout(
					randomDataLayout);

			assertEquals(randomDataLayout, postDataLayout);
			assertValid(postDataLayout);
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataDefinitionId", "name"};
	}

	@Override
	protected DataLayout randomDataLayout() {
		return _createDataLayout(RandomTestUtil.randomString());
	}

	@Override
	protected DataLayout randomIrrelevantDataLayout() throws Exception {
		DataLayout randomIrrelevantDataLayout =
			super.randomIrrelevantDataLayout();

		randomIrrelevantDataLayout.setDataDefinitionId(
			_irrelevantDDMStructure.getStructureId());

		return randomIrrelevantDataLayout;
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
	protected DataLayout testGetSiteDataLayout_addDataLayout()
		throws Exception {

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

	private DataLayout _createDataLayout(String name) {
		DataLayout dataLayout = new DataLayout() {
			{
				dataDefinitionId = _ddmStructure.getStructureId();
				dateCreated = RandomTestUtil.nextDate();
				dataLayoutKey = RandomTestUtil.randomString();
				dateModified = RandomTestUtil.nextDate();
				defaultLanguageId = "en_US";
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
			}
		};

		dataLayout.setName(
			new HashMap<String, Object>() {
				{
					put("en_US", name);
				}
			});

		return dataLayout;
	}

	private void _testGetDataDefinitionDataLayoutsPage(
			String keywords, String name)
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataLayoutsPage_getDataDefinitionId();

		DataLayout dataLayout =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, _createDataLayout(name));

		Page<DataLayout> page =
			dataLayoutResource.getDataDefinitionDataLayoutsPage(
				dataDefinitionId, keywords, Pagination.of(1, 2), null);

		IdempotentRetryAssert.retryAssert(
			8, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(1, page.getTotalCount());

				assertEqualsIgnoringOrder(
					Arrays.asList(dataLayout),
					(List<DataLayout>)page.getItems());
				assertValid(page);

				return null;
			});

		dataLayoutResource.deleteDataLayout(dataLayout.getId());
	}

	private void _testGetSiteDataLayoutPage(String keywords, String name)
		throws Exception {

		Long siteId = testGetSiteDataLayoutPage_getSiteId();

		DataLayout dataLayout = testGetSiteDataLayoutPage_addDataLayout(
			siteId, _createDataLayout(name));

		Page<DataLayout> page = dataLayoutResource.getSiteDataLayoutPage(
			siteId, keywords, Pagination.of(1, 2), null);

		IdempotentRetryAssert.retryAssert(
			8, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(1, page.getTotalCount());

				assertEqualsIgnoringOrder(
					Arrays.asList(dataLayout),
					(List<DataLayout>)page.getItems());
				assertValid(page);

				return null;
			});

		dataLayoutResource.deleteDataLayout(dataLayout.getId());
	}

	private DDMStructure _ddmStructure;
	private DDMStructure _irrelevantDDMStructure;

}