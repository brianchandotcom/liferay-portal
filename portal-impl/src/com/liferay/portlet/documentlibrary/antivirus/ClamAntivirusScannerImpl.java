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

package com.liferay.portlet.documentlibrary.antivirus;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Michael C. Han
 */
public class ClamAntivirusScannerImpl extends BaseFileAntivirusScanner {

	public void scan(File file)
		throws AntivirusScannerException, SystemException {

		Runtime runtime = Runtime.getRuntime();

		String filePath = file.getAbsolutePath();

		String[] parameters = new String[] {
			"clamscan", "--stdout", "--no-summary", filePath };

		Process process = null;

		try {
			process = runtime.exec(parameters);

			InputStream inputStream = process.getInputStream();

			String scanResult = StringUtil.read(inputStream);

			process.waitFor();

			int exitValue = process.exitValue();

			if (exitValue != 0) {
				throw new AntivirusScannerException(
					"Unable to scan file due to inability to execute " +
						"antivirus process");
			}

			if (scanResult.contains("FOUND")) {
				throw new AntivirusScannerException(
					"Virus detected in " + filePath);
			}
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to scan file", ioe);
		}
		catch (InterruptedException ie) {
			throw new SystemException("Unable to scan file", ie);
		}
		finally {
			if (process != null) {
				process.destroy();
			}
		}
	}

}