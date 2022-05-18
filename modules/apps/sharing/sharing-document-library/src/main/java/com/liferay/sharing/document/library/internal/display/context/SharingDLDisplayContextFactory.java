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

package com.liferay.sharing.document.library.internal.display.context;

import com.liferay.document.library.display.context.DLDisplayContextFactory;
import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.display.context.util.SharingDropdownItemFactory;
import com.liferay.sharing.display.context.util.SharingMenuItemFactory;
import com.liferay.sharing.display.context.util.SharingToolbarItemFactory;
import com.liferay.sharing.document.library.internal.constants.SharingDLWebKeys;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.service.SharingEntryLocalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = DLDisplayContextFactory.class)
public class SharingDLDisplayContextFactory implements DLDisplayContextFactory {

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DLFileEntryType dlFileEntryType) {

		return parentDLEditFileEntryDisplayContext;
	}

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileEntry fileEntry) {

		return parentDLEditFileEntryDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileShortcut fileShortcut) {

		return parentDLViewFileVersionDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion) {

		try {
			SharingConfiguration sharingConfiguration =
				_getSharingConfiguration(httpServletRequest);

			if (!sharingConfiguration.isEnabled()) {
				return parentDLViewFileVersionDisplayContext;
			}

			FileEntry fileEntry = null;

			if (fileVersion != null) {
				fileEntry = fileVersion.getFileEntry();
			}

			return new SharingDLViewFileVersionDisplayContext(
				parentDLViewFileVersionDisplayContext, httpServletRequest,
				httpServletResponse, fileEntry, fileVersion,
				_sharingEntryLocalService, _sharingDropdownItemFactory,
				_sharingMenuItemFactory, _sharingToolbarItemFactory,
				_sharingPermission, sharingConfiguration);
		}
		catch (PortalException portalException) {
			throw new SystemException(
				"Unable to create sharing document library view file version " +
					"display context for file version " + fileVersion,
				portalException);
		}
	}

	private SharingConfiguration _getSharingConfiguration(
		HttpServletRequest httpServletRequest) {

		SharingConfiguration sharingConfiguration =
			(SharingConfiguration)httpServletRequest.getAttribute(
				SharingDLWebKeys.SHARING_CONFIGURATION);

		if (sharingConfiguration != null) {
			return sharingConfiguration;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		sharingConfiguration =
			_sharingConfigurationFactory.getGroupSharingConfiguration(
				themeDisplay.getSiteGroup());

		httpServletRequest.setAttribute(
			SharingDLWebKeys.SHARING_CONFIGURATION, sharingConfiguration);

		return sharingConfiguration;
	}

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Reference
	private SharingDropdownItemFactory _sharingDropdownItemFactory;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingMenuItemFactory _sharingMenuItemFactory;

	@Reference
	private SharingPermission _sharingPermission;

	@Reference
	private SharingToolbarItemFactory _sharingToolbarItemFactory;

}