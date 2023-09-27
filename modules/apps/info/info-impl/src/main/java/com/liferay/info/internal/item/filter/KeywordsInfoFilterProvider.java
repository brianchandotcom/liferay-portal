/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = InfoFilterProvider.class)
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