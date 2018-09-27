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

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.list.constants.AssetListFormConstants;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalServiceUtil;
import com.liferay.asset.util.comparator.AssetRendererFactoryTypeNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class EditAssetListDisplayContext {

	public EditAssetListDisplayContext(
		PortletRequest portletRequest, PortletResponse portletResponse,
		UnicodeProperties properties) {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_properties = properties;
		_request = PortalUtil.getHttpServletRequest(portletRequest);
	}

	public long getAssetListEntryId() {
		if (_assetListEntryId != null) {
			return _assetListEntryId;
		}

		_assetListEntryId = ParamUtil.getLong(_request, "assetListEntryId");

		return _assetListEntryId;
	}

	public JSONArray getAutoFieldRulesJSONArray() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String queryLogicIndexesParam = ParamUtil.getString(
			_request, "queryLogicIndexes");

		int[] queryLogicIndexes = null;

		if (Validator.isNotNull(queryLogicIndexesParam)) {
			queryLogicIndexes = StringUtil.split(queryLogicIndexesParam, 0);
		}
		else {
			queryLogicIndexes = new int[0];

			for (int i = 0; true; i++) {
				String queryValues = PropertiesParamUtil.getString(
					_properties, _request, "queryValues" + i);

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

			boolean queryAndOperator = PropertiesParamUtil.getBoolean(
				_properties, _request, "queryAndOperator" + queryLogicIndex);

			ruleJSONObject.put("queryAndOperator", queryAndOperator);

			boolean queryContains = PropertiesParamUtil.getBoolean(
				_properties, _request, "queryContains" + queryLogicIndex, true);

			ruleJSONObject.put("queryContains", queryContains);

			String queryValues = _properties.getProperty(
				"queryValues" + queryLogicIndex, StringPool.BLANK);

			String queryName = PropertiesParamUtil.getString(
				_properties, _request, "queryName" + queryLogicIndex,
				"assetTags");

			if (Objects.equals(queryName, "assetTags")) {
				queryValues = ParamUtil.getString(
					_request, "queryTagNames" + queryLogicIndex, queryValues);

				queryValues = _filterAssetTagNames(
					themeDisplay.getScopeGroupId(), queryValues);
			}
			else {
				queryValues = ParamUtil.getString(
					_request, "queryCategoryIds" + queryLogicIndex,
					queryValues);

				JSONArray categoryIdsTitles = JSONFactoryUtil.createJSONArray();

				List<AssetCategory> categories = _filterAssetCategories(
					GetterUtil.getLongValues(queryValues.split(",")));

				for (AssetCategory category : categories) {
					categoryIdsTitles.put(
						category.getTitle(themeDisplay.getLocale()));
				}

				List<Long> categoryIds = ListUtil.toList(
					categories, AssetCategory.CATEGORY_ID_ACCESSOR);

				queryValues = StringUtil.merge(categoryIds, ",");

				ruleJSONObject.put("categoryIdsTitles", categoryIdsTitles);
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

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				themeDisplay.getCompanyId(), true);

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

	public String getClassName(AssetRendererFactory<?> assetRendererFactory) {
		Class<?> clazz = assetRendererFactory.getClass();

		String className = clazz.getName();

		int pos = className.lastIndexOf(StringPool.PERIOD);

		return className.substring(pos + 1);
	}

	public long[] getClassNameIds() {
		if (_classNameIds != null) {
			return _classNameIds;
		}

		_classNameIds = getClassNameIds(
			_properties, getAvailableClassNameIds());

		return _classNameIds;
	}

	public long[] getClassNameIds(
		UnicodeProperties properties, long[] availableClassNameIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			properties.getProperty("anyAssetType", Boolean.TRUE.toString()));
		String selectionStyle = properties.getProperty(
			"selectionStyle", "dynamic");

		if (anyAssetType || selectionStyle.equals("manual")) {
			return availableClassNameIds;
		}

		long defaultClassNameId = GetterUtil.getLong(
			properties.getProperty("anyAssetType", null));

		if (defaultClassNameId > 0) {
			return new long[] {defaultClassNameId};
		}

		long[] classNameIds = GetterUtil.getLongValues(
			StringUtil.split(
				properties.getProperty("classNameIds", StringPool.BLANK)));

		if (ArrayUtil.isNotEmpty(classNameIds)) {
			return classNameIds;
		}

		return availableClassNameIds;
	}

	public long[] getClassTypeIds() {
		if (_classTypeIds != null) {
			return _classTypeIds;
		}

		_classTypeIds = GetterUtil.getLongValues(
			StringUtil.split(
				_properties.getProperty("classTypeIds", StringPool.BLANK)));

		return _classTypeIds;
	}

	public Long[] getClassTypeIds(
		UnicodeProperties properties, String className,
		List<ClassType> availableClassTypes) {

		Long[] availableClassTypeIds = new Long[availableClassTypes.size()];

		for (int i = 0; i < availableClassTypeIds.length; i++) {
			ClassType classType = availableClassTypes.get(i);

			availableClassTypeIds[i] = classType.getClassTypeId();
		}

		return _getClassTypeIds(properties, className, availableClassTypeIds);
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

	public Map<String, Map<String, Object>> getManualAddIconDataMap()
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, Map<String, Object>> manualAddIconDataMap = new HashMap<>();

		List<AssetRendererFactory<?>> assetRendererFactories = ListUtil.sort(
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				themeDisplay.getCompanyId()),
			new AssetRendererFactoryTypeNameComparator(
				themeDisplay.getLocale()));

		for (AssetRendererFactory<?> curRendererFactory :
				assetRendererFactories) {

			if (!curRendererFactory.isSelectable()) {
				continue;
			}

			PortletURL assetBrowserURL = PortletProviderUtil.getPortletURL(
				_request, curRendererFactory.getClassName(),
				PortletProvider.Action.BROWSE);

			if (assetBrowserURL == null) {
				continue;
			}

			assetBrowserURL.setParameter(
				"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
			assetBrowserURL.setParameter(
				"multipleSelection", String.valueOf(Boolean.TRUE));
			assetBrowserURL.setParameter(
				"selectedGroupIds",
				String.valueOf(themeDisplay.getScopeGroupId()));
			assetBrowserURL.setParameter(
				"typeSelection", curRendererFactory.getClassName());
			assetBrowserURL.setParameter(
				"showNonindexable", String.valueOf(Boolean.TRUE));
			assetBrowserURL.setParameter(
				"showScheduled", String.valueOf(Boolean.TRUE));
			assetBrowserURL.setParameter(
				"eventName", _portletResponse.getNamespace() + "selectAsset");
			assetBrowserURL.setPortletMode(PortletMode.VIEW);
			assetBrowserURL.setWindowState(LiferayWindowState.POP_UP);

			if (!curRendererFactory.isSupportsClassTypes()) {
				Map<String, Object> data = new HashMap<>();

				data.put("destroyOnHide", true);
				data.put(
					"groupid", String.valueOf(themeDisplay.getScopeGroupId()));
				data.put("href", assetBrowserURL.toString());

				String type = curRendererFactory.getTypeName(
					themeDisplay.getLocale());

				data.put(
					"title",
					LanguageUtil.format(_request, "select-x", type, false));
				data.put("type", type);

				manualAddIconDataMap.put(type, data);

				continue;
			}

			ClassTypeReader classTypeReader =
				curRendererFactory.getClassTypeReader();

			List<ClassType> assetAvailableClassTypes =
				classTypeReader.getAvailableClassTypes(
					PortalUtil.getCurrentAndAncestorSiteGroupIds(
						themeDisplay.getScopeGroupId()),
					themeDisplay.getLocale());

			for (ClassType assetAvailableClassType : assetAvailableClassTypes) {
				Map<String, Object> data = new HashMap<>();

				data.put("destroyOnHide", true);
				data.put(
					"groupid", String.valueOf(themeDisplay.getScopeGroupId()));

				assetBrowserURL.setParameter(
					"subtypeSelectionId",
					String.valueOf(assetAvailableClassType.getClassTypeId()));

				data.put("href", assetBrowserURL.toString());

				String type = assetAvailableClassType.getName();

				data.put(
					"title",
					LanguageUtil.format(_request, "select-x", type, false));
				data.put("type", type);

				manualAddIconDataMap.put(type, data);
			}
		}

		return manualAddIconDataMap;
	}

	public String getOrderByColumn1() {
		if (_orderByColumn1 != null) {
			return _orderByColumn1;
		}

		_orderByColumn1 = GetterUtil.getString(
			_properties.getProperty("orderByColumn1", "modifiedDate"));

		return _orderByColumn1;
	}

	public String getOrderByColumn2() {
		if (_orderByColumn2 != null) {
			return _orderByColumn2;
		}

		_orderByColumn2 = GetterUtil.getString(
			_properties.getProperty("orderByColumn2", "title"));

		return _orderByColumn2;
	}

	public String getOrderByType1() {
		if (_orderByType1 != null) {
			return _orderByType1;
		}

		_orderByType1 = GetterUtil.getString(
			_properties.getProperty("orderByType1", "DESC"));

		return _orderByType1;
	}

	public String getOrderByType2() {
		if (_orderByType2 != null) {
			return _orderByType2;
		}

		_orderByType2 = GetterUtil.getString(
			_properties.getProperty("orderByType2", "ASC"));

		return _orderByType2;
	}

	public String getRedirectURL() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		String redirect = ParamUtil.getString(_request, "redirect");

		if (Validator.isNull(redirect)) {
			LiferayPortletResponse liferayPortletResponse =
				PortalUtil.getLiferayPortletResponse(_portletResponse);

			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			redirect = portletURL.toString();
		}

		_redirect = redirect;

		return _redirect;
	}

	public long[] getReferencedModelsGroupIds() throws PortalException {

		// Referenced models are asset subtypes, tags or categories that
		// are used to filter assets and can belong to a different scope of
		// the asset they are associated to

		if (_referencedModelsGroupIds != null) {
			return _referencedModelsGroupIds;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_referencedModelsGroupIds =
			PortalUtil.getCurrentAndAncestorSiteGroupIds(
				themeDisplay.getScopeGroupId(), true);

		return _referencedModelsGroupIds;
	}

	public SearchContainer getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer searchContainer = new SearchContainer(
			_portletRequest, _getPortletURL(), null,
			"there-are-no-asset-entries");

		searchContainer.setTotal(
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRelsCount(getAssetListEntryId()));

		searchContainer.setResults(
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRels(
					getAssetListEntryId(), searchContainer.getStart(),
					searchContainer.getEnd()));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getTagSelectorURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				_request, AssetTag.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			portletURL.setParameter(
				"groupIds", String.valueOf(themeDisplay.getScopeGroupId()));

			portletURL.setParameter(
				"eventName", _portletResponse.getNamespace() + "selectTag");
			portletURL.setParameter("selectedTagNames", "{selectedTagNames}");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return null;
	}

	public String getVocabularyIds() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupsVocabularies(
				new long[] {themeDisplay.getScopeGroupId()});

		return ListUtil.toString(
			vocabularies, AssetVocabulary.VOCABULARY_ID_ACCESSOR);
	}

	public Boolean isAnyAssetType() {
		if (_anyAssetType != null) {
			return _anyAssetType;
		}

		_anyAssetType = GetterUtil.getBoolean(
			_properties.getProperty("anyAssetType", null), true);

		return _anyAssetType;
	}

	public boolean isShowSubtypeFieldsFilter() {
		return true;
	}

	public boolean isSubtypeFieldsFilterEnabled() {
		if (_subtypeFieldsFilterEnabled != null) {
			return _subtypeFieldsFilterEnabled;
		}

		_subtypeFieldsFilterEnabled = GetterUtil.getBoolean(
			_properties.getProperty(
				"subtypeFieldsFilterEnabled", Boolean.FALSE.toString()));

		return _subtypeFieldsFilterEnabled;
	}

	protected void setDDMStructure() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

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
			_properties.getProperty(
				"ddmStructureDisplayFieldValue", StringPool.BLANK));
		_ddmStructureFieldName = ParamUtil.getString(
			_request, "ddmStructureFieldName",
			_properties.getProperty("ddmStructureFieldName", StringPool.BLANK));
		_ddmStructureFieldValue = ParamUtil.getString(
			_request, "ddmStructureFieldValue",
			_properties.getProperty(
				"ddmStructureFieldValue", StringPool.BLANK));

		if (Validator.isNotNull(_ddmStructureFieldName) &&
			Validator.isNotNull(_ddmStructureFieldValue)) {

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(classNameIds[0]);

			ClassTypeReader classTypeReader =
				assetRendererFactory.getClassTypeReader();

			ClassType classType = classTypeReader.getClassType(
				classTypeIds[0], themeDisplay.getLocale());

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

	private String _filterAssetTagNames(long groupId, String assetTagNames) {
		List<String> filteredAssetTagNames = new ArrayList<>();

		String[] assetTagNamesArray = StringUtil.split(assetTagNames);

		long[] assetTagIds = AssetTagLocalServiceUtil.getTagIds(
			groupId, assetTagNamesArray);

		for (long assetTagId : assetTagIds) {
			AssetTag assetTag = AssetTagLocalServiceUtil.fetchAssetTag(
				assetTagId);

			if (assetTag != null) {
				filteredAssetTagNames.add(assetTag.getName());
			}
		}

		return StringUtil.merge(filteredAssetTagNames);
	}

	private Long[] _getClassTypeIds(
		UnicodeProperties properties, String className,
		Long[] availableClassTypeIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			properties.getProperty(
				"anyClassType" + className, Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassTypeIds;
		}

		long defaultClassTypeId = GetterUtil.getLong(
			properties.getProperty("anyClassType" + className, null), -1);

		if (defaultClassTypeId > -1) {
			return new Long[] {defaultClassTypeId};
		}

		Long[] classTypeIds = ArrayUtil.toArray(
			StringUtil.split(
				properties.getProperty("classTypeIds" + className, null), 0L));

		if (classTypeIds != null) {
			return classTypeIds;
		}

		return availableClassTypeIds;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			_portletRequest, AssetListPortletKeys.ASSET_LIST,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_asset_list_entry.jsp");
		portletURL.setParameter(
			"assetListEntryId", String.valueOf(getAssetListEntryId()));
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			AssetListFormConstants.CATEGORY_KEY_GENERAL);
		portletURL.setParameter(
			"screenNavigationEntryKey",
			AssetListFormConstants.ENTRY_KEY_ASSET_ENTRIES);

		return portletURL;
	}

	private Boolean _anyAssetType;
	private Long _assetListEntryId;
	private long[] _availableClassNameIds;
	private long[] _classNameIds;
	private long[] _classTypeIds;
	private String _ddmStructureDisplayFieldValue;
	private String _ddmStructureFieldLabel;
	private String _ddmStructureFieldName;
	private String _ddmStructureFieldValue;
	private String _orderByColumn1;
	private String _orderByColumn2;
	private String _orderByType1;
	private String _orderByType2;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final UnicodeProperties _properties;
	private String _redirect;
	private long[] _referencedModelsGroupIds;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;
	private Boolean _subtypeFieldsFilterEnabled;

}