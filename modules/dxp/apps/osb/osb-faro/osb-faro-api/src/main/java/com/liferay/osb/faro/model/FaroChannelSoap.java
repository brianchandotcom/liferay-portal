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
public class FaroChannelSoap implements Serializable {

	public static FaroChannelSoap toSoapModel(FaroChannel model) {
		FaroChannelSoap soapModel = new FaroChannelSoap();

		soapModel.setFaroChannelId(model.getFaroChannelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateTime(model.getCreateTime());
		soapModel.setModifiedTime(model.getModifiedTime());
		soapModel.setChannelId(model.getChannelId());
		soapModel.setName(model.getName());
		soapModel.setPermissionType(model.getPermissionType());
		soapModel.setWorkspaceGroupId(model.getWorkspaceGroupId());

		return soapModel;
	}

	public static FaroChannelSoap[] toSoapModels(FaroChannel[] models) {
		FaroChannelSoap[] soapModels = new FaroChannelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FaroChannelSoap[][] toSoapModels(FaroChannel[][] models) {
		FaroChannelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FaroChannelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FaroChannelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FaroChannelSoap[] toSoapModels(List<FaroChannel> models) {
		List<FaroChannelSoap> soapModels = new ArrayList<FaroChannelSoap>(
			models.size());

		for (FaroChannel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FaroChannelSoap[soapModels.size()]);
	}

	public FaroChannelSoap() {
	}

	public long getPrimaryKey() {
		return _faroChannelId;
	}

	public void setPrimaryKey(long pk) {
		setFaroChannelId(pk);
	}

	public long getFaroChannelId() {
		return _faroChannelId;
	}

	public void setFaroChannelId(long faroChannelId) {
		_faroChannelId = faroChannelId;
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

	public String getChannelId() {
		return _channelId;
	}

	public void setChannelId(String channelId) {
		_channelId = channelId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getPermissionType() {
		return _permissionType;
	}

	public void setPermissionType(int permissionType) {
		_permissionType = permissionType;
	}

	public long getWorkspaceGroupId() {
		return _workspaceGroupId;
	}

	public void setWorkspaceGroupId(long workspaceGroupId) {
		_workspaceGroupId = workspaceGroupId;
	}

	private long _faroChannelId;
	private long _groupId;
	private long _userId;
	private String _userName;
	private long _createTime;
	private long _modifiedTime;
	private String _channelId;
	private String _name;
	private int _permissionType;
	private long _workspaceGroupId;

}