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

package com.liferay.data.engine.rest.test.util;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.portal.kernel.model.User;

/**
 * @author Rodrigo Paulino
 */
public class DataDefinitionTestUtil {

	public static DataDefinition addDataDefinition(
			String contentType, long groupId, String json, User user)
		throws Exception {

		DataDefinition dataDefinition = DataDefinition.toDTO(json);

		DataDefinitionResource.Builder dataDefinitionResourcedBuilder =
			DataDefinitionResource.builder();

		DataDefinitionResource dataDefinitionResource =
			dataDefinitionResourcedBuilder.user(
				user
			).build();

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			groupId, contentType, dataDefinition);
	}

}