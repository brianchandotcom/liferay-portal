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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.StagingLocalServiceBaseImpl;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class StagingLocalServiceImpl extends StagingLocalServiceBaseImpl {

	@Override
	public void cleanup(long stagingRequestId)
		throws PortalException, SystemException {

		try {
			PortletFileRepositoryUtil.deleteFolder(stagingRequestId);
		}
		catch (NoSuchFolderException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Staging request already cleaned up: " + stagingRequestId,
					e);
			}
		}
	}

	@Override
	public long prepare(long userId, long groupId, String checksum)
		throws PortalException, SystemException {

		ServiceContext serviceContext = new ServiceContext();

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, PortletKeys.SITES_ADMIN, serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, checksum,
			serviceContext);

		return folder.getFolderId();
	}

	@Override
	public void publish(
			long userId, long stagingRequestId, boolean privateLayout,
			Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			stagingRequestId);

		List<FileEntry> fileEntries =
			PortletFileRepositoryUtil.getPortletFileEntries(
				folder.getGroupId(), folder.getFolderId());

		File tempFile = null;

		FileOutputStream fileOutputStream = null;

		try {
			tempFile = FileUtil.createTempFile("lar");

			fileOutputStream = new FileOutputStream(tempFile);

			for (FileEntry fileEntry : fileEntries) {
				InputStream inputStream = fileEntry.getContentStream();

				try {
					StreamUtil.transfer(inputStream, fileOutputStream, false);
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}

			StreamUtil.cleanUp(fileOutputStream);

			String md5Checksum = FileUtil.getMD5Checksum(tempFile);

			if (!folder.getName().equals(md5Checksum)) {
				throw new SystemException(
					"Invalid checksum for LAR file. Please retry");
			}

			layoutLocalService.importLayouts(
				userId, folder.getGroupId(), privateLayout, parameterMap,
				tempFile);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to concatenate lar file", ioe);
		}
		finally {
			StreamUtil.cleanUp(fileOutputStream);

			FileUtil.delete(tempFile);
		}
	}

	@Override
	public void stage(
			long userId, long stagingRequestId, String fileName,
			byte[] byteBuffer)
		throws PortalException, SystemException {

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			stagingRequestId);

		long fileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				folder.getGroupId(), folder.getFolderId());

		PortletFileRepositoryUtil.addPortletFileEntry(
			folder.getGroupId(), userId, Group.class.getName(),
			folder.getGroupId(), PortletKeys.SITES_ADMIN, folder.getFolderId(),
			new UnsyncByteArrayInputStream(byteBuffer),
			fileName + fileEntriesCount, ContentTypes.APPLICATION_ZIP);
	}

	private static Log _log = LogFactoryUtil.getLog(
		StagingLocalServiceImpl.class);

}