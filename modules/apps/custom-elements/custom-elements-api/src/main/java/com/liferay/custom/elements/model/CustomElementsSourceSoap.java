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

package com.liferay.custom.elements.model;

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
public class CustomElementsSourceSoap implements Serializable {

	public static CustomElementsSourceSoap toSoapModel(
		CustomElementsSource model) {

		CustomElementsSourceSoap soapModel = new CustomElementsSourceSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setCustomElementsSourceId(model.getCustomElementsSourceId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setHTMLElementName(model.getHTMLElementName());
		soapModel.setName(model.getName());
		soapModel.setURLs(model.getURLs());

		return soapModel;
	}

	public static CustomElementsSourceSoap[] toSoapModels(
		CustomElementsSource[] models) {

		CustomElementsSourceSoap[] soapModels =
			new CustomElementsSourceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CustomElementsSourceSoap[][] toSoapModels(
		CustomElementsSource[][] models) {

		CustomElementsSourceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CustomElementsSourceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CustomElementsSourceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CustomElementsSourceSoap[] toSoapModels(
		List<CustomElementsSource> models) {

		List<CustomElementsSourceSoap> soapModels =
			new ArrayList<CustomElementsSourceSoap>(models.size());

		for (CustomElementsSource model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CustomElementsSourceSoap[soapModels.size()]);
	}

	public CustomElementsSourceSoap() {
	}

	public long getPrimaryKey() {
		return _customElementsSourceId;
	}

	public void setPrimaryKey(long pk) {
		setCustomElementsSourceId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCustomElementsSourceId() {
		return _customElementsSourceId;
	}

	public void setCustomElementsSourceId(long customElementsSourceId) {
		_customElementsSourceId = customElementsSourceId;
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

	public String getHTMLElementName() {
		return _htmlElementName;
	}

	public void setHTMLElementName(String htmlElementName) {
		_htmlElementName = htmlElementName;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getURLs() {
		return _urls;
	}

	public void setURLs(String urls) {
		_urls = urls;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _customElementsSourceId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _htmlElementName;
	private String _name;
	private String _urls;

}