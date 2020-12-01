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

package com.liferay.dynamic.data.mapping.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DDMFieldSoap implements Serializable {

	public static DDMFieldSoap toSoapModel(DDMField model) {
		DDMFieldSoap soapModel = new DDMFieldSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setFieldId(model.getFieldId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setParentFieldId(model.getParentFieldId());
		soapModel.setStorageId(model.getStorageId());
		soapModel.setStructureVersionId(model.getStructureVersionId());
		soapModel.setFieldName(model.getFieldName());
		soapModel.setFieldType(model.getFieldType());
		soapModel.setInstanceId(model.getInstanceId());
		soapModel.setLocalizable(model.isLocalizable());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static DDMFieldSoap[] toSoapModels(DDMField[] models) {
		DDMFieldSoap[] soapModels = new DDMFieldSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMFieldSoap[][] toSoapModels(DDMField[][] models) {
		DDMFieldSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDMFieldSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMFieldSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMFieldSoap[] toSoapModels(List<DDMField> models) {
		List<DDMFieldSoap> soapModels = new ArrayList<DDMFieldSoap>(
			models.size());

		for (DDMField model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMFieldSoap[soapModels.size()]);
	}

	public DDMFieldSoap() {
	}

	public long getPrimaryKey() {
		return _fieldId;
	}

	public void setPrimaryKey(long pk) {
		setFieldId(pk);
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

	public long getFieldId() {
		return _fieldId;
	}

	public void setFieldId(long fieldId) {
		_fieldId = fieldId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getParentFieldId() {
		return _parentFieldId;
	}

	public void setParentFieldId(long parentFieldId) {
		_parentFieldId = parentFieldId;
	}

	public long getStorageId() {
		return _storageId;
	}

	public void setStorageId(long storageId) {
		_storageId = storageId;
	}

	public long getStructureVersionId() {
		return _structureVersionId;
	}

	public void setStructureVersionId(long structureVersionId) {
		_structureVersionId = structureVersionId;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public String getFieldType() {
		return _fieldType;
	}

	public void setFieldType(String fieldType) {
		_fieldType = fieldType;
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public void setInstanceId(String instanceId) {
		_instanceId = instanceId;
	}

	public boolean getLocalizable() {
		return _localizable;
	}

	public boolean isLocalizable() {
		return _localizable;
	}

	public void setLocalizable(boolean localizable) {
		_localizable = localizable;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _fieldId;
	private long _companyId;
	private long _parentFieldId;
	private long _storageId;
	private long _structureVersionId;
	private String _fieldName;
	private String _fieldType;
	private String _instanceId;
	private boolean _localizable;
	private int _priority;

}