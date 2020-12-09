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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.fragment.renderer.collection.filter.internal.constants.CollectionFilterFragmentRendererWebKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class CollectionFilterFragmentRendererDisplayContext {

	public CollectionFilterFragmentRendererDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAssetCategoryTreeNodeTitle() {
		AssetCategory assetCategory =
			(AssetCategory)_httpServletRequest.getAttribute(
				CollectionFilterFragmentRendererWebKeys.ASSET_CATEGORY);

		AssetVocabulary assetVocabulary =
			(AssetVocabulary)_httpServletRequest.getAttribute(
				CollectionFilterFragmentRendererWebKeys.ASSET_VOCABULARY);

		if ((assetCategory == null) && (assetVocabulary == null)) {
			return StringPool.BLANK;
		}

		if (assetVocabulary != null) {
			return assetVocabulary.getTitle(_themeDisplay.getLanguageId());
		}

		return assetCategory.getTitle(_themeDisplay.getLanguageId());
	}

	public List<DropdownItem> getDropdownItems() {
		String urlCurrent = _themeDisplay.getURLCurrent();

		String parameterName = _getParameterName();

		String url = HttpUtil.removeParameter(urlCurrent, parameterName);

		DropdownItemListBuilder.DropdownItemListWrapper
			dropdownItemListWrapper = DropdownItemListBuilder.add(
				dropdownItem -> {
					dropdownItem.setHref(url);
					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "all"));
				});

		List<AssetCategory> assetCategories =
			(List<AssetCategory>)_httpServletRequest.getAttribute(
				CollectionFilterFragmentRendererWebKeys.ASSET_CATEGORIES);

		if (assetCategories == null) {
			return dropdownItemListWrapper.build();
		}

		for (AssetCategory assetCategory : assetCategories) {
			dropdownItemListWrapper.add(
				dropdownItem -> {
					dropdownItem.setHref(
						HttpUtil.addParameter(
							url, parameterName, assetCategory.getCategoryId()));
					dropdownItem.setLabel(
						assetCategory.getTitle(_themeDisplay.getLanguageId()));
				});
		}

		return dropdownItemListWrapper.build();
	}

	public Map<String, Object> getProps() {
		if (_props != null) {
			return _props;
		}

		_props = HashMapBuilder.<String, Object>put(
			"assetCategories",
			() -> {
				List<AssetCategory> assetCategories =
					(List<AssetCategory>)_httpServletRequest.getAttribute(
						CollectionFilterFragmentRendererWebKeys.
							ASSET_CATEGORIES);

				if (assetCategories == null) {
					return new ArrayList<>();
				}

				Stream<AssetCategory> stream = assetCategories.stream();

				return stream.map(
					assetCategory -> HashMapBuilder.put(
						"id", String.valueOf(assetCategory.getCategoryId())
					).put(
						"label",
						assetCategory.getTitle(_themeDisplay.getLanguageId())
					).build()
				).collect(
					Collectors.toList()
				);
			}
		).put(
			"fragmentEntryLinkId",
			String.valueOf(
				GetterUtil.getLong(
					_httpServletRequest.getAttribute(
						CollectionFilterFragmentRendererWebKeys.
							FRAGMENT_ENTRY_LINK_ID)))
		).put(
			"selectedAssetCategoryIds",
			() -> {
				long fragmentEntryLinkId = GetterUtil.getLong(
					_httpServletRequest.getAttribute(
						CollectionFilterFragmentRendererWebKeys.
							FRAGMENT_ENTRY_LINK_ID));

				return ParamUtil.getStringValues(
					_httpServletRequest, "categoryId_" + fragmentEntryLinkId);
			}
		).build();

		return _props;
	}

	public String getSelectedAssetCategoryTitle() {
		long assetCategoryId = ParamUtil.get(
			_httpServletRequest, _getParameterName(), 0);

		if (assetCategoryId != 0) {
			AssetCategory assetCategory = null;

			try {
				assetCategory = AssetCategoryServiceUtil.fetchCategory(
					assetCategoryId);
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}

			if (assetCategory != null) {
				return assetCategory.getTitle(_themeDisplay.getLanguageId());
			}
		}

		return LanguageUtil.get(_httpServletRequest, "select");
	}

	public boolean isMultipleSelection() {
		return GetterUtil.getBoolean(
			_httpServletRequest.getAttribute(
				CollectionFilterFragmentRendererWebKeys.MULTIPLE_SELECTION));
	}

	private String _getParameterName() {
		Long fragmentEntryLinkId = (Long)_httpServletRequest.getAttribute(
			CollectionFilterFragmentRendererWebKeys.FRAGMENT_ENTRY_LINK_ID);

		return CollectionFilterFragmentRendererWebKeys.CATEGORY_ID + "_" +
			fragmentEntryLinkId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionFilterFragmentRendererDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private Map<String, Object> _props;
	private final ThemeDisplay _themeDisplay;

}