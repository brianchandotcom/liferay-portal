/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.writer;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.batch.engine.csv.ColumnDescriptor;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Shuyang Zhou
 * @author Igor Beslic
 */
public class ColumnValuesExtractor {

	public ColumnValuesExtractor(
			Map<String, ObjectValuePair<Field, Method>>
				fieldNameObjectValuePairs,
			List<String> fieldNames, long objectDefinitionId)
		throws PortalException {

		_columnDescriptors = _getColumnDescriptors(
			fieldNameObjectValuePairs, fieldNames, 0, objectDefinitionId, null);
	}

	public List<Object[]> extractValues(Object item)
		throws ReflectiveOperationException {

		List<Object[]> valuesList = new ArrayList<>();

		Object[] values = _getBlankValues(_columnDescriptors.length);

		List<ColumnDescriptor> childFieldColumnDescriptors = new ArrayList<>();

		for (ColumnDescriptor columnDescriptor : _columnDescriptors) {
			if (columnDescriptor.isChild()) {
				childFieldColumnDescriptors.add(columnDescriptor);

				continue;
			}

			values[columnDescriptor.getIndex()] = columnDescriptor.getValue(item);
		}

		valuesList.add(values);

		int hash = -1;

		for (ColumnDescriptor childFieldColumnDescriptor :
				childFieldColumnDescriptors) {

			if (hash != childFieldColumnDescriptor.getParentHashCode()) {
				hash = childFieldColumnDescriptor.getParentHashCode();

				values = _getBlankValues(_columnDescriptors.length);

				valuesList.add(values);
			}

			values[childFieldColumnDescriptor.getIndex()] =
				childFieldColumnDescriptor.getValue(item);
		}

		return valuesList;
	}

	public String[] getHeaders() {
		String[] headers = new String[_columnDescriptors.length];

		for (ColumnDescriptor columnDescriptor : _columnDescriptors) {
			headers[columnDescriptor.getIndex()] = columnDescriptor.getHeader();
		}

		return headers;
	}

	private <T> T[] _combine(T[] array1, T[] array2, int index) {
		Class<?> array1Class = array1.getClass();

		T[] newArray = (T[])Array.newInstance(
			array1Class.getComponentType(), array1.length + array2.length - 1);

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, index, array2.length);

		return newArray;
	}

	private Object[] _getBlankValues(int size) {
		Object[] objects = new Object[size];

		Arrays.fill(objects, StringPool.BLANK);

		return objects;
	}

	private ColumnDescriptor[] _getColumnDescriptors(
			Map<String, ObjectValuePair<Field, Method>>
				fieldNameObjectValuePairs,
			Collection<String> fieldNames, int masterIndex,
			long objectDefinitionId, ColumnDescriptor parentColumnDescriptor)
		throws PortalException {

		ColumnDescriptor[] columnDescriptors =
			new ColumnDescriptor[fieldNames.size()];
		int localIndex = 0;

		for (String fieldName : fieldNames) {
			ObjectValuePair<Field, Method> objectValuePair =
				fieldNameObjectValuePairs.get(fieldName);

			if (objectValuePair == null) {
				ObjectField objectField =
					ObjectFieldLocalServiceUtil.getObjectField(
						objectDefinitionId, fieldName);

				if (Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.
							BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

					ColumnDescriptor[] multiselectPickListColumnDescriptors =
						_getMultiselectPickListColumnDescriptors(
							fieldName, objectField.getListTypeDefinitionId(),
							masterIndex, objectField.getBusinessType(),
							parentColumnDescriptor,
							fieldNameObjectValuePairs.get("properties"));

					columnDescriptors = _combine(
						columnDescriptors, multiselectPickListColumnDescriptors,
						localIndex);

					masterIndex += multiselectPickListColumnDescriptors.length;

					localIndex += multiselectPickListColumnDescriptors.length;

					continue;
				}

				if (Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

					ColumnDescriptor[] pickListColumnDescriptors =
						_getPickListColumnDescriptors(
							fieldName, masterIndex,
							objectField.getBusinessType(),
							parentColumnDescriptor,
							fieldNameObjectValuePairs.get("properties"));

					columnDescriptors = _combine(
						columnDescriptors, pickListColumnDescriptors,
						localIndex);

					masterIndex += pickListColumnDescriptors.length;

					localIndex += pickListColumnDescriptors.length;

					continue;
				}

				columnDescriptors[localIndex] = ColumnDescriptor.from(
					null, fieldName, masterIndex++, null,
					parentColumnDescriptor,
					_getObjectEntryCustomFieldUnsafeFunction(
						objectField.getBusinessType(),
						fieldNameObjectValuePairs.get("properties"),
						fieldName));

				localIndex++;

				continue;
			}

			Field field = objectValuePair.getKey();

			columnDescriptors[localIndex] = ColumnDescriptor.from(
				field, field.getName(), masterIndex++,
				objectValuePair.getValue(), parentColumnDescriptor,
				_getPOJOFieldUnsafeFunction(
					fieldNameObjectValuePairs.get(fieldName)));

			Class<?> fieldClass = field.getType();

			if (ItemClassIndexUtil.isMap(fieldClass) ||
				ItemClassIndexUtil.isSingleColumnAdoptableArray(fieldClass) ||
				ItemClassIndexUtil.isSingleColumnAdoptableValue(fieldClass)) {

				localIndex++;

				continue;
			}

			if (ItemClassIndexUtil.isIterable(fieldClass)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Mapping collection of ",
							fieldClass.getDeclaredClasses(),
							" to a single column may not contain all data"));
				}

				localIndex++;

				continue;
			}

			Map<String, ObjectValuePair<Field, Method>>
				childFieldMethodPairsMap = ItemClassIndexUtil.index(fieldClass);

			ColumnDescriptor[] childFieldColumnDescriptors =
				_getColumnDescriptors(
					childFieldMethodPairsMap,
					_sort(childFieldMethodPairsMap.keySet()), localIndex,
					objectDefinitionId, columnDescriptors[localIndex]);

			columnDescriptors = _combine(
				columnDescriptors, childFieldColumnDescriptors, localIndex);

			masterIndex = _getLastMasterIndex(childFieldColumnDescriptors) + 1;

			localIndex = localIndex + childFieldColumnDescriptors.length;
		}

		return columnDescriptors;
	}

	private int _getLastMasterIndex(ColumnDescriptor[] columnDescriptors) {
		ColumnDescriptor columnDescriptor =
			columnDescriptors[columnDescriptors.length - 1];

		return columnDescriptor.getIndex();
	}

	private String _getListEntryValue(Object object, String fieldName) {
		ListEntry listEntry = (ListEntry)object;

		if (Objects.equals(fieldName, "key")) {
			return listEntry.getKey();
		}

		return listEntry.getName();
	}

	private String _getMultiselectListEntryValue(
		List<ListEntry> listEntries, String fieldName) {

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
		String fieldName, long listTypeDefinitionId, int masterIndex,
		String objectFieldBusinessType, ColumnDescriptor parentColumnDescriptor,
		ObjectValuePair<Field, Method> propertiesObjectValuePair) {

		int listTypeEntriesCount =
			ListTypeEntryLocalServiceUtil.getListTypeEntriesCount(
				listTypeDefinitionId);

		ColumnDescriptor[] multiselectPickListColumnDescriptors =
			new ColumnDescriptor[listTypeEntriesCount * 2];

		int listTypeEntriesHeaderIndex = 1;

		for (int i = 0; i < multiselectPickListColumnDescriptors.length;
			 i = i + 2) {

			multiselectPickListColumnDescriptors[i] = ColumnDescriptor.from(
				null,
				StringBundler.concat(
					fieldName, ".key_", listTypeEntriesHeaderIndex),
				masterIndex++, null, parentColumnDescriptor,
				_getObjectEntryCustomFieldUnsafeFunction(
					objectFieldBusinessType, propertiesObjectValuePair,
					fieldName, "key_" + listTypeEntriesHeaderIndex));

			multiselectPickListColumnDescriptors[i + 1] =
				ColumnDescriptor.from(
					null,
					StringBundler.concat(
						fieldName, ".name_", listTypeEntriesHeaderIndex),
					masterIndex++, null, parentColumnDescriptor,
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
						(List<ListEntry>)value, fieldNames[1]);
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

					return _getListEntryValue(value, fieldNames[1]);
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
		String fieldName, int masterIndex, String objectFieldBusinessType,
		ColumnDescriptor parentColumnDescriptor,
		ObjectValuePair<Field, Method> propertiesObjectValuePair) {

		ColumnDescriptor[] pickListColumnDescriptors = new ColumnDescriptor[2];

		pickListColumnDescriptors[0] = ColumnDescriptor.from(
			null, fieldName + ".key", masterIndex++, null,
			parentColumnDescriptor,
			_getObjectEntryCustomFieldUnsafeFunction(
				objectFieldBusinessType, propertiesObjectValuePair, fieldName,
				"key"));

		pickListColumnDescriptors[1] = ColumnDescriptor.from(
			null, fieldName + ".name", masterIndex++, null,
			parentColumnDescriptor,
			_getObjectEntryCustomFieldUnsafeFunction(
				objectFieldBusinessType, propertiesObjectValuePair, fieldName,
				"name"));

		return pickListColumnDescriptors;
	}

	private UnsafeFunction<Object, Object, ReflectiveOperationException>
		_getPOJOFieldUnsafeFunction(
			ObjectValuePair<Field, Method> objectValuePair) {

		Field field = objectValuePair.getKey();

		Class<?> fieldClass = field.getType();

		if (ItemClassIndexUtil.isSingleColumnAdoptableValue(fieldClass)) {
			if (ItemClassIndexUtil.isDate(fieldClass)) {
				DateFormat dateFormat = new ISO8601DateFormat();

				return new UnsafeFunction
					<Object, Object, ReflectiveOperationException>() {

					@Override
					public Object apply(Object object)
						throws ReflectiveOperationException {

						Object value = _getValue(object, objectValuePair);

						if (value == null) {
							return StringPool.BLANK;
						}

						return dateFormat.format(value);
					}

				};
			}

			return new UnsafeFunction
				<Object, Object, ReflectiveOperationException>() {

				@Override
				public Object apply(Object object)
					throws ReflectiveOperationException {

					Object value = _getValue(object, objectValuePair);

					if (value == null) {
						return StringPool.BLANK;
					}

					return value;
				}

			};
		}

		if (ItemClassIndexUtil.isSingleColumnAdoptableArray(fieldClass)) {
			return new UnsafeFunction
				<Object, Object, ReflectiveOperationException>() {

				@Override
				public Object apply(Object object)
					throws ReflectiveOperationException {

					Object value = _getValue(object, objectValuePair);

					if (value == null) {
						return StringPool.BLANK;
					}

					return StringUtil.merge(
						(Object[])value, CSVUtil::encode, StringPool.COMMA);
				}

			};
		}

		if (ItemClassIndexUtil.isMap(fieldClass)) {
			return new UnsafeFunction
				<Object, Object, ReflectiveOperationException>() {

				@Override
				public Object apply(Object object)
					throws ReflectiveOperationException {

					Map<?, ?> map = (Map<?, ?>)_getValue(
						object, objectValuePair);

					if (map == null) {
						return StringPool.BLANK;
					}

					StringBundler sb = new StringBundler(map.size() * 3);

					Set<? extends Map.Entry<?, ?>> entries = map.entrySet();

					Iterator<? extends Map.Entry<?, ?>> iterator =
						entries.iterator();

					while (iterator.hasNext()) {
						Map.Entry<?, ?> entry = iterator.next();

						sb.append(CSVUtil.encode(entry.getKey()));

						sb.append(StringPool.COLON);

						if (entry.getValue() != null) {
							sb.append(CSVUtil.encode(entry.getValue()));
						}
						else {
							sb.append(StringPool.BLANK);
						}

						if (iterator.hasNext()) {
							sb.append(StringPool.COMMA_AND_SPACE);
						}
					}

					return sb.toString();
				}

			};
		}

		return new UnsafeFunction
			<Object, Object, ReflectiveOperationException>() {

			@Override
			public Object apply(Object object)
				throws ReflectiveOperationException {

				if (_getValue(object, objectValuePair) == null) {
					return StringPool.BLANK;
				}

				return CSVUtil.encode(object);
			}

		};
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

	private Collection<String> _sort(Collection<String> collection) {
		return ListUtil.sort(
			new ArrayList<>(collection),
			(value1, value2) -> value1.compareToIgnoreCase(value2));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ColumnValuesExtractor.class);

	private final ColumnDescriptor[] _columnDescriptors;

}