/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.forecast.constants;

/**
 * @author Riccardo Ferrari
 */
public enum CommerceMLForecastPeriod {

	MONTH("month"), WEEK("week");

	public String getLabel() {
		return _label;
	}

	private CommerceMLForecastPeriod(String label) {
		_label = label;
	}

	private final String _label;

}