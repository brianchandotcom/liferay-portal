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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 * @author Brian Wing Shun Chan
 */
public class SimpleURLPatternMapper<T> extends BaseURLPatternMapper<T> {

	public SimpleURLPatternMapper(Map<String, T> cargos) {
		for (Map.Entry<String, T> entry : cargos.entrySet()) {
			put(entry.getValue(), entry.getKey());
		}
	}

	@Override
	public T get(String urlPath) {
		if (Validator.isNull(urlPath)) {
			return null;
		}

		T cargo = _exactURLPatternCargos.get(urlPath);

		if (cargo != null) {
			return cargo;
		}

		cargo = _wildcardURLPatternCargos.get(urlPath + "/*");

		if (cargo != null) {
			return cargo;
		}

		int index = 0;

		for (int i = urlPath.length(); i > 0; --i) {
			if ((index < 1) && (urlPath.charAt(i - 1) == '.')) {
				index = i - 1;
			}

			if (urlPath.charAt(i - 1) != '/') {
				continue;
			}

			cargo = _wildcardURLPatternCargos.get(
				urlPath.substring(0, i) + "*");

			if (cargo != null) {
				return cargo;
			}
		}

		return _extensionURLPatternCargos.get("*" + urlPath.substring(index));
	}

	@Override
	protected void put(T cargo, String urlPattern)
		throws IllegalArgumentException {

		if (Validator.isBlank(urlPattern)) {
			throw new IllegalArgumentException("URL pattern is blank");
		}

		if (isWildcardURLPattern(urlPattern)) {
			if (!_wildcardURLPatternCargos.containsKey(urlPattern)) {
				_wildcardURLPatternCargos.put(urlPattern, cargo);
			}

			return;
		}

		if (isExtensionURLPattern(urlPattern)) {
			if (!_extensionURLPatternCargos.containsKey(urlPattern)) {
				_extensionURLPatternCargos.put(urlPattern, cargo);
			}

			return;
		}

		if (!_exactURLPatternCargos.containsKey(urlPattern)) {
			_exactURLPatternCargos.put(urlPattern, cargo);
		}
	}

	private final Map<String, T> _exactURLPatternCargos = new HashMap<>();
	private final Map<String, T> _extensionURLPatternCargos = new HashMap<>();
	private final Map<String, T> _wildcardURLPatternCargos = new HashMap<>();

}