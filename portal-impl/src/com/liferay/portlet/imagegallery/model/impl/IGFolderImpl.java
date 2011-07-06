/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class IGFolderImpl extends IGFolderBaseImpl {

	public IGFolderImpl() {
	}

	public List<IGFolder> getAncestors()
		throws PortalException, SystemException {

		List<IGFolder> ancestors = new ArrayList<IGFolder>();

		IGFolder folder = this;

		while (true) {
			if (!folder.isRoot()) {
				folder = folder.getParentFolder();

				ancestors.add(folder);
			}
			else {
				break;
			}
		}

		return ancestors;
	}

	public IGFolder getParentFolder()
		throws PortalException, SystemException {

		if (getParentFolderId() == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return null;
		}

		return IGFolderLocalServiceUtil.getFolder(getParentFolderId());
	}

	public boolean isRoot() {
		if (getParentFolderId() == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return true;
		}
		else {
			return false;
		}
	}

}