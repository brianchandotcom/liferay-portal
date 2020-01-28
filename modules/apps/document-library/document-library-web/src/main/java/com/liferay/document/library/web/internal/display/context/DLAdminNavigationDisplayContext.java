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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DLAdminNavigationDisplayContext {

	public DLAdminNavigationDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_httpServletRequest = liferayPortletRequest.getHttpServletRequest();

		_dlRequestHelper = new DLRequestHelper(_httpServletRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<NavigationItem> getNavigationItems() {
		String navigation = ParamUtil.getString(
			_httpServletRequest, "navigation");

		return new NavigationItemList() {
			{
				add(
					navigationItem -> _populateDocumentLibraryNavigationItem(
						navigationItem, navigation));

				if (DLPortletKeys.DOCUMENT_LIBRARY_ADMIN.equals(
						_dlRequestHelper.getPortletName())) {

					add(
						navigationItem -> _populateFileEntryTypesNavigationItem(
							navigationItem, navigation));

					add(
						navigationItem -> _populateMetadataSetsNavigationItem(
							navigationItem, navigation));
				}
			}
		};
	}

	private Portlet _getPortlet(ThemeDisplay themeDisplay) {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	}

	private void _populateDocumentLibraryNavigationItem(
		NavigationItem navigationItem, String navigation) {

		navigationItem.setActive(
			!navigation.equals("file_entry_types") &&
			!navigation.equals("file_entry_metadata_sets"));

		PortletURL viewDocumentLibraryURL =
			_liferayPortletResponse.createRenderURL();

		viewDocumentLibraryURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");
		viewDocumentLibraryURL.setParameter(
			"redirect", _currentURLObj.toString());

		navigationItem.setHref(viewDocumentLibraryURL.toString());

		navigationItem.setLabel(
			LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(),
				"documents-and-media"));
	}

	private void _populateFileEntryTypesNavigationItem(
		NavigationItem navigationItem, String navigation) {

		navigationItem.setActive(navigation.equals("file_entry_types"));

		PortletURL viewFileEntryTypesURL =
			_liferayPortletResponse.createRenderURL();

		viewFileEntryTypesURL.setParameter("navigation", "file_entry_types");
		viewFileEntryTypesURL.setParameter(
			"redirect", _currentURLObj.toString());

		navigationItem.setHref(viewFileEntryTypesURL.toString());

		navigationItem.setLabel(
			LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(),
				"document-types"));
	}

	private void _populateMetadataSetsNavigationItem(
		NavigationItem navigationItem, String navigation) {

		navigationItem.setActive(navigation.equals("file_entry_metadata_sets"));

		Portlet portlet = _getPortlet(_themeDisplay);

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("navigation", "file_entry_metadata_sets");
		portletURL.setParameter("backURL", _themeDisplay.getURLCurrent());
		portletURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		portletURL.setParameter(
			"refererWebDAVToken", WebDAVUtil.getStorageToken(portlet));
		portletURL.setParameter("redirect", _currentURLObj.toString());

		navigationItem.setHref(portletURL.toString());

		navigationItem.setLabel(
			LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(),
				"metadata-sets"));
	}

	private final PortletURL _currentURLObj;
	private final DLRequestHelper _dlRequestHelper;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}