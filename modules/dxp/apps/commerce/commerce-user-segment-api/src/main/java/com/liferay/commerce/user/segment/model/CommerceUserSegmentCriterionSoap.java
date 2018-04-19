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

package com.liferay.commerce.user.segment.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.user.segment.service.http.CommerceUserSegmentCriterionServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.user.segment.service.http.CommerceUserSegmentCriterionServiceSoap
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionSoap implements Serializable {
	public static CommerceUserSegmentCriterionSoap toSoapModel(
		CommerceUserSegmentCriterion model) {
		CommerceUserSegmentCriterionSoap soapModel = new CommerceUserSegmentCriterionSoap();

		soapModel.setCommerceUserSegmentCriterionId(model.getCommerceUserSegmentCriterionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceUserSegmentEntryId(model.getCommerceUserSegmentEntryId());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static CommerceUserSegmentCriterionSoap[] toSoapModels(
		CommerceUserSegmentCriterion[] models) {
		CommerceUserSegmentCriterionSoap[] soapModels = new CommerceUserSegmentCriterionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceUserSegmentCriterionSoap[][] toSoapModels(
		CommerceUserSegmentCriterion[][] models) {
		CommerceUserSegmentCriterionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceUserSegmentCriterionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceUserSegmentCriterionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceUserSegmentCriterionSoap[] toSoapModels(
		List<CommerceUserSegmentCriterion> models) {
		List<CommerceUserSegmentCriterionSoap> soapModels = new ArrayList<CommerceUserSegmentCriterionSoap>(models.size());

		for (CommerceUserSegmentCriterion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceUserSegmentCriterionSoap[soapModels.size()]);
	}

	public CommerceUserSegmentCriterionSoap() {
	}

	public long getPrimaryKey() {
		return _commerceUserSegmentCriterionId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceUserSegmentCriterionId(pk);
	}

	public long getCommerceUserSegmentCriterionId() {
		return _commerceUserSegmentCriterionId;
	}

	public void setCommerceUserSegmentCriterionId(
		long commerceUserSegmentCriterionId) {
		_commerceUserSegmentCriterionId = commerceUserSegmentCriterionId;
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

	public long getCommerceUserSegmentEntryId() {
		return _commerceUserSegmentEntryId;
	}

	public void setCommerceUserSegmentEntryId(long commerceUserSegmentEntryId) {
		_commerceUserSegmentEntryId = commerceUserSegmentEntryId;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	private long _commerceUserSegmentCriterionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceUserSegmentEntryId;
	private String _type;
	private String _typeSettings;
	private double _priority;
}