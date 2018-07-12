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

package com.liferay.document.library.opener.google.drive.web.internal.display.context;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.opener.google.drive.constants.GoogleDriveMimeTypes;
import com.liferay.document.library.opener.google.drive.service.GoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.constants.GoogleDriveOpenerWebConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptUIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class GoogleDriveOpenerDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public GoogleDriveOpenerDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion, Function<String, String> translationFunction,
		GoogleDriveManager googleDriveManager) {

		super(_UUID, parentDLDisplayContext, request, response, fileVersion);

		_translationFunction = translationFunction;
		_googleDriveManager = googleDriveManager;
	}

	@Override
	public Menu getMenu() throws PortalException {
		if (!GoogleDriveMimeTypes.isMimeTypeSupported(
				fileVersion.getMimeType())) {

			return super.getMenu();
		}

		Menu menu = super.getMenu();

		if (_isCheckedOutInGoogleDrive()) {
			Collection<MenuItem> menuItems = _patchMenuItems(
				menu.getMenuItems());

			menuItems.add(_createEditInGoogleDriveMenuItem());

			return menu;
		}

		List<MenuItem> menuItems = menu.getMenuItems();

		menuItems.add(_createCheckoutInGoogleDriveMenuItem());

		return menu;
	}

	private MenuItem _createCheckoutInGoogleDriveMenuItem() {
		URLMenuItem menuItem = new URLMenuItem();

		menuItem.setLabel(
			_translationFunction.apply("checkout-to-google-drive"));
		menuItem.setMethod(HttpMethods.POST);
		menuItem.setURL(
			_getActionURL(GoogleDriveOpenerWebConstants.GOOGLE_DRIVE_CHECKOUT));

		return menuItem;
	}

	private MenuItem _createEditInGoogleDriveMenuItem() {
		URLMenuItem menuItem = new URLMenuItem();

		menuItem.setLabel(_translationFunction.apply("edit-in-google-drive"));
		menuItem.setMethod(HttpMethods.POST);
		menuItem.setURL(
			_getActionURL(GoogleDriveOpenerWebConstants.GOOGLE_DRIVE_EDIT));

		return menuItem;
	}

	private String _getActionURL(String cmd) {
		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			request, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			PortletRequest.ACTION_PHASE);

		liferayPortletURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/edit_in_google_docs");
		liferayPortletURL.setParameter(
			"fileEntryId", String.valueOf(fileVersion.getFileEntryId()));
		liferayPortletURL.setParameter("cmd", cmd);

		return liferayPortletURL.toString();
	}

	private LiferayPortletResponse _getLiferayPortletResponse() {
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	private String _getNamespace() {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		return liferayPortletResponse.getNamespace();
	}

	private boolean _isCheckedOutInGoogleDrive() throws PortalException {
		FileEntry fileEntry = fileVersion.getFileEntry();

		if (fileEntry.isCheckedOut() ||
			_googleDriveManager.isGoogleDriveFile(fileEntry)) {

			return true;
		}

		return false;
	}

	private Collection<MenuItem> _patchMenuItems(
		Collection<MenuItem> menuItems) {

		for (MenuItem menuItem : menuItems) {
			if (DLUIItemKeys.CHECKIN.equals(menuItem.getKey())) {
				if (menuItem instanceof JavaScriptUIItem) {
					JavaScriptUIItem javaScriptUIItem =
						(JavaScriptUIItem)menuItem;

					javaScriptUIItem.setOnClick(
						StringBundler.concat(
							_getNamespace(), "showVersionDetailsDialog('",
							_getActionURL(
								GoogleDriveOpenerWebConstants.
									GOOGLE_DRIVE_CHECKIN),
							"');"));
				}
			}
			else if (DLUIItemKeys.CANCEL_CHECKOUT.equals(menuItem.getKey())) {
				if (menuItem instanceof URLMenuItem) {
					URLMenuItem urlMenuItem = (URLMenuItem)menuItem;

					urlMenuItem.setMethod(HttpMethods.POST);
					urlMenuItem.setURL(
						_getActionURL(
							GoogleDriveOpenerWebConstants.
								GOOGLE_DRIVE_CANCEL_CHECKOUT));
				}
			}
		}

		return menuItems;
	}

	private static final UUID _UUID = UUID.fromString(
		"c3a385d0-7551-11e8-9798-186590d14d8f");

	private final GoogleDriveManager _googleDriveManager;
	private final Function<String, String> _translationFunction;

}