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

package com.liferay.object.internal.action.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class ObjectActionVariablesUtil {

	public static Map<String, Object> toVariables(
		DTOConverterRegistry dtoConverterRegistry,
		ObjectDefinition objectDefinition, JSONObject payloadJSONObject) {

		if (objectDefinition.isSystem()) {
			DTOConverter<?, ?> dtoConverter =
				dtoConverterRegistry.getDTOConverter(
					objectDefinition.getClassName());

			JSONObject modelDTOJSONObject = payloadJSONObject.getJSONObject(
				"modelDTO" + dtoConverter.getContentType());

			if (modelDTOJSONObject == null) {
				return payloadJSONObject.toMap();
			}

			modelDTOJSONObject.put(
				"companyId", payloadJSONObject.get("companyId")
			).put(
				"currentUserId", payloadJSONObject.get("userId")
			).put(
				"objectDefinitionId",
				payloadJSONObject.get("objectDefinitionId")
			);

			return modelDTOJSONObject.toMap();
		}

		Map<String, Object> variables = new HashMap<>(
			(Map)payloadJSONObject.get("objectEntry"));

		Object values = variables.get("values");

		if (values != null) {
			variables.putAll((Map<String, Object>)values);

			variables.remove("values");
		}

		variables.put("currentUserId", payloadJSONObject.getLong("userId"));

		return variables;
	}

}