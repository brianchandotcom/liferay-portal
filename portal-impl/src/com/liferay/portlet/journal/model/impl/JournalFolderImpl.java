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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * The extended model implementation for the JournalFolder service. Represents a row in the &quot;JournalFolder&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.journal.model.JournalFolder} interface.
 * </p>
 *
 * @author Juan Fernández
 */
public class JournalFolderImpl extends JournalFolderBaseImpl {
/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a journal article folder model instance should use the {@link com.liferay.portlet.journal.model.JournalFolder} interface instead.
	 */
	public JournalFolderImpl() {
	}

	public List<JournalFolder> getAncestors()
		throws PortalException, SystemException {

		List<JournalFolder> ancestors = new ArrayList<JournalFolder>();

		JournalFolder folder = this;

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

	public JournalFolder getParentFolder()
		throws PortalException, SystemException {

		if (getParentFolderId() ==
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return null;
		}

		return JournalFolderLocalServiceUtil.getFolder(getParentFolderId());
	}

	public boolean isRoot() {
		if (getParentFolderId() ==
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return true;
		}
		else {
			return false;
		}
	}

}