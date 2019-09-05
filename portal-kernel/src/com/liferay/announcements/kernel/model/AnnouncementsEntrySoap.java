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

package com.liferay.announcements.kernel.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.announcements.service.http.AnnouncementsEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnnouncementsEntrySoap implements Serializable {

	public static AnnouncementsEntrySoap toSoapModel(AnnouncementsEntry model) {
		AnnouncementsEntrySoap soapModel = new AnnouncementsEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setTitle(model.getTitle());
		soapModel.setContent(model.getContent());
		soapModel.setUrl(model.getUrl());
		soapModel.setType(model.getType());
		soapModel.setDisplayDate(model.getDisplayDate());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setPriority(model.getPriority());
		soapModel.setAlert(model.isAlert());

		return soapModel;
	}

	public static AnnouncementsEntrySoap[] toSoapModels(
		AnnouncementsEntry[] models) {

		AnnouncementsEntrySoap[] soapModels =
			new AnnouncementsEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AnnouncementsEntrySoap[][] toSoapModels(
		AnnouncementsEntry[][] models) {

		AnnouncementsEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AnnouncementsEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AnnouncementsEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AnnouncementsEntrySoap[] toSoapModels(
		List<AnnouncementsEntry> models) {

		List<AnnouncementsEntrySoap> soapModels =
			new ArrayList<AnnouncementsEntrySoap>(models.size());

		for (AnnouncementsEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AnnouncementsEntrySoap[soapModels.size()]);
	}

	public AnnouncementsEntrySoap() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
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

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public boolean getAlert() {
		return _alert;
	}

	public boolean isAlert() {
		return _alert;
	}

	public void setAlert(boolean alert) {
		_alert = alert;
	}

	private String _uuid;
	private long _entryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private String _title;
	private String _content;
	private String _url;
	private String _type;
	private Date _displayDate;
	private Date _expirationDate;
	private int _priority;
	private boolean _alert;

}