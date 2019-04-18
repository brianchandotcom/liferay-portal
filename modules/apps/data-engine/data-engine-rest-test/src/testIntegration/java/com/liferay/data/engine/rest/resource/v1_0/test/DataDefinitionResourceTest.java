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
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionPermission;
import com.liferay.data.engine.rest.client.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;

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

		super.testPostDataDefinitionDataDefinitionPermission();

		DDMStructure ddmStructure = DataDefinitionTestUtil.addDDMStructure(
			testGroup);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		invokePostDataDefinitionDataDefinitionPermission(
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

		invokePostSiteDataDefinitionPermission(
			testGroup.getGroupId(), _OPERATION_SAVE_PERMISSION,
			new DataDefinitionPermission() {
				{
					addDataDefinition = true;
					roleNames = new String[] {role.getName()};
				}
			});
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
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
				siteId = testGroup.getGroupId();
			}
		};
	}

	private static final String _OPERATION_SAVE_PERMISSION = "save";

}