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

package com.liferay.asset.categories.navigation.web.display.context;

import com.liferay.asset.categories.navigation.web.configuration.AssetCategoriesNavigationPortletInstanceConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetCategoriesNavigationDisplayContext {

	public AssetCategoriesNavigationDisplayContext(HttpServletRequest request)
		throws SettingsException {

		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_assetCategoriesNavigationPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				AssetCategoriesNavigationPortletInstanceConfiguration.class);
	}

	public List<AssetVocabulary> getAssetVocabularies() throws PortalException {
		if (_assetVocabularies != null) {
			return _assetVocabularies;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
			themeDisplay.getScopeGroupId());

		_assetVocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(
			groupIds);

		return _assetVocabularies;
	}

	public long[] getAssetVocabularyIds() throws PortalException {
		if (_assetVocabularyIds != null) {
			return _assetVocabularyIds;
		}

		_assetVocabularyIds = getAvailableAssetVocabularyIds();

		if (!_assetCategoriesNavigationPortletInstanceConfiguration.
				allAssetVocabularies() &&
			(_assetCategoriesNavigationPortletInstanceConfiguration.
				assetVocabularyIds() != null)) {

			String assetVocabularyIds = StringUtil.merge(
				_assetCategoriesNavigationPortletInstanceConfiguration.
					assetVocabularyIds());

			_assetVocabularyIds = StringUtil.split(assetVocabularyIds, 0L);
		}

		return _assetVocabularyIds;
	}

	public long[] getAvailableAssetVocabularyIds() throws PortalException {
		if (_availableAssetVocabularyIds != null) {
			return _availableAssetVocabularyIds;
		}

		List<AssetVocabulary> assetVocabularies = getAssetVocabularies();

		_availableAssetVocabularyIds = new long[assetVocabularies.size()];

		for (int i = 0; i < assetVocabularies.size(); i++) {
			AssetVocabulary assetVocabulary = assetVocabularies.get(i);

			_availableAssetVocabularyIds[i] = assetVocabulary.getVocabularyId();
		}

		return _availableAssetVocabularyIds;
	}

	public List<KeyValuePair> getAvailableVocabularyNames()
		throws PortalException {

		List<KeyValuePair> availableVocabularNames = new ArrayList<>();

		long[] assetVocabularyIds = getAssetVocabularyIds();

		Arrays.sort(assetVocabularyIds);

		Set<Long> availableAssetVocabularyIdsSet = SetUtil.fromArray(
			getAvailableAssetVocabularyIds());

		for (long assetVocabularyId : availableAssetVocabularyIdsSet) {
			if (Arrays.binarySearch(
					assetVocabularyIds, assetVocabularyId) < 0) {

				AssetVocabulary assetVocabulary =
					AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
						assetVocabularyId);

				assetVocabulary = assetVocabulary.toEscapedModel();

				availableVocabularNames.add(
					new KeyValuePair(
						String.valueOf(assetVocabularyId),
						getTitle(assetVocabulary)));
			}
		}

		return ListUtil.sort(
			availableVocabularNames, new KeyValuePairComparator(false, true));
	}

	public List<KeyValuePair> getCurrentVocabularyNames()
		throws PortalException {

		List<KeyValuePair> currentVocabularNames = new ArrayList<>();

		for (long assetVocabularyId : getAssetVocabularyIds()) {
			AssetVocabulary assetVocabulary =
				AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
					assetVocabularyId);

			assetVocabulary = assetVocabulary.toEscapedModel();

			currentVocabularNames.add(
				new KeyValuePair(
					String.valueOf(assetVocabularyId),
					getTitle(assetVocabulary)));
		}

		return currentVocabularNames;
	}

	public List<AssetVocabulary> getDDMTemplateAssetVocabularies()
		throws PortalException {

		if (_ddmTemplateAssetVocabularies != null) {
			return _ddmTemplateAssetVocabularies;
		}

		_ddmTemplateAssetVocabularies = new ArrayList<>();

		if (_assetCategoriesNavigationPortletInstanceConfiguration.
				allAssetVocabularies()) {

			_ddmTemplateAssetVocabularies = getAssetVocabularies();

			return _ddmTemplateAssetVocabularies;
		}

		for (long assetVocabularyId : getAssetVocabularyIds()) {
			_ddmTemplateAssetVocabularies.add(
				AssetVocabularyServiceUtil.fetchVocabulary(assetVocabularyId));
		}

		return _ddmTemplateAssetVocabularies;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle =
			_assetCategoriesNavigationPortletInstanceConfiguration.
				displayStyle();

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_assetCategoriesNavigationPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public boolean isAllAssetVocabularies() {
		if (_allAssetVocabularies != null) {
			return _allAssetVocabularies;
		}

		_allAssetVocabularies =
			_assetCategoriesNavigationPortletInstanceConfiguration.
				allAssetVocabularies();

		return _allAssetVocabularies;
	}

	protected String getTitle(AssetVocabulary assetVocabulary) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String title = assetVocabulary.getTitle(themeDisplay.getLanguageId());

		if (assetVocabulary.getGroupId() == themeDisplay.getCompanyGroupId()) {
			title += " (" + LanguageUtil.get(_request, "global") + ")";
		}

		return title;
	}

	private Boolean _allAssetVocabularies;
	private final AssetCategoriesNavigationPortletInstanceConfiguration
		_assetCategoriesNavigationPortletInstanceConfiguration;
	private List<AssetVocabulary> _assetVocabularies;
	private long[] _assetVocabularyIds;
	private long[] _availableAssetVocabularyIds;
	private List<AssetVocabulary> _ddmTemplateAssetVocabularies;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private final HttpServletRequest _request;

}