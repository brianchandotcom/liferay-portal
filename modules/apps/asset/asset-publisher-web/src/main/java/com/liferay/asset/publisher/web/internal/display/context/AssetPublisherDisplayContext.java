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
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.util.AssetEntryResult;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.action.AssetEntryActionRegistry;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherPortletInstanceConfiguration;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.internal.util.AssetPublisherCustomizer;
import com.liferay.asset.publisher.web.internal.util.AssetPublisherWebUtil;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.AssetPublisherAddItemHolder;
import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.info.provider.DefaultInfoListProviderContext;
import com.liferay.info.provider.InfoListProvider;
import com.liferay.info.provider.InfoListProviderTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
import com.liferay.segments.constants.SegmentsWebKeys;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
			AssetPublisherCustomizer assetPublisherCustomizer,
			AssetPublisherHelper assetPublisherHelper,
			AssetPublisherWebConfiguration assetPublisherWebConfiguration,
			AssetPublisherWebUtil assetPublisherWebUtil,
			InfoListProviderTracker infoListProviderTracker,
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortletPreferences portletPreferences)
		throws ConfigurationException {

		_assetEntryActionRegistry = assetEntryActionRegistry;
		_assetHelper = assetHelper;
		_assetPublisherCustomizer = assetPublisherCustomizer;
		_assetPublisherHelper = assetPublisherHelper;
		_assetPublisherWebConfiguration = assetPublisherWebConfiguration;
		_assetPublisherWebUtil = assetPublisherWebUtil;
		_infoListProviderTracker = infoListProviderTracker;
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_portletPreferences = portletPreferences;

		_themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_assetPublisherPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				AssetPublisherPortletInstanceConfiguration.class);

		_request = PortalUtil.getHttpServletRequest(portletRequest);
	}

	public AssetListEntry fetchAssetListEntry() throws PortalException {
		if (_assetListEntry != null) {
			return _assetListEntry;
		}

		long assetListEntryId = GetterUtil.getLong(
			_portletPreferences.getValue("assetListEntryId", null));

		_assetListEntry = AssetListEntryServiceUtil.fetchAssetListEntry(
			assetListEntryId);

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

		long assetCategoryId = ParamUtil.getLong(_request, "categoryId");

		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("dynamic")) {
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

		String assetTagName = ParamUtil.getString(_request, "tag");

		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("dynamic")) {
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

	public long getAssetCategoryId() {
		if (_assetCategoryId != null) {
			return _assetCategoryId;
		}

		_assetCategoryId = ParamUtil.getLong(_request, "categoryId");

		return _assetCategoryId;
	}

	public List<AssetEntry> getAssetEntries() throws Exception {
		AssetListEntry assetListEntry = fetchAssetListEntry();

		if (isSelectionStyleManual()) {
			return _assetPublisherHelper.getAssetEntries(
				_portletRequest, _portletPreferences,
				_themeDisplay.getPermissionChecker(), getGroupIds(),
				getAllAssetCategoryIds(), getAllAssetTagNames(), false,
				isEnablePermissions());
		}
		else if (isSelectionStyleAssetList() && (assetListEntry != null)) {
			return assetListEntry.getAssetEntries(_getSegmentsEntryIds());
		}
		else if (isSelectionStyleAssetListProvider()) {
			String infoListProviderClassName = GetterUtil.getString(
				_portletPreferences.getValue(
					"infoListProviderClassName", null));

			if (Validator.isNull(infoListProviderClassName)) {
				return Collections.emptyList();
			}

			InfoListProvider infoListProvider =
				_infoListProviderTracker.getInfoListProvider(
					infoListProviderClassName);

			if (infoListProvider == null) {
				return Collections.emptyList();
			}

			DefaultInfoListProviderContext defaultInfoListProviderContext =
				new DefaultInfoListProviderContext(
					_themeDisplay.getScopeGroup(), _themeDisplay.getUser());

			AssetEntry assetEntry = (AssetEntry)_portletRequest.getAttribute(
				WebKeys.LAYOUT_ASSET_ENTRY);

			defaultInfoListProviderContext.setAssetEntry(assetEntry);

			defaultInfoListProviderContext.setLayout(_themeDisplay.getLayout());

			return infoListProvider.getInfoList(defaultInfoListProviderContext);
		}

		return Collections.emptyList();
	}

	public List<AssetEntryAction> getAssetEntryActions(String className) {
		return _assetEntryActionRegistry.getAssetEntryActions(className);
	}

	public List<InfoListProvider> getAssetEntryInfoListProviders() {
		return _infoListProviderTracker.getInfoListProviders(AssetEntry.class);
	}

	public AssetEntryQuery getAssetEntryQuery() throws Exception {
		if (_assetEntryQuery != null) {
			return _assetEntryQuery;
		}

		AssetListEntry assetListEntry = fetchAssetListEntry();

		if (isSelectionStyleAssetList() && (assetListEntry != null)) {
			_assetEntryQuery = assetListEntry.getAssetEntryQuery(
				_getSegmentsEntryIds());
		}
		else {
			_assetEntryQuery = _assetPublisherHelper.getAssetEntryQuery(
				_portletPreferences, _themeDisplay.getScopeGroupId(),
				_themeDisplay.getLayout(), getAllAssetCategoryIds(),
				getAllAssetTagNames());
		}

		_assetEntryQuery.setEnablePermissions(isEnablePermissions());

		configureSubtypeFieldFilter(
			_assetEntryQuery, _themeDisplay.getLocale());

		_assetEntryQuery.setPaginationType(getPaginationType());

		_assetPublisherWebUtil.processAssetEntryQuery(
			_themeDisplay.getUser(), _portletPreferences, _assetEntryQuery);

		_assetPublisherCustomizer.setAssetEntryQueryOptions(
			_assetEntryQuery, _request);

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

		List<AssetEntryResult> assetEntryResults = null;

		SearchContainer searchContainer = getSearchContainer();

		searchContainer.setTotal(assetEntries.size());

		assetEntries = assetEntries.subList(
			searchContainer.getStart(), searchContainer.getResultEnd());

		searchContainer.setResults(assetEntries);

		assetEntryResults = new ArrayList<>();

		assetEntryResults.add(new AssetEntryResult(assetEntries));

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

	public String getAssetListSelectorURL() throws Exception {
		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_request, AssetListEntry.class.getName(),
			PortletProvider.Action.BROWSE);

		AssetListEntry assetListEntry = fetchAssetListEntry();

		if (assetListEntry != null) {
			portletURL.setParameter(
				"assetListEntryId",
				String.valueOf(assetListEntry.getAssetListEntryId()));
		}

		portletURL.setParameter("eventName", getSelectAssetListEventName());

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public String getAssetTagName() {
		if (_assetTagName != null) {
			return _assetTagName;
		}

		_assetTagName = ParamUtil.getString(_request, "tag");

		return _assetTagName;
	}

	public Map<String, Serializable> getAttributes() {
		if (_attributes != null) {
			return _attributes;
		}

		_attributes = new HashMap<>();

		Map<String, String[]> parameters = _request.getParameterMap();

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
			_request, "queryLogicIndexes");

		int[] queryLogicIndexes = null;

		if (Validator.isNotNull(queryLogicIndexesParam)) {
			queryLogicIndexes = StringUtil.split(queryLogicIndexesParam, 0);
		}
		else {
			queryLogicIndexes = new int[0];

			for (int i = 0; true; i++) {
				String queryValues = PrefsParamUtil.getString(
					_portletPreferences, _request, "queryValues" + i);

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
			JSONObject ruleJSONObject = JSONFactoryUtil.createJSONObject();

			boolean queryAndOperator = PrefsParamUtil.getBoolean(
				_portletPreferences, _request,
				"queryAndOperator" + queryLogicIndex);

			ruleJSONObject.put("queryAndOperator", queryAndOperator);

			boolean queryContains = PrefsParamUtil.getBoolean(
				_portletPreferences, _request,
				"queryContains" + queryLogicIndex, true);

			ruleJSONObject.put("queryContains", queryContains);

			String queryValues = StringUtil.merge(
				_portletPreferences.getValues(
					"queryValues" + queryLogicIndex, new String[0]));

			String queryName = PrefsParamUtil.getString(
				_portletPreferences, _request, "queryName" + queryLogicIndex,
				"assetTags");

			if (Objects.equals(queryName, "assetTags")) {
				queryValues = ParamUtil.getString(
					_request, "queryTagNames" + queryLogicIndex, queryValues);

				queryValues = _assetPublisherWebUtil.filterAssetTagNames(
					_themeDisplay.getScopeGroupId(), queryValues);

				List<Map<String, String>> selectedItems = new ArrayList<>();

				String[] tagNames = StringUtil.split(
					queryValues, StringPool.COMMA);

				for (String tagName : tagNames) {
					Map<String, String> item = new HashMap<>();

					item.put("label", tagName);
					item.put("value", tagName);

					selectedItems.add(item);
				}

				ruleJSONObject.put("selectedItems", selectedItems);
			}
			else {
				queryValues = ParamUtil.getString(
					_request, "queryCategoryIds" + queryLogicIndex,
					queryValues);

				List<HashMap<String, Object>> selectedItems = new ArrayList<>();

				List<AssetCategory> categories = _filterAssetCategories(
					GetterUtil.getLongValues(queryValues.split(",")));

				for (AssetCategory category : categories) {
					HashMap<String, Object> selectedCategory = new HashMap<>();

					selectedCategory.put(
						"label", category.getTitle(_themeDisplay.getLocale()));
					selectedCategory.put("value", category.getCategoryId());

					selectedItems.add(selectedCategory);
				}

				ruleJSONObject.put("selectedItems", selectedItems);
			}

			if (Validator.isNull(queryValues)) {
				continue;
			}

			ruleJSONObject.put("queryValues", queryValues);
			ruleJSONObject.put("type", queryName);

			rulesJSONArray.put(ruleJSONObject);
		}

		return rulesJSONArray;
	}

	public long[] getAvailableClassNameIds() {
		if (_availableClassNameIds != null) {
			return _availableClassNameIds;
		}

		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				_themeDisplay.getCompanyId(), true);

		_availableClassNameIds = ArrayUtil.filter(
			availableClassNameIds,
			availableClassNameId ->
				IndexerRegistryUtil.getIndexer(
					PortalUtil.getClassName(availableClassNameId)) != null);

		return _availableClassNameIds;
	}

	public String getCategorySelectorURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				_request, AssetCategory.class.getName(),
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
		catch (Exception e) {
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
			_compilerTagNames = ParamUtil.getParameterValues(_request, "tags");
		}

		return _compilerTagNames;
	}

	public String getDDMStructureDisplayFieldValue() throws Exception {
		if (_ddmStructureDisplayFieldValue != null) {
			return _ddmStructureDisplayFieldValue;
		}

		setDDMStructure();

		return _ddmStructureDisplayFieldValue;
	}

	public String getDDMStructureFieldLabel() throws Exception {
		if (_ddmStructureFieldLabel != null) {
			return _ddmStructureFieldLabel;
		}

		setDDMStructure();

		return _ddmStructureFieldLabel;
	}

	public String getDDMStructureFieldName() throws Exception {
		if (_ddmStructureFieldName != null) {
			return _ddmStructureFieldName;
		}

		setDDMStructure();

		return _ddmStructureFieldName;
	}

	public String getDDMStructureFieldValue() throws Exception {
		if (_ddmStructureFieldValue != null) {
			return _ddmStructureFieldValue;
		}

		setDDMStructure();

		return _ddmStructureFieldValue;
	}

	public String getDefaultDisplayStyle() {
		return _assetPublisherPortletInstanceConfiguration.
			defaultDisplayStyle();
	}

	public Integer getDelta() {
		return _assetPublisherCustomizer.getDelta(_request);
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
		PortletConfig portletConfig = (PortletConfig)_request.getAttribute(
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

		_portletResource = ParamUtil.getString(_request, "portletResource");

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

	public SearchContainer getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer searchContainer = new SearchContainer(
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
		return _portletResponse.getNamespace() + "selectAssetList";
	}

	public String getSelectionStyle() {
		if (_selectionStyle != null) {
			return _selectionStyle;
		}

		_selectionStyle = GetterUtil.getString(
			_portletPreferences.getValue("selectionStyle", null), "dynamic");

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
				_request, AssetTag.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter(
				"eventName", _portletResponse.getNamespace() + "selectTag");
			portletURL.setParameter(
				"groupIds", StringUtil.merge(getGroupIds()));
			portletURL.setParameter("selectedTagNames", "{selectedTagNames}");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return null;
	}

	public List<Long> getVocabularyIds() {
		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupsVocabularies(getGroupIds());

		return ListUtil.toList(
			vocabularies, AssetVocabulary.VOCABULARY_ID_ACCESSOR);
	}

	public AssetEntry incrementViewCounter(AssetEntry assetEntry)
		throws PortalException {

		// Dynamically created asset entries are never persisted so incrementing
		// the view counter breaks

		if ((assetEntry == null) || assetEntry.isNew() ||
			!assetEntry.isVisible() || !isEnableViewCountIncrement()) {

			return assetEntry;
		}

		if (isEnablePermissions()) {
			return AssetEntryServiceUtil.incrementViewCounter(
				assetEntry.getClassName(), assetEntry.getClassPK());
		}

		return AssetEntryLocalServiceUtil.incrementViewCounter(
			_themeDisplay.getUserId(), assetEntry.getClassName(),
			assetEntry.getClassPK());
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

		_defaultAssetPublisher = _assetPublisherWebUtil.isDefaultAssetPublisher(
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

	public boolean isEnableFlags() {
		if (_enableFlags != null) {
			return _enableFlags;
		}

		_enableFlags = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableFlags", null));

		return _enableFlags;
	}

	public Boolean isEnablePermissions() {
		return _assetPublisherCustomizer.isEnablePermissions(_request);
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
			_portletPreferences.getValue("enableRelatedAssets", null));

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
		return _assetPublisherCustomizer.isOrderingByTitleEnabled(_request);
	}

	public boolean isPaginationTypeNone() {
		String paginationType = getPaginationType();

		if (Objects.equals(paginationType, PAGINATION_TYPE_NONE)) {
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
		if (Objects.equals(getSelectionStyle(), "asset-list")) {
			return true;
		}

		return false;
	}

	public boolean isSelectionStyleAssetListProvider() {
		if (Objects.equals(getSelectionStyle(), "asset-list-provider")) {
			return true;
		}

		return false;
	}

	public boolean isSelectionStyleDynamic() {
		if (Objects.equals(getSelectionStyle(), "dynamic")) {
			return true;
		}

		return false;
	}

	public boolean isSelectionStyleManual() throws PortalException {
		AssetListEntry assetListEntry = fetchAssetListEntry();

		if (isSelectionStyleAssetList() && (assetListEntry != null) &&
			(assetListEntry.getType() ==
				AssetListEntryTypeConstants.TYPE_MANUAL)) {

			return true;
		}

		if (Objects.equals(getSelectionStyle(), "manual")) {
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
		return _assetPublisherCustomizer.isShowEnableAddContentButton(_request);
	}

	public Boolean isShowEnablePermissions() {
		if (_assetPublisherWebConfiguration.searchWithIndex()) {
			return false;
		}

		return _assetPublisherWebConfiguration.permissionCheckingConfigurable();
	}

	public boolean isShowEnableRelatedAssets() {
		return _assetPublisherCustomizer.isShowEnableRelatedAssets(_request);
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
		return _assetPublisherCustomizer.isShowSubtypeFieldsFilter(_request);
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
				portletName, AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS)) {

			return false;
		}

		if (Objects.equals(
				portletName, AssetPublisherPortletKeys.MOST_VIEWED_ASSETS)) {

			return false;
		}

		if (Objects.equals(
				portletName, AssetPublisherPortletKeys.RECENT_CONTENT)) {

			return false;
		}

		if (Objects.equals(
				portletName, AssetPublisherPortletKeys.RELATED_ASSETS)) {

			return false;
		}

		if (!_assetPublisherWebUtil.getEmailAssetEntryAddedEnabled(
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

		String defaultAssetPublisherPortletId =
			_assetPublisherWebUtil.getDefaultAssetPublisherId(
				_themeDisplay.getLayout());

		if (isDefaultAssetPublisher() ||
			Validator.isNull(defaultAssetPublisherPortletId) ||
			!PortletPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _themeDisplay.getLayout(),
				defaultAssetPublisherPortletId, ActionKeys.VIEW)) {

			_request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
		}
	}

	public void setPageKeywords() throws PortalException {
		if (getAssetCategoryId() > 0) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.getCategory(getAssetCategoryId());

			PortalUtil.setPageKeywords(
				HtmlUtil.escape(
					assetCategory.getTitle(_themeDisplay.getLocale())),
				_request);
		}

		if (Validator.isNotNull(getAssetTagName())) {
			PortalUtil.setPageKeywords(getAssetTagName(), _request);
		}
	}

	public void setSelectionStyle(String selectionStyle) {
		_selectionStyle = selectionStyle;
	}

	protected void configureSubtypeFieldFilter(
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
			_assetPublisherWebUtil.encodeName(
				classTypeField.getClassTypeId(), getDDMStructureFieldName(),
				locale));

		assetEntryQuery.setAttribute(
			"ddmStructureFieldValue", getDDMStructureFieldValue());
	}

	protected void setDDMStructure() throws Exception {
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
			_request, "ddmStructureDisplayFieldValue",
			_portletPreferences.getValue(
				"ddmStructureDisplayFieldValue", StringPool.BLANK));
		_ddmStructureFieldName = ParamUtil.getString(
			_request, "ddmStructureFieldName",
			_portletPreferences.getValue(
				"ddmStructureFieldName", StringPool.BLANK));
		_ddmStructureFieldValue = ParamUtil.getString(
			_request, "ddmStructureFieldValue",
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

	private long[] _getSegmentsEntryIds() {
		return GetterUtil.getLongValues(
			_portletRequest.getAttribute(SegmentsWebKeys.SEGMENTS_ENTRY_IDS));
	}

	private Integer _abstractLength;
	private long[] _allAssetCategoryIds;
	private String[] _allAssetTagNames;
	private Boolean _anyAssetType;
	private Long _assetCategoryId;
	private final AssetEntryActionRegistry _assetEntryActionRegistry;
	private AssetEntryQuery _assetEntryQuery;
	private List<AssetEntryResult> _assetEntryResults;
	private final AssetHelper _assetHelper;
	private String _assetLinkBehavior;
	private AssetListEntry _assetListEntry;
	private final AssetPublisherCustomizer _assetPublisherCustomizer;
	private final AssetPublisherHelper _assetPublisherHelper;
	private final AssetPublisherPortletInstanceConfiguration
		_assetPublisherPortletInstanceConfiguration;
	private final AssetPublisherWebConfiguration
		_assetPublisherWebConfiguration;
	private final AssetPublisherWebUtil _assetPublisherWebUtil;
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
	private final InfoListProviderTracker _infoListProviderTracker;
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
	private final HttpServletRequest _request;
	private Integer _rssDelta;
	private String _rssDisplayStyle;
	private String _rssFeedType;
	private String _rssName;
	private SearchContainer _searchContainer;
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
	private Boolean _showTags;
	private Boolean _showViewCount;
	private String _socialBookmarksDisplayStyle;
	private String _socialBookmarksTypes;
	private Boolean _subtypeFieldsFilterEnabled;
	private final ThemeDisplay _themeDisplay;

}