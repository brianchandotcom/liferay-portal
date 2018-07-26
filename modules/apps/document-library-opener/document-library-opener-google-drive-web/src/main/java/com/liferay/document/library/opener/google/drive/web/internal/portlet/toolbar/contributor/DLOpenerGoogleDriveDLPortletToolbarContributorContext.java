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

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.opener.google.drive.constants.DLOpenerGoogleDriveMimeTypes;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveWebConstants;
import com.liferay.document.library.portlet.toolbar.contributor.DLPortletToolbarContributorContext;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

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
@Component(immediate = true, service = DLPortletToolbarContributorContext.class)
public class DLOpenerGoogleDriveDLPortletToolbarContributorContext
	implements DLPortletToolbarContributorContext {

	@Override
	public void updatePortletTitleMenuItems(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(
			_translateKey(portletRequest, "google-docs-document"));
		urlMenuItem.setMethod(HttpMethods.POST);
		urlMenuItem.setURL(
			_getActionURL(
				portletRequest, folder,
				DLOpenerGoogleDriveMimeTypes.APPLICATION_VND_DOCX));

		menuItems.add(urlMenuItem);
	}

	private String _getActionURL(
		PortletRequest portletRequest, Folder folder, String contentType) {

		try {
			LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
				portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
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

			return liferayPortletURL.toString();
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private String _translateKey(PortletRequest portletRequest, String key) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				_portal.getLocale(portletRequest));

		return _language.get(resourceBundle, key);
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.opener.google.drive.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}