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

package com.liferay.portal.model;

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
public class ReleaseSoap implements Serializable {
	public static ReleaseSoap toSoapModel(Release model) {
		ReleaseSoap soapModel = new ReleaseSoap();

		soapModel.setReleaseId(model.getReleaseId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setServletContextName(model.getServletContextName());
		soapModel.setBuildNumber(model.getBuildNumber());
		soapModel.setBuildDate(model.getBuildDate());
		soapModel.setVerified(model.getVerified());
		soapModel.setState(model.getState());
		soapModel.setTestString(model.getTestString());

		return soapModel;
	}

	public static ReleaseSoap[] toSoapModels(Release[] models) {
		ReleaseSoap[] soapModels = new ReleaseSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ReleaseSoap[][] toSoapModels(Release[][] models) {
		ReleaseSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ReleaseSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ReleaseSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ReleaseSoap[] toSoapModels(List<Release> models) {
		List<ReleaseSoap> soapModels = new ArrayList<ReleaseSoap>(models.size());

		for (Release model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ReleaseSoap[soapModels.size()]);
	}

	public ReleaseSoap() {
	}

	public long getPrimaryKey() {
		return _releaseId;
	}

	public void setPrimaryKey(long pk) {
		setReleaseId(pk);
	}

	public long getReleaseId() {
		return _releaseId;
	}

	public void setReleaseId(long releaseId) {
		_releaseId = releaseId;
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

	public String getServletContextName() {
		return _servletContextName;
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	public int getBuildNumber() {
		return _buildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		_buildNumber = buildNumber;
	}

	public Date getBuildDate() {
		return _buildDate;
	}

	public void setBuildDate(Date buildDate) {
		_buildDate = buildDate;
	}

	public boolean getVerified() {
		return _verified;
	}

	public boolean isVerified() {
		return _verified;
	}

	public void setVerified(boolean verified) {
		_verified = verified;
	}

	public int getState() {
		return _state;
	}

	public void setState(int state) {
		_state = state;
	}

	public String getTestString() {
		return _testString;
	}

	public void setTestString(String testString) {
		_testString = testString;
	}

	private long _releaseId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _servletContextName;
	private int _buildNumber;
	private Date _buildDate;
	private boolean _verified;
	private int _state;
	private String _testString;
}