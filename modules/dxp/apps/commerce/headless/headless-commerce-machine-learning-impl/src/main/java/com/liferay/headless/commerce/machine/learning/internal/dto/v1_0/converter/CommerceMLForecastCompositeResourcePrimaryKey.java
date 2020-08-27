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