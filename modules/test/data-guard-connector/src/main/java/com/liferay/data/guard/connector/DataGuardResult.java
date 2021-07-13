/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	public long get() throws Throwable {
		if (_throwable != null) {
			throw _throwable;
		}

		return _result;
	}

	private DataGuardResult(long result, Throwable throwable) {
		_result = result;

		if (throwable != null) {
			Class<? extends Throwable> clazz = throwable.getClass();

			Throwable simpleThrowable = new Throwable(
				clazz.getName() + ": " + throwable.getMessage());

			simpleThrowable.setStackTrace(throwable.getStackTrace());

			_throwable = simpleThrowable;
		}
		else {
			_throwable = null;
		}
	}

	private static final long serialVersionUID = 1L;

	private final long _result;
	private final Throwable _throwable;

}