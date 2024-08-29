/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.manager.v1_0.util;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;

import java.util.Map;

/**
 * @author Paulo Albuquerque
 */
public class ObjectEntryManagerUtil {

	public static ObjectEntry partialUpdateObjectEntry(
		ObjectEntry existingObjectEntry, long objectDefinitionId,
		ObjectEntry objectEntry) {

		if (objectEntry.getDateCreated() != null) {
			existingObjectEntry.setDateCreated(objectEntry::getDateCreated);
		}

		if (objectEntry.getDateModified() != null) {
			existingObjectEntry.setDateModified(objectEntry::getDateModified);
		}

		if (objectEntry.getExternalReferenceCode() != null) {
			existingObjectEntry.setExternalReferenceCode(
				objectEntry::getExternalReferenceCode);
		}

		if (objectEntry.getKeywords() != null) {
			existingObjectEntry.setKeywords(objectEntry::getKeywords);
		}

		existingObjectEntry.setPermissions(objectEntry::getPermissions);

		if (objectEntry.getProperties() != null) {
			Map<String, Object> existingProperties =
				existingObjectEntry.getProperties();

			Map<String, Object> properties = objectEntry.getProperties();

			for (ObjectField objectField :
					ObjectFieldLocalServiceUtil.getObjectFieldsByBusinessType(
						objectDefinitionId,
						ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

				String objectRelationshipERCObjectFieldName =
					ObjectFieldSettingUtil.getValue(
						ObjectFieldSettingConstants.
							NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
						objectField);

				if (properties.containsKey(objectField.getName()) &&
					!properties.containsKey(
						objectRelationshipERCObjectFieldName)) {

					existingProperties.remove(
						objectRelationshipERCObjectFieldName);
				}
			}

			existingProperties.putAll(properties);

			existingObjectEntry.setProperties(() -> existingProperties);
		}

		if (objectEntry.getStatus() != null) {
			existingObjectEntry.setStatus(objectEntry::getStatus);
		}

		if (objectEntry.getTaxonomyCategoryIds() != null) {
			existingObjectEntry.setTaxonomyCategoryIds(
				objectEntry::getTaxonomyCategoryIds);
		}

		return existingObjectEntry;
	}

}