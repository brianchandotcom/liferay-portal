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

package com.liferay.apio.architect.test.util.result;

import com.liferay.apio.architect.error.APIError;

import java.util.Optional;

/**
 * Represents a mock {@link APIError} that can be used to test an {@link
 * com.liferay.apio.architect.message.json.ErrorMessageMapper}.
 *
 * @author Alejandro Hernández
 */
public class MockAPIError implements APIError {

	@Override
	public Optional<String> getDescription() {
		return Optional.of(getException().getMessage());
	}

	@Override
	public Exception getException() {
		return new IllegalArgumentException("A description");
	}

	@Override
	public int getStatusCode() {
		return 404;
	}

	@Override
	public String getTitle() {
		return "A title";
	}

	@Override
	public String getType() {
		return "A type";
	}

}