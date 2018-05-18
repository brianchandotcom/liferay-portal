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