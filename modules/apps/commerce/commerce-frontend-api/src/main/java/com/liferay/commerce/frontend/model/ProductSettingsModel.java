/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public class ProductSettingsModel {

	public BigDecimal[] getAllowedQuantities() {
		return _allowedQuantities;
	}

	public BigDecimal getLowStockQuantity() {
		return _lowStockQuantity;
	}

	public BigDecimal getMaxQuantity() {
		return _maxQuantity;
	}

	public BigDecimal getMinQuantity() {
		return _minQuantity;
	}

	public BigDecimal getMultipleQuantity() {
		return _multipleQuantity;
	}

	public boolean isBackOrders() {
		return _backOrders;
	}

	public boolean isShowAvailabilityDot() {
		return _showAvailabilityDot;
	}

	public void setAllowedQuantities(BigDecimal[] allowedQuantities) {
		_allowedQuantities = allowedQuantities;
	}

	public void setBackOrders(boolean backOrders) {
		_backOrders = backOrders;
	}

	public void setLowStockQuantity(BigDecimal lowStockQuantity) {
		_lowStockQuantity = lowStockQuantity;
	}

	public void setMaxQuantity(BigDecimal maxQuantity) {
		_maxQuantity = maxQuantity;
	}

	public void setMinQuantity(BigDecimal minQuantity) {
		_minQuantity = minQuantity;
	}

	public void setMultipleQuantity(BigDecimal multipleQuantity) {
		_multipleQuantity = multipleQuantity;
	}

	public void setShowAvailabilityDot(boolean showAvailabilityDot) {
		_showAvailabilityDot = showAvailabilityDot;
	}

	private BigDecimal[] _allowedQuantities;
	private boolean _backOrders;
	private BigDecimal _lowStockQuantity;
	private BigDecimal _maxQuantity;
	private BigDecimal _minQuantity;
	private BigDecimal _multipleQuantity;
	private boolean _showAvailabilityDot;

}