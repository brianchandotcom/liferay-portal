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
import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionPermission;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.Objects;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataDefinitionResourceTest
	extends BaseDataDefinitionResourceTestCase {

	public static final String OPERATION_DELETE_PERMISSION = "delete";

	public static final String OPERATION_SAVE_PERMISSION = "save";

	public static final String RESOURCE_NAME =
		"com.liferay.data.engine.rest.internal.model.InternalDataDefinition";

	@Override
	public void testPostContentSpaceDataDefinitionPermission()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		DataDefinitionPermission dataDefinitionPermission =
			new DataDefinitionPermission() {
				{
					addDataDefinition = true;
					roleNames = new String[] {role.getName()};
				}
			};

		invokePostContentSpaceDataDefinitionPermission(
			testGroup.getGroupId(), OPERATION_SAVE_PERMISSION,
			dataDefinitionPermission);
	}

	@Override
	public void testPostDataDefinitionDataDefinitionPermission()
		throws Exception {

		_ddmStructure = _addDDMStructure(testGroup);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		DataDefinitionPermission dataDefinitionPermission =
			new DataDefinitionPermission() {
				{
					view = true;
					roleNames = new String[] {role.getName()};
				}
			};

		invokePostDataDefinitionDataDefinitionPermission(
			_ddmStructure.getStructureId(), OPERATION_SAVE_PERMISSION,
			dataDefinitionPermission);
	}

	protected void assertValid(DataDefinition dataDefinition) {
		boolean valid = false;

		if (Objects.equals(
				dataDefinition.getContentSpaceId(), testGroup.getGroupId()) &&
			(dataDefinition.getDateCreated() != null) &&
			(dataDefinition.getDateModified() != null) &&
			(dataDefinition.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		DataDefinition dataDefinition1, DataDefinition dataDefinition2) {

		LocalizedValue[] localizedValues1 = dataDefinition1.getName();
		LocalizedValue[] localizedValues2 = dataDefinition2.getName();

		if (Objects.equals(
				localizedValues1[0].getKey(), localizedValues2[0].getKey())) {

			return true;
		}

		return false;
	}

	@Override
	protected DataDefinition randomDataDefinition() {
		return new DataDefinition() {
			{
				name = new LocalizedValue[] {
					new LocalizedValue() {
						{
							key = "en_US";
							value = RandomTestUtil.randomString();
						}
					}
				};
			}
		};
	}

	@Override
	protected DataDefinition testDeleteDataDefinition_addDataDefinition()
		throws Exception {

		return invokePostContentSpaceDataDefinition(
			testGroup.getGroupId(), randomDataDefinition());
	}

	@Override
	protected DataDefinition
			testGetContentSpaceDataDefinitionsPage_addDataDefinition(
				Long contentSpaceId, DataDefinition dataDefinition)
		throws Exception {

		return invokePostContentSpaceDataDefinition(
			contentSpaceId, dataDefinition);
	}

	@Override
	protected Long testGetContentSpaceDataDefinitionsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	@Override
	protected DataDefinition testGetDataDefinition_addDataDefinition()
		throws Exception {

		return invokePostContentSpaceDataDefinition(
			testGroup.getGroupId(), randomDataDefinition());
	}

	@Override
	protected DataDefinition
			testPostContentSpaceDataDefinition_addDataDefinition(
				DataDefinition dataDefinition)
		throws Exception {

		return invokePostContentSpaceDataDefinition(
			testGroup.getGroupId(), dataDefinition);
	}

	@Override
	protected DataDefinition testPutDataDefinition_addDataDefinition()
		throws Exception {

		return invokePostContentSpaceDataDefinition(
			testGroup.getGroupId(), randomDataDefinition());
	}

	private DDMStructure _addDDMStructure(Group group) throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(RESOURCE_NAME), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(RESOURCE_NAME),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_read("test-structured-content-structure.json"),
			StorageType.JSON.getValue());
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(fileName);

		return StringUtil.read(inputStream);
	}

	private DDMStructure _ddmStructure;

}