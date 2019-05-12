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

package com.liferay.document.library.web.internal.info.display.url.provider;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = InfoEditURLProvider.class
)
public class DLFileEntryAssetInfoEditURLProvider
	implements InfoEditURLProvider<FileEntry> {

	@Override
	public String getURL(
		FileEntry fileEntry, HttpServletRequest httpServletRequest) {

		Group group = _groupLocalService.fetchGroup(fileEntry.getGroupId());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (group.isCompany()) {
			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, group, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0,
			0, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		return portletURL.toString();
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}