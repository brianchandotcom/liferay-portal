/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.DateTimeInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.text.Format;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

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

	public static Object getValue(
			Locale locale, ObjectField objectField, TimeZone timeZone,
			Map<String, Object> values)
		throws Exception {

		Object value = values.get(objectField.getName());

		if (value == null) {
			return null;
		}

		if (objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			FileEntry fileEntry = (FileEntry)value;

			DLFileEntry dlFileEntry =
				DLFileEntryLocalServiceUtil.fetchDLFileEntry(
					GetterUtil.getLong(fileEntry.getId()));

			if (dlFileEntry == null) {
				return null;
			}

			return fileEntry;
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_DATE)) {

			return DateUtil.parseDate("yyyy-MM-dd", value.toString(), locale);
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME)) {

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
				ObjectFieldUtil.getDateTimePattern(value.toString()));

			return LocalDateTime.parse(value.toString(), dateTimeFormatter);
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

			List<ListTypeEntry> listTypeEntries = new ArrayList<>();

			for (ListEntry listEntry : (List<ListEntry>)value) {
				ListTypeEntry listTypeEntry =
					ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
						objectField.getListTypeDefinitionId(),
						listEntry.getKey());

				if (listTypeEntry == null) {
					continue;
				}

				listTypeEntries.add(listTypeEntry);
			}

			return listTypeEntries;
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			return ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
				objectField.getListTypeDefinitionId(),
				((ListEntry)value).getKey());
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

			long primaryKey = GetterUtil.getLong(value);

			if (primaryKey == 0) {
				return null;
			}

			ObjectRelationship objectRelationship =
				ObjectRelationshipLocalServiceUtil.
					fetchObjectRelationshipByObjectFieldId2(
						objectField.getObjectFieldId());

			return ObjectEntryLocalServiceUtil.getTitleValue(
				objectRelationship.getObjectDefinitionId1(), primaryKey);
		}

		return value;
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
			else if (Objects.equals(
						DateTimeInfoFieldType.INSTANCE,
						infoField.getInfoFieldType()) &&
					 (value instanceof LocalDateTime) &&
					 FeatureFlagManagerUtil.isEnabled("LPS-183727")) {

				DateTimeFormatter dateTimeFormatter =
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

				properties.put(
					infoField.getName(),
					dateTimeFormatter.format((LocalDateTime)value));
			}
			else {
				properties.put(infoField.getName(), value);
			}
		}

		return properties;
	}

}