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
import com.liferay.data.engine.rest.client.resource.v1_0.DataDefinitionResource;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;

/**
 * @author Gabriel Albuquerque
 */
public class DataDefinitionTestUtil {

	public static DataDefinition createDataDefinition(
			String dataDefinitionFieldName, String description, String name,
			long siteId)
		throws Exception {

		DataDefinition dataDefinition = new DataDefinition() {
			{
				availableLanguageIds = new String[] {"en_US"};
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
									put("en_US", RandomTestUtil.randomString());
								}
							};
							name = dataDefinitionFieldName;
							tip = new HashMap<String, Object>() {
								{
									put("en_US", RandomTestUtil.randomString());
								}
							};
						}
					}
				};
				dataDefinitionKey = RandomTestUtil.randomString();
				defaultLanguageId = "en_US";
				storageType = "json";
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
		dataDefinition.setSiteId(siteId);

		return dataDefinition;
	}

	public static DataDefinition postSiteDataDefinition(
			Long siteId, DataDefinition dataDefinition)
		throws Exception {

		DataDefinitionResource.Builder builder =
			DataDefinitionResource.builder();

		DataDefinitionResource dataDefinitionResource = builder.locale(
			LocaleUtil.getDefault()
		).build();

		return dataDefinitionResource.postSiteDataDefinition(
			siteId, dataDefinition);
	}

}