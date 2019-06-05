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
import com.liferay.data.engine.rest.client.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataDefinitionResourceTest
	extends BaseDataDefinitionResourceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dataDefinitionResource = new DataDefinitionResource();
	}

	@Override
	public void testPostDataDefinitionDataDefinitionPermission()
		throws Exception {

		super.testPostDataDefinitionDataDefinitionPermission();

		DDMStructure ddmStructure = DataDefinitionTestUtil.addDDMStructure(
			testGroup);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_dataDefinitionResource.postDataDefinitionDataDefinitionPermission(
			ddmStructure.getStructureId(), _OPERATION_SAVE_PERMISSION,
			new DataDefinitionPermission() {
				{
					view = true;
					roleNames = new String[] {role.getName()};
				}
			});
	}

	@Override
	public void testPostSiteDataDefinitionPermission() throws Exception {
		super.testPostSiteDataDefinitionPermission();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_dataDefinitionResource.postSiteDataDefinitionPermission(
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
		return new String[] {"name", "userId"};
	}

	@Override
	protected DataDefinition randomDataDefinition() throws Exception {
		return new DataDefinition() {
			{
				dataDefinitionFields = new DataDefinitionField[] {
					new DataDefinitionField() {
						{
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
				name = new HashMap<String, Object>() {
					{
						put("en_US", RandomTestUtil.randomString());
					}
				};
				siteId = testGroup.getGroupId();
				userId = TestPropsValues.getUserId();
			}
		};
	}

	private static final String _OPERATION_SAVE_PERMISSION = "save";

	private DataDefinitionResource _dataDefinitionResource =
		new DataDefinitionResource();

}