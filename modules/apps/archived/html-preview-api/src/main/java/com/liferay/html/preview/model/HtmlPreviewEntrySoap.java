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

package com.liferay.html.preview.model;

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
public class HtmlPreviewEntrySoap implements Serializable {

	public static HtmlPreviewEntrySoap toSoapModel(HtmlPreviewEntry model) {
		HtmlPreviewEntrySoap soapModel = new HtmlPreviewEntrySoap();

		soapModel.setHtmlPreviewEntryId(model.getHtmlPreviewEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setFileEntryId(model.getFileEntryId());

		return soapModel;
	}

	public static HtmlPreviewEntrySoap[] toSoapModels(
		HtmlPreviewEntry[] models) {

		HtmlPreviewEntrySoap[] soapModels =
			new HtmlPreviewEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static HtmlPreviewEntrySoap[][] toSoapModels(
		HtmlPreviewEntry[][] models) {

		HtmlPreviewEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new HtmlPreviewEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new HtmlPreviewEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static HtmlPreviewEntrySoap[] toSoapModels(
		List<HtmlPreviewEntry> models) {

		List<HtmlPreviewEntrySoap> soapModels =
			new ArrayList<HtmlPreviewEntrySoap>(models.size());

		for (HtmlPreviewEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new HtmlPreviewEntrySoap[soapModels.size()]);
	}

	public HtmlPreviewEntrySoap() {
	}

	public long getPrimaryKey() {
		return _htmlPreviewEntryId;
	}

	public void setPrimaryKey(long pk) {
		setHtmlPreviewEntryId(pk);
	}

	public long getHtmlPreviewEntryId() {
		return _htmlPreviewEntryId;
	}

	public void setHtmlPreviewEntryId(long htmlPreviewEntryId) {
		_htmlPreviewEntryId = htmlPreviewEntryId;
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

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	private long _htmlPreviewEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _fileEntryId;

}