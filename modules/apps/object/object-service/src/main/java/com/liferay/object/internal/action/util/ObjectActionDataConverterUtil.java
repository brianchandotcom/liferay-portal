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
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(immediate = true, service = {})
public class ObjectActionDataConverterUtil {

	public static Map<String, Object> convertPayloadJSONObject(
		JSONObject payloadJSONObject) {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				payloadJSONObject.getLong("objectDefinitionId"));

		if (objectDefinition.isSystem()) {
			return _convertSystemObjectPayloadJSONObject(
				objectDefinition, payloadJSONObject);
		}

		return _convertObjectEntryPayloadJSONObject(payloadJSONObject);
	}

	private static Map<String, Object> _convertObjectEntryPayloadJSONObject(
		JSONObject payloadJSONObject) {

		JSONObject objectEntryJSONObject = payloadJSONObject.getJSONObject(
			"objectEntry");

		Map<String, Object> objectEntryMap = objectEntryJSONObject.toMap();

		Object values = objectEntryMap.get("values");

		if (values != null) {
			objectEntryMap.putAll((Map<String, Object>)values);

			objectEntryMap.remove("values");
		}

		objectEntryMap.put("currentUserId", payloadJSONObject.get("userId"));

		return objectEntryMap;
	}

	private static Map<String, Object> _convertSystemObjectPayloadJSONObject(
		ObjectDefinition objectDefinition, JSONObject payloadJSONObject) {

		DTOConverter<?, ?> dtoConverter = _dtoConverterRegistry.getDTOConverter(
			objectDefinition.getClassName());

		JSONObject modelDTOJSONObject = payloadJSONObject.getJSONObject(
			"modelDTO" + dtoConverter.getContentType());

		modelDTOJSONObject.put(
			"companyId", payloadJSONObject.get("companyId")
		).put(
			"currentUserId", payloadJSONObject.get("userId")
		).put(
			"objectDefinitionId", payloadJSONObject.get("objectDefinitionId")
		);

		return modelDTOJSONObject.toMap();
	}

	@Reference(unbind = "-")
	private void _setDTOConverterRegistry(
		DTOConverterRegistry dtoConverterRegistry) {

		_dtoConverterRegistry = dtoConverterRegistry;
	}

	@Reference(unbind = "-")
	private void _setObjectDefinitionLocalService(
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	private static DTOConverterRegistry _dtoConverterRegistry;
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

}