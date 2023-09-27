/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.manager.exception;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryManagerHttpException extends RuntimeException {

	public ObjectEntryManagerHttpException() {
	}

	public ObjectEntryManagerHttpException(String msg) {
		super(msg);
	}

	public ObjectEntryManagerHttpException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectEntryManagerHttpException(Throwable throwable) {
		super(throwable);
	}

}