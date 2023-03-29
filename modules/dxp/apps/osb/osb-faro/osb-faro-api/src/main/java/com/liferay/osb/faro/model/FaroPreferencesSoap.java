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
public class FaroPreferencesSoap implements Serializable {

	public static FaroPreferencesSoap toSoapModel(FaroPreferences model) {
		FaroPreferencesSoap soapModel = new FaroPreferencesSoap();

		soapModel.setFaroPreferencesId(model.getFaroPreferencesId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateTime(model.getCreateTime());
		soapModel.setModifiedTime(model.getModifiedTime());
		soapModel.setOwnerId(model.getOwnerId());
		soapModel.setPreferences(model.getPreferences());

		return soapModel;
	}

	public static FaroPreferencesSoap[] toSoapModels(FaroPreferences[] models) {
		FaroPreferencesSoap[] soapModels =
			new FaroPreferencesSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FaroPreferencesSoap[][] toSoapModels(
		FaroPreferences[][] models) {

		FaroPreferencesSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FaroPreferencesSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FaroPreferencesSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FaroPreferencesSoap[] toSoapModels(
		List<FaroPreferences> models) {

		List<FaroPreferencesSoap> soapModels =
			new ArrayList<FaroPreferencesSoap>(models.size());

		for (FaroPreferences model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FaroPreferencesSoap[soapModels.size()]);
	}

	public FaroPreferencesSoap() {
	}

	public long getPrimaryKey() {
		return _faroPreferencesId;
	}

	public void setPrimaryKey(long pk) {
		setFaroPreferencesId(pk);
	}

	public long getFaroPreferencesId() {
		return _faroPreferencesId;
	}

	public void setFaroPreferencesId(long faroPreferencesId) {
		_faroPreferencesId = faroPreferencesId;
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

	public long getOwnerId() {
		return _ownerId;
	}

	public void setOwnerId(long ownerId) {
		_ownerId = ownerId;
	}

	public String getPreferences() {
		return _preferences;
	}

	public void setPreferences(String preferences) {
		_preferences = preferences;
	}

	private long _faroPreferencesId;
	private long _groupId;
	private long _userId;
	private String _userName;
	private long _createTime;
	private long _modifiedTime;
	private long _ownerId;
	private String _preferences;

}