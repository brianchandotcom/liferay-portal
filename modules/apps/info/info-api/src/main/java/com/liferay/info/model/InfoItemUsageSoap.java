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

package com.liferay.info.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class InfoItemUsageSoap implements Serializable {

	public static InfoItemUsageSoap toSoapModel(InfoItemUsage model) {
		InfoItemUsageSoap soapModel = new InfoItemUsageSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setInfoItemUsageId(model.getInfoItemUsageId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setContainerType(model.getContainerType());
		soapModel.setContainerKey(model.getContainerKey());
		soapModel.setPlid(model.getPlid());
		soapModel.setType(model.getType());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static InfoItemUsageSoap[] toSoapModels(InfoItemUsage[] models) {
		InfoItemUsageSoap[] soapModels = new InfoItemUsageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static InfoItemUsageSoap[][] toSoapModels(InfoItemUsage[][] models) {
		InfoItemUsageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new InfoItemUsageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new InfoItemUsageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static InfoItemUsageSoap[] toSoapModels(List<InfoItemUsage> models) {
		List<InfoItemUsageSoap> soapModels = new ArrayList<InfoItemUsageSoap>(
			models.size());

		for (InfoItemUsage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new InfoItemUsageSoap[soapModels.size()]);
	}

	public InfoItemUsageSoap() {
	}

	public long getPrimaryKey() {
		return _infoItemUsageId;
	}

	public void setPrimaryKey(long pk) {
		setInfoItemUsageId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getInfoItemUsageId() {
		return _infoItemUsageId;
	}

	public void setInfoItemUsageId(long infoItemUsageId) {
		_infoItemUsageId = infoItemUsageId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public long getContainerType() {
		return _containerType;
	}

	public void setContainerType(long containerType) {
		_containerType = containerType;
	}

	public String getContainerKey() {
		return _containerKey;
	}

	public void setContainerKey(String containerKey) {
		_containerKey = containerKey;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _infoItemUsageId;
	private long _groupId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _containerType;
	private String _containerKey;
	private long _plid;
	private int _type;
	private Date _lastPublishDate;

}