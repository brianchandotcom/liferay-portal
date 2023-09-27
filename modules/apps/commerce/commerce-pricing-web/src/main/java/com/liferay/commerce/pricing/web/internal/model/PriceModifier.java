/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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