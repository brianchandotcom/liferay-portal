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
			T value = getWildcardValue(urlPath);

			if (value != null) {
				return value;
			}

			return getExtensionValue(urlPath);
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
			throw new IllegalArgumentException(
				"URL path contains invalid characters",
				indexOutOfBoundsException);
		}
	}

	protected abstract T getExtensionValue(String urlPath);

	protected abstract T getWildcardValue(String urlPath);

	@Override
	protected void put(T value, String urlPattern)
		throws IllegalArgumentException {

		if (value == null) {
			throw new IllegalArgumentException("Value is null");
		}

		if (Validator.isBlank(urlPattern)) {
			throw new IllegalArgumentException("URL pattern is blank");
		}

		try {
			if (isWildcardURLPattern(urlPattern)) {
				put(value, urlPattern, true);

				return;
			}

			if (isExtensionURLPattern(urlPattern)) {
				put(value, urlPattern, false);

				return;
			}

			put(value, urlPattern, true);
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
			throw new IllegalArgumentException(
				"URL pattern contains invalid characters",
				indexOutOfBoundsException);
		}
	}

	protected abstract void put(T value, String urlPattern, boolean wildcard);

	protected static final byte ASCII_CHARACTER_RANGE = 96;

	protected static final byte ASCII_PRINTABLE_OFFSET = 32;

}