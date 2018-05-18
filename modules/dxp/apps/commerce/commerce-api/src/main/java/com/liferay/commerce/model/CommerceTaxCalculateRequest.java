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

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CommerceTaxCalculateRequest {

	public Map<String, Serializable> getAttributes() {
		return _attributes;
	}

	public long getCommerceAddressId() {
		return _commerceAddressId;
	}

	public long getCommerceTaxMethodId() {
		return _commerceTaxMethodId;
	}

	public Locale getLocale() {
		return _locale;
	}

	public BigDecimal getPrice() {
		return _price;
	}

	public long getSiteGroupId() {
		return _siteGroupId;
	}

	public long getTaxCategoryId() {
		return _taxCategoryId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setAttributes(Map<String, Serializable> attributes) {
		_attributes = attributes;
	}

	public void setCommerceAddressId(long commerceAddressId) {
		_commerceAddressId = commerceAddressId;
	}

	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		_commerceTaxMethodId = commerceTaxMethodId;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	public void setSiteGroupId(long siteGroupId) {
		_siteGroupId = siteGroupId;
	}

	public void setTaxCategoryId(long taxCategoryId) {
		_taxCategoryId = taxCategoryId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private Map<String, Serializable> _attributes = new LinkedHashMap<>();
	private long _commerceAddressId;
	private long _commerceTaxMethodId;
	private Locale _locale;
	private BigDecimal _price;
	private long _siteGroupId;
	private long _taxCategoryId;
	private long _userId;

}