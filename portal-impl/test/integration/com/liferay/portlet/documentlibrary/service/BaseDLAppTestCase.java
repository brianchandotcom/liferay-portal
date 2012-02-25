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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

/**
 * @author Alexander Chow
 */
public class BaseDLAppTestCase extends BaseServiceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		String name = "Test Folder";
		String description = "This is a test folder.";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		try {
			DLAppServiceUtil.deleteFolder(
				TestPropsValues.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);
		}
		catch (NoSuchFolderException nsfe) {
		}

		folder = DLAppServiceUtil.addFolder(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, description,
			serviceContext);
	}

	@Override
	public void tearDown() throws Exception {
		if (folder != null) {
			DLAppServiceUtil.deleteFolder(folder.getFolderId());
		}

		super.tearDown();
	}

	protected FileEntry addFileEntry(boolean rootFolder, String fileName)
		throws Exception {

		return addFileEntry(rootFolder, fileName, fileName);
	}

	protected FileEntry addFileEntry(
			boolean rootFolder, String sourceFileName, String title)
		throws Exception {

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (!rootFolder) {
			folderId = folder.getFolderId();
		}

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		byte[] bytes = null;

		if (Validator.isNotNull(sourceFileName)) {
			bytes = CONTENT.getBytes();
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DLAppServiceUtil.addFileEntry(
			TestPropsValues.getGroupId(), folderId, sourceFileName,
			ContentTypes.TEXT_PLAIN, title, description, changeLog, bytes,
			serviceContext);
	}

	protected FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title)
		throws Exception {

		return updateFileEntry(fileEntryId, sourceFileName, title, false);
	}

	protected FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title,
			boolean majorVersion)
		throws Exception {

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		byte[] bytes = null;

		if (Validator.isNotNull(sourceFileName)) {
			bytes = CONTENT.getBytes();
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DLAppServiceUtil.updateFileEntry(
			fileEntryId, sourceFileName, ContentTypes.TEXT_PLAIN, title,
			description, changeLog, majorVersion, bytes, serviceContext);
	}

	protected static final String CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	protected Folder folder;

}