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
public class FaroNotificationSoap implements Serializable {

	public static FaroNotificationSoap toSoapModel(FaroNotification model) {
		FaroNotificationSoap soapModel = new FaroNotificationSoap();

		soapModel.setFaroNotificationId(model.getFaroNotificationId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateTime(model.getCreateTime());
		soapModel.setModifiedTime(model.getModifiedTime());
		soapModel.setOwnerId(model.getOwnerId());
		soapModel.setScope(model.getScope());
		soapModel.setRead(model.isRead());
		soapModel.setType(model.getType());
		soapModel.setSubtype(model.getSubtype());

		return soapModel;
	}

	public static FaroNotificationSoap[] toSoapModels(
		FaroNotification[] models) {

		FaroNotificationSoap[] soapModels =
			new FaroNotificationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FaroNotificationSoap[][] toSoapModels(
		FaroNotification[][] models) {

		FaroNotificationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FaroNotificationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FaroNotificationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FaroNotificationSoap[] toSoapModels(
		List<FaroNotification> models) {

		List<FaroNotificationSoap> soapModels =
			new ArrayList<FaroNotificationSoap>(models.size());

		for (FaroNotification model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FaroNotificationSoap[soapModels.size()]);
	}

	public FaroNotificationSoap() {
	}

	public long getPrimaryKey() {
		return _faroNotificationId;
	}

	public void setPrimaryKey(long pk) {
		setFaroNotificationId(pk);
	}

	public long getFaroNotificationId() {
		return _faroNotificationId;
	}

	public void setFaroNotificationId(long faroNotificationId) {
		_faroNotificationId = faroNotificationId;
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

	public long getOwnerId() {
		return _ownerId;
	}

	public void setOwnerId(long ownerId) {
		_ownerId = ownerId;
	}

	public String getScope() {
		return _scope;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	public boolean getRead() {
		return _read;
	}

	public boolean isRead() {
		return _read;
	}

	public void setRead(boolean read) {
		_read = read;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getSubtype() {
		return _subtype;
	}

	public void setSubtype(String subtype) {
		_subtype = subtype;
	}

	private long _faroNotificationId;
	private long _groupId;
	private long _userId;
	private long _createTime;
	private long _modifiedTime;
	private long _ownerId;
	private String _scope;
	private boolean _read;
	private String _type;
	private String _subtype;

}