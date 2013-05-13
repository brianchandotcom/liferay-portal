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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Fernández
 */
public class JournalFolderImpl extends JournalFolderBaseImpl {

	public JournalFolderImpl() {
	}

	@Override
	public List<JournalFolder> getAncestors()
		throws PortalException, SystemException {

		List<JournalFolder> ancestors = new ArrayList<JournalFolder>();

		JournalFolder folder = this;

		while (!folder.isRoot()) {
			folder = folder.getParentFolder();

			ancestors.add(folder);
		}

		return ancestors;
	}

	@Override
	public JournalFolder getParentFolder()
		throws PortalException, SystemException {

		if (getParentFolderId() ==
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return null;
		}

		return JournalFolderLocalServiceUtil.getFolder(getParentFolderId());
	}

	@Override
	public JournalFolder getTrashContainer() {
		JournalFolder folder = null;

		try {
			folder = getParentFolder();
		}
		catch (Exception e) {
			return null;
		}

		while (folder != null) {
			if (folder.isInTrash()) {
				return folder;
			}

			try {
				folder = folder.getParentFolder();
			}
			catch (Exception e) {
				return null;
			}
		}

		return null;
	}

	@Override
	public boolean isInTrashContainer() {
		if (getTrashContainer() != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isRoot() {
		if (getParentFolderId() ==
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return true;
		}

		return false;
	}

}