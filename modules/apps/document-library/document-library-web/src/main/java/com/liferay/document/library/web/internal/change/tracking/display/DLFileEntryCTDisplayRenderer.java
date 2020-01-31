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

package com.liferay.document.library.web.internal.change.tracking.display;

import com.liferay.change.tracking.display.CTDisplayRenderer;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DLFileEntryCTDisplayRenderer
	implements CTDisplayRenderer<DLFileEntry> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, DLFileEntry dlFileEntry)
		throws Exception {

		Group group = _groupLocalService.getGroup(dlFileEntry.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, group, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0,
			0, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(dlFileEntry.getFileEntryId()));

		return portletURL.toString();
	}

	@Override
	public Class<DLFileEntry> getModelClass() {
		return DLFileEntry.class;
	}

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, DLFileEntryCTDisplayRenderer.class);

		return _language.get(
			resourceBundle,
			"model.resource.com.liferay.document.library.kernel.model." +
				"DLFileEntry");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, DLFileEntry dlFileEntry)
		throws Exception {

		_ctDisplayRenderer.render(
			httpServletRequest, httpServletResponse,
			dlFileEntry.getFileVersion());
	}

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileVersion)"
	)
	private CTDisplayRenderer<DLFileVersion> _ctDisplayRenderer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}