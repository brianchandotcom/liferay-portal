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

package com.liferay.asset.list.internal.info.filter;

import com.liferay.asset.list.info.filter.AssetEntryListInfoFilter;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.InfoRequestItemProvider;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = "infoFilterKey=" + AssetEntryListInfoFilter.KEY,
	service = InfoRequestItemProvider.class
)
public class AssetEntryListInfoFilterProvider
	implements InfoRequestItemProvider<InfoFilter> {

	@Override
	public InfoFilter create(HttpServletRequest httpServletRequest) {
		AssetEntryListInfoFilter assetEntryListInfoFilter =
			new AssetEntryListInfoFilter();

		assetEntryListInfoFilter.setAssetCategoryIds(
			_getAssetCategoryIds(httpServletRequest));

		return assetEntryListInfoFilter;
	}

	private long[][] _getAssetCategoryIds(
		HttpServletRequest httpServletRequest) {

		Set<long[]> assetCategoryIdsSet = new HashSet<>();

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		Map<String, String[]> parameterMap =
			originalHttpServletRequest.getParameterMap();

		Set<String> parameterNames = parameterMap.keySet();

		Stream<String> parameterNamesStream = parameterNames.stream();

		Set<String> categoryIdParameterNames = parameterNamesStream.filter(
			parameterName -> parameterName.startsWith("categoryId_")
		).collect(
			Collectors.toSet()
		);

		for (String categoryIdParameterName : categoryIdParameterNames) {
			String[] values = parameterMap.get(categoryIdParameterName);

			if (ArrayUtil.isNotEmpty(values)) {
				assetCategoryIdsSet.add(
					ArrayUtil.filter(
						GetterUtil.getLongValues(values),
						categoryId -> categoryId != 0));
			}
		}

		return assetCategoryIdsSet.toArray(
			new long[assetCategoryIdsSet.size()][]);
	}

	@Reference
	private Portal _portal;

}