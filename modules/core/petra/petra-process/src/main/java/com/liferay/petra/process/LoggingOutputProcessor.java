/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.Charset;

import java.util.function.BiConsumer;

/**
 * @author Shuyang Zhou
 */
public class LoggingOutputProcessor implements OutputProcessor<Void, Void> {

	public LoggingOutputProcessor(BiConsumer<Boolean, String> logLineConsumer) {
		this(Charset.defaultCharset(), logLineConsumer);
	}

	public LoggingOutputProcessor(
		Charset charset, BiConsumer<Boolean, String> logLineConsumer) {

		_charset = charset;
		_logLineConsumer = logLineConsumer;
	}

	@Override
	public Void processStdErr(InputStream stdErrInputStream)
		throws ProcessException {

		_processOut(true, stdErrInputStream);

		return null;
	}

	@Override
	public Void processStdOut(InputStream stdOutInputStream)
		throws ProcessException {

		_processOut(false, stdOutInputStream);

		return null;
	}

	private void _processOut(boolean stdErr, InputStream inputStream)
		throws ProcessException {

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(inputStream, _charset));

		String line = null;

		try {
			while ((line = unsyncBufferedReader.readLine()) != null) {
				_logLineConsumer.accept(stdErr, line);
			}
		}
		catch (IOException ioException) {
			throw new ProcessException(ioException);
		}
		finally {
			try {
				unsyncBufferedReader.close();
			}
			catch (IOException ioException) {
				throw new ProcessException(ioException);
			}
		}
	}

	private final Charset _charset;
	private final BiConsumer<Boolean, String> _logLineConsumer;

}