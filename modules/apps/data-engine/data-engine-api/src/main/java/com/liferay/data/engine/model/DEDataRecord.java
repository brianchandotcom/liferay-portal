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

package com.liferay.data.engine.model;

import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class DEDataRecord implements Serializable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataRecord)) {
			return false;
		}

		DEDataRecord deDataRecord = (DEDataRecord)obj;

		Map<String, Object> values = deDataRecord._values;

		if (Objects.equals(_deDataRecordId, deDataRecord._deDataRecordId) &&
			Objects.equals(
				_deDataRecordCollection,
				deDataRecord._deDataRecordCollection) &&
			Objects.equals(_values.keySet(), values.keySet()) &&
			equals(values)) {

			return true;
		}

		return false;
	}

	public DEDataDefinition getDEDataDefinition() {
		DEDataRecordCollection deDataRecordCollection =
			getDEDataRecordCollection();

		if (deDataRecordCollection == null) {
			return null;
		}

		return deDataRecordCollection.getDEDataDefinition();
	}

	public DEDataRecordCollection getDEDataRecordCollection() {
		return _deDataRecordCollection;
	}

	public long getDEDataRecordCollectionId() {
		DEDataRecordCollection deDataRecordCollection =
			getDEDataRecordCollection();

		if (deDataRecordCollection == null) {
			return 0;
		}

		return deDataRecordCollection.getDEDataRecordCollectionId();
	}

	public long getDEDataRecordId() {
		return _deDataRecordId;
	}

	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _deDataRecordId);

		hash = HashUtil.hash(hash, _deDataRecordCollection.hashCode());

		return HashUtil.hash(hash, _values.hashCode());
	}

	public void setDEDataRecordCollection(
		DEDataRecordCollection deDataRecordCollection) {

		_deDataRecordCollection = deDataRecordCollection;
	}

	public void setDEDataRecordId(long deDataRecordId) {
		_deDataRecordId = deDataRecordId;
	}

	public void setValues(Map<String, Object> values) {
		_values = values;
	}

	protected boolean equals(Map<String, Object> values) {
		boolean result = true;

		for (Map.Entry<String, Object> entry : _values.entrySet()) {
			Object value = entry.getValue();
			Object otherValue = values.get(entry.getKey());

			Class<?> clazz = value.getClass();

			if (clazz.isArray()) {
				result = Arrays.equals((Object[])value, (Object[])otherValue);
			}
			else {
				result = Objects.equals(value, otherValue);
			}

			if (!result) {
				break;
			}
		}

		return result;
	}

	private DEDataRecordCollection _deDataRecordCollection;
	private long _deDataRecordId;
	private Map<String, Object> _values;

}