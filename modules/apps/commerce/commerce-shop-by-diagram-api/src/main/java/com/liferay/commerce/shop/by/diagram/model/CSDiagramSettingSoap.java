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

package com.liferay.commerce.shop.by.diagram.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.shop.by.diagram.service.http.CSDiagramSettingServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CSDiagramSettingSoap implements Serializable {

	public static CSDiagramSettingSoap toSoapModel(CSDiagramSetting model) {
		CSDiagramSettingSoap soapModel = new CSDiagramSettingSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCSDiagramSettingId(model.getCSDiagramSettingId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPAttachmentFileEntryId(
			model.getCPAttachmentFileEntryId());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setColor(model.getColor());
		soapModel.setRadius(model.getRadius());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static CSDiagramSettingSoap[] toSoapModels(
		CSDiagramSetting[] models) {

		CSDiagramSettingSoap[] soapModels =
			new CSDiagramSettingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CSDiagramSettingSoap[][] toSoapModels(
		CSDiagramSetting[][] models) {

		CSDiagramSettingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CSDiagramSettingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CSDiagramSettingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CSDiagramSettingSoap[] toSoapModels(
		List<CSDiagramSetting> models) {

		List<CSDiagramSettingSoap> soapModels =
			new ArrayList<CSDiagramSettingSoap>(models.size());

		for (CSDiagramSetting model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CSDiagramSettingSoap[soapModels.size()]);
	}

	public CSDiagramSettingSoap() {
	}

	public long getPrimaryKey() {
		return _CSDiagramSettingId;
	}

	public void setPrimaryKey(long pk) {
		setCSDiagramSettingId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCSDiagramSettingId() {
		return _CSDiagramSettingId;
	}

	public void setCSDiagramSettingId(long CSDiagramSettingId) {
		_CSDiagramSettingId = CSDiagramSettingId;
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

	public long getCPAttachmentFileEntryId() {
		return _CPAttachmentFileEntryId;
	}

	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		_CPAttachmentFileEntryId = CPAttachmentFileEntryId;
	}

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public String getColor() {
		return _color;
	}

	public void setColor(String color) {
		_color = color;
	}

	public double getRadius() {
		return _radius;
	}

	public void setRadius(double radius) {
		_radius = radius;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _uuid;
	private long _CSDiagramSettingId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPAttachmentFileEntryId;
	private long _CPDefinitionId;
	private String _color;
	private double _radius;
	private String _type;

}