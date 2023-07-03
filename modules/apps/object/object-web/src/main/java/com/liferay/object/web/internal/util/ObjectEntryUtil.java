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

package com.liferay.object.web.internal.util;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.text.Format;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class ObjectEntryUtil {

	public static String getScopeKey(
		long groupId, ObjectDefinition objectDefinition,
		ObjectScopeProviderRegistry objectScopeProviderRegistry) {

		ObjectScopeProvider objectScopeProvider =
			objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (!objectScopeProvider.isGroupAware()) {
			return null;
		}

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		return group.getGroupKey();
	}

	public static ObjectEntry toObjectEntry(
		long objectDefinitionId,
		com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry) {

		ObjectEntry serviceBuilderObjectEntry =
			ObjectEntryLocalServiceUtil.createObjectEntry(0L);

		serviceBuilderObjectEntry.setExternalReferenceCode(
			objectEntry.getExternalReferenceCode());
		serviceBuilderObjectEntry.setObjectEntryId(
			GetterUtil.getLong(objectEntry.getId()));
		serviceBuilderObjectEntry.setObjectDefinitionId(objectDefinitionId);

		return serviceBuilderObjectEntry;
	}

	public static Map<String, Object> toProperties(
		InfoItemFieldValues infoItemFieldValues) {

		Map<String, Object> properties = new HashMap<>();

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			InfoField<?> infoField = infoFieldValue.getInfoField();

			Object value = infoFieldValue.getValue();

			if (Objects.equals(
					DateInfoFieldType.INSTANCE, infoField.getInfoFieldType()) &&
				(value instanceof Date)) {

				Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
					"yyyy-MM-dd");

				properties.put(infoField.getName(), format.format(value));
			}
			else {
				properties.put(infoField.getName(), value);
			}
		}

		return properties;
	}

}