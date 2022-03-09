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
import com.liferay.fragment.collection.filter.constants.FragmentCollectionFilterConstants;
import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.InfoFilterProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
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

	public RenderCollectionLayoutStructureItemDisplayContext(
		CollectionStyledLayoutStructureItem collectionStyledLayoutStructureItem,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_collectionStyledLayoutStructureItem =
			collectionStyledLayoutStructureItem;
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public int getActivePage() {
		if (_activePage != null) {
			return _activePage;
		}

		_activePage = Math.max(
			1,
			Math.min(
				getNumberOfPages(),
				ParamUtil.getInteger(
					PortalUtil.getOriginalServletRequest(_httpServletRequest),
					PAGE_NUMBER_PARAM_PREFIX +
						_collectionStyledLayoutStructureItem.getItemId(),
					1)));

		return _activePage;
	}

	public List<Object> getCollection() {
		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			_getLayoutListRetriever();
		ListObjectReference listObjectReference = _getListObjectReference();

		if ((layoutListRetriever == null) || (listObjectReference == null)) {
			return Collections.emptyList();
		}

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			_getDefaultLayoutListRetrieverContext(
				layoutListRetriever, listObjectReference);

		CollectionPaginationHelper collectionPaginationHelper =
			ServletContextUtil.getCollectionPaginationHelper();

		defaultLayoutListRetrieverContext.setPagination(
			collectionPaginationHelper.getPagination(
				getActivePage(), getCollectionCount(),
				_collectionStyledLayoutStructureItem.isDisplayAllPages(),
				_collectionStyledLayoutStructureItem.isDisplayAllItems(),
				_collectionStyledLayoutStructureItem.getNumberOfItems(),
				_collectionStyledLayoutStructureItem.getNumberOfItemsPerPage(),
				_collectionStyledLayoutStructureItem.getNumberOfPages(),
				_collectionStyledLayoutStructureItem.getPaginationType(),
				_collectionStyledLayoutStructureItem.isShowAllItems()));

		return layoutListRetriever.getList(
			listObjectReference, defaultLayoutListRetrieverContext);
	}

	public int getCollectionCount() {
		if (_collectionCount != null) {
			return _collectionCount;
		}

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			_getLayoutListRetriever();
		ListObjectReference listObjectReference = _getListObjectReference();

		if ((layoutListRetriever == null) || (listObjectReference == null)) {
			return 0;
		}

		_collectionCount = layoutListRetriever.getListCount(
			listObjectReference,
			_getDefaultLayoutListRetrieverContext(
				layoutListRetriever, listObjectReference));

		return _collectionCount;
	}

	public String getCollectionItemType() {
		if (_collectionItemType != null) {
			return _collectionItemType;
		}

		JSONObject collectionJSONObject =
			_collectionStyledLayoutStructureItem.getCollectionJSONObject();

		String collectionItemType = StringPool.BLANK;

		if ((collectionJSONObject != null) &&
			collectionJSONObject.has("itemType")) {

			collectionItemType = collectionJSONObject.getString("itemType");
		}

		_collectionItemType = collectionItemType;

		return _collectionItemType;
	}

	public LayoutDisplayPageProvider<?>
		getCollectionLayoutDisplayPageProvider() {

		JSONObject collectionJSONObject =
			_collectionStyledLayoutStructureItem.getCollectionJSONObject();

		if ((collectionJSONObject == null) ||
			(collectionJSONObject.length() <= 0)) {

			return null;
		}

		ListObjectReference listObjectReference = _getListObjectReference();

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

	public InfoListRenderer<?> getInfoListRenderer() {
		if (Validator.isNull(
				_collectionStyledLayoutStructureItem.getListStyle())) {

			return null;
		}

		InfoListRendererTracker infoListRendererTracker =
			ServletContextUtil.getInfoListRendererTracker();

		return infoListRendererTracker.getInfoListRenderer(
			_collectionStyledLayoutStructureItem.getListStyle());
	}

	public int getMaxNumberOfItemsPerPage() {
		if (_maxNumberOfItemsPerPage != null) {
			return _maxNumberOfItemsPerPage;
		}

		_maxNumberOfItemsPerPage = Math.min(
			getCollectionCount(), _getNumberOfItemsPerPage());

		return _maxNumberOfItemsPerPage;
	}

	public int getNumberOfItemsToDisplay() {
		if (_numberOfItemsToDisplay != null) {
			return _numberOfItemsToDisplay;
		}

		int numberOfItemsToDisplay = getTotalNumberOfItems();

		CollectionPaginationHelper collectionPaginationHelper =
			ServletContextUtil.getCollectionPaginationHelper();

		if (collectionPaginationHelper.isPaginationEnabled(
				_collectionStyledLayoutStructureItem.getPaginationType())) {

			numberOfItemsToDisplay = Math.min(
				numberOfItemsToDisplay, _getNumberOfItemsPerPage());
		}

		_numberOfItemsToDisplay = numberOfItemsToDisplay;

		return _numberOfItemsToDisplay;
	}

	public int getNumberOfPages() {
		if (_numberOfPages != null) {
			return _numberOfPages;
		}

		_numberOfPages = _getNumberOfPages();

		return _numberOfPages;
	}

	public int getNumberOfRows() {
		if (_numberOfRows != null) {
			return _numberOfRows;
		}

		_numberOfRows = (int)Math.ceil(
			(double)getMaxNumberOfItemsPerPage() /
				_collectionStyledLayoutStructureItem.getNumberOfColumns());

		int numberOfItemsToDisplay = getTotalNumberOfItems();

		CollectionPaginationHelper collectionPaginationHelper =
			ServletContextUtil.getCollectionPaginationHelper();

		if (collectionPaginationHelper.isPaginationEnabled(
				_collectionStyledLayoutStructureItem.getPaginationType())) {

			numberOfItemsToDisplay = Math.min(
				numberOfItemsToDisplay, _getNumberOfItemsPerPage());
		}

		_numberOfRows = (int)Math.ceil(
			(double)numberOfItemsToDisplay /
				_collectionStyledLayoutStructureItem.getNumberOfColumns());

		return _numberOfRows;
	}

	public Map<String, Object> getNumericCollectionPaginationAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"collectionId", _collectionStyledLayoutStructureItem.getItemId()
		).build();
	}

	public Map<String, Object> getSimpleCollectionPaginationContext() {
		return HashMapBuilder.<String, Object>put(
			"activePage", getActivePage()
		).put(
			"collectionId", _collectionStyledLayoutStructureItem.getItemId()
		).build();
	}

	public int getTotalNumberOfItems() {
		CollectionPaginationHelper collectionPaginationHelper =
			ServletContextUtil.getCollectionPaginationHelper();

		return collectionPaginationHelper.getTotalNumberOfItems(
			getCollectionCount(),
			_collectionStyledLayoutStructureItem.isDisplayAllPages(),
			_collectionStyledLayoutStructureItem.isDisplayAllItems(),
			_collectionStyledLayoutStructureItem.getNumberOfItems(),
			_collectionStyledLayoutStructureItem.getNumberOfItemsPerPage(),
			_collectionStyledLayoutStructureItem.getNumberOfPages(),
			_collectionStyledLayoutStructureItem.getPaginationType(),
			_collectionStyledLayoutStructureItem.isShowAllItems());
	}

	private Map<String, String[]> _getConfiguration() {
		JSONObject collectionJSONObject =
			_collectionStyledLayoutStructureItem.getCollectionJSONObject();

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
		_getDefaultLayoutListRetrieverContext(
			LayoutListRetriever<?, ListObjectReference> layoutListRetriever,
			ListObjectReference listObjectReference) {

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			new DefaultLayoutListRetrieverContext();

		defaultLayoutListRetrieverContext.setConfiguration(_getConfiguration());
		defaultLayoutListRetrieverContext.setContextObject(
			Optional.ofNullable(
				_httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT)
			).orElse(
				_httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM)
			));
		defaultLayoutListRetrieverContext.setInfoFilters(
			_getInfoFilters(layoutListRetriever, listObjectReference));
		defaultLayoutListRetrieverContext.setHttpServletRequest(
			_httpServletRequest);
		defaultLayoutListRetrieverContext.setSegmentsEntryIds(
			_getSegmentsEntryIds());

		return defaultLayoutListRetrieverContext;
	}

	private Map<String, String[]> _getFilterValues() {
		Map<String, String[]> filterValues = new HashMap<>();

		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(_httpServletRequest);

		Map<String, String[]> parameterMap =
			originalHttpServletRequest.getParameterMap();

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			ServletContextUtil.getFragmentEntryConfigurationParser();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();

			if (!parameterName.startsWith(
					FragmentCollectionFilterConstants.FILTER_PREFIX) ||
				ArrayUtil.isEmpty(entry.getValue())) {

				continue;
			}

			List<String> parameterNameParts = StringUtil.split(
				parameterName, CharPool.UNDERLINE);

			if (parameterNameParts.size() != 3) {
				continue;
			}

			FragmentEntryLink fragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
					GetterUtil.getLong(parameterNameParts.get(2)));

			if (fragmentEntryLink == null) {
				continue;
			}

			JSONArray targetCollectionsJSONArray =
				(JSONArray)
					fragmentEntryConfigurationParser.getConfigurationFieldValue(
						fragmentEntryLink.getEditableValues(),
						"targetCollections",
						FragmentConfigurationFieldDataType.ARRAY);

			if ((targetCollectionsJSONArray != null) &&
				JSONUtil.hasValue(
					targetCollectionsJSONArray,
					_collectionStyledLayoutStructureItem.getItemId())) {

				filterValues.put(
					parameterName.replaceFirst(
						FragmentCollectionFilterConstants.FILTER_PREFIX,
						StringPool.BLANK),
					entry.getValue());
			}
		}

		return filterValues;
	}

	private Map<String, InfoFilter> _getInfoFilters(
		LayoutListRetriever<?, ListObjectReference> layoutListRetriever,
		ListObjectReference listObjectReference) {

		Map<String, InfoFilter> infoFilters = new HashMap<>();

		InfoItemServiceTracker infoItemServiceTracker =
			ServletContextUtil.getInfoItemServiceTracker();

		Map<String, String[]> filterValues = _getFilterValues();

		for (InfoFilter infoFilter :
				layoutListRetriever.getSupportedInfoFilters(
					listObjectReference)) {

			Class<?> clazz = infoFilter.getClass();

			InfoFilterProvider<?> infoFilterProvider =
				infoItemServiceTracker.getFirstInfoItemService(
					InfoFilterProvider.class, clazz.getName());

			infoFilters.put(
				clazz.getName(), infoFilterProvider.create(filterValues));
		}

		return infoFilters;
	}

	private LayoutListRetriever<?, ListObjectReference>
		_getLayoutListRetriever() {

		JSONObject collectionJSONObject =
			_collectionStyledLayoutStructureItem.getCollectionJSONObject();

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

	private ListObjectReference _getListObjectReference() {
		JSONObject collectionJSONObject =
			_collectionStyledLayoutStructureItem.getCollectionJSONObject();

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

	private int _getNumberOfItemsPerPage() {
		int numberOfItemsPerPage =
			_collectionStyledLayoutStructureItem.getNumberOfItemsPerPage();

		if ((numberOfItemsPerPage <= 0) ||
			(numberOfItemsPerPage >
				PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA)) {

			numberOfItemsPerPage = PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA;
		}

		return numberOfItemsPerPage;
	}

	private int _getNumberOfPages() {
		int numberOfItemsPerPage = _getNumberOfItemsPerPage();

		int maxNumberOfItems = getCollectionCount();

		if (_collectionStyledLayoutStructureItem.getNumberOfPages() > 0) {
			maxNumberOfItems = Math.min(
				getCollectionCount(),
				_collectionStyledLayoutStructureItem.getNumberOfPages() *
					numberOfItemsPerPage);
		}

		if (_collectionStyledLayoutStructureItem.isDisplayAllPages()) {
			maxNumberOfItems = getCollectionCount();
		}

		return (int)Math.ceil((double)maxNumberOfItems / numberOfItemsPerPage);
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

	private Integer _activePage;
	private Integer _collectionCount;
	private String _collectionItemType;
	private final CollectionStyledLayoutStructureItem
		_collectionStyledLayoutStructureItem;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private Integer _maxNumberOfItemsPerPage;
	private Integer _numberOfItemsToDisplay;
	private Integer _numberOfPages;
	private Integer _numberOfRows;
	private long[] _segmentsEntryIds;
	private final ThemeDisplay _themeDisplay;

}