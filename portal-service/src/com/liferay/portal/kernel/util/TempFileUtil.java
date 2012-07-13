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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sergio González
 * @author Matthew Kong
 */
public class TempFileUtil {

	public static String addTempFile(
			long userId, String tempPathName, File file)
		throws IOException, PortalException, SystemException {

		String fileName = FileUtil.createTempFileName();

		return addTempFile(userId, fileName, tempPathName, file);
	}

	public static String addTempFile(
			long userId, String fileName, String tempPathName, File file)
		throws IOException, PortalException, SystemException {

		String tempFileName = getTempFileName(userId, fileName, tempPathName);

		DLStoreUtil.addFile(_COMPANY_ID, _REPOSITORY_ID, tempFileName, file);

		return fileName;
	}

	public static String addTempFile(
			long userId, String fileName, String tempPathName,
			InputStream inputStream)
		throws IOException, PortalException, SystemException {

		File file = null;

		if (inputStream instanceof ByteArrayFileInputStream) {
			ByteArrayFileInputStream byteArrayFileInputStream =
				(ByteArrayFileInputStream)inputStream;

			file = byteArrayFileInputStream.getFile();
		}

		String tempFileName = getTempFileName(userId, fileName, tempPathName);

		if (file != null) {
			DLStoreUtil.addFile(
				_COMPANY_ID, _REPOSITORY_ID, tempFileName, file);
		}
		else {
			DLStoreUtil.addFile(
				_COMPANY_ID, _REPOSITORY_ID, tempFileName, inputStream);
		}

		return fileName;
	}

	public static String addTempFile(String tempPathName, File file)
		throws IOException, PortalException, SystemException {

		return addTempFile(_USER_ID, tempPathName, file);
	}

	public static String addTempFile(
			String fileName, String tempPathName, File file)
		throws IOException, PortalException, SystemException {

		return addTempFile(_USER_ID, fileName, tempPathName, file);
	}

	public static void deleteTempFile(
			long userId, String fileName, String tempPathName)
		throws PortalException, SystemException {

		String tempFileName = getTempFileName(userId, fileName, tempPathName);

		DLStoreUtil.deleteFile(_COMPANY_ID, _REPOSITORY_ID, tempFileName);
	}

	public static void deleteTempFile(String fileName, String tempPathName)
		throws PortalException, SystemException {

		deleteTempFile(_USER_ID, fileName, tempPathName);
	}

	public static InputStream getTempFileAsStream(
			String fileName, String tempPathName)
		throws PortalException, SystemException {

		return getTempFileAsStream(_USER_ID, fileName, tempPathName);
	}

	public static InputStream getTempFileAsStream(
			long userId, String fileName, String tempPathName)
		throws PortalException, SystemException {

		String tempFileName = getTempFileName(userId, fileName, tempPathName);

		return DLStoreUtil.getFileAsStream(
			_COMPANY_ID, _REPOSITORY_ID, tempFileName);
	}

	public static String[] getTempFileEntryNames(
		long userId, String tempPathName) {

		try {
			String tempFolderName = getTempFolderName(userId, tempPathName);

			String[] fileNames = DLStoreUtil.getFileNames(
				_COMPANY_ID, _REPOSITORY_ID, tempFolderName);

			for (int i = 0; i < fileNames.length; i++) {
				String fileName = StringUtil.extractLast(
					fileNames[i], StringPool.SLASH);

				fileName = StringUtil.replace(
					fileName, _SUFFIX_TEMP_FILENAME, StringPool.BLANK);

				fileNames[i] = fileName;
			}

			return fileNames;
		}
		catch (Exception e) {
			return new String[0];
		}
	}

	public static String[] getTempFileEntryNames(String tempPathName)
		throws PortalException {

		return getTempFileEntryNames(_USER_ID, tempPathName);
	}

	public static long getTempFileSize
			(String fileName, String tempPathName)
		throws PortalException, SystemException {

		return getTempFileSize(_USER_ID, fileName, tempPathName);
	}

	public static long getTempFileSize
			(long userId, String fileName, String tempPathName)
		throws PortalException, SystemException {

		String tempFileName = getTempFileName(userId, fileName, tempPathName);

		return DLStoreUtil.getFileSize(
			_COMPANY_ID, _REPOSITORY_ID, tempFileName);
	}

	protected static String getTempFileName(
			long userId, String fileName, String tempPathName)
		throws PortalException {

		validateFileName(fileName);

		StringBundler sb = new StringBundler(3);

		sb.append(getTempFolderName(userId, tempPathName));
		sb.append(fileName);
		sb.append(_SUFFIX_TEMP_FILENAME);

		return sb.toString();
	}

	protected static String getTempFolderName(
		long userId, String tempPathName) {

		StringBundler sb = new StringBundler(5);

		sb.append(_BASE_TEMP_PATHNAME);
		sb.append(tempPathName);
		sb.append(StringPool.SLASH);
		sb.append(userId);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected static void validateFileName(String name) throws PortalException {
		if ((name == null) ||
			name.contains(StringPool.SLASH) ||
			name.contains(StringPool.BACK_SLASH) ||
			name.contains(File.pathSeparator)) {

			throw new TempFileNameException();
		}
	}

	private static final String _BASE_TEMP_PATHNAME = "liferay_temp/";

	private static final long _COMPANY_ID = 0;

	private static final long _REPOSITORY_ID = 0;

	private static final String _SUFFIX_TEMP_FILENAME = "_temp.tmp";

	private static final long _USER_ID = 0;

}