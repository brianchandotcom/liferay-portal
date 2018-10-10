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
 * Models {@link Sort} errors.
 *
 * @author     Cristina González
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.parser.apio.architect.sort.InvalidSortException}
 */
@Deprecated
public class InvalidSortException extends BadRequestException {

	/**
	 * Creates a new {@code InvalidSortException} with the provided message.
	 *
	 * @param msg the message
	 */
	public InvalidSortException(String msg) {
		super(msg);
	}

	/**
	 * Creates a new {@code InvalidSortException} with the provided message and
	 * cause.
	 *
	 * @param msg the message
	 * @param cause the cause
	 */
	public InvalidSortException(String msg, Throwable cause) {
		super(msg, cause);
	}

}