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

package com.liferay.apio.architect.error.internal.converter;

import com.liferay.apio.architect.error.APIError;

import java.util.Optional;

/**
 * @author Alejandro Hernández
 */
public class APIErrorImpl implements APIError {

	public APIErrorImpl(
		Exception exception, String title, String type, int statusCode) {

		_exception = exception;
		_title = title;
		_type = type;
		_statusCode = statusCode;
	}

	public APIErrorImpl(
		Exception exception, String title, String description, String type,
		int statusCode) {

		_exception = exception;
		_title = title;
		_description = description;
		_type = type;
		_statusCode = statusCode;
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.ofNullable(_description);
	}

	@Override
	public Exception getException() {
		return _exception;
	}

	@Override
	public int getStatusCode() {
		return _statusCode;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public String getType() {
		return _type;
	}

	private String _description;
	private final Exception _exception;
	private final int _statusCode;
	private final String _title;
	private final String _type;

}