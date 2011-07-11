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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.FileFilter;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sergio González
 */
public class TempFileUtil {

	public static String addTempFile(String tempPathName, File file)
		throws IOException, PortalException, SystemException {

		String tempFileName = FileUtil.createTempFileName();

		InputStream inputStream = new FileInputStream(file);

		DLStoreUtil.validate(tempFileName, false, inputStream);

		File tmpFile = getTempFile(tempFileName, tempPathName);

		FileUtil.write(tmpFile, inputStream);

		return tempFileName;
	}

	public static String addTempFile(
			long userId, String tempPathName, File file)
		throws IOException, PortalException, SystemException {

		String tempFileName = FileUtil.createTempFileName();

		InputStream inputStream = new FileInputStream(file);

		DLStoreUtil.validate(tempFileName, true, inputStream);

		File tmpFile = getTempFile(tempFileName, tempPathName);

		FileUtil.write(tmpFile, inputStream);

		return tempFileName;
	}

	public static String addTempFile(
			String fileName, String tempPathName, File file)
		throws IOException, PortalException, SystemException {

		InputStream inputStream = new FileInputStream(file);

		DLStoreUtil.validate(fileName, true, inputStream);

		File tmpFile = getTempFile(fileName, tempPathName);

		FileUtil.write(tmpFile, inputStream);

		return fileName;
	}

	public static String addTempFile(
			long userId, String fileName, String tempPathName, File file)
		throws IOException, PortalException, SystemException {

		InputStream inputStream = new FileInputStream(file);

		DLStoreUtil.validate(fileName, true, inputStream);

		File tmpFile = getTempFile(userId, fileName, tempPathName);

		FileUtil.write(tmpFile, inputStream);

		return fileName;
	}

	public static void deleteTempFile(String fileName, String tempPathName) {
		File file = getTempFile(fileName, tempPathName);

		FileUtil.delete(file);
	}

	public static void deleteTempFile(
		long userId, String fileName, String tempPathName) {

		File file = getTempFile(userId, fileName, tempPathName);

		FileUtil.delete(file);
	}

	public static File getTempFile(String fileName, String tempPathName) {
		String absoluteFilePath = getTempAbsolutePath(fileName, tempPathName);

		return new File(absoluteFilePath);
	}

	public static File getTempFile(
		long userId, String fileName, String tempPathName) {

		String absoluteFilePath = getTempAbsolutePath(
			userId, fileName, tempPathName);

		return new File(absoluteFilePath);
	}

	public static String[] getTempFileEntryNames(String tempPathName) {
		File dir = new File(getTempAbsolutePath(tempPathName));

		File[] files =  dir.listFiles(
			new FileFilter(_SUFFIX_TEMP_FILENAME_REGEX));

		String[] fileNames = new String[files.length];

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			String fileName = StringUtil.replace(
				file.getName(), _SUFFIX_TEMP_FILENAME, StringPool.BLANK);

			fileNames[i] = fileName;
		}

		return fileNames;
	}

	public static String[] getTempFileEntryNames(
		long userId, String tempPathName) {

		File dir = new File(getTempAbsolutePath(tempPathName));

		StringBundler sb = new StringBundler(5);

		sb.append(StringPool.PERIOD);
		sb.append(StringPool.STAR);
		sb.append(StringPool.UNDERLINE);
		sb.append(userId);
		sb.append(_SUFFIX_TEMP_FILENAME_USERID_REGEX);

		FileFilter fileFilter = new FileFilter(sb.toString());

		File[] files = dir.listFiles(fileFilter);

		int numOfFiles = 0;

		if (files != null) {
			numOfFiles = files.length;
		}

		String[] fileNames = new String[numOfFiles];

		for (int i = 0; i < numOfFiles; i++) {
			File file = files[i];

			String fileName = StringUtil.replace(
				file.getName(),
				StringPool.UNDERLINE + userId + _SUFFIX_TEMP_FILENAME,
				StringPool.BLANK);

			fileNames[i] = fileName;
		}

		return fileNames;
	}

	private static String getTempAbsolutePath(String tempPathName) {
		StringBundler sb = new StringBundler(3);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append(_BASE_TEMP_PATHNAME);
		sb.append(tempPathName);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	private static String getTempAbsolutePath(
		String fileName, String tempPathName) {

		StringBundler sb = new StringBundler(3);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append(_BASE_TEMP_PATHNAME);
		sb.append(tempPathName);
		sb.append(StringPool.SLASH);
		sb.append(getTempFileName(fileName));

		return sb.toString();
	}

	private static String getTempAbsolutePath(
		long userId, String fileName, String tempPathName) {

		StringBundler sb = new StringBundler(3);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append(_BASE_TEMP_PATHNAME);
		sb.append(tempPathName);
		sb.append(StringPool.SLASH);
		sb.append(getTempFileName(userId, fileName));

		return sb.toString();
	}

	private static String getTempFileName(String fileName) {
		return fileName + _SUFFIX_TEMP_FILENAME;
	}

	private static String getTempFileName(long userId, String fileName) {
		StringBundler sb = new StringBundler(4);

		sb.append(fileName);
		sb.append(StringPool.UNDERLINE);
		sb.append(userId);
		sb.append(_SUFFIX_TEMP_FILENAME);

		return sb.toString();
	}

	private static final String _BASE_TEMP_PATHNAME = "/liferay/";

	private static final String _SUFFIX_TEMP_FILENAME = "_temp.tmp";

	private static final String _SUFFIX_TEMP_FILENAME_REGEX = ".*_temp\\.tmp";

	private static final String _SUFFIX_TEMP_FILENAME_USERID_REGEX =
		"_temp\\.tmp";

}