/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.validator;

/**
 * @author Rubén Pulido
 */
public class JSONValidatorException extends Exception {

	public JSONValidatorException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public JSONValidatorException(Throwable throwable) {
		super(throwable);
	}

}