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

package com.liferay.trash.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TrashVersionSoap implements Serializable {

	public static TrashVersionSoap toSoapModel(TrashVersion model) {
		TrashVersionSoap soapModel = new TrashVersionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setVersionId(model.getVersionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static TrashVersionSoap[] toSoapModels(TrashVersion[] models) {
		TrashVersionSoap[] soapModels = new TrashVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TrashVersionSoap[][] toSoapModels(TrashVersion[][] models) {
		TrashVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TrashVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new TrashVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TrashVersionSoap[] toSoapModels(List<TrashVersion> models) {
		List<TrashVersionSoap> soapModels = new ArrayList<TrashVersionSoap>(
			models.size());

		for (TrashVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TrashVersionSoap[soapModels.size()]);
	}

	public TrashVersionSoap() {
	}

	public long getPrimaryKey() {
		return _versionId;
	}

	public void setPrimaryKey(long pk) {
		setVersionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getVersionId() {
		return _versionId;
	}

	public void setVersionId(long versionId) {
		_versionId = versionId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
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

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private long _versionId;
	private long _companyId;
	private long _entryId;
	private long _classNameId;
	private long _classPK;
	private String _typeSettings;
	private int _status;

}