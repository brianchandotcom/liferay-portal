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
 * This class is used by SOAP remote services, specifically {@link com.liferay.powwow.service.http.PowwowParticipantServiceSoap}.
 *
 * @author Shinn Lok
 * @see com.liferay.powwow.service.http.PowwowParticipantServiceSoap
 * @generated
 */
@ProviderType
public class PowwowParticipantSoap implements Serializable {
	public static PowwowParticipantSoap toSoapModel(PowwowParticipant model) {
		PowwowParticipantSoap soapModel = new PowwowParticipantSoap();

		soapModel.setPowwowParticipantId(model.getPowwowParticipantId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setPowwowMeetingId(model.getPowwowMeetingId());
		soapModel.setName(model.getName());
		soapModel.setParticipantUserId(model.getParticipantUserId());
		soapModel.setEmailAddress(model.getEmailAddress());
		soapModel.setType(model.getType());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static PowwowParticipantSoap[] toSoapModels(
		PowwowParticipant[] models) {
		PowwowParticipantSoap[] soapModels = new PowwowParticipantSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PowwowParticipantSoap[][] toSoapModels(
		PowwowParticipant[][] models) {
		PowwowParticipantSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PowwowParticipantSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PowwowParticipantSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PowwowParticipantSoap[] toSoapModels(
		List<PowwowParticipant> models) {
		List<PowwowParticipantSoap> soapModels = new ArrayList<PowwowParticipantSoap>(models.size());

		for (PowwowParticipant model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PowwowParticipantSoap[soapModels.size()]);
	}

	public PowwowParticipantSoap() {
	}

	public long getPrimaryKey() {
		return _powwowParticipantId;
	}

	public void setPrimaryKey(long pk) {
		setPowwowParticipantId(pk);
	}

	public long getPowwowParticipantId() {
		return _powwowParticipantId;
	}

	public void setPowwowParticipantId(long powwowParticipantId) {
		_powwowParticipantId = powwowParticipantId;
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

	public long getPowwowMeetingId() {
		return _powwowMeetingId;
	}

	public void setPowwowMeetingId(long powwowMeetingId) {
		_powwowMeetingId = powwowMeetingId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public long getParticipantUserId() {
		return _participantUserId;
	}

	public void setParticipantUserId(long participantUserId) {
		_participantUserId = participantUserId;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _powwowParticipantId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _powwowMeetingId;
	private String _name;
	private long _participantUserId;
	private String _emailAddress;
	private int _type;
	private int _status;
}