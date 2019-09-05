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

package com.liferay.portal.kernel.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.EmailAddressServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class EmailAddressSoap implements Serializable {

	public static EmailAddressSoap toSoapModel(EmailAddress model) {
		EmailAddressSoap soapModel = new EmailAddressSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setEmailAddressId(model.getEmailAddressId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setAddress(model.getAddress());
		soapModel.setTypeId(model.getTypeId());
		soapModel.setPrimary(model.isPrimary());

		return soapModel;
	}

	public static EmailAddressSoap[] toSoapModels(EmailAddress[] models) {
		EmailAddressSoap[] soapModels = new EmailAddressSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static EmailAddressSoap[][] toSoapModels(EmailAddress[][] models) {
		EmailAddressSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new EmailAddressSoap[models.length][models[0].length];
		}
		else {
			soapModels = new EmailAddressSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static EmailAddressSoap[] toSoapModels(List<EmailAddress> models) {
		List<EmailAddressSoap> soapModels = new ArrayList<EmailAddressSoap>(
			models.size());

		for (EmailAddress model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new EmailAddressSoap[soapModels.size()]);
	}

	public EmailAddressSoap() {
	}

	public long getPrimaryKey() {
		return _emailAddressId;
	}

	public void setPrimaryKey(long pk) {
		setEmailAddressId(pk);
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

	public long getEmailAddressId() {
		return _emailAddressId;
	}

	public void setEmailAddressId(long emailAddressId) {
		_emailAddressId = emailAddressId;
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

	public String getAddress() {
		return _address;
	}

	public void setAddress(String address) {
		_address = address;
	}

	public long getTypeId() {
		return _typeId;
	}

	public void setTypeId(long typeId) {
		_typeId = typeId;
	}

	public boolean getPrimary() {
		return _primary;
	}

	public boolean isPrimary() {
		return _primary;
	}

	public void setPrimary(boolean primary) {
		_primary = primary;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _emailAddressId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private String _address;
	private long _typeId;
	private boolean _primary;

}