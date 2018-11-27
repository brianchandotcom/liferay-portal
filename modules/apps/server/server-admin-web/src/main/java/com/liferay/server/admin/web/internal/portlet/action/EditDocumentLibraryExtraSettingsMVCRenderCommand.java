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

package com.liferay.server.admin.web.internal.portlet.action;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.server.admin.web.internal.constants.ServerAdminWebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Philip Jones
 */
@Component(
	property = {
		"javax.portlet.name=" + PortletKeys.SERVER_ADMIN,
		"mvc.command.name=/server_admin/edit_document_library_extra_settings"
	},
	service = MVCRenderCommand.class
)
public class EditDocumentLibraryExtraSettingsMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		List<DLFileEntry> dlFileEntries =
			_dlFileEntryLocalService.getExtraSettingsFileEntries(0, 1);

		if (!dlFileEntries.isEmpty()) {
			renderRequest.setAttribute(
				ServerAdminWebKeys.DL_FILE_ENTRY, dlFileEntries.get(0));
		}

		return "/edit_document_library_extra_settings.jsp";
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}