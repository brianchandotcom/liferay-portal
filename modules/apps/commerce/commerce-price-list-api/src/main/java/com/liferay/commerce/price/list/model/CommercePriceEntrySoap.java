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

package com.liferay.commerce.price.list.model;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.price.list.service.http.CommercePriceEntryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePriceEntrySoap implements Serializable {

	public static CommercePriceEntrySoap toSoapModel(CommercePriceEntry model) {
		CommercePriceEntrySoap soapModel = new CommercePriceEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setCommercePriceEntryId(model.getCommercePriceEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommercePriceListId(model.getCommercePriceListId());
		soapModel.setCPInstanceUuid(model.getCPInstanceUuid());
		soapModel.setCProductId(model.getCProductId());
		soapModel.setPrice(model.getPrice());
		soapModel.setPromoPrice(model.getPromoPrice());
		soapModel.setDiscountDiscovery(model.isDiscountDiscovery());
		soapModel.setDiscountLevel1(model.getDiscountLevel1());
		soapModel.setDiscountLevel2(model.getDiscountLevel2());
		soapModel.setDiscountLevel3(model.getDiscountLevel3());
		soapModel.setDiscountLevel4(model.getDiscountLevel4());
		soapModel.setHasTierPrice(model.isHasTierPrice());
		soapModel.setBulkPricing(model.isBulkPricing());
		soapModel.setDisplayDate(model.getDisplayDate());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setLastPublishDate(model.getLastPublishDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static CommercePriceEntrySoap[] toSoapModels(
		CommercePriceEntry[] models) {

		CommercePriceEntrySoap[] soapModels =
			new CommercePriceEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceEntrySoap[][] toSoapModels(
		CommercePriceEntry[][] models) {

		CommercePriceEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommercePriceEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePriceEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceEntrySoap[] toSoapModels(
		List<CommercePriceEntry> models) {

		List<CommercePriceEntrySoap> soapModels =
			new ArrayList<CommercePriceEntrySoap>(models.size());

		for (CommercePriceEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommercePriceEntrySoap[soapModels.size()]);
	}

	public CommercePriceEntrySoap() {
	}

	public long getPrimaryKey() {
		return _commercePriceEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePriceEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getCommercePriceEntryId() {
		return _commercePriceEntryId;
	}

	public void setCommercePriceEntryId(long commercePriceEntryId) {
		_commercePriceEntryId = commercePriceEntryId;
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

	public long getCommercePriceListId() {
		return _commercePriceListId;
	}

	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListId = commercePriceListId;
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

	public boolean getDiscountDiscovery() {
		return _discountDiscovery;
	}

	public boolean isDiscountDiscovery() {
		return _discountDiscovery;
	}

	public void setDiscountDiscovery(boolean discountDiscovery) {
		_discountDiscovery = discountDiscovery;
	}

	public BigDecimal getDiscountLevel1() {
		return _discountLevel1;
	}

	public void setDiscountLevel1(BigDecimal discountLevel1) {
		_discountLevel1 = discountLevel1;
	}

	public BigDecimal getDiscountLevel2() {
		return _discountLevel2;
	}

	public void setDiscountLevel2(BigDecimal discountLevel2) {
		_discountLevel2 = discountLevel2;
	}

	public BigDecimal getDiscountLevel3() {
		return _discountLevel3;
	}

	public void setDiscountLevel3(BigDecimal discountLevel3) {
		_discountLevel3 = discountLevel3;
	}

	public BigDecimal getDiscountLevel4() {
		return _discountLevel4;
	}

	public void setDiscountLevel4(BigDecimal discountLevel4) {
		_discountLevel4 = discountLevel4;
	}

	public boolean getHasTierPrice() {
		return _hasTierPrice;
	}

	public boolean isHasTierPrice() {
		return _hasTierPrice;
	}

	public void setHasTierPrice(boolean hasTierPrice) {
		_hasTierPrice = hasTierPrice;
	}

	public boolean getBulkPricing() {
		return _bulkPricing;
	}

	public boolean isBulkPricing() {
		return _bulkPricing;
	}

	public void setBulkPricing(boolean bulkPricing) {
		_bulkPricing = bulkPricing;
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
	private String _externalReferenceCode;
	private long _commercePriceEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commercePriceListId;
	private String _CPInstanceUuid;
	private long _CProductId;
	private BigDecimal _price;
	private BigDecimal _promoPrice;
	private boolean _discountDiscovery;
	private BigDecimal _discountLevel1;
	private BigDecimal _discountLevel2;
	private BigDecimal _discountLevel3;
	private BigDecimal _discountLevel4;
	private boolean _hasTierPrice;
	private boolean _bulkPricing;
	private Date _displayDate;
	private Date _expirationDate;
	private Date _lastPublishDate;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;

}