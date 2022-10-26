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

package com.liferay.object.rest.internal.util;

import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.object.util.ObjectFieldSettingValueUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryValuesUtil {

	public static Object getObjectFieldValue(
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectEntryLocalService objectEntryLocalService,
			ObjectField objectField,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			SystemObjectDefinitionMetadataTracker
				systemObjectDefinitionMetadataTracker,
			long userId, Map<String, ?> values)
		throws PortalException {

		Object value = values.get(objectField.getName());

		if (!Objects.equals(
				objectField.getRelationshipType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY) ||
			(GetterUtil.getLong(value) > 0)) {

			return value;
		}

		String externalReferenceCode = GetterUtil.getString(
			values.get(
				ObjectFieldSettingValueUtil.getObjectFieldSettingValue(
					objectField,
					ObjectFieldSettingConstants.
						OBJECT_RELATIONSHIP_ERC_FIELD_NAME)));

		if (Validator.isNull(externalReferenceCode)) {
			return value;
		}

		ObjectRelationship objectRelationship =
			objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.fetchObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		if (objectDefinition.isSystem()) {
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
				systemObjectDefinitionMetadataTracker.
					getSystemObjectDefinitionMetadata(
						objectDefinition.getName());

			BaseModel<?> baseModel =
				systemObjectDefinitionMetadata.
					getBaseModelByExternalReferenceCode(
						externalReferenceCode, objectDefinition.getCompanyId());

			return baseModel.getPrimaryKeyObj();
		}

		ObjectEntry objectEntry = objectEntryLocalService.fetchObjectEntry(
			externalReferenceCode, objectDefinition.getObjectDefinitionId());

		if (objectEntry == null) {
			objectEntry = objectEntryLocalService.addObjectEntry(
				externalReferenceCode, userId, objectDefinition);
		}

		return objectEntry.getObjectEntryId();
	}

}