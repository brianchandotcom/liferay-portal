/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.liferay.petra.string.StringBundler;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class Field {

	public String getContext() {
		return _context;
	}

	public String getDataSourceId() {
		return _dataSourceId;
	}

	public String getDataSourceName() {
		return _dataSourceName;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public String getFieldType() {
		return _fieldType;
	}

	public String getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public String getOwnerId() {
		return _ownerId;
	}

	public String getOwnerType() {
		return _ownerType;
	}

	public String getSourceName() {
		return _sourceName;
	}

	public String getValue() {
		return _value;
	}

	public void setContext(String context) {
		_context = context;
	}

	public void setDataSourceId(String dataSourceId) {
		_dataSourceId = dataSourceId;
	}

	public void setDataSourceName(String dataSourceName) {
		_dataSourceName = dataSourceName;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setFieldType(String fieldType) {
		_fieldType = fieldType;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOwnerId(String ownerId) {
		_ownerId = ownerId;
	}

	public void setOwnerType(String ownerType) {
		_ownerType = ownerType;
	}

	public void setSourceName(String sourceName) {
		_sourceName = sourceName;
	}

	public void setValue(String value) {
		_value = value;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{context=", _context, ", dataSourceId=", _dataSourceId,
			", dataSourceName=", _dataSourceName, ", dateModified=",
			_dateModified, ", fieldType=", _fieldType, ", id=", _id, ", label=",
			_label, ", name=", _name, ", ownerId=", _ownerId, ", ownerType=",
			_ownerType, ", sourceName=", _sourceName, ", value=", _value, "}");
	}

	private String _context;
	private String _dataSourceId;
	private String _dataSourceName;
	private Date _dateModified;
	private String _fieldType;
	private String _id;
	private String _label;
	private String _name;
	private String _ownerId;
	private String _ownerType;
	private String _sourceName;
	private String _value;

}