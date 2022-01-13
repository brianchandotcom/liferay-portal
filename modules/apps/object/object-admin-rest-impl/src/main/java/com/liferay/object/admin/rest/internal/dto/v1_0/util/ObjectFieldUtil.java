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

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectFieldUtil {

	public static String getDBType(String dbType, String type) {
		if (Validator.isNull(dbType) && Validator.isNotNull(type)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The type property is deprecated. Use the DBType " +
						"property instead.");
			}

			return type;
		}

		return dbType;
	}

	public static ObjectField toObjectField(
		Map<String, Map<String, String>> actions,
		com.liferay.object.model.ObjectField serviceBuilderObjectField) {

		ObjectField objectField = new ObjectField() {
			{
				businessType = ObjectField.BusinessType.create(
					serviceBuilderObjectField.getBusinessType());
				DBType = ObjectField.DBType.create(
					serviceBuilderObjectField.getDBType());
				id = serviceBuilderObjectField.getObjectFieldId();
				indexed = serviceBuilderObjectField.getIndexed();
				indexedAsKeyword =
					serviceBuilderObjectField.getIndexedAsKeyword();
				indexedLanguageId =
					serviceBuilderObjectField.getIndexedLanguageId();
				label = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectField.getLabelMap());
				listTypeDefinitionId =
					serviceBuilderObjectField.getListTypeDefinitionId();
				name = serviceBuilderObjectField.getName();
				relationshipType = ObjectField.RelationshipType.create(
					serviceBuilderObjectField.getRelationshipType());
				required = serviceBuilderObjectField.isRequired();
				type = ObjectField.Type.create(
					serviceBuilderObjectField.getDBType());
			}
		};

		objectField.setActions(actions);

		return objectField;
	}

	public static com.liferay.object.model.ObjectField toObjectField(
		ObjectField objectField,
		ObjectFieldLocalService objectFieldLocalService) {

		com.liferay.object.model.ObjectField serviceBuilderObjectField =
			objectFieldLocalService.createObjectField(0L);

		serviceBuilderObjectField.setListTypeDefinitionId(
			GetterUtil.getLong(objectField.getListTypeDefinitionId()));
		serviceBuilderObjectField.setBusinessType(
			objectField.getBusinessTypeAsString());
		serviceBuilderObjectField.setDBType(
			getDBType(
				objectField.getDBTypeAsString(),
				objectField.getTypeAsString()));
		serviceBuilderObjectField.setIndexed(
			GetterUtil.getBoolean(objectField.getIndexed()));
		serviceBuilderObjectField.setIndexedAsKeyword(
			GetterUtil.getBoolean(objectField.getIndexedAsKeyword()));
		serviceBuilderObjectField.setIndexedLanguageId(
			objectField.getIndexedLanguageId());
		serviceBuilderObjectField.setLabelMap(
			LocalizedMapUtil.getLocalizedMap(objectField.getLabel()));
		serviceBuilderObjectField.setName(objectField.getName());
		serviceBuilderObjectField.setRequired(
			GetterUtil.getBoolean(objectField.getRequired()));

		return serviceBuilderObjectField;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFieldUtil.class);

}