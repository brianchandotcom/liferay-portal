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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class CMISModelCache {

	public List<FileEntry> getFileEntries(long folderId) {
		Map<Long, List<FileEntry>> fileEntriesMap = _fileEntriesMaps.get();

		return fileEntriesMap.get(folderId);
	}

	public FileEntry getFileEntry(long fileEntryId) {
		Map<Long, FileEntry> fileEntryMap = _fileEntryMaps.get();

		return fileEntryMap.get(fileEntryId);
	}

	public Folder getFolder(long folderId) {
		Map<Long, Folder> folderMap = _folderMaps.get();

		return folderMap.get(folderId);
	}

	public List<Folder> getFolders(long folderId) {
		Map<Long, List<Folder>> foldersMap = _foldersMap.get();

		return foldersMap.get(folderId);
	}

	public List<Object> getFoldersAndFileEntries(long folderId) {
		Map<Long, List<Object>> foldersAndFileEntriesMap =
			_foldersAndFileEntriesMaps.get();

		return foldersAndFileEntriesMap.get(folderId);
	}

	public void putFileEntries(long folderId, List<FileEntry> fileEntries) {
		Map<Long, List<FileEntry>> fileEntriesMap = _fileEntriesMaps.get();

		fileEntriesMap.put(folderId, fileEntries);
	}

	public void putFileEntry(FileEntry fileEntry) {
		if (fileEntry == null) {
			return;
		}

		Map<Long, FileEntry> fileEntryMap = _fileEntryMaps.get();

		fileEntryMap.put(fileEntry.getFileEntryId(), fileEntry);
	}

	public void putFolder(Folder folder) {
		if (folder == null) {
			return;
		}

		Map<Long, Folder> folderMap = _folderMaps.get();

		folderMap.put(folder.getFolderId(), folder);
	}

	public void putFolders(long folderId, List<Folder> folders) {
		Map<Long, List<Folder>> foldersMap = _foldersMap.get();

		foldersMap.put(folderId, folders);
	}

	public void putFoldersAndFileEntries(
		long folderId, List<Object> foldersAndFileEntries) {

		Map<Long, List<Object>> foldersAndFileEntriesMap =
			_foldersAndFileEntriesMaps.get();

		foldersAndFileEntriesMap.put(folderId, foldersAndFileEntries);
	}

	private final ThreadLocal<Map<Long, List<FileEntry>>> _fileEntriesMaps =
		new AutoResetThreadLocal<Map<Long, List<FileEntry>>>(
			CMISRepository.class + "._fileEntriesMaps",
			new HashMap<Long, List<FileEntry>>());
	private final ThreadLocal<Map<Long, FileEntry>> _fileEntryMaps =
		new AutoResetThreadLocal<Map<Long, FileEntry>>(
			CMISRepository.class + "._fileEntryMaps",
			new HashMap<Long, FileEntry>());
	private final ThreadLocal<Map<Long, Folder>> _folderMaps =
		new AutoResetThreadLocal<Map<Long, Folder>>(
			CMISRepository.class + "._folderMaps", new HashMap<Long, Folder>());
	private final ThreadLocal<Map<Long, List<Object>>>
		_foldersAndFileEntriesMaps =
			new AutoResetThreadLocal<Map<Long, List<Object>>>(
				CMISRepository.class + "._foldersAndFileEntriesMaps",
				new HashMap<Long, List<Object>>());
	private final ThreadLocal<Map<Long, List<Folder>>> _foldersMap =
		new AutoResetThreadLocal<Map<Long, List<Folder>>>(
			CMISRepository.class + "._foldersMap",
			new HashMap<Long, List<Folder>>());

}