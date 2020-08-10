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

package com.liferay.commerce.frontend.model;

/**
 * @author Marco Leo
 */
public class ProductSettingsModel {

	public int[] getAllowedQuantities() {
		return _allowedQuantities;
	}

	public int getLowStockQuantity() {
		return _lowStockQuantity;
	}

	public int getMaxQuantity() {
		return _maxQuantity;
	}

	public int getMinQuantity() {
		return _minQuantity;
	}

	public int getMultipleQuantity() {
		return _multipleQuantity;
	}

	public boolean isShowAvailabilityDot() {
		return _showAvailabilityDot;
	}

	public void setAllowedQuantities(int[] allowedQuantities) {
		_allowedQuantities = allowedQuantities;
	}

	public void setLowStockQuantity(int lowStockQuantity) {
		_lowStockQuantity = lowStockQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		_maxQuantity = maxQuantity;
	}

	public void setMinQuantity(int minQuantity) {
		_minQuantity = minQuantity;
	}

	public void setMultipleQuantity(int multipleQuantity) {
		_multipleQuantity = multipleQuantity;
	}

	public void setShowAvailabilityDot(boolean showAvailabilityDot) {
		_showAvailabilityDot = showAvailabilityDot;
	}

	private int[] _allowedQuantities;
	private int _lowStockQuantity;
	private int _maxQuantity;
	private int _minQuantity;
	private int _multipleQuantity;
	private boolean _showAvailabilityDot;

}