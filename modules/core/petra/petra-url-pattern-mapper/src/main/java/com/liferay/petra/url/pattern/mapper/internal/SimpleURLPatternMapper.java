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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 * @author Brian Wing Shun Chan
 */
public class SimpleURLPatternMapper<T> extends BaseURLPatternMapper<T> {

	public SimpleURLPatternMapper(Map<String, T> values) {
		for (Map.Entry<String, T> entry : values.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void consumeValues(Consumer<T> consumer, String urlPath) {
		if (Objects.isNull(urlPath)) {
			return;
		}

		T value = _exactURLPatternValues.get(urlPath);

		if (Objects.nonNull(value)) {
			consumer.accept(value);
		}

		value = _wildcardURLPatternValues.get(urlPath + "/*");

		if (Objects.nonNull(value)) {
			consumer.accept(value);
		}

		int index = 0;

		for (int i = urlPath.length(); i > 0; --i) {
			if ((index < 1) && (urlPath.charAt(i - 1) == '.')) {
				index = i - 1;
			}

			if (urlPath.charAt(i - 1) != '/') {
				continue;
			}

			value = _wildcardURLPatternValues.get(
				urlPath.substring(0, i) + "*");

			if (Objects.nonNull(value)) {
				consumer.accept(value);
			}
		}

		value = _extensionURLPatternValues.get("*" + urlPath.substring(index));

		if (Objects.nonNull(value)) {
			consumer.accept(value);
		}
	}

	@Override
	public T getValue(String urlPath) {
		if (Objects.isNull(urlPath)) {
			return null;
		}

		T value = _exactURLPatternValues.get(urlPath);

		if (Objects.nonNull(value)) {
			return value;
		}

		value = _wildcardURLPatternValues.get(urlPath + "/*");

		if (Objects.nonNull(value)) {
			return value;
		}

		int index = 0;

		for (int i = urlPath.length(); i > 0; --i) {
			if ((index < 1) && (urlPath.charAt(i - 1) == '.')) {
				index = i - 1;
			}

			if (urlPath.charAt(i - 1) != '/') {
				continue;
			}

			value = _wildcardURLPatternValues.get(
				urlPath.substring(0, i) + "*");

			if (Objects.nonNull(value)) {
				return value;
			}
		}

		return _extensionURLPatternValues.get("*" + urlPath.substring(index));
	}

	@Override
	protected void put(String urlPattern, T value)
		throws IllegalArgumentException {

		if (Objects.isNull(urlPattern) || (urlPattern.length() == 0)) {
			throw new IllegalArgumentException("URL pattern is blank");
		}

		if (isWildcardURLPattern(urlPattern)) {
			_wildcardURLPatternValues.put(urlPattern, value);

			return;
		}

		if (isExtensionURLPattern(urlPattern)) {
			_extensionURLPatternValues.put(urlPattern, value);

			return;
		}

		_exactURLPatternValues.put(urlPattern, value);
	}

	private final Map<String, T> _exactURLPatternValues = new HashMap<>();
	private final Map<String, T> _extensionURLPatternValues = new HashMap<>();
	private final Map<String, T> _wildcardURLPatternValues = new HashMap<>();

}