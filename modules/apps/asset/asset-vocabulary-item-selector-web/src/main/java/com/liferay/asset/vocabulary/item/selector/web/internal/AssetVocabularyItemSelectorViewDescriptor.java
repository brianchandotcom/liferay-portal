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

package com.liferay.asset.vocabulary.item.selector.web.internal;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.vocabulary.item.selector.AssetVocabularyItemSelectorReturnType;
import com.liferay.asset.vocabulary.item.selector.criterion.AssetVocabularyItemSelectorCriterion;
import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.comparator.AssetVocabularyGroupLocalizedTitleComparator;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetVocabularyItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<AssetVocabulary> {

	public AssetVocabularyItemSelectorViewDescriptor(
		AssetVocabularyItemSelectorCriterion
			assetVocabularyItemSelectorCriterion,
		HttpServletRequest httpServletRequest, PortletURL portletURL) {

		_assetVocabularyItemSelectorCriterion =
			assetVocabularyItemSelectorCriterion;
		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	@Override
	public ItemDescriptor getItemDescriptor(AssetVocabulary assetVocabulary) {
		return new AssetVocabularyItemDescriptor(
			assetVocabulary, _httpServletRequest);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new AssetVocabularyItemSelectorReturnType();
	}

	@Override
	public SearchContainer<AssetVocabulary> getSearchContainer()
		throws PortalException {

		SearchContainer<AssetVocabulary> searchContainer =
			new SearchContainer<>(
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST),
				_portletURL, null, "there-are-no-items-to-display");

		if (searchContainer.isSearch()) {
			searchContainer.setResultsAndTotal(
				AssetVocabularyLocalServiceUtil.searchVocabularies(
					_themeDisplay.getCompanyId(), _getGroupIds(),
					ParamUtil.getString(_httpServletRequest, "keywords"),
					_getVisibilityTypes(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					new Sort(
						Field.getSortableFieldName(
							"localized_title_" + _themeDisplay.getLanguageId()),
						false)));
		}
		else {
			searchContainer.setResultsAndTotal(_getAssetVocabularies());
		}

		return searchContainer;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowManagementToolbar() {
		return true;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private List<AssetVocabulary> _getAssetVocabularies() {
		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				_getGroupIds(), _getVisibilityTypes());

		if (assetVocabularies.isEmpty()) {
			return Collections.emptyList();
		}

		ListUtil.sort(
			assetVocabularies,
			new AssetVocabularyGroupLocalizedTitleComparator(
				_themeDisplay.getScopeGroupId(), _themeDisplay.getLocale(),
				true));

		return assetVocabularies;
	}

	private long[] _getGroupIds() {
		long groupId = _assetVocabularyItemSelectorCriterion.getGroupId();

		if (groupId == 0) {
			groupId = _themeDisplay.getScopeGroupId();
		}

		long[] groupIds = {groupId};

		if (_assetVocabularyItemSelectorCriterion.
				isIncludeAncestorSiteAndDepotGroupIds()) {

			try {
				groupIds =
					SiteConnectedGroupGroupProviderUtil.
						getCurrentAndAncestorSiteAndDepotGroupIds(groupId);
			}
			catch (Exception exception) {
			}
		}

		return groupIds;
	}

	private int[] _getVisibilityTypes() {
		int[] visibilityTypes = AssetVocabularyConstants.VISIBILITY_TYPES;

		if (!_assetVocabularyItemSelectorCriterion.
				isIncludeInternalVocabularies()) {

			visibilityTypes = new int[] {
				AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC
			};
		}

		return visibilityTypes;
	}

	private final AssetVocabularyItemSelectorCriterion
		_assetVocabularyItemSelectorCriterion;
	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;
	private final ThemeDisplay _themeDisplay;

}