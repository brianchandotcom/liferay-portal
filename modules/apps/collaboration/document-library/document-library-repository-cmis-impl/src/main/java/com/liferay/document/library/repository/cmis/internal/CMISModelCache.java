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
		Map<Long, List<FileEntry>> fileEntriesCache = _fileEntriesCache.get();

		return fileEntriesCache.get(folderId);
	}

	public FileEntry getFileEntry(long fileEntryId) {
		Map<Long, FileEntry> fileEntryMap = _fileEntryCache.get();

		return fileEntryMap.get(fileEntryId);
	}

	public Folder getFolder(long folderId) {
		Map<Long, Folder> folderMap = _folderCache.get();

		return folderMap.get(folderId);
	}

	public List<Folder> getFolders(long folderId) {
		Map<Long, List<Folder>> foldersCache = _foldersCache.get();

		return foldersCache.get(folderId);
	}

	public List<Object> getFoldersAndFileEntries(long folderId) {
		Map<Long, List<Object>> foldersAndFileEntriesCache =
			_foldersAndFileEntriesCache.get();

		return foldersAndFileEntriesCache.get(folderId);
	}

	public void putFileEntries(long folderId, List<FileEntry> fileEntries) {
		Map<Long, List<FileEntry>> fileEntriesCache = _fileEntriesCache.get();

		fileEntriesCache.put(folderId, fileEntries);
	}

	public void putFileEntry(FileEntry fileEntry) {
		if (fileEntry == null) {
			return;
		}

		Map<Long, FileEntry> fileEntryMap = _fileEntryCache.get();

		fileEntryMap.put(fileEntry.getFileEntryId(), fileEntry);
	}

	public void putFolder(Folder folder) {
		if (folder == null) {
			return;
		}

		Map<Long, Folder> folderMap = _folderCache.get();

		folderMap.put(folder.getFolderId(), folder);
	}

	public void putFolders(long folderId, List<Folder> folders) {
		Map<Long, List<Folder>> foldersCache = _foldersCache.get();

		foldersCache.put(folderId, folders);
	}

	public void putFoldersAndFileEntries(
		long folderId, List<Object> foldersAndFileEntries) {

		Map<Long, List<Object>> foldersAndFileEntriesCache =
			_foldersAndFileEntriesCache.get();

		foldersAndFileEntriesCache.put(folderId, foldersAndFileEntries);
	}

	private final ThreadLocal<Map<Long, List<FileEntry>>> _fileEntriesCache =
		new AutoResetThreadLocal<Map<Long, List<FileEntry>>>(
			CMISRepository.class + "._fileEntriesCache",
			new HashMap<Long, List<FileEntry>>());
	private final ThreadLocal<Map<Long, FileEntry>> _fileEntryCache =
		new AutoResetThreadLocal<Map<Long, FileEntry>>(
			CMISRepository.class + "._fileEntryCache",
			new HashMap<Long, FileEntry>());
	private final ThreadLocal<Map<Long, Folder>> _folderCache =
		new AutoResetThreadLocal<Map<Long, Folder>>(
			CMISRepository.class + "._folderCache",
			new HashMap<Long, Folder>());
	private final ThreadLocal<Map<Long, List<Object>>>
		_foldersAndFileEntriesCache =
			new AutoResetThreadLocal<Map<Long, List<Object>>>(
				CMISRepository.class + "._foldersAndFileEntriesCache",
				new HashMap<Long, List<Object>>());
	private final ThreadLocal<Map<Long, List<Folder>>> _foldersCache =
		new AutoResetThreadLocal<Map<Long, List<Folder>>>(
			CMISRepository.class + "._foldersCache",
			new HashMap<Long, List<Folder>>());

}