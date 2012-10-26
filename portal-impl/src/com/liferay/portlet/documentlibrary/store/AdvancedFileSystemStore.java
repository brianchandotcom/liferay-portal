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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		File repositoryDir = getRepositoryDir(companyId, repositoryId);

		String[] directories = FileUtil.listDirs(repositoryDir);

		List<String> fileNames = new ArrayList<String>();

		for (String directory : directories) {
			fileNames.addAll(
				getAdvancedFileNames(
					companyId, repositoryId,
					repositoryDir.getPath() + StringPool.SLASH + directory));
		}

		return fileNames.toArray(new String[fileNames.size()]);
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

			boolean renamed = FileUtil.move(
				fileNameVersionFile, newFileNameVersionFile);

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

		for (int i = 0; i < fileNameFragmentLength; i += 2) {
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

	protected List<String> getAdvancedFileNames(
		long companyId, long repositoryId, String fileName) {

		List<String> fileNames = new ArrayList<String>();

		String shortFileName = FileUtil.getShortFileName(fileName);

		if (shortFileName.equals("DLFE") || Validator.isNumber(shortFileName)) {
			String[] curFileNames = FileUtil.listDirs(fileName);

			for (String curFileName : curFileNames) {
				fileNames.addAll(
					getAdvancedFileNames(
						companyId, repositoryId,
						fileName + StringPool.SLASH + curFileName));
			}
		}
		else {
			if (shortFileName.endsWith(_HOOK_EXTENSION)) {
				shortFileName = FileUtil.stripExtension(shortFileName);
			}

			fileNames.add(shortFileName);
		}

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
		long companyId, long repositoryId, String fileName) {

		File fileNameDir = getFileNameDir(companyId, repositoryId, fileName);

		if (!fileNameDir.exists()) {
			return VERSION_DEFAULT;
		}

		String[] versionLabels = FileUtil.listFiles(fileNameDir);

		String headVersionLabel = VERSION_DEFAULT;

		for (int i = 0; i < versionLabels.length; i++) {
			String versionLabelFragment = versionLabels[i];

			int x = versionLabelFragment.lastIndexOf(CharPool.UNDERLINE);
			int y = versionLabelFragment.lastIndexOf(CharPool.PERIOD);

			if (x > -1) {
				versionLabelFragment = versionLabelFragment.substring(x + 1, y);
			}

			String versionLabel = versionLabelFragment;

			if (DLUtil.compareVersions(versionLabel, headVersionLabel) > 0) {
				headVersionLabel = versionLabel;
			}
		}

		return headVersionLabel;
	}

	private static final String _HOOK_EXTENSION = "afsh";

}