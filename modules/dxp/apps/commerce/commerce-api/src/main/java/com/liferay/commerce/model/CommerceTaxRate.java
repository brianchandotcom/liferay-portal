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

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxRate {

	public CommerceTaxRate(String name, String label, double rate) {
		_name = name;
		_label = label;
		_rate = rate;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public double getRate() {
		return _rate;
	}

	private final String _label;
	private final String _name;
	private final double _rate;

}