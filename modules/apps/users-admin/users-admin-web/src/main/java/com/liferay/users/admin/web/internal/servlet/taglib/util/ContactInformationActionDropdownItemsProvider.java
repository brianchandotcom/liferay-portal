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

package com.liferay.users.admin.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gislayne Vitorino
 */
public class ContactInformationActionDropdownItemsProvider {

	public ContactInformationActionDropdownItemsProvider(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		String listType, String mvcPath, long primaryKey) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
		_listType = listType;
		_mvcPath = mvcPath;
		_primaryKey = primaryKey;

		_className = (String)httpServletRequest.getAttribute(
			"contact_information.jsp-className");
		_classPK = (long)httpServletRequest.getAttribute(
			"contact_information.jsp-classPK");
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setMVCPath(
						_mvcPath
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"className", _className
					).setParameter(
						"classPK", _classPK
					).setParameter(
						"primaryKey", _primaryKey
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "edit"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.create(
						_getActionURL()
					).setCMD(
						"makePrimary"
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "make-primary"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.create(
						_getActionURL()
					).setCMD(
						Constants.DELETE
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "remove"));
			}
		).build();
	}

	private PortletURL _getActionURL() {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/users_admin/update_contact_information"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"className", _className
		).setParameter(
			"classPK", _classPK
		).setParameter(
			"listType", _listType
		).setParameter(
			"primaryKey", _primaryKey
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildPortletURL();
	}

	private final String _className;
	private final long _classPK;
	private final HttpServletRequest _httpServletRequest;
	private final String _listType;
	private final String _mvcPath;
	private final long _primaryKey;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}