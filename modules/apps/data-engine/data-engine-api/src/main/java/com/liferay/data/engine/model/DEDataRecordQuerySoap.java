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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DEDataRecordQuerySoap implements Serializable {

	public static DEDataRecordQuerySoap toSoapModel(DEDataRecordQuery model) {
		DEDataRecordQuerySoap soapModel = new DEDataRecordQuerySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setDeDataRecordQueryId(model.getDeDataRecordQueryId());
		soapModel.setAppliedFilters(model.getAppliedFilters());
		soapModel.setFieldNames(model.getFieldNames());

		return soapModel;
	}

	public static DEDataRecordQuerySoap[] toSoapModels(
		DEDataRecordQuery[] models) {

		DEDataRecordQuerySoap[] soapModels =
			new DEDataRecordQuerySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DEDataRecordQuerySoap[][] toSoapModels(
		DEDataRecordQuery[][] models) {

		DEDataRecordQuerySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DEDataRecordQuerySoap[models.length][models[0].length];
		}
		else {
			soapModels = new DEDataRecordQuerySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DEDataRecordQuerySoap[] toSoapModels(
		List<DEDataRecordQuery> models) {

		List<DEDataRecordQuerySoap> soapModels =
			new ArrayList<DEDataRecordQuerySoap>(models.size());

		for (DEDataRecordQuery model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DEDataRecordQuerySoap[soapModels.size()]);
	}

	public DEDataRecordQuerySoap() {
	}

	public long getPrimaryKey() {
		return _deDataRecordQueryId;
	}

	public void setPrimaryKey(long pk) {
		setDeDataRecordQueryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getDeDataRecordQueryId() {
		return _deDataRecordQueryId;
	}

	public void setDeDataRecordQueryId(long deDataRecordQueryId) {
		_deDataRecordQueryId = deDataRecordQueryId;
	}

	public String getAppliedFilters() {
		return _appliedFilters;
	}

	public void setAppliedFilters(String appliedFilters) {
		_appliedFilters = appliedFilters;
	}

	public String getFieldNames() {
		return _fieldNames;
	}

	public void setFieldNames(String fieldNames) {
		_fieldNames = fieldNames;
	}

	private String _uuid;
	private long _deDataRecordQueryId;
	private String _appliedFilters;
	private String _fieldNames;

}