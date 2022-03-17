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

package com.liferay.asset.publisher.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.action.AssetEntryAction;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.util.AssetEntryResult;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.action.AssetEntryActionRegistry;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherPortletInstanceConfiguration;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherSelectionStyleConfigurationUtil;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.internal.constants.AssetPublisherSelectionStyleConstants;
import com.liferay.asset.publisher.web.internal.helper.AssetPublisherWebHelper;
import com.liferay.asset.publisher.web.internal.util.AssetPublisherCustomizer;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.AssetPublisherAddItemHolder;
import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.item.selector.criterion.InfoCollectionProviderItemSelectorCriterion;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoListItemSelectorCriterion;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.rss.util.RSSUtil;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.context.RequestContextMapper;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides utility methods moved from the Asset Publisher portlet's JSP files
 * to reduce the complexity of the views.
 *
 * @author Eudaldo Alonso
 */
public class AssetPublisherDisplayContext {

	public static final String PAGINATION_TYPE_NONE = "none";

	public static final String PAGINATION_TYPE_REGULAR = "regular";

	public static final String PAGINATION_TYPE_SIMPLE = "simple";

	public static final String[] PAGINATION_TYPES = {
		PAGINATION_TYPE_NONE, PAGINATION_TYPE_REGULAR, PAGINATION_TYPE_SIMPLE
	};

	public AssetPublisherDisplayContext(
			AssetEntryActionRegistry assetEntryActionRegistry,
			AssetHelper assetHelper,
			AssetListAssetEntryProvider assetListAssetEntryProvider,
			AssetPublisherCustomizer assetPublisherCustomizer,
			AssetPublisherHelper assetPublisherHelper,
			AssetPublisherWebConfiguration assetPublisherWebConfiguration,
			AssetPublisherWebHelper assetPublisherWebHelper,
			InfoItemServiceTracker infoItemServiceTracker,
			ItemSelector itemSelector, PortletRequest portletRequest,
			PortletResponse portletResponse,
			PortletPreferences portletPreferences,
			RequestContextMapper requestContextMapper,
			SegmentsEntryRetriever segmentsEntryRetriever)
		throws ConfigurationException {

		_assetEntryActionRegistry = assetEntryActionRegistry;
		_assetHelper = assetHelper;
		_assetListAssetEntryProvider = assetListAssetEntryProvider;
		_assetPublisherCustomizer = assetPublisherCustomizer;
		_assetPublisherHelper = assetPublisherHelper;
		_assetPublisherWebConfiguration = assetPublisherWebConfiguration;
		_assetPublisherWebHelper = assetPublisherWebHelper;
		_infoItemServiceTracker = infoItemServiceTracker;
		_itemSelector = itemSelector;
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_portletPreferences = portletPreferences;
		_requestContextMapper = requestContextMapper;
		_segmentsEntryRetriever = segmentsEntryRetriever;

		_themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_assetPublisherPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				AssetPublisherPortletInstanceConfiguration.class);

		_httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
	}

	public AssetListEntry fetchAssetListEntry() throws PortalException {
		if (_assetListEntry != null) {
			return _assetListEntry;
		}

		_assetListEntry = AssetListEntryServiceUtil.fetchAssetListEntry(
			GetterUtil.getLong(
				_portletPreferences.getValue("assetListEntryId", null)));

		return _assetListEntry;
	}

	public int getAbstractLength() {
		if (_abstractLength != null) {
			return _abstractLength;
		}

		_abstractLength = GetterUtil.getInteger(
			_portletPreferences.getValue("abstractLength", null),
			AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH);

		return _abstractLength;
	}

	public long[] getAllAssetCategoryIds() {
		if (_allAssetCategoryIds != null) {
			return _allAssetCategoryIds;
		}

		_allAssetCategoryIds = new long[0];

		long assetCategoryId = ParamUtil.getLong(
			_httpServletRequest, "categoryId");

		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals(
				AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC)) {

			_allAssetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
				_portletPreferences);
		}

		if ((assetCategoryId > 0) &&
			!ArrayUtil.contains(_allAssetCategoryIds, assetCategoryId)) {

			_allAssetCategoryIds = ArrayUtil.append(
				_allAssetCategoryIds, assetCategoryId);
		}

		return _allAssetCategoryIds;
	}

	public String[] getAllAssetTagNames() {
		if (_allAssetTagNames != null) {
			return _allAssetTagNames;
		}

		_allAssetTagNames = new String[0];

		String assetTagName = ParamUtil.getString(_httpServletRequest, "tag");

		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals(
				AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC)) {

			_allAssetTagNames = _assetPublisherHelper.getAssetTagNames(
				_portletPreferences);
		}

		if (Validator.isNotNull(assetTagName) &&
			!ArrayUtil.contains(_allAssetTagNames, assetTagName)) {

			_allAssetTagNames = ArrayUtil.append(
				_allAssetTagNames, assetTagName);
		}

		if (isMergeURLTags()) {
			_allAssetTagNames = ArrayUtil.append(
				_allAssetTagNames, getCompilerTagNames());
		}

		_allAssetTagNames = ArrayUtil.distinct(
			_allAssetTagNames, new StringComparator());

		return _allAssetTagNames;
	}

	public String[] getAllKeywords() {
		if (_allKeywords != null) {
			return _allKeywords;
		}

		_allKeywords = new String[0];

		String keyword = ParamUtil.getString(_httpServletRequest, "keyword");

		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals(
				AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC)) {

			_allKeywords = _assetPublisherHelper.getKeywords(
				_portletPreferences);
		}

		if (Validator.isNotNull(keyword) &&
			!ArrayUtil.contains(_allKeywords, keyword)) {

			_allKeywords = ArrayUtil.append(_allKeywords, keyword);
		}

		_allKeywords = ArrayUtil.distinct(_allKeywords, new StringComparator());

		return _allKeywords;
	}

	public long getAssetCategoryId() {
		if (_assetCategoryId != null) {
			return _assetCategoryId;
		}

		_assetCategoryId = ParamUtil.getLong(_httpServletRequest, "categoryId");

		return _assetCategoryId;
	}

	public List<AssetEntry> getAssetEntries() throws Exception {
		if (isSelectionStyleManual()) {
			return _assetPublisherHelper.getAssetEntries(
				_portletRequest, _portletPreferences,
				_themeDisplay.getPermissionChecker(), getGroupIds(),
				getAllAssetCategoryIds(), getAllAssetTagNames(), false,
				isEnablePermissions());
		}
		else if (isSelectionStyleAssetList()) {
			List<AssetEntry> assetEntries = Collections.emptyList();

			AssetListEntry assetListEntry = fetchAssetListEntry();

			if (assetListEntry != null) {
				assetEntries = _assetListAssetEntryProvider.getAssetEntries(
					assetListEntry, _getSegmentsEntryIds(),
					_getSegmentsAnonymousUserId());
			}
			else {
				if (Validator.isNull(getInfoListProviderKey())) {
					return Collections.emptyList();
				}

				InfoCollectionProvider<AssetEntry> infoCollectionProvider =
					_infoItemServiceTracker.getInfoItemService(
						InfoCollectionProvider.class, getInfoListProviderKey());

				if (infoCollectionProvider == null) {
					return Collections.emptyList();
				}

				InfoPage<AssetEntry> infoPage =
					infoCollectionProvider.getCollectionInfoPage(
						new CollectionQuery());

				assetEntries = (List<AssetEntry>)infoPage.getPageItems();
			}

			if (assetEntries.isEmpty() ||
				(ArrayUtil.isEmpty(getAllAssetCategoryIds()) &&
				 ArrayUtil.isEmpty(getAllAssetTagNames()))) {

				return assetEntries;
			}

			if (!ArrayUtil.isEmpty(getAllAssetCategoryIds())) {
				assetEntries = _filterAssetCategoriesAssetEntries(
					assetEntries, getAllAssetCategoryIds());
			}

			if (!ArrayUtil.isEmpty(getAllAssetTagNames())) {
				assetEntries = _filterAssetTagNamesAssetEntries(
					assetEntries, getAllAssetTagNames());
			}

			return assetEntries;
		}

		return Collections.emptyList();
	}

	public List<AssetEntryAction<?>> getAssetEntryActions(String className) {
		return _assetEntryActionRegistry.getAssetEntryActions(className);
	}

	public String getAssetEntryId() {
		return ParamUtil.getString(_httpServletRequest, "assetEntryId");
	}

	public AssetEntryQuery getAssetEntryQuery() throws Exception {
		if (_assetEntryQuery != null) {
			return _assetEntryQuery;
		}

		AssetListEntry assetListEntry = fetchAssetListEntry();

		if (isSelectionStyleAssetList() && (assetListEntry != null)) {
			_assetEntryQuery = _assetListAssetEntryProvider.getAssetEntryQuery(
				assetListEntry, _getSegmentsEntryIds(),
				_getSegmentsAnonymousUserId());
		}
		else {
			_assetEntryQuery = _assetPublisherHelper.getAssetEntryQuery(
				_portletPreferences, _themeDisplay.getScopeGroupId(),
				_themeDisplay.getLayout(), getAllAssetCategoryIds(),
				getAllAssetTagNames(), getAllKeywords());
		}

		_assetEntryQuery.setEnablePermissions(isEnablePermissions());

		_configureSubtypeFieldFilter(
			_assetEntryQuery, _themeDisplay.getSiteDefaultLocale());

		_assetEntryQuery.setPaginationType(getPaginationType());

		_assetPublisherWebHelper.processAssetEntryQuery(
			_themeDisplay.getUser(), _portletPreferences, _assetEntryQuery);

		_assetPublisherCustomizer.setAssetEntryQueryOptions(
			_assetEntryQuery, _httpServletRequest);

		return _assetEntryQuery;
	}

	public List<AssetEntryResult> getAssetEntryResults() throws Exception {
		if (_assetEntryResults != null) {
			return _assetEntryResults;
		}

		if (isSelectionStyleDynamic()) {
			_assetEntryResults = _assetPublisherHelper.getAssetEntryResults(
				getSearchContainer(), getAssetEntryQuery(),
				_themeDisplay.getLayout(), _portletPreferences,
				getPortletName(), _themeDisplay.getLocale(),
				_themeDisplay.getTimeZone(), _themeDisplay.getCompanyId(),
				_themeDisplay.getScopeGroupId(), _themeDisplay.getUserId(),
				getClassNameIds(), null);

			return _assetEntryResults;
		}

		List<AssetEntry> assetEntries = getAssetEntries();

		if (ListUtil.isEmpty(assetEntries)) {
			return Collections.emptyList();
		}

		SearchContainer<AssetEntry> searchContainer = getSearchContainer();

		searchContainer.setResultsAndTotal(
			() -> assetEntries.subList(
				searchContainer.getStart(), searchContainer.getResultEnd()),
			assetEntries.size());

		List<AssetEntryResult> assetEntryResults = new ArrayList<>();

		assetEntryResults.add(
			new AssetEntryResult(searchContainer.getResults()));

		_assetEntryResults = assetEntryResults;

		return _assetEntryResults;
	}

	public String getAssetLinkBehavior() {
		if (_assetLinkBehavior != null) {
			return _assetLinkBehavior;
		}

		_assetLinkBehavior = GetterUtil.getString(
			_portletPreferences.getValue("assetLinkBehavior", "viewInPortlet"));

		return _assetLinkBehavior;
	}

	public String getAssetListSelectorURL() {
		InfoListItemSelectorCriterion infoListItemSelectorCriterion =
			new InfoListItemSelectorCriterion();

		infoListItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoListItemSelectorReturnType());

		InfoCollectionProviderItemSelectorCriterion
			infoCollectionProviderItemSelectorCriterion =
				new InfoCollectionProviderItemSelectorCriterion();

		infoCollectionProviderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new InfoListProviderItemSelectorReturnType());
		infoCollectionProviderItemSelectorCriterion.setItemType(
			AssetEntry.class.getName());

		PortletURL portletURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_portletRequest),
			getSelectAssetListEventName(), infoListItemSelectorCriterion,
			infoCollectionProviderItemSelectorCriterion);

		return portletURL.toString();
	}

	public String getAssetTagName() {
		if (_assetTagName != null) {
			return _assetTagName;
		}

		_assetTagName = ParamUtil.getString(_httpServletRequest, "tag");

		return _assetTagName;
	}

	public Map<String, Serializable> getAttributes() {
		if (_attributes != null) {
			return _attributes;
		}

		_attributes = new HashMap<>();

		Map<String, String[]> parameters =
			_httpServletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String[] values = entry.getValue();

			if (ArrayUtil.isNotEmpty(values)) {
				String name = entry.getKey();

				if (values.length == 1) {
					_attributes.put(name, values[0]);
				}
				else {
					_attributes.put(name, values);
				}
			}
		}

		return _attributes;
	}

	public JSONArray getAutoFieldRulesJSONArray() {
		String queryLogicIndexesParam = ParamUtil.getString(
			_httpServletRequest, "queryLogicIndexes");

		int[] queryLogicIndexes = null;

		if (Validator.isNotNull(queryLogicIndexesParam)) {
			queryLogicIndexes = StringUtil.split(queryLogicIndexesParam, 0);
		}
		else {
			queryLogicIndexes = new int[0];

			for (int i = 0; true; i++) {
				String queryValues = PrefsParamUtil.getString(
					_portletPreferences, _httpServletRequest,
					"queryValues" + i);

				if (Validator.isNull(queryValues)) {
					break;
				}

				queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, i);
			}

			if (queryLogicIndexes.length == 0) {
				queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, -1);
			}
		}

		JSONArray rulesJSONArray = JSONFactoryUtil.createJSONArray();

		for (int queryLogicIndex : queryLogicIndexes) {
			boolean queryAndOperator = PrefsParamUtil.getBoolean(
				_portletPreferences, _httpServletRequest,
				"queryAndOperator" + queryLogicIndex);

			JSONObject ruleJSONObject = JSONUtil.put(
				"queryAndOperator", queryAndOperator
			).put(
				"queryContains",
				PrefsParamUtil.getBoolean(
					_portletPreferences, _httpServletRequest,
					"queryContains" + queryLogicIndex, true)
			);

			String queryValues = StringUtil.merge(
				_portletPreferences.getValues(
					"queryValues" + queryLogicIndex, new String[0]));

			String queryName = PrefsParamUtil.getString(
				_portletPreferences, _httpServletRequest,
				"queryName" + queryLogicIndex, "assetTags");

			if (Objects.equals(queryName, "assetTags")) {
				String[] tagNames = StringUtil.split(
					queryValues, StringPool.COMMA);

				tagNames = _normalizeAssetTagNames(tagNames);

				tagNames = ParamUtil.getStringValues(
					_httpServletRequest, "queryTagNames" + queryLogicIndex,
					tagNames);

				tagNames = _assetPublisherWebHelper.filterAssetTagNames(
					_themeDisplay.getScopeGroupId(), tagNames);

				queryValues = StringUtil.merge(tagNames);

				if (ArrayUtil.isEmpty(tagNames)) {
					continue;
				}

				List<Map<String, String>> selectedItems = new ArrayList<>();

				for (String tagName : tagNames) {
					selectedItems.add(
						HashMapBuilder.put(
							"label", tagName
						).put(
							"value", tagName
						).build());
				}

				ruleJSONObject.put("selectedItems", selectedItems);
			}
			else if (Objects.equals(queryName, "keywords")) {
				queryValues = ParamUtil.getString(
					_httpServletRequest, "keywords" + queryLogicIndex,
					queryValues);

				String[] keywords = StringUtil.split(queryValues, ",");

				if (ArrayUtil.isEmpty(keywords)) {
					continue;
				}

				List<String> items = new ArrayList<>();

				for (String keyword : keywords) {
					if (keyword.contains(" ")) {
						keyword = StringUtil.quote(keyword, CharPool.QUOTE);
					}

					items.add(keyword);
				}

				Stream<String> stream = items.stream();

				queryValues = stream.collect(
					Collectors.joining(StringPool.SPACE));

				ruleJSONObject.put("selectedItems", queryValues);
			}
			else {
				queryValues = ParamUtil.getString(
					_httpServletRequest, "queryCategoryIds" + queryLogicIndex,
					queryValues);

				List<AssetCategory> categories = _filterAssetCategories(
					GetterUtil.getLongValues(queryValues.split(",")));

				if (ListUtil.isEmpty(categories)) {
					continue;
				}

				List<Map<String, Object>> selectedItems = new ArrayList<>();

				for (AssetCategory category : categories) {
					selectedItems.add(
						HashMapBuilder.<String, Object>put(
							"label",
							category.getTitle(_themeDisplay.getLocale())
						).put(
							"value", category.getCategoryId()
						).build());
				}

				ruleJSONObject.put("selectedItems", selectedItems);
			}

			if (Validator.isNull(queryValues)) {
				continue;
			}

			ruleJSONObject.put(
				"queryValues", queryValues
			).put(
				"type", queryName
			);

			rulesJSONArray.put(ruleJSONObject);
		}

		return rulesJSONArray;
	}

	public long[] getAvailableClassNameIds() {
		if (_availableClassNameIds != null) {
			return _availableClassNameIds;
		}

		_availableClassNameIds = ArrayUtil.filter(
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				_themeDisplay.getCompanyId(), true),
			availableClassNameId -> {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					PortalUtil.getClassName(availableClassNameId));

				return indexer != null;
			});

		return _availableClassNameIds;
	}

	public String getCategorySelectorURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				_httpServletRequest, AssetCategory.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter(
				"eventName",
				_portletResponse.getNamespace() + "selectCategory");
			portletURL.setParameter(
				"selectedCategories", "{selectedCategories}");
			portletURL.setParameter("singleSelect", "{singleSelect}");
			portletURL.setParameter("vocabularyIds", "{vocabularyIds}");

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	public long[] getClassNameIds() throws Exception {
		if (_classNameIds != null) {
			return _classNameIds;
		}

		if (isSelectionStyleAssetList()) {
			AssetEntryQuery assetEntryQuery = getAssetEntryQuery();

			_classNameIds = assetEntryQuery.getClassNameIds();
		}
		else {
			_classNameIds = _assetPublisherHelper.getClassNameIds(
				_portletPreferences, getAvailableClassNameIds());
		}

		return _classNameIds;
	}

	public long[] getClassTypeIds() {
		if (_classTypeIds != null) {
			return _classTypeIds;
		}

		_classTypeIds = GetterUtil.getLongValues(
			_portletPreferences.getValues("classTypeIds", null));

		return _classTypeIds;
	}

	public String[] getCompilerTagNames() {
		if (_compilerTagNames != null) {
			return _compilerTagNames;
		}

		_compilerTagNames = new String[0];

		if (isMergeURLTags()) {
			_compilerTagNames = ParamUtil.getParameterValues(
				_httpServletRequest, "tags");
		}

		return _compilerTagNames;
	}

	public String getDDMStructureDisplayFieldValue() throws Exception {
		if (_ddmStructureDisplayFieldValue != null) {
			return _ddmStructureDisplayFieldValue;
		}

		_setDDMStructure();

		return _ddmStructureDisplayFieldValue;
	}

	public String getDDMStructureFieldLabel() throws Exception {
		if (_ddmStructureFieldLabel != null) {
			return _ddmStructureFieldLabel;
		}

		_setDDMStructure();

		return _ddmStructureFieldLabel;
	}

	public String getDDMStructureFieldName() throws Exception {
		if (_ddmStructureFieldName != null) {
			return _ddmStructureFieldName;
		}

		_setDDMStructure();

		return _ddmStructureFieldName;
	}

	public String getDDMStructureFieldValue() throws Exception {
		if (_ddmStructureFieldValue != null) {
			return _ddmStructureFieldValue;
		}

		_setDDMStructure();

		return _ddmStructureFieldValue;
	}

	public String getDefaultDisplayStyle() {
		return _assetPublisherPortletInstanceConfiguration.
			defaultDisplayStyle();
	}

	public Integer getDelta() {
		return _assetPublisherCustomizer.getDelta(_httpServletRequest);
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = GetterUtil.getString(
			_portletPreferences.getValue(
				"displayStyle",
				_assetPublisherPortletInstanceConfiguration.
					defaultDisplayStyle()));

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != null) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId = GetterUtil.getLong(
			_portletPreferences.getValue("displayStyleGroupId", null),
			_themeDisplay.getScopeGroupId());

		return _displayStyleGroupId;
	}

	public String[] getDisplayStyles() {
		return _assetPublisherPortletInstanceConfiguration.displayStyles();
	}

	public LocalizedValuesMap getEmailAssetEntryAddedBody() {
		return _assetPublisherPortletInstanceConfiguration.
			emailAssetEntryAddedBody();
	}

	public LocalizedValuesMap getEmailAssetEntryAddedSubject() {
		return _assetPublisherPortletInstanceConfiguration.
			emailAssetEntryAddedSubject();
	}

	public String[] getExtensions() {
		if (_extensions != null) {
			return _extensions;
		}

		_extensions = _portletPreferences.getValues(
			"extensions", new String[0]);

		return _extensions;
	}

	public String[] getExtensions(AssetRenderer<?> assetRenderer) {
		final String[] supportedConversions =
			assetRenderer.getSupportedConversions();

		if (supportedConversions == null) {
			return getExtensions();
		}

		return ArrayUtil.filter(
			getExtensions(),
			extension -> ArrayUtil.contains(supportedConversions, extension));
	}

	public long[] getGroupIds() {
		if (_groupIds != null) {
			return _groupIds;
		}

		_groupIds = _assetPublisherHelper.getGroupIds(
			_portletPreferences, _themeDisplay.getScopeGroupId(),
			_themeDisplay.getLayout());

		return _groupIds;
	}

	public String getInfoListProviderKey() {
		if (_infoListProviderKey != null) {
			return _infoListProviderKey;
		}

		_infoListProviderKey = GetterUtil.getString(
			_portletPreferences.getValue("infoListProviderKey", null));

		return _infoListProviderKey;
	}

	public String getInfoListProviderLabel() {
		if (Validator.isNull(getInfoListProviderKey())) {
			return StringPool.BLANK;
		}

		InfoCollectionProvider<AssetEntry> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class, getInfoListProviderKey());

		if (infoCollectionProvider == null) {
			return StringPool.BLANK;
		}

		return infoCollectionProvider.getLabel(_themeDisplay.getLocale());
	}

	public String[] getMetadataFields() {
		if (_metadataFields != null) {
			return _metadataFields;
		}

		String metadataFields = _portletPreferences.getValue(
			"metadataFields", null);

		if (metadataFields == null) {
			_metadataFields = new String[] {"author", "modified-date"};
		}
		else {
			_metadataFields = StringUtil.split(metadataFields);
		}

		return _metadataFields;
	}

	public String getOrderByColumn1() {
		if (_orderByColumn1 != null) {
			return _orderByColumn1;
		}

		_orderByColumn1 = GetterUtil.getString(
			_portletPreferences.getValue("orderByColumn1", "modifiedDate"));

		return _orderByColumn1;
	}

	public String getOrderByColumn2() {
		if (_orderByColumn2 != null) {
			return _orderByColumn2;
		}

		_orderByColumn2 = GetterUtil.getString(
			_portletPreferences.getValue("orderByColumn2", "title"));

		return _orderByColumn2;
	}

	public String getOrderByType1() {
		if (_orderByType1 != null) {
			return _orderByType1;
		}

		_orderByType1 = GetterUtil.getString(
			_portletPreferences.getValue("orderByType1", "DESC"));

		return _orderByType1;
	}

	public String getOrderByType2() {
		if (_orderByType2 != null) {
			return _orderByType2;
		}

		_orderByType2 = GetterUtil.getString(
			_portletPreferences.getValue("orderByType2", "ASC"));

		return _orderByType2;
	}

	public String getPaginationType() {
		if (_paginationType != null) {
			return _paginationType;
		}

		_paginationType = GetterUtil.getString(
			_portletPreferences.getValue("paginationType", "none"));

		if (!ArrayUtil.contains(PAGINATION_TYPES, _paginationType)) {
			_paginationType = PAGINATION_TYPE_NONE;
		}

		return _paginationType;
	}

	public String getPortletName() {
		PortletConfig portletConfig =
			(PortletConfig)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfig == null) {
			return StringPool.BLANK;
		}

		return portletConfig.getPortletName();
	}

	public String getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(
			_httpServletRequest, "portletResource");

		return _portletResource;
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_portletResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		if (getAssetCategoryId() > 0) {
			portletURL.setParameter(
				"categoryId", String.valueOf(getAssetCategoryId()));
		}

		return portletURL;
	}

	public long[] getReferencedModelsGroupIds() throws PortalException {

		// Referenced models are asset subtypes, tags or categories that
		// are used to filter assets and can belong to a different scope of
		// the asset they are associated to

		if (_referencedModelsGroupIds != null) {
			return _referencedModelsGroupIds;
		}

		_referencedModelsGroupIds =
			PortalUtil.getCurrentAndAncestorSiteGroupIds(getGroupIds(), true);

		return _referencedModelsGroupIds;
	}

	public int getRSSDelta() {
		if (_rssDelta != null) {
			return _rssDelta;
		}

		_rssDelta = GetterUtil.getInteger(
			_portletPreferences.getValue("rssDelta", StringPool.BLANK),
			SearchContainer.DEFAULT_DELTA);

		return _rssDelta;
	}

	public String getRSSDisplayStyle() {
		if (_rssDisplayStyle != null) {
			return _rssDisplayStyle;
		}

		_rssDisplayStyle = _portletPreferences.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_ABSTRACT);

		return _rssDisplayStyle;
	}

	public String getRSSFeedType() {
		if (_rssFeedType != null) {
			return _rssFeedType;
		}

		_rssFeedType = _portletPreferences.getValue(
			"rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);

		return _rssFeedType;
	}

	public String getRSSName() {
		if (_rssName != null) {
			return _rssName;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_rssName = _portletPreferences.getValue(
			"rssName", portletDisplay.getTitle());

		return _rssName;
	}

	public Map<Long, List<AssetPublisherAddItemHolder>>
			getScopeAssetPublisherAddItemHolders(int max)
		throws Exception {

		long[] groupIds = getGroupIds();

		if (groupIds.length == 0) {
			return Collections.emptyMap();
		}

		Map<Long, List<AssetPublisherAddItemHolder>>
			scopeAssetPublisherAddItemHolders = new HashMap<>();

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(_portletRequest);
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_portletResponse);

		for (long groupId : groupIds) {
			List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders =
				_assetHelper.getAssetPublisherAddItemHolders(
					liferayPortletRequest, liferayPortletResponse, groupId,
					getClassNameIds(), getClassTypeIds(),
					getAllAssetCategoryIds(), getAllAssetTagNames(),
					_themeDisplay.getURLCurrent());

			if (ListUtil.isNotEmpty(assetPublisherAddItemHolders)) {
				scopeAssetPublisherAddItemHolders.put(
					groupId, assetPublisherAddItemHolders);
			}

			if (scopeAssetPublisherAddItemHolders.size() > max) {
				break;
			}
		}

		return scopeAssetPublisherAddItemHolders;
	}

	public SearchContainer<AssetEntry> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<AssetEntry> searchContainer = new SearchContainer(
			_portletRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
			getDelta(), getPortletURL(), null, null);

		if (!isPaginationTypeNone()) {
			searchContainer.setDelta(getDelta());
			searchContainer.setDeltaConfigurable(false);
		}

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getSelectAssetListEventName() {
		String portletNamespace = PortalUtil.getPortletNamespace(
			AssetPublisherPortletKeys.ASSET_PUBLISHER);

		return portletNamespace + "selectAssetList";
	}

	public String getSelectionStyle() {
		if (_selectionStyle != null) {
			return _selectionStyle;
		}

		_selectionStyle = GetterUtil.getString(
			_portletPreferences.getValue("selectionStyle", null),
			AssetPublisherSelectionStyleConfigurationUtil.
				defaultSelectionStyle());

		return _selectionStyle;
	}

	public String getSocialBookmarksDisplayStyle() {
		if (_socialBookmarksDisplayStyle != null) {
			return _socialBookmarksDisplayStyle;
		}

		_socialBookmarksDisplayStyle = _portletPreferences.getValue(
			"socialBookmarksDisplayStyle", null);

		return _socialBookmarksDisplayStyle;
	}

	public String getSocialBookmarksTypes() {
		if (_socialBookmarksTypes == null) {
			_socialBookmarksTypes = GetterUtil.getString(
				_portletPreferences.getValue("socialBookmarksTypes", null));
		}

		return _socialBookmarksTypes;
	}

	public String getTagSelectorURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				_httpServletRequest, AssetTag.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter(
				"groupIds", StringUtil.merge(getGroupIds()));
			portletURL.setParameter(
				"eventName", _portletResponse.getNamespace() + "selectTag");
			portletURL.setParameter("selectedTagNames", "{selectedTagNames}");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	public List<Long> getVocabularyIds() throws PortalException {
		long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
			getReferencedModelsGroupIds());

		List<AssetVocabulary> vocabularies = ListUtil.filter(
			AssetVocabularyServiceUtil.getGroupsVocabularies(groupIds),
			vocabulary -> {
				long[] classNameIds = vocabulary.getSelectedClassNameIds();

				for (long classNameId : classNameIds) {
					if (classNameId == 0) {
						return true;
					}

					AssetRendererFactory<?> assetRendererFactory =
						AssetRendererFactoryRegistryUtil.
							getAssetRendererFactoryByClassNameId(classNameId);

					if (assetRendererFactory.isSelectable()) {
						return true;
					}
				}

				return false;
			});

		return ListUtil.toList(
			vocabularies, AssetVocabulary.VOCABULARY_ID_ACCESSOR);
	}

	public AssetEntry incrementViewCounter(AssetEntry assetEntry)
		throws PortalException {

		// Dynamically created asset entries are never persisted so incrementing
		// the view counter breaks

		if ((assetEntry == null) || assetEntry.isNew() ||
			!assetEntry.isVisible()) {

			return assetEntry;
		}

		if (isEnablePermissions()) {
			return AssetEntryServiceUtil.incrementViewCounter(
				assetEntry.getCompanyId(), assetEntry.getClassName(),
				assetEntry.getClassPK());
		}

		return AssetEntryLocalServiceUtil.incrementViewCounter(
			assetEntry.getCompanyId(), _themeDisplay.getUserId(),
			assetEntry.getClassName(), assetEntry.getClassPK());
	}

	public Boolean isAnyAssetType() {
		if (_anyAssetType != null) {
			return _anyAssetType;
		}

		_anyAssetType = GetterUtil.getBoolean(
			_portletPreferences.getValue("anyAssetType", null), true);

		return _anyAssetType;
	}

	public boolean isAssetLinkBehaviorShowFullContent() {
		String assetLinkBehavior = getAssetLinkBehavior();

		return assetLinkBehavior.equals("showFullContent");
	}

	public boolean isAssetLinkBehaviorViewInPortlet() {
		String assetLinkBehavior = getAssetLinkBehavior();

		return assetLinkBehavior.equals("viewInPortlet");
	}

	public boolean isDefaultAssetPublisher() {
		if (_defaultAssetPublisher != null) {
			return _defaultAssetPublisher;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_defaultAssetPublisher =
			_assetPublisherWebHelper.isDefaultAssetPublisher(
				_themeDisplay.getLayout(), portletDisplay.getId(),
				getPortletResource());

		return _defaultAssetPublisher;
	}

	public boolean isEnableCommentRatings() {
		if (_enableCommentRatings != null) {
			return _enableCommentRatings;
		}

		_enableCommentRatings = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableCommentRatings", null));

		return _enableCommentRatings;
	}

	public boolean isEnableComments() {
		if (_enableComments != null) {
			return _enableComments;
		}

		_enableComments = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableComments", null));

		return _enableComments;
	}

	public Boolean isEnableConversions() {
		if (_enableConversions != null) {
			return _enableConversions;
		}

		_enableConversions =
			isOpenOfficeServerEnabled() &&
			ArrayUtil.isNotEmpty(getExtensions());

		return _enableConversions;
	}

	public boolean isEnabledAutoscroll() {
		return _assetPublisherWebConfiguration.enableAutoscroll();
	}

	public boolean isEnableFlags() {
		if (_enableFlags != null) {
			return _enableFlags;
		}

		_enableFlags = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableFlags", null));

		return _enableFlags;
	}

	public Boolean isEnablePermissions() {
		return _assetPublisherCustomizer.isEnablePermissions(
			_httpServletRequest);
	}

	public boolean isEnablePrint() {
		if (_enablePrint != null) {
			return _enablePrint;
		}

		_enablePrint = GetterUtil.getBoolean(
			_portletPreferences.getValue("enablePrint", null));

		return _enablePrint;
	}

	public boolean isEnableRatings() {
		if (_enableRatings != null) {
			return _enableRatings;
		}

		_enableRatings = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableRatings", null));

		return _enableRatings;
	}

	public boolean isEnableRelatedAssets() {
		if (_enableRelatedAssets != null) {
			return _enableRelatedAssets;
		}

		_enableRelatedAssets = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableRelatedAssets", null),
			_isShowRelatedAssets());

		return _enableRelatedAssets;
	}

	public boolean isEnableRSS() {
		if (_enableRSS != null) {
			return _enableRSS;
		}

		_enableRSS = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableRss", null));

		return _enableRSS;
	}

	public boolean isEnableSetAsDefaultAssetPublisher() {
		Layout layout = _themeDisplay.getLayout();

		if (layout.isTypeAssetDisplay()) {
			return false;
		}

		if (layout.isTypeContent()) {
			Layout publishedLayout = LayoutLocalServiceUtil.fetchLayout(
				layout.getClassPK());

			if (publishedLayout == null) {
				return true;
			}

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(
						publishedLayout.getPlid());

			if (layoutPageTemplateEntry != null) {
				return false;
			}
		}

		String rootPortletId = PortletIdCodec.decodePortletName(
			getPortletResource());

		if (rootPortletId.equals(AssetPublisherPortletKeys.ASSET_PUBLISHER)) {
			return true;
		}

		return false;
	}

	public boolean isEnableSubscriptions() {
		if (_enableSubscriptions != null) {
			return _enableSubscriptions;
		}

		_enableSubscriptions = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableSubscriptions", null));

		return _enableSubscriptions;
	}

	public boolean isEnableTagBasedNavigation() {
		if (_enableTagBasedNavigation != null) {
			return _enableTagBasedNavigation;
		}

		_enableTagBasedNavigation = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableTagBasedNavigation", null));

		return _enableTagBasedNavigation;
	}

	public boolean isEnableViewCountIncrement() {
		if (_enableViewCountIncrement != null) {
			return _enableViewCountIncrement;
		}

		_enableViewCountIncrement = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableViewCountIncrement", null));

		return _enableViewCountIncrement;
	}

	public boolean isExcludeZeroViewCount() {
		if (_excludeZeroViewCount != null) {
			return _excludeZeroViewCount;
		}

		_excludeZeroViewCount = GetterUtil.getBoolean(
			_portletPreferences.getValue("excludeZeroViewCount", null));

		return _excludeZeroViewCount;
	}

	public boolean isMergeURLTags() {
		if (_mergeURLTags != null) {
			return _mergeURLTags;
		}

		_mergeURLTags = GetterUtil.getBoolean(
			_portletPreferences.getValue("mergeUrlTags", null), true);

		return _mergeURLTags;
	}

	public boolean isOpenOfficeServerEnabled() {
		return DocumentConversionUtil.isEnabled();
	}

	public boolean isOrderingByTitleEnabled() {
		return _assetPublisherCustomizer.isOrderingByTitleEnabled(
			_httpServletRequest);
	}

	public boolean isPaginationTypeNone() {
		if (Objects.equals(getPaginationType(), PAGINATION_TYPE_NONE)) {
			return true;
		}

		return false;
	}

	public boolean isPaginationTypeSelected(String paginationType) {
		String curPaginationType = getPaginationType();

		return curPaginationType.equals(paginationType);
	}

	public boolean isSearchWithIndex() {
		return _assetPublisherWebConfiguration.searchWithIndex();
	}

	public boolean isSelectionStyleAssetList() {
		if (Objects.equals(
				getSelectionStyle(),
				AssetPublisherSelectionStyleConstants.TYPE_ASSET_LIST) ||
			Objects.equals(
				getSelectionStyle(),
				AssetPublisherSelectionStyleConstants.
					TYPE_ASSET_LIST_PROVIDER)) {

			return true;
		}

		return false;
	}

	public boolean isSelectionStyleDynamic() {
		if (Objects.equals(
				getSelectionStyle(),
				AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC)) {

			return true;
		}

		return false;
	}

	public boolean isSelectionStyleManual() {
		if (Objects.equals(
				getSelectionStyle(),
				AssetPublisherSelectionStyleConstants.TYPE_MANUAL)) {

			return true;
		}

		return false;
	}

	public boolean isShowAddContentButton() {
		if (_showAddContentButton != null) {
			return _showAddContentButton;
		}

		_showAddContentButton = GetterUtil.getBoolean(
			_portletPreferences.getValue("showAddContentButton", null), true);

		return _showAddContentButton;
	}

	public Boolean isShowAssetTitle() {
		if (_showAssetTitle != null) {
			return _showAssetTitle;
		}

		_showAssetTitle = GetterUtil.getBoolean(
			_portletPreferences.getValue("showAssetTitle", null), true);

		return _showAssetTitle;
	}

	public boolean isShowAuthor() {
		if (_showAuthor != null) {
			return _showAuthor;
		}

		if (ArrayUtil.contains(getMetadataFields(), "author")) {
			_showAuthor = true;

			return _showAuthor;
		}

		_showAuthor = false;

		return _showAuthor;
	}

	public Boolean isShowAvailableLocales() {
		if (_showAvailableLocales != null) {
			return _showAvailableLocales;
		}

		_showAvailableLocales = GetterUtil.getBoolean(
			_portletPreferences.getValue("showAvailableLocales", null));

		return _showAvailableLocales;
	}

	public boolean isShowCategories() {
		if (_showCategories != null) {
			return _showCategories;
		}

		if (ArrayUtil.contains(getMetadataFields(), "categories")) {
			_showCategories = true;

			return _showCategories;
		}

		_showCategories = false;

		return _showCategories;
	}

	public Boolean isShowContextLink() {
		if (_showContextLink != null) {
			return _showContextLink;
		}

		_showContextLink = GetterUtil.getBoolean(
			_portletPreferences.getValue("showContextLink", null), true);

		return _showContextLink;
	}

	public Boolean isShowContextLink(long groupId, String portletId)
		throws PortalException {

		if (_showContextLink != null) {
			return _showContextLink;
		}

		_showContextLink = isShowContextLink();

		if (_showContextLink &&
			(PortalUtil.getPlidFromPortletId(groupId, portletId) == 0)) {

			_showContextLink = false;
		}

		return _showContextLink;
	}

	public boolean isShowCreateDate() {
		if (_showCreateDate != null) {
			return _showCreateDate;
		}

		if (ArrayUtil.contains(getMetadataFields(), "create-date")) {
			_showCreateDate = true;

			return _showCreateDate;
		}

		_showCreateDate = false;

		return _showCreateDate;
	}

	public boolean isShowEnableAddContentButton() {
		return _assetPublisherCustomizer.isShowEnableAddContentButton(
			_httpServletRequest);
	}

	public Boolean isShowEnablePermissions() {
		if (_assetPublisherWebConfiguration.searchWithIndex()) {
			return false;
		}

		return _assetPublisherWebConfiguration.permissionCheckingConfigurable();
	}

	public boolean isShowEnableRelatedAssets() {
		return _assetPublisherCustomizer.isShowEnableRelatedAssets(
			_httpServletRequest);
	}

	public boolean isShowExpirationDate() {
		if (_showExpirationDate != null) {
			return _showExpirationDate;
		}

		if (ArrayUtil.contains(getMetadataFields(), "expiration-date")) {
			_showExpirationDate = true;

			return _showExpirationDate;
		}

		_showExpirationDate = false;

		return _showExpirationDate;
	}

	public boolean isShowExtraInfo() {
		if (_showExtraInfo != null) {
			return _showExtraInfo;
		}

		_showExtraInfo = GetterUtil.getBoolean(
			_portletPreferences.getValue("showExtraInfo", null), true);

		return _showExtraInfo;
	}

	public boolean isShowMetadataDescriptions() {
		if (_showMetadataDescriptions != null) {
			return _showMetadataDescriptions;
		}

		_showMetadataDescriptions = GetterUtil.getBoolean(
			_portletPreferences.getValue("showMetadataDescriptions", null),
			true);

		return _showMetadataDescriptions;
	}

	public boolean isShowModifiedDate() {
		if (_showModifiedDate != null) {
			return _showModifiedDate;
		}

		if (ArrayUtil.contains(getMetadataFields(), "modified-date")) {
			_showModifiedDate = true;

			return _showModifiedDate;
		}

		_showModifiedDate = false;

		return _showModifiedDate;
	}

	public boolean isShowOnlyLayoutAssets() {
		if (_showOnlyLayoutAssets != null) {
			return _showOnlyLayoutAssets;
		}

		_showOnlyLayoutAssets = GetterUtil.getBoolean(
			_portletPreferences.getValue("showOnlyLayoutAssets", null));

		return _showOnlyLayoutAssets;
	}

	public boolean isShowPriority() {
		if (_showPriority != null) {
			return _showPriority;
		}

		if (ArrayUtil.contains(getMetadataFields(), "priority")) {
			_showPriority = true;

			return _showPriority;
		}

		_showPriority = false;

		return _showPriority;
	}

	public boolean isShowPublishDate() {
		if (_showPublishDate != null) {
			return _showPublishDate;
		}

		if (ArrayUtil.contains(getMetadataFields(), "publish-date")) {
			_showPublishDate = true;

			return _showPublishDate;
		}

		_showPublishDate = false;

		return _showPublishDate;
	}

	public boolean isShowSubtypeFieldsFilter() {
		return _assetPublisherCustomizer.isShowSubtypeFieldsFilter(
			_httpServletRequest);
	}

	public boolean isShowTags() {
		if (_showTags != null) {
			return _showTags;
		}

		if (ArrayUtil.contains(getMetadataFields(), "tags")) {
			_showTags = true;

			return _showTags;
		}

		_showTags = false;

		return _showTags;
	}

	public boolean isShowViewCount() {
		if (_showViewCount != null) {
			return _showViewCount;
		}

		if (ArrayUtil.contains(getMetadataFields(), "view-count")) {
			_showViewCount = true;

			return _showViewCount;
		}

		_showViewCount = false;

		return _showViewCount;
	}

	public boolean isSubscriptionEnabled() throws PortalException {
		String portletName = getPortletName();

		if (Objects.equals(
				portletName, AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) ||
			Objects.equals(
				portletName, AssetPublisherPortletKeys.MOST_VIEWED_ASSETS) ||
			Objects.equals(
				portletName, AssetPublisherPortletKeys.RECENT_CONTENT) ||
			Objects.equals(
				portletName, AssetPublisherPortletKeys.RELATED_ASSETS) ||
			!_assetPublisherWebHelper.getEmailAssetEntryAddedEnabled(
				_portletPreferences)) {

			return false;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		if (!PortletPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), 0,
				_themeDisplay.getLayout(), portletDisplay.getId(),
				ActionKeys.SUBSCRIBE, false, false)) {

			return false;
		}

		return true;
	}

	public boolean isSubtypeFieldsFilterEnabled() {
		if (_subtypeFieldsFilterEnabled != null) {
			return _subtypeFieldsFilterEnabled;
		}

		_subtypeFieldsFilterEnabled = GetterUtil.getBoolean(
			_portletPreferences.getValue(
				"subtypeFieldsFilterEnabled", Boolean.FALSE.toString()));

		return _subtypeFieldsFilterEnabled;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setLayoutAssetEntry(AssetEntry assetEntry)
		throws PortalException {

		if (_httpServletRequest.getAttribute(WebKeys.LAYOUT_ASSET_ENTRY) !=
				null) {

			return;
		}

		String defaultAssetPublisherPortletId =
			_assetPublisherWebHelper.getDefaultAssetPublisherId(
				_themeDisplay.getLayout());

		if (isDefaultAssetPublisher() ||
			Validator.isNull(defaultAssetPublisherPortletId) ||
			!PortletPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _themeDisplay.getLayout(),
				defaultAssetPublisherPortletId, ActionKeys.VIEW)) {

			_httpServletRequest.setAttribute(
				WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
		}
	}

	public void setPageKeywords() {
		if (getAssetCategoryId() > 0) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					getAssetCategoryId());

			if (assetCategory != null) {
				PortalUtil.setPageKeywords(
					HtmlUtil.escape(
						assetCategory.getTitle(_themeDisplay.getLocale())),
					_httpServletRequest);
			}
		}

		if (Validator.isNotNull(getAssetTagName())) {
			PortalUtil.setPageKeywords(getAssetTagName(), _httpServletRequest);
		}
	}

	public void setSelectionStyle(String selectionStyle) {
		_selectionStyle = selectionStyle;
	}

	private void _configureSubtypeFieldFilter(
			AssetEntryQuery assetEntryQuery, Locale locale)
		throws Exception {

		long[] classNameIds = getClassNameIds();
		long[] classTypeIds = getClassTypeIds();

		if (!isSubtypeFieldsFilterEnabled() || (classNameIds.length != 1) ||
			(classTypeIds.length != 1) ||
			Validator.isNull(getDDMStructureFieldName()) ||
			Validator.isNull(getDDMStructureFieldValue())) {

			return;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameIds[0]);

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(
			classTypeIds[0], locale);

		ClassTypeField classTypeField = classType.getClassTypeField(
			getDDMStructureFieldName());

		assetEntryQuery.setAttribute(
			"ddmStructureFieldName",
			_assetPublisherWebHelper.encodeName(
				classTypeField.getClassTypeId(),
				classTypeField.getFieldReference(), locale));

		assetEntryQuery.setAttribute(
			"ddmStructureFieldValue", getDDMStructureFieldValue());
	}

	private List<AssetCategory> _filterAssetCategories(long[] categoryIds) {
		List<AssetCategory> filteredCategories = new ArrayList<>();

		for (long categoryId : categoryIds) {
			AssetCategory category =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(categoryId);

			if (category == null) {
				continue;
			}

			filteredCategories.add(category);
		}

		return filteredCategories;
	}

	private List<AssetEntry> _filterAssetCategoriesAssetEntries(
		List<AssetEntry> assetEntries, long[] assetCategoryIds) {

		List<AssetEntry> filteredAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			if (ArrayUtil.containsAll(
					assetEntry.getCategoryIds(), assetCategoryIds)) {

				filteredAssetEntries.add(assetEntry);
			}
		}

		return filteredAssetEntries;
	}

	private List<AssetEntry> _filterAssetTagNamesAssetEntries(
		List<AssetEntry> assetEntries, String[] assetTagNames) {

		List<AssetEntry> filteredAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			List<AssetTag> assetTags = assetEntry.getTags();

			String[] assetEntryAssetTagNames = new String[assetTags.size()];

			for (int i = 0; i < assetTags.size(); i++) {
				AssetTag assetTag = assetTags.get(i);

				assetEntryAssetTagNames[i] = assetTag.getName();
			}

			if (ArrayUtil.containsAll(assetEntryAssetTagNames, assetTagNames)) {
				filteredAssetEntries.add(assetEntry);
			}
		}

		return filteredAssetEntries;
	}

	private String _getSegmentsAnonymousUserId() {
		return GetterUtil.getString(
			_portletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_ANONYMOUS_USER_ID));
	}

	private long[] _getSegmentsEntryIds() {
		return _segmentsEntryRetriever.getSegmentsEntryIds(
			_themeDisplay.getScopeGroupId(), _themeDisplay.getUserId(),
			_requestContextMapper.map(
				PortalUtil.getHttpServletRequest(_portletRequest)));
	}

	private boolean _isShowRelatedAssets() {
		if (_showRelatedAssets != null) {
			return _showRelatedAssets;
		}

		_showRelatedAssets = ParamUtil.getBoolean(
			_httpServletRequest, "showRelatedAssets");

		return _showRelatedAssets;
	}

	private String[] _normalizeAssetTagNames(String[] assetTagNames) {
		if (ArrayUtil.isEmpty(assetTagNames)) {
			return assetTagNames;
		}

		for (int i = 0; i < assetTagNames.length; i++) {
			assetTagNames[i] = StringUtil.toLowerCase(
				StringUtil.trim(assetTagNames[i]));
		}

		return assetTagNames;
	}

	private void _setDDMStructure() throws Exception {
		_ddmStructureDisplayFieldValue = StringPool.BLANK;
		_ddmStructureFieldLabel = StringPool.BLANK;
		_ddmStructureFieldName = StringPool.BLANK;
		_ddmStructureFieldValue = null;

		long[] classNameIds = getClassNameIds();
		long[] classTypeIds = getClassTypeIds();

		if (!isSubtypeFieldsFilterEnabled() || (classNameIds.length != 1) ||
			(classTypeIds.length != 1)) {

			return;
		}

		_ddmStructureDisplayFieldValue = ParamUtil.getString(
			_httpServletRequest, "ddmStructureDisplayFieldValue",
			_portletPreferences.getValue(
				"ddmStructureDisplayFieldValue", StringPool.BLANK));

		_ddmStructureFieldName = ParamUtil.getString(
			_httpServletRequest, "ddmStructureFieldName",
			_portletPreferences.getValue(
				"ddmStructureFieldName", StringPool.BLANK));
		_ddmStructureFieldValue = ParamUtil.getString(
			_httpServletRequest, "ddmStructureFieldValue",
			_portletPreferences.getValue(
				"ddmStructureFieldValue", StringPool.BLANK));

		if (Validator.isNotNull(_ddmStructureFieldName) &&
			Validator.isNotNull(_ddmStructureFieldValue)) {

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(classNameIds[0]);

			ClassTypeReader classTypeReader =
				assetRendererFactory.getClassTypeReader();

			ClassType classType = classTypeReader.getClassType(
				classTypeIds[0], _themeDisplay.getLocale());

			ClassTypeField classTypeField = classType.getClassTypeField(
				_ddmStructureFieldName);

			_ddmStructureFieldLabel = classTypeField.getLabel();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherDisplayContext.class);

	private Integer _abstractLength;
	private long[] _allAssetCategoryIds;
	private String[] _allAssetTagNames;
	private String[] _allKeywords;
	private Boolean _anyAssetType;
	private Long _assetCategoryId;
	private final AssetEntryActionRegistry _assetEntryActionRegistry;
	private AssetEntryQuery _assetEntryQuery;
	private List<AssetEntryResult> _assetEntryResults;
	private final AssetHelper _assetHelper;
	private String _assetLinkBehavior;
	private final AssetListAssetEntryProvider _assetListAssetEntryProvider;
	private AssetListEntry _assetListEntry;
	private final AssetPublisherCustomizer _assetPublisherCustomizer;
	private final AssetPublisherHelper _assetPublisherHelper;
	private final AssetPublisherPortletInstanceConfiguration
		_assetPublisherPortletInstanceConfiguration;
	private final AssetPublisherWebConfiguration
		_assetPublisherWebConfiguration;
	private final AssetPublisherWebHelper _assetPublisherWebHelper;
	private String _assetTagName;
	private Map<String, Serializable> _attributes;
	private long[] _availableClassNameIds;
	private long[] _classNameIds;
	private long[] _classTypeIds;
	private String[] _compilerTagNames;
	private String _ddmStructureDisplayFieldValue;
	private String _ddmStructureFieldLabel;
	private String _ddmStructureFieldName;
	private String _ddmStructureFieldValue;
	private Boolean _defaultAssetPublisher;
	private String _displayStyle;
	private Long _displayStyleGroupId;
	private Boolean _enableCommentRatings;
	private Boolean _enableComments;
	private Boolean _enableConversions;
	private Boolean _enableFlags;
	private Boolean _enablePrint;
	private Boolean _enableRatings;
	private Boolean _enableRelatedAssets;
	private Boolean _enableRSS;
	private Boolean _enableSubscriptions;
	private Boolean _enableTagBasedNavigation;
	private Boolean _enableViewCountIncrement;
	private Boolean _excludeZeroViewCount;
	private String[] _extensions;
	private long[] _groupIds;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private String _infoListProviderKey;
	private final ItemSelector _itemSelector;
	private Boolean _mergeURLTags;
	private String[] _metadataFields;
	private String _orderByColumn1;
	private String _orderByColumn2;
	private String _orderByType1;
	private String _orderByType2;
	private String _paginationType;
	private final PortletPreferences _portletPreferences;
	private final PortletRequest _portletRequest;
	private String _portletResource;
	private final PortletResponse _portletResponse;
	private long[] _referencedModelsGroupIds;
	private final RequestContextMapper _requestContextMapper;
	private Integer _rssDelta;
	private String _rssDisplayStyle;
	private String _rssFeedType;
	private String _rssName;
	private SearchContainer<AssetEntry> _searchContainer;
	private final SegmentsEntryRetriever _segmentsEntryRetriever;
	private String _selectionStyle;
	private Boolean _showAddContentButton;
	private Boolean _showAssetTitle;
	private Boolean _showAuthor;
	private Boolean _showAvailableLocales;
	private Boolean _showCategories;
	private Boolean _showContextLink;
	private Boolean _showCreateDate;
	private Boolean _showExpirationDate;
	private Boolean _showExtraInfo;
	private Boolean _showMetadataDescriptions;
	private Boolean _showModifiedDate;
	private Boolean _showOnlyLayoutAssets;
	private Boolean _showPriority;
	private Boolean _showPublishDate;
	private Boolean _showRelatedAssets;
	private Boolean _showTags;
	private Boolean _showViewCount;
	private String _socialBookmarksDisplayStyle;
	private String _socialBookmarksTypes;
	private Boolean _subtypeFieldsFilterEnabled;
	private final ThemeDisplay _themeDisplay;

}