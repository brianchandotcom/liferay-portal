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
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataDefinitionResourceTest
	extends BaseDataDefinitionResourceTestCase {

	@Override
	public void testGetDataDefinitionDataDefinitionFieldFieldType()
		throws Exception {

		String fieldTypes =
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldType();

		Assert.assertTrue(Validator.isNotNull(fieldTypes));
	}

	@Override
	@Test
	public void testGetSiteDataDefinitionsPage() throws Exception {
		super.testGetSiteDataDefinitionsPage();

		Page<DataDefinition> page =
			dataDefinitionResource.getSiteDataDefinitionsPage(
				testGetSiteDataDefinitionsPage_getSiteId(), "definition",
				Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		_testGetSiteDataDefinitionsPage(
			"DeFiNiTiON dEsCrIpTiOn", "DEFINITION", "name");
		_testGetSiteDataDefinitionsPage(
			"abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789", "definition");
		_testGetSiteDataDefinitionsPage(
			"description name", "description name", "definition");
		_testGetSiteDataDefinitionsPage(
			"description", "DEFINITION", "DeFiNiTiON NaMe");
		_testGetSiteDataDefinitionsPage(
			"description", "definition name", "definition name");
		_testGetSiteDataDefinitionsPage(
			"description", "nam", "definition name");
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteDataDefinition() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDataDefinition() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteDataDefinition() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteDataDefinitionsPage() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLPostSiteDataDefinition() {
	}

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
		return new String[] {
			"availableLanguageIds", "defaultLanguageId", "name", "userId"
		};
	}

	@Override
	protected DataDefinition randomDataDefinition() throws Exception {
		return _createDataDefinition(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	private DataDefinition _createDataDefinition(
			String description, String name)
		throws Exception {

		DataDefinition dataDefinition = new DataDefinition() {
			{
				availableLanguageIds = new String[] {"en_US", "pt_BR"};
				dataDefinitionFields = new DataDefinitionField[] {
					new DataDefinitionField() {
						{
							description = new HashMap<String, Object>() {
								{
									put("en_US", RandomTestUtil.randomString());
								}
							};
							fieldType = "text";
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
				defaultLanguageId = "en_US";
				siteId = testGroup.getGroupId();
				userId = TestPropsValues.getUserId();
			}
		};

		dataDefinition.setDescription(
			new HashMap<String, Object>() {
				{
					put("en_US", description);
				}
			});
		dataDefinition.setName(
			new HashMap<String, Object>() {
				{
					put("en_US", name);
				}
			});

		return dataDefinition;
	}

	private void _testGetSiteDataDefinitionsPage(
			String description, String keywords, String name)
		throws Exception {

		Long siteId = testGetSiteDataDefinitionsPage_getSiteId();

		DataDefinition dataDefinition =
			testGetSiteDataDefinitionsPage_addDataDefinition(
				siteId, _createDataDefinition(description, name));

		Page<DataDefinition> page =
			dataDefinitionResource.getSiteDataDefinitionsPage(
				siteId, keywords, Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition),
			(List<DataDefinition>)page.getItems());
		assertValid(page);

		dataDefinitionResource.deleteDataDefinition(dataDefinition.getId());
	}

	private static final String _OPERATION_SAVE_PERMISSION = "save";

}