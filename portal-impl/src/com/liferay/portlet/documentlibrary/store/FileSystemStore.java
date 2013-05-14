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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 * @author Alexander Chow
 * @author Edward Han
 */
public class FileSystemStore extends BaseStore {

	public FileSystemStore() {
		if (!_rootDir.exists()) {
			_rootDir.mkdirs();
		}
	}

	@Override
	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (dirNameDir.exists()) {
			throw new DuplicateDirectoryException(dirNameDir.getPath());
		}

		dirNameDir.mkdirs();
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, VERSION_DEFAULT);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException(fileNameVersionFile.getPath());
			}

			FileUtil.write(fileNameVersionFile, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void checkRoot(long companyId) {
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		File fromFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, fromVersionLabel);

		File toFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, toVersionLabel);

		if (toFileNameVersionFile.exists()) {
			throw new DuplicateFileException(toFileNameVersionFile.getPath());
		}

		try {
			toFileNameVersionFile.createNewFile();

			FileUtil.copyFile(fromFileNameVersionFile, toFileNameVersionFile);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			throw new NoSuchDirectoryException(dirNameDir.getPath());
		}

		File parentFile = dirNameDir.getParentFile();

		FileUtil.deltree(dirNameDir);

		RepositoryDirKey repositoryDirKey = new RepositoryDirKey(
			companyId, repositoryId);

		_repositoryDirs.remove(repositoryDirKey);

		deleteEmptyAncestors(parentFile);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			throw new NoSuchFileException(fileNameDir.getPath());
		}

		File parentFile = fileNameDir.getParentFile();

		FileUtil.deltree(fileNameDir);

		deleteEmptyAncestors(companyId, repositoryId, parentFile);
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(fileNameVersionFile.getPath());
		}

		File parentFile = fileNameVersionFile.getParentFile();

		fileNameVersionFile.delete();

		deleteEmptyAncestors(companyId, repositoryId, parentFile);
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = getHeadVersionLabel(
				companyId, repositoryId, fileName);
		}

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(fileNameVersionFile.getPath());
		}

		return fileNameVersionFile;
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = getHeadVersionLabel(
				companyId, repositoryId, fileName);
		}

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		try {
			return new FileInputStream(fileNameVersionFile);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException(fileNameVersionFile.getPath(), fnfe);
		}
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		return FileUtil.listDirs(repositoryDir);
	}

	@Override
	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			throw new NoSuchDirectoryException(dirNameDir.getPath());
		}

		String[] fileNames = FileUtil.listDirs(dirNameDir);

		Arrays.sort(fileNames);

		// Convert /${fileName} to /${dirName}/${fileName}

		for (int i = 0; i < fileNames.length; i++) {
			fileNames[i] =
				StringPool.SLASH + dirName + StringPool.SLASH + fileNames[i];
		}

		return fileNames;
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		String versionLabel = getHeadVersionLabel(
			companyId, repositoryId, fileName);

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(fileNameVersionFile.getPath());
		}

		return fileNameVersionFile.length();
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		return dirNameDir.exists();
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (fileNameVersionFile.exists()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void move(String srcDir, String destDir) {
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);
		File newFileNameDir = getFileNameDir(
			companyId, newRepositoryId, fileName);

		if (newFileNameDir.exists()) {
			throw new DuplicateFileException(fileName);
		}

		File parentFile = fileNameDir.getParentFile();

		boolean renamed = FileUtil.move(fileNameDir, newFileNameDir);

		if (!renamed) {
			throw new SystemException(
				"File name directory was not renamed from " +
					fileNameDir.getPath() + " to " + newFileNameDir.getPath());
		}

		deleteEmptyAncestors(companyId, repositoryId, parentFile);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);
		File newFileNameDir = getFileNameDir(
			companyId, repositoryId, newFileName);

		if (newFileNameDir.exists()) {
			throw new DuplicateFileException(newFileName);
		}

		File parentFile = fileNameDir.getParentFile();

		boolean renamed = FileUtil.move(fileNameDir, newFileNameDir);

		if (!renamed) {
			throw new SystemException(
				"File name directory was not renamed from " +
					fileNameDir.getPath() + " to " + newFileNameDir.getPath());
		}

		deleteEmptyAncestors(companyId, repositoryId, parentFile);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException, SystemException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, versionLabel);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException(fileNameVersionFile.getPath());
			}

			FileUtil.write(fileNameVersionFile, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		File fromFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, fromVersionLabel);

		File toFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, toVersionLabel);

		if (toFileNameVersionFile.exists()) {
			throw new DuplicateFileException(toFileNameVersionFile.getPath());
		}

		boolean renamed = FileUtil.move(
			fromFileNameVersionFile, toFileNameVersionFile);

		if (!renamed) {
			throw new SystemException(
				"File name version file was not renamed from " +
					fromFileNameVersionFile.getPath() + " to " +
						toFileNameVersionFile.getPath());
		}
	}

	protected void deleteEmptyAncestors(File file) {
		deleteEmptyAncestors(-1, -1, file);
	}

	protected void deleteEmptyAncestors(
		long companyId, long repositoryId, File file) {

		String[] fileNames = file.list();

		if ((fileNames == null) || (fileNames.length > 0)) {
			return;
		}

		String fileName = file.getName();

		if ((repositoryId > 0) &&
			fileName.equals(String.valueOf(repositoryId))) {

			RepositoryDirKey repositoryDirKey = new RepositoryDirKey(
				companyId, repositoryId);

			_repositoryDirs.remove(repositoryDirKey);
		}

		File parentFile = file.getParentFile();

		if (file.delete() && (parentFile != null)) {
			deleteEmptyAncestors(companyId, repositoryId, parentFile);
		}
	}

	protected File getCompanyDir(long companyId) {
		File companyDir = new File(_rootDir + StringPool.SLASH + companyId);

		if (!companyDir.exists()) {
			companyDir.mkdirs();
		}

		return companyDir;
	}

	protected File getDirNameDir(
		long companyId, long repositoryId, String dirName) {

		return getFileNameDir(companyId, repositoryId, dirName);
	}

	protected File getFileNameDir(
		long companyId, long repositoryId, String fileName) {

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		File fileNameDir = new File(
			repositoryDir + StringPool.SLASH + fileName);

		return fileNameDir;
	}

	protected File getFileNameVersionFile(
		long companyId, long repositoryId, String fileName, String version) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		File fileNameVersionFile = new File(
			fileNameDir + StringPool.SLASH + version);

		return fileNameVersionFile;
	}

	protected String getHeadVersionLabel(
		long companyId, long repositoryId, String fileName) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			return VERSION_DEFAULT;
		}

		String[] versionLabels = FileUtil.listFiles(fileNameDir);

		String headVersionLabel = VERSION_DEFAULT;

		for (String versionLabel : versionLabels) {
			if (DLUtil.compareVersions(versionLabel, headVersionLabel) > 0) {
				headVersionLabel = versionLabel;
			}
		}

		return headVersionLabel;
	}

	protected File getRepositoryDir(long companyId, long repositoryId) {
		RepositoryDirKey repositoryDirKey = new RepositoryDirKey(
			companyId, repositoryId);

		File repositoryDir = _repositoryDirs.get(repositoryDirKey);

		if (repositoryDir == null) {
			File companyDir = getCompanyDir(companyId);

			repositoryDir = new File(
				companyDir + StringPool.SLASH + repositoryId);

			if (!repositoryDir.exists()) {
				repositoryDir.mkdirs();
			}

			_repositoryDirs.put(repositoryDirKey, repositoryDir);
		}

		return repositoryDir;
	}

	private Map<RepositoryDirKey, File> _repositoryDirs =
		new ConcurrentHashMap<RepositoryDirKey, File>();
	private File _rootDir = new File(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR);

	private class RepositoryDirKey {

		public RepositoryDirKey(long companyId, long repositoryId) {
			_companyId = companyId;
			_repositoryId = repositoryId;
		}

		@Override
		public boolean equals(Object obj) {
			RepositoryDirKey repositoryDirKey = (RepositoryDirKey)obj;

			if ((_companyId == repositoryDirKey._companyId) &&
				(_repositoryId == repositoryDirKey._repositoryId)) {

				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return (int)(_companyId * 11 + _repositoryId);
		}

		private long _companyId;
		private long _repositoryId;

	}

}