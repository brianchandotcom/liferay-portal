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

package com.liferay.portal.kernel.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.ListTypeServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ListTypeSoap implements Serializable {

	public static ListTypeSoap toSoapModel(ListType model) {
		ListTypeSoap soapModel = new ListTypeSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setListTypeId(model.getListTypeId());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static ListTypeSoap[] toSoapModels(ListType[] models) {
		ListTypeSoap[] soapModels = new ListTypeSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ListTypeSoap[][] toSoapModels(ListType[][] models) {
		ListTypeSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ListTypeSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ListTypeSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ListTypeSoap[] toSoapModels(List<ListType> models) {
		List<ListTypeSoap> soapModels = new ArrayList<ListTypeSoap>(
			models.size());

		for (ListType model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ListTypeSoap[soapModels.size()]);
	}

	public ListTypeSoap() {
	}

	public long getPrimaryKey() {
		return _listTypeId;
	}

	public void setPrimaryKey(long pk) {
		setListTypeId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getListTypeId() {
		return _listTypeId;
	}

	public void setListTypeId(long listTypeId) {
		_listTypeId = listTypeId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	private long _mvccVersion;
	private long _listTypeId;
	private String _name;
	private String _type;

}