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

package com.liferay.data.engine.rest.resource.v1_0.test.util;

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.HashMap;

/**
 * @author Gabriel Albuquerque
 */
public class DataDefinitionTestUtil {

	public static DDMStructure addDDMStructure(Group group) throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(_RESOURCE_NAME), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(_RESOURCE_NAME),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_read("test-structured-content-structure.json"),
			StorageType.JSON.getValue());
	}

	public static DataDefinition createDataDefinition(
			String description, String name, Group group)
		throws Exception {

		DataDefinition dataDefinition = new DataDefinition() {
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
				siteId = group.getGroupId();
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

	public static DataDefinition randomDataDefinition(Group group)
		throws Exception {

		return createDataDefinition(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			group);
	}

	private static String _read(String fileName) throws Exception {
		Class<?> clazz = DataDefinitionTestUtil.class;

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private static final String _RESOURCE_NAME =
		"com.liferay.data.engine.rest.internal.model.InternalDataDefinition";

}