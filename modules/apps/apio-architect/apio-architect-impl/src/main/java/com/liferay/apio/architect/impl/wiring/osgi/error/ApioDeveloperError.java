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

package com.liferay.apio.architect.impl.wiring.osgi.error;

import com.liferay.apio.architect.uri.Path;

/**
 * Represents the errors that can occur while using Apio. Each error is a nested
 * error subclass.
 *
 * @author Alejandro Hernández
 * @author Jorge Ferrer
 */
public class ApioDeveloperError extends Error {

	/**
	 * Represents the error the developer should throw when an identifier's path
	 * mapper is missing.
	 */
	public static class MustHavePathIdentifierMapper
		extends ApioDeveloperError {

		public MustHavePathIdentifierMapper(Path path) {
			super(path.asURI() + " path does not have a valid path mapper");
		}

	}

	private ApioDeveloperError(String message) {
		super(message);
	}

}