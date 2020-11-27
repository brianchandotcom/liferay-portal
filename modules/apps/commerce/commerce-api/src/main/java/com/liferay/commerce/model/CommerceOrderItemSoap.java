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
		soapModel.setBookedQuantityId(model.getBookedQuantityId());
		soapModel.setCommerceOrderId(model.getCommerceOrderId());
		soapModel.setCommercePriceListId(model.getCommercePriceListId());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setCProductId(model.getCProductId());
		soapModel.setParentCommerceOrderItemId(
			model.getParentCommerceOrderItemId());
		soapModel.setShippingAddressId(model.getShippingAddressId());
		soapModel.setDeliveryGroup(model.getDeliveryGroup());
		soapModel.setDeliveryMaxSubscriptionCycles(
			model.getDeliveryMaxSubscriptionCycles());
		soapModel.setDeliverySubscriptionLength(
			model.getDeliverySubscriptionLength());
		soapModel.setDeliverySubscriptionType(
			model.getDeliverySubscriptionType());
		soapModel.setDeliverySubscriptionTypeSettings(
			model.getDeliverySubscriptionTypeSettings());
		soapModel.setDepth(model.getDepth());
		soapModel.setDiscountAmount(model.getDiscountAmount());
		soapModel.setDiscountPercentageLevel1(
			model.getDiscountPercentageLevel1());
		soapModel.setDiscountPercentageLevel2(
			model.getDiscountPercentageLevel2());
		soapModel.setDiscountPercentageLevel3(
			model.getDiscountPercentageLevel3());
		soapModel.setDiscountPercentageLevel4(
			model.getDiscountPercentageLevel4());
		soapModel.setDiscountPercentageLevel1WithTaxAmount(
			model.getDiscountPercentageLevel1WithTaxAmount());
		soapModel.setDiscountPercentageLevel2WithTaxAmount(
			model.getDiscountPercentageLevel2WithTaxAmount());
		soapModel.setDiscountPercentageLevel3WithTaxAmount(
			model.getDiscountPercentageLevel3WithTaxAmount());
		soapModel.setDiscountPercentageLevel4WithTaxAmount(
			model.getDiscountPercentageLevel4WithTaxAmount());
		soapModel.setDiscountWithTaxAmount(model.getDiscountWithTaxAmount());
		soapModel.setFinalPrice(model.getFinalPrice());
		soapModel.setFinalPriceWithTaxAmount(
			model.getFinalPriceWithTaxAmount());
		soapModel.setFreeShipping(model.isFreeShipping());
		soapModel.setHeight(model.getHeight());
		soapModel.setJson(model.getJson());
		soapModel.setManuallyAdjusted(model.isManuallyAdjusted());
		soapModel.setMaxSubscriptionCycles(model.getMaxSubscriptionCycles());
		soapModel.setName(model.getName());
		soapModel.setPrintedNote(model.getPrintedNote());
		soapModel.setPromoPrice(model.getPromoPrice());
		soapModel.setPromoPriceWithTaxAmount(
			model.getPromoPriceWithTaxAmount());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setRequestedDeliveryDate(model.getRequestedDeliveryDate());
		soapModel.setShipSeparately(model.isShipSeparately());
		soapModel.setShippable(model.isShippable());
		soapModel.setShippedQuantity(model.getShippedQuantity());
		soapModel.setShippingExtraPrice(model.getShippingExtraPrice());
		soapModel.setSku(model.getSku());
		soapModel.setSubscription(model.isSubscription());
		soapModel.setSubscriptionLength(model.getSubscriptionLength());
		soapModel.setSubscriptionType(model.getSubscriptionType());
		soapModel.setSubscriptionTypeSettings(
			model.getSubscriptionTypeSettings());
		soapModel.setUnitPrice(model.getUnitPrice());
		soapModel.setUnitPriceWithTaxAmount(model.getUnitPriceWithTaxAmount());
		soapModel.setWeight(model.getWeight());
		soapModel.setWidth(model.getWidth());

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

	public long getBookedQuantityId() {
		return _bookedQuantityId;
	}

	public void setBookedQuantityId(long bookedQuantityId) {
		_bookedQuantityId = bookedQuantityId;
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

	public long getParentCommerceOrderItemId() {
		return _parentCommerceOrderItemId;
	}

	public void setParentCommerceOrderItemId(long parentCommerceOrderItemId) {
		_parentCommerceOrderItemId = parentCommerceOrderItemId;
	}

	public long getShippingAddressId() {
		return _shippingAddressId;
	}

	public void setShippingAddressId(long shippingAddressId) {
		_shippingAddressId = shippingAddressId;
	}

	public String getDeliveryGroup() {
		return _deliveryGroup;
	}

	public void setDeliveryGroup(String deliveryGroup) {
		_deliveryGroup = deliveryGroup;
	}

	public long getDeliveryMaxSubscriptionCycles() {
		return _deliveryMaxSubscriptionCycles;
	}

	public void setDeliveryMaxSubscriptionCycles(
		long deliveryMaxSubscriptionCycles) {

		_deliveryMaxSubscriptionCycles = deliveryMaxSubscriptionCycles;
	}

	public int getDeliverySubscriptionLength() {
		return _deliverySubscriptionLength;
	}

	public void setDeliverySubscriptionLength(int deliverySubscriptionLength) {
		_deliverySubscriptionLength = deliverySubscriptionLength;
	}

	public String getDeliverySubscriptionType() {
		return _deliverySubscriptionType;
	}

	public void setDeliverySubscriptionType(String deliverySubscriptionType) {
		_deliverySubscriptionType = deliverySubscriptionType;
	}

	public String getDeliverySubscriptionTypeSettings() {
		return _deliverySubscriptionTypeSettings;
	}

	public void setDeliverySubscriptionTypeSettings(
		String deliverySubscriptionTypeSettings) {

		_deliverySubscriptionTypeSettings = deliverySubscriptionTypeSettings;
	}

	public double getDepth() {
		return _depth;
	}

	public void setDepth(double depth) {
		_depth = depth;
	}

	public BigDecimal getDiscountAmount() {
		return _discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		_discountAmount = discountAmount;
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

	public BigDecimal getDiscountWithTaxAmount() {
		return _discountWithTaxAmount;
	}

	public void setDiscountWithTaxAmount(BigDecimal discountWithTaxAmount) {
		_discountWithTaxAmount = discountWithTaxAmount;
	}

	public BigDecimal getFinalPrice() {
		return _finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		_finalPrice = finalPrice;
	}

	public BigDecimal getFinalPriceWithTaxAmount() {
		return _finalPriceWithTaxAmount;
	}

	public void setFinalPriceWithTaxAmount(BigDecimal finalPriceWithTaxAmount) {
		_finalPriceWithTaxAmount = finalPriceWithTaxAmount;
	}

	public boolean getFreeShipping() {
		return _freeShipping;
	}

	public boolean isFreeShipping() {
		return _freeShipping;
	}

	public void setFreeShipping(boolean freeShipping) {
		_freeShipping = freeShipping;
	}

	public double getHeight() {
		return _height;
	}

	public void setHeight(double height) {
		_height = height;
	}

	public String getJson() {
		return _json;
	}

	public void setJson(String json) {
		_json = json;
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

	public long getMaxSubscriptionCycles() {
		return _maxSubscriptionCycles;
	}

	public void setMaxSubscriptionCycles(long maxSubscriptionCycles) {
		_maxSubscriptionCycles = maxSubscriptionCycles;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getPrintedNote() {
		return _printedNote;
	}

	public void setPrintedNote(String printedNote) {
		_printedNote = printedNote;
	}

	public BigDecimal getPromoPrice() {
		return _promoPrice;
	}

	public void setPromoPrice(BigDecimal promoPrice) {
		_promoPrice = promoPrice;
	}

	public BigDecimal getPromoPriceWithTaxAmount() {
		return _promoPriceWithTaxAmount;
	}

	public void setPromoPriceWithTaxAmount(BigDecimal promoPriceWithTaxAmount) {
		_promoPriceWithTaxAmount = promoPriceWithTaxAmount;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public Date getRequestedDeliveryDate() {
		return _requestedDeliveryDate;
	}

	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		_requestedDeliveryDate = requestedDeliveryDate;
	}

	public boolean getShipSeparately() {
		return _shipSeparately;
	}

	public boolean isShipSeparately() {
		return _shipSeparately;
	}

	public void setShipSeparately(boolean shipSeparately) {
		_shipSeparately = shipSeparately;
	}

	public boolean getShippable() {
		return _shippable;
	}

	public boolean isShippable() {
		return _shippable;
	}

	public void setShippable(boolean shippable) {
		_shippable = shippable;
	}

	public int getShippedQuantity() {
		return _shippedQuantity;
	}

	public void setShippedQuantity(int shippedQuantity) {
		_shippedQuantity = shippedQuantity;
	}

	public double getShippingExtraPrice() {
		return _shippingExtraPrice;
	}

	public void setShippingExtraPrice(double shippingExtraPrice) {
		_shippingExtraPrice = shippingExtraPrice;
	}

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
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

	public int getSubscriptionLength() {
		return _subscriptionLength;
	}

	public void setSubscriptionLength(int subscriptionLength) {
		_subscriptionLength = subscriptionLength;
	}

	public String getSubscriptionType() {
		return _subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		_subscriptionType = subscriptionType;
	}

	public String getSubscriptionTypeSettings() {
		return _subscriptionTypeSettings;
	}

	public void setSubscriptionTypeSettings(String subscriptionTypeSettings) {
		_subscriptionTypeSettings = subscriptionTypeSettings;
	}

	public BigDecimal getUnitPrice() {
		return _unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		_unitPrice = unitPrice;
	}

	public BigDecimal getUnitPriceWithTaxAmount() {
		return _unitPriceWithTaxAmount;
	}

	public void setUnitPriceWithTaxAmount(BigDecimal unitPriceWithTaxAmount) {
		_unitPriceWithTaxAmount = unitPriceWithTaxAmount;
	}

	public double getWeight() {
		return _weight;
	}

	public void setWeight(double weight) {
		_weight = weight;
	}

	public double getWidth() {
		return _width;
	}

	public void setWidth(double width) {
		_width = width;
	}

	private String _externalReferenceCode;
	private long _commerceOrderItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _bookedQuantityId;
	private long _commerceOrderId;
	private long _commercePriceListId;
	private long _CPInstanceId;
	private long _CProductId;
	private long _parentCommerceOrderItemId;
	private long _shippingAddressId;
	private String _deliveryGroup;
	private long _deliveryMaxSubscriptionCycles;
	private int _deliverySubscriptionLength;
	private String _deliverySubscriptionType;
	private String _deliverySubscriptionTypeSettings;
	private double _depth;
	private BigDecimal _discountAmount;
	private BigDecimal _discountPercentageLevel1;
	private BigDecimal _discountPercentageLevel2;
	private BigDecimal _discountPercentageLevel3;
	private BigDecimal _discountPercentageLevel4;
	private BigDecimal _discountPercentageLevel1WithTaxAmount;
	private BigDecimal _discountPercentageLevel2WithTaxAmount;
	private BigDecimal _discountPercentageLevel3WithTaxAmount;
	private BigDecimal _discountPercentageLevel4WithTaxAmount;
	private BigDecimal _discountWithTaxAmount;
	private BigDecimal _finalPrice;
	private BigDecimal _finalPriceWithTaxAmount;
	private boolean _freeShipping;
	private double _height;
	private String _json;
	private boolean _manuallyAdjusted;
	private long _maxSubscriptionCycles;
	private String _name;
	private String _printedNote;
	private BigDecimal _promoPrice;
	private BigDecimal _promoPriceWithTaxAmount;
	private int _quantity;
	private Date _requestedDeliveryDate;
	private boolean _shipSeparately;
	private boolean _shippable;
	private int _shippedQuantity;
	private double _shippingExtraPrice;
	private String _sku;
	private boolean _subscription;
	private int _subscriptionLength;
	private String _subscriptionType;
	private String _subscriptionTypeSettings;
	private BigDecimal _unitPrice;
	private BigDecimal _unitPriceWithTaxAmount;
	private double _weight;
	private double _width;

}