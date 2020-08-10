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

package com.liferay.commerce.product.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPDefinitionLinkServiceSoap}.
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionLinkSoap implements Serializable {

	public static CPDefinitionLinkSoap toSoapModel(CPDefinitionLink model) {
		CPDefinitionLinkSoap soapModel = new CPDefinitionLinkSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDefinitionLinkId(model.getCPDefinitionLinkId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCProductId(model.getCProductId());
		soapModel.setPriority(model.getPriority());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static CPDefinitionLinkSoap[] toSoapModels(
		CPDefinitionLink[] models) {

		CPDefinitionLinkSoap[] soapModels =
			new CPDefinitionLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionLinkSoap[][] toSoapModels(
		CPDefinitionLink[][] models) {

		CPDefinitionLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CPDefinitionLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionLinkSoap[] toSoapModels(
		List<CPDefinitionLink> models) {

		List<CPDefinitionLinkSoap> soapModels =
			new ArrayList<CPDefinitionLinkSoap>(models.size());

		for (CPDefinitionLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPDefinitionLinkSoap[soapModels.size()]);
	}

	public CPDefinitionLinkSoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionLinkId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionLinkId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDefinitionLinkId() {
		return _CPDefinitionLinkId;
	}

	public void setCPDefinitionLinkId(long CPDefinitionLinkId) {
		_CPDefinitionLinkId = CPDefinitionLinkId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public long getCProductId() {
		return _CProductId;
	}

	public void setCProductId(long CProductId) {
		_CProductId = CProductId;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _uuid;
	private long _CPDefinitionLinkId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private long _CProductId;
	private double _priority;
	private String _type;

}