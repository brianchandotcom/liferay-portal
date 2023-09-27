/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.writer;

import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

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
		Map<String, Field> fieldsMap, List<String> fieldNames) {

		_columnDescriptors = _getColumnDescriptors(
			fieldsMap, fieldNames, 0, null);
	}

	public List<Object[]> extractValues(Object item)
		throws ReflectiveOperationException {

		List<Object[]> valuesList = new ArrayList<>();

		Object[] values = _getBlankValues(_columnDescriptors.length);

		List<ColumnDescriptor> childFieldColumnDescriptors = new ArrayList<>();

		for (ColumnDescriptor columnDescriptor : _columnDescriptors) {
			if (columnDescriptor._isChild()) {
				childFieldColumnDescriptors.add(columnDescriptor);

				continue;
			}

			values[columnDescriptor._index] = columnDescriptor._getValue(item);
		}

		valuesList.add(values);

		int hash = -1;

		for (ColumnDescriptor childFieldColumnDescriptor :
				childFieldColumnDescriptors) {

			if (hash != childFieldColumnDescriptor._getParentHashCode()) {
				hash = childFieldColumnDescriptor._getParentHashCode();

				values = _getBlankValues(_columnDescriptors.length);

				valuesList.add(values);
			}

			values[childFieldColumnDescriptor._index] =
				childFieldColumnDescriptor._getValue(item);
		}

		return valuesList;
	}

	public String[] getHeaders() {
		String[] headers = new String[_columnDescriptors.length];

		for (ColumnDescriptor columnDescriptor : _columnDescriptors) {
			headers[columnDescriptor._index] = columnDescriptor._getHeader();
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
		Map<String, Field> fieldsMap, Collection<String> fieldNames,
		int masterIndex, ColumnDescriptor parentColumnDescriptor) {

		ColumnDescriptor[] columnDescriptors =
			new ColumnDescriptor[fieldNames.size()];
		int localIndex = 0;

		for (String fieldName : fieldNames) {
			Field field = fieldsMap.get(fieldName);

			if (field == null) {
				columnDescriptors[localIndex] = ColumnDescriptor._from(
					null, fieldName, masterIndex++, parentColumnDescriptor,
					_getUnsafeFunction(fieldsMap, fieldName));

				localIndex++;

				continue;
			}

			columnDescriptors[localIndex] = ColumnDescriptor._from(
				field, field.getName(), masterIndex++, parentColumnDescriptor,
				_getUnsafeFunction(fieldsMap, fieldName));

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

			Map<String, Field> childFieldsMap = ItemClassIndexUtil.index(
				fieldClass);

			ColumnDescriptor[] childFieldColumnDescriptors =
				_getColumnDescriptors(
					childFieldsMap, _sort(childFieldsMap.keySet()), localIndex,
					columnDescriptors[localIndex]);

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

		return columnDescriptor._index;
	}

	private String _getListEntryKey(Object object) {
		ListEntry listEntry = (ListEntry)object;

		return listEntry.getKey();
	}

	private UnsafeFunction<Object, Object, ReflectiveOperationException>
		_getUnsafeFunction(Map<String, Field> fieldsMap, String fieldName) {

		Field field = fieldsMap.get(fieldName);

		if (field != null) {
			Class<?> fieldClass = field.getType();

			if (ItemClassIndexUtil.isSingleColumnAdoptableValue(fieldClass)) {
				return new UnsafeFunction
					<Object, Object, ReflectiveOperationException>() {

					@Override
					public Object apply(Object object)
						throws ReflectiveOperationException {

						if (field.get(object) == null) {
							return StringPool.BLANK;
						}

						return field.get(object);
					}

				};
			}

			if (ItemClassIndexUtil.isSingleColumnAdoptableArray(fieldClass)) {
				return new UnsafeFunction
					<Object, Object, ReflectiveOperationException>() {

					@Override
					public Object apply(Object object)
						throws ReflectiveOperationException {

						if (field.get(object) == null) {
							return StringPool.BLANK;
						}

						return StringUtil.merge(
							(Object[])field.get(object), CSVUtil::encode,
							StringPool.COMMA);
					}

				};
			}

			if (ItemClassIndexUtil.isMap(fieldClass)) {
				return new UnsafeFunction
					<Object, Object, ReflectiveOperationException>() {

					@Override
					public Object apply(Object object)
						throws ReflectiveOperationException {

						Map<?, ?> map = (Map<?, ?>)field.get(object);

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

					if (field.get(object) == null) {
						return StringPool.BLANK;
					}

					return CSVUtil.encode(object);
				}

			};
		}

		Field propertiesField = fieldsMap.get("properties");

		if (!ItemClassIndexUtil.isObjectEntryProperties(propertiesField)) {
			throw new IllegalArgumentException(
				"Invalid field name: " + fieldName);
		}

		return new UnsafeFunction
			<Object, Object, ReflectiveOperationException>() {

			@Override
			public Object apply(Object object)
				throws ReflectiveOperationException {

				Map<?, ?> map = (Map<?, ?>)propertiesField.get(object);

				Object value = map.get(fieldName);

				if (value == null) {
					return StringPool.BLANK;
				}

				if (ItemClassIndexUtil.isListEntry(value)) {
					return _getListEntryKey(value);
				}

				if (value instanceof String) {
					return CSVUtil.encode(value);
				}

				return value;
			}

		};
	}

	private Collection<String> _sort(Collection<String> collection) {
		return ListUtil.sort(
			new ArrayList<>(collection),
			(value1, value2) -> value1.compareToIgnoreCase(value2));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ColumnValuesExtractor.class);

	private final ColumnDescriptor[] _columnDescriptors;

	private static class ColumnDescriptor {

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof ColumnDescriptor)) {
				return false;
			}

			ColumnDescriptor columnDescriptor = (ColumnDescriptor)object;

			if (Objects.equals(_field, columnDescriptor._field) &&
				_parentColumnDescriptors.equals(
					columnDescriptor._parentColumnDescriptors)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return _field.hashCode();
		}

		private static ColumnDescriptor _from(
			Field field, String fieldName, int index,
			ColumnDescriptor parentColumnDescriptor,
			UnsafeFunction<Object, Object, ReflectiveOperationException>
				unsafeFunction) {

			ColumnDescriptor columnDescriptor = new ColumnDescriptor(
				field, fieldName, index, unsafeFunction);

			if (parentColumnDescriptor == null) {
				return columnDescriptor;
			}

			columnDescriptor._add(parentColumnDescriptor);

			return columnDescriptor;
		}

		private ColumnDescriptor(
			Field field, String fieldName, int index,
			UnsafeFunction<Object, Object, ReflectiveOperationException>
				unsafeFunction) {

			_field = field;
			_fieldName = fieldName;
			_index = index;
			_unsafeFunction = unsafeFunction;
		}

		private void _add(ColumnDescriptor columnDescriptor) {
			if (!columnDescriptor._parentColumnDescriptors.isEmpty()) {
				_parentColumnDescriptors.addAll(
					columnDescriptor._parentColumnDescriptors);
			}

			_parentColumnDescriptors.add(columnDescriptor);
		}

		private String _getHeader() {
			StringBundler sb = new StringBundler(
				(_parentColumnDescriptors.size() * 2) + 2);

			for (ColumnDescriptor columnDescriptor : _parentColumnDescriptors) {
				sb.append(columnDescriptor._getSanitizedFieldName());
				sb.append(StringPool.PERIOD);
			}

			sb.append(_getSanitizedFieldName());

			return sb.toString();
		}

		private int _getParentHashCode() {
			if (_parentColumnDescriptors.isEmpty()) {
				throw new UnsupportedOperationException();
			}

			ColumnDescriptor columnDescriptor = _parentColumnDescriptors.get(
				_parentColumnDescriptors.size() - 1);

			return columnDescriptor.hashCode();
		}

		private String _getSanitizedFieldName() {
			if (_fieldName.startsWith(StringPool.UNDERLINE)) {
				return _fieldName.substring(1);
			}

			return _fieldName;
		}

		private Object _getValue(Object object)
			throws ReflectiveOperationException {

			if (!_isChild()) {
				return _unsafeFunction.apply(object);
			}

			Object result = object;

			for (ColumnDescriptor columnDescriptor : _parentColumnDescriptors) {
				result = columnDescriptor._field.get(result);

				if (result == null) {
					return StringPool.BLANK;
				}
			}

			return _unsafeFunction.apply(result);
		}

		private boolean _isChild() {
			if (_parentColumnDescriptors.isEmpty()) {
				return false;
			}

			return true;
		}

		private final Field _field;
		private final String _fieldName;
		private final int _index;
		private final List<ColumnDescriptor> _parentColumnDescriptors =
			new ArrayList<>();
		private final UnsafeFunction
			<Object, Object, ReflectiveOperationException> _unsafeFunction;

	}

}