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
 * This class is used by SOAP remote services.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsEntryRoleSoap implements Serializable {

	public static SegmentsEntryRoleSoap toSoapModel(SegmentsEntryRole model) {
		SegmentsEntryRoleSoap soapModel = new SegmentsEntryRoleSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setSegmentsEntryRoleId(model.getSegmentsEntryRoleId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setRoleId(model.getRoleId());
		soapModel.setSegmentsEntryId(model.getSegmentsEntryId());

		return soapModel;
	}

	public static SegmentsEntryRoleSoap[] toSoapModels(
		SegmentsEntryRole[] models) {

		SegmentsEntryRoleSoap[] soapModels =
			new SegmentsEntryRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SegmentsEntryRoleSoap[][] toSoapModels(
		SegmentsEntryRole[][] models) {

		SegmentsEntryRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SegmentsEntryRoleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SegmentsEntryRoleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SegmentsEntryRoleSoap[] toSoapModels(
		List<SegmentsEntryRole> models) {

		List<SegmentsEntryRoleSoap> soapModels =
			new ArrayList<SegmentsEntryRoleSoap>(models.size());

		for (SegmentsEntryRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SegmentsEntryRoleSoap[soapModels.size()]);
	}

	public SegmentsEntryRoleSoap() {
	}

	public long getPrimaryKey() {
		return _segmentsEntryRoleId;
	}

	public void setPrimaryKey(long pk) {
		setSegmentsEntryRoleId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getSegmentsEntryRoleId() {
		return _segmentsEntryRoleId;
	}

	public void setSegmentsEntryRoleId(long segmentsEntryRoleId) {
		_segmentsEntryRoleId = segmentsEntryRoleId;
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

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	public long getSegmentsEntryId() {
		return _segmentsEntryId;
	}

	public void setSegmentsEntryId(long segmentsEntryId) {
		_segmentsEntryId = segmentsEntryId;
	}

	private long _mvccVersion;
	private long _segmentsEntryRoleId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _roleId;
	private long _segmentsEntryId;

}