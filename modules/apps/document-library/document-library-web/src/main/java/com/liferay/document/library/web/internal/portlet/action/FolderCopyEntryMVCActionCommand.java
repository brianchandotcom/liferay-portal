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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Galluzzi
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/folder_copy_entry"
	},
	service = MVCActionCommand.class
)
public class FolderCopyEntryMVCActionCommand
	extends BaseCopyEntryMVCActionCommand {

	@Override
	protected void doCopyEntry(ActionRequest actionRequest)
		throws PortalException {

		long sourceRepositoryId = ParamUtil.getLong(
			actionRequest, "sourceRepositoryId");
		long sourceFolderId = ParamUtil.getLong(
			actionRequest, "sourceFolderId");
		long destinationRepositoryId = ParamUtil.getLong(
			actionRequest, "destinationRepositoryId");
		long destinationParentFolderId = ParamUtil.getLong(
			actionRequest, "destinationParentFolderId");

		_dlAppService.copyFolder(
			sourceRepositoryId, sourceFolderId, destinationRepositoryId,
			destinationParentFolderId,
			ServiceContextFactory.getInstance(
				DLFolder.class.getName(), actionRequest));
	}

	@Reference
	private DLAppService _dlAppService;

}