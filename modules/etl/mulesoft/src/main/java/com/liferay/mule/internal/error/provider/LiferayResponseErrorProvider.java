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

package com.liferay.mule.internal.error.provider;

import com.liferay.mule.internal.error.LiferayError;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

/**
 * @author Matija Petanjek
 */
public class LiferayResponseErrorProvider implements ErrorTypeProvider {

	@Override
	public Set<ErrorTypeDefinition> getErrorTypes() {
		Set<ErrorTypeDefinition> errors = new HashSet<>();

		errors.add(LiferayError.BAD_REQUEST);
		errors.add(LiferayError.NOT_ACCEPTABLE);
		errors.add(LiferayError.NOT_ALLOWED);
		errors.add(LiferayError.NOT_FOUND);
		errors.add(LiferayError.NOT_IMPLEMENTED);
		errors.add(LiferayError.SERVER_ERROR);
		errors.add(LiferayError.UNAUTHORIZED);
		errors.add(LiferayError.UNSUPPORTED_MEDIA_TYPE);

		return errors;
	}

}