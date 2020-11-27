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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

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

	public List<DropdownItem> getDropdownItems() {
		List<AssetCategory> assetCategories =
			(List<AssetCategory>)_httpServletRequest.getAttribute(
				CollectionFilterFragmentRendererWebKeys.ASSET_CATEGORIES);

		Long fragmentEntryLinkId = (Long)_httpServletRequest.getAttribute(
			CollectionFilterFragmentRendererWebKeys.FRAGMENT_ENTRY_LINK_ID);

		Layout layout = _themeDisplay.getLayout();

		try {
			String layoutURL = layout.getRegularURL(_httpServletRequest);

			DropdownItemListBuilder.DropdownItemListWrapper
				dropdownItemListWrapper = DropdownItemListBuilder.add(
					dropdownItem -> {
						dropdownItem.setHref(layoutURL);
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "all"));
					});

			for (AssetCategory assetCategory : assetCategories) {
				dropdownItemListWrapper.add(
					dropdownItem -> {
						dropdownItem.setHref(
							HttpUtil.addParameter(
								layoutURL,
								CollectionFilterFragmentRendererWebKeys.
									CATEGORY_ID + "_" + fragmentEntryLinkId,
								assetCategory.getCategoryId()));
						dropdownItem.setLabel(
							assetCategory.getTitle(
								_themeDisplay.getLanguageId()));
					});
			}

			return dropdownItemListWrapper.build();
		}
		catch (PortalException portalException) {
			_log.error("Unable to get dropdown items", portalException);
		}

		return new ArrayList<>();
	}

	public String getSelectedCategoryTitle() {
		long categoryId = ParamUtil.get(
			_httpServletRequest,
			CollectionFilterFragmentRendererWebKeys.CATEGORY_ID, 0);

		if (categoryId != 0) {
			AssetCategory assetCategory = null;

			try {
				assetCategory = AssetCategoryServiceUtil.fetchCategory(
					categoryId);
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

	public String getVocabularyTitle() {
		AssetVocabulary assetVocabulary =
			(AssetVocabulary)_httpServletRequest.getAttribute(
				CollectionFilterFragmentRendererWebKeys.ASSET_VOCABULARY);

		if (assetVocabulary == null) {
			return StringPool.BLANK;
		}

		return assetVocabulary.getTitle(_themeDisplay.getLanguageId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionFilterFragmentRendererDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}