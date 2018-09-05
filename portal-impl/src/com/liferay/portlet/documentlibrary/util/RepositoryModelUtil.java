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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileShortcut;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Adolfo Pérez
 * @author Alexander Chow
 */
public class RepositoryModelUtil {

	public static List<FileEntry> toFileEntries(
		List<DLFileEntry> dlFileEntries) {

		List<FileEntry> fileEntries = new ArrayList<>(dlFileEntries.size());

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			fileEntries.add(fileEntry);
		}

		if (ListUtil.isUnmodifiableList(dlFileEntries)) {
			return Collections.unmodifiableList(fileEntries);
		}

		return fileEntries;
	}

	public static List<FileShortcut> toFileShortcuts(
		List<DLFileShortcut> dlFileShortcuts) {

		List<FileShortcut> fileShortcuts = new ArrayList<>(
			dlFileShortcuts.size());

		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			FileShortcut fileShortcut = new LiferayFileShortcut(dlFileShortcut);

			fileShortcuts.add(fileShortcut);
		}

		if (ListUtil.isUnmodifiableList(dlFileShortcuts)) {
			return Collections.unmodifiableList(fileShortcuts);
		}

		return fileShortcuts;
	}

	public static List<FileVersion> toFileVersions(
		List<DLFileVersion> dlFileVersions) {

		List<FileVersion> fileVersions = new ArrayList<>(dlFileVersions.size());

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			FileVersion fileVersion = new LiferayFileVersion(dlFileVersion);

			fileVersions.add(fileVersion);
		}

		if (ListUtil.isUnmodifiableList(dlFileVersions)) {
			return Collections.unmodifiableList(fileVersions);
		}

		return fileVersions;
	}

	public static List<Folder> toFolders(List<DLFolder> dlFolders) {
		List<Folder> folders = new ArrayList<>(dlFolders.size());

		for (DLFolder dlFolder : dlFolders) {
			Folder folder = new LiferayFolder(dlFolder);

			folders.add(folder);
		}

		if (ListUtil.isUnmodifiableList(dlFolders)) {
			return Collections.unmodifiableList(folders);
		}

		return folders;
	}

	public static List<RepositoryEntry> toRepositoryEntries(
		List<Object> dlFoldersAndDLFileEntriesAndDLFileShortcuts) {

		List<RepositoryEntry> repositoryEntries = new ArrayList<>(
			dlFoldersAndDLFileEntriesAndDLFileShortcuts.size());

		for (Object object : dlFoldersAndDLFileEntriesAndDLFileShortcuts) {
			if (object instanceof DLFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)object;

				FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

				repositoryEntries.add(fileEntry);
			}
			else if (object instanceof DLFolder) {
				DLFolder dlFolder = (DLFolder)object;

				Folder folder = new LiferayFolder(dlFolder);

				repositoryEntries.add(folder);
			}
			else if (object instanceof DLFileShortcut) {
				DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

				FileShortcut fileShortcut = new LiferayFileShortcut(
					dlFileShortcut);

				repositoryEntries.add(fileShortcut);
			}
			else {
				throw new IllegalArgumentException(
					String.format(
						"Expected an instance of one of: %s; got %s",
						Arrays.asList(
							DLFileEntry.class.getName(),
							DLFolder.class.getName(),
							DLFileShortcutConstants.getClassName()),
						object));
			}
		}

		if (ListUtil.isUnmodifiableList(
				dlFoldersAndDLFileEntriesAndDLFileShortcuts)) {

			return Collections.unmodifiableList(repositoryEntries);
		}

		return repositoryEntries;
	}

}