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

package com.liferay.info.internal.item.filter;

import com.liferay.info.filter.InfoFilterProvider;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoFilterProvider.class)
public class KeywordsInfoFilterProvider
	implements InfoFilterProvider<KeywordsInfoFilter> {

	@Override
	public KeywordsInfoFilter create(Map<String, String[]> values) {
		KeywordsInfoFilter keywordsInfoFilter = new KeywordsInfoFilter();

		keywordsInfoFilter.setKeywords(_getKeywords(values));

		return keywordsInfoFilter;
	}

	private String _getKeywords(Map<String, String[]> values) {
		Set<String> keywordsSet = new HashSet<>();

		for (Map.Entry<String, String[]> entry : values.entrySet()) {
			if (!StringUtil.startsWith(
					entry.getKey(),
					KeywordsInfoFilter.FILTER_TYPE_NAME +
						StringPool.UNDERLINE)) {

				continue;
			}

			Collections.addAll(keywordsSet, entry.getValue());
		}

		return StringUtil.merge(keywordsSet, StringPool.SPACE);
	}

}