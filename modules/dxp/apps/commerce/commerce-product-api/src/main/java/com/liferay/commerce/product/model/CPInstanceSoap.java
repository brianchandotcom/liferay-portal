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

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPInstanceServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPInstanceServiceSoap
 * @generated
 */
@ProviderType
public class CPInstanceSoap implements Serializable {
	public static CPInstanceSoap toSoapModel(CPInstance model) {
		CPInstanceSoap soapModel = new CPInstanceSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setSku(model.getSku());
		soapModel.setGtin(model.getGtin());
		soapModel.setManufacturerPartNumber(model.getManufacturerPartNumber());
		soapModel.setPurchasable(model.isPurchasable());
		soapModel.setDDMContent(model.getDDMContent());
		soapModel.setWidth(model.getWidth());
		soapModel.setHeight(model.getHeight());
		soapModel.setDepth(model.getDepth());
		soapModel.setWeight(model.getWeight());
		soapModel.setCost(model.getCost());
		soapModel.setPrice(model.getPrice());
		soapModel.setPromoPrice(model.getPromoPrice());
		soapModel.setPublished(model.isPublished());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setDisplayDate(model.getDisplayDate());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setLastPublishDate(model.getLastPublishDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static CPInstanceSoap[] toSoapModels(CPInstance[] models) {
		CPInstanceSoap[] soapModels = new CPInstanceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPInstanceSoap[][] toSoapModels(CPInstance[][] models) {
		CPInstanceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPInstanceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPInstanceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPInstanceSoap[] toSoapModels(List<CPInstance> models) {
		List<CPInstanceSoap> soapModels = new ArrayList<CPInstanceSoap>(models.size());

		for (CPInstance model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPInstanceSoap[soapModels.size()]);
	}

	public CPInstanceSoap() {
	}

	public long getPrimaryKey() {
		return _CPInstanceId;
	}

	public void setPrimaryKey(long pk) {
		setCPInstanceId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPInstanceId() {
		return _CPInstanceId;
	}

	public void setCPInstanceId(long CPInstanceId) {
		_CPInstanceId = CPInstanceId;
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

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public String getGtin() {
		return _gtin;
	}

	public void setGtin(String gtin) {
		_gtin = gtin;
	}

	public String getManufacturerPartNumber() {
		return _manufacturerPartNumber;
	}

	public void setManufacturerPartNumber(String manufacturerPartNumber) {
		_manufacturerPartNumber = manufacturerPartNumber;
	}

	public boolean getPurchasable() {
		return _purchasable;
	}

	public boolean isPurchasable() {
		return _purchasable;
	}

	public void setPurchasable(boolean purchasable) {
		_purchasable = purchasable;
	}

	public String getDDMContent() {
		return _DDMContent;
	}

	public void setDDMContent(String DDMContent) {
		_DDMContent = DDMContent;
	}

	public double getWidth() {
		return _width;
	}

	public void setWidth(double width) {
		_width = width;
	}

	public double getHeight() {
		return _height;
	}

	public void setHeight(double height) {
		_height = height;
	}

	public double getDepth() {
		return _depth;
	}

	public void setDepth(double depth) {
		_depth = depth;
	}

	public double getWeight() {
		return _weight;
	}

	public void setWeight(double weight) {
		_weight = weight;
	}

	public BigDecimal getCost() {
		return _cost;
	}

	public void setCost(BigDecimal cost) {
		_cost = cost;
	}

	public BigDecimal getPrice() {
		return _price;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	public BigDecimal getPromoPrice() {
		return _promoPrice;
	}

	public void setPromoPrice(BigDecimal promoPrice) {
		_promoPrice = promoPrice;
	}

	public boolean getPublished() {
		return _published;
	}

	public boolean isPublished() {
		return _published;
	}

	public void setPublished(boolean published) {
		_published = published;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private String _uuid;
	private long _CPInstanceId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private String _sku;
	private String _gtin;
	private String _manufacturerPartNumber;
	private boolean _purchasable;
	private String _DDMContent;
	private double _width;
	private double _height;
	private double _depth;
	private double _weight;
	private BigDecimal _cost;
	private BigDecimal _price;
	private BigDecimal _promoPrice;
	private boolean _published;
	private String _externalReferenceCode;
	private Date _displayDate;
	private Date _expirationDate;
	private Date _lastPublishDate;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
}