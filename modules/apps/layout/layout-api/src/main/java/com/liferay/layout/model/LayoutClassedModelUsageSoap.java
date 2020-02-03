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

package com.liferay.layout.model;

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
public class LayoutClassedModelUsageSoap implements Serializable {

	public static LayoutClassedModelUsageSoap toSoapModel(
		LayoutClassedModelUsage model) {

		LayoutClassedModelUsageSoap soapModel =
			new LayoutClassedModelUsageSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setLayoutClassedModelUsageId(
			model.getLayoutClassedModelUsageId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setContainerKey(model.getContainerKey());
		soapModel.setContainerType(model.getContainerType());
		soapModel.setPlid(model.getPlid());
		soapModel.setType(model.getType());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static LayoutClassedModelUsageSoap[] toSoapModels(
		LayoutClassedModelUsage[] models) {

		LayoutClassedModelUsageSoap[] soapModels =
			new LayoutClassedModelUsageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutClassedModelUsageSoap[][] toSoapModels(
		LayoutClassedModelUsage[][] models) {

		LayoutClassedModelUsageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new LayoutClassedModelUsageSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutClassedModelUsageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutClassedModelUsageSoap[] toSoapModels(
		List<LayoutClassedModelUsage> models) {

		List<LayoutClassedModelUsageSoap> soapModels =
			new ArrayList<LayoutClassedModelUsageSoap>(models.size());

		for (LayoutClassedModelUsage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new LayoutClassedModelUsageSoap[soapModels.size()]);
	}

	public LayoutClassedModelUsageSoap() {
	}

	public long getPrimaryKey() {
		return _layoutClassedModelUsageId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutClassedModelUsageId(pk);
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

	public long getLayoutClassedModelUsageId() {
		return _layoutClassedModelUsageId;
	}

	public void setLayoutClassedModelUsageId(long layoutClassedModelUsageId) {
		_layoutClassedModelUsageId = layoutClassedModelUsageId;
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

	public String getContainerKey() {
		return _containerKey;
	}

	public void setContainerKey(String containerKey) {
		_containerKey = containerKey;
	}

	public long getContainerType() {
		return _containerType;
	}

	public void setContainerType(long containerType) {
		_containerType = containerType;
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
	private long _layoutClassedModelUsageId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private String _containerKey;
	private long _containerType;
	private long _plid;
	private int _type;
	private Date _lastPublishDate;

}