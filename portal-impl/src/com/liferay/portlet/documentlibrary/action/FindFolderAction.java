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

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.struts.FindAction;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class FindFolderAction extends FindAction {

	@Override
	protected long getGroupId(long primaryKey) throws Exception {
		Folder folder = DLAppLocalServiceUtil.getFolder(primaryKey);

		return folder.getRepositoryId();
	}

	@Override
	protected String getPrimaryKeyParameterName() {
		return "folderId";
	}

	@Override
	protected String getStrutsAction(
		HttpServletRequest request, String portletId) {

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (rootPortletId.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY)) {
			return "/document_library_display/view";
		}
		else if (rootPortletId.equals(PortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return "/image_gallery_display/view";
		}

		return "/document_library/view";
	}

	@Override
	protected String[] initPortletIds() {
		return new String[] {
			PortletKeys.DOCUMENT_LIBRARY, PortletKeys.DOCUMENT_LIBRARY_DISPLAY,
			PortletKeys.MEDIA_GALLERY_DISPLAY};
	}

}