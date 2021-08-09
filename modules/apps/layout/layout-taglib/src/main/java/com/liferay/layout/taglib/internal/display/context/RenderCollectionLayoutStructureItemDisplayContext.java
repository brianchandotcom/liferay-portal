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

package com.liferay.layout.taglib.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.info.pagination.Pagination;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.RequestContextMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class RenderCollectionLayoutStructureItemDisplayContext {

	public static final String PAGE_NUMBER_PARAM_PREFIX = "page_number_";

	public static final String PAGINATION_TYPE_NUMERIC = "numeric";

	public static final String PAGINATION_TYPE_SIMPLE = "simple";

	public RenderCollectionLayoutStructureItemDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<Object> getCollection(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		JSONObject collectionJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			_getLayoutListRetriever(collectionJSONObject);
		ListObjectReference listObjectReference = _getListObjectReference(
			collectionJSONObject);

		if ((layoutListRetriever == null) || (listObjectReference == null)) {
			return Collections.emptyList();
		}

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			_getDefaultLayoutListRetrieverContext(collectionJSONObject);

		int end = collectionStyledLayoutStructureItem.getNumberOfItems();
		int start = 0;

		String paginationType =
			collectionStyledLayoutStructureItem.getPaginationType();

		if (Objects.equals(paginationType, PAGINATION_TYPE_NUMERIC) ||
			Objects.equals(paginationType, PAGINATION_TYPE_SIMPLE)) {

			HttpServletRequest originalHttpServletRequest =
				PortalUtil.getOriginalServletRequest(_httpServletRequest);

			int currentPage = ParamUtil.getInteger(
				originalHttpServletRequest,
				PAGE_NUMBER_PARAM_PREFIX +
					collectionStyledLayoutStructureItem.getItemId());

			if (currentPage < 1) {
				currentPage = 1;
			}

			int numberOfItems =
				collectionStyledLayoutStructureItem.getNumberOfItems();

			int numberOfItemsPerPage =
				collectionStyledLayoutStructureItem.getNumberOfItemsPerPage();

			if (numberOfItemsPerPage >
					PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA) {

				numberOfItemsPerPage =
					PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA;
			}

			int listCount = layoutListRetriever.getListCount(
				listObjectReference, defaultLayoutListRetrieverContext);

			end = Math.min(
				Math.min(currentPage * numberOfItemsPerPage, numberOfItems),
				listCount);

			start = (currentPage - 1) * numberOfItemsPerPage;
		}

		defaultLayoutListRetrieverContext.setPagination(
			Pagination.of(end, start));

		return layoutListRetriever.getList(
			listObjectReference, defaultLayoutListRetrieverContext);
	}

	public int getCollectionCount(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		JSONObject collectionJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			_getLayoutListRetriever(collectionJSONObject);
		ListObjectReference listObjectReference = _getListObjectReference(
			collectionJSONObject);

		if ((layoutListRetriever == null) || (listObjectReference == null)) {
			return 0;
		}

		return layoutListRetriever.getListCount(
			listObjectReference,
			_getDefaultLayoutListRetrieverContext(collectionJSONObject));
	}

	public LayoutDisplayPageProvider<?> getCollectionLayoutDisplayPageProvider(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		JSONObject collectionJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		if ((collectionJSONObject == null) ||
			(collectionJSONObject.length() <= 0)) {

			return null;
		}

		ListObjectReference listObjectReference = _getListObjectReference(
			collectionJSONObject);

		if (listObjectReference == null) {
			return null;
		}

		LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker =
			ServletContextUtil.getLayoutDisplayPageProviderTracker();

		String className = listObjectReference.getItemType();

		if (Objects.equals(className, DLFileEntry.class.getName())) {
			className = FileEntry.class.getName();
		}

		return layoutDisplayPageProviderTracker.
			getLayoutDisplayPageProviderByClassName(className);
	}

	public InfoListRenderer<?> getInfoListRenderer(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		if (Validator.isNull(
				collectionStyledLayoutStructureItem.getListStyle())) {

			return null;
		}

		InfoListRendererTracker infoListRendererTracker =
			ServletContextUtil.getInfoListRendererTracker();

		return infoListRendererTracker.getInfoListRenderer(
			collectionStyledLayoutStructureItem.getListStyle());
	}

	public InfoListRendererContext getInfoListRendererContext(
		String listItemStyle, String templateKey) {

		DefaultInfoListRendererContext defaultInfoListRendererContext =
			new DefaultInfoListRendererContext(
				_httpServletRequest, _httpServletResponse);

		defaultInfoListRendererContext.setListItemRendererKey(listItemStyle);
		defaultInfoListRendererContext.setTemplateKey(templateKey);

		return defaultInfoListRendererContext;
	}

	private Map<String, String[]> _getConfiguration(
		JSONObject collectionJSONObject) {

		JSONObject configurationJSONObject = collectionJSONObject.getJSONObject(
			"config");

		if (configurationJSONObject == null) {
			return null;
		}

		Map<String, String[]> configuration = new HashMap<>();

		for (String key : configurationJSONObject.keySet()) {
			List<String> values = new ArrayList<>();

			Object object = configurationJSONObject.get(key);

			if (object instanceof JSONArray) {
				JSONArray jsonArray = configurationJSONObject.getJSONArray(key);

				for (int i = 0; i < jsonArray.length(); i++) {
					values.add(jsonArray.getString(i));
				}
			}
			else {
				values.add(String.valueOf(object));
			}

			configuration.put(key, values.toArray(new String[0]));
		}

		return configuration;
	}

	private DefaultLayoutListRetrieverContext
		_getDefaultLayoutListRetrieverContext(JSONObject collectionJSONObject) {

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			new DefaultLayoutListRetrieverContext();

		defaultLayoutListRetrieverContext.setConfiguration(
			_getConfiguration(collectionJSONObject));
		defaultLayoutListRetrieverContext.setContextObject(
			Optional.ofNullable(
				_httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT)
			).orElse(
				_httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM)
			));
		defaultLayoutListRetrieverContext.setHttpServletRequest(
			_httpServletRequest);
		defaultLayoutListRetrieverContext.setSegmentsEntryIds(
			_getSegmentsEntryIds());

		return defaultLayoutListRetrieverContext;
	}

	private LayoutListRetriever<?, ListObjectReference> _getLayoutListRetriever(
		JSONObject collectionJSONObject) {

		if ((collectionJSONObject == null) ||
			(collectionJSONObject.length() <= 0)) {

			return null;
		}

		LayoutListRetrieverTracker layoutListRetrieverTracker =
			ServletContextUtil.getLayoutListRetrieverTracker();

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			(LayoutListRetriever<?, ListObjectReference>)
				layoutListRetrieverTracker.getLayoutListRetriever(
					collectionJSONObject.getString("type"));

		if (layoutListRetriever == null) {
			return null;
		}

		return layoutListRetriever;
	}

	private ListObjectReference _getListObjectReference(
		JSONObject collectionJSONObject) {

		if ((collectionJSONObject == null) ||
			(collectionJSONObject.length() <= 0)) {

			return null;
		}

		LayoutListRetrieverTracker layoutListRetrieverTracker =
			ServletContextUtil.getLayoutListRetrieverTracker();

		String type = collectionJSONObject.getString("type");

		LayoutListRetriever<?, ?> layoutListRetriever =
			layoutListRetrieverTracker.getLayoutListRetriever(type);

		if (layoutListRetriever == null) {
			return null;
		}

		ListObjectReferenceFactoryTracker listObjectReferenceFactoryTracker =
			ServletContextUtil.getListObjectReferenceFactoryTracker();

		ListObjectReferenceFactory<?> listObjectReferenceFactory =
			listObjectReferenceFactoryTracker.getListObjectReference(type);

		if (listObjectReferenceFactory == null) {
			return null;
		}

		return listObjectReferenceFactory.getListObjectReference(
			collectionJSONObject);
	}

	private long[] _getSegmentsEntryIds() {
		if (_segmentsEntryIds != null) {
			return _segmentsEntryIds;
		}

		SegmentsEntryRetriever segmentsEntryRetriever =
			ServletContextUtil.getSegmentsEntryRetriever();

		RequestContextMapper requestContextMapper =
			ServletContextUtil.getRequestContextMapper();

		_segmentsEntryIds = segmentsEntryRetriever.getSegmentsEntryIds(
			_themeDisplay.getScopeGroupId(), _themeDisplay.getUserId(),
			requestContextMapper.map(_httpServletRequest));

		return _segmentsEntryIds;
	}

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private long[] _segmentsEntryIds;
	private final ThemeDisplay _themeDisplay;

}