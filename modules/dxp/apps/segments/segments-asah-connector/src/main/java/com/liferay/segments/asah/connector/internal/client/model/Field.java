/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.liferay.petra.string.StringBundler;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class Field {

	public Field() {
	}

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
		StringBundler sb = new StringBundler(25);

		sb.append("{context=");
		sb.append(_context);
		sb.append(", dataSourceId=");
		sb.append(_dataSourceId);
		sb.append(", dataSourceName=");
		sb.append(_dataSourceName);
		sb.append(", dateModified=");
		sb.append(_dateModified);
		sb.append(", fieldType=");
		sb.append(_fieldType);
		sb.append(", id=");
		sb.append(_id);
		sb.append(", label=");
		sb.append(_label);
		sb.append(", name=");
		sb.append(_name);
		sb.append(", ownerId=");
		sb.append(_ownerId);
		sb.append(", ownerType=");
		sb.append(_ownerType);
		sb.append(", sourceName=");
		sb.append(_sourceName);
		sb.append(", value=");
		sb.append(_value);
		sb.append("}");

		return sb.toString();
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