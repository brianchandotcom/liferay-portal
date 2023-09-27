/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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