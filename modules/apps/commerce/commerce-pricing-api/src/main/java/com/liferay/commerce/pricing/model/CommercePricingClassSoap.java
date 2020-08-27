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

package com.liferay.commerce.pricing.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.pricing.service.http.CommercePricingClassServiceSoap}.
 *
 * @author Riccardo Alberti
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePricingClassSoap implements Serializable {

	public static CommercePricingClassSoap toSoapModel(
		CommercePricingClass model) {

		CommercePricingClassSoap soapModel = new CommercePricingClassSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setCommercePricingClassId(model.getCommercePricingClassId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommercePricingClassSoap[] toSoapModels(
		CommercePricingClass[] models) {

		CommercePricingClassSoap[] soapModels =
			new CommercePricingClassSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePricingClassSoap[][] toSoapModels(
		CommercePricingClass[][] models) {

		CommercePricingClassSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommercePricingClassSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePricingClassSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePricingClassSoap[] toSoapModels(
		List<CommercePricingClass> models) {

		List<CommercePricingClassSoap> soapModels =
			new ArrayList<CommercePricingClassSoap>(models.size());

		for (CommercePricingClass model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommercePricingClassSoap[soapModels.size()]);
	}

	public CommercePricingClassSoap() {
	}

	public long getPrimaryKey() {
		return _commercePricingClassId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePricingClassId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getCommercePricingClassId() {
		return _commercePricingClassId;
	}

	public void setCommercePricingClassId(long commercePricingClassId) {
		_commercePricingClassId = commercePricingClassId;
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

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private String _externalReferenceCode;
	private long _commercePricingClassId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private Date _lastPublishDate;

}