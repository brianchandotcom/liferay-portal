/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static boolean exists(String fileName) {
		File file = new File(fileName);

		return file.exists();
	}

	public static byte[] getBytes(File file) throws IOException {
		return FileUtils.readFileToByteArray(file);
	}

	public static String read(File file) throws IOException {
		return read(file, true);
	}

	public static String read(File file, boolean escapeReturnCharacter)
		throws IOException {

		try {
			String s = FileUtils.readFileToString(file, StringPool.UTF8);

			if (!escapeReturnCharacter) {
				return s;
			}

			return StringUtil.replace(
				s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
		}
		catch (FileNotFoundException fileNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(fileNotFoundException);
			}

			return null;
		}
	}

	public static void write(File file, String s) throws IOException {
		FileUtils.writeStringToFile(file, s, StringPool.UTF8);
	}

	private static final Log _log = LogFactoryUtil.getLog(FileUtil.class);

}