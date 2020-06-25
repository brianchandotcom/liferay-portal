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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.content.dashboard.web.internal.configuration.ContentDashboardAdminConfiguration;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ContentDashboardAdminConfigurationDisplayContext {

	public ContentDashboardAdminConfigurationDisplayContext(
		HttpServletRequest httpServletRequest) {

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<KeyValuePair> getAvailableVocabularyNames() throws Exception {
		List<KeyValuePair> availableVocabularNames = new ArrayList<>();

		String[] assetVocabularyNames = _getAssetVocabularyNames();

		Arrays.sort(assetVocabularyNames);

		Set<String> availableAssetVocabularyNamesSet = SetUtil.fromArray(
			_getAvailableAssetVocabularyNames());

		for (String assetVocabularyName : availableAssetVocabularyNamesSet) {
			if (Arrays.binarySearch(assetVocabularyNames, assetVocabularyName) <
					0) {

				AssetVocabulary assetVocabulary =
					AssetVocabularyLocalServiceUtil.fetchGroupVocabulary(
						_themeDisplay.getCompanyGroupId(), assetVocabularyName);

				if (assetVocabulary == null) {
					continue;
				}

				availableVocabularNames.add(
					new KeyValuePair(
						assetVocabulary.getName(),
						HtmlUtil.escape(
							assetVocabulary.getTitle(
								_themeDisplay.getLanguageId()))));
			}
		}

		return ListUtil.sort(
			availableVocabularNames, new KeyValuePairComparator(false, true));
	}

	public List<KeyValuePair> getCurrentVocabularyNames() throws Exception {
		List<KeyValuePair> currentVocabularNames = new ArrayList<>();

		for (String assetVocabularyName : _getAssetVocabularyNames()) {
			AssetVocabulary assetVocabulary =
				AssetVocabularyLocalServiceUtil.fetchGroupVocabulary(
					_themeDisplay.getCompanyGroupId(), assetVocabularyName);

			if (assetVocabulary == null) {
				continue;
			}

			currentVocabularNames.add(
				new KeyValuePair(
					assetVocabulary.getName(),
					HtmlUtil.escape(
						assetVocabulary.getTitle(
							_themeDisplay.getLanguageId()))));
		}

		return currentVocabularNames;
	}

	private List<AssetVocabulary> _getAssetVocabularies() {
		if (_assetVocabularies != null) {
			return _assetVocabularies;
		}

		_assetVocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(
			new long[] {_themeDisplay.getCompanyGroupId()});

		return _assetVocabularies;
	}

	private String[] _getAssetVocabularyNames() throws Exception {
		if (_assetVocabularyNames != null) {
			return _assetVocabularyNames;
		}

		ContentDashboardAdminConfiguration contentDashboardAdminConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				ContentDashboardAdminConfiguration.class,
				_themeDisplay.getCompanyId());

		_assetVocabularyNames =
			contentDashboardAdminConfiguration.assetVocabularyNames();

		return _assetVocabularyNames;
	}

	private String[] _getAvailableAssetVocabularyNames() {
		if (_availableAssetVocabularyNames != null) {
			return _availableAssetVocabularyNames;
		}

		List<AssetVocabulary> assetVocabularies = _getAssetVocabularies();

		_availableAssetVocabularyNames = new String[assetVocabularies.size()];

		for (int i = 0; i < assetVocabularies.size(); i++) {
			AssetVocabulary assetVocabulary = assetVocabularies.get(i);

			_availableAssetVocabularyNames[i] = assetVocabulary.getName();
		}

		return _availableAssetVocabularyNames;
	}

	private List<AssetVocabulary> _assetVocabularies;
	private String[] _assetVocabularyNames;
	private String[] _availableAssetVocabularyNames;
	private final ThemeDisplay _themeDisplay;

}