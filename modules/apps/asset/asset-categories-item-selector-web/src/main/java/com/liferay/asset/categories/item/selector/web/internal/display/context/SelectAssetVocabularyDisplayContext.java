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

package com.liferay.asset.categories.item.selector.web.internal.display.context;

import com.liferay.asset.categories.item.selector.web.internal.constants.AssetCategoryTreeNodeConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class SelectAssetVocabularyDisplayContext {

	public SelectAssetVocabularyDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public long getAssetCategoryTreeNodeId() {
		if (_assetCategoryTreeNodeId != null) {
			return _assetCategoryTreeNodeId;
		}

		_assetCategoryTreeNodeId = ParamUtil.getLong(
			_httpServletRequest, "assetCategoryTreeNodeId", -1);

		return _assetCategoryTreeNodeId;
	}

	public SearchContainer<AssetVocabulary>
		getAssetVocabularySearchContainer() {

		SearchContainer<AssetVocabulary> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null,
				"there-are-no-items-to-display");

		List<AssetVocabulary> assetVocabularies = _getAssetVocabularies();

		searchContainer.setResults(assetVocabularies);
		searchContainer.setTotal(assetVocabularies.size());

		return searchContainer;
	}

	public String getAssetVocabularyTitle(AssetVocabulary assetVocabulary) {
		if (assetVocabulary == null) {
			return null;
		}

		return assetVocabulary.getTitle(_themeDisplay.getLocale());
	}

	public String getAssetVocabularyURL(long assetVocabularyId)
		throws PortletException {

		return _getAssetVocabularyURL(assetVocabularyId);
	}

	public List<BreadcrumbEntry> getBreadcrumbEntries() {
		return ListUtil.fromArray(_getAssetVocabulariesBreadcrumbEntry());
	}

	private BreadcrumbEntry _createBreadcrumbEntry(String title, String url) {
		return new BreadcrumbEntry() {
			{
				setBrowsable(url != null);
				setTitle(title);
				setURL(url);
			}
		};
	}

	private List<AssetVocabulary> _getAssetVocabularies() {
		return AssetVocabularyServiceUtil.getGroupVocabularies(
			new long[] {
				_themeDisplay.getCompanyGroupId(),
				_themeDisplay.getScopeGroupId()
			},
			new int[] {AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC});
	}

	private BreadcrumbEntry _getAssetVocabulariesBreadcrumbEntry() {
		String backURL = ParamUtil.getString(
			_httpServletRequest, "backURL",
			PortalUtil.getCurrentURL(_httpServletRequest));

		return _createBreadcrumbEntry(
			LanguageUtil.get(_themeDisplay.getLocale(), "vocabularies"),
			backURL);
	}

	private String _getAssetVocabularyURL(long assetVocabularyId)
		throws PortletException {

		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortletURLBuilder.create(
			PortletURLUtil.clone(
				_portletURL,
				PortalUtil.getLiferayPortletResponse(portletResponse))
		).setBackURL(
			ParamUtil.getString(
				_httpServletRequest, "backURL",
				PortalUtil.getCurrentURL(_httpServletRequest))
		).setParameter(
			"assetCategoryTreeNodeId", assetVocabularyId
		).setParameter(
			"assetCategoryTreeNodeType",
			AssetCategoryTreeNodeConstants.TYPE_ASSET_VOCABULARY
		).buildString();
	}

	private PortletRequest _getPortletRequest() {
		return (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	private Long _assetCategoryTreeNodeId;
	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;
	private final ThemeDisplay _themeDisplay;

}