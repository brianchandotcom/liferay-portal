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

package com.liferay.content.dashboard.web.internal.util;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardConstants;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

/**
 * @author Alejandro Tardín
 * @author Yurena Cabrera
 */
public class ContentDashboardUtil {

	public static long[] getAssetVocabularyIds(RenderRequest renderRequest) {
		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String[] assetVocabularyIds = portletPreferences.getValues(
			"assetVocabularyIds", new String[0]);

		if (ArrayUtil.isNotEmpty(assetVocabularyIds)) {
			return GetterUtil.getLongValues(assetVocabularyIds);
		}

		long[] defaultAssetVocabularyIds = _getDefaultAssetVocabularyIds(
			renderRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			portletPreferences.setValues(
				"assetVocabularyIds",
				ArrayUtil.toStringArray(defaultAssetVocabularyIds));

			PortletPreferencesLocalServiceUtil.updatePreferences(
				themeDisplay.getUserId(), PortletKeys.PREFS_OWNER_TYPE_USER, 0,
				ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
				portletPreferences);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return defaultAssetVocabularyIds;
	}

	private static long[] _getDefaultAssetVocabularyIds(
		RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetVocabulary audienceAssetVocabulary =
			AssetVocabularyLocalServiceUtil.fetchGroupVocabulary(
				themeDisplay.getCompanyGroupId(),
				ContentDashboardConstants.DefaultInternalAssetVocabularyName.
					AUDIENCE.toString());
		AssetVocabulary stageAssetVocabulary =
			AssetVocabularyLocalServiceUtil.fetchGroupVocabulary(
				themeDisplay.getCompanyGroupId(),
				ContentDashboardConstants.DefaultInternalAssetVocabularyName.
					STAGE.toString());

		return new long[] {
			audienceAssetVocabulary.getVocabularyId(),
			stageAssetVocabulary.getVocabularyId()
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardUtil.class);

}