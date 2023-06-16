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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;

import java.util.Map;

/**
 * @author Carlos Correa
 */
public class ApiPropertyModelTransformer
	implements ObjectModelTransformer<ApiPropertyModel> {

	@Override
	public ApiPropertyModel toModel(long companyId, ObjectEntry objectEntry)
		throws Exception {

		Map<String, Object> properties = objectEntry.getProperties();

		return new ApiPropertyModel() {

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

			@Override
			public String getType() {
				try {
					ObjectDefinition objectDefinition =
						ObjectDefinitionLocalServiceUtil.
							getObjectDefinitionByExternalReferenceCode(
								(String)properties.get(
									ApiSchemaModelTransformer.
										MAIN_OBJECT_DEFINITION_ERC_FIELD_NAME),
								companyId);

					ObjectField objectField =
						ObjectFieldLocalServiceUtil.getObjectField(
							(String)properties.get("objectFieldERC"),
							objectDefinition.getObjectDefinitionId());

					return objectField.getBusinessType();
				}
				catch (Throwable throwable) {
					throw new RuntimeException(throwable);
				}
			}

		};
	}

}