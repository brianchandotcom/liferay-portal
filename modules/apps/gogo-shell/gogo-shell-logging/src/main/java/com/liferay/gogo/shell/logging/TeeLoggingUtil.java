/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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