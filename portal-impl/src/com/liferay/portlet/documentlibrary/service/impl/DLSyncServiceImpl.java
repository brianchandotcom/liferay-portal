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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.ldiff.ByteChannelReader;
import com.liferay.portal.kernel.ldiff.ByteChannelWriter;
import com.liferay.portal.kernel.ldiff.LDiff;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.service.base.DLSyncServiceBaseImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author Michael Young
 */
public class DLSyncServiceImpl extends DLSyncServiceBaseImpl {

	public InputStream getFileDeltaAsStream(
			long userId, long fileEntryId, String srcVersion,
			String destVersion)
		throws PortalException, SystemException {

		InputStream deltaInputStream = null;
		InputStream srcInputStream = null;
		FileInputStream srcFileInputStream = null;
		OutputStream checksumsOutputStream = null;
		ByteChannelWriter checksumsWriter = null;

		FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

		File checksumsFile = FileUtil.createTempFile();

		try {
			File srcTempFile = FileUtil.createTempFile();

			FileUtil.write(srcTempFile, srcInputStream);

			srcFileInputStream = new FileInputStream(
				srcTempFile);

			checksumsOutputStream = new FileOutputStream(
				checksumsFile);

			checksumsWriter =
				new ByteChannelWriter(
					Channels.newChannel(checksumsOutputStream));

			LDiff.checksums(
				srcFileInputStream.getChannel(), checksumsWriter);

			checksumsWriter.finish();
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
		finally {
			StreamUtil.cleanUp(srcInputStream);
			StreamUtil.cleanUp(srcFileInputStream);
			StreamUtil.cleanUp(checksumsOutputStream);
		}

		InputStream destInputStream = null;
		InputStream checksumsInputStream = null;
		ByteChannelReader checksumsReader = null;
		OutputStream deltaOutputStream = null;
		ByteChannelWriter deltaWriter = null;

		try {
			destInputStream = fileEntry.getContentStream(destVersion);

			checksumsInputStream = new FileInputStream(checksumsFile);

			checksumsReader = new ByteChannelReader(
				Channels.newChannel(checksumsInputStream));

			File deltaFile = FileUtil.createTempFile();

			deltaOutputStream = new FileOutputStream(deltaFile);

			deltaWriter = new ByteChannelWriter(
				Channels.newChannel(deltaOutputStream));

			LDiff.delta(
				Channels.newChannel(destInputStream), checksumsReader,
				deltaWriter);

			deltaWriter.finish();

			deltaInputStream = new FileInputStream(deltaFile);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
		finally {
			StreamUtil.cleanUp(destInputStream);
			StreamUtil.cleanUp(checksumsInputStream);
			StreamUtil.cleanUp(deltaOutputStream);
		}

		return deltaInputStream;
	}

	public FileEntry updateFileEntryDelta(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream deltaInputStream, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

		InputStream patchedInputStream = null;

		File patchedFile = null;

		try {
			InputStream originalInputStream = fileEntry.getContentStream();

			patchedFile = FileUtil.createTempFile();

			patchFile(originalInputStream, deltaInputStream, patchedFile);

			patchedInputStream = new FileInputStream(patchedFile);

			return dlAppLocalService.updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, patchedInputStream, size,
				serviceContext);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
		finally {
			FileUtil.delete(patchedFile);
		}
	}

	protected void patchFile(
			InputStream originalInputStream, InputStream deltaInputStream,
			File patchedFile)
		throws PortalException {

		FileOutputStream patchedFileOutputStream = null;
		File originalTempFile = null;
		FileInputStream originalFileInputStream = null;
		ReadableByteChannel deltaChannel = null;

		try {
			patchedFileOutputStream =  new FileOutputStream(patchedFile);

			originalTempFile = FileUtil.createTempFile();

			FileUtil.write(originalTempFile, originalInputStream);

			originalFileInputStream = new FileInputStream(originalTempFile);

			deltaChannel = Channels.newChannel(deltaInputStream);

			ByteChannelReader deltaReader = new ByteChannelReader(deltaChannel);

			LDiff.patch(
				originalFileInputStream.getChannel(),
				Channels.newChannel(patchedFileOutputStream),
				deltaReader);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
		finally {
			StreamUtil.cleanUp(patchedFileOutputStream);
			StreamUtil.cleanUp(originalFileInputStream);
			StreamUtil.cleanUp(deltaChannel);

			FileUtil.delete(originalTempFile);
		}
	}

}