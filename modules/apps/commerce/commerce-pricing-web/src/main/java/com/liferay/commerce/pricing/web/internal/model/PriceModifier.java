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

package com.liferay.commerce.pricing.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class PriceModifier {

	public PriceModifier(
		String endDate, String modifier, String name, long priceModifierId,
		String startDate, String target) {

		_endDate = endDate;
		_modifier = modifier;
		_name = name;
		_priceModifierId = priceModifierId;
		_startDate = startDate;
		_target = target;
	}

	public String getEndDate() {
		return _endDate;
	}

	public String getModifier() {
		return _modifier;
	}

	public String getName() {
		return _name;
	}

	public long getPriceModifierId() {
		return _priceModifierId;
	}

	public String getStartDate() {
		return _startDate;
	}

	public String getTarget() {
		return _target;
	}

	private final String _endDate;
	private final String _modifier;
	private final String _name;
	private final long _priceModifierId;
	private final String _startDate;
	private final String _target;

}