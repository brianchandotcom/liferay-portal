/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class IGImageSoap implements Serializable {
	public static IGImageSoap toSoapModel(IGImage model) {
		IGImageSoap soapModel = new IGImageSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setImageId(model.getImageId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFolderId(model.getFolderId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setSmallImageId(model.getSmallImageId());
		soapModel.setLargeImageId(model.getLargeImageId());
		soapModel.setCustom1ImageId(model.getCustom1ImageId());
		soapModel.setCustom2ImageId(model.getCustom2ImageId());

		return soapModel;
	}

	public static IGImageSoap[] toSoapModels(IGImage[] models) {
		IGImageSoap[] soapModels = new IGImageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static IGImageSoap[][] toSoapModels(IGImage[][] models) {
		IGImageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new IGImageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new IGImageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static IGImageSoap[] toSoapModels(List<IGImage> models) {
		List<IGImageSoap> soapModels = new ArrayList<IGImageSoap>(models.size());

		for (IGImage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new IGImageSoap[soapModels.size()]);
	}

	public IGImageSoap() {
	}

	public long getPrimaryKey() {
		return _imageId;
	}

	public void setPrimaryKey(long pk) {
		setImageId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getImageId() {
		return _imageId;
	}

	public void setImageId(long imageId) {
		_imageId = imageId;
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

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public long getSmallImageId() {
		return _smallImageId;
	}

	public void setSmallImageId(long smallImageId) {
		_smallImageId = smallImageId;
	}

	public long getLargeImageId() {
		return _largeImageId;
	}

	public void setLargeImageId(long largeImageId) {
		_largeImageId = largeImageId;
	}

	public long getCustom1ImageId() {
		return _custom1ImageId;
	}

	public void setCustom1ImageId(long custom1ImageId) {
		_custom1ImageId = custom1ImageId;
	}

	public long getCustom2ImageId() {
		return _custom2ImageId;
	}

	public void setCustom2ImageId(long custom2ImageId) {
		_custom2ImageId = custom2ImageId;
	}

	private String _uuid;
	private long _imageId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private String _name;
	private String _description;
	private long _smallImageId;
	private long _largeImageId;
	private long _custom1ImageId;
	private long _custom2ImageId;
}