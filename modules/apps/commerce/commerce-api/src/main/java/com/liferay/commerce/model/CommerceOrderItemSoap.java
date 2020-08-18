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

package com.liferay.commerce.model;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceOrderItemServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceOrderItemSoap implements Serializable {

	public static CommerceOrderItemSoap toSoapModel(CommerceOrderItem model) {
		CommerceOrderItemSoap soapModel = new CommerceOrderItemSoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setCommerceOrderItemId(model.getCommerceOrderItemId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceOrderId(model.getCommerceOrderId());
		soapModel.setCommercePriceListId(model.getCommercePriceListId());
		soapModel.setCProductId(model.getCProductId());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setParentCommerceOrderItemId(
			model.getParentCommerceOrderItemId());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setShippedQuantity(model.getShippedQuantity());
		soapModel.setJson(model.getJson());
		soapModel.setName(model.getName());
		soapModel.setSku(model.getSku());
		soapModel.setUnitPrice(model.getUnitPrice());
		soapModel.setPromoPrice(model.getPromoPrice());
		soapModel.setDiscountAmount(model.getDiscountAmount());
		soapModel.setFinalPrice(model.getFinalPrice());
		soapModel.setDiscountPercentageLevel1(
			model.getDiscountPercentageLevel1());
		soapModel.setDiscountPercentageLevel2(
			model.getDiscountPercentageLevel2());
		soapModel.setDiscountPercentageLevel3(
			model.getDiscountPercentageLevel3());
		soapModel.setDiscountPercentageLevel4(
			model.getDiscountPercentageLevel4());
		soapModel.setUnitPriceWithTaxAmount(model.getUnitPriceWithTaxAmount());
		soapModel.setPromoPriceWithTaxAmount(
			model.getPromoPriceWithTaxAmount());
		soapModel.setDiscountWithTaxAmount(model.getDiscountWithTaxAmount());
		soapModel.setFinalPriceWithTaxAmount(
			model.getFinalPriceWithTaxAmount());
		soapModel.setDiscountPercentageLevel1WithTaxAmount(
			model.getDiscountPercentageLevel1WithTaxAmount());
		soapModel.setDiscountPercentageLevel2WithTaxAmount(
			model.getDiscountPercentageLevel2WithTaxAmount());
		soapModel.setDiscountPercentageLevel3WithTaxAmount(
			model.getDiscountPercentageLevel3WithTaxAmount());
		soapModel.setDiscountPercentageLevel4WithTaxAmount(
			model.getDiscountPercentageLevel4WithTaxAmount());
		soapModel.setSubscription(model.isSubscription());
		soapModel.setDeliveryGroup(model.getDeliveryGroup());
		soapModel.setShippingAddressId(model.getShippingAddressId());
		soapModel.setPrintedNote(model.getPrintedNote());
		soapModel.setRequestedDeliveryDate(model.getRequestedDeliveryDate());
		soapModel.setBookedQuantityId(model.getBookedQuantityId());
		soapModel.setManuallyAdjusted(model.isManuallyAdjusted());

		return soapModel;
	}

	public static CommerceOrderItemSoap[] toSoapModels(
		CommerceOrderItem[] models) {

		CommerceOrderItemSoap[] soapModels =
			new CommerceOrderItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderItemSoap[][] toSoapModels(
		CommerceOrderItem[][] models) {

		CommerceOrderItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceOrderItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceOrderItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderItemSoap[] toSoapModels(
		List<CommerceOrderItem> models) {

		List<CommerceOrderItemSoap> soapModels =
			new ArrayList<CommerceOrderItemSoap>(models.size());

		for (CommerceOrderItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceOrderItemSoap[soapModels.size()]);
	}

	public CommerceOrderItemSoap() {
	}

	public long getPrimaryKey() {
		return _commerceOrderItemId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceOrderItemId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
	}

	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceOrderItemId = commerceOrderItemId;
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

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderId = commerceOrderId;
	}

	public long getCommercePriceListId() {
		return _commercePriceListId;
	}

	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListId = commercePriceListId;
	}

	public long getCProductId() {
		return _CProductId;
	}

	public void setCProductId(long CProductId) {
		_CProductId = CProductId;
	}

	public long getCPInstanceId() {
		return _CPInstanceId;
	}

	public void setCPInstanceId(long CPInstanceId) {
		_CPInstanceId = CPInstanceId;
	}

	public long getParentCommerceOrderItemId() {
		return _parentCommerceOrderItemId;
	}

	public void setParentCommerceOrderItemId(long parentCommerceOrderItemId) {
		_parentCommerceOrderItemId = parentCommerceOrderItemId;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public int getShippedQuantity() {
		return _shippedQuantity;
	}

	public void setShippedQuantity(int shippedQuantity) {
		_shippedQuantity = shippedQuantity;
	}

	public String getJson() {
		return _json;
	}

	public void setJson(String json) {
		_json = json;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public BigDecimal getUnitPrice() {
		return _unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		_unitPrice = unitPrice;
	}

	public BigDecimal getPromoPrice() {
		return _promoPrice;
	}

	public void setPromoPrice(BigDecimal promoPrice) {
		_promoPrice = promoPrice;
	}

	public BigDecimal getDiscountAmount() {
		return _discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		_discountAmount = discountAmount;
	}

	public BigDecimal getFinalPrice() {
		return _finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		_finalPrice = finalPrice;
	}

	public BigDecimal getDiscountPercentageLevel1() {
		return _discountPercentageLevel1;
	}

	public void setDiscountPercentageLevel1(
		BigDecimal discountPercentageLevel1) {

		_discountPercentageLevel1 = discountPercentageLevel1;
	}

	public BigDecimal getDiscountPercentageLevel2() {
		return _discountPercentageLevel2;
	}

	public void setDiscountPercentageLevel2(
		BigDecimal discountPercentageLevel2) {

		_discountPercentageLevel2 = discountPercentageLevel2;
	}

	public BigDecimal getDiscountPercentageLevel3() {
		return _discountPercentageLevel3;
	}

	public void setDiscountPercentageLevel3(
		BigDecimal discountPercentageLevel3) {

		_discountPercentageLevel3 = discountPercentageLevel3;
	}

	public BigDecimal getDiscountPercentageLevel4() {
		return _discountPercentageLevel4;
	}

	public void setDiscountPercentageLevel4(
		BigDecimal discountPercentageLevel4) {

		_discountPercentageLevel4 = discountPercentageLevel4;
	}

	public BigDecimal getUnitPriceWithTaxAmount() {
		return _unitPriceWithTaxAmount;
	}

	public void setUnitPriceWithTaxAmount(BigDecimal unitPriceWithTaxAmount) {
		_unitPriceWithTaxAmount = unitPriceWithTaxAmount;
	}

	public BigDecimal getPromoPriceWithTaxAmount() {
		return _promoPriceWithTaxAmount;
	}

	public void setPromoPriceWithTaxAmount(BigDecimal promoPriceWithTaxAmount) {
		_promoPriceWithTaxAmount = promoPriceWithTaxAmount;
	}

	public BigDecimal getDiscountWithTaxAmount() {
		return _discountWithTaxAmount;
	}

	public void setDiscountWithTaxAmount(BigDecimal discountWithTaxAmount) {
		_discountWithTaxAmount = discountWithTaxAmount;
	}

	public BigDecimal getFinalPriceWithTaxAmount() {
		return _finalPriceWithTaxAmount;
	}

	public void setFinalPriceWithTaxAmount(BigDecimal finalPriceWithTaxAmount) {
		_finalPriceWithTaxAmount = finalPriceWithTaxAmount;
	}

	public BigDecimal getDiscountPercentageLevel1WithTaxAmount() {
		return _discountPercentageLevel1WithTaxAmount;
	}

	public void setDiscountPercentageLevel1WithTaxAmount(
		BigDecimal discountPercentageLevel1WithTaxAmount) {

		_discountPercentageLevel1WithTaxAmount =
			discountPercentageLevel1WithTaxAmount;
	}

	public BigDecimal getDiscountPercentageLevel2WithTaxAmount() {
		return _discountPercentageLevel2WithTaxAmount;
	}

	public void setDiscountPercentageLevel2WithTaxAmount(
		BigDecimal discountPercentageLevel2WithTaxAmount) {

		_discountPercentageLevel2WithTaxAmount =
			discountPercentageLevel2WithTaxAmount;
	}

	public BigDecimal getDiscountPercentageLevel3WithTaxAmount() {
		return _discountPercentageLevel3WithTaxAmount;
	}

	public void setDiscountPercentageLevel3WithTaxAmount(
		BigDecimal discountPercentageLevel3WithTaxAmount) {

		_discountPercentageLevel3WithTaxAmount =
			discountPercentageLevel3WithTaxAmount;
	}

	public BigDecimal getDiscountPercentageLevel4WithTaxAmount() {
		return _discountPercentageLevel4WithTaxAmount;
	}

	public void setDiscountPercentageLevel4WithTaxAmount(
		BigDecimal discountPercentageLevel4WithTaxAmount) {

		_discountPercentageLevel4WithTaxAmount =
			discountPercentageLevel4WithTaxAmount;
	}

	public boolean getSubscription() {
		return _subscription;
	}

	public boolean isSubscription() {
		return _subscription;
	}

	public void setSubscription(boolean subscription) {
		_subscription = subscription;
	}

	public String getDeliveryGroup() {
		return _deliveryGroup;
	}

	public void setDeliveryGroup(String deliveryGroup) {
		_deliveryGroup = deliveryGroup;
	}

	public long getShippingAddressId() {
		return _shippingAddressId;
	}

	public void setShippingAddressId(long shippingAddressId) {
		_shippingAddressId = shippingAddressId;
	}

	public String getPrintedNote() {
		return _printedNote;
	}

	public void setPrintedNote(String printedNote) {
		_printedNote = printedNote;
	}

	public Date getRequestedDeliveryDate() {
		return _requestedDeliveryDate;
	}

	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		_requestedDeliveryDate = requestedDeliveryDate;
	}

	public long getBookedQuantityId() {
		return _bookedQuantityId;
	}

	public void setBookedQuantityId(long bookedQuantityId) {
		_bookedQuantityId = bookedQuantityId;
	}

	public boolean getManuallyAdjusted() {
		return _manuallyAdjusted;
	}

	public boolean isManuallyAdjusted() {
		return _manuallyAdjusted;
	}

	public void setManuallyAdjusted(boolean manuallyAdjusted) {
		_manuallyAdjusted = manuallyAdjusted;
	}

	private String _externalReferenceCode;
	private long _commerceOrderItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceOrderId;
	private long _commercePriceListId;
	private long _CProductId;
	private long _CPInstanceId;
	private long _parentCommerceOrderItemId;
	private int _quantity;
	private int _shippedQuantity;
	private String _json;
	private String _name;
	private String _sku;
	private BigDecimal _unitPrice;
	private BigDecimal _promoPrice;
	private BigDecimal _discountAmount;
	private BigDecimal _finalPrice;
	private BigDecimal _discountPercentageLevel1;
	private BigDecimal _discountPercentageLevel2;
	private BigDecimal _discountPercentageLevel3;
	private BigDecimal _discountPercentageLevel4;
	private BigDecimal _unitPriceWithTaxAmount;
	private BigDecimal _promoPriceWithTaxAmount;
	private BigDecimal _discountWithTaxAmount;
	private BigDecimal _finalPriceWithTaxAmount;
	private BigDecimal _discountPercentageLevel1WithTaxAmount;
	private BigDecimal _discountPercentageLevel2WithTaxAmount;
	private BigDecimal _discountPercentageLevel3WithTaxAmount;
	private BigDecimal _discountPercentageLevel4WithTaxAmount;
	private boolean _subscription;
	private String _deliveryGroup;
	private long _shippingAddressId;
	private String _printedNote;
	private Date _requestedDeliveryDate;
	private long _bookedQuantityId;
	private boolean _manuallyAdjusted;

}