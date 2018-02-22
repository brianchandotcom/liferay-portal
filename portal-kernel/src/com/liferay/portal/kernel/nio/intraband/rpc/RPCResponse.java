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

package com.liferay.portal.kernel.nio.intraband.rpc;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class RPCResponse implements Serializable {

	public RPCResponse(Exception exception) {
		_exception = exception;

		_result = null;
	}

	public RPCResponse(Serializable result) {
		_result = result;

		_exception = null;
	}

	public Exception getException() {
		return _exception;
	}

	public Serializable getResult() {
		return _result;
	}

	private static final long serialVersionUID = 1L;

	private final Exception _exception;
	private final Serializable _result;

}