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

package com.liferay.portal.store.filesystem;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.convert.FileSystemStoreRootDirException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.filesystem.configuration.FileSystemConfiguration;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.store.BaseStore;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 * @author Alexander Chow
 * @author Edward Han
 * @author Manuel de la Peña
 */
@Component(
	configurationPid = "com.liferay.portal.store.filesystem.configuration.FileSystemConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = "store.type=com.liferay.portal.store.filesystem.FileSystemStore",
	service = Store.class
)
public class FileSystemStore extends BaseStore {

	public FileSystemStore() {
		_rootDir = new File(getRootDirName());

		try {
			FileUtil.mkdirs(_rootDir);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (dirNameDir.exists()) {
			return;
		}

		try {
			FileUtil.mkdirs(dirNameDir);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws DuplicateFileException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, VERSION_DEFAULT);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException(
					companyId, repositoryId, fileName);
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
		throws DuplicateFileException, NoSuchFileException {

		File fromFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (!fromFileNameVersionFile.exists()) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, fromVersionLabel);
		}

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
		long companyId, long repositoryId, String dirName) {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			logFailedDeletion(companyId, repositoryId, dirName);

			return;
		}

		File parentFile = dirNameDir.getParentFile();

		FileUtil.deltree(dirNameDir);

		RepositoryDirKey repositoryDirKey = new RepositoryDirKey(
			companyId, repositoryId);

		_repositoryDirs.remove(repositoryDirKey);

		deleteEmptyAncestors(parentFile);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			logFailedDeletion(companyId, repositoryId, fileName);

			return;
		}

		File parentFile = fileNameDir.getParentFile();

		FileUtil.deltree(fileNameDir);

		deleteEmptyAncestors(companyId, repositoryId, parentFile);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileNameVersionFile.exists()) {
			logFailedDeletion(companyId, repositoryId, fileName, versionLabel);

			return;
		}

		File parentFile = fileNameVersionFile.getParentFile();

		fileNameVersionFile.delete();

		deleteEmptyAncestors(companyId, repositoryId, parentFile);
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = getHeadVersionLabel(
				companyId, repositoryId, fileName);
		}

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel);
		}

		return fileNameVersionFile;
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

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
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, fnfe);
		}
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		List<String> fileNames = new ArrayList<>();

		String[] dirNames = FileUtil.listDirs(repositoryDir);

		for (String dirName : dirNames) {
			getFileNames(
				fileNames, dirName,
				repositoryDir.getPath() + StringPool.SLASH + dirName);
		}

		return fileNames.toArray(new String[fileNames.size()]);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		File dirNameDir = getDirNameDir(companyId, repositoryId, dirName);

		if (!dirNameDir.exists()) {
			return new String[0];
		}

		List<String> fileNames = new ArrayList<>();

		getFileNames(fileNames, dirName, dirNameDir.getPath());

		Collections.sort(fileNames);

		return fileNames.toArray(new String[fileNames.size()]);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws NoSuchFileException {

		String versionLabel = getHeadVersionLabel(
			companyId, repositoryId, fileName);

		File fileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, versionLabel);

		if (!fileNameVersionFile.exists()) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
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

		return fileNameVersionFile.exists();
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws DuplicateFileException, NoSuchFileException {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		File newFileNameDir = getFileNameDir(
			companyId, newRepositoryId, fileName);

		if (newFileNameDir.exists()) {
			throw new DuplicateFileException(
				companyId, newRepositoryId, fileName);
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
		throws DuplicateFileException, NoSuchFileException {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			throw new NoSuchFileException(companyId, repositoryId, fileName);
		}

		File newFileNameDir = getFileNameDir(
			companyId, repositoryId, newFileName);

		if (newFileNameDir.exists()) {
			throw new DuplicateFileException(
				companyId, repositoryId, newFileName);
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
		throws DuplicateFileException {

		try {
			File fileNameVersionFile = getFileNameVersionFile(
				companyId, repositoryId, fileName, versionLabel);

			if (fileNameVersionFile.exists()) {
				throw new DuplicateFileException(
					companyId, repositoryId, fileName, versionLabel);
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
		throws DuplicateFileException, NoSuchFileException {

		File fromFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (!fromFileNameVersionFile.exists()) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, fromVersionLabel);
		}

		File toFileNameVersionFile = getFileNameVersionFile(
			companyId, repositoryId, fileName, toVersionLabel);

		if (toFileNameVersionFile.exists()) {
			throw new DuplicateFileException(
				companyId, repositoryId, fileName, toVersionLabel);
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

	@Activate
	protected void activate(Map<String, Object> properties) {
		_fileSystemConfiguration = Configurable.createConfigurable(
			FileSystemConfiguration.class, properties);

		if (Validator.isBlank(_fileSystemConfiguration.rootDir())) {
			throw new IllegalArgumentException(
				"File System Root Directory Configuration is not set",
				new FileSystemStoreRootDirException());
		}

		FileSystemConfigurationValidator fileSystemConfigurationValidator =
			new FileSystemConfigurationValidator();

		fileSystemConfigurationValidator.validate(
			"com.liferay.portal.store.filesystem.configuration." +
				"FileSystemConfiguration",
			"com.liferay.portal.store.filesystem.configuration." +
				"AdvancedFileSystemConfiguration");
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

		try {
			FileUtil.mkdirs(companyDir);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
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

	protected void getFileNames(
		List<String> fileNames, String dirName, String path) {

		String[] pathDirNames = FileUtil.listDirs(path);

		if (ArrayUtil.isNotEmpty(pathDirNames)) {
			for (String pathDirName : pathDirNames) {
				String subdirName = null;

				if (Validator.isBlank(dirName)) {
					subdirName = pathDirName;
				}
				else {
					subdirName = dirName + StringPool.SLASH + pathDirName;
				}

				getFileNames(
					fileNames, subdirName,
					path + StringPool.SLASH + pathDirName);
			}
		}
		else {
			fileNames.add(dirName);
		}
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

			try {
				FileUtil.mkdirs(repositoryDir);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}

			_repositoryDirs.put(repositoryDirKey, repositoryDir);
		}

		return repositoryDir;
	}

	protected String getRootDirName() {
		return _fileSystemConfiguration.rootDir();
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		this.configurationAdmin = configurationAdmin;
	}

	protected ConfigurationAdmin configurationAdmin;

	private final Map<RepositoryDirKey, File> _repositoryDirs =
		new ConcurrentHashMap<>();
	private File _rootDir;

	private static volatile FileSystemConfiguration _fileSystemConfiguration;

	protected class FileSystemConfigurationValidator {

		public void validate(
			String fileSystemPid, String advancedFileSystemPid) {

			try {
				Configuration advancedFileSystemConfiguration =
					configurationAdmin.getConfiguration(advancedFileSystemPid);

				Configuration fileSystemConfiguration =
					configurationAdmin.getConfiguration(fileSystemPid);

				Dictionary<String, Object> advancedFileSystemDictionary =
					advancedFileSystemConfiguration.getProperties();

				Dictionary<String, Object> fileSystemDictionary =
					fileSystemConfiguration.getProperties();

				if (advancedFileSystemDictionary != null &&
					fileSystemDictionary != null) {

					String advancedFileSystemRootDir =
						(String) advancedFileSystemDictionary.get("rootdir");

					String fileSystemRootDir =
						(String) fileSystemDictionary.get("rootdir");

					if (Validator.equals(
							advancedFileSystemRootDir, fileSystemRootDir)) {

						throw new IllegalArgumentException(
							"Advanced File System Root Dir and File System" +
								" Root Dir have the same value",
							new FileSystemStoreRootDirException());
					}
				}
			}
			catch (IOException ioe) {
				throw new IllegalArgumentException(ioe);
			}
		}

	}

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