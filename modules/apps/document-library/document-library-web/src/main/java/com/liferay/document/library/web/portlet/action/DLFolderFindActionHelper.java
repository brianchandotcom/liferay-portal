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

package com.liferay.document.library.web.portlet.action;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.struts.BaseFindActionHelper;
import com.liferay.portal.struts.BasePortletPageFinder;
import com.liferay.portal.struts.FindActionHelper;
import com.liferay.portal.struts.PortletPageFinder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.repository.model.Folder",
	service = FindActionHelper.class
)
public class DLFolderFindActionHelper extends BaseFindActionHelper {

	@Override
	public long getGroupId(long primaryKey) throws Exception {
		Folder folder = DLAppLocalServiceUtil.getFolder(primaryKey);

		return folder.getRepositoryId();
	}

	@Override
	public String getPrimaryKeyParameterName() {
		return "folderId";
	}

	@Override
	public PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		Folder folder = DLAppLocalServiceUtil.getFolder(primaryKey);

		portletURL.setParameter(
			"folderId", String.valueOf(folder.getFolderId()));
	}

	@Override
	protected void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL) {

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

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
	protected PortletPageFinder getPortletPageFinder() {
		return new DLFolderPortletPageFinder();
	}

	private static final String[] _PORTLET_IDS = {
		DLPortletKeys.DOCUMENT_LIBRARY, DLPortletKeys.MEDIA_GALLERY_DISPLAY
	};

	private static class DLFolderPortletPageFinder
		extends BasePortletPageFinder {

		@Override
		protected String[] getPortletIds() {
			return _PORTLET_IDS;
		}

	}

}