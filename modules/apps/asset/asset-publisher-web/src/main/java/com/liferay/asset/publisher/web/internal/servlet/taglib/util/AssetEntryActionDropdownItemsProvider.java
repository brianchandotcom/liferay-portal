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

package com.liferay.asset.publisher.web.internal.servlet.taglib.util;

import com.liferay.asset.kernel.action.AssetEntryAction;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryActionDropdownItemsProvider {

	public AssetEntryActionDropdownItemsProvider(
		AssetRenderer assetRenderer, List<AssetEntryAction> assetEntryActions,
		String fullContentRedirect, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_assetRenderer = assetRenderer;
		_assetEntryActions = assetEntryActions;
		_fullContentRedirect = fullContentRedirect;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				PortletURL editAssetEntryURL = _getEditAssetEntryURL();

				if (editAssetEntryURL != null) {
					add(
						dropdownItem -> {
							dropdownItem.setIcon("pencil");
							dropdownItem.putData(
								"useDialog", Boolean.FALSE.toString());
							dropdownItem.setHref(editAssetEntryURL.toString());
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "edit"));
						});
				}

				if (ListUtil.isNotEmpty(_assetEntryActions)) {
					for (AssetEntryAction assetEntryAction :
							_assetEntryActions) {

						try {
							if (!assetEntryAction.hasPermission(
									_themeDisplay.getPermissionChecker(),
									_assetRenderer)) {

								continue;
							}
						}
						catch (Exception e) {
							continue;
						}

						String title = assetEntryAction.getMessage(
							_themeDisplay.getLocale());

						add(
							dropdownItem -> {
								dropdownItem.setHref(
									assetEntryAction.getDialogURL(
										_httpServletRequest, _assetRenderer));
								dropdownItem.setIcon(
									assetEntryAction.getIcon());
								dropdownItem.putData(
									"destroyOnHide", Boolean.TRUE.toString());
								dropdownItem.putData(
									"useDialog", Boolean.TRUE.toString());
								dropdownItem.putData("title", title);
								dropdownItem.setLabel(title);
							});
					}
				}
			}
		};
	}

	private PortletURL _getEditAssetEntryURL() {
		boolean showEditURL = ParamUtil.getBoolean(
			_httpServletRequest, "showEditURL", true);

		if (!showEditURL) {
			return null;
		}

		try {
			if (!_assetRenderer.hasEditPermission(
					_themeDisplay.getPermissionChecker())) {

				return null;
			}

			String redirect = _themeDisplay.getURLCurrent();

			if (Validator.isNotNull(_fullContentRedirect)) {
				redirect = _fullContentRedirect;
			}

			PortletURL portletURL = _assetRenderer.getURLEdit(
				_liferayPortletRequest, _liferayPortletResponse,
				LiferayWindowState.NORMAL, redirect);

			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			portletURL.setParameter(
				"portletResource", portletDisplay.getPortletName());

			return portletURL;
		}
		catch (Exception e) {
		}

		return null;
	}

	private final List<AssetEntryAction> _assetEntryActions;
	private final AssetRenderer _assetRenderer;
	private final String _fullContentRedirect;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}