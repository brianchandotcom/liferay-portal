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
import com.liferay.portal.kernel.lar.MissingReferences;
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
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Mate Thurzo
 */
public class StagingLocalServiceImpl extends StagingLocalServiceBaseImpl {

	@Override
	public void cleanUpStagingRequest(long stagingRequestId)
		throws PortalException, SystemException {

		try {
			PortletFileRepositoryUtil.deleteFolder(stagingRequestId);
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to clean up staging request " + stagingRequestId,
					nsfe);
			}
		}
	}

	@Override
	public long createStagingRequest(long userId, long groupId, String checksum)
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
	public void publishStagingRequest(
			long userId, long stagingRequestId, boolean privateLayout,
			Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			stagingRequestId);

		FileEntry stagingRequestFileEntry = getStagingRequestFileEntry(
			userId, stagingRequestId, folder);

		layoutLocalService.importLayouts(
			userId, folder.getGroupId(), privateLayout, parameterMap,
			stagingRequestFileEntry.getContentStream());
	}

	@Override
	public void updateStagingRequest(
			long userId, long stagingRequestId, String fileName, byte[] bytes)
		throws PortalException, SystemException {

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			stagingRequestId);

		fileName += PortletFileRepositoryUtil.getPortletFileEntriesCount(
			folder.getGroupId(), folder.getFolderId());

		PortletFileRepositoryUtil.addPortletFileEntry(
			folder.getGroupId(), userId, Group.class.getName(),
			folder.getGroupId(), PortletKeys.SITES_ADMIN, folder.getFolderId(),
			new UnsyncByteArrayInputStream(bytes), fileName,
			ContentTypes.APPLICATION_ZIP, false);
	}

	@Override
	public MissingReferences validateStagingRequest(
			long userId, long stagingRequestId, boolean privateLayout,
			Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			stagingRequestId);

		FileEntry fileEntry = getStagingRequestFileEntry(
			userId, stagingRequestId, folder);

		return layoutLocalService.validateImportLayoutsFile(
			userId, folder.getGroupId(), privateLayout, parameterMap,
			fileEntry.getContentStream());
	}

	protected FileEntry fetchStagingRequestFileEntry(
			long stagingRequestId, Folder folder)
		throws PortalException, SystemException {

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				folder.getGroupId(), folder.getFolderId(),
				getAssembledFileEntryName(stagingRequestId));
		}
		catch (NoSuchFileException nsfe) {
			return null;
		}
	}

	protected String getAssembledFileEntryName(long stagingRequestId) {
		return _ASSEMBLED_LAR_PREFIX + String.valueOf(stagingRequestId) +
			".lar";
	}

	protected FileEntry getStagingRequestFileEntry(
			long userId, long stagingRequestId, Folder folder)
		throws PortalException, SystemException {

		FileEntry stagingRequestFileEntry = fetchStagingRequestFileEntry(
			stagingRequestId, folder);

		if (stagingRequestFileEntry != null) {
			return stagingRequestFileEntry;
		}

		FileOutputStream fileOutputStream = null;

		File tempFile = null;

		try {
			tempFile = FileUtil.createTempFile("lar");

			fileOutputStream = new FileOutputStream(tempFile);

			List<FileEntry> fileEntries =
				PortletFileRepositoryUtil.getPortletFileEntries(
					folder.getGroupId(), folder.getFolderId());

			for (FileEntry fileEntry : fileEntries) {
				InputStream inputStream = fileEntry.getContentStream();

				try {
					StreamUtil.transfer(inputStream, fileOutputStream, false);
				}
				finally {
					StreamUtil.cleanUp(inputStream);

					PortletFileRepositoryUtil.deletePortletFileEntry(
						fileEntry.getFileEntryId());
				}
			}

			String checksum = FileUtil.getMD5Checksum(tempFile);

			if (!checksum.equals(folder.getName())) {
				throw new SystemException("Invalid checksum for LAR file");
			}

			PortletFileRepositoryUtil.addPortletFileEntry(
				folder.getGroupId(), userId, Group.class.getName(),
				folder.getGroupId(), PortletKeys.SITES_ADMIN,
				folder.getFolderId(), tempFile,
				getAssembledFileEntryName(stagingRequestId),
				ContentTypes.APPLICATION_ZIP, false);

			stagingRequestFileEntry = fetchStagingRequestFileEntry(
				stagingRequestId, folder);

			if (stagingRequestFileEntry == null) {
				throw new SystemException("Unable to assemble LAR file");
			}

			return stagingRequestFileEntry;
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to reassemble LAR file", ioe);
		}
		finally {
			StreamUtil.cleanUp(fileOutputStream);

			FileUtil.delete(tempFile);
		}
	}

	private static final String _ASSEMBLED_LAR_PREFIX = "assembled_";

	private static Log _log = LogFactoryUtil.getLog(
		StagingLocalServiceImpl.class);

}