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

package com.liferay.structured.content.apio.architect.sort;

import javax.ws.rs.BadRequestException;

/**
 * This exception is used to model errors when handling {@link Sort}.
 *
 * @author Cristina González
 * @review
 */
public class InvalidSortException extends BadRequestException {

	/**
	 * Creates a new <code>InvalidSortException</code> with a message
	 *
	 * @param  msg - message of the Exception
	 * @review
	 */
	public InvalidSortException(String msg) {
		super(msg);
	}

	/**
	 * Creates a new <code>InvalidSortException</code> with a message and a
	 * cause
	 *
	 * @param  msg - message of the Exception
	 * @param  cause - cause of the Exception
	 * @review
	 */
	public InvalidSortException(String msg, Throwable cause) {
		super(msg, cause);
	}

}