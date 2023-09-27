/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.item.filter;

import com.liferay.info.filter.CategoriesInfoFilter;
import com.liferay.info.filter.InfoFilterProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoFilterProvider.class)
public class CategoriesInfoFilterProvider
	implements InfoFilterProvider<CategoriesInfoFilter> {

	@Override
	public CategoriesInfoFilter create(Map<String, String[]> values) {
		CategoriesInfoFilter categoriesInfoFilter = new CategoriesInfoFilter();

		categoriesInfoFilter.setCategoryIds(_getAssetCategoryIds(values));

		return categoriesInfoFilter;
	}

	private long[][] _getAssetCategoryIds(Map<String, String[]> values) {
		Set<long[]> assetCategoryIdsSet = new HashSet<>();

		for (Map.Entry<String, String[]> entry : values.entrySet()) {
			if (!StringUtil.startsWith(
					entry.getKey(),
					CategoriesInfoFilter.FILTER_TYPE_NAME +
						StringPool.UNDERLINE)) {

				continue;
			}

			assetCategoryIdsSet.add(
				ArrayUtil.filter(
					GetterUtil.getLongValues(entry.getValue()),
					categoryId -> categoryId != 0));
		}

		return assetCategoryIdsSet.toArray(
			new long[assetCategoryIdsSet.size()][]);
	}

}