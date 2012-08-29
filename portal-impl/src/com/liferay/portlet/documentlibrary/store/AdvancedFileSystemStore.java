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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;

import java.io.File;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-1976.
 * </p>
 *
 * @author Jorge Ferrer
 * @author Ryan Park
 * @author Brian Wing Shun Chan
 */
public class AdvancedFileSystemStore extends FileSystemStore {

	public AdvancedFileSystemStore() {
		if (!_rootDir.exists()) {
			_rootDir.mkdirs();
		}
	}

	@Override
	public String[] getFileVersions(
			long companyId, long repositoryId, String fileName)
		throws NoSuchDirectoryException {

		String[] fileVersions = super.getFileVersions(
			companyId, repositoryId, fileName);

		for (int i = 0;i < fileVersions.length;i++) {
			int x = fileVersions[i].lastIndexOf(CharPool.UNDERLINE);
			int y = fileVersions[i].lastIndexOf(CharPool.PERIOD);

			fileVersions[i] = fileVersions[i].substring(x + 1, y);
		}

		return fileVersions;
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException {

		super.updateFile(companyId, repositoryId, fileName, newFileName);

		File newFileNameDir = getFileNameDir(
			companyId, repositoryId, newFileName);

		String[] fileNameVersions = FileUtil.listFiles(newFileNameDir);

		for (String fileNameVersion : fileNameVersions) {
			String ext = FileUtil.getExtension(fileNameVersion);

			if (ext.equals(_HOOK_EXTENSION)) {
				continue;
			}

			File fileNameVersionFile = new File(
				newFileNameDir + StringPool.SLASH + fileNameVersion);
			File newFileNameVersionFile = new File(
				newFileNameDir + StringPool.SLASH +
					FileUtil.stripExtension(fileNameVersion) +
						StringPool.PERIOD + _HOOK_EXTENSION);

			boolean renamed = fileNameVersionFile.renameTo(
				newFileNameVersionFile);

			if (!renamed) {
				throw new SystemException(
					"File name version file was not renamed from " +
						fileNameVersionFile.getPath() + " to " +
							newFileNameVersionFile.getPath());
			}
		}
	}

	protected void buildPath(StringBundler sb, String fileNameFragment) {
		int fileNameFragmentLength = fileNameFragment.length();

		if ((fileNameFragmentLength <= 2) || (getDepth(sb.toString()) > 3)) {
			return;
		}

		for (int i = 0;i < fileNameFragmentLength;i += 2) {
			if ((i + 2) < fileNameFragmentLength) {
				sb.append(fileNameFragment.substring(i, i + 2));
				sb.append(StringPool.SLASH);

				if (getDepth(sb.toString()) > 3) {
					return;
				}
			}
		}

		return;
	}

	@Override
	protected File getCompanyDir(long companyId) {
		File companyDir = new File(_rootDir + StringPool.SLASH + companyId);

		if (!companyDir.exists()) {
			companyDir.mkdirs();
		}

		return companyDir;
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

			sb.append("DLFE" + StringPool.SLASH);
		}

		buildPath(sb, fileNameFragment);

		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		File fileNameDir = new File(
			repositoryDir + StringPool.SLASH + sb.toString() +
				StringPool.SLASH + fileNameFragment + ext);

		File parentFile = fileNameDir.getParentFile();

		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		return fileNameDir;
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

				sb.append("DLFE" + StringPool.SLASH);
			}

			buildPath(sb, fileNameFragment);

			File repositoryDir = getRepositoryDir(companyId, repositoryId);

			return new File(
				repositoryDir + StringPool.SLASH + sb.toString() +
					StringPool.SLASH + fileNameFragment + ext +
						StringPool.SLASH + fileNameFragment +
							StringPool.UNDERLINE + version + ext);
		}
		else {
			File fileNameDir = getDirNameDir(companyId, repositoryId, fileName);

			String fileNameFragment = FileUtil.stripExtension(
				fileName.substring(pos + 1));

			return new File(
				fileNameDir + StringPool.SLASH + fileNameFragment +
					StringPool.UNDERLINE + version + ext);
		}
	}

	@Override
	protected String getHeadVersionLabel(
			long companyId, long repositoryId, String fileName)
		throws NoSuchDirectoryException {

		String versions[] = getFileVersions(companyId, repositoryId, fileName);

		return versions[versions.length - 1];
	}

	private static final String _HOOK_EXTENSION = "afsh";

	private File _rootDir = new File(
		PropsValues.DL_STORE_ADVANCED_FILE_SYSTEM_ROOT);

}