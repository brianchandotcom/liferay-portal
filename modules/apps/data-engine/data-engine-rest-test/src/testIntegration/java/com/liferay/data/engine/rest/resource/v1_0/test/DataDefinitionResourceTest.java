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
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionPermission;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataDefinitionResourceTest
	extends BaseDataDefinitionResourceTestCase {

	@Override
	public void testPostDataDefinitionDataDefinitionPermission()
		throws Exception {

		DataDefinition dataDefinition =
			testGetSiteDataDefinitionsPage_addDataDefinition(
				testGetSiteDataDefinitionsPage_getSiteId(),
				randomDataDefinition());

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		dataDefinitionResource.postDataDefinitionDataDefinitionPermission(
			dataDefinition.getId(), _OPERATION_SAVE_PERMISSION,
			new DataDefinitionPermission() {
				{
					view = true;
					roleNames = new String[] {role.getName()};
				}
			});
	}

	@Override
	public void testPostSiteDataDefinitionPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		dataDefinitionResource.postSiteDataDefinitionPermission(
			testGroup.getGroupId(), _OPERATION_SAVE_PERMISSION,
			new DataDefinitionPermission() {
				{
					addDataDefinition = true;
					roleNames = new String[] {role.getName()};
				}
			});
	}

	@Test
	public void testSearchNonexistingSiteDataDefinition() throws Exception {
		Page<DataDefinition> page =
			dataDefinitionResource.getSiteDataDefinitionsPage(
				testGetSiteDataDefinitionsPage_getSiteId(), "definition",
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());
	}

	@Test
	public void testSearchSiteDataDefinitionByCaseSensitiveDescription()
		throws Exception {

		testSearchDataDefinition(
			"name", "DeFiNiTiON dEsCrIpTiOn", "DEFINITION");
	}

	@Test
	public void testSearchSiteDataDefinitionByCaseSensitiveName()
		throws Exception {

		testSearchDataDefinition(
			"DeFiNiTiON NaMe", "description", "DEFINITION");
	}

	@Test
	public void testSearchSiteDataDefinitionByDescriptionWithNonasciiChar()
		throws Exception {

		testSearchDataDefinition("name", "π€† description", "π€†");
	}

	@Test
	public void testSearchSiteDataDefinitionByDescriptionWithSpecialASCIIChar()
		throws Exception {

		testSearchDataDefinition("name", "!@#description", "!@#d");
	}

	@Test
	public void testSearchSiteDataDefinitionByFullDescription()
		throws Exception {

		testSearchDataDefinition(
			"definition", "description name", "description name");
	}

	@Test
	public void testSearchSiteDataDefinitionByFullName() throws Exception {
		testSearchDataDefinition(
			"definition name", "description", "definition name");
	}

	@Test
	public void testSearchSiteDataDefinitionByLongDescription()
		throws Exception {

		testSearchDataDefinition(
			"definition", "abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789");
	}

	@Test
	public void testSearchSiteDataDefinitionByNameWithNonasciiChar()
		throws Exception {

		testSearchDataDefinition("π€† name", "description", "π€†");
	}

	@Test
	public void testSearchSiteDataDefinitionByNameWithSpecialASCIIChar()
		throws Exception {

		testSearchDataDefinition("!@#name", "description", "!@#n");
	}

	@Test
	public void testSearchSiteDataDefinitionByPartialDescription()
		throws Exception {

		testSearchDataDefinition("name", "definition description", "descr");
	}

	@Test
	public void testSearchSiteDataDefinitionByPartialName() throws Exception {
		testSearchDataDefinition("definition name", "description", "nam");
	}

	@Override
	protected void assertValid(DataDefinition dataDefinition) {
		super.assertValid(dataDefinition);

		boolean valid = true;

		if (dataDefinition.getDataDefinitionFields() != null) {
			for (DataDefinitionField dataDefinitionField :
					dataDefinition.getDataDefinitionFields()) {

				if (Validator.isNull(dataDefinitionField.getFieldType()) ||
					Validator.isNull(dataDefinitionField.getLabel()) ||
					Validator.isNull(dataDefinitionField.getName()) ||
					Validator.isNull(dataDefinitionField.getTip())) {

					valid = false;
				}
			}
		}
		else {
			valid = false;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name", "userId"};
	}

	@Override
	protected DataDefinition randomDataDefinition() throws Exception {
		return _createDataDefinition(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	protected void testSearchDataDefinition(
			String dataDefinitionName, String dataDefinitionDescription,
			String keywords)
		throws Exception {

		Long siteId = testGetSiteDataDefinitionsPage_getSiteId();

		DataDefinition dataDefinition1 =
			testGetSiteDataDefinitionsPage_addDataDefinition(
				siteId,
				_createDataDefinition(
					dataDefinitionName, dataDefinitionDescription));

		Page<DataDefinition> page =
			dataDefinitionResource.getSiteDataDefinitionsPage(
				siteId, keywords, Pagination.of(1, 2));

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1),
			(List<DataDefinition>)page.getItems());
		assertValid(page);
	}

	private DataDefinition _createDataDefinition(
			String dataDefinitionName, String dataDefinitionDescription)
		throws Exception {

		return new DataDefinition() {
			{
				dataDefinitionFields = new DataDefinitionField[] {
					new DataDefinitionField() {
						{
							description = new HashMap<String, Object>() {
								{
									put("en_US", RandomTestUtil.randomString());
								}
							};
							fieldType = "fieldType";
							label = new HashMap<String, Object>() {
								{
									put("label", RandomTestUtil.randomString());
								}
							};
							name = RandomTestUtil.randomString();
							tip = new HashMap<String, Object>() {
								{
									put("tip", RandomTestUtil.randomString());
								}
							};
						}
					}
				};
				dataDefinitionKey = RandomTestUtil.randomString();
				description = new HashMap<String, Object>() {
					{
						put("en_US", dataDefinitionDescription);
					}
				};
				name = new HashMap<String, Object>() {
					{
						put("en_US", dataDefinitionName);
					}
				};
				siteId = testGroup.getGroupId();
				userId = TestPropsValues.getUserId();
			}
		};
	}

	private static final String _OPERATION_SAVE_PERMISSION = "save";

}