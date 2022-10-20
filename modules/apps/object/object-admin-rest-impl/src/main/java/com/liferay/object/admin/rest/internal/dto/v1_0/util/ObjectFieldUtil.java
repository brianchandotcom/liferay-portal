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

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.exception.ObjectFieldListTypeDefinitionIdException;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFilterLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.TransformUtil;

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

	public static long getListTypeDefinitionId(
			long companyId,
			ListTypeDefinitionLocalService listTypeDefinitionLocalService,
			ObjectField objectField)
		throws ObjectFieldListTypeDefinitionIdException {

		if (!StringUtil.equals(
				objectField.getBusinessTypeAsString(),
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			return 0;
		}

		long listTypeDefinitionId = GetterUtil.getLong(
			objectField.getListTypeDefinitionId());

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-164278")) ||
			(listTypeDefinitionId != 0) ||
			Validator.isNull(
				objectField.getListTypeDefinitionExternalReferenceCode())) {

			return listTypeDefinitionId;
		}

		ListTypeDefinition listTypeDefinition =
			listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					companyId,
					objectField.getListTypeDefinitionExternalReferenceCode());

		if (listTypeDefinition == null) {
			return 0;
		}

		return listTypeDefinition.getListTypeDefinitionId();
	}

	public static com.liferay.object.model.ObjectField toObjectField(
			ListTypeDefinitionLocalService listTypeDefinitionLocalService,
			ObjectField objectField,
			ObjectFieldLocalService objectFieldLocalService,
			ObjectFieldSettingLocalService objectFieldSettingLocalService,
			ObjectFilterLocalService objectFilterLocalService)
		throws ObjectFieldListTypeDefinitionIdException {

		com.liferay.object.model.ObjectField serviceBuilderObjectField =
			objectFieldLocalService.createObjectField(0L);

		serviceBuilderObjectField.setExternalReferenceCode(
			objectField.getExternalReferenceCode());
		serviceBuilderObjectField.setListTypeDefinitionId(
			getListTypeDefinitionId(
				serviceBuilderObjectField.getCompanyId(),
				listTypeDefinitionLocalService, objectField));
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
		serviceBuilderObjectField.setObjectFieldSettings(
			TransformUtil.transformToList(
				objectField.getObjectFieldSettings(),
				objectFieldSetting ->
					ObjectFieldSettingUtil.toObjectFieldSetting(
						objectField.getBusinessTypeAsString(),
						objectFieldSetting, objectFieldSettingLocalService,
						objectFilterLocalService)));
		serviceBuilderObjectField.setRequired(
			GetterUtil.getBoolean(objectField.getRequired()));
		serviceBuilderObjectField.setSystem(
			GetterUtil.getBoolean(objectField.getSystem()));

		return serviceBuilderObjectField;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFieldUtil.class);

}