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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.shop.by.diagram.service.http.CPDefinitionDiagramEntryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CPDefinitionDiagramEntrySoap implements Serializable {

	public static CPDefinitionDiagramEntrySoap toSoapModel(
		CPDefinitionDiagramEntry model) {

		CPDefinitionDiagramEntrySoap soapModel =
			new CPDefinitionDiagramEntrySoap();

		soapModel.setCPDefinitionDiagramEntryId(
			model.getCPDefinitionDiagramEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionDiagramSettingsId(
			model.getCPDefinitionDiagramSettingsId());
		soapModel.setCPInstanceUuid(model.getCPInstanceUuid());
		soapModel.setCProductId(model.getCProductId());
		soapModel.setNumber(model.getNumber());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setPositionX(model.getPositionX());
		soapModel.setPositionY(model.getPositionY());
		soapModel.setRadius(model.getRadius());

		return soapModel;
	}

	public static CPDefinitionDiagramEntrySoap[] toSoapModels(
		CPDefinitionDiagramEntry[] models) {

		CPDefinitionDiagramEntrySoap[] soapModels =
			new CPDefinitionDiagramEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionDiagramEntrySoap[][] toSoapModels(
		CPDefinitionDiagramEntry[][] models) {

		CPDefinitionDiagramEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CPDefinitionDiagramEntrySoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionDiagramEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionDiagramEntrySoap[] toSoapModels(
		List<CPDefinitionDiagramEntry> models) {

		List<CPDefinitionDiagramEntrySoap> soapModels =
			new ArrayList<CPDefinitionDiagramEntrySoap>(models.size());

		for (CPDefinitionDiagramEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CPDefinitionDiagramEntrySoap[soapModels.size()]);
	}

	public CPDefinitionDiagramEntrySoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionDiagramEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionDiagramEntryId(pk);
	}

	public long getCPDefinitionDiagramEntryId() {
		return _CPDefinitionDiagramEntryId;
	}

	public void setCPDefinitionDiagramEntryId(long CPDefinitionDiagramEntryId) {
		_CPDefinitionDiagramEntryId = CPDefinitionDiagramEntryId;
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

	public long getCPDefinitionDiagramSettingsId() {
		return _CPDefinitionDiagramSettingsId;
	}

	public void setCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId) {

		_CPDefinitionDiagramSettingsId = CPDefinitionDiagramSettingsId;
	}

	public String getCPInstanceUuid() {
		return _CPInstanceUuid;
	}

	public void setCPInstanceUuid(String CPInstanceUuid) {
		_CPInstanceUuid = CPInstanceUuid;
	}

	public long getCProductId() {
		return _CProductId;
	}

	public void setCProductId(long CProductId) {
		_CProductId = CProductId;
	}

	public int getNumber() {
		return _number;
	}

	public void setNumber(int number) {
		_number = number;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
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

	public double getRadius() {
		return _radius;
	}

	public void setRadius(double radius) {
		_radius = radius;
	}

	private long _CPDefinitionDiagramEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionDiagramSettingsId;
	private String _CPInstanceUuid;
	private long _CProductId;
	private int _number;
	private int _quantity;
	private double _positionX;
	private double _positionY;
	private double _radius;

}