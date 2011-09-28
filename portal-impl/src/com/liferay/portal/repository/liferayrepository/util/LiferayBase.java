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

package com.liferay.portal.repository.liferayrepository.util;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 */
public abstract class LiferayBase {

	public List<FileEntry> toFileEntries(List<DLFileEntry> dlFileEntries) {
		List<FileEntry> fileEntries = new ArrayList<FileEntry>(
			dlFileEntries.size());

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			FileEntry fileEntry = null;

			if (dlFileEntry != null) {
				fileEntry = new LiferayFileEntry(dlFileEntry);
			}

			fileEntries.add(fileEntry);
		}

		if (dlFileEntries instanceof UnmodifiableList) {
			return new UnmodifiableList<FileEntry>(fileEntries);
		}
		else {
			return fileEntries;
		}
	}

	public List<Object> toFileEntriesAndFolders(
		List<Object> dlFileEntriesAndDLFolders) {

		List<Object> fileEntriesAndFolders = new ArrayList<Object>(
			dlFileEntriesAndDLFolders.size());

		for (Object object : dlFileEntriesAndDLFolders) {
			if (object instanceof DLFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)object;

				FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

				fileEntriesAndFolders.add(fileEntry);
			}
			else if (object instanceof DLFolder) {
				DLFolder dlFolder = (DLFolder)object;

				Folder folder = new LiferayFolder(dlFolder);

				fileEntriesAndFolders.add(folder);
			}
			else {
				fileEntriesAndFolders.add(object);
			}
		}

		if (dlFileEntriesAndDLFolders instanceof UnmodifiableList) {
			return new UnmodifiableList<Object>(fileEntriesAndFolders);
		}
		else {
			return fileEntriesAndFolders;
		}
	}

	public List<FileVersion> toFileVersions(
		List<DLFileVersion> dlFileVersions) {

		List<FileVersion> fileVersions = new ArrayList<FileVersion>(
			dlFileVersions.size());

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			FileVersion fileVersion = new LiferayFileVersion(dlFileVersion);

			fileVersions.add(fileVersion);
		}

		if (dlFileVersions instanceof UnmodifiableList) {
			return new UnmodifiableList<FileVersion>(fileVersions);
		}
		else {
			return fileVersions;
		}
	}

	public List<Folder> toFolders(List<DLFolder> dlFolders) {
		List<Folder> folders = new ArrayList<Folder>(dlFolders.size());

		for (DLFolder dlFolder : dlFolders) {
			Folder folder = new LiferayFolder(dlFolder);

			folders.add(folder);
		}

		if (dlFolders instanceof UnmodifiableList) {
			return new UnmodifiableList<Folder>(folders);
		}
		else {
			return folders;
		}
	}

}