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

package com.liferay.object.admin.rest.dto.v1_0.util;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Feliphe Marinho
 */
public class ObjectFieldUtil {

	public static JSONObject toJSONObject(
		ListTypeDefinitionService listTypeDefinitionService,
		ObjectField objectField) {

		return JSONUtil.put(
			"businessType", objectField.getBusinessType()
		).put(
			"DBType", objectField.getDBType()
		).put(
			"defaultValue", objectField.getDefaultValue()
		).put(
			"externalReferenceCode", objectField.getExternalReferenceCode()
		).put(
			"id", Long.valueOf(objectField.getObjectFieldId())
		).put(
			"indexed", objectField.isIndexed()
		).put(
			"indexedAsKeyword", objectField.isIndexedAsKeyword()
		).put(
			"indexedLanguageId", objectField.getIndexedLanguageId()
		).put(
			"label", objectField.getLabelMap()
		).put(
			"listTypeDefinitionExternalReferenceCode",
			() -> {
				if (!StringUtil.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

					return null;
				}

				ListTypeDefinition listTypeDefinition =
					listTypeDefinitionService.getListTypeDefinition(
						objectField.getListTypeDefinitionId());

				return listTypeDefinition.getExternalReferenceCode();
			}
		).put(
			"listTypeDefinitionId",
			Long.valueOf(objectField.getListTypeDefinitionId())
		).put(
			"name", objectField.getName()
		).put(
			"objectFieldSettings",
			ObjectFieldSettingUtil.toJSONObject(objectField)
		).put(
			"relationshipType", objectField.getRelationshipType()
		).put(
			"required", objectField.isRequired()
		).put(
			"state", objectField.isState()
		).put(
			"system", objectField.isSystem()
		);
	}

}