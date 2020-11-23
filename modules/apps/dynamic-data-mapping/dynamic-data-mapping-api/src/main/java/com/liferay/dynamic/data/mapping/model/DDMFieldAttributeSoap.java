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
public class DDMFieldAttributeSoap implements Serializable {

	public static DDMFieldAttributeSoap toSoapModel(DDMFieldAttribute model) {
		DDMFieldAttributeSoap soapModel = new DDMFieldAttributeSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setFieldAttributeId(model.getFieldAttributeId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setFieldId(model.getFieldId());
		soapModel.setStorageId(model.getStorageId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setAttributeName(model.getAttributeName());
		soapModel.setSmallAttributeValue(model.getSmallAttributeValue());
		soapModel.setLargeAttributeValue(model.getLargeAttributeValue());

		return soapModel;
	}

	public static DDMFieldAttributeSoap[] toSoapModels(
		DDMFieldAttribute[] models) {

		DDMFieldAttributeSoap[] soapModels =
			new DDMFieldAttributeSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMFieldAttributeSoap[][] toSoapModels(
		DDMFieldAttribute[][] models) {

		DDMFieldAttributeSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DDMFieldAttributeSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMFieldAttributeSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMFieldAttributeSoap[] toSoapModels(
		List<DDMFieldAttribute> models) {

		List<DDMFieldAttributeSoap> soapModels =
			new ArrayList<DDMFieldAttributeSoap>(models.size());

		for (DDMFieldAttribute model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMFieldAttributeSoap[soapModels.size()]);
	}

	public DDMFieldAttributeSoap() {
	}

	public long getPrimaryKey() {
		return _fieldAttributeId;
	}

	public void setPrimaryKey(long pk) {
		setFieldAttributeId(pk);
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

	public long getFieldAttributeId() {
		return _fieldAttributeId;
	}

	public void setFieldAttributeId(long fieldAttributeId) {
		_fieldAttributeId = fieldAttributeId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getFieldId() {
		return _fieldId;
	}

	public void setFieldId(long fieldId) {
		_fieldId = fieldId;
	}

	public long getStorageId() {
		return _storageId;
	}

	public void setStorageId(long storageId) {
		_storageId = storageId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getAttributeName() {
		return _attributeName;
	}

	public void setAttributeName(String attributeName) {
		_attributeName = attributeName;
	}

	public String getSmallAttributeValue() {
		return _smallAttributeValue;
	}

	public void setSmallAttributeValue(String smallAttributeValue) {
		_smallAttributeValue = smallAttributeValue;
	}

	public String getLargeAttributeValue() {
		return _largeAttributeValue;
	}

	public void setLargeAttributeValue(String largeAttributeValue) {
		_largeAttributeValue = largeAttributeValue;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _fieldAttributeId;
	private long _companyId;
	private long _fieldId;
	private long _storageId;
	private String _languageId;
	private String _attributeName;
	private String _smallAttributeValue;
	private String _largeAttributeValue;

}