/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.exception;

/**
 * @author Matthew Kong
 */
public class FaroEngineClientException extends RuntimeException {

	public FaroEngineClientException() {
	}

	public FaroEngineClientException(String msg) {
		super(msg);
	}

	public FaroEngineClientException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public FaroEngineClientException(Throwable throwable) {
		super(throwable);
	}

}