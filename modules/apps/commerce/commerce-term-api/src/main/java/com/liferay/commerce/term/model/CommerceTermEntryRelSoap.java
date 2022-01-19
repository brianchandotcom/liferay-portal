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

package com.liferay.commerce.term.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.term.service.http.CommerceTermEntryRelServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceTermEntryRelSoap implements Serializable {

	public static CommerceTermEntryRelSoap toSoapModel(
		CommerceTermEntryRel model) {

		CommerceTermEntryRelSoap soapModel = new CommerceTermEntryRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCommerceTermEntryRelId(model.getCommerceTermEntryRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setCommerceTermEntryId(model.getCommerceTermEntryId());

		return soapModel;
	}

	public static CommerceTermEntryRelSoap[] toSoapModels(
		CommerceTermEntryRel[] models) {

		CommerceTermEntryRelSoap[] soapModels =
			new CommerceTermEntryRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceTermEntryRelSoap[][] toSoapModels(
		CommerceTermEntryRel[][] models) {

		CommerceTermEntryRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceTermEntryRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceTermEntryRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceTermEntryRelSoap[] toSoapModels(
		List<CommerceTermEntryRel> models) {

		List<CommerceTermEntryRelSoap> soapModels =
			new ArrayList<CommerceTermEntryRelSoap>(models.size());

		for (CommerceTermEntryRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceTermEntryRelSoap[soapModels.size()]);
	}

	public CommerceTermEntryRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceTermEntryRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceTermEntryRelId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCommerceTermEntryRelId() {
		return _commerceTermEntryRelId;
	}

	public void setCommerceTermEntryRelId(long commerceTermEntryRelId) {
		_commerceTermEntryRelId = commerceTermEntryRelId;
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

	public long getCommerceTermEntryId() {
		return _commerceTermEntryId;
	}

	public void setCommerceTermEntryId(long commerceTermEntryId) {
		_commerceTermEntryId = commerceTermEntryId;
	}

	private long _mvccVersion;
	private long _commerceTermEntryRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _commerceTermEntryId;

}