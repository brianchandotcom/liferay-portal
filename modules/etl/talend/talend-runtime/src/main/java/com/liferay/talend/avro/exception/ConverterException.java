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

package com.liferay.talend.avro.exception;

import com.liferay.talend.exception.BaseComponentException;

/**
 * @author Igor Beslic
 */
public class ConverterException extends BaseComponentException {

	public ConverterException(String message) {
		super(message, 0);
	}

	public ConverterException(String message, Throwable cause) {
		super(message, 0, cause);
	}

}