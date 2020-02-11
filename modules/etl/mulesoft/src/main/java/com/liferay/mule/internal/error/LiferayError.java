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

package com.liferay.mule.internal.error;

import java.util.Optional;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

/**
 * @author Matija Petanjek
 */
public enum LiferayError implements ErrorTypeDefinition<LiferayError> {

	BAD_REQUEST(MuleErrors.CONNECTIVITY, 400),
	NOT_ACCEPTABLE(MuleErrors.CONNECTIVITY, 406),
	NOT_ALLOWED(MuleErrors.CONNECTIVITY, 405),
	NOT_FOUND(MuleErrors.CONNECTIVITY, 404),
	NOT_IMPLEMENTED(MuleErrors.CONNECTIVITY, 501),
	SERVER_ERROR(MuleErrors.CONNECTIVITY, 500),
	UNAUTHORIZED(MuleErrors.CONNECTIVITY, 401),
	UNSUPPORTED_MEDIA_TYPE(MuleErrors.CONNECTIVITY, 415);

	public static LiferayError fromStatus(int status) {
		for (LiferayError liferayError : values()) {
			if (liferayError._status == status) {
				return liferayError;
			}
		}

		throw new IllegalArgumentException(
			"No error defined for status: " + status);
	}

	@Override
	public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
		return Optional.ofNullable(_parent);
	}

	private LiferayError(ErrorTypeDefinition parent, int status) {
		_parent = parent;
		_status = status;
	}

	private final ErrorTypeDefinition _parent;
	private int _status;

}