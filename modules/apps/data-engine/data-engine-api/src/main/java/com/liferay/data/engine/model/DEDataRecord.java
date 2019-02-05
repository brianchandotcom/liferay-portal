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
 * This class represents a record belonging to a {@link DEDataRecordCollection}.
 *
 * @author Leonardo Barros
 * @review
 */
public class DEDataRecord implements Serializable {

	/**
	 * Overrided equals method
	 * @param obj
	 * @return
	 * @review
	 */
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

	/**
	 * Returns the data definition related to the data record
	 *
	 * @return the data definition
	 * @review
	 */
	public DEDataDefinition getDEDataDefinition() {
		DEDataRecordCollection deDataRecordCollection =
			getDEDataRecordCollection();

		if (deDataRecordCollection == null) {
			return null;
		}

		return deDataRecordCollection.getDEDataDefinition();
	}

	/**
	 * Returns the data record collection related to the data record
	 *
	 * @return the data record collection
	 * @review
	 */
	public DEDataRecordCollection getDEDataRecordCollection() {
		return _deDataRecordCollection;
	}

	/**
	 * Returns the id from the data record collection related to the data record
	 *
	 * @return the data record collection id
	 * @review
	 */
	public long getDEDataRecordCollectionId() {
		DEDataRecordCollection deDataRecordCollection =
			getDEDataRecordCollection();

		if (deDataRecordCollection == null) {
			return 0;
		}

		return deDataRecordCollection.getDEDataRecordCollectionId();
	}

	/**
	 * Returns the id from the data record
	 *
	 * @return the data record id
	 * @review
	 */
	public long getDEDataRecordId() {
		return _deDataRecordId;
	}

	/**
	 * Returns the field values related to the data record
	 *
	 * @return a map of field values
	 * @review
	 */
	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	/**
	 * Overrided hashCode method
	 * @return
	 * @review
	 */
	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _deDataRecordId);

		hash = HashUtil.hash(hash, _deDataRecordCollection.hashCode());

		return HashUtil.hash(hash, _values.hashCode());
	}

	/**
	 * Sets the data record collection related to the data record
	 *
	 * @param deDataRecordCollection the data record collection
	 * @review
	 */
	public void setDEDataRecordCollection(
		DEDataRecordCollection deDataRecordCollection) {

		_deDataRecordCollection = deDataRecordCollection;
	}

	/**
	 * Sets the id from the data record. If no id is passed it's assumed that a create
	 * operation will be performed. If the id is informed, an update operation
	 * will be executed
	 *
	 * @param deDataRecordId the Id of the Data Record
	 * @review
	 */
	public void setDEDataRecordId(long deDataRecordId) {
		_deDataRecordId = deDataRecordId;
	}

	/**
	 * Sets the Data Record field values of the Data Record
	 *
	 * @param deDataRecordId the data record id
	 * @review
	 */
	public void setValues(Map<String, Object> values) {
		_values = values;
	}

	/**
	 * Returns true if the values passed as parameter are equals to the Data Record values
	 *
	 * @param values field values
	 * @review
	 */
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