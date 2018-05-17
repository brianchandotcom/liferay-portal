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

package com.liferay.commerce.notification.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.notification.service.http.CommerceNotificationTemplateUserSegmentRelServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.notification.service.http.CommerceNotificationTemplateUserSegmentRelServiceSoap
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelSoap
	implements Serializable {
	public static CommerceNotificationTemplateUserSegmentRelSoap toSoapModel(
		CommerceNotificationTemplateUserSegmentRel model) {
		CommerceNotificationTemplateUserSegmentRelSoap soapModel = new CommerceNotificationTemplateUserSegmentRelSoap();

		soapModel.setCommerceNotificationTemplateUserSegmentRelId(model.getCommerceNotificationTemplateUserSegmentRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceNotificationTemplateId(model.getCommerceNotificationTemplateId());
		soapModel.setCommerceUserSegmentEntryId(model.getCommerceUserSegmentEntryId());

		return soapModel;
	}

	public static CommerceNotificationTemplateUserSegmentRelSoap[] toSoapModels(
		CommerceNotificationTemplateUserSegmentRel[] models) {
		CommerceNotificationTemplateUserSegmentRelSoap[] soapModels = new CommerceNotificationTemplateUserSegmentRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceNotificationTemplateUserSegmentRelSoap[][] toSoapModels(
		CommerceNotificationTemplateUserSegmentRel[][] models) {
		CommerceNotificationTemplateUserSegmentRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceNotificationTemplateUserSegmentRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceNotificationTemplateUserSegmentRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceNotificationTemplateUserSegmentRelSoap[] toSoapModels(
		List<CommerceNotificationTemplateUserSegmentRel> models) {
		List<CommerceNotificationTemplateUserSegmentRelSoap> soapModels = new ArrayList<CommerceNotificationTemplateUserSegmentRelSoap>(models.size());

		for (CommerceNotificationTemplateUserSegmentRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceNotificationTemplateUserSegmentRelSoap[soapModels.size()]);
	}

	public CommerceNotificationTemplateUserSegmentRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceNotificationTemplateUserSegmentRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceNotificationTemplateUserSegmentRelId(pk);
	}

	public long getCommerceNotificationTemplateUserSegmentRelId() {
		return _commerceNotificationTemplateUserSegmentRelId;
	}

	public void setCommerceNotificationTemplateUserSegmentRelId(
		long commerceNotificationTemplateUserSegmentRelId) {
		_commerceNotificationTemplateUserSegmentRelId = commerceNotificationTemplateUserSegmentRelId;
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

	public long getCommerceNotificationTemplateId() {
		return _commerceNotificationTemplateId;
	}

	public void setCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		_commerceNotificationTemplateId = commerceNotificationTemplateId;
	}

	public long getCommerceUserSegmentEntryId() {
		return _commerceUserSegmentEntryId;
	}

	public void setCommerceUserSegmentEntryId(long commerceUserSegmentEntryId) {
		_commerceUserSegmentEntryId = commerceUserSegmentEntryId;
	}

	private long _commerceNotificationTemplateUserSegmentRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceNotificationTemplateId;
	private long _commerceUserSegmentEntryId;
}