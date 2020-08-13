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

package com.liferay.redirect.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.redirect.service.http.RedirectEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class RedirectEntrySoap implements Serializable {

	public static RedirectEntrySoap toSoapModel(RedirectEntry model) {
		RedirectEntrySoap soapModel = new RedirectEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setRedirectEntryId(model.getRedirectEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDestinationURL(model.getDestinationURL());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setLastOccurrenceDate(model.getLastOccurrenceDate());
		soapModel.setPermanent(model.isPermanent());
		soapModel.setSourceURL(model.getSourceURL());

		return soapModel;
	}

	public static RedirectEntrySoap[] toSoapModels(RedirectEntry[] models) {
		RedirectEntrySoap[] soapModels = new RedirectEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RedirectEntrySoap[][] toSoapModels(RedirectEntry[][] models) {
		RedirectEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RedirectEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new RedirectEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RedirectEntrySoap[] toSoapModels(List<RedirectEntry> models) {
		List<RedirectEntrySoap> soapModels = new ArrayList<RedirectEntrySoap>(
			models.size());

		for (RedirectEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RedirectEntrySoap[soapModels.size()]);
	}

	public RedirectEntrySoap() {
	}

	public long getPrimaryKey() {
		return _redirectEntryId;
	}

	public void setPrimaryKey(long pk) {
		setRedirectEntryId(pk);
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

	public long getRedirectEntryId() {
		return _redirectEntryId;
	}

	public void setRedirectEntryId(long redirectEntryId) {
		_redirectEntryId = redirectEntryId;
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

	public String getDestinationURL() {
		return _destinationURL;
	}

	public void setDestinationURL(String destinationURL) {
		_destinationURL = destinationURL;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public Date getLastOccurrenceDate() {
		return _lastOccurrenceDate;
	}

	public void setLastOccurrenceDate(Date lastOccurrenceDate) {
		_lastOccurrenceDate = lastOccurrenceDate;
	}

	public boolean getPermanent() {
		return _permanent;
	}

	public boolean isPermanent() {
		return _permanent;
	}

	public void setPermanent(boolean permanent) {
		_permanent = permanent;
	}

	public String getSourceURL() {
		return _sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		_sourceURL = sourceURL;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _redirectEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _destinationURL;
	private Date _expirationDate;
	private Date _lastOccurrenceDate;
	private boolean _permanent;
	private String _sourceURL;

}