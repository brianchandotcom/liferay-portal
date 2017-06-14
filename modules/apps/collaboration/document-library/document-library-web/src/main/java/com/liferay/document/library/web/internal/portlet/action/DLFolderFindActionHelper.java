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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.struts.FindActionHelper;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.repository.model.Folder",
	service = FindActionHelper.class
)
public class DLFolderFindActionHelper extends BaseDLFindActionHelper {

	@Override
	public long getGroupId(long primaryKey) throws Exception {
		Folder folder = _dlAppLocalService.getFolder(primaryKey);

		return folder.getRepositoryId();
	}

	@Override
	public String getPrimaryKeyParameterName() {
		return "folderId";
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		if (primaryKey != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = _dlAppLocalService.getFolder(primaryKey);

			primaryKey = folder.getFolderId();
		}

		portletURL.setParameter("folderId", String.valueOf(primaryKey));
	}

	@Override
	protected void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL) {

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		if (rootPortletId.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/image_gallery_display/view");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}
	}

	@Override
	protected PortletLayoutFinder getPortletLayoutFinder() {
		return _portletPageFinder;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)",
		unbind = "-"
	)
	protected void setPortletLayoutFinder(
		PortletLayoutFinder portletPageFinder) {

		_portletPageFinder = portletPageFinder;
	}

	private DLAppLocalService _dlAppLocalService;
	private PortletLayoutFinder _portletPageFinder;

}