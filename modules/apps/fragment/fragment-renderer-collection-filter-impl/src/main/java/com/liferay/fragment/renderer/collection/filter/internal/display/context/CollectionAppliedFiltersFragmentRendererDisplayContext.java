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

package com.liferay.fragment.renderer.collection.filter.internal.display.context;

import com.liferay.fragment.collection.filter.constants.FragmentCollectionFilterConstants;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pablo.Molina
 */
public class CollectionAppliedFiltersFragmentRendererDisplayContext {

	public CollectionAppliedFiltersFragmentRendererDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public List<Map<String, String>> getAppliedFilters() {
		List<Map<String, String>> appliedFilters = new ArrayList<>();

		Map<String, String[]> parameters =
			_httpServletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String parameterName = entry.getKey();

			if (!parameterName.startsWith(
					FragmentCollectionFilterConstants.FILTER_PREFIX)) {

				continue;
			}

			List<String> parameterData = StringUtil.split(
				parameterName, CharPool.UNDERLINE);

			if (parameterData.size() != 3) {
				continue;
			}

			for (String filterValue : entry.getValue()) {
				appliedFilters.add(
					HashMapBuilder.put(
						"filterFragmentEntryLinkId", parameterData.get(2)
					).put(
						"filterType", parameterData.get(1)
					).put(
						"filterValue", filterValue
					).build());
			}
		}

		return appliedFilters;
	}

	public Map<String, Object> getCollectionAppliedFiltersProps() {
		if (_collectionAppliedFiltersProps != null) {
			return _collectionAppliedFiltersProps;
		}

		_collectionAppliedFiltersProps = HashMapBuilder.<String, Object>put(
			"filterPrefix", FragmentCollectionFilterConstants.FILTER_PREFIX
		).build();

		return _collectionAppliedFiltersProps;
	}

	private Map<String, Object> _collectionAppliedFiltersProps;
	private final HttpServletRequest _httpServletRequest;

}