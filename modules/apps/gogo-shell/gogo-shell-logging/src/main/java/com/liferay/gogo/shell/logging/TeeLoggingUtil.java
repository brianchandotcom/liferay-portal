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

package com.liferay.gogo.shell.logging;

import com.liferay.petra.io.OutputStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * @author Shuyang Zhou
 */
public class TeeLoggingUtil {

	public static void runWithTeeLogging(Runnable runnable) {
		PatternLayout.Builder build = PatternLayout.newBuilder();

		build.withPattern("%level - %m%n");

		PatternLayout patternLayout = build.build();

		WriterAppender writerAppender = WriterAppender.createAppender(
			patternLayout, null,
			new OutputStreamWriter(System.out, "UTF-8", true),
			"TeeLoggingAppender", false, false);

		writerAppender.start();

		Logger rootLogger = (Logger)LogManager.getRootLogger();

		rootLogger.addAppender(writerAppender);

		try {
			runnable.run();
		}
		finally {
			rootLogger.removeAppender(writerAppender);

			writerAppender.stop();
		}
	}

}