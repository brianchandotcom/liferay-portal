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

package com.liferay.style.book.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.style.book.service.http.StyleBookEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class StyleBookEntrySoap implements Serializable {

	public static StyleBookEntrySoap toSoapModel(StyleBookEntry model) {
		StyleBookEntrySoap soapModel = new StyleBookEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setHeadId(model.getHeadId());
		soapModel.setStyleBookEntryId(model.getStyleBookEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setDefaultStyleBookEntry(model.isDefaultStyleBookEntry());
		soapModel.setFrontendTokensValues(model.getFrontendTokensValues());
		soapModel.setName(model.getName());
		soapModel.setPreviewFileEntryId(model.getPreviewFileEntryId());
		soapModel.setStyleBookEntryKey(model.getStyleBookEntryKey());

		return soapModel;
	}

	public static StyleBookEntrySoap[] toSoapModels(StyleBookEntry[] models) {
		StyleBookEntrySoap[] soapModels = new StyleBookEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static StyleBookEntrySoap[][] toSoapModels(
		StyleBookEntry[][] models) {

		StyleBookEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new StyleBookEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new StyleBookEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static StyleBookEntrySoap[] toSoapModels(
		List<StyleBookEntry> models) {

		List<StyleBookEntrySoap> soapModels = new ArrayList<StyleBookEntrySoap>(
			models.size());

		for (StyleBookEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new StyleBookEntrySoap[soapModels.size()]);
	}

	public StyleBookEntrySoap() {
	}

	public long getPrimaryKey() {
		return _styleBookEntryId;
	}

	public void setPrimaryKey(long pk) {
		setStyleBookEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getHeadId() {
		return _headId;
	}

	public void setHeadId(long headId) {
		_headId = headId;
	}

	public long getStyleBookEntryId() {
		return _styleBookEntryId;
	}

	public void setStyleBookEntryId(long styleBookEntryId) {
		_styleBookEntryId = styleBookEntryId;
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

	public boolean getDefaultStyleBookEntry() {
		return _defaultStyleBookEntry;
	}

	public boolean isDefaultStyleBookEntry() {
		return _defaultStyleBookEntry;
	}

	public void setDefaultStyleBookEntry(boolean defaultStyleBookEntry) {
		_defaultStyleBookEntry = defaultStyleBookEntry;
	}

	public String getFrontendTokensValues() {
		return _frontendTokensValues;
	}

	public void setFrontendTokensValues(String frontendTokensValues) {
		_frontendTokensValues = frontendTokensValues;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public long getPreviewFileEntryId() {
		return _previewFileEntryId;
	}

	public void setPreviewFileEntryId(long previewFileEntryId) {
		_previewFileEntryId = previewFileEntryId;
	}

	public String getStyleBookEntryKey() {
		return _styleBookEntryKey;
	}

	public void setStyleBookEntryKey(String styleBookEntryKey) {
		_styleBookEntryKey = styleBookEntryKey;
	}

	private long _mvccVersion;
	private long _headId;
	private long _styleBookEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private boolean _defaultStyleBookEntry;
	private String _frontendTokensValues;
	private String _name;
	private long _previewFileEntryId;
	private String _styleBookEntryKey;

}