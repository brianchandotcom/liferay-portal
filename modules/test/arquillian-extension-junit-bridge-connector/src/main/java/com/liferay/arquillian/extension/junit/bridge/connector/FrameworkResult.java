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

package com.liferay.arquillian.extension.junit.bridge.connector;

import java.io.Serializable;

/**
 * @author Matthew Tambara
 */
public class FrameworkResult<T extends Serializable> implements Serializable {

	public FrameworkResult(T result) {
		this(result, null);
	}

	public FrameworkResult(Throwable throwable) {
		this(null, throwable);
	}

	public T get() throws Throwable {
		if (_throwable != null) {
			throw _throwable;
		}

		return _result;
	}

	private FrameworkResult(T result, Throwable throwable) {
		_result = result;
		_throwable = throwable;
	}

	private static final long serialVersionUID = 1L;

	private final T _result;
	private final Throwable _throwable;

}