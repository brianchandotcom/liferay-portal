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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.shop.by.diagram.service.http.CSDiagramEntryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CSDiagramEntrySoap implements Serializable {

	public static CSDiagramEntrySoap toSoapModel(CSDiagramEntry model) {
		CSDiagramEntrySoap soapModel = new CSDiagramEntrySoap();

		soapModel.setCSDiagramEntryId(model.getCSDiagramEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setCProductId(model.getCProductId());
		soapModel.setDiagram(model.isDiagram());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setSequence(model.getSequence());
		soapModel.setSku(model.getSku());

		return soapModel;
	}

	public static CSDiagramEntrySoap[] toSoapModels(CSDiagramEntry[] models) {
		CSDiagramEntrySoap[] soapModels = new CSDiagramEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CSDiagramEntrySoap[][] toSoapModels(
		CSDiagramEntry[][] models) {

		CSDiagramEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CSDiagramEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CSDiagramEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CSDiagramEntrySoap[] toSoapModels(
		List<CSDiagramEntry> models) {

		List<CSDiagramEntrySoap> soapModels = new ArrayList<CSDiagramEntrySoap>(
			models.size());

		for (CSDiagramEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CSDiagramEntrySoap[soapModels.size()]);
	}

	public CSDiagramEntrySoap() {
	}

	public long getPrimaryKey() {
		return _CSDiagramEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCSDiagramEntryId(pk);
	}

	public long getCSDiagramEntryId() {
		return _CSDiagramEntryId;
	}

	public void setCSDiagramEntryId(long CSDiagramEntryId) {
		_CSDiagramEntryId = CSDiagramEntryId;
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

	public long getCPInstanceId() {
		return _CPInstanceId;
	}

	public void setCPInstanceId(long CPInstanceId) {
		_CPInstanceId = CPInstanceId;
	}

	public long getCProductId() {
		return _CProductId;
	}

	public void setCProductId(long CProductId) {
		_CProductId = CProductId;
	}

	public boolean getDiagram() {
		return _diagram;
	}

	public boolean isDiagram() {
		return _diagram;
	}

	public void setDiagram(boolean diagram) {
		_diagram = diagram;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public String getSequence() {
		return _sequence;
	}

	public void setSequence(String sequence) {
		_sequence = sequence;
	}

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	private long _CSDiagramEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private long _CPInstanceId;
	private long _CProductId;
	private boolean _diagram;
	private int _quantity;
	private String _sequence;
	private String _sku;

}