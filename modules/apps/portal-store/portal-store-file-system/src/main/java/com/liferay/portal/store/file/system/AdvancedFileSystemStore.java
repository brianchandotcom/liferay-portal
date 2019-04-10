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

package com.liferay.portal.store.file.system;

import com.liferay.document.library.kernel.exception.DuplicateFileException;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.convert.documentlibrary.FileSystemStoreRootDirException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.file.system.configuration.AdvancedFileSystemStoreConfiguration;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-1976.
 * </p>
 *
 * @author Jorge Ferrer
 * @author Ryan Park
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
@Component(
	configurationPid = "com.liferay.portal.store.file.system.configuration.AdvancedFileSystemStoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = {
		"service.ranking:Integer=0",
		"store.type=com.liferay.portal.store.file.system.AdvancedFileSystemStore"
	},
	service = Store.class
)
public class AdvancedFileSystemStore extends FileSystemStore {

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws DuplicateFileException, NoSuchFileException {

		super.updateFile(companyId, repositoryId, fileName, newFileName);

		File newFileNameDir = getFileNameDir(
			companyId, repositoryId, newFileName);

		String[] fileNameVersions = FileUtil.listFiles(newFileNameDir);

		for (String fileNameVersion : fileNameVersions) {
			String ext = FileUtil.getExtension(fileNameVersion);

			String newFileNameVersion = newFileName;

			if (ext.equals(_HOOK_EXTENSION)) {
				int pos = fileNameVersion.lastIndexOf(CharPool.UNDERLINE);

				newFileNameVersion += fileNameVersion.substring(pos);
			}

			File fileNameVersionFile = new File(
				newFileNameDir + StringPool.SLASH + fileNameVersion);

			if (!fileNameVersionFile.exists()) {
				throw new NoSuchFileException(
					companyId, repositoryId, fileName, fileNameVersion);
			}

			File newFileNameVersionFile = new File(
				newFileNameDir + StringPool.SLASH + newFileNameVersion);

			fileSystemHelper.move(fileNameVersionFile, newFileNameVersionFile);
		}
	}

	@Activate
	@Modified
	@Override
	protected void activate(Map<String, Object> properties) {
		_advancedFileSystemStoreConfiguration =
			ConfigurableUtil.createConfigurable(
				AdvancedFileSystemStoreConfiguration.class, properties);

		if (Validator.isBlank(
				_advancedFileSystemStoreConfiguration.rootDir())) {

			throw new IllegalArgumentException(
				"Advanced file system root directory is not set",
				new FileSystemStoreRootDirException());
		}

		initializeRootDir();

		fileSystemHelper = new FileSystemHelper(
			_advancedFileSystemStoreConfiguration.useHardLinks(),
			getRootDirPath());
	}

	protected void buildPath(StringBundler sb, String fileNameFragment) {
		int fileNameFragmentLength = fileNameFragment.length();

		if (fileNameFragmentLength <= 2) {
			return;
		}

		for (int i = 0; (i + 2) < fileNameFragmentLength; i += 2) {
			sb.append(fileNameFragment.substring(i, i + 2));
			sb.append(StringPool.SLASH);

			if (getDepth(sb.toString()) > 3) {
				return;
			}
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getFileNames(List, String, String)}
	 */
	@Deprecated
	protected List<String> getAdvancedFileNames(
		long companyId, long repositoryId, String fileName) {

		List<String> fileNames = new ArrayList<>();

		getFileNames(fileNames, StringPool.BLANK, fileName);

		return fileNames;
	}

	protected int getDepth(String path) {
		String[] fragments = StringUtil.split(path, CharPool.SLASH);

		return fragments.length;
	}

	@Override
	protected File getDirNameDir(
		long companyId, long repositoryId, String dirName) {

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		return new File(repositoryDir + StringPool.SLASH + dirName);
	}

	@Override
	protected File getFileNameDir(
		long companyId, long repositoryId, String fileName) {

		if (fileName.indexOf(CharPool.SLASH) != -1) {
			return getDirNameDir(companyId, repositoryId, fileName);
		}

		String ext = StringPool.PERIOD + FileUtil.getExtension(fileName);

		if (ext.equals(StringPool.PERIOD)) {
			ext += _HOOK_EXTENSION;
		}

		StringBundler sb = new StringBundler();

		String fileNameFragment = FileUtil.stripExtension(fileName);

		if (fileNameFragment.startsWith("DLFE-")) {
			fileNameFragment = fileNameFragment.substring(5);

			sb.append("DLFE/");
		}

		buildPath(sb, fileNameFragment);

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		StringBundler pathSB = new StringBundler(6);

		pathSB.append(repositoryDir);
		pathSB.append(StringPool.SLASH);
		pathSB.append(sb.toString());

		FileUtil.mkdirs(pathSB.toString());

		pathSB.append(StringPool.SLASH);
		pathSB.append(fileNameFragment);
		pathSB.append(ext);

		return new File(pathSB.toString());
	}

	@Override
	protected void getFileNames(
		List<String> fileNames, String dirName, String path) {

		super.getFileNames(fileNames, dirName, path);

		ListIterator<String> iterator = fileNames.listIterator();

		while (iterator.hasNext()) {
			String shortFileName = iterator.next();

			if (path.endsWith(_HOOK_EXTENSION)) {
				shortFileName = FileUtil.stripExtension(shortFileName);
			}

			iterator.set(unbuildPath(shortFileName));
		}
	}

	@Override
	protected File getFileNameVersionFile(
		long companyId, long repositoryId, String fileName, String version) {

		String ext = StringPool.PERIOD + FileUtil.getExtension(fileName);

		if (ext.equals(StringPool.PERIOD)) {
			ext += _HOOK_EXTENSION;
		}

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		if (pos == -1) {
			StringBundler sb = new StringBundler();

			String fileNameFragment = FileUtil.stripExtension(fileName);

			if (fileNameFragment.startsWith("DLFE-")) {
				fileNameFragment = fileNameFragment.substring(5);

				sb.append("DLFE/");
			}

			buildPath(sb, fileNameFragment);

			File repositoryDir = getRepositoryDir(companyId, repositoryId);

			StringBundler pathSB = new StringBundler(11);

			pathSB.append(repositoryDir);
			pathSB.append(StringPool.SLASH);
			pathSB.append(sb.toString());
			pathSB.append(StringPool.SLASH);
			pathSB.append(fileNameFragment);
			pathSB.append(ext);
			pathSB.append(StringPool.SLASH);
			pathSB.append(fileNameFragment);
			pathSB.append(StringPool.UNDERLINE);
			pathSB.append(version);
			pathSB.append(ext);

			return new File(pathSB.toString());
		}

		File fileNameDir = getDirNameDir(companyId, repositoryId, fileName);

		String fileNameFragment = FileUtil.stripExtension(
			fileName.substring(pos + 1));

		StringBundler pathSB = new StringBundler(6);

		pathSB.append(fileNameDir);
		pathSB.append(StringPool.SLASH);
		pathSB.append(fileNameFragment);
		pathSB.append(StringPool.UNDERLINE);
		pathSB.append(version);
		pathSB.append(ext);

		return new File(pathSB.toString());
	}

	@Override
	protected String getHeadVersionLabel(
		long companyId, long repositoryId, String fileName) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			return VERSION_DEFAULT;
		}

		String[] versionLabels = FileUtil.listFiles(fileNameDir);

		String headVersionLabel = VERSION_DEFAULT;

		for (String versionLabelFragment : versionLabels) {
			int x = versionLabelFragment.lastIndexOf(CharPool.UNDERLINE);

			if (x > -1) {
				int y = versionLabelFragment.lastIndexOf(CharPool.PERIOD);

				versionLabelFragment = versionLabelFragment.substring(x + 1, y);
			}

			String versionLabel = versionLabelFragment;

			if (DLUtil.compareVersions(versionLabel, headVersionLabel) > 0) {
				headVersionLabel = versionLabel;
			}
		}

		return headVersionLabel;
	}

	@Override
	protected String getRootDirName() {
		return _advancedFileSystemStoreConfiguration.rootDir();
	}

	protected String unbuildPath(String path) {
		if (path.startsWith("DLFE/")) {
			path = path.substring(5);
		}

		if (path.length() <= 2) {
			return path;
		}

		String[] parts = StringUtil.split(path, CharPool.SLASH);

		StringBundler sb = new StringBundler(parts.length - 1);

		for (int i = 0; i < (parts.length - 1); i++) {
			sb.append(parts[i]);
		}

		String simpleName = parts[parts.length - 1];

		if (simpleName.startsWith(sb.toString())) {
			return simpleName;
		}

		return path;
	}

	private static final String _HOOK_EXTENSION = "afsh";

	private static volatile AdvancedFileSystemStoreConfiguration
		_advancedFileSystemStoreConfiguration;

}