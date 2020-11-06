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

package com.liferay.petra.url.pattern.mapper.internal;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Carlos Sierra Andrés
 * @author Arthur Chan
 */
public abstract class BaseTrieURLPatternMapper<T>
	extends BaseURLPatternMapper<T> {

	@Override
	public void consumeValues(Consumer<T> consumer, String urlPath) {
		if (Objects.isNull(urlPath)) {
			return;
		}

		consumeWildcardValues(consumer, urlPath);

		T extensionValue = getExtensionValue(urlPath);

		if (extensionValue != null) {
			consumer.accept(extensionValue);
		}
	}

	@Override
	public T getValue(String urlPath) {
		if (Objects.isNull(urlPath)) {
			return null;
		}

		try {
			T value = getWildcardValue(urlPath);

			if (Objects.nonNull(value)) {
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

	protected abstract void consumeWildcardValues(
		Consumer<T> consumer, String urlPath);

	protected abstract T getExtensionValue(String urlPath);

	protected abstract T getWildcardValue(String urlPath);

	@Override
	protected void put(String urlPattern, T value)
		throws IllegalArgumentException {

		if (Objects.isNull(urlPattern) || (urlPattern.length() == 0)) {
			throw new IllegalArgumentException("URL pattern is blank");
		}

		if (Objects.isNull(value)) {
			throw new IllegalArgumentException("Value is null");
		}

		try {
			if (isWildcardURLPattern(urlPattern)) {
				put(urlPattern, value, true);

				return;
			}

			if (isExtensionURLPattern(urlPattern)) {
				put(urlPattern, value, false);

				return;
			}

			put(urlPattern, value, true);
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
			throw new IllegalArgumentException(
				"URL pattern contains invalid characters",
				indexOutOfBoundsException);
		}
	}

	protected abstract void put(String urlPattern, T value, boolean wildcard);

	protected static final byte ASCII_CHARACTER_RANGE = 96;

	protected static final byte ASCII_PRINTABLE_OFFSET = 32;

}