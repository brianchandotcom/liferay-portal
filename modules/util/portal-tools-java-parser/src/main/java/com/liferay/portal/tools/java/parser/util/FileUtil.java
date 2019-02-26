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

package com.liferay.portal.tools.java.parser.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author Hugo Huijser
 */
public class FileUtil {

	public static File createTempFile(String extension) {
		StringBundler sb = new StringBundler(5);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append(StringPool.SLASH);
		sb.append(PwdGenerator.getPassword(8, PwdGenerator.KEY2));

		if (Validator.isFileExtension(extension)) {
			sb.append(StringPool.PERIOD);
			sb.append(extension);
		}

		return new File(sb.toString());
	}

	public static String read(File file) throws IOException {
		try {
			String s = FileUtils.readFileToString(file, StringPool.UTF8);

			return StringUtil.replace(
				s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
		}
		catch (FileNotFoundException fnfe) {
			return null;
		}
	}

	public static void write(File file, String s) throws IOException {
		FileUtils.writeStringToFile(file, s, StringPool.UTF8);
	}

}