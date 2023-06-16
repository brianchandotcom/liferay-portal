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

import com.liferay.headless.builder.internal.generator.consumer.model.ApiApplicationModel;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;

import java.util.Map;

/**
 * @author Carlos Correa
 */
public class ApiApplicationModelTransformer
	implements ObjectModelTransformer<ApiApplicationModel> {

	@Override
	public ApiApplicationModel toModel(
		long companyId, ObjectEntry objectEntry) {

		if (objectEntry == null) {
			return null;
		}

		Map<String, Object> properties = objectEntry.getProperties();

		return new ApiApplicationModel() {

			@Override
			public String getBaseURL() {
				return (String)properties.get("baseURL");
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
			public String getTitle() {
				return (String)properties.get("title");
			}

			@Override
			public String getVersion() {
				return (String)properties.get("version");
			}

		};
	}

}