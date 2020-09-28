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

package com.liferay.portal.remote.cors.internal.url.pattern.mapper.factory;

import com.liferay.portal.remote.cors.internal.url.pattern.mapper.URLPatternMapper;
import com.liferay.portal.remote.cors.internal.url.pattern.mapper.trie.DynamicSizeTrieURLPatternMapper;
import com.liferay.portal.remote.cors.internal.url.pattern.mapper.trie.StaticSizeTrieURLPatternMapper;

import java.util.Map;

/**
 * @author Carlos Sierra Andrés
 * @author Arthur Chan
 */
public class URLPatternMapperFactory {

	public static <T> URLPatternMapper<T> create(Map<String, T> values) {
		if (values.size() > 64) {
			return new DynamicSizeTrieURLPatternMapper<>(values);
		}

		return new StaticSizeTrieURLPatternMapper<>(values);
	}

}