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

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class BasicFragmentCompositionActionDropdownItemsProvider {

	public BasicFragmentCompositionActionDropdownItemsProvider(
		FragmentComposition fragmentComposition, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_fragmentComposition = fragmentComposition;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				if (FragmentPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

					add(_getDeleteFragmentCompositionActionUnsafeConsumer());
				}
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteFragmentCompositionActionUnsafeConsumer() {

		PortletURL deleteFragmentCompositionURL =
			_renderResponse.createActionURL();

		deleteFragmentCompositionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/delete_fragment_compositions");
		deleteFragmentCompositionURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteFragmentCompositionURL.setParameter(
			"fragmentCompositionId",
			String.valueOf(_fragmentComposition.getFragmentCompositionId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteFragmentComposition");
			dropdownItem.putData(
				"deleteFragmentCompositionURL",
				deleteFragmentCompositionURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private final FragmentComposition _fragmentComposition;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}