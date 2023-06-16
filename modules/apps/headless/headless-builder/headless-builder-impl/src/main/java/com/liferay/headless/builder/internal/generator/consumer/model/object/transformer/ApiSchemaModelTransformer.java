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

package com.liferay.headless.builder.internal.generator.consumer.model.object.transformer;

import com.liferay.headless.builder.internal.generator.consumer.model.ApiPropertyModel;
import com.liferay.headless.builder.internal.generator.consumer.model.ApiSchemaModel;
import com.liferay.headless.builder.internal.generator.consumer.model.object.constants.ObjectModelConstants;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Carlos Correa
 */
public class ApiSchemaModelTransformer
	implements ObjectModelTransformer<ApiSchemaModel> {

	public static final String MAIN_OBJECT_DEFINITION_ERC_FIELD_NAME =
		"mainObjectDefinitionERC";

	@Override
	public ApiSchemaModel toModel(long companyId, ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		String mainObjectDefinitionERC = MapUtil.getString(
			properties, MAIN_OBJECT_DEFINITION_ERC_FIELD_NAME);

		return new ApiSchemaModel() {

			@Override
			public List<ApiPropertyModel> getApiPropertyModels() {
				return TransformUtil.transformToList(
					(ObjectEntry[])properties.get(
						ObjectModelConstants.
							SCHEMA_TO_PROPERTIES_RELATIONSHIP_NAME),
					objectEntry1 -> {
						Map<String, Object> propertyProperties =
							objectEntry1.getProperties();

						propertyProperties.put(
							MAIN_OBJECT_DEFINITION_ERC_FIELD_NAME,
							mainObjectDefinitionERC);

						return (ApiPropertyModel)
							_apiSchemaPropertyModelTransformer.toModel(
								companyId, objectEntry1);
					});
			}

			@Override
			public long getCompanyId() {
				return companyId;
			}

			@Override
			public String getDescription() {
				return (String)properties.get("description");
			}

			@Override
			public String getExternalReferenceCode() {
				return objectEntry.getExternalReferenceCode();
			}

			@Override
			public String getName() {
				return (String)properties.get("name");
			}

		};
	}

	private final ObjectModelTransformer _apiSchemaPropertyModelTransformer =
		new ApiPropertyModelTransformer();

}