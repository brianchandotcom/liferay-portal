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

package com.liferay.portlet.documentlibrary.antivirus;

import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.antivirus.BaseFileAntivirusScanner;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class ClamAntivirusScannerImpl extends BaseFileAntivirusScanner {

	@Override
	public void scan(File file) throws AntivirusScannerException {
		int exitValue = 0;

		try {
			exitValue = _execute("clamdscan", file);
		}
		catch (InterruptedException | IOException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to successfully execute clamdscan", exception);
			}

			exitValue = -1;
		}

		try {
			if ((exitValue != 0) && (exitValue != 1)) {
				exitValue = _execute("clamscan", file);
			}

			if (exitValue == 1) {
				throw new AntivirusScannerException(
					"Virus detected in " + file.getAbsolutePath(),
					AntivirusScannerException.VIRUS_DETECTED);
			}
			else if (exitValue >= 2) {
				throw new AntivirusScannerException(
					AntivirusScannerException.PROCESS_FAILURE);
			}
		}
		catch (InterruptedException | IOException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to successfully execute clamscan", exception);
			}

			throw new AntivirusScannerException(
				AntivirusScannerException.PROCESS_FAILURE, exception);
		}
	}

	private int _execute(String command, File file)
		throws InterruptedException, IOException {

		Process process = null;

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(
				command, "--stdout", "--no-summary", file.getAbsolutePath());

			processBuilder.redirectErrorStream(true);

			process = processBuilder.start();

			process.waitFor();

			return process.exitValue();
		}
		finally {
			if (process != null) {
				process.destroy();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClamAntivirusScannerImpl.class);

}