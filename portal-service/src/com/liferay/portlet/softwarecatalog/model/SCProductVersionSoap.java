/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.softwarecatalog.service.http.SCProductVersionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.http.SCProductVersionServiceSoap
 * @generated
 */
public class SCProductVersionSoap implements Serializable {
	public static SCProductVersionSoap toSoapModel(SCProductVersion model) {
		SCProductVersionSoap soapModel = new SCProductVersionSoap();

		soapModel.setProductVersionId(model.getProductVersionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setProductEntryId(model.getProductEntryId());
		soapModel.setVersion(model.getVersion());
		soapModel.setChangeLog(model.getChangeLog());
		soapModel.setDownloadPageURL(model.getDownloadPageURL());
		soapModel.setDirectDownloadURL(model.getDirectDownloadURL());
		soapModel.setRepoStoreArtifact(model.getRepoStoreArtifact());

		return soapModel;
	}

	public static SCProductVersionSoap[] toSoapModels(SCProductVersion[] models) {
		SCProductVersionSoap[] soapModels = new SCProductVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SCProductVersionSoap[][] toSoapModels(
		SCProductVersion[][] models) {
		SCProductVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SCProductVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SCProductVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SCProductVersionSoap[] toSoapModels(
		List<SCProductVersion> models) {
		List<SCProductVersionSoap> soapModels = new ArrayList<SCProductVersionSoap>(models.size());

		for (SCProductVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SCProductVersionSoap[soapModels.size()]);
	}

	public SCProductVersionSoap() {
	}

	public long getPrimaryKey() {
		return _productVersionId;
	}

	public void setPrimaryKey(long pk) {
		setProductVersionId(pk);
	}

	public long getProductVersionId() {
		return _productVersionId;
	}

	public void setProductVersionId(long productVersionId) {
		_productVersionId = productVersionId;
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

	public long getProductEntryId() {
		return _productEntryId;
	}

	public void setProductEntryId(long productEntryId) {
		_productEntryId = productEntryId;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public String getChangeLog() {
		return _changeLog;
	}

	public void setChangeLog(String changeLog) {
		_changeLog = changeLog;
	}

	public String getDownloadPageURL() {
		return _downloadPageURL;
	}

	public void setDownloadPageURL(String downloadPageURL) {
		_downloadPageURL = downloadPageURL;
	}

	public String getDirectDownloadURL() {
		return _directDownloadURL;
	}

	public void setDirectDownloadURL(String directDownloadURL) {
		_directDownloadURL = directDownloadURL;
	}

	public boolean getRepoStoreArtifact() {
		return _repoStoreArtifact;
	}

	public boolean isRepoStoreArtifact() {
		return _repoStoreArtifact;
	}

	public void setRepoStoreArtifact(boolean repoStoreArtifact) {
		_repoStoreArtifact = repoStoreArtifact;
	}

	private long _productVersionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _productEntryId;
	private String _version;
	private String _changeLog;
	private String _downloadPageURL;
	private String _directDownloadURL;
	private boolean _repoStoreArtifact;
}