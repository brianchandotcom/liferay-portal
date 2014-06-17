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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.service.DLContentLocalServiceUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.nio.channels.FileChannel;

import java.sql.Blob;
import java.sql.SQLException;

import java.util.List;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class DBStore extends BaseStore {

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException {

		updateFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT, bytes);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException {

		updateFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT, file);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			InputStream inputStream)
		throws PortalException {

		updateFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT,
			inputStream);
	}

	@Override
	public void checkRoot(long companyId) {
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		DLContentLocalServiceUtil.deleteContentsByDirectory(
			companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		DLContentLocalServiceUtil.deleteContents(
			companyId, repositoryId, fileName);
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		DLContentLocalServiceUtil.deleteContent(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		DLContent dlContent = DLContentLocalServiceUtil.getContent(
			companyId, repositoryId, fileName);

		dlContent.resetOriginalValues();

		Blob blobData = dlContent.getData();

		if (blobData == null) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(9);

				sb.append("No blob data found for file {companyId=");
				sb.append(companyId);
				sb.append(", repositoryId=");
				sb.append(repositoryId);
				sb.append(", fileName=");
				sb.append(fileName);
				sb.append("}");

				_log.warn(sb.toString());
			}

			return null;
		}

		try {
			return blobData.getBinaryStream();
		}
		catch (SQLException sqle) {
			StringBundler sb = new StringBundler(7);

			sb.append("Unable to load data binary stream for file {companyId=");
			sb.append(companyId);
			sb.append(", repositoryId=");
			sb.append(repositoryId);
			sb.append(", fileName=");
			sb.append(fileName);
			sb.append("}");

			throw new SystemException(sb.toString(), sqle);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		DLContent dlContent = DLContentLocalServiceUtil.getContent(
			companyId, repositoryId, fileName, versionLabel);

		Blob blobData = dlContent.getData();

		if (blobData == null) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(9);

				sb.append("No blob data found for file {companyId=");
				sb.append(companyId);
				sb.append(", repositoryId=");
				sb.append(repositoryId);
				sb.append(", fileName=");
				sb.append(fileName);
				sb.append(", versionLabel=");
				sb.append(versionLabel);
				sb.append("}");

				_log.warn(sb.toString());
			}

			return null;
		}

		try {
			return blobData.getBinaryStream();
		}
		catch (SQLException sqle) {
			StringBundler sb = new StringBundler(9);

			sb.append("Unable to load data binary stream for file {companyId=");
			sb.append(companyId);
			sb.append(", repositoryId=");
			sb.append(repositoryId);
			sb.append(", fileName=");
			sb.append(fileName);
			sb.append(", versionLabel=");
			sb.append(versionLabel);
			sb.append("}");

			throw new SystemException(sb.toString(), sqle);
		}
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		List<DLContent> dlContents = DLContentLocalServiceUtil.getContents(
			companyId, repositoryId);

		String[] fileNames = new String[dlContents.size()];

		for (int i = 0; i < dlContents.size(); i++) {
			DLContent dlContent = dlContents.get(i);

			fileNames[i] = dlContent.getPath();
		}

		return fileNames;
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		List<DLContent> dlContents =
			DLContentLocalServiceUtil.getContentsByDirectory(
				companyId, repositoryId, dirName);

		String[] fileNames = new String[dlContents.size()];

		for (int i = 0; i < dlContents.size(); i++) {
			DLContent dlContent = dlContents.get(i);

			fileNames[i] = dlContent.getPath();
		}

		return fileNames;
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		DLContent dlContent = DLContentLocalServiceUtil.getContent(
			companyId, repositoryId, fileName);

		return dlContent.getSize();
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		return true;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return DLContentLocalServiceUtil.hasContent(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public void move(String srcDir, String destDir) {
	}

	@Override
	public void updateFile(
		long companyId, long repositoryId, long newRepositoryId,
		String fileName) {

		DLContentLocalServiceUtil.updateDLContent(
			companyId, repositoryId, newRepositoryId, fileName, fileName);
	}

	@Override
	public void updateFile(
		long companyId, long repositoryId, String fileName,
		String newFileName) {

		DLContentLocalServiceUtil.updateDLContent(
			companyId, repositoryId, repositoryId, fileName, newFileName);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException {

		if (DLContentLocalServiceUtil.hasContent(
				companyId, repositoryId, fileName, versionLabel)) {

			throw new DuplicateFileException(fileName);
		}

		DLContentLocalServiceUtil.addContent(
			companyId, repositoryId, fileName, versionLabel, bytes);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException {

		if (DLContentLocalServiceUtil.hasContent(
				companyId, repositoryId, fileName, versionLabel)) {

			throw new DuplicateFileException(fileName);
		}

		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException(fnfe);
		}

		DLContentLocalServiceUtil.addContent(
			companyId, repositoryId, fileName, versionLabel, inputStream,
			file.length());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream inputStream)
		throws PortalException {

		if (DLContentLocalServiceUtil.hasContent(
				companyId, repositoryId, fileName, versionLabel)) {

			throw new DuplicateFileException(fileName);
		}

		long length = -1;

		if (inputStream instanceof ByteArrayInputStream) {
			ByteArrayInputStream byteArrayInputStream =
				(ByteArrayInputStream)inputStream;

			length = byteArrayInputStream.available();
		}
		else if (inputStream instanceof FileInputStream) {
			FileInputStream fileInputStream = (FileInputStream)inputStream;

			FileChannel fileChannel = fileInputStream.getChannel();

			try {
				length = fileChannel.size();
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to detect file size from file channel", ioe);
				}
			}
		}
		else if (inputStream instanceof UnsyncByteArrayInputStream) {
			UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				(UnsyncByteArrayInputStream)inputStream;

			length = unsyncByteArrayInputStream.available();
		}

		if (length >= 0) {
			DLContentLocalServiceUtil.addContent(
				companyId, repositoryId, fileName, versionLabel, inputStream,
				length);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to detect length from input stream. Reading " +
						"entire input stream into memory as a last resort.");
			}

			byte[] bytes = null;

			try {
				bytes = FileUtil.getBytes(inputStream);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}

			DLContentLocalServiceUtil.addContent(
				companyId, repositoryId, fileName, versionLabel, bytes);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DBStore.class);

}