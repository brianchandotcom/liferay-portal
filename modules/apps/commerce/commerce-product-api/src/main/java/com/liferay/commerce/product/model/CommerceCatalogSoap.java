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

package com.liferay.commerce.product.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CommerceCatalogServiceSoap}.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceCatalogSoap implements Serializable {

	public static CommerceCatalogSoap toSoapModel(CommerceCatalog model) {
		CommerceCatalogSoap soapModel = new CommerceCatalogSoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setCommerceCatalogId(model.getCommerceCatalogId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setCommerceCurrencyCode(model.getCommerceCurrencyCode());
		soapModel.setCatalogDefaultLanguageId(
			model.getCatalogDefaultLanguageId());
		soapModel.setSystem(model.isSystem());

		return soapModel;
	}

	public static CommerceCatalogSoap[] toSoapModels(CommerceCatalog[] models) {
		CommerceCatalogSoap[] soapModels =
			new CommerceCatalogSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceCatalogSoap[][] toSoapModels(
		CommerceCatalog[][] models) {

		CommerceCatalogSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceCatalogSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceCatalogSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceCatalogSoap[] toSoapModels(
		List<CommerceCatalog> models) {

		List<CommerceCatalogSoap> soapModels =
			new ArrayList<CommerceCatalogSoap>(models.size());

		for (CommerceCatalog model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceCatalogSoap[soapModels.size()]);
	}

	public CommerceCatalogSoap() {
	}

	public long getPrimaryKey() {
		return _commerceCatalogId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceCatalogId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getCommerceCatalogId() {
		return _commerceCatalogId;
	}

	public void setCommerceCatalogId(long commerceCatalogId) {
		_commerceCatalogId = commerceCatalogId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getCommerceCurrencyCode() {
		return _commerceCurrencyCode;
	}

	public void setCommerceCurrencyCode(String commerceCurrencyCode) {
		_commerceCurrencyCode = commerceCurrencyCode;
	}

	public String getCatalogDefaultLanguageId() {
		return _catalogDefaultLanguageId;
	}

	public void setCatalogDefaultLanguageId(String catalogDefaultLanguageId) {
		_catalogDefaultLanguageId = catalogDefaultLanguageId;
	}

	public boolean getSystem() {
		return _system;
	}

	public boolean isSystem() {
		return _system;
	}

	public void setSystem(boolean system) {
		_system = system;
	}

	private String _externalReferenceCode;
	private long _commerceCatalogId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _commerceCurrencyCode;
	private String _catalogDefaultLanguageId;
	private boolean _system;

}