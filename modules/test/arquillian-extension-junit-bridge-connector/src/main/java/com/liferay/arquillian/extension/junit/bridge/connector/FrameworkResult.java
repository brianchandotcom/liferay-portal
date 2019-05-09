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
public class FrameworkResult implements Serializable {

	public long getBundleId() {
		return _bundleId;
	}

	public Exception getException() {
		return _exception;
	}

	public void setBundleId(long bundleId) {
		_bundleId = bundleId;
	}

	public void setException(Exception exceptions) {
		_exception = exceptions;
	}

	private static final long serialVersionUID = 1L;

	private long _bundleId;
	private Exception _exception;

}