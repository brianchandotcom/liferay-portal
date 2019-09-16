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

package com.liferay.segments.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.segments.service.http.SegmentsEntryServiceSoap}.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsEntrySoap implements Serializable {

	public static SegmentsEntrySoap toSoapModel(SegmentsEntry model) {
		SegmentsEntrySoap soapModel = new SegmentsEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setSegmentsEntryId(model.getSegmentsEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSegmentsEntryKey(model.getSegmentsEntryKey());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setActive(model.isActive());
		soapModel.setCriteria(model.getCriteria());
		soapModel.setSource(model.getSource());
		soapModel.setType(model.getType());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static SegmentsEntrySoap[] toSoapModels(SegmentsEntry[] models) {
		SegmentsEntrySoap[] soapModels = new SegmentsEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SegmentsEntrySoap[][] toSoapModels(SegmentsEntry[][] models) {
		SegmentsEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SegmentsEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SegmentsEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SegmentsEntrySoap[] toSoapModels(List<SegmentsEntry> models) {
		List<SegmentsEntrySoap> soapModels = new ArrayList<SegmentsEntrySoap>(
			models.size());

		for (SegmentsEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SegmentsEntrySoap[soapModels.size()]);
	}

	public SegmentsEntrySoap() {
	}

	public long getPrimaryKey() {
		return _segmentsEntryId;
	}

	public void setPrimaryKey(long pk) {
		setSegmentsEntryId(pk);
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

	public long getSegmentsEntryId() {
		return _segmentsEntryId;
	}

	public void setSegmentsEntryId(long segmentsEntryId) {
		_segmentsEntryId = segmentsEntryId;
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

	public String getSegmentsEntryKey() {
		return _segmentsEntryKey;
	}

	public void setSegmentsEntryKey(String segmentsEntryKey) {
		_segmentsEntryKey = segmentsEntryKey;
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

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public String getCriteria() {
		return _criteria;
	}

	public void setCriteria(String criteria) {
		_criteria = criteria;
	}

	public String getSource() {
		return _source;
	}

	public void setSource(String source) {
		_source = source;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
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
	private long _segmentsEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _segmentsEntryKey;
	private String _name;
	private String _description;
	private boolean _active;
	private String _criteria;
	private String _source;
	private String _type;
	private Date _lastPublishDate;

}