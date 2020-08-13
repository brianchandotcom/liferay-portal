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

import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class EditDisplayPageMenuDisplayContext {

	public EditDisplayPageMenuDisplayContext(
		HttpServletRequest httpServletRequest,
		InfoEditURLProvider<Object> infoEditURLProvider) {

		_httpServletRequest = httpServletRequest;
		_infoEditURLProvider = infoEditURLProvider;

		_infoDisplayObjectProvider =
			(InfoDisplayObjectProvider<?>)httpServletRequest.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getDropdownItems() throws Exception {
		String editURL = _infoEditURLProvider.getURL(
			_infoDisplayObjectProvider.getDisplayObject(), _httpServletRequest);

		return DropdownItemListBuilder.add(
			() ->
				(_infoEditURLProvider != null) && Validator.isNotNull(editURL),
			dropdownItem -> {
				dropdownItem.setHref(editURL);
				dropdownItem.setLabel(
					LanguageUtil.format(
						_httpServletRequest, "edit-x",
						_infoDisplayObjectProvider.getTitle(
							_themeDisplay.getLocale())));
			}
		).add(
			() -> LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _themeDisplay.getLayout(),
				ActionKeys.UPDATE),
			dropdownItem -> {
				Layout draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
					_themeDisplay.getPlid());

				String editLayoutURL = PortalUtil.getLayoutFullURL(
					draftLayout, _themeDisplay);

				editLayoutURL = HttpUtil.setParameter(
					editLayoutURL, "p_l_back_url",
					_themeDisplay.getURLCurrent());

				editLayoutURL = HttpUtil.setParameter(
					editLayoutURL, "p_l_mode", Constants.EDIT);

				dropdownItem.setHref(editLayoutURL);

				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", _themeDisplay.getLocale(), getClass());

				dropdownItem.setLabel(
					LanguageUtil.get(
						resourceBundle, "edit-display-page-template"));
			}
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final InfoDisplayObjectProvider<?> _infoDisplayObjectProvider;
	private final InfoEditURLProvider<Object> _infoEditURLProvider;
	private final ThemeDisplay _themeDisplay;

}