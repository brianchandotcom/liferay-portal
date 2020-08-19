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

package com.liferay.portal.odata.filter;

import javax.ws.rs.BadRequestException;

/**
 * Models errors that occur when handling {@link Filter} objects.
 *
 * @author David Arques
 * @review
 */
public class InvalidFilterException extends BadRequestException {

	/**
	 * Creates a new {@code InvalidFilterException} with a message.
	 *
	 * @param  msg the message
	 * @review
	 */
	public InvalidFilterException(String msg) {
		super(msg);
	}

	/**
	 * Creates a new {@code InvalidFilterException} with a message and the
	 * throwable of the exception.
	 *
	 * @param  msg the message
	 * @param  throwable the throwable
	 * @review
	 */
	public InvalidFilterException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}