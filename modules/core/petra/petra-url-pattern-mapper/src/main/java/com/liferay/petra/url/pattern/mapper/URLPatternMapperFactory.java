/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.url.pattern.mapper;

import com.liferay.petra.url.pattern.mapper.internal.DynamicSizeTrieURLPatternMapper;
import com.liferay.petra.url.pattern.mapper.internal.StaticSizeTrieURLPatternMapper;

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