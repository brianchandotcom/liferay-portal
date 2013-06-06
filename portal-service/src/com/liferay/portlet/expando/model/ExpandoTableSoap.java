/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoTableSoap implements Serializable {
	public static ExpandoTableSoap toSoapModel(ExpandoTable model) {
		ExpandoTableSoap soapModel = new ExpandoTableSoap();

		soapModel.setTableId(model.getTableId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static ExpandoTableSoap[] toSoapModels(ExpandoTable[] models) {
		ExpandoTableSoap[] soapModels = new ExpandoTableSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ExpandoTableSoap[][] toSoapModels(ExpandoTable[][] models) {
		ExpandoTableSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ExpandoTableSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ExpandoTableSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ExpandoTableSoap[] toSoapModels(List<ExpandoTable> models) {
		List<ExpandoTableSoap> soapModels = new ArrayList<ExpandoTableSoap>(models.size());

		for (ExpandoTable model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ExpandoTableSoap[soapModels.size()]);
	}

	public ExpandoTableSoap() {
	}

	public long getPrimaryKey() {
		return _tableId;
	}

	public void setPrimaryKey(long pk) {
		setTableId(pk);
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		_tableId = tableId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _tableId;
	private long _companyId;
	private long _classNameId;
	private String _name;
}