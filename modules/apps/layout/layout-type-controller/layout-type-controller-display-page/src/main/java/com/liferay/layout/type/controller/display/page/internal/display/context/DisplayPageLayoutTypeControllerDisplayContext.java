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

package com.liferay.layout.type.controller.display.page.internal.display.context;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.constants.SegmentsWebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageLayoutTypeControllerDisplayContext {

	public DisplayPageLayoutTypeControllerDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_infoDisplayObjectProvider =
			(InfoDisplayObjectProvider)httpServletRequest.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

		InfoDisplayContributor infoDisplayContributor =
			(InfoDisplayContributor)_httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR);

		if ((infoDisplayContributor == null) &&
			(_infoDisplayObjectProvider != null)) {

			InfoDisplayContributorTracker infoDisplayContributorTracker =
				(InfoDisplayContributorTracker)httpServletRequest.getAttribute(
					ContentPageEditorWebKeys.ASSET_DISPLAY_CONTRIBUTOR_TRACKER);

			infoDisplayContributor =
				infoDisplayContributorTracker.getInfoDisplayContributor(
					PortalUtil.getClassName(
						_infoDisplayObjectProvider.getClassNameId()));
		}

		_infoDisplayContributor = infoDisplayContributor;
	}

	public AssetRendererFactory getAssetRendererFactory() {
		if (_infoDisplayContributor == null) {
			return null;
		}

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassNameId(
				_infoDisplayObjectProvider.getClassNameId());
	}

	public Map<String, Object> getInfoDisplayFieldsValues()
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long versionClassPK = GetterUtil.getLong(
			_httpServletRequest.getAttribute(
				InfoDisplayWebKeys.VERSION_CLASS_PK));

		if (versionClassPK > 0) {
			return _infoDisplayContributor.getVersionInfoDisplayFieldsValues(
				_infoDisplayObjectProvider.getDisplayObject(), versionClassPK,
				themeDisplay.getLocale());
		}

		return _infoDisplayContributor.getInfoDisplayFieldsValues(
			_infoDisplayObjectProvider.getDisplayObject(),
			themeDisplay.getLocale());
	}

	public InfoDisplayObjectProvider getInfoDisplayObjectProvider() {
		return _infoDisplayObjectProvider;
	}

	public long getLayoutPageTemplateEntryId() {
		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryLocalServiceUtil.fetchAssetDisplayPageEntry(
				_infoDisplayObjectProvider.getGroupId(),
				_infoDisplayObjectProvider.getClassNameId(),
				_infoDisplayObjectProvider.getClassPK());

		if (assetDisplayPageEntry != null) {
			if (assetDisplayPageEntry.getType() ==
					AssetDisplayPageConstants.TYPE_NONE) {

				return 0;
			}

			if (assetDisplayPageEntry.getType() ==
					AssetDisplayPageConstants.TYPE_SPECIFIC) {

				return assetDisplayPageEntry.getLayoutPageTemplateEntryId();
			}
		}

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					_infoDisplayObjectProvider.getGroupId(),
					_infoDisplayObjectProvider.getClassNameId(),
					_infoDisplayObjectProvider.getClassTypeId());

		if (defaultLayoutPageTemplateEntry != null) {
			return defaultLayoutPageTemplateEntry.
				getLayoutPageTemplateEntryId();
		}

		return 0;
	}

	public long[] getSegmentExperienceIds() {
		return GetterUtil.getLongValues(
			_httpServletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
			new long[] {SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT});
	}

	public JSONArray getStructureJSONArray() throws PortalException {
		InfoDisplayObjectProvider infoDisplayObjectProvider =
			getInfoDisplayObjectProvider();

		if (infoDisplayObjectProvider == null) {
			return null;
		}

		long[] segmentsExperienceIds = getSegmentExperienceIds();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.getLayoutPageTemplateEntry(
				getLayoutPageTemplateEntryId());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					infoDisplayObjectProvider.getGroupId(),
					PortalUtil.getClassNameId(Layout.class.getName()),
					layoutPageTemplateEntry.getPlid(), true);

		String data = layoutPageTemplateStructure.getData(
			segmentsExperienceIds);

		if (Validator.isNull(data)) {
			return null;
		}

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

		return dataJSONObject.getJSONArray("structure");
	}

	private final HttpServletRequest _httpServletRequest;
	private final InfoDisplayContributor _infoDisplayContributor;
	private final InfoDisplayObjectProvider _infoDisplayObjectProvider;

}