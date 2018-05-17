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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.notification.service.http.CommerceNotificationTemplateServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.notification.service.http.CommerceNotificationTemplateServiceSoap
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateSoap implements Serializable {
	public static CommerceNotificationTemplateSoap toSoapModel(
		CommerceNotificationTemplate model) {
		CommerceNotificationTemplateSoap soapModel = new CommerceNotificationTemplateSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceNotificationTemplateId(model.getCommerceNotificationTemplateId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setCc(model.getCc());
		soapModel.setCcn(model.getCcn());
		soapModel.setType(model.getType());
		soapModel.setEnabled(model.isEnabled());
		soapModel.setSubject(model.getSubject());
		soapModel.setBody(model.getBody());

		return soapModel;
	}

	public static CommerceNotificationTemplateSoap[] toSoapModels(
		CommerceNotificationTemplate[] models) {
		CommerceNotificationTemplateSoap[] soapModels = new CommerceNotificationTemplateSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceNotificationTemplateSoap[][] toSoapModels(
		CommerceNotificationTemplate[][] models) {
		CommerceNotificationTemplateSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceNotificationTemplateSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceNotificationTemplateSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceNotificationTemplateSoap[] toSoapModels(
		List<CommerceNotificationTemplate> models) {
		List<CommerceNotificationTemplateSoap> soapModels = new ArrayList<CommerceNotificationTemplateSoap>(models.size());

		for (CommerceNotificationTemplate model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceNotificationTemplateSoap[soapModels.size()]);
	}

	public CommerceNotificationTemplateSoap() {
	}

	public long getPrimaryKey() {
		return _commerceNotificationTemplateId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceNotificationTemplateId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceNotificationTemplateId() {
		return _commerceNotificationTemplateId;
	}

	public void setCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		_commerceNotificationTemplateId = commerceNotificationTemplateId;
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

	public String getCc() {
		return _cc;
	}

	public void setCc(String cc) {
		_cc = cc;
	}

	public String getCcn() {
		return _ccn;
	}

	public void setCcn(String ccn) {
		_ccn = ccn;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public boolean getEnabled() {
		return _enabled;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public String getSubject() {
		return _subject;
	}

	public void setSubject(String subject) {
		_subject = subject;
	}

	public String getBody() {
		return _body;
	}

	public void setBody(String body) {
		_body = body;
	}

	private String _uuid;
	private long _commerceNotificationTemplateId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private String _cc;
	private String _ccn;
	private String _type;
	private boolean _enabled;
	private String _subject;
	private String _body;
}