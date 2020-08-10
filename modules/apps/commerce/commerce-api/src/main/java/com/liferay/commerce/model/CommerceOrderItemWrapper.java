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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceOrderItem}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItem
 * @generated
 */
public class CommerceOrderItemWrapper
	implements CommerceOrderItem, ModelWrapper<CommerceOrderItem> {

	public CommerceOrderItemWrapper(CommerceOrderItem commerceOrderItem) {
		_commerceOrderItem = commerceOrderItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceOrderItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceOrderItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceOrderItemId", getCommerceOrderItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("commercePriceListId", getCommercePriceListId());
		attributes.put("CProductId", getCProductId());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put(
			"parentCommerceOrderItemId", getParentCommerceOrderItemId());
		attributes.put("quantity", getQuantity());
		attributes.put("shippedQuantity", getShippedQuantity());
		attributes.put("json", getJson());
		attributes.put("name", getName());
		attributes.put("sku", getSku());
		attributes.put("unitPrice", getUnitPrice());
		attributes.put("promoPrice", getPromoPrice());
		attributes.put("discountAmount", getDiscountAmount());
		attributes.put("finalPrice", getFinalPrice());
		attributes.put(
			"discountPercentageLevel1", getDiscountPercentageLevel1());
		attributes.put(
			"discountPercentageLevel2", getDiscountPercentageLevel2());
		attributes.put(
			"discountPercentageLevel3", getDiscountPercentageLevel3());
		attributes.put(
			"discountPercentageLevel4", getDiscountPercentageLevel4());
		attributes.put("unitPriceWithTaxAmount", getUnitPriceWithTaxAmount());
		attributes.put("promoPriceWithTaxAmount", getPromoPriceWithTaxAmount());
		attributes.put("discountWithTaxAmount", getDiscountWithTaxAmount());
		attributes.put("finalPriceWithTaxAmount", getFinalPriceWithTaxAmount());
		attributes.put(
			"discountPercentageLevel1WithTaxAmount",
			getDiscountPercentageLevel1WithTaxAmount());
		attributes.put(
			"discountPercentageLevel2WithTaxAmount",
			getDiscountPercentageLevel2WithTaxAmount());
		attributes.put(
			"discountPercentageLevel3WithTaxAmount",
			getDiscountPercentageLevel3WithTaxAmount());
		attributes.put(
			"discountPercentageLevel4WithTaxAmount",
			getDiscountPercentageLevel4WithTaxAmount());
		attributes.put("subscription", isSubscription());
		attributes.put("deliveryGroup", getDeliveryGroup());
		attributes.put("shippingAddressId", getShippingAddressId());
		attributes.put("printedNote", getPrintedNote());
		attributes.put("requestedDeliveryDate", getRequestedDeliveryDate());
		attributes.put("bookedQuantityId", getBookedQuantityId());
		attributes.put("manuallyAdjusted", isManuallyAdjusted());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceOrderItemId = (Long)attributes.get("commerceOrderItemId");

		if (commerceOrderItemId != null) {
			setCommerceOrderItemId(commerceOrderItemId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long commerceOrderId = (Long)attributes.get("commerceOrderId");

		if (commerceOrderId != null) {
			setCommerceOrderId(commerceOrderId);
		}

		Long commercePriceListId = (Long)attributes.get("commercePriceListId");

		if (commercePriceListId != null) {
			setCommercePriceListId(commercePriceListId);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}

		Long CPInstanceId = (Long)attributes.get("CPInstanceId");

		if (CPInstanceId != null) {
			setCPInstanceId(CPInstanceId);
		}

		Long parentCommerceOrderItemId = (Long)attributes.get(
			"parentCommerceOrderItemId");

		if (parentCommerceOrderItemId != null) {
			setParentCommerceOrderItemId(parentCommerceOrderItemId);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}

		Integer shippedQuantity = (Integer)attributes.get("shippedQuantity");

		if (shippedQuantity != null) {
			setShippedQuantity(shippedQuantity);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJson(json);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		BigDecimal unitPrice = (BigDecimal)attributes.get("unitPrice");

		if (unitPrice != null) {
			setUnitPrice(unitPrice);
		}

		BigDecimal promoPrice = (BigDecimal)attributes.get("promoPrice");

		if (promoPrice != null) {
			setPromoPrice(promoPrice);
		}

		BigDecimal discountAmount = (BigDecimal)attributes.get(
			"discountAmount");

		if (discountAmount != null) {
			setDiscountAmount(discountAmount);
		}

		BigDecimal finalPrice = (BigDecimal)attributes.get("finalPrice");

		if (finalPrice != null) {
			setFinalPrice(finalPrice);
		}

		BigDecimal discountPercentageLevel1 = (BigDecimal)attributes.get(
			"discountPercentageLevel1");

		if (discountPercentageLevel1 != null) {
			setDiscountPercentageLevel1(discountPercentageLevel1);
		}

		BigDecimal discountPercentageLevel2 = (BigDecimal)attributes.get(
			"discountPercentageLevel2");

		if (discountPercentageLevel2 != null) {
			setDiscountPercentageLevel2(discountPercentageLevel2);
		}

		BigDecimal discountPercentageLevel3 = (BigDecimal)attributes.get(
			"discountPercentageLevel3");

		if (discountPercentageLevel3 != null) {
			setDiscountPercentageLevel3(discountPercentageLevel3);
		}

		BigDecimal discountPercentageLevel4 = (BigDecimal)attributes.get(
			"discountPercentageLevel4");

		if (discountPercentageLevel4 != null) {
			setDiscountPercentageLevel4(discountPercentageLevel4);
		}

		BigDecimal unitPriceWithTaxAmount = (BigDecimal)attributes.get(
			"unitPriceWithTaxAmount");

		if (unitPriceWithTaxAmount != null) {
			setUnitPriceWithTaxAmount(unitPriceWithTaxAmount);
		}

		BigDecimal promoPriceWithTaxAmount = (BigDecimal)attributes.get(
			"promoPriceWithTaxAmount");

		if (promoPriceWithTaxAmount != null) {
			setPromoPriceWithTaxAmount(promoPriceWithTaxAmount);
		}

		BigDecimal discountWithTaxAmount = (BigDecimal)attributes.get(
			"discountWithTaxAmount");

		if (discountWithTaxAmount != null) {
			setDiscountWithTaxAmount(discountWithTaxAmount);
		}

		BigDecimal finalPriceWithTaxAmount = (BigDecimal)attributes.get(
			"finalPriceWithTaxAmount");

		if (finalPriceWithTaxAmount != null) {
			setFinalPriceWithTaxAmount(finalPriceWithTaxAmount);
		}

		BigDecimal discountPercentageLevel1WithTaxAmount =
			(BigDecimal)attributes.get("discountPercentageLevel1WithTaxAmount");

		if (discountPercentageLevel1WithTaxAmount != null) {
			setDiscountPercentageLevel1WithTaxAmount(
				discountPercentageLevel1WithTaxAmount);
		}

		BigDecimal discountPercentageLevel2WithTaxAmount =
			(BigDecimal)attributes.get("discountPercentageLevel2WithTaxAmount");

		if (discountPercentageLevel2WithTaxAmount != null) {
			setDiscountPercentageLevel2WithTaxAmount(
				discountPercentageLevel2WithTaxAmount);
		}

		BigDecimal discountPercentageLevel3WithTaxAmount =
			(BigDecimal)attributes.get("discountPercentageLevel3WithTaxAmount");

		if (discountPercentageLevel3WithTaxAmount != null) {
			setDiscountPercentageLevel3WithTaxAmount(
				discountPercentageLevel3WithTaxAmount);
		}

		BigDecimal discountPercentageLevel4WithTaxAmount =
			(BigDecimal)attributes.get("discountPercentageLevel4WithTaxAmount");

		if (discountPercentageLevel4WithTaxAmount != null) {
			setDiscountPercentageLevel4WithTaxAmount(
				discountPercentageLevel4WithTaxAmount);
		}

		Boolean subscription = (Boolean)attributes.get("subscription");

		if (subscription != null) {
			setSubscription(subscription);
		}

		String deliveryGroup = (String)attributes.get("deliveryGroup");

		if (deliveryGroup != null) {
			setDeliveryGroup(deliveryGroup);
		}

		Long shippingAddressId = (Long)attributes.get("shippingAddressId");

		if (shippingAddressId != null) {
			setShippingAddressId(shippingAddressId);
		}

		String printedNote = (String)attributes.get("printedNote");

		if (printedNote != null) {
			setPrintedNote(printedNote);
		}

		Date requestedDeliveryDate = (Date)attributes.get(
			"requestedDeliveryDate");

		if (requestedDeliveryDate != null) {
			setRequestedDeliveryDate(requestedDeliveryDate);
		}

		Long bookedQuantityId = (Long)attributes.get("bookedQuantityId");

		if (bookedQuantityId != null) {
			setBookedQuantityId(bookedQuantityId);
		}

		Boolean manuallyAdjusted = (Boolean)attributes.get("manuallyAdjusted");

		if (manuallyAdjusted != null) {
			setManuallyAdjusted(manuallyAdjusted);
		}
	}

	@Override
	public Object clone() {
		return new CommerceOrderItemWrapper(
			(CommerceOrderItem)_commerceOrderItem.clone());
	}

	@Override
	public int compareTo(CommerceOrderItem commerceOrderItem) {
		return _commerceOrderItem.compareTo(commerceOrderItem);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance fetchCPInstance() {
		return _commerceOrderItem.fetchCPInstance();
	}

	@Override
	public com.liferay.commerce.product.model.CProduct fetchCProduct() {
		return _commerceOrderItem.fetchCProduct();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _commerceOrderItem.getAvailableLanguageIds();
	}

	/**
	 * Returns the booked quantity ID of this commerce order item.
	 *
	 * @return the booked quantity ID of this commerce order item
	 */
	@Override
	public long getBookedQuantityId() {
		return _commerceOrderItem.getBookedQuantityId();
	}

	@Override
	public java.util.List<CommerceOrderItem> getChildCommerceOrderItems() {
		return _commerceOrderItem.getChildCommerceOrderItems();
	}

	@Override
	public CommerceOrder getCommerceOrder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getCommerceOrder();
	}

	/**
	 * Returns the commerce order ID of this commerce order item.
	 *
	 * @return the commerce order ID of this commerce order item
	 */
	@Override
	public long getCommerceOrderId() {
		return _commerceOrderItem.getCommerceOrderId();
	}

	/**
	 * Returns the commerce order item ID of this commerce order item.
	 *
	 * @return the commerce order item ID of this commerce order item
	 */
	@Override
	public long getCommerceOrderItemId() {
		return _commerceOrderItem.getCommerceOrderItemId();
	}

	/**
	 * Returns the commerce price list ID of this commerce order item.
	 *
	 * @return the commerce price list ID of this commerce order item
	 */
	@Override
	public long getCommercePriceListId() {
		return _commerceOrderItem.getCommercePriceListId();
	}

	/**
	 * Returns the company ID of this commerce order item.
	 *
	 * @return the company ID of this commerce order item
	 */
	@Override
	public long getCompanyId() {
		return _commerceOrderItem.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getCPDefinition();
	}

	@Override
	public long getCPDefinitionId() {
		return _commerceOrderItem.getCPDefinitionId();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.product.model.CPInstance getCPInstance()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getCPInstance();
	}

	/**
	 * Returns the cp instance ID of this commerce order item.
	 *
	 * @return the cp instance ID of this commerce order item
	 */
	@Override
	public long getCPInstanceId() {
		return _commerceOrderItem.getCPInstanceId();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.product.model.CProduct getCProduct()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getCProduct();
	}

	/**
	 * Returns the c product ID of this commerce order item.
	 *
	 * @return the c product ID of this commerce order item
	 */
	@Override
	public long getCProductId() {
		return _commerceOrderItem.getCProductId();
	}

	/**
	 * Returns the create date of this commerce order item.
	 *
	 * @return the create date of this commerce order item
	 */
	@Override
	public Date getCreateDate() {
		return _commerceOrderItem.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _commerceOrderItem.getDefaultLanguageId();
	}

	/**
	 * Returns the delivery group of this commerce order item.
	 *
	 * @return the delivery group of this commerce order item
	 */
	@Override
	public String getDeliveryGroup() {
		return _commerceOrderItem.getDeliveryGroup();
	}

	/**
	 * Returns the discount amount of this commerce order item.
	 *
	 * @return the discount amount of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountAmount() {
		return _commerceOrderItem.getDiscountAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getDiscountAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getDiscountAmountMoney();
	}

	/**
	 * Returns the discount percentage level1 of this commerce order item.
	 *
	 * @return the discount percentage level1 of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountPercentageLevel1() {
		return _commerceOrderItem.getDiscountPercentageLevel1();
	}

	/**
	 * Returns the discount percentage level1 with tax amount of this commerce order item.
	 *
	 * @return the discount percentage level1 with tax amount of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountPercentageLevel1WithTaxAmount() {
		return _commerceOrderItem.getDiscountPercentageLevel1WithTaxAmount();
	}

	/**
	 * Returns the discount percentage level2 of this commerce order item.
	 *
	 * @return the discount percentage level2 of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountPercentageLevel2() {
		return _commerceOrderItem.getDiscountPercentageLevel2();
	}

	/**
	 * Returns the discount percentage level2 with tax amount of this commerce order item.
	 *
	 * @return the discount percentage level2 with tax amount of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountPercentageLevel2WithTaxAmount() {
		return _commerceOrderItem.getDiscountPercentageLevel2WithTaxAmount();
	}

	/**
	 * Returns the discount percentage level3 of this commerce order item.
	 *
	 * @return the discount percentage level3 of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountPercentageLevel3() {
		return _commerceOrderItem.getDiscountPercentageLevel3();
	}

	/**
	 * Returns the discount percentage level3 with tax amount of this commerce order item.
	 *
	 * @return the discount percentage level3 with tax amount of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountPercentageLevel3WithTaxAmount() {
		return _commerceOrderItem.getDiscountPercentageLevel3WithTaxAmount();
	}

	/**
	 * Returns the discount percentage level4 of this commerce order item.
	 *
	 * @return the discount percentage level4 of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountPercentageLevel4() {
		return _commerceOrderItem.getDiscountPercentageLevel4();
	}

	/**
	 * Returns the discount percentage level4 with tax amount of this commerce order item.
	 *
	 * @return the discount percentage level4 with tax amount of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountPercentageLevel4WithTaxAmount() {
		return _commerceOrderItem.getDiscountPercentageLevel4WithTaxAmount();
	}

	/**
	 * Returns the discount with tax amount of this commerce order item.
	 *
	 * @return the discount with tax amount of this commerce order item
	 */
	@Override
	public BigDecimal getDiscountWithTaxAmount() {
		return _commerceOrderItem.getDiscountWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getDiscountWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getDiscountWithTaxAmountMoney();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceOrderItem.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this commerce order item.
	 *
	 * @return the external reference code of this commerce order item
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commerceOrderItem.getExternalReferenceCode();
	}

	/**
	 * Returns the final price of this commerce order item.
	 *
	 * @return the final price of this commerce order item
	 */
	@Override
	public BigDecimal getFinalPrice() {
		return _commerceOrderItem.getFinalPrice();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getFinalPriceMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getFinalPriceMoney();
	}

	/**
	 * Returns the final price with tax amount of this commerce order item.
	 *
	 * @return the final price with tax amount of this commerce order item
	 */
	@Override
	public BigDecimal getFinalPriceWithTaxAmount() {
		return _commerceOrderItem.getFinalPriceWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getFinalPriceWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getFinalPriceWithTaxAmountMoney();
	}

	/**
	 * Returns the group ID of this commerce order item.
	 *
	 * @return the group ID of this commerce order item
	 */
	@Override
	public long getGroupId() {
		return _commerceOrderItem.getGroupId();
	}

	/**
	 * Returns the json of this commerce order item.
	 *
	 * @return the json of this commerce order item
	 */
	@Override
	public String getJson() {
		return _commerceOrderItem.getJson();
	}

	/**
	 * Returns the manually adjusted of this commerce order item.
	 *
	 * @return the manually adjusted of this commerce order item
	 */
	@Override
	public boolean getManuallyAdjusted() {
		return _commerceOrderItem.getManuallyAdjusted();
	}

	/**
	 * Returns the modified date of this commerce order item.
	 *
	 * @return the modified date of this commerce order item
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceOrderItem.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce order item.
	 *
	 * @return the name of this commerce order item
	 */
	@Override
	public String getName() {
		return _commerceOrderItem.getName();
	}

	/**
	 * Returns the localized name of this commerce order item in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this commerce order item
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return _commerceOrderItem.getName(locale);
	}

	/**
	 * Returns the localized name of this commerce order item in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this commerce order item. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceOrderItem.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this commerce order item in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this commerce order item
	 */
	@Override
	public String getName(String languageId) {
		return _commerceOrderItem.getName(languageId);
	}

	/**
	 * Returns the localized name of this commerce order item in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this commerce order item
	 */
	@Override
	public String getName(String languageId, boolean useDefault) {
		return _commerceOrderItem.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _commerceOrderItem.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _commerceOrderItem.getNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized names of this commerce order item.
	 *
	 * @return the locales and localized names of this commerce order item
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _commerceOrderItem.getNameMap();
	}

	@Override
	public long getParentCommerceOrderItemCPDefinitionId() {
		return _commerceOrderItem.getParentCommerceOrderItemCPDefinitionId();
	}

	/**
	 * Returns the parent commerce order item ID of this commerce order item.
	 *
	 * @return the parent commerce order item ID of this commerce order item
	 */
	@Override
	public long getParentCommerceOrderItemId() {
		return _commerceOrderItem.getParentCommerceOrderItemId();
	}

	/**
	 * Returns the primary key of this commerce order item.
	 *
	 * @return the primary key of this commerce order item
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceOrderItem.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceOrderItem.getPrimaryKeyObj();
	}

	/**
	 * Returns the printed note of this commerce order item.
	 *
	 * @return the printed note of this commerce order item
	 */
	@Override
	public String getPrintedNote() {
		return _commerceOrderItem.getPrintedNote();
	}

	/**
	 * Returns the promo price of this commerce order item.
	 *
	 * @return the promo price of this commerce order item
	 */
	@Override
	public BigDecimal getPromoPrice() {
		return _commerceOrderItem.getPromoPrice();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getPromoPriceMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getPromoPriceMoney();
	}

	/**
	 * Returns the promo price with tax amount of this commerce order item.
	 *
	 * @return the promo price with tax amount of this commerce order item
	 */
	@Override
	public BigDecimal getPromoPriceWithTaxAmount() {
		return _commerceOrderItem.getPromoPriceWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getPromoPriceWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getPromoPriceWithTaxAmountMoney();
	}

	/**
	 * Returns the quantity of this commerce order item.
	 *
	 * @return the quantity of this commerce order item
	 */
	@Override
	public int getQuantity() {
		return _commerceOrderItem.getQuantity();
	}

	/**
	 * Returns the requested delivery date of this commerce order item.
	 *
	 * @return the requested delivery date of this commerce order item
	 */
	@Override
	public Date getRequestedDeliveryDate() {
		return _commerceOrderItem.getRequestedDeliveryDate();
	}

	/**
	 * Returns the shipped quantity of this commerce order item.
	 *
	 * @return the shipped quantity of this commerce order item
	 */
	@Override
	public int getShippedQuantity() {
		return _commerceOrderItem.getShippedQuantity();
	}

	/**
	 * Returns the shipping address ID of this commerce order item.
	 *
	 * @return the shipping address ID of this commerce order item
	 */
	@Override
	public long getShippingAddressId() {
		return _commerceOrderItem.getShippingAddressId();
	}

	/**
	 * Returns the sku of this commerce order item.
	 *
	 * @return the sku of this commerce order item
	 */
	@Override
	public String getSku() {
		return _commerceOrderItem.getSku();
	}

	/**
	 * Returns the subscription of this commerce order item.
	 *
	 * @return the subscription of this commerce order item
	 */
	@Override
	public boolean getSubscription() {
		return _commerceOrderItem.getSubscription();
	}

	/**
	 * Returns the unit price of this commerce order item.
	 *
	 * @return the unit price of this commerce order item
	 */
	@Override
	public BigDecimal getUnitPrice() {
		return _commerceOrderItem.getUnitPrice();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney getUnitPriceMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getUnitPriceMoney();
	}

	/**
	 * Returns the unit price with tax amount of this commerce order item.
	 *
	 * @return the unit price with tax amount of this commerce order item
	 */
	@Override
	public BigDecimal getUnitPriceWithTaxAmount() {
		return _commerceOrderItem.getUnitPriceWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getUnitPriceWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderItem.getUnitPriceWithTaxAmountMoney();
	}

	/**
	 * Returns the user ID of this commerce order item.
	 *
	 * @return the user ID of this commerce order item
	 */
	@Override
	public long getUserId() {
		return _commerceOrderItem.getUserId();
	}

	/**
	 * Returns the user name of this commerce order item.
	 *
	 * @return the user name of this commerce order item
	 */
	@Override
	public String getUserName() {
		return _commerceOrderItem.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce order item.
	 *
	 * @return the user uuid of this commerce order item
	 */
	@Override
	public String getUserUuid() {
		return _commerceOrderItem.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceOrderItem.hashCode();
	}

	@Override
	public boolean hasParentCommerceOrderItem() {
		return _commerceOrderItem.hasParentCommerceOrderItem();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceOrderItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceOrderItem.isEscapedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce order item is manually adjusted.
	 *
	 * @return <code>true</code> if this commerce order item is manually adjusted; <code>false</code> otherwise
	 */
	@Override
	public boolean isManuallyAdjusted() {
		return _commerceOrderItem.isManuallyAdjusted();
	}

	@Override
	public boolean isNew() {
		return _commerceOrderItem.isNew();
	}

	/**
	 * Returns <code>true</code> if this commerce order item is subscription.
	 *
	 * @return <code>true</code> if this commerce order item is subscription; <code>false</code> otherwise
	 */
	@Override
	public boolean isSubscription() {
		return _commerceOrderItem.isSubscription();
	}

	@Override
	public void persist() {
		_commerceOrderItem.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		_commerceOrderItem.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		_commerceOrderItem.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets the booked quantity ID of this commerce order item.
	 *
	 * @param bookedQuantityId the booked quantity ID of this commerce order item
	 */
	@Override
	public void setBookedQuantityId(long bookedQuantityId) {
		_commerceOrderItem.setBookedQuantityId(bookedQuantityId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceOrderItem.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce order ID of this commerce order item.
	 *
	 * @param commerceOrderId the commerce order ID of this commerce order item
	 */
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderItem.setCommerceOrderId(commerceOrderId);
	}

	/**
	 * Sets the commerce order item ID of this commerce order item.
	 *
	 * @param commerceOrderItemId the commerce order item ID of this commerce order item
	 */
	@Override
	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceOrderItem.setCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Sets the commerce price list ID of this commerce order item.
	 *
	 * @param commercePriceListId the commerce price list ID of this commerce order item
	 */
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commerceOrderItem.setCommercePriceListId(commercePriceListId);
	}

	/**
	 * Sets the company ID of this commerce order item.
	 *
	 * @param companyId the company ID of this commerce order item
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceOrderItem.setCompanyId(companyId);
	}

	/**
	 * Sets the cp instance ID of this commerce order item.
	 *
	 * @param CPInstanceId the cp instance ID of this commerce order item
	 */
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		_commerceOrderItem.setCPInstanceId(CPInstanceId);
	}

	/**
	 * Sets the c product ID of this commerce order item.
	 *
	 * @param CProductId the c product ID of this commerce order item
	 */
	@Override
	public void setCProductId(long CProductId) {
		_commerceOrderItem.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this commerce order item.
	 *
	 * @param createDate the create date of this commerce order item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceOrderItem.setCreateDate(createDate);
	}

	/**
	 * Sets the delivery group of this commerce order item.
	 *
	 * @param deliveryGroup the delivery group of this commerce order item
	 */
	@Override
	public void setDeliveryGroup(String deliveryGroup) {
		_commerceOrderItem.setDeliveryGroup(deliveryGroup);
	}

	/**
	 * Sets the discount amount of this commerce order item.
	 *
	 * @param discountAmount the discount amount of this commerce order item
	 */
	@Override
	public void setDiscountAmount(BigDecimal discountAmount) {
		_commerceOrderItem.setDiscountAmount(discountAmount);
	}

	/**
	 * Sets the discount percentage level1 of this commerce order item.
	 *
	 * @param discountPercentageLevel1 the discount percentage level1 of this commerce order item
	 */
	@Override
	public void setDiscountPercentageLevel1(
		BigDecimal discountPercentageLevel1) {

		_commerceOrderItem.setDiscountPercentageLevel1(
			discountPercentageLevel1);
	}

	/**
	 * Sets the discount percentage level1 with tax amount of this commerce order item.
	 *
	 * @param discountPercentageLevel1WithTaxAmount the discount percentage level1 with tax amount of this commerce order item
	 */
	@Override
	public void setDiscountPercentageLevel1WithTaxAmount(
		BigDecimal discountPercentageLevel1WithTaxAmount) {

		_commerceOrderItem.setDiscountPercentageLevel1WithTaxAmount(
			discountPercentageLevel1WithTaxAmount);
	}

	/**
	 * Sets the discount percentage level2 of this commerce order item.
	 *
	 * @param discountPercentageLevel2 the discount percentage level2 of this commerce order item
	 */
	@Override
	public void setDiscountPercentageLevel2(
		BigDecimal discountPercentageLevel2) {

		_commerceOrderItem.setDiscountPercentageLevel2(
			discountPercentageLevel2);
	}

	/**
	 * Sets the discount percentage level2 with tax amount of this commerce order item.
	 *
	 * @param discountPercentageLevel2WithTaxAmount the discount percentage level2 with tax amount of this commerce order item
	 */
	@Override
	public void setDiscountPercentageLevel2WithTaxAmount(
		BigDecimal discountPercentageLevel2WithTaxAmount) {

		_commerceOrderItem.setDiscountPercentageLevel2WithTaxAmount(
			discountPercentageLevel2WithTaxAmount);
	}

	/**
	 * Sets the discount percentage level3 of this commerce order item.
	 *
	 * @param discountPercentageLevel3 the discount percentage level3 of this commerce order item
	 */
	@Override
	public void setDiscountPercentageLevel3(
		BigDecimal discountPercentageLevel3) {

		_commerceOrderItem.setDiscountPercentageLevel3(
			discountPercentageLevel3);
	}

	/**
	 * Sets the discount percentage level3 with tax amount of this commerce order item.
	 *
	 * @param discountPercentageLevel3WithTaxAmount the discount percentage level3 with tax amount of this commerce order item
	 */
	@Override
	public void setDiscountPercentageLevel3WithTaxAmount(
		BigDecimal discountPercentageLevel3WithTaxAmount) {

		_commerceOrderItem.setDiscountPercentageLevel3WithTaxAmount(
			discountPercentageLevel3WithTaxAmount);
	}

	/**
	 * Sets the discount percentage level4 of this commerce order item.
	 *
	 * @param discountPercentageLevel4 the discount percentage level4 of this commerce order item
	 */
	@Override
	public void setDiscountPercentageLevel4(
		BigDecimal discountPercentageLevel4) {

		_commerceOrderItem.setDiscountPercentageLevel4(
			discountPercentageLevel4);
	}

	/**
	 * Sets the discount percentage level4 with tax amount of this commerce order item.
	 *
	 * @param discountPercentageLevel4WithTaxAmount the discount percentage level4 with tax amount of this commerce order item
	 */
	@Override
	public void setDiscountPercentageLevel4WithTaxAmount(
		BigDecimal discountPercentageLevel4WithTaxAmount) {

		_commerceOrderItem.setDiscountPercentageLevel4WithTaxAmount(
			discountPercentageLevel4WithTaxAmount);
	}

	/**
	 * Sets the discount with tax amount of this commerce order item.
	 *
	 * @param discountWithTaxAmount the discount with tax amount of this commerce order item
	 */
	@Override
	public void setDiscountWithTaxAmount(BigDecimal discountWithTaxAmount) {
		_commerceOrderItem.setDiscountWithTaxAmount(discountWithTaxAmount);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceOrderItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceOrderItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceOrderItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this commerce order item.
	 *
	 * @param externalReferenceCode the external reference code of this commerce order item
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commerceOrderItem.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the final price of this commerce order item.
	 *
	 * @param finalPrice the final price of this commerce order item
	 */
	@Override
	public void setFinalPrice(BigDecimal finalPrice) {
		_commerceOrderItem.setFinalPrice(finalPrice);
	}

	/**
	 * Sets the final price with tax amount of this commerce order item.
	 *
	 * @param finalPriceWithTaxAmount the final price with tax amount of this commerce order item
	 */
	@Override
	public void setFinalPriceWithTaxAmount(BigDecimal finalPriceWithTaxAmount) {
		_commerceOrderItem.setFinalPriceWithTaxAmount(finalPriceWithTaxAmount);
	}

	/**
	 * Sets the group ID of this commerce order item.
	 *
	 * @param groupId the group ID of this commerce order item
	 */
	@Override
	public void setGroupId(long groupId) {
		_commerceOrderItem.setGroupId(groupId);
	}

	/**
	 * Sets the json of this commerce order item.
	 *
	 * @param json the json of this commerce order item
	 */
	@Override
	public void setJson(String json) {
		_commerceOrderItem.setJson(json);
	}

	/**
	 * Sets whether this commerce order item is manually adjusted.
	 *
	 * @param manuallyAdjusted the manually adjusted of this commerce order item
	 */
	@Override
	public void setManuallyAdjusted(boolean manuallyAdjusted) {
		_commerceOrderItem.setManuallyAdjusted(manuallyAdjusted);
	}

	/**
	 * Sets the modified date of this commerce order item.
	 *
	 * @param modifiedDate the modified date of this commerce order item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceOrderItem.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce order item.
	 *
	 * @param name the name of this commerce order item
	 */
	@Override
	public void setName(String name) {
		_commerceOrderItem.setName(name);
	}

	/**
	 * Sets the localized name of this commerce order item in the language.
	 *
	 * @param name the localized name of this commerce order item
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		_commerceOrderItem.setName(name, locale);
	}

	/**
	 * Sets the localized name of this commerce order item in the language, and sets the default locale.
	 *
	 * @param name the localized name of this commerce order item
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setName(
		String name, java.util.Locale locale, java.util.Locale defaultLocale) {

		_commerceOrderItem.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_commerceOrderItem.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized names of this commerce order item from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this commerce order item
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_commerceOrderItem.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this commerce order item from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this commerce order item
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		_commerceOrderItem.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceOrderItem.setNew(n);
	}

	/**
	 * Sets the parent commerce order item ID of this commerce order item.
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID of this commerce order item
	 */
	@Override
	public void setParentCommerceOrderItemId(long parentCommerceOrderItemId) {
		_commerceOrderItem.setParentCommerceOrderItemId(
			parentCommerceOrderItemId);
	}

	/**
	 * Sets the primary key of this commerce order item.
	 *
	 * @param primaryKey the primary key of this commerce order item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceOrderItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceOrderItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the printed note of this commerce order item.
	 *
	 * @param printedNote the printed note of this commerce order item
	 */
	@Override
	public void setPrintedNote(String printedNote) {
		_commerceOrderItem.setPrintedNote(printedNote);
	}

	/**
	 * Sets the promo price of this commerce order item.
	 *
	 * @param promoPrice the promo price of this commerce order item
	 */
	@Override
	public void setPromoPrice(BigDecimal promoPrice) {
		_commerceOrderItem.setPromoPrice(promoPrice);
	}

	/**
	 * Sets the promo price with tax amount of this commerce order item.
	 *
	 * @param promoPriceWithTaxAmount the promo price with tax amount of this commerce order item
	 */
	@Override
	public void setPromoPriceWithTaxAmount(BigDecimal promoPriceWithTaxAmount) {
		_commerceOrderItem.setPromoPriceWithTaxAmount(promoPriceWithTaxAmount);
	}

	/**
	 * Sets the quantity of this commerce order item.
	 *
	 * @param quantity the quantity of this commerce order item
	 */
	@Override
	public void setQuantity(int quantity) {
		_commerceOrderItem.setQuantity(quantity);
	}

	/**
	 * Sets the requested delivery date of this commerce order item.
	 *
	 * @param requestedDeliveryDate the requested delivery date of this commerce order item
	 */
	@Override
	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		_commerceOrderItem.setRequestedDeliveryDate(requestedDeliveryDate);
	}

	/**
	 * Sets the shipped quantity of this commerce order item.
	 *
	 * @param shippedQuantity the shipped quantity of this commerce order item
	 */
	@Override
	public void setShippedQuantity(int shippedQuantity) {
		_commerceOrderItem.setShippedQuantity(shippedQuantity);
	}

	/**
	 * Sets the shipping address ID of this commerce order item.
	 *
	 * @param shippingAddressId the shipping address ID of this commerce order item
	 */
	@Override
	public void setShippingAddressId(long shippingAddressId) {
		_commerceOrderItem.setShippingAddressId(shippingAddressId);
	}

	/**
	 * Sets the sku of this commerce order item.
	 *
	 * @param sku the sku of this commerce order item
	 */
	@Override
	public void setSku(String sku) {
		_commerceOrderItem.setSku(sku);
	}

	/**
	 * Sets whether this commerce order item is subscription.
	 *
	 * @param subscription the subscription of this commerce order item
	 */
	@Override
	public void setSubscription(boolean subscription) {
		_commerceOrderItem.setSubscription(subscription);
	}

	/**
	 * Sets the unit price of this commerce order item.
	 *
	 * @param unitPrice the unit price of this commerce order item
	 */
	@Override
	public void setUnitPrice(BigDecimal unitPrice) {
		_commerceOrderItem.setUnitPrice(unitPrice);
	}

	/**
	 * Sets the unit price with tax amount of this commerce order item.
	 *
	 * @param unitPriceWithTaxAmount the unit price with tax amount of this commerce order item
	 */
	@Override
	public void setUnitPriceWithTaxAmount(BigDecimal unitPriceWithTaxAmount) {
		_commerceOrderItem.setUnitPriceWithTaxAmount(unitPriceWithTaxAmount);
	}

	/**
	 * Sets the user ID of this commerce order item.
	 *
	 * @param userId the user ID of this commerce order item
	 */
	@Override
	public void setUserId(long userId) {
		_commerceOrderItem.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce order item.
	 *
	 * @param userName the user name of this commerce order item
	 */
	@Override
	public void setUserName(String userName) {
		_commerceOrderItem.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce order item.
	 *
	 * @param userUuid the user uuid of this commerce order item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceOrderItem.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceOrderItem>
		toCacheModel() {

		return _commerceOrderItem.toCacheModel();
	}

	@Override
	public CommerceOrderItem toEscapedModel() {
		return new CommerceOrderItemWrapper(
			_commerceOrderItem.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceOrderItem.toString();
	}

	@Override
	public CommerceOrderItem toUnescapedModel() {
		return new CommerceOrderItemWrapper(
			_commerceOrderItem.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceOrderItem.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceOrderItemWrapper)) {
			return false;
		}

		CommerceOrderItemWrapper commerceOrderItemWrapper =
			(CommerceOrderItemWrapper)object;

		if (Objects.equals(
				_commerceOrderItem,
				commerceOrderItemWrapper._commerceOrderItem)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceOrderItem getWrappedModel() {
		return _commerceOrderItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceOrderItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceOrderItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceOrderItem.resetOriginalValues();
	}

	private final CommerceOrderItem _commerceOrderItem;

}