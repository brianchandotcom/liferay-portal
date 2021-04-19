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

package com.liferay.document.library.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.InputStream;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DLFileShortcutCTDisplayRenderer
	extends BaseCTDisplayRenderer<DLFileShortcut> {

	@Override
	public InputStream getDownloadInputStream(
			DLFileShortcut dlFileShortcut, String version)
		throws PortalException {

		FileVersion fileVersion = dlFileShortcut.getFileVersion();

		return fileVersion.getContentStream(false);
	}

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest,
			DLFileShortcut dlFileShortcut)
		throws PortalException {

		Group group = _groupLocalService.getGroup(dlFileShortcut.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
				0, 0, PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/document_library/edit_file_shortcut"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"fileShortcutId", dlFileShortcut.getFileShortcutId()
		).setParameter(
			"folderId", dlFileShortcut.getFolderId()
		).setParameter(
			"repositoryId", dlFileShortcut.getRepositoryId()
		).setParameter(
			"toFileEntryId", dlFileShortcut.getToFileEntryId()
		).buildString();
	}

	@Override
	public Class<DLFileShortcut> getModelClass() {
		return DLFileShortcut.class;
	}

	@Override
	public String getTitle(Locale locale, DLFileShortcut dlFileShortcut) {
		return dlFileShortcut.getToTitle();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DLFileShortcut> displayBuilder)
		throws PortalException {

		DLFileShortcut dlFileShortcut = displayBuilder.getModel();

		displayBuilder.display(
			"title", dlFileShortcut.getToTitle()
		).display(
			"created-by",
			() -> {
				String userName = dlFileShortcut.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", dlFileShortcut.getCreateDate()
		).display(
			"folder",
			() -> {
				Folder folder = dlFileShortcut.getFolder();

				return folder.getName();
			}
		).display(
			"download", _getDownloadLink(displayBuilder, dlFileShortcut), false
		);
	}

	private String _getDownloadLink(
			DisplayBuilder<?> displayBuilder, DLFileShortcut dlFileShortcut)
		throws PortalException {

		FileVersion fileVersion = dlFileShortcut.getFileVersion();

		return DLFileVersionCTDisplayRenderer.getDownloadLink(
			displayBuilder, (DLFileVersion)fileVersion.getModel());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}