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

package com.liferay.portal.remote.cors.internal;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 * @author Brian Wing Shun Chan
 */
public class SimpleURLToCORSSupportMapper extends BaseURLToCORSSupportMapper {

	public SimpleURLToCORSSupportMapper(Map<String, CORSSupport> corsSupports) {
		for (Map.Entry<String, CORSSupport> entry : corsSupports.entrySet()) {
			put(entry.getValue(), entry.getKey());
		}
	}

	@Override
	public CORSSupport get(String urlPath) {
		if (Validator.isNull(urlPath)) {
			return null;
		}

		CORSSupport corsSupport = _exactURLPatternCORSSupports.get(urlPath);

		if (corsSupport != null) {
			return corsSupport;
		}

		corsSupport = _wildcardURLPatternCORSSupports.get(urlPath + "/*");

		if (corsSupport != null) {
			return corsSupport;
		}

		int index = 0;

		for (int i = urlPath.length(); i > 0; --i) {
			if ((index < 1) && (urlPath.charAt(i - 1) == '.')) {
				index = i - 1;
			}

			if (urlPath.charAt(i - 1) != '/') {
				continue;
			}

			corsSupport = _wildcardURLPatternCORSSupports.get(
				urlPath.substring(0, i) + "*");

			if (corsSupport != null) {
				return corsSupport;
			}
		}

		return _extensionURLPatternCORSSupports.get(
			"*" + urlPath.substring(index));
	}

	@Override
	protected void put(CORSSupport corsSupport, String urlPattern)
		throws IllegalArgumentException {

		if (Validator.isBlank(urlPattern)) {
			throw new IllegalArgumentException("URL pattern is blank");
		}

		if (isWildcardURLPattern(urlPattern)) {
			if (!_wildcardURLPatternCORSSupports.containsKey(urlPattern)) {
				_wildcardURLPatternCORSSupports.put(urlPattern, corsSupport);
			}

			return;
		}

		if (isExtensionURLPattern(urlPattern)) {
			if (!_extensionURLPatternCORSSupports.containsKey(urlPattern)) {
				_extensionURLPatternCORSSupports.put(urlPattern, corsSupport);
			}

			return;
		}

		if (!_exactURLPatternCORSSupports.containsKey(urlPattern)) {
			_exactURLPatternCORSSupports.put(urlPattern, corsSupport);
		}
	}

	private final Map<String, CORSSupport> _exactURLPatternCORSSupports =
		new HashMap<>();
	private final Map<String, CORSSupport> _extensionURLPatternCORSSupports =
		new HashMap<>();
	private final Map<String, CORSSupport> _wildcardURLPatternCORSSupports =
		new HashMap<>();

}