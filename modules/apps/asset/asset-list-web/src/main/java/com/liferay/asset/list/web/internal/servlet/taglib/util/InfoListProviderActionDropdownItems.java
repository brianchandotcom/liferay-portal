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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class InfoListProviderActionDropdownItems {

	public InfoListProviderActionDropdownItems(
		InfoListProvider<?> infoListProvider,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_infoListProvider = infoListProvider;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return DropdownItemListBuilder.add(
			_getViewInfoListProviderItemsActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getViewInfoListProviderItemsActionUnsafeConsumer()
		throws Exception {

		PortletURL viewInfoListProviderItemsURL =
			_liferayPortletResponse.createRenderURL();

		viewInfoListProviderItemsURL.setParameter(
			"mvcPath", "/view_info_list_provider_items.jsp");
		viewInfoListProviderItemsURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		viewInfoListProviderItemsURL.setParameter(
			"infoListProviderKey", String.valueOf(_infoListProvider.getKey()));

		viewInfoListProviderItemsURL.setWindowState(LiferayWindowState.POP_UP);

		return dropdownItem -> {
			dropdownItem.putData("action", "viewInfoListProviderItems");
			dropdownItem.putData(
				"infoListProviderTitle",
				_infoListProvider.getLabel(_themeDisplay.getLocale()));
			dropdownItem.putData(
				"viewInfoListProviderItemsURL",
				String.valueOf(viewInfoListProviderItemsURL));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-items"));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final InfoListProvider<?> _infoListProvider;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}