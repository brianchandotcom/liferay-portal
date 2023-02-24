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

package com.liferay.journal.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.journal.model.JournalFeed;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class EditJournalFeedDisplayContext {

	public EditJournalFeedDisplayContext(
			HttpServletRequest httpServletRequest, JournalFeed journalFeed,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		_httpServletRequest = httpServletRequest;
		_journalFeed = journalFeed;
		_liferayPortletResponse = liferayPortletResponse;

		_itemSelector = (ItemSelector)_httpServletRequest.getAttribute(
			ItemSelector.class.getName());

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAssetCategoriesSelectorURL() {
		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());
		itemSelectorCriterion.setItemType(AssetCategory.class.getName());

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
				_liferayPortletResponse.getNamespace() + "selectAssetCategory",
				itemSelectorCriterion)
		).buildString();
	}

	public long getAssetCategoryId() throws PortalException {
		if (_assetCategoryId != null) {
			return _assetCategoryId;
		}

		long assetCategoryId = 0;

		long[] assetCategoryIds = ParamUtil.getLongValues(
			_httpServletRequest, "assetCategoryIds", null);

		if (assetCategoryIds != null) {
			if (ArrayUtil.isNotEmpty(assetCategoryIds)) {
				assetCategoryId = assetCategoryIds[0];
			}
		}
		else if (_journalFeed != null) {
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				JournalFeed.class.getName(), _journalFeed.getId());

			assetCategoryIds = assetEntry.getCategoryIds();

			if (ArrayUtil.isNotEmpty(assetCategoryIds)) {
				assetCategoryId = assetCategoryIds[0];
			}
		}

		_assetCategoryId = assetCategoryId;

		return _assetCategoryId;
	}

	public String getAssetCategoryIds() throws PortalException {
		if (getAssetCategoryId() > 0) {
			return String.valueOf(getAssetCategoryId());
		}

		return StringPool.BLANK;
	}

	public String getAssetCategoryName() throws PortalException {
		if (_assetCategoryName != null) {
			return _assetCategoryName;
		}

		String assetCategoryName = StringPool.BLANK;

		if (getAssetCategoryId() > 0) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					getAssetCategoryId());

			if (assetCategory != null) {
				assetCategoryName = assetCategory.getTitle(
					_themeDisplay.getLocale());
			}
		}

		_assetCategoryName = assetCategoryName;

		return _assetCategoryName;
	}

	private Long _assetCategoryId;
	private String _assetCategoryName;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final JournalFeed _journalFeed;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}