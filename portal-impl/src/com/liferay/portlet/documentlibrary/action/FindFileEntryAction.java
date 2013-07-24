/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 * @author Ryan Park
 */
public class FindFileEntryAction extends FindFolderAction {

	@Override
	protected long getGroupId(long primaryKey) throws Exception {
		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(primaryKey);

		return fileEntry.getRepositoryId();
	}

	@Override
	protected String getPrimaryKeyParameterName() {
		return "fileEntryId";
	}

	@Override
	protected String getStrutsAction(
		HttpServletRequest request, String portletId) {

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (rootPortletId.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY)) {
			return "/document_library_display/view_file_entry";
		}
		else if (rootPortletId.equals(PortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return "/image_gallery_display/view_image";
		}

		return "/document_library/view_file_entry";
	}

}