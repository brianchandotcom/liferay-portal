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

package com.liferay.fragment.collection.filter.category.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rubén Pulido
 */
public class FragmentCollectionFilterCategoryDisplayContext {

	public FragmentCollectionFilterCategoryDisplayContext(
		String configuration,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererContext fragmentRendererContext) {

		_configuration = configuration;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_fragmentRendererContext = fragmentRendererContext;

		_fragmentEntryLink = fragmentRendererContext.getFragmentEntryLink();
	}

	public String getAssetCategoryTreeNodeTitle() throws PortalException {
		long assetCategoryTreeNodeId = _getAssetCategoryTreeNodeId();

		if (assetCategoryTreeNodeId == 0) {
			return StringPool.BLANK;
		}

		String assetCategoryTreeNodeType = _getAssetCategoryTreeNodeType();

		if (assetCategoryTreeNodeType.equals("Category")) {
			AssetCategory assetCategory =
				AssetCategoryServiceUtil.fetchCategory(assetCategoryTreeNodeId);

			return assetCategory.getTitle(_fragmentRendererContext.getLocale());
		}
		else if (assetCategoryTreeNodeType.equals("Vocabulary")) {
			AssetVocabulary assetVocabulary =
				AssetVocabularyServiceUtil.fetchVocabulary(
					assetCategoryTreeNodeId);

			return assetVocabulary.getTitle(
				_fragmentRendererContext.getLocale());
		}

		return StringPool.BLANK;
	}

	public String getLabel() throws PortalException {
		String label = GetterUtil.getString(_getFieldValue("label"));

		if (Validator.isNotNull(label)) {
			return label;
		}

		return getAssetCategoryTreeNodeTitle();
	}

	public Map<String, Object> getProps() {
		if (_props != null) {
			return _props;
		}

		_props = HashMapBuilder.<String, Object>put(
			"assetCategories",
			() -> {
				List<AssetCategory> assetCategories = _getAssetCategories();

				if (assetCategories == null) {
					return new ArrayList<>();
				}

				Stream<AssetCategory> stream = assetCategories.stream();

				return stream.map(
					assetCategory -> HashMapBuilder.put(
						"id", String.valueOf(assetCategory.getCategoryId())
					).put(
						"label",
						assetCategory.getTitle(
							_fragmentRendererContext.getLocale())
					).build()
				).collect(
					Collectors.toList()
				);
			}
		).put(
			"enableDropdown",
			!Objects.equals(
				_fragmentRendererContext.getMode(),
				FragmentEntryLinkConstants.EDIT)
		).put(
			"fragmentEntryLinkId",
			String.valueOf(_fragmentEntryLink.getFragmentEntryLinkId())
		).put(
			"showSearch", _isShowSearch()
		).put(
			"singleSelection", _isSingleSelection()
		).build();

		return _props;
	}

	public boolean isShowLabel() {
		return GetterUtil.getBoolean(_getFieldValue("showLabel"));
	}

	private List<AssetCategory> _getAssetCategories() throws PortalException {
		if (_assetCategories != null) {
			return _assetCategories;
		}

		long assetCategoryTreeNodeId = _getAssetCategoryTreeNodeId();

		_assetCategories = Collections.emptyList();

		if (assetCategoryTreeNodeId == 0) {
			return _assetCategories;
		}

		if (Objects.equals(_getAssetCategoryTreeNodeType(), "Category")) {
			_assetCategories = AssetCategoryServiceUtil.getChildCategories(
				assetCategoryTreeNodeId);
		}
		else if (Objects.equals(
					_getAssetCategoryTreeNodeType(), "Vocabulary")) {

			AssetVocabulary assetVocabulary =
				AssetVocabularyServiceUtil.fetchVocabulary(
					assetCategoryTreeNodeId);

			_assetCategories =
				AssetCategoryServiceUtil.getVocabularyRootCategories(
					assetVocabulary.getGroupId(), assetCategoryTreeNodeId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		return _assetCategories;
	}

	private long _getAssetCategoryTreeNodeId() {
		if (_assetCategoryTreeNodeId != null) {
			return _assetCategoryTreeNodeId;
		}

		JSONObject sourceJSONObject = _getSourceJSONObject();

		_assetCategoryTreeNodeId = sourceJSONObject.getLong(
			"categoryTreeNodeId", 0);

		return _assetCategoryTreeNodeId;
	}

	private String _getAssetCategoryTreeNodeType() {
		if (_assetCategoryTreeNodeType != null) {
			return _assetCategoryTreeNodeType;
		}

		_assetCategoryTreeNodeType = StringPool.BLANK;

		JSONObject sourceJSONObject = _getSourceJSONObject();

		if (sourceJSONObject != null) {
			_assetCategoryTreeNodeType = sourceJSONObject.getString(
				"categoryTreeNodeType");
		}

		return _assetCategoryTreeNodeType;
	}

	private Object _getFieldValue(String fieldName) {
		return _fragmentEntryConfigurationParser.getFieldValue(
			_configuration, _fragmentEntryLink.getEditableValues(),
			_fragmentRendererContext.getLocale(), fieldName);
	}

	private JSONObject _getSourceJSONObject() {
		if (_sourceJSONObject != null) {
			return _sourceJSONObject;
		}

		Object sourceObject = _getFieldValue("source");

		if (sourceObject == null) {
			_sourceJSONObject = JSONFactoryUtil.createJSONObject();

			return _sourceJSONObject;
		}

		try {
			_sourceJSONObject = JSONFactoryUtil.createJSONObject(
				sourceObject.toString());
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			_sourceJSONObject = JSONFactoryUtil.createJSONObject();
		}

		return _sourceJSONObject;
	}

	private boolean _isShowSearch() {
		return GetterUtil.getBoolean(_getFieldValue("showSearch"));
	}

	private boolean _isSingleSelection() {
		return GetterUtil.getBoolean(_getFieldValue("singleSelection"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionFilterCategoryDisplayContext.class);

	private List<AssetCategory> _assetCategories;
	private Long _assetCategoryTreeNodeId;
	private String _assetCategoryTreeNodeType;
	private final String _configuration;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private final FragmentEntryLink _fragmentEntryLink;
	private final FragmentRendererContext _fragmentRendererContext;
	private Map<String, Object> _props;
	private JSONObject _sourceJSONObject;

}