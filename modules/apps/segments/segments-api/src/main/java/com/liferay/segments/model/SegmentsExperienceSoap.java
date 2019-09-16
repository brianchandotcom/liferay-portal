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
 * This class is used by SOAP remote services, specifically {@link com.liferay.segments.service.http.SegmentsExperienceServiceSoap}.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsExperienceSoap implements Serializable {

	public static SegmentsExperienceSoap toSoapModel(SegmentsExperience model) {
		SegmentsExperienceSoap soapModel = new SegmentsExperienceSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setSegmentsExperienceId(model.getSegmentsExperienceId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSegmentsEntryId(model.getSegmentsEntryId());
		soapModel.setSegmentsExperienceKey(model.getSegmentsExperienceKey());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setName(model.getName());
		soapModel.setPriority(model.getPriority());
		soapModel.setActive(model.isActive());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static SegmentsExperienceSoap[] toSoapModels(
		SegmentsExperience[] models) {

		SegmentsExperienceSoap[] soapModels =
			new SegmentsExperienceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SegmentsExperienceSoap[][] toSoapModels(
		SegmentsExperience[][] models) {

		SegmentsExperienceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SegmentsExperienceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SegmentsExperienceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SegmentsExperienceSoap[] toSoapModels(
		List<SegmentsExperience> models) {

		List<SegmentsExperienceSoap> soapModels =
			new ArrayList<SegmentsExperienceSoap>(models.size());

		for (SegmentsExperience model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SegmentsExperienceSoap[soapModels.size()]);
	}

	public SegmentsExperienceSoap() {
	}

	public long getPrimaryKey() {
		return _segmentsExperienceId;
	}

	public void setPrimaryKey(long pk) {
		setSegmentsExperienceId(pk);
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

	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	public void setSegmentsExperienceId(long segmentsExperienceId) {
		_segmentsExperienceId = segmentsExperienceId;
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

	public long getSegmentsEntryId() {
		return _segmentsEntryId;
	}

	public void setSegmentsEntryId(long segmentsEntryId) {
		_segmentsEntryId = segmentsEntryId;
	}

	public String getSegmentsExperienceKey() {
		return _segmentsExperienceKey;
	}

	public void setSegmentsExperienceKey(String segmentsExperienceKey) {
		_segmentsExperienceKey = segmentsExperienceKey;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
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

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _segmentsExperienceId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _segmentsEntryId;
	private String _segmentsExperienceKey;
	private long _classNameId;
	private long _classPK;
	private String _name;
	private int _priority;
	private boolean _active;
	private Date _lastPublishDate;

}