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

package com.liferay.document.library.opener.google.drive.web.internal.portlet.toolbar.contributor;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.constants.DLOpenerGoogleDriveMimeTypes;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveWebConstants;
import com.liferay.document.library.portlet.toolbar.contributor.DLPortletToolbarContributorContext;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLPortletToolbarContributorContext.class)
public class DLOpenerGoogleDriveDLPortletToolbarContributorContext
	implements DLPortletToolbarContributorContext {

	@Override
	public void updatePortletTitleMenuItems(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		try {
			long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			if (folder != null) {
				folderId = folder.getFolderId();
			}

			if (!_dlOpenerGoogleDriveManager.isConfigured(
					themeDisplay.getCompanyId()) ||
				!ModelResourcePermissionHelper.contains(
					_folderEntryModelResourcePermission,
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroupId(), folderId,
					ActionKeys.ADD_DOCUMENT)) {

				return;
			}

			menuItems.add(
				_createURLMenuItem(
					portletRequest, folder, "google-docs",
					DLOpenerGoogleDriveMimeTypes.APPLICATION_VND_DOCX));
			menuItems.add(
				_createURLMenuItem(
					portletRequest, folder, "google-slides",
					DLOpenerGoogleDriveMimeTypes.APPLICATION_VND_PPTX));
			menuItems.add(
				_createURLMenuItem(
					portletRequest, folder, "google-sheets",
					DLOpenerGoogleDriveMimeTypes.APPLICATION_VND_XSLX));
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	private URLMenuItem _createURLMenuItem(
		PortletRequest portletRequest, Folder folder, String key,
		String contentType) {

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(_translateKey(portletRequest, key));
		urlMenuItem.setMethod(HttpMethods.POST);
		urlMenuItem.setURL(_getActionURL(portletRequest, folder, contentType));

		return urlMenuItem;
	}

	private String _getActionURL(
		PortletRequest portletRequest, Folder folder, String contentType) {

		try {
			LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
				portletRequest, _portal.getPortletId(portletRequest),
				PortletRequest.ACTION_PHASE);

			liferayPortletURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/document_library/edit_in_google_docs");
			liferayPortletURL.setParameter(
				Constants.CMD,
				DLOpenerGoogleDriveWebConstants.GOOGLE_DRIVE_ADD);

			long repositoryId = BeanPropertiesUtil.getLong(
				folder, "repositoryId",
				_portal.getScopeGroupId(portletRequest));

			liferayPortletURL.setParameter(
				"repositoryId", String.valueOf(repositoryId));

			long folderId = BeanPropertiesUtil.getLong(
				folder, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			liferayPortletURL.setParameter(
				"folderId", String.valueOf(folderId));

			liferayPortletURL.setParameter("contentType", contentType);
			liferayPortletURL.setParameter(
				"googleDocsRedirect",
				_portal.getCurrentCompleteURL(
					_portal.getHttpServletRequest(portletRequest)));

			return liferayPortletURL.toString();
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private String _translateKey(PortletRequest portletRequest, String key) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_portal.getLocale(portletRequest),
			DLOpenerGoogleDriveDLPortletToolbarContributorContext.class);

		return _language.get(resourceBundle, key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLOpenerGoogleDriveDLPortletToolbarContributorContext.class);

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private ModelResourcePermission<Folder> _folderEntryModelResourcePermission;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}