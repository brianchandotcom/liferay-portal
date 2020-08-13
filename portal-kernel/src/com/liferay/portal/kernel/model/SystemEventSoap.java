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

package com.liferay.portal.kernel.model;

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
public class SystemEventSoap implements Serializable {

	public static SystemEventSoap toSoapModel(SystemEvent model) {
		SystemEventSoap soapModel = new SystemEventSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setSystemEventId(model.getSystemEventId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setClassUuid(model.getClassUuid());
		soapModel.setReferrerClassNameId(model.getReferrerClassNameId());
		soapModel.setParentSystemEventId(model.getParentSystemEventId());
		soapModel.setSystemEventSetKey(model.getSystemEventSetKey());
		soapModel.setType(model.getType());
		soapModel.setExtraData(model.getExtraData());

		return soapModel;
	}

	public static SystemEventSoap[] toSoapModels(SystemEvent[] models) {
		SystemEventSoap[] soapModels = new SystemEventSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SystemEventSoap[][] toSoapModels(SystemEvent[][] models) {
		SystemEventSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SystemEventSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SystemEventSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SystemEventSoap[] toSoapModels(List<SystemEvent> models) {
		List<SystemEventSoap> soapModels = new ArrayList<SystemEventSoap>(
			models.size());

		for (SystemEvent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SystemEventSoap[soapModels.size()]);
	}

	public SystemEventSoap() {
	}

	public long getPrimaryKey() {
		return _systemEventId;
	}

	public void setPrimaryKey(long pk) {
		setSystemEventId(pk);
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

	public long getSystemEventId() {
		return _systemEventId;
	}

	public void setSystemEventId(long systemEventId) {
		_systemEventId = systemEventId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getClassUuid() {
		return _classUuid;
	}

	public void setClassUuid(String classUuid) {
		_classUuid = classUuid;
	}

	public long getReferrerClassNameId() {
		return _referrerClassNameId;
	}

	public void setReferrerClassNameId(long referrerClassNameId) {
		_referrerClassNameId = referrerClassNameId;
	}

	public long getParentSystemEventId() {
		return _parentSystemEventId;
	}

	public void setParentSystemEventId(long parentSystemEventId) {
		_parentSystemEventId = parentSystemEventId;
	}

	public long getSystemEventSetKey() {
		return _systemEventSetKey;
	}

	public void setSystemEventSetKey(long systemEventSetKey) {
		_systemEventSetKey = systemEventSetKey;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getExtraData() {
		return _extraData;
	}

	public void setExtraData(String extraData) {
		_extraData = extraData;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _systemEventId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _classNameId;
	private long _classPK;
	private String _classUuid;
	private long _referrerClassNameId;
	private long _parentSystemEventId;
	private long _systemEventSetKey;
	private int _type;
	private String _extraData;

}