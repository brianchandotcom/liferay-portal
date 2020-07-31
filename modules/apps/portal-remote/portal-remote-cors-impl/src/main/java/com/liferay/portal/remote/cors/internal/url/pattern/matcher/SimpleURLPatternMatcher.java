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

package com.liferay.portal.remote.cors.internal.url.pattern.matcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Chan
 */
public class SimpleURLPatternMatcher<T> implements URLPatternMatcher<T> {

	@Override
	public T getValue(String urlPath) {
		T value = _exactURLPatternMap.get(urlPath);

		if (value != null) {
			return value;
		}

		int lastDotIndex = 0;

		for (int i = urlPath.length(); i > 0; --i) {
			value = _wildcardURLPatternMap.get(urlPath.substring(0, i) + "*");

			if (value != null) {
				return value;
			}

			if ((lastDotIndex < 1) && (urlPath.charAt(i - 1) == '.')) {
				lastDotIndex = i - 1;
			}
		}

		return _extensionURLPatternMap.get(
			"*" + urlPath.substring(lastDotIndex));
	}

	@Override
	public void putValue(String urlPattern, T value)
		throws IllegalArgumentException {

		if (URLPatternMatcher.isWildcardURLPattern(urlPattern)) {
			if (!_wildcardURLPatternMap.containsKey(urlPattern)) {
				_wildcardURLPatternMap.put(urlPattern, value);
			}

			return;
		}

		if (URLPatternMatcher.isExtensionURLPattern(urlPattern)) {
			if (!_extensionURLPatternMap.containsKey(urlPattern)) {
				_extensionURLPatternMap.put(urlPattern, value);
			}

			return;
		}

		if (!_exactURLPatternMap.containsKey(urlPattern)) {
			_exactURLPatternMap.put(urlPattern, value);
		}
	}

	private final Map<String, T> _exactURLPatternMap = new HashMap<>();
	private final Map<String, T> _extensionURLPatternMap = new HashMap<>();
	private final Map<String, T> _wildcardURLPatternMap = new HashMap<>();

}