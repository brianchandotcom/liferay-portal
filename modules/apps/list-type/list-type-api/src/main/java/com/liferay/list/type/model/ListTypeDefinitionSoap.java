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

package com.liferay.list.type.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.list.type.service.http.ListTypeDefinitionServiceSoap}.
 *
 * @author Gabriel Albuquerque
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ListTypeDefinitionSoap implements Serializable {

	public static ListTypeDefinitionSoap toSoapModel(ListTypeDefinition model) {
		ListTypeDefinitionSoap soapModel = new ListTypeDefinitionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setListTypeDefinitionId(model.getListTypeDefinitionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static ListTypeDefinitionSoap[] toSoapModels(
		ListTypeDefinition[] models) {

		ListTypeDefinitionSoap[] soapModels =
			new ListTypeDefinitionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ListTypeDefinitionSoap[][] toSoapModels(
		ListTypeDefinition[][] models) {

		ListTypeDefinitionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ListTypeDefinitionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ListTypeDefinitionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ListTypeDefinitionSoap[] toSoapModels(
		List<ListTypeDefinition> models) {

		List<ListTypeDefinitionSoap> soapModels =
			new ArrayList<ListTypeDefinitionSoap>(models.size());

		for (ListTypeDefinition model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ListTypeDefinitionSoap[soapModels.size()]);
	}

	public ListTypeDefinitionSoap() {
	}

	public long getPrimaryKey() {
		return _listTypeDefinitionId;
	}

	public void setPrimaryKey(long pk) {
		setListTypeDefinitionId(pk);
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

	public long getListTypeDefinitionId() {
		return _listTypeDefinitionId;
	}

	public void setListTypeDefinitionId(long listTypeDefinitionId) {
		_listTypeDefinitionId = listTypeDefinitionId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _listTypeDefinitionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;

}