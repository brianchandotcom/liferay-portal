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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class EditAssetDisplayMenuDisplayContext {

	public EditAssetDisplayMenuDisplayContext(HttpServletRequest request) {
		_request = request;

		_assetEntry = (AssetEntry)request.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);
		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				String editAssetEntryURL = _getEditAssetEntryURL();

				if (Validator.isNotNull(editAssetEntryURL)) {
					add(
						dropdownItem -> {
							dropdownItem.setHref(editAssetEntryURL);
							dropdownItem.setLabel(
								LanguageUtil.format(
									_request, "edit-x",
									_assetEntry.getTitle(
										_themeDisplay.getLocale())));
						});
				}

				if (LayoutPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getLayout(), ActionKeys.UPDATE)) {

					String editLayoutURL = HttpUtil.setParameter(
						_themeDisplay.getURLCurrent(), "p_l_mode",
						Constants.EDIT);

					add(
						dropdownItem -> {
							dropdownItem.setHref(editLayoutURL);
							dropdownItem.setLabel(
								LanguageUtil.get(
									_request, "edit-display-page-template"));
						});
				}
			}
		};
	}

	private String _getEditAssetEntryURL() throws Exception {
		if (_assetEntry == null) {
			return StringPool.BLANK;
		}

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_assetEntry.getClassName());

		if (assetRendererFactory == null) {
			return StringPool.BLANK;
		}

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			_assetEntry.getClassPK());

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		if (!assetRenderer.hasEditPermission(
				_themeDisplay.getPermissionChecker())) {

			return StringPool.BLANK;
		}

		PortletURL editAssetEntryURL = assetRenderer.getURLEdit(
			_request, LiferayWindowState.MAXIMIZED, null);

		if (editAssetEntryURL == null) {
			return StringPool.BLANK;
		}

		editAssetEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		editAssetEntryURL.setParameter(
			"hideDefaultSuccessMessage", Boolean.TRUE.toString());
		editAssetEntryURL.setParameter("showHeader", Boolean.FALSE.toString());

		return editAssetEntryURL.toString();
	}

	private final AssetEntry _assetEntry;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}