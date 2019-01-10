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

package com.liferay.layout.type.controller.asset.display.internal.display.context;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.constants.AssetDisplayWebKeys;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.util.LayoutPageTemplateStructureRenderUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jürgen Kappler
 */
public class AssetDisplayLayoutTypeControllerDisplayContext {

	public AssetDisplayLayoutTypeControllerDisplayContext(
		HttpServletRequest request, HttpServletResponse response) {

		_request = request;
		_response = response;

		_assetEntry = (AssetEntry)request.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);
	}

	public AssetEntry getAssetEntry() {
		return _assetEntry;
	}

	public String getRenderedContent() throws PortalException {
		String content = StringPool.BLANK;

		if (_assetEntry == null) {
			return content;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					_assetEntry.getGroupId(),
					PortalUtil.getClassNameId(
						LayoutPageTemplateEntry.class.getName()),
					_getLayoutPageTemplateEntryId(), true);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String currentI18nLanguageId = GetterUtil.getString(
			_request.getAttribute(AssetDisplayWebKeys.CURRENT_I18N_LANGUAGE_ID),
			themeDisplay.getLanguageId());

		try {
			content = LayoutPageTemplateStructureRenderUtil.renderLayoutContent(
				_request, _response, layoutPageTemplateStructure,
				FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE,
				_getAssetDisplayFieldsValues());
		}
		finally {
			_request.setAttribute(
				WebKeys.I18N_LANGUAGE_ID, currentI18nLanguageId);
		}

		return content;
	}

	private Map<String, Object> _getAssetDisplayFieldsValues()
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetDisplayContributor assetDisplayContributor =
			(AssetDisplayContributor)_request.getAttribute(
				AssetDisplayWebKeys.ASSET_DISPLAY_CONTRIBUTOR);

		long versionClassPK = GetterUtil.getLong(
			_request.getAttribute(AssetDisplayWebKeys.VERSION_CLASS_PK));

		if (versionClassPK > 0) {
			return assetDisplayContributor.getAssetDisplayFieldsValues(
				_assetEntry, versionClassPK, themeDisplay.getLocale());
		}

		return assetDisplayContributor.getAssetDisplayFieldsValues(
			_assetEntry, themeDisplay.getLocale());
	}

	private long _getLayoutPageTemplateEntryId() {
		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryLocalServiceUtil.fetchAssetDisplayPageEntry(
				_assetEntry.getGroupId(), _assetEntry.getClassNameId(),
				_assetEntry.getClassPK());

		if ((assetDisplayPageEntry == null) ||
			(assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_NONE)) {

			return 0;
		}

		if (assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_SPECIFIC) {

			return assetDisplayPageEntry.getLayoutPageTemplateEntryId();
		}

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					_assetEntry.getGroupId(), _assetEntry.getClassNameId(),
					_assetEntry.getClassTypeId());

		if (defaultLayoutPageTemplateEntry != null) {
			return defaultLayoutPageTemplateEntry.
				getLayoutPageTemplateEntryId();
		}

		return 0;
	}

	private final AssetEntry _assetEntry;
	private final HttpServletRequest _request;
	private final HttpServletResponse _response;

}