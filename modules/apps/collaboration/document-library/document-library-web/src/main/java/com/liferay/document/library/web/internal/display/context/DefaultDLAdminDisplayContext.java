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
import com.liferay.document.library.display.context.DLAdminDisplayContext;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DefaultDLAdminDisplayContext implements DLAdminDisplayContext {

	public DefaultDLAdminDisplayContext(
			PortletURL currentURL, ResourceBundle resourceBundle,
			LiferayPortletRequest request, LiferayPortletResponse response)
		throws PortletException {

		String mvcRenderCommandName = ParamUtil.getString(
			request, "mvcRenderCommandName", "/document_library/view");

		DLRequestHelper dlRequestHelper = new DLRequestHelper(
			request.getHttpServletRequest());

		ThemeDisplay themeDisplay = _getThemeDisplay(
			request.getHttpServletRequest());

		Portlet portlet = _getPortlet(themeDisplay);

		NavigationItem documentLibraryNavigationItem =
			_getDocumentLibraryNavigationItem(
				mvcRenderCommandName, currentURL, resourceBundle, response);

		_navigationItems.add(documentLibraryNavigationItem);

		if (DLPortletKeys.DOCUMENT_LIBRARY_ADMIN.equals(
				dlRequestHelper.getPortletName())) {

			NavigationItem fileEntryTypesNavigationItem =
				_getFileEntryTypesNavigationItem(
					currentURL, response, resourceBundle, mvcRenderCommandName);

			_navigationItems.add(fileEntryTypesNavigationItem);

			NavigationItem metadataSetsNavigationItem =
				_getMetadataSetsNavigationItem(
					request, themeDisplay, resourceBundle, portlet);

			_navigationItems.add(metadataSetsNavigationItem);
		}
	}

	@Override
	public List<NavigationItem> getNavigationItems() {
		return _navigationItems;
	}

	private NavigationItem _getDocumentLibraryNavigationItem(
			String mvcRenderCommandName, PortletURL currentURL,
			ResourceBundle resourceBundle, LiferayPortletResponse response)
		throws PortletException {

		PortletURL viewDocumentLibraryURL = PortletURLUtil.clone(
			currentURL, response);

		viewDocumentLibraryURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");
		viewDocumentLibraryURL.setParameter("redirect", currentURL.toString());

		NavigationItem documentLibraryNavigationItem = new NavigationItem();

		documentLibraryNavigationItem.setActive(
			!mvcRenderCommandName.equals(
				"/document_library/view_file_entry_types"));

		documentLibraryNavigationItem.setHref(
			viewDocumentLibraryURL.toString());

		documentLibraryNavigationItem.setLabel(
			ResourceBundleUtil.getString(
				resourceBundle, "documents-and-media"));

		return documentLibraryNavigationItem;
	}

	private NavigationItem _getFileEntryTypesNavigationItem(
			PortletURL currentURL, LiferayPortletResponse response,
			ResourceBundle resourceBundle, String mvcRenderCommandName)
		throws PortletException {

		PortletURL viewFileEntryTypesURL = PortletURLUtil.clone(
			currentURL, response);

		viewFileEntryTypesURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_file_entry_types");
		viewFileEntryTypesURL.setParameter("redirect", currentURL.toString());

		NavigationItem fileEntryTypesNavigationItem = new NavigationItem();

		fileEntryTypesNavigationItem.setActive(
			mvcRenderCommandName.equals(
				"/document_library/view_file_entry_types"));
		fileEntryTypesNavigationItem.setHref(viewFileEntryTypesURL.toString());
		fileEntryTypesNavigationItem.setLabel(
			ResourceBundleUtil.getString(resourceBundle, "document-types"));

		return fileEntryTypesNavigationItem;
	}

	private NavigationItem _getMetadataSetsNavigationItem(
		LiferayPortletRequest request, ThemeDisplay themeDisplay,
		ResourceBundle resourceBundle, Portlet portlet) {

		PortletURL viewMetadataSetsURL = PortletURLFactoryUtil.create(
			request,
			PortletProviderUtil.getPortletId(
				DDMStructure.class.getName(), PortletProvider.Action.VIEW),
			PortletRequest.RENDER_PHASE);

		viewMetadataSetsURL.setParameter("mvcPath", "/view.jsp");
		viewMetadataSetsURL.setParameter(
			"backURL", themeDisplay.getURLCurrent());
		viewMetadataSetsURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
		viewMetadataSetsURL.setParameter(
			"refererPortletName", DLPortletKeys.DOCUMENT_LIBRARY);
		viewMetadataSetsURL.setParameter(
			"refererWebDAVToken", WebDAVUtil.getStorageToken(portlet));
		viewMetadataSetsURL.setParameter(
			"showAncestorScopes", Boolean.TRUE.toString());
		viewMetadataSetsURL.setParameter(
			"showManageTemplates", Boolean.FALSE.toString());

		NavigationItem metadataSetsNavigationItem = new NavigationItem();

		metadataSetsNavigationItem.setActive(false);
		metadataSetsNavigationItem.setHref(viewMetadataSetsURL.toString());
		metadataSetsNavigationItem.setLabel(
			ResourceBundleUtil.getString(resourceBundle, "metadata-sets"));

		return metadataSetsNavigationItem;
	}

	private Portlet _getPortlet(ThemeDisplay themeDisplay) {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest request) {
		return (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
	}

	private List<NavigationItem> _navigationItems = new ArrayList<>();

}