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

package com.liferay.asset.publisher.web.internal.server.taglib.util;

import com.liferay.asset.kernel.action.AssetEntryAction;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
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

		_request = PortalUtil.getHttpServletRequest(liferayPortletRequest);

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				PortletURL editAssetEntryURL = _getEditAssetEntryURL();

				if (editAssetEntryURL != null) {
					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.putData(
									"destroyOnHide", Boolean.TRUE.toString());

								PortletDisplay portletDisplay =
									_themeDisplay.getPortletDisplay();

								String id = HtmlUtil.escape(
									portletDisplay.getNamespace());

								dropdownItem.putData("id", id + "editAsset");

								dropdownItem.putData(
									"title",
									LanguageUtil.format(
										_request, "edit-x",
										_assetRenderer.getTitle(
											_themeDisplay.getLocale())));
								dropdownItem.setHref(
									editAssetEntryURL.toString());
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "edit"));
							}));
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
							SafeConsumer.ignore(
								dropdownItem -> {
									dropdownItem.setHref(
										assetEntryAction.getDialogURL(
											_request, _assetRenderer));
									dropdownItem.putData(
										"destroyOnHide",
										Boolean.TRUE.toString());
									dropdownItem.putData("title", title);
									dropdownItem.setLabel(title);
								}));
					}
				}
			}
		};
	}

	private PortletURL _getEditAssetEntryURL() {
		boolean showEditURL = ParamUtil.getBoolean(
			_request, "showEditURL", true);

		if (!showEditURL) {
			return null;
		}

		try {
			if (!_assetRenderer.hasEditPermission(
					_themeDisplay.getPermissionChecker())) {

				return null;
			}

			PortletURL editAssetEntryURL = _assetRenderer.getURLEdit(
				_liferayPortletRequest, _liferayPortletResponse,
				LiferayWindowState.MAXIMIZED, null);

			if (Validator.isNotNull(_fullContentRedirect)) {
				editAssetEntryURL.setParameter(
					"redirect", _fullContentRedirect);
			}
			else {
				editAssetEntryURL.setParameter(
					"redirect", _themeDisplay.getURLCurrent());
			}

			if (editAssetEntryURL == null) {
				return null;
			}

			editAssetEntryURL.setParameter(
				"hideDefaultSuccessMessage", Boolean.TRUE.toString());
			editAssetEntryURL.setParameter(
				"showHeader", Boolean.FALSE.toString());

			return editAssetEntryURL;
		}
		catch (Exception e) {
		}

		return null;
	}

	private final List<AssetEntryAction> _assetEntryActions;
	private final AssetRenderer _assetRenderer;
	private final String _fullContentRedirect;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}