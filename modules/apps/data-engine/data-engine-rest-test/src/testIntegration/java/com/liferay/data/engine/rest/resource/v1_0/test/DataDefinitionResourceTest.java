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
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
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
	public void testGetDataDefinitionDataDefinitionFieldFieldTypes()
		throws Exception {

		String fieldTypes =
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldTypes();

		Assert.assertTrue(Validator.isNotNull(fieldTypes));
	}

	@Override
	public void testGetDataDefinitionDataDefinitionFieldLinks()
		throws Exception {

		DataDefinition postDataDefinition =
			testGetDataDefinition_addDataDefinition();

		String fieldLinks =
			dataDefinitionResource.getDataDefinitionDataDefinitionFieldLinks(
				postDataDefinition.getId(), "");

		Assert.assertTrue(Validator.isNotNull(fieldLinks));
	}

	@Override
	@Test
	public void testGetSiteDataDefinitionsPage() throws Exception {
		super.testGetSiteDataDefinitionsPage();

		Page<DataDefinition> page =
			dataDefinitionResource.getSiteDataDefinitionsPage(
				testGetSiteDataDefinitionsPage_getSiteId(), null, "definition",
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

		dataDefinitionResource.postSiteDataDefinition(
			testGroup.getGroupId(), randomDataDefinition());

		DataDefinition expectedDataDefinition = randomDataDefinition();

		long classNameId = _portal.getClassNameId(
			DDMFormInstance.class.getName());

		expectedDataDefinition.setClassNameId(classNameId);

		dataDefinitionResource.postSiteDataDefinition(
			testGroup.getGroupId(), expectedDataDefinition);

		page = dataDefinitionResource.getSiteDataDefinitionsPage(
			testGetSiteDataDefinitionsPage_getSiteId(), classNameId, null,
			Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());
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
			testGetSiteDataDefinition_addDataDefinition();

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
							description = HashMapBuilder.<String, Object>put(
								"en_US", RandomTestUtil.randomString()
							).build();
							fieldType = "text";
							label = HashMapBuilder.<String, Object>put(
								"label", RandomTestUtil.randomString()
							).build();
							name = RandomTestUtil.randomString();
							tip = HashMapBuilder.<String, Object>put(
								"tip", RandomTestUtil.randomString()
							).build();
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
			HashMapBuilder.<String, Object>put(
				"en_US", description
			).build());
		dataDefinition.setName(
			HashMapBuilder.<String, Object>put(
				"en_US", name
			).build());

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
				siteId, null, keywords, Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition),
			(List<DataDefinition>)page.getItems());
		assertValid(page);

		dataDefinitionResource.deleteDataDefinition(dataDefinition.getId());
	}

	private static final String _OPERATION_SAVE_PERMISSION = "save";

	@Inject
	private Portal _portal;

}