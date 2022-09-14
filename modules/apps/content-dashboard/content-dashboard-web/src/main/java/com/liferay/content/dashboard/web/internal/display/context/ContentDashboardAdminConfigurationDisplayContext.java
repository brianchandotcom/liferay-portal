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
import com.liferay.content.dashboard.web.internal.util.ContentDashboardGroupUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
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
		long[] assetVocabularyIds, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
		_assetVocabularyIds = assetVocabularyIds;
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

	public JSONArray getAvailableVocabularyJSONArray() {
		long[] assetVocabularyIds = ArrayUtil.clone(_assetVocabularyIds);

		Arrays.sort(assetVocabularyIds);

		List<AssetVocabulary> availableAssetVocabularies =
			_getAvailableAssetVocabularies();

		Stream<AssetVocabulary> stream = availableAssetVocabularies.stream();

		return stream.filter(
			assetVocabulary -> {
				int pos = Arrays.binarySearch(
					assetVocabularyIds, assetVocabulary.getVocabularyId());

				return pos < 0;
			}
		).sorted(
			Comparator.comparing(
				this::_getAssetVocabularyLabel, String.CASE_INSENSITIVE_ORDER)
		).map(
			this::_toJSONObject
		).collect(
			JSONUtil.createCollector()
		);
	}

	public JSONArray getCurrentVocabularyJSONArray() {
		return Arrays.stream(
			_assetVocabularyIds
		).boxed(
		).map(
			assetVocabularyId -> {
				try {
					return _assetVocabularyLocalService.getAssetVocabulary(
						assetVocabularyId);
				}
				catch (PortalException portalException) {
					_log.error(portalException);

					return null;
				}
			}
		).filter(
			Objects::nonNull
		).map(
			this::_toJSONObject
		).collect(
			JSONUtil.createCollector()
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

	private String _getAssetVocabularyLabel(AssetVocabulary assetVocabulary) {
		String assetVocabularyTitle = assetVocabulary.getTitle(
			_themeDisplay.getLanguageId());

		Group group = _groupLocalService.fetchGroup(
			assetVocabulary.getGroupId());

		if (group == null) {
			return assetVocabularyTitle;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		return LanguageUtil.format(
			resourceBundle, "x-group-x",
			new String[] {
				assetVocabularyTitle,
				ContentDashboardGroupUtil.getGroupName(
					group, resourceBundle.getLocale())
			});
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

	private JSONObject _toJSONObject(AssetVocabulary assetVocabulary) {
		Group group = _groupLocalService.fetchGroup(
			assetVocabulary.getGroupId());

		return JSONUtil.put(
			"global", group.isCompany()
		).put(
			"label", HtmlUtil.escape(_getAssetVocabularyLabel(assetVocabulary))
		).put(
			"site", assetVocabulary.getGroupId()
		).put(
			"value", assetVocabulary.getVocabularyId()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardAdminConfigurationDisplayContext.class);

	private List<AssetVocabulary> _assetVocabularies;
	private final long[] _assetVocabularyIds;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private List<AssetVocabulary> _availableAssetVocabularies;
	private final GroupLocalService _groupLocalService;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}