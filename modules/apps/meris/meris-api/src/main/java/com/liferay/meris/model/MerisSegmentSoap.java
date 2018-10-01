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

package com.liferay.meris.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.meris.service.http.MerisSegmentServiceSoap}.
 *
 * @author Eduardo Garcia
 * @see com.liferay.meris.service.http.MerisSegmentServiceSoap
 * @generated
 */
@ProviderType
public class MerisSegmentSoap implements Serializable {
	public static MerisSegmentSoap toSoapModel(MerisSegment model) {
		MerisSegmentSoap soapModel = new MerisSegmentSoap();

		soapModel.setMerisSegmentId(model.getMerisSegmentId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setKey(model.getKey());
		soapModel.setActive(model.isActive());
		soapModel.setType(model.getType());
		soapModel.setCriteria(model.getCriteria());

		return soapModel;
	}

	public static MerisSegmentSoap[] toSoapModels(MerisSegment[] models) {
		MerisSegmentSoap[] soapModels = new MerisSegmentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MerisSegmentSoap[][] toSoapModels(MerisSegment[][] models) {
		MerisSegmentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MerisSegmentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MerisSegmentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MerisSegmentSoap[] toSoapModels(List<MerisSegment> models) {
		List<MerisSegmentSoap> soapModels = new ArrayList<MerisSegmentSoap>(models.size());

		for (MerisSegment model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MerisSegmentSoap[soapModels.size()]);
	}

	public MerisSegmentSoap() {
	}

	public long getPrimaryKey() {
		return _merisSegmentId;
	}

	public void setPrimaryKey(long pk) {
		setMerisSegmentId(pk);
	}

	public long getMerisSegmentId() {
		return _merisSegmentId;
	}

	public void setMerisSegmentId(long merisSegmentId) {
		_merisSegmentId = merisSegmentId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getCriteria() {
		return _criteria;
	}

	public void setCriteria(String criteria) {
		_criteria = criteria;
	}

	private long _merisSegmentId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private String _key;
	private boolean _active;
	private String _type;
	private String _criteria;
}