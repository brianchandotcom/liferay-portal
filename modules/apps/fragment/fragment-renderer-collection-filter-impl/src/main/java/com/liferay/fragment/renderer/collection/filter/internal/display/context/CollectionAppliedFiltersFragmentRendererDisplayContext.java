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
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_fragmentRendererContext = fragmentRendererContext;
		_httpServletRequest = httpServletRequest;

		_fragmentEntryLink = fragmentRendererContext.getFragmentEntryLink();
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
		).put(
			"removeAllFiltersButtonSelector",
			StringPool.POUND + getFragmentEntryLinkNamespace() +
				" .remove-all-collection-filters-button"
		).put(
			"removeButtonSelector",
			StringPool.POUND + getFragmentEntryLinkNamespace() +
				" .remove-collection-applied-filter-button"
		).build();

		return _collectionAppliedFiltersProps;
	}

	public String getFragmentEntryLinkNamespace() {
		return StringBundler.concat(
			"fragment_", _fragmentEntryLink.getFragmentEntryLinkId(),
			StringPool.UNDERLINE, _fragmentEntryLink.getNamespace());
	}

	public boolean showClearFiltersButton() {
		return (boolean)
			_fragmentEntryConfigurationParser.getConfigurationFieldValue(
				_fragmentEntryLink.getEditableValues(), "bool",
				"showClearFilters");
	}

	private Map<String, Object> _collectionAppliedFiltersProps;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private final FragmentEntryLink _fragmentEntryLink;
	private final FragmentRendererContext _fragmentRendererContext;
	private final HttpServletRequest _httpServletRequest;

}