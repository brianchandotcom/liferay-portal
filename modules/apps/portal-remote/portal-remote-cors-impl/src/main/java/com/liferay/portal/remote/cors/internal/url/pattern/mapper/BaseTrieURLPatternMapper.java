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

package com.liferay.portal.remote.cors.internal.url.pattern.mapper;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Carlos Sierra Andrés
 * @author Arthur Chan
 */
public abstract class BaseTrieURLPatternMapper<T>
	extends BaseURLPatternMapper<T> {

	@Override
	public T get(String urlPath) {
		try {
			T cargo = getWildcardCargo(urlPath);

			if (cargo != null) {
				return cargo;
			}

			return getExtensionCargo(urlPath);
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
			throw new IllegalArgumentException(
				"URL path contains invalid characters",
				indexOutOfBoundsException);
		}
	}

	protected abstract T getExtensionCargo(String urlPath);

	protected abstract T getWildcardCargo(String urlPath);

	@Override
	protected void put(T cargo, String urlPattern)
		throws IllegalArgumentException {

		if (cargo == null) {
			throw new IllegalArgumentException("CORS support is null");
		}

		if (Validator.isBlank(urlPattern)) {
			throw new IllegalArgumentException("URL pattern is blank");
		}

		try {
			if (isWildcardURLPattern(urlPattern)) {
				put(cargo, urlPattern, true);

				return;
			}

			if (isExtensionURLPattern(urlPattern)) {
				put(cargo, urlPattern, false);

				return;
			}

			put(cargo, urlPattern, true);
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
			throw new IllegalArgumentException(
				"URL pattern contains invalid characters",
				indexOutOfBoundsException);
		}
	}

	protected abstract void put(T cargo, String urlPattern, boolean wildcard);

	protected static final byte ASCII_CHARACTER_RANGE = 96;

	protected static final byte ASCII_PRINTABLE_OFFSET = 32;

}