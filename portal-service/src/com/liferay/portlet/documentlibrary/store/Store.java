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

import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;

import java.io.File;
import java.io.InputStream;

/**
 * The interface for all file store implementations. Most, if not all
 * implementations should extend from the class {@link BaseStore}.
 *
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @see    BaseStore
 */
public interface Store {

	public static final String VERSION_DEFAULT = "1.0";

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws DuplicateDirectoryException;

	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws DuplicateFileException;

	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws DuplicateFileException;

	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws DuplicateFileException;

	public void checkRoot(long companyId);

	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws DuplicateFileException, NoSuchFileException;

	public void deleteDirectory(
		long companyId, long repositoryId, String dirName);

	public void deleteFile(long companyId, long repositoryId, String fileName);

	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel);

	public File getFile(long companyId, long repositoryId, String fileName)
		throws NoSuchFileException;

	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException;

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws NoSuchFileException;

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException;

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws NoSuchFileException;

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException;

	public String[] getFileNames(long companyId, long repositoryId);

	public String[] getFileNames(
		long companyId, long repositoryId, String dirName);

	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws NoSuchFileException;

	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName);

	public boolean hasFile(long companyId, long repositoryId, String fileName);

	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel);

	public void move(String srcDir, String destDir);

	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws DuplicateFileException, NoSuchFileException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws DuplicateFileException, NoSuchFileException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws DuplicateFileException, NoSuchFileException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws DuplicateFileException, NoSuchFileException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws DuplicateFileException, NoSuchFileException;

	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws DuplicateFileException, NoSuchFileException;

}