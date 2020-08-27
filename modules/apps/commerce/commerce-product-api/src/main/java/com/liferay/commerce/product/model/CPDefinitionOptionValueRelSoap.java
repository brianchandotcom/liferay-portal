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

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPDefinitionOptionValueRelServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CPDefinitionOptionValueRelSoap implements Serializable {

	public static CPDefinitionOptionValueRelSoap toSoapModel(
		CPDefinitionOptionValueRel model) {

		CPDefinitionOptionValueRelSoap soapModel =
			new CPDefinitionOptionValueRelSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDefinitionOptionValueRelId(
			model.getCPDefinitionOptionValueRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionOptionRelId(
			model.getCPDefinitionOptionRelId());
		soapModel.setCPInstanceUuid(model.getCPInstanceUuid());
		soapModel.setCProductId(model.getCProductId());
		soapModel.setName(model.getName());
		soapModel.setPriority(model.getPriority());
		soapModel.setKey(model.getKey());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setPreselected(model.isPreselected());
		soapModel.setPrice(model.getPrice());

		return soapModel;
	}

	public static CPDefinitionOptionValueRelSoap[] toSoapModels(
		CPDefinitionOptionValueRel[] models) {

		CPDefinitionOptionValueRelSoap[] soapModels =
			new CPDefinitionOptionValueRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionOptionValueRelSoap[][] toSoapModels(
		CPDefinitionOptionValueRel[][] models) {

		CPDefinitionOptionValueRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CPDefinitionOptionValueRelSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionOptionValueRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionOptionValueRelSoap[] toSoapModels(
		List<CPDefinitionOptionValueRel> models) {

		List<CPDefinitionOptionValueRelSoap> soapModels =
			new ArrayList<CPDefinitionOptionValueRelSoap>(models.size());

		for (CPDefinitionOptionValueRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CPDefinitionOptionValueRelSoap[soapModels.size()]);
	}

	public CPDefinitionOptionValueRelSoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionOptionValueRelId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionOptionValueRelId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDefinitionOptionValueRelId() {
		return _CPDefinitionOptionValueRelId;
	}

	public void setCPDefinitionOptionValueRelId(
		long CPDefinitionOptionValueRelId) {

		_CPDefinitionOptionValueRelId = CPDefinitionOptionValueRelId;
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

	public long getCPDefinitionOptionRelId() {
		return _CPDefinitionOptionRelId;
	}

	public void setCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		_CPDefinitionOptionRelId = CPDefinitionOptionRelId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public boolean getPreselected() {
		return _preselected;
	}

	public boolean isPreselected() {
		return _preselected;
	}

	public void setPreselected(boolean preselected) {
		_preselected = preselected;
	}

	public BigDecimal getPrice() {
		return _price;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	private String _uuid;
	private long _CPDefinitionOptionValueRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionOptionRelId;
	private String _CPInstanceUuid;
	private long _CProductId;
	private String _name;
	private double _priority;
	private String _key;
	private int _quantity;
	private boolean _preselected;
	private BigDecimal _price;

}