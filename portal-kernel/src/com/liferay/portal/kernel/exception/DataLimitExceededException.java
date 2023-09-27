/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Javier de Arcos
 */
public class DataLimitExceededException extends SystemException {

	public DataLimitExceededException() {
	}

	public DataLimitExceededException(String msg) {
		super(msg);
	}

	public DataLimitExceededException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DataLimitExceededException(Throwable throwable) {
		super(throwable);
	}

}