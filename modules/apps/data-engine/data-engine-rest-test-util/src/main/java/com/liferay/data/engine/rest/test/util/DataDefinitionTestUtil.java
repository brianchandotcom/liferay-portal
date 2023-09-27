/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			String contentType,
			DataDefinitionResource.Factory dataDefinitionResourceFactory,
			long groupId, String json, User user)
		throws Exception {

		DataDefinition dataDefinition = DataDefinition.toDTO(json);

		DataDefinitionResource.Builder dataDefinitionResourcedBuilder =
			dataDefinitionResourceFactory.create();

		DataDefinitionResource dataDefinitionResource =
			dataDefinitionResourcedBuilder.user(
				user
			).build();

		return dataDefinitionResource.postSiteDataDefinitionByContentType(
			groupId, contentType, dataDefinition);
	}

}