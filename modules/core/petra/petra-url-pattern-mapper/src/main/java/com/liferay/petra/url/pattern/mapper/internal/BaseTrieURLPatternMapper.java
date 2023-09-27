/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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