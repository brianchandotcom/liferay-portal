/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.shop.by.diagram.service.http.CPDefinitionDiagramPinServiceSoap}.
 *
 * @author Andrea Sbarra
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CPDefinitionDiagramPinSoap implements Serializable {

	public static CPDefinitionDiagramPinSoap toSoapModel(
		CPDefinitionDiagramPin model) {

		CPDefinitionDiagramPinSoap soapModel = new CPDefinitionDiagramPinSoap();

		soapModel.setCPDefinitionDiagramPinId(
			model.getCPDefinitionDiagramPinId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setNumber(model.getNumber());
		soapModel.setPositionX(model.getPositionX());
		soapModel.setPositionY(model.getPositionY());

		return soapModel;
	}

	public static CPDefinitionDiagramPinSoap[] toSoapModels(
		CPDefinitionDiagramPin[] models) {

		CPDefinitionDiagramPinSoap[] soapModels =
			new CPDefinitionDiagramPinSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionDiagramPinSoap[][] toSoapModels(
		CPDefinitionDiagramPin[][] models) {

		CPDefinitionDiagramPinSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CPDefinitionDiagramPinSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionDiagramPinSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionDiagramPinSoap[] toSoapModels(
		List<CPDefinitionDiagramPin> models) {

		List<CPDefinitionDiagramPinSoap> soapModels =
			new ArrayList<CPDefinitionDiagramPinSoap>(models.size());

		for (CPDefinitionDiagramPin model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CPDefinitionDiagramPinSoap[soapModels.size()]);
	}

	public CPDefinitionDiagramPinSoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionDiagramPinId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionDiagramPinId(pk);
	}

	public long getCPDefinitionDiagramPinId() {
		return _CPDefinitionDiagramPinId;
	}

	public void setCPDefinitionDiagramPinId(long CPDefinitionDiagramPinId) {
		_CPDefinitionDiagramPinId = CPDefinitionDiagramPinId;
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

	public int getNumber() {
		return _number;
	}

	public void setNumber(int number) {
		_number = number;
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

	private long _CPDefinitionDiagramPinId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private int _number;
	private double _positionX;
	private double _positionY;

}