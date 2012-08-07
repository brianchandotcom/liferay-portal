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

package com.liferay.portal.kernel.process;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Shuyang Zhou
 */
public class LoggingOutputProcessor implements OutputProcessor<Void, Void> {

	public Void processStdErr(InputStream stdErrInputStream)
		throws ProcessException {

		_processOut("[stderr]", stdErrInputStream);

		return null;
	}

	public Void processStdOut(InputStream stdOutInputStream)
		throws ProcessException {

		_processOut("[stdout]", stdOutInputStream);

		return null;
	}

	private void _processOut(String prefix, InputStream inputStream)
		throws ProcessException {

		UnsyncBufferedReader unsyncBufferedReader =
			new UnsyncBufferedReader(new InputStreamReader(inputStream));

		String line = null;

		try {
			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (_log.isInfoEnabled()) {
					_log.info(prefix.concat(line));
				}
			}
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
		finally {
			try {
				unsyncBufferedReader.close();
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LoggingOutputProcessor.class);

}