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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

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

		_layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getDropdownItems() {
		UnsafeConsumer<DropdownItem, Exception>
			editURLDropdownItemUnsafeConsumer =
				_getEditURLDropdownItemUnsafeConsumer(_infoEditURLProvider);

		return DropdownItemListBuilder.add(
			() -> editURLDropdownItemUnsafeConsumer != null,
			editURLDropdownItemUnsafeConsumer
		).add(
			() -> LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _themeDisplay.getLayout(),
				ActionKeys.UPDATE),
			dropdownItem -> {
				String editLayoutURL = PortalUtil.getLayoutFullURL(
					LayoutLocalServiceUtil.fetchDraftLayout(
						_themeDisplay.getPlid()),
					_themeDisplay);

				editLayoutURL = HttpComponentsUtil.setParameter(
					editLayoutURL, "p_l_back_url",
					_themeDisplay.getURLCurrent());

				editLayoutURL = HttpComponentsUtil.setParameter(
					editLayoutURL, "p_l_mode", Constants.EDIT);

				dropdownItem.setHref(editLayoutURL);

				dropdownItem.setLabel(
					LanguageUtil.get(
						_themeDisplay.getLocale(),
						"edit-display-page-template"));
			}
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditURLDropdownItemUnsafeConsumer(
			InfoEditURLProvider<Object> infoEditURLProvider) {

		if (infoEditURLProvider == null) {
			return null;
		}

		return dropdownItem -> {
			String editURL = _infoEditURLProvider.getURL(
				_layoutDisplayPageObjectProvider.getDisplayObject(),
				_httpServletRequest);

			dropdownItem.setHref(editURL);

			dropdownItem.setLabel(
				LanguageUtil.format(
					_httpServletRequest, "edit-x",
					_layoutDisplayPageObjectProvider.getTitle(
						_themeDisplay.getLocale())));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final InfoEditURLProvider<Object> _infoEditURLProvider;
	private final LayoutDisplayPageObjectProvider<?>
		_layoutDisplayPageObjectProvider;
	private final ThemeDisplay _themeDisplay;

}