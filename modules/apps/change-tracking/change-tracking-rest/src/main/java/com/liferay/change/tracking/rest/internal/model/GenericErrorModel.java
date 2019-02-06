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

package com.liferay.change.tracking.rest.internal.model;

import com.liferay.petra.string.StringPool;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Máté Thurzó
 */
@XmlRootElement
public class GenericErrorModel {

	public GenericErrorModel() {
	}

	public GenericErrorModel(Exception exception) {
		Class<?> clazz = exception.getClass();

		_exception = clazz.getName();

		_message = exception.getMessage();
	}

	public GenericErrorModel(String message) {
		_exception = StringPool.BLANK;
		_message = message;
	}

	@XmlElement
	public String getException() {
		return _exception;
	}

	@XmlElement
	public String getMessage() {
		return _message;
	}

	private String _exception;
	private String _message;

}