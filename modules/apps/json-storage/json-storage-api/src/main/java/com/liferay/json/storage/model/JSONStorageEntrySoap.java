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

package com.liferay.json.storage.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Preston Crary
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class JSONStorageEntrySoap implements Serializable {

	public static JSONStorageEntrySoap toSoapModel(JSONStorageEntry model) {
		JSONStorageEntrySoap soapModel = new JSONStorageEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setJsonStorageEntryId(model.getJsonStorageEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setParentJSONStorageEntryId(
			model.getParentJSONStorageEntryId());
		soapModel.setIndex(model.getIndex());
		soapModel.setKey(model.getKey());
		soapModel.setType(model.getType());
		soapModel.setValueLong(model.getValueLong());
		soapModel.setValueString(model.getValueString());

		return soapModel;
	}

	public static JSONStorageEntrySoap[] toSoapModels(
		JSONStorageEntry[] models) {

		JSONStorageEntrySoap[] soapModels =
			new JSONStorageEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static JSONStorageEntrySoap[][] toSoapModels(
		JSONStorageEntry[][] models) {

		JSONStorageEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new JSONStorageEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new JSONStorageEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static JSONStorageEntrySoap[] toSoapModels(
		List<JSONStorageEntry> models) {

		List<JSONStorageEntrySoap> soapModels =
			new ArrayList<JSONStorageEntrySoap>(models.size());

		for (JSONStorageEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new JSONStorageEntrySoap[soapModels.size()]);
	}

	public JSONStorageEntrySoap() {
	}

	public long getPrimaryKey() {
		return _jsonStorageEntryId;
	}

	public void setPrimaryKey(long pk) {
		setJsonStorageEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getJsonStorageEntryId() {
		return _jsonStorageEntryId;
	}

	public void setJsonStorageEntryId(long jsonStorageEntryId) {
		_jsonStorageEntryId = jsonStorageEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getParentJSONStorageEntryId() {
		return _parentJSONStorageEntryId;
	}

	public void setParentJSONStorageEntryId(long parentJSONStorageEntryId) {
		_parentJSONStorageEntryId = parentJSONStorageEntryId;
	}

	public int getIndex() {
		return _index;
	}

	public void setIndex(int index) {
		_index = index;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public long getValueLong() {
		return _valueLong;
	}

	public void setValueLong(long valueLong) {
		_valueLong = valueLong;
	}

	public String getValueString() {
		return _valueString;
	}

	public void setValueString(String valueString) {
		_valueString = valueString;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _jsonStorageEntryId;
	private long _companyId;
	private long _classNameId;
	private long _classPK;
	private long _parentJSONStorageEntryId;
	private int _index;
	private String _key;
	private int _type;
	private long _valueLong;
	private String _valueString;

}