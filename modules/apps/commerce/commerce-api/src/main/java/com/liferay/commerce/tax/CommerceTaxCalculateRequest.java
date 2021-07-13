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

package com.liferay.commerce.tax;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public class CommerceTaxCalculateRequest {

	public long getCommerceBillingAddressId() {
		return _commerceBillingAddressId;
	}

	public long getCommerceChannelGroupId() {
		return _commerceChannelGroupId;
	}

	public long getCommerceShippingAddressId() {
		return _commerceShippingAddressId;
	}

	public long getCommerceTaxMethodId() {
		return _commerceTaxMethodId;
	}

	public BigDecimal getPrice() {
		return _price;
	}

	public long getTaxCategoryId() {
		return _taxCategoryId;
	}

	public boolean isIncludeTax() {
		return _includeTax;
	}

	public boolean isPercentage() {
		return _percentage;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public boolean isWithTaxAmount() {
		return _includeTax;
	}

	public void setCommerceBillingAddressId(long commerceBillingAddressId) {
		_commerceBillingAddressId = commerceBillingAddressId;
	}

	public void setCommerceChannelGroupId(long commerceChannelGroupId) {
		_commerceChannelGroupId = commerceChannelGroupId;
	}

	public void setCommerceShippingAddressId(long commerceShippingAddressId) {
		_commerceShippingAddressId = commerceShippingAddressId;
	}

	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		_commerceTaxMethodId = commerceTaxMethodId;
	}

	public void setIncludeTax(boolean includeTax) {
		_includeTax = includeTax;
	}

	public void setPercentage(boolean percentage) {
		_percentage = percentage;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	public void setTaxCategoryId(long taxCategoryId) {
		_taxCategoryId = taxCategoryId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setWithTaxAmount(boolean includeTax) {
		_includeTax = includeTax;
	}

	private long _commerceBillingAddressId;
	private long _commerceChannelGroupId;
	private long _commerceShippingAddressId;
	private long _commerceTaxMethodId;
	private boolean _includeTax;
	private boolean _percentage;
	private BigDecimal _price;
	private long _taxCategoryId;

}