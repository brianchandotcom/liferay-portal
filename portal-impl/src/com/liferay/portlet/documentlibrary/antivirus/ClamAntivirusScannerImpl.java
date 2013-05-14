/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.File;
import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class ClamAntivirusScannerImpl extends BaseFileAntivirusScanner {

	@Override
	public void scan(File file)
		throws AntivirusScannerException, SystemException {

		ProcessBuilder processBuilder = new ProcessBuilder(
			"clamscan", "--stdout", "--no-summary", file.getAbsolutePath());

		processBuilder.redirectErrorStream(true);

		Process process = null;

		try {
			process = processBuilder.start();

			process.waitFor();

			int exitValue = process.exitValue();

			if (exitValue == 1) {
				throw new AntivirusScannerException(
					"Virus detected in " + file.getAbsolutePath());
			}
			else if (exitValue >= 2) {
				throw new AntivirusScannerException(
					"Unable to scan file due to inability to execute " +
						"antivirus process");
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