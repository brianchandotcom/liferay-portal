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

package com.liferay.change.tracking.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.change.tracking.service.http.CTProcessServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CTProcessSoap implements Serializable {

	public static CTProcessSoap toSoapModel(CTProcess model) {
		CTProcessSoap soapModel = new CTProcessSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtProcessId(model.getCtProcessId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setBackgroundTaskId(model.getBackgroundTaskId());

		return soapModel;
	}

	public static CTProcessSoap[] toSoapModels(CTProcess[] models) {
		CTProcessSoap[] soapModels = new CTProcessSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTProcessSoap[][] toSoapModels(CTProcess[][] models) {
		CTProcessSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CTProcessSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTProcessSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTProcessSoap[] toSoapModels(List<CTProcess> models) {
		List<CTProcessSoap> soapModels = new ArrayList<CTProcessSoap>(
			models.size());

		for (CTProcess model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CTProcessSoap[soapModels.size()]);
	}

	public CTProcessSoap() {
	}

	public long getPrimaryKey() {
		return _ctProcessId;
	}

	public void setPrimaryKey(long pk) {
		setCtProcessId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtProcessId() {
		return _ctProcessId;
	}

	public void setCtProcessId(long ctProcessId) {
		_ctProcessId = ctProcessId;
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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getBackgroundTaskId() {
		return _backgroundTaskId;
	}

	public void setBackgroundTaskId(long backgroundTaskId) {
		_backgroundTaskId = backgroundTaskId;
	}

	private long _mvccVersion;
	private long _ctProcessId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private long _ctCollectionId;
	private long _backgroundTaskId;

}