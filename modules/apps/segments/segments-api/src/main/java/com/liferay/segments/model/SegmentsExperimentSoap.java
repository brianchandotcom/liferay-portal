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
 * This class is used by SOAP remote services, specifically {@link com.liferay.segments.service.http.SegmentsExperimentServiceSoap}.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsExperimentSoap implements Serializable {

	public static SegmentsExperimentSoap toSoapModel(SegmentsExperiment model) {
		SegmentsExperimentSoap soapModel = new SegmentsExperimentSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setSegmentsExperimentId(model.getSegmentsExperimentId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSegmentsEntryId(model.getSegmentsEntryId());
		soapModel.setSegmentsExperienceId(model.getSegmentsExperienceId());
		soapModel.setSegmentsExperimentKey(model.getSegmentsExperimentKey());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static SegmentsExperimentSoap[] toSoapModels(
		SegmentsExperiment[] models) {

		SegmentsExperimentSoap[] soapModels =
			new SegmentsExperimentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SegmentsExperimentSoap[][] toSoapModels(
		SegmentsExperiment[][] models) {

		SegmentsExperimentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SegmentsExperimentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SegmentsExperimentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SegmentsExperimentSoap[] toSoapModels(
		List<SegmentsExperiment> models) {

		List<SegmentsExperimentSoap> soapModels =
			new ArrayList<SegmentsExperimentSoap>(models.size());

		for (SegmentsExperiment model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SegmentsExperimentSoap[soapModels.size()]);
	}

	public SegmentsExperimentSoap() {
	}

	public long getPrimaryKey() {
		return _segmentsExperimentId;
	}

	public void setPrimaryKey(long pk) {
		setSegmentsExperimentId(pk);
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

	public long getSegmentsExperimentId() {
		return _segmentsExperimentId;
	}

	public void setSegmentsExperimentId(long segmentsExperimentId) {
		_segmentsExperimentId = segmentsExperimentId;
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

	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	public void setSegmentsExperienceId(long segmentsExperienceId) {
		_segmentsExperienceId = segmentsExperienceId;
	}

	public String getSegmentsExperimentKey() {
		return _segmentsExperimentKey;
	}

	public void setSegmentsExperimentKey(String segmentsExperimentKey) {
		_segmentsExperimentKey = segmentsExperimentKey;
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

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _segmentsExperimentId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _segmentsEntryId;
	private long _segmentsExperienceId;
	private String _segmentsExperimentKey;
	private long _classNameId;
	private long _classPK;
	private String _name;
	private String _description;
	private String _typeSettings;
	private int _status;

}