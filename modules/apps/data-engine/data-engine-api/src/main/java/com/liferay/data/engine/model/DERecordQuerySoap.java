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
public class DERecordQuerySoap implements Serializable {

	public static DERecordQuerySoap toSoapModel(DERecordQuery model) {
		DERecordQuerySoap soapModel = new DERecordQuerySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setRecordQueryId(model.getRecordQueryId());
		soapModel.setAppliedFilters(model.getAppliedFilters());
		soapModel.setFieldNames(model.getFieldNames());

		return soapModel;
	}

	public static DERecordQuerySoap[] toSoapModels(DERecordQuery[] models) {
		DERecordQuerySoap[] soapModels = new DERecordQuerySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DERecordQuerySoap[][] toSoapModels(DERecordQuery[][] models) {
		DERecordQuerySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DERecordQuerySoap[models.length][models[0].length];
		}
		else {
			soapModels = new DERecordQuerySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DERecordQuerySoap[] toSoapModels(List<DERecordQuery> models) {
		List<DERecordQuerySoap> soapModels = new ArrayList<DERecordQuerySoap>(
			models.size());

		for (DERecordQuery model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DERecordQuerySoap[soapModels.size()]);
	}

	public DERecordQuerySoap() {
	}

	public long getPrimaryKey() {
		return _recordQueryId;
	}

	public void setPrimaryKey(long pk) {
		setRecordQueryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getRecordQueryId() {
		return _recordQueryId;
	}

	public void setRecordQueryId(long recordQueryId) {
		_recordQueryId = recordQueryId;
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
	private long _recordQueryId;
	private String _appliedFilters;
	private String _fieldNames;

}