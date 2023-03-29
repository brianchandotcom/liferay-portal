/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroUserSoap implements Serializable {

	public static FaroUserSoap toSoapModel(FaroUser model) {
		FaroUserSoap soapModel = new FaroUserSoap();

		soapModel.setFaroUserId(model.getFaroUserId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateTime(model.getCreateTime());
		soapModel.setModifiedTime(model.getModifiedTime());
		soapModel.setLiveUserId(model.getLiveUserId());
		soapModel.setRoleId(model.getRoleId());
		soapModel.setEmailAddress(model.getEmailAddress());
		soapModel.setKey(model.getKey());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static FaroUserSoap[] toSoapModels(FaroUser[] models) {
		FaroUserSoap[] soapModels = new FaroUserSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FaroUserSoap[][] toSoapModels(FaroUser[][] models) {
		FaroUserSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FaroUserSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FaroUserSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FaroUserSoap[] toSoapModels(List<FaroUser> models) {
		List<FaroUserSoap> soapModels = new ArrayList<FaroUserSoap>(
			models.size());

		for (FaroUser model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FaroUserSoap[soapModels.size()]);
	}

	public FaroUserSoap() {
	}

	public long getPrimaryKey() {
		return _faroUserId;
	}

	public void setPrimaryKey(long pk) {
		setFaroUserId(pk);
	}

	public long getFaroUserId() {
		return _faroUserId;
	}

	public void setFaroUserId(long faroUserId) {
		_faroUserId = faroUserId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public long getCreateTime() {
		return _createTime;
	}

	public void setCreateTime(long createTime) {
		_createTime = createTime;
	}

	public long getModifiedTime() {
		return _modifiedTime;
	}

	public void setModifiedTime(long modifiedTime) {
		_modifiedTime = modifiedTime;
	}

	public long getLiveUserId() {
		return _liveUserId;
	}

	public void setLiveUserId(long liveUserId) {
		_liveUserId = liveUserId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _faroUserId;
	private long _groupId;
	private long _userId;
	private String _userName;
	private long _createTime;
	private long _modifiedTime;
	private long _liveUserId;
	private long _roleId;
	private String _emailAddress;
	private String _key;
	private int _status;

}