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

package com.liferay.unit.converter.web.model;

/**
 * @author James Lefeu
 */
public class UnitConverter {

	public UnitConverter(int type, int fromId, int toId, double fromValue) {
		_type = type;
		_fromId = fromId;
		_toId = toId;
		_fromValue = fromValue;
	}

	public UnitConverter(
		int type, int fromId, int toId, double fromValue, double toValue) {

		_type = type;
		_fromId = fromId;
		_toId = toId;
		_fromValue = fromValue;
		_toValue = toValue;
	}

	public int getFromId() {
		return _fromId;
	}

	public double getFromValue() {
		return _fromValue;
	}

	public int getToId() {
		return _toId;
	}

	public double getToValue() {
		return _toValue;
	}

	public int getType() {
		return _type;
	}

	public void setFromValue(double fromValue) {
		_fromValue = fromValue;
	}

	public void setToValue(double toValue) {
		_toValue = toValue;
	}

	private final int _fromId;
	private double _fromValue;
	private final int _toId;
	private double _toValue;
	private final int _type;

}