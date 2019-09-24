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

package com.liferay.document.library.test.util.search;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class FileEntrySearchFixture {

	public FileEntrySearchFixture(DLAppLocalService dlAppLocalService1) {
		dlAppLocalService = dlAppLocalService1;
	}

	public FileEntry addFileEntry(FileEntryBlueprint fileEntryBlueprint) {
		ServiceContext serviceContext = getServiceContext(fileEntryBlueprint);

		serviceContext.setAssetTagNames(fileEntryBlueprint.getAssetTagNames());
		serviceContext.setAttributes(fileEntryBlueprint.getAttributes());

		FileEntry fileEntry = null;

		if (fileEntryBlueprint.getInputStream() != null) {
			fileEntry = addFileEntry(
				fileEntryBlueprint.getInputStream(), fileEntryBlueprint,
				serviceContext);
		}
		else {
			fileEntry = addFileEntryWithWorkflow(
				fileEntryBlueprint.getUserId(), fileEntryBlueprint.getGroupId(),
				fileEntryBlueprint.getTitle(), serviceContext);
		}

		_fileEntries.add(fileEntry);

		return fileEntry;
	}

	public List<FileEntry> getFileEntries() {
		return _fileEntries;
	}

	public void setUp() {
	}

	public void tearDown() throws Exception {
		for (FileEntry fileEntry : _fileEntries) {
			dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());
		}

		_fileEntries.clear();
	}

	protected FileEntry addFileEntry(
		InputStream inputStream, FileEntryBlueprint fileEntryBlueprint,
		ServiceContext serviceContext) {

		File file = createTempFile(inputStream);

		try {
			return addFileEntry(
				fileEntryBlueprint.getFileName(), fileEntryBlueprint.getTitle(),
				file, serviceContext);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	protected FileEntry addFileEntry(
		String fileName, String title, File file,
		ServiceContext serviceContext) {

		try {
			return dlAppLocalService.addFileEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
				MimeTypesUtil.getContentType(fileName), title, StringPool.BLANK,
				StringPool.BLANK, file, serviceContext);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	protected FileEntry addFileEntryWithWorkflow(
		long userId, long groupId, String title,
		ServiceContext serviceContext) {

		try {
			return DLAppTestUtil.addFileEntryWithWorkflow(
				userId, groupId, 0, StringPool.BLANK, title, true,
				serviceContext);
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected File createTempFile(InputStream inputStream) {
		try {
			return FileUtil.createTempFile(inputStream);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected ServiceContext getServiceContext(
		FileEntryBlueprint fileEntryBlueprint) {

		try {
			if (fileEntryBlueprint.getUserId() != null) {
				return ServiceContextTestUtil.getServiceContext(
					fileEntryBlueprint.getGroupId(),
					fileEntryBlueprint.getUserId());
			}

			return ServiceContextTestUtil.getServiceContext(
				fileEntryBlueprint.getGroupId());
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	protected final DLAppLocalService dlAppLocalService;

	private final List<FileEntry> _fileEntries = new ArrayList<>();

}