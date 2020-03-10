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

package com.liferay.revert.schema.version.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SchemaEntrySoap implements Serializable {

	public static SchemaEntrySoap toSoapModel(SchemaEntry model) {
		SchemaEntrySoap soapModel = new SchemaEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setCompanyId(model.getCompanyId());

		return soapModel;
	}

	public static SchemaEntrySoap[] toSoapModels(SchemaEntry[] models) {
		SchemaEntrySoap[] soapModels = new SchemaEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SchemaEntrySoap[][] toSoapModels(SchemaEntry[][] models) {
		SchemaEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SchemaEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SchemaEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SchemaEntrySoap[] toSoapModels(List<SchemaEntry> models) {
		List<SchemaEntrySoap> soapModels = new ArrayList<SchemaEntrySoap>(
			models.size());

		for (SchemaEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SchemaEntrySoap[soapModels.size()]);
	}

	public SchemaEntrySoap() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private long _mvccVersion;
	private long _entryId;
	private long _companyId;

}