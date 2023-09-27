/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.forecast.alert.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Riccardo Ferrari
 */
public class NoSuchMLForecastAlertEntryException extends NoSuchModelException {

	public NoSuchMLForecastAlertEntryException() {
	}

	public NoSuchMLForecastAlertEntryException(String msg) {
		super(msg);
	}

	public NoSuchMLForecastAlertEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchMLForecastAlertEntryException(Throwable throwable) {
		super(throwable);
	}

}