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
import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pablo.Molina
 */
public class CollectionAppliedFiltersFragmentRendererDisplayContext {

	public CollectionAppliedFiltersFragmentRendererDisplayContext(
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_httpServletRequest = httpServletRequest;

		_fragmentEntryLink = fragmentRendererContext.getFragmentEntryLink();
	}

	public List<Map<String, String>> getAppliedFilters() {
		List<Map<String, String>> appliedFilters = new ArrayList<>();

		Map<String, String[]> parameters =
			_httpServletRequest.getParameterMap();

		JSONArray targetCollectionsJSONArray =
			(JSONArray)
				_fragmentEntryConfigurationParser.getConfigurationFieldValue(
					_fragmentEntryLink.getEditableValues(), "targetCollections",
					FragmentConfigurationFieldDataType.ARRAY);

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

			String filterFragmentEntryLinkId = parameterData.get(2);

			FragmentEntryLink filterFragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					GetterUtil.getLong(filterFragmentEntryLinkId));

			JSONArray filterTargetCollectionsJSONArray = null;

			if (filterFragmentEntryLink != null) {
				filterTargetCollectionsJSONArray =
					(JSONArray)
						_fragmentEntryConfigurationParser.
							getConfigurationFieldValue(
								filterFragmentEntryLink.getEditableValues(),
								"targetCollections",
								FragmentConfigurationFieldDataType.ARRAY);
			}

			if (filterTargetCollectionsJSONArray == null) {
				filterTargetCollectionsJSONArray =
					JSONFactoryUtil.createJSONArray();
			}

			if (Collections.disjoint(
					JSONUtil.toStringSet(filterTargetCollectionsJSONArray),
					JSONUtil.toStringSet(targetCollectionsJSONArray))) {

				continue;
			}

			for (String filterValue : entry.getValue()) {
				appliedFilters.add(
					HashMapBuilder.put(
						"filterFragmentEntryLinkId", filterFragmentEntryLinkId
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
			"fragmentEntryLinkNamespace", getFragmentEntryLinkNamespace()
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
				_fragmentEntryLink.getEditableValues(), "showClearFilters",
				FragmentConfigurationFieldDataType.BOOLEAN);
	}

	private Map<String, Object> _collectionAppliedFiltersProps;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private final FragmentEntryLink _fragmentEntryLink;
	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final HttpServletRequest _httpServletRequest;

}