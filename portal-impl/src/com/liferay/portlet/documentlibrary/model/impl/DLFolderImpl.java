/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFolderImpl extends DLFolderBaseImpl {

	public DLFolderImpl() {
	}

	public List<DLFolder> getAncestors()
		throws PortalException, SystemException {

		List<DLFolder> ancestors = new ArrayList<DLFolder>();

		DLFolder folder = this;

		while (!folder.isRoot()) {
			folder = folder.getParentFolder();

			ancestors.add(folder);
		}

		return ancestors;
	}

	public DLFolder getParentFolder() throws PortalException, SystemException {
		if (getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return null;
		}

		return DLFolderLocalServiceUtil.getFolder(getParentFolderId());
	}

	public String getPath() throws PortalException, SystemException {
		StringBuilder sb = new StringBuilder();

		DLFolder folder = this;

		while (folder != null) {
			sb.insert(0, folder.getName());
			sb.insert(0, StringPool.SLASH);

			folder = folder.getParentFolder();
		}

		return sb.toString();
	}

	public String[] getPathArray() throws PortalException, SystemException {
		String path = getPath();

		// Remove leading /

		path = path.substring(1);

		return StringUtil.split(path, CharPool.SLASH);
	}

	public boolean hasInheritableLock() {
		try {
			return DLFolderServiceUtil.hasInheritableLock(getFolderId());
		}
		catch (Exception e) {
		}

		return false;
	}

	public boolean hasLock() {
		try {
			return DLFolderServiceUtil.hasFolderLock(getFolderId());
		}
		catch (Exception e) {
		}

		return false;
	}

	public boolean isLocked() {
		try {
			return DLFolderServiceUtil.isFolderLocked(getFolderId());
		}
		catch (Exception e) {
		}

		return false;
	}

	public boolean isRoot() {
		if (getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return true;
		}
		else {
			return false;
		}
	}

}