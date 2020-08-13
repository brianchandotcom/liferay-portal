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

package com.liferay.message.boards.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class MBStatsUserSoap implements Serializable {

	public static MBStatsUserSoap toSoapModel(MBStatsUser model) {
		MBStatsUserSoap soapModel = new MBStatsUserSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setStatsUserId(model.getStatsUserId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setMessageCount(model.getMessageCount());
		soapModel.setLastPostDate(model.getLastPostDate());

		return soapModel;
	}

	public static MBStatsUserSoap[] toSoapModels(MBStatsUser[] models) {
		MBStatsUserSoap[] soapModels = new MBStatsUserSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MBStatsUserSoap[][] toSoapModels(MBStatsUser[][] models) {
		MBStatsUserSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MBStatsUserSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MBStatsUserSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MBStatsUserSoap[] toSoapModels(List<MBStatsUser> models) {
		List<MBStatsUserSoap> soapModels = new ArrayList<MBStatsUserSoap>(
			models.size());

		for (MBStatsUser model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MBStatsUserSoap[soapModels.size()]);
	}

	public MBStatsUserSoap() {
	}

	public long getPrimaryKey() {
		return _statsUserId;
	}

	public void setPrimaryKey(long pk) {
		setStatsUserId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getStatsUserId() {
		return _statsUserId;
	}

	public void setStatsUserId(long statsUserId) {
		_statsUserId = statsUserId;
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

	public int getMessageCount() {
		return _messageCount;
	}

	public void setMessageCount(int messageCount) {
		_messageCount = messageCount;
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _statsUserId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private int _messageCount;
	private Date _lastPostDate;

}