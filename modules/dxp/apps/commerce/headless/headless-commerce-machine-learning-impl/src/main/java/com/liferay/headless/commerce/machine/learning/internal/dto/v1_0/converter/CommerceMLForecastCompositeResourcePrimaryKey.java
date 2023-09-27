/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

/**
 * @author Riccardo Ferrari
 */
public class CommerceMLForecastCompositeResourcePrimaryKey {

	public CommerceMLForecastCompositeResourcePrimaryKey(
		long companyId, long forecastId) {

		_companyId = companyId;
		_forecastId = forecastId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getForecastId() {
		return _forecastId;
	}

	private final long _companyId;
	private final long _forecastId;

}