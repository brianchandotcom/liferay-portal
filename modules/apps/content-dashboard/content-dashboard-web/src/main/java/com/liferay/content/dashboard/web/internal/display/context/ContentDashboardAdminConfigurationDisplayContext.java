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
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ContentDashboardAdminConfigurationDisplayContext {

	public ContentDashboardAdminConfigurationDisplayContext(
		AssetVocabularyLocalService assetVocabularyLocalService,
		String[] assetVocabularyNames, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
		_assetVocabularyNames = assetVocabularyNames;
		_groupLocalService = groupLocalService;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public ActionURL getActionURL() {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/content_dashboard/update_content_dashboard_configuration"
		).setRedirect(
			getRedirect()
		).buildActionURL();
	}

	public List<KeyValuePair> getAvailableVocabularyNames() {
		String[] assetVocabularyNames = ArrayUtil.clone(_assetVocabularyNames);

		Arrays.sort(assetVocabularyNames);

		List<AssetVocabulary> availableAssetVocabularies =
			_getAvailableAssetVocabularies();

		Stream<AssetVocabulary> stream = availableAssetVocabularies.stream();

		return stream.filter(
			assetVocabulary -> {
				int pos = Arrays.binarySearch(
					assetVocabularyNames, assetVocabulary.getName());

				return pos < 0;
			}
		).filter(
			Objects::nonNull
		).map(
			this::_toKeyValuePair
		).sorted(
			new KeyValuePairComparator(false, true)
		).collect(
			Collectors.toList()
		);
	}

	public List<KeyValuePair> getCurrentVocabularyNames() {
		return Stream.of(
			_assetVocabularyNames
		).map(
			assetVocabularyName ->
				_assetVocabularyLocalService.fetchGroupVocabulary(
					_themeDisplay.getCompanyGroupId(), assetVocabularyName)
		).filter(
			Objects::nonNull
		).map(
			this::_toKeyValuePair
		).collect(
			Collectors.toList()
		);
	}

	public String getRedirect() {
		return _themeDisplay.getURLCurrent();
	}

	private List<AssetVocabulary> _getAssetVocabularies() {
		if (_assetVocabularies != null) {
			return _assetVocabularies;
		}

		_assetVocabularies = _assetVocabularyLocalService.getGroupVocabularies(
			_getGroupIds(_themeDisplay.getCompanyId()));

		return _assetVocabularies;
	}

	private List<AssetVocabulary> _getAvailableAssetVocabularies() {
		if (_availableAssetVocabularies == null) {
			_availableAssetVocabularies = _getAssetVocabularies();
		}

		return _availableAssetVocabularies;
	}

	private long[] _getGroupIds(long companyId) {
		List<Long> groupIds = _groupLocalService.getGroupIds(companyId, true);

		Stream<Long> stream = groupIds.stream();

		return stream.mapToLong(
			groupId -> groupId
		).toArray();
	}

	private KeyValuePair _toKeyValuePair(AssetVocabulary assetVocabulary) {
		return new KeyValuePair(
			assetVocabulary.getName(),
			HtmlUtil.escape(
				assetVocabulary.getTitle(_themeDisplay.getLanguageId())));
	}

	private List<AssetVocabulary> _assetVocabularies;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final String[] _assetVocabularyNames;
	private List<AssetVocabulary> _availableAssetVocabularies;
	private final GroupLocalService _groupLocalService;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}