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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.shop.by.diagram.service.http.CSDiagramPinServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CSDiagramPinSoap implements Serializable {

	public static CSDiagramPinSoap toSoapModel(CSDiagramPin model) {
		CSDiagramPinSoap soapModel = new CSDiagramPinSoap();

		soapModel.setCSDiagramPinId(model.getCSDiagramPinId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setPositionX(model.getPositionX());
		soapModel.setPositionY(model.getPositionY());
		soapModel.setSequence(model.getSequence());

		return soapModel;
	}

	public static CSDiagramPinSoap[] toSoapModels(CSDiagramPin[] models) {
		CSDiagramPinSoap[] soapModels = new CSDiagramPinSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CSDiagramPinSoap[][] toSoapModels(CSDiagramPin[][] models) {
		CSDiagramPinSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CSDiagramPinSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CSDiagramPinSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CSDiagramPinSoap[] toSoapModels(List<CSDiagramPin> models) {
		List<CSDiagramPinSoap> soapModels = new ArrayList<CSDiagramPinSoap>(
			models.size());

		for (CSDiagramPin model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CSDiagramPinSoap[soapModels.size()]);
	}

	public CSDiagramPinSoap() {
	}

	public long getPrimaryKey() {
		return _CSDiagramPinId;
	}

	public void setPrimaryKey(long pk) {
		setCSDiagramPinId(pk);
	}

	public long getCSDiagramPinId() {
		return _CSDiagramPinId;
	}

	public void setCSDiagramPinId(long CSDiagramPinId) {
		_CSDiagramPinId = CSDiagramPinId;
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

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public double getPositionX() {
		return _positionX;
	}

	public void setPositionX(double positionX) {
		_positionX = positionX;
	}

	public double getPositionY() {
		return _positionY;
	}

	public void setPositionY(double positionY) {
		_positionY = positionY;
	}

	public String getSequence() {
		return _sequence;
	}

	public void setSequence(String sequence) {
		_sequence = sequence;
	}

	private long _CSDiagramPinId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private double _positionX;
	private double _positionY;
	private String _sequence;

}