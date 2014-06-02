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

package com.liferay.portal.repository.liferayrepository.util;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.util.RepositoryModelUtil;

import java.util.List;

/**
 * @author     Alexander Chow
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.portlet.documentlibrary.util.RepositoryModelUtil}
 */
@Deprecated
public abstract class LiferayBase {

	/**
	 * @see com.liferay.portal.portletfilerepository.PortletFileRepositoryImpl#toFileEntries
	 */
	public List<FileEntry> toFileEntries(List<DLFileEntry> dlFileEntries) {
		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	public List<Object> toFileEntriesAndFolders(
		List<Object> dlFileEntriesAndDLFolders) {

		return RepositoryModelUtil.toFileEntriesAndFolders(
			dlFileEntriesAndDLFolders);
	}

	public List<FileVersion> toFileVersions(
		List<DLFileVersion> dlFileVersions) {

		return RepositoryModelUtil.toFileVersions(dlFileVersions);
	}

	public List<Folder> toFolders(List<DLFolder> dlFolders) {
		return RepositoryModelUtil.toFolders(dlFolders);
	}

}