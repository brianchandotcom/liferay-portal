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

package com.liferay.portal.kernel.process;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.Charset;

import java.util.function.BiConsumer;

/**
 * @author Shuyang Zhou
 */
public class LoggingOutputProcessor implements OutputProcessor<Void, Void> {

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             CollectorOutputProcessor#INSTANCE}
	 */
	@Deprecated
	public static final OutputProcessor<Void, Void> INSTANCE =
		new LoggingOutputProcessor();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             CollectorOutputProcessor#INSTANCE}
	 */
	@Deprecated
	public LoggingOutputProcessor() {
		this(
			Charset.defaultCharset(),
			(stdErr, line) -> {
				if (stdErr) {
					_log.error(line);
				}
				else if (_log.isInfoEnabled()) {
					_log.info(line);
				}
			});
	}

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

	private static final Log _log = LogFactoryUtil.getLog(
		LoggingOutputProcessor.class);

	private final Charset _charset;
	private final BiConsumer<Boolean, String> _logLineConsumer;

}