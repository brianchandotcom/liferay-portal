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

package com.liferay.portal.license.sigar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarLoader;

/**
 * @author Ivica Cardic
 * @author Amos Fong
 */
public class SigarNativeLoader {

	public static synchronized void load() throws Exception {
		File targetDirectory = _getTargetDirectory();

		if (!targetDirectory.exists()) {
			targetDirectory.mkdirs();
		}

		SigarLoader sigarLoader = new SigarLoader(Sigar.class);

		String libraryName = sigarLoader.getLibraryName();

		InputStream inputStream = SigarNativeLoader.class.getResourceAsStream(
			"/com/liferay/portal/license/sigar/dependencies/" + libraryName);

		File targetFile = new File(targetDirectory, libraryName);

		targetFile = targetFile.getAbsoluteFile();

		OutputStream outputStream = new FileOutputStream(targetFile);

		_copy(inputStream, outputStream);

		inputStream.close();

		outputStream.close();

		String libraryPath = targetFile.getAbsolutePath();

		System.load(libraryPath);

		System.setProperty("org.hyperic.sigar.path", "-");

		sigarLoader.load();

		if (_log.isDebugEnabled()) {
			_log.debug("Sigar native library " + libraryPath + " is loaded");
		}
	}

	private static void _copy(
			InputStream inputStream, OutputStream outputStream)
		throws Exception {

		int size = 64 * 1024;

		byte[] data = new byte[size];

		while (true) {
			int count = inputStream.read(data, 0, size);

			if (count == -1) {
				break;
			}

			outputStream.write(data, 0, count);
		}
	}

	private static File _getTargetDirectory() {
		StringBundler sb = new StringBundler(5);

		sb.append(PropsUtil.get("liferay.home"));
		sb.append(File.separator);
		sb.append("data");
		sb.append(File.separator);
		sb.append("sigar");

		return new File(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SigarNativeLoader.class.getName());

}