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
 * @generated
 */
public class DDMStorageLinkSoap implements Serializable {

	public static DDMStorageLinkSoap toSoapModel(DDMStorageLink model) {
		DDMStorageLinkSoap soapModel = new DDMStorageLinkSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setStorageLinkId(model.getStorageLinkId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setStructureId(model.getStructureId());
		soapModel.setStructureVersionId(model.getStructureVersionId());

		return soapModel;
	}

	public static DDMStorageLinkSoap[] toSoapModels(DDMStorageLink[] models) {
		DDMStorageLinkSoap[] soapModels = new DDMStorageLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMStorageLinkSoap[][] toSoapModels(
		DDMStorageLink[][] models) {

		DDMStorageLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DDMStorageLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMStorageLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMStorageLinkSoap[] toSoapModels(
		List<DDMStorageLink> models) {

		List<DDMStorageLinkSoap> soapModels = new ArrayList<DDMStorageLinkSoap>(
			models.size());

		for (DDMStorageLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMStorageLinkSoap[soapModels.size()]);
	}

	public DDMStorageLinkSoap() {
	}

	public long getPrimaryKey() {
		return _storageLinkId;
	}

	public void setPrimaryKey(long pk) {
		setStorageLinkId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getStorageLinkId() {
		return _storageLinkId;
	}

	public void setStorageLinkId(long storageLinkId) {
		_storageLinkId = storageLinkId;
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

	public long getStructureId() {
		return _structureId;
	}

	public void setStructureId(long structureId) {
		_structureId = structureId;
	}

	public long getStructureVersionId() {
		return _structureVersionId;
	}

	public void setStructureVersionId(long structureVersionId) {
		_structureVersionId = structureVersionId;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _storageLinkId;
	private long _companyId;
	private long _classNameId;
	private long _classPK;
	private long _structureId;
	private long _structureVersionId;

}