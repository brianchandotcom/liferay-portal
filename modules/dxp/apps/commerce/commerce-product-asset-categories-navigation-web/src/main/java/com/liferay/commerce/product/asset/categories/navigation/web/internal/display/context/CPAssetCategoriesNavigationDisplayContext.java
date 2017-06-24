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

package com.liferay.commerce.product.asset.categories.navigation.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.commerce.product.asset.categories.navigation.web.internal.configuration.CPAssetCategoriesNavigationPortletInstanceConfiguration;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPAssetCategoriesNavigationDisplayContext {

	public CPAssetCategoriesNavigationDisplayContext(
			HttpServletRequest httpServletRequest,
			AssetCategoryService assetCategoryService,
			AssetVocabularyService assetVocabularyService)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;
		_assetCategoryService = assetCategoryService;
		_assetVocabularyService = assetVocabularyService;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpAssetCategoriesNavigationPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPAssetCategoriesNavigationPortletInstanceConfiguration.class);
	}

	public List<AssetCategory> getAssetCategories() throws PortalException {
		AssetVocabulary assetVocabulary = getAssetVocabulary();

		if (assetVocabulary == null) {
			return Collections.emptyList();
		}

		if (_assetCategories != null) {
			return _assetCategories;
		}

		_assetCategories = _assetCategoryService.getVocabularyRootCategories(
			assetVocabulary.getGroupId(), assetVocabulary.getVocabularyId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return _assetCategories;
	}

	public List<AssetVocabulary> getAssetVocabularies() {
		if (_assetVocabularies != null) {
			return _assetVocabularies;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long[] groupIds = new long[0];

		try {
			groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
				themeDisplay.getScopeGroupId());
		}
		catch (PortalException pe) {
			groupIds = new long[] {themeDisplay.getScopeGroupId()};

			_log.error(pe, pe);
		}

		_assetVocabularies = _assetVocabularyService.getGroupVocabularies(
			groupIds);

		return _assetVocabularies;
	}

	public AssetVocabulary getAssetVocabulary() throws PortalException {
		if (_assetVocabulary != null) {
			return _assetVocabulary;
		}

		long assetVocabularyId = GetterUtil.getLong(
			_cpAssetCategoriesNavigationPortletInstanceConfiguration.
				assetVocabularyId());

		_assetVocabulary = _assetVocabularyService.fetchVocabulary(
			assetVocabularyId);

		return _assetVocabulary;
	}

	public List<AssetCategory> getChildAssetCategories(long categoryId)
		throws PortalException {

		return _assetCategoryService.getChildCategories(categoryId);
	}

	public CPAssetCategoriesNavigationPortletInstanceConfiguration
		getCPAssetCategoriesNavigationPortletInstanceConfiguration() {

		return _cpAssetCategoriesNavigationPortletInstanceConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPAssetCategoriesNavigationDisplayContext.class);

	private List<AssetCategory> _assetCategories;
	private final AssetCategoryService _assetCategoryService;
	private List<AssetVocabulary> _assetVocabularies;
	private AssetVocabulary _assetVocabulary;
	private final AssetVocabularyService _assetVocabularyService;
	private final CPAssetCategoriesNavigationPortletInstanceConfiguration
		_cpAssetCategoriesNavigationPortletInstanceConfiguration;
	private final HttpServletRequest _httpServletRequest;

}