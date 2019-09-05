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

package com.liferay.expando.kernel.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.expando.service.http.ExpandoColumnServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoColumnSoap implements Serializable {

	public static ExpandoColumnSoap toSoapModel(ExpandoColumn model) {
		ExpandoColumnSoap soapModel = new ExpandoColumnSoap();

		soapModel.setColumnId(model.getColumnId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setTableId(model.getTableId());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setDefaultData(model.getDefaultData());
		soapModel.setTypeSettings(model.getTypeSettings());

		return soapModel;
	}

	public static ExpandoColumnSoap[] toSoapModels(ExpandoColumn[] models) {
		ExpandoColumnSoap[] soapModels = new ExpandoColumnSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ExpandoColumnSoap[][] toSoapModels(ExpandoColumn[][] models) {
		ExpandoColumnSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ExpandoColumnSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ExpandoColumnSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ExpandoColumnSoap[] toSoapModels(List<ExpandoColumn> models) {
		List<ExpandoColumnSoap> soapModels = new ArrayList<ExpandoColumnSoap>(
			models.size());

		for (ExpandoColumn model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ExpandoColumnSoap[soapModels.size()]);
	}

	public ExpandoColumnSoap() {
	}

	public long getPrimaryKey() {
		return _columnId;
	}

	public void setPrimaryKey(long pk) {
		setColumnId(pk);
	}

	public long getColumnId() {
		return _columnId;
	}

	public void setColumnId(long columnId) {
		_columnId = columnId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		_tableId = tableId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getDefaultData() {
		return _defaultData;
	}

	public void setDefaultData(String defaultData) {
		_defaultData = defaultData;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	private long _columnId;
	private long _companyId;
	private long _tableId;
	private String _name;
	private int _type;
	private String _defaultData;
	private String _typeSettings;

}