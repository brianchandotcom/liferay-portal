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

package com.liferay.frontend.view.state.model;

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
public class FrontendViewStateEntrySoap implements Serializable {

	public static FrontendViewStateEntrySoap toSoapModel(
		FrontendViewStateEntry model) {

		FrontendViewStateEntrySoap soapModel = new FrontendViewStateEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setFrontendViewStateEntryId(
			model.getFrontendViewStateEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setViewState(model.getViewState());

		return soapModel;
	}

	public static FrontendViewStateEntrySoap[] toSoapModels(
		FrontendViewStateEntry[] models) {

		FrontendViewStateEntrySoap[] soapModels =
			new FrontendViewStateEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FrontendViewStateEntrySoap[][] toSoapModels(
		FrontendViewStateEntry[][] models) {

		FrontendViewStateEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FrontendViewStateEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new FrontendViewStateEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FrontendViewStateEntrySoap[] toSoapModels(
		List<FrontendViewStateEntry> models) {

		List<FrontendViewStateEntrySoap> soapModels =
			new ArrayList<FrontendViewStateEntrySoap>(models.size());

		for (FrontendViewStateEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new FrontendViewStateEntrySoap[soapModels.size()]);
	}

	public FrontendViewStateEntrySoap() {
	}

	public long getPrimaryKey() {
		return _frontendViewStateEntryId;
	}

	public void setPrimaryKey(long pk) {
		setFrontendViewStateEntryId(pk);
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

	public long getFrontendViewStateEntryId() {
		return _frontendViewStateEntryId;
	}

	public void setFrontendViewStateEntryId(long frontendViewStateEntryId) {
		_frontendViewStateEntryId = frontendViewStateEntryId;
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

	public String getViewState() {
		return _viewState;
	}

	public void setViewState(String viewState) {
		_viewState = viewState;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _frontendViewStateEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _viewState;

}