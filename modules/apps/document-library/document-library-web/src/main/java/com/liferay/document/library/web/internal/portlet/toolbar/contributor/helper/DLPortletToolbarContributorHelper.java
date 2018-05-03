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

package com.liferay.document.library.web.internal.portlet.toolbar.contributor.helper;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DLPortletToolbarContributorHelper.class)
public class DLPortletToolbarContributorHelper {

	public Folder getFolder(
		ThemeDisplay themeDisplay, PortletRequest portletRequest) {

		Folder folder = (Folder)portletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FOLDER);

		if (folder != null) {
			return folder;
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		long rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		try {
			DLPortletInstanceSettings dlPortletInstanceSettings =
				DLPortletInstanceSettings.getInstance(
					themeDisplay.getLayout(), portletDisplay.getId());

			rootFolderId = dlPortletInstanceSettings.getRootFolderId();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			try {
				folder = _dlAppLocalService.getFolder(rootFolderId);
			}
			catch (NoSuchFolderException | PrincipalException e) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}

				folder = null;
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return folder;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLPortletToolbarContributorHelper.class);

	private DLAppLocalService _dlAppLocalService;

}