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

package com.liferay.structured.content.apio.internal.architect.filter;

import javax.ws.rs.BadRequestException;

/**
 * This exception is used to model errors when handling search {@link
 * com.liferay.structured.content.apio.architect.filter.Filter}s.
 *
 * @author David Arques
 * @review
 */
public class InvalidFilterException extends BadRequestException {

	public InvalidFilterException(String msg) {
		super(msg);
	}

	public InvalidFilterException(String msg, Throwable cause) {
		super(msg, cause);
	}

}