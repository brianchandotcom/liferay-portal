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

package com.liferay.asset.list.web.internal.servlet.taglib.util;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.info.display.url.provider.AssetInfoEditURLProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetListContentActionDropdownItems {

	public AssetListContentActionDropdownItems(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		AssetInfoEditURLProvider assetInfoEditURLProvider,
		HttpServletRequest httpServletRequest) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_assetInfoEditURLProvider = assetInfoEditURLProvider;

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems(AssetEntry assetEntry)
		throws Exception {

		return DropdownItemListBuilder.add(
			_getViewDisplayPageActionUnsafeConsumer(assetEntry)
		).add(
			_getEditContentActionUnsafeConsumer(assetEntry)
		).add(
			_getEditDisplayPageActionUnsafeConsumer(assetEntry)
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditContentActionUnsafeConsumer(AssetEntry assetEntry) {

		String editContentURL = _assetInfoEditURLProvider.getURL(
			assetEntry.getClassName(), assetEntry.getClassPK(),
			_httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData("action", "editContent");
			dropdownItem.putData("editContentURL", editContentURL);
			dropdownItem.setDisabled(Validator.isNull(editContentURL));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit-content"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getEditDisplayPageActionUnsafeConsumer(AssetEntry assetEntry)
		throws Exception {

		String editDisplayPageTemplateURL = _getEditDisplayPageTemplateURL(
			assetEntry);

		return dropdownItem -> {
			dropdownItem.putData("action", "editDisplayPageTemplate");
			dropdownItem.putData(
				"editDisplayPageTemplateURL", editDisplayPageTemplateURL);
			dropdownItem.setDisabled(
				Validator.isNull(editDisplayPageTemplateURL));
			dropdownItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "edit-display-page-template"));
		};
	}

	private String _getEditDisplayPageTemplateURL(AssetEntry assetEntry)
		throws Exception {

		LayoutPageTemplateEntry assetDisplayPageLayoutPageTemplateEntry =
			AssetDisplayPageUtil.getAssetDisplayPageLayoutPageTemplateEntry(
				_themeDisplay.getScopeGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassPK(), assetEntry.getClassTypeId());

		if (assetDisplayPageLayoutPageTemplateEntry == null) {
			return null;
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			assetDisplayPageLayoutPageTemplateEntry.getPlid());

		if (layout == null) {
			return null;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if ((draftLayout == null) ||
			!LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), draftLayout,
				ActionKeys.UPDATE)) {

			return null;
		}

		String editDisplayPageTemplateURL = PortalUtil.getLayoutFullURL(
			draftLayout, _themeDisplay);

		editDisplayPageTemplateURL = HttpUtil.setParameter(
			editDisplayPageTemplateURL, "p_l_back_url",
			_themeDisplay.getURLCurrent());

		editDisplayPageTemplateURL = HttpUtil.setParameter(
			editDisplayPageTemplateURL, "p_l_mode", Constants.EDIT);

		return editDisplayPageTemplateURL;
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getViewDisplayPageActionUnsafeConsumer(AssetEntry assetEntry)
		throws Exception {

		String viewDisplayPageURL = _getViewDisplayPageURL(assetEntry);

		return dropdownItem -> {
			dropdownItem.putData("action", "viewDisplayPage");
			dropdownItem.putData("viewDisplayPageURL", viewDisplayPageURL);
			dropdownItem.setDisabled(Validator.isNull(viewDisplayPageURL));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-display-page"));
		};
	}

	private String _getViewDisplayPageURL(AssetEntry assetEntry)
		throws Exception {

		if (_assetDisplayPageFriendlyURLProvider == null) {
			return null;
		}

		if (!AssetDisplayPageUtil.hasAssetDisplayPage(
				_themeDisplay.getScopeGroupId(), assetEntry)) {

			return null;
		}

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			assetEntry.getClassName(), assetEntry.getClassPK(), _themeDisplay);
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final AssetInfoEditURLProvider _assetInfoEditURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}