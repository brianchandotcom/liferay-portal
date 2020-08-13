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
 * This class is used by SOAP remote services, specifically {@link com.liferay.segments.service.http.SegmentsExperimentRelServiceSoap}.
 *
 * @author Eduardo Garcia
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SegmentsExperimentRelSoap implements Serializable {

	public static SegmentsExperimentRelSoap toSoapModel(
		SegmentsExperimentRel model) {

		SegmentsExperimentRelSoap soapModel = new SegmentsExperimentRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setSegmentsExperimentRelId(
			model.getSegmentsExperimentRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSegmentsExperimentId(model.getSegmentsExperimentId());
		soapModel.setSegmentsExperienceId(model.getSegmentsExperienceId());
		soapModel.setSplit(model.getSplit());

		return soapModel;
	}

	public static SegmentsExperimentRelSoap[] toSoapModels(
		SegmentsExperimentRel[] models) {

		SegmentsExperimentRelSoap[] soapModels =
			new SegmentsExperimentRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SegmentsExperimentRelSoap[][] toSoapModels(
		SegmentsExperimentRel[][] models) {

		SegmentsExperimentRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SegmentsExperimentRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SegmentsExperimentRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SegmentsExperimentRelSoap[] toSoapModels(
		List<SegmentsExperimentRel> models) {

		List<SegmentsExperimentRelSoap> soapModels =
			new ArrayList<SegmentsExperimentRelSoap>(models.size());

		for (SegmentsExperimentRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SegmentsExperimentRelSoap[soapModels.size()]);
	}

	public SegmentsExperimentRelSoap() {
	}

	public long getPrimaryKey() {
		return _segmentsExperimentRelId;
	}

	public void setPrimaryKey(long pk) {
		setSegmentsExperimentRelId(pk);
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

	public long getSegmentsExperimentRelId() {
		return _segmentsExperimentRelId;
	}

	public void setSegmentsExperimentRelId(long segmentsExperimentRelId) {
		_segmentsExperimentRelId = segmentsExperimentRelId;
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

	public long getSegmentsExperimentId() {
		return _segmentsExperimentId;
	}

	public void setSegmentsExperimentId(long segmentsExperimentId) {
		_segmentsExperimentId = segmentsExperimentId;
	}

	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	public void setSegmentsExperienceId(long segmentsExperienceId) {
		_segmentsExperienceId = segmentsExperienceId;
	}

	public double getSplit() {
		return _split;
	}

	public void setSplit(double split) {
		_split = split;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _segmentsExperimentRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _segmentsExperimentId;
	private long _segmentsExperienceId;
	private double _split;

}