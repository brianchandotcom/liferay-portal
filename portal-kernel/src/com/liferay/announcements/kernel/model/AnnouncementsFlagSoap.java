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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.announcements.service.http.AnnouncementsFlagServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnnouncementsFlagSoap implements Serializable {

	public static AnnouncementsFlagSoap toSoapModel(AnnouncementsFlag model) {
		AnnouncementsFlagSoap soapModel = new AnnouncementsFlagSoap();

		soapModel.setFlagId(model.getFlagId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static AnnouncementsFlagSoap[] toSoapModels(
		AnnouncementsFlag[] models) {

		AnnouncementsFlagSoap[] soapModels =
			new AnnouncementsFlagSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AnnouncementsFlagSoap[][] toSoapModels(
		AnnouncementsFlag[][] models) {

		AnnouncementsFlagSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AnnouncementsFlagSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AnnouncementsFlagSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AnnouncementsFlagSoap[] toSoapModels(
		List<AnnouncementsFlag> models) {

		List<AnnouncementsFlagSoap> soapModels =
			new ArrayList<AnnouncementsFlagSoap>(models.size());

		for (AnnouncementsFlag model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AnnouncementsFlagSoap[soapModels.size()]);
	}

	public AnnouncementsFlagSoap() {
	}

	public long getPrimaryKey() {
		return _flagId;
	}

	public void setPrimaryKey(long pk) {
		setFlagId(pk);
	}

	public long getFlagId() {
		return _flagId;
	}

	public void setFlagId(long flagId) {
		_flagId = flagId;
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

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		_value = value;
	}

	private long _flagId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private long _entryId;
	private int _value;

}