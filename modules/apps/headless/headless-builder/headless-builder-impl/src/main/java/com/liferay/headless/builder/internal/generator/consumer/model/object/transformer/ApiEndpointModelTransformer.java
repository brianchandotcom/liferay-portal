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

import com.liferay.headless.builder.internal.generator.consumer.model.ApiEndpointModel;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;

import java.util.Map;

/**
 * @author Carlos Correa
 */
public class ApiEndpointModelTransformer
	implements ObjectModelTransformer<ApiEndpointModel> {

	@Override
	public ApiEndpointModel toModel(long companyId, ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return new ApiEndpointModel() {

			@Override
			public long getCompanyId() {
				return companyId;
			}

			@Override
			public String getExternalReferenceCode() {
				return objectEntry.getExternalReferenceCode();
			}

			@Override
			public String getMethod() {
				ListEntry listEntry = (ListEntry)properties.get("httpMethod");

				return listEntry.getKey();
			}

			@Override
			public String getPath() {
				return (String)properties.get("path");
			}

			@Override
			public String getRequestSchemaERC() {
				return (String)properties.get(
					"r_requestAPISchemaToAPIEndpoints_c_apiSchemaERC");
			}

			@Override
			public String getResponseSchemaERC() {
				return (String)properties.get(
					"r_responseAPISchemaToAPIEndpoints_c_apiSchemaERC");
			}

			@Override
			public String getScope() {
				ListEntry listEntry = (ListEntry)properties.get("scope");

				return listEntry.getKey();
			}

		};
	}

}