/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.guard.connector;

import java.io.Serializable;

/**
 * @author Matthew Tambara
 */
public class DataGuardResult implements Serializable {

	public DataGuardResult(long result) {
		this(result, null);
	}

	public DataGuardResult(Throwable throwable) {
		this(0, throwable);
	}

	public long get() throws Exception {
		if (_exception != null) {
			throw _exception;
		}

		return _result;
	}

	private DataGuardResult(long result, Throwable throwable) {
		_result = result;

		if (throwable != null) {
			Class<? extends Throwable> clazz = throwable.getClass();

			Exception exception = new Exception(
				clazz.getName() + ": " + throwable.getMessage());

			exception.setStackTrace(throwable.getStackTrace());

			_exception = exception;
		}
		else {
			_exception = null;
		}
	}

	private static final long serialVersionUID = 1L;

	private final Exception _exception;
	private final long _result;

}