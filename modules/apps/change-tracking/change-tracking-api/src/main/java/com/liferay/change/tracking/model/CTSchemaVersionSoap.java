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

package com.liferay.change.tracking.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CTSchemaVersionSoap implements Serializable {

	public static CTSchemaVersionSoap toSoapModel(CTSchemaVersion model) {
		CTSchemaVersionSoap soapModel = new CTSchemaVersionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setSchemaVersionId(model.getSchemaVersionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setSchemaContext(model.getSchemaContext());

		return soapModel;
	}

	public static CTSchemaVersionSoap[] toSoapModels(CTSchemaVersion[] models) {
		CTSchemaVersionSoap[] soapModels =
			new CTSchemaVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTSchemaVersionSoap[][] toSoapModels(
		CTSchemaVersion[][] models) {

		CTSchemaVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CTSchemaVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTSchemaVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTSchemaVersionSoap[] toSoapModels(
		List<CTSchemaVersion> models) {

		List<CTSchemaVersionSoap> soapModels =
			new ArrayList<CTSchemaVersionSoap>(models.size());

		for (CTSchemaVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CTSchemaVersionSoap[soapModels.size()]);
	}

	public CTSchemaVersionSoap() {
	}

	public long getPrimaryKey() {
		return _schemaVersionId;
	}

	public void setPrimaryKey(long pk) {
		setSchemaVersionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getSchemaVersionId() {
		return _schemaVersionId;
	}

	public void setSchemaVersionId(long schemaVersionId) {
		_schemaVersionId = schemaVersionId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Map<String, Serializable> getSchemaContext() {
		return _schemaContext;
	}

	public void setSchemaContext(Map<String, Serializable> schemaContext) {
		_schemaContext = schemaContext;
	}

	private long _mvccVersion;
	private long _schemaVersionId;
	private long _companyId;
	private Map<String, Serializable> _schemaContext;

}