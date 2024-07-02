/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.batch.engine;

import com.liferay.batch.engine.csv.ColumnDescriptor;
import com.liferay.batch.engine.csv.ColumnDescriptorProvider;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.Link;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = ColumnDescriptorProvider.class)
public class ColumnDescriptorProviderImpl implements ColumnDescriptorProvider {

	@Override
	public ColumnDescriptor[] getColumnDescriptors(
			long companyId, String fieldName, int index,
			Map<String, ObjectValuePair<Field, Method>> objectValuePairs,
			String taskItemDelegateName)
		throws PortalException {

		ObjectValuePair<Field, Method> propertiesObjectValuePair =
			objectValuePairs.get("properties");

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, taskItemDelegateName);

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(), fieldName);

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			return _getAttachmentColumnDescriptors(
				fieldName, index, propertiesObjectValuePair);
		}

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

			return _getMultiselectPicklistColumnDescriptors(
				fieldName, index, objectField.getListTypeDefinitionId(),
				propertiesObjectValuePair);
		}

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			return _getPicklistColumnDescriptors(
				fieldName, index, propertiesObjectValuePair);
		}

		return new ColumnDescriptor[] {
			ColumnDescriptor.from(
				fieldName, index,
				object -> {
					Object value = _getValue(
						fieldName, object, propertiesObjectValuePair);

					if (value == null) {
						return StringPool.BLANK;
					}

					return CSVUtil.encode(value);
				})
		};
	}

	private ColumnDescriptor[] _getAttachmentColumnDescriptors(
		String fieldName, int index,
		ObjectValuePair<Field, Method> propertiesObjectValuePair) {

		ColumnDescriptor[] attachmentColumnDescriptors =
			new ColumnDescriptor[4];

		attachmentColumnDescriptors[0] = ColumnDescriptor.from(
			fieldName + ".id", index++,
			object -> {
				Object value = _getValue(
					fieldName, object, propertiesObjectValuePair);

				if (value == null) {
					return StringPool.BLANK;
				}

				FileEntry fileEntry = (FileEntry)value;

				return fileEntry.getId();
			});
		attachmentColumnDescriptors[1] = ColumnDescriptor.from(
			fieldName + ".link.href", index++,
			object -> {
				Object value = _getValue(
					fieldName, object, propertiesObjectValuePair);

				if (value == null) {
					return StringPool.BLANK;
				}

				FileEntry fileEntry = (FileEntry)value;

				Link link = fileEntry.getLink();

				return link.getHref();
			});
		attachmentColumnDescriptors[2] = ColumnDescriptor.from(
			fieldName + ".link.label", index++,
			object -> {
				Object value = _getValue(
					fieldName, object, propertiesObjectValuePair);

				if (value == null) {
					return StringPool.BLANK;
				}

				FileEntry fileEntry = (FileEntry)value;

				Link link = fileEntry.getLink();

				return link.getLabel();
			});
		attachmentColumnDescriptors[3] = ColumnDescriptor.from(
			fieldName + ".name", index,
			object -> {
				Object value = _getValue(
					fieldName, object, propertiesObjectValuePair);

				if (value == null) {
					return StringPool.BLANK;
				}

				FileEntry fileEntry = (FileEntry)value;

				return fileEntry.getName();
			});

		return attachmentColumnDescriptors;
	}

	private String _getMultiselectListEntryValue(
		int columnIndex, String fieldName, List<ListEntry> listEntries) {

		if (listEntries.size() < columnIndex) {
			return StringPool.BLANK;
		}

		ListEntry listEntry = listEntries.get(columnIndex - 1);

		if (Objects.equals(fieldName, "key")) {
			return listEntry.getKey();
		}

		return listEntry.getName();
	}

	private ColumnDescriptor[] _getMultiselectPicklistColumnDescriptors(
		String fieldName, int index, long listTypeDefinitionId,
		ObjectValuePair<Field, Method> propertiesObjectValuePair) {

		int listTypeEntriesCount =
			_listTypeEntryLocalService.getListTypeEntriesCount(
				listTypeDefinitionId);

		ColumnDescriptor[] multiselectPicklistColumnDescriptors =
			new ColumnDescriptor[listTypeEntriesCount * 2];

		int listTypeEntriesIndex = 1;

		for (int i = 0; i < multiselectPicklistColumnDescriptors.length;
			 i = i + 2) {

			int finalListTypeEntriesIndex = listTypeEntriesIndex;

			multiselectPicklistColumnDescriptors[i] = ColumnDescriptor.from(
				StringBundler.concat(fieldName, ".key_", listTypeEntriesIndex),
				index++,
				object -> {
					Object value = _getValue(
						fieldName, object, propertiesObjectValuePair);

					if (value == null) {
						return StringPool.BLANK;
					}

					return _getMultiselectListEntryValue(
						finalListTypeEntriesIndex, "key",
						(List<ListEntry>)value);
				});
			multiselectPicklistColumnDescriptors[i + 1] = ColumnDescriptor.from(
				StringBundler.concat(fieldName, ".name_", listTypeEntriesIndex),
				index++,
				object -> {
					Object value = _getValue(
						fieldName, object, propertiesObjectValuePair);

					if (value == null) {
						return StringPool.BLANK;
					}

					return _getMultiselectListEntryValue(
						finalListTypeEntriesIndex, "name",
						(List<ListEntry>)value);
				});

			listTypeEntriesIndex++;
		}

		return multiselectPicklistColumnDescriptors;
	}

	private ColumnDescriptor[] _getPicklistColumnDescriptors(
		String fieldName, int index,
		ObjectValuePair<Field, Method> propertiesObjectValuePair) {

		ColumnDescriptor[] picklistColumnDescriptors = new ColumnDescriptor[2];

		picklistColumnDescriptors[0] = ColumnDescriptor.from(
			fieldName + ".key", index++,
			object -> {
				Object value = _getValue(
					fieldName, object, propertiesObjectValuePair);

				if (value == null) {
					return StringPool.BLANK;
				}

				ListEntry listEntry = (ListEntry)value;

				return listEntry.getKey();
			});

		picklistColumnDescriptors[1] = ColumnDescriptor.from(
			fieldName + ".name", index,
			object -> {
				Object value = _getValue(
					fieldName, object, propertiesObjectValuePair);

				if (value == null) {
					return StringPool.BLANK;
				}

				ListEntry listEntry = (ListEntry)value;

				return listEntry.getName();
			});

		return picklistColumnDescriptors;
	}

	private Object _getValue(
			String fieldName, Object object,
			ObjectValuePair<Field, Method> objectValuePair)
		throws ReflectiveOperationException {

		Map<?, ?> map = null;

		Method method = objectValuePair.getValue();

		if (method == null) {
			Field field = objectValuePair.getKey();

			map = (Map<?, ?>)field.get(object);
		}
		else {
			map = (Map<?, ?>)method.invoke(object);
		}

		Object value = map.get(fieldName);

		if (!(value instanceof UnsafeSupplier<?, ?>)) {
			return value;
		}

		UnsafeSupplier<?, ?> unsafeSupplier = (UnsafeSupplier<?, ?>)value;

		try {
			value = unsafeSupplier.get();
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}

		return value;
	}

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}