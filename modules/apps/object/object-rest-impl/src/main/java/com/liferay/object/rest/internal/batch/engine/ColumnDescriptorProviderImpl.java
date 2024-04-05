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
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
			ObjectValuePair<Field, Method> propertiesObjectValuePair,
			String taskItemDelegateName)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, taskItemDelegateName);

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(), fieldName);

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

			return _getMultiselectPickListColumnDescriptors(
				fieldName, index, objectField.getListTypeDefinitionId(),
				objectField.getBusinessType(), propertiesObjectValuePair);
		}

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			return _getPickListColumnDescriptors(
				fieldName, index, objectField.getBusinessType(),
				propertiesObjectValuePair);
		}

		return new ColumnDescriptor[] {
			ColumnDescriptor.from(
				fieldName, index,
				_getObjectEntryCustomFieldUnsafeFunction(
					objectField.getBusinessType(), propertiesObjectValuePair,
					fieldName))
		};
	}

	private String _getListEntryValue(String fieldName, Object object) {
		ListEntry listEntry = (ListEntry)object;

		if (Objects.equals(fieldName, "key")) {
			return listEntry.getKey();
		}

		return listEntry.getName();
	}

	private String _getMultiselectListEntryValue(
		String fieldName, List<ListEntry> listEntries) {

		String[] parts = fieldName.split(StringPool.UNDERLINE);

		int columnIndex = GetterUtil.getInteger(parts[1]);

		if (listEntries.size() < columnIndex) {
			return StringPool.BLANK;
		}

		ListEntry listEntry = listEntries.get(columnIndex - 1);

		if (Objects.equals(parts[0], "key")) {
			return listEntry.getKey();
		}

		return listEntry.getName();
	}

	private ColumnDescriptor[] _getMultiselectPickListColumnDescriptors(
		String fieldName, int index, long listTypeDefinitionId,
		String objectFieldBusinessType,
		ObjectValuePair<Field, Method> propertiesObjectValuePair) {

		int listTypeEntriesCount =
			_listTypeEntryLocalService.getListTypeEntriesCount(
				listTypeDefinitionId);

		ColumnDescriptor[] multiselectPickListColumnDescriptors =
			new ColumnDescriptor[listTypeEntriesCount * 2];

		int listTypeEntriesHeaderIndex = 1;

		for (int i = 0; i < multiselectPickListColumnDescriptors.length;
			 i = i + 2) {

			multiselectPickListColumnDescriptors[i] = ColumnDescriptor.from(
				StringBundler.concat(
					fieldName, ".key_", listTypeEntriesHeaderIndex),
				index++,
				_getObjectEntryCustomFieldUnsafeFunction(
					objectFieldBusinessType, propertiesObjectValuePair,
					fieldName, "key_" + listTypeEntriesHeaderIndex));

			multiselectPickListColumnDescriptors[i + 1] = ColumnDescriptor.from(
				StringBundler.concat(
					fieldName, ".name_", listTypeEntriesHeaderIndex),
				index++,
				_getObjectEntryCustomFieldUnsafeFunction(
					objectFieldBusinessType, propertiesObjectValuePair,
					fieldName, "name_" + listTypeEntriesHeaderIndex));

			listTypeEntriesHeaderIndex++;
		}

		return multiselectPickListColumnDescriptors;
	}

	private UnsafeFunction<Object, Object, ReflectiveOperationException>
		_getObjectEntryCustomFieldUnsafeFunction(
			String objectFieldBusinessType,
			ObjectValuePair<Field, Method> propertiesObjectValuePair,
			String... fieldNames) {

		if (Objects.equals(
				objectFieldBusinessType,
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

			return new UnsafeFunction
				<Object, Object, ReflectiveOperationException>() {

				@Override
				public Object apply(Object object)
					throws ReflectiveOperationException {

					Map<?, ?> map = (Map<?, ?>)_getValue(
						object, propertiesObjectValuePair);

					Object value = map.get(fieldNames[0]);

					if (value == null) {
						return StringPool.BLANK;
					}

					return _getMultiselectListEntryValue(
						fieldNames[1], (List<ListEntry>)value);
				}

			};
		}

		if (Objects.equals(
				objectFieldBusinessType,
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			return new UnsafeFunction
				<Object, Object, ReflectiveOperationException>() {

				@Override
				public Object apply(Object object)
					throws ReflectiveOperationException {

					Map<?, ?> map = (Map<?, ?>)_getValue(
						object, propertiesObjectValuePair);

					Object value = map.get(fieldNames[0]);

					if (value == null) {
						return StringPool.BLANK;
					}

					return _getListEntryValue(fieldNames[1], value);
				}

			};
		}

		return new UnsafeFunction
			<Object, Object, ReflectiveOperationException>() {

			@Override
			public Object apply(Object object)
				throws ReflectiveOperationException {

				Map<?, ?> map = (Map<?, ?>)_getValue(
					object, propertiesObjectValuePair);

				Object value = map.get(fieldNames[0]);

				if (value == null) {
					return StringPool.BLANK;
				}

				return CSVUtil.encode(value);
			}

		};
	}

	private ColumnDescriptor[] _getPickListColumnDescriptors(
		String fieldName, int index, String objectFieldBusinessType,
		ObjectValuePair<Field, Method> propertiesObjectValuePair) {

		ColumnDescriptor[] pickListColumnDescriptors = new ColumnDescriptor[2];

		pickListColumnDescriptors[0] = ColumnDescriptor.from(
			fieldName + ".key", index++,
			_getObjectEntryCustomFieldUnsafeFunction(
				objectFieldBusinessType, propertiesObjectValuePair, fieldName,
				"key"));

		pickListColumnDescriptors[1] = ColumnDescriptor.from(
			fieldName + ".name", index,
			_getObjectEntryCustomFieldUnsafeFunction(
				objectFieldBusinessType, propertiesObjectValuePair, fieldName,
				"name"));

		return pickListColumnDescriptors;
	}

	private Object _getValue(
			Object object, ObjectValuePair<Field, Method> objectValuePair)
		throws ReflectiveOperationException {

		Method method = objectValuePair.getValue();

		if (method == null) {
			Field field = objectValuePair.getKey();

			return field.get(object);
		}

		return method.invoke(object);
	}

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}