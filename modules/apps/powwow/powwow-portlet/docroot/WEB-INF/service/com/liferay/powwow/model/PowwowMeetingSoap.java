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

package com.liferay.powwow.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.powwow.service.http.PowwowMeetingServiceSoap}.
 *
 * @author Shinn Lok
 * @see com.liferay.powwow.service.http.PowwowMeetingServiceSoap
 * @generated
 */
@ProviderType
public class PowwowMeetingSoap implements Serializable {
	public static PowwowMeetingSoap toSoapModel(PowwowMeeting model) {
		PowwowMeetingSoap soapModel = new PowwowMeetingSoap();

		soapModel.setPowwowMeetingId(model.getPowwowMeetingId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setPowwowServerId(model.getPowwowServerId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setProviderType(model.getProviderType());
		soapModel.setProviderTypeMetadata(model.getProviderTypeMetadata());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setCalendarBookingId(model.getCalendarBookingId());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static PowwowMeetingSoap[] toSoapModels(PowwowMeeting[] models) {
		PowwowMeetingSoap[] soapModels = new PowwowMeetingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PowwowMeetingSoap[][] toSoapModels(PowwowMeeting[][] models) {
		PowwowMeetingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PowwowMeetingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PowwowMeetingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PowwowMeetingSoap[] toSoapModels(List<PowwowMeeting> models) {
		List<PowwowMeetingSoap> soapModels = new ArrayList<PowwowMeetingSoap>(models.size());

		for (PowwowMeeting model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PowwowMeetingSoap[soapModels.size()]);
	}

	public PowwowMeetingSoap() {
	}

	public long getPrimaryKey() {
		return _powwowMeetingId;
	}

	public void setPrimaryKey(long pk) {
		setPowwowMeetingId(pk);
	}

	public long getPowwowMeetingId() {
		return _powwowMeetingId;
	}

	public void setPowwowMeetingId(long powwowMeetingId) {
		_powwowMeetingId = powwowMeetingId;
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

	public long getPowwowServerId() {
		return _powwowServerId;
	}

	public void setPowwowServerId(long powwowServerId) {
		_powwowServerId = powwowServerId;
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

	public String getProviderType() {
		return _providerType;
	}

	public void setProviderType(String providerType) {
		_providerType = providerType;
	}

	public String getProviderTypeMetadata() {
		return _providerTypeMetadata;
	}

	public void setProviderTypeMetadata(String providerTypeMetadata) {
		_providerTypeMetadata = providerTypeMetadata;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public long getCalendarBookingId() {
		return _calendarBookingId;
	}

	public void setCalendarBookingId(long calendarBookingId) {
		_calendarBookingId = calendarBookingId;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _powwowMeetingId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _powwowServerId;
	private String _name;
	private String _description;
	private String _providerType;
	private String _providerTypeMetadata;
	private String _languageId;
	private long _calendarBookingId;
	private int _status;
}