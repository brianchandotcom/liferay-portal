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
@Component(immediate = true, service = InfoFilterProvider.class)
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