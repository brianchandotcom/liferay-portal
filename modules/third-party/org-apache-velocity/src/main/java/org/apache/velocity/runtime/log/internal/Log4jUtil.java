/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.apache.velocity.runtime.log.internal;

import java.io.File;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DirectWriteRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * @author Hai Yu
 */
public class Log4jUtil {
	public static void setLevel(String loggerName, Level level) {
		LoggerContext loggerContext = (LoggerContext)LogManager.getContext();

		Configuration configuration = loggerContext.getConfiguration();

		Map<String, LoggerConfig> loggerConfigs = configuration.getLoggers();

		LoggerConfig loggerConfig = loggerConfigs.get(loggerName);

		if (loggerConfig == null) {
			loggerConfig = new LoggerConfig(loggerName, level, true);

			configuration.addLogger(loggerName, loggerConfig);
		}
		else {
			loggerConfig.setLevel(level);
		}

		loggerContext.updateLoggers();
	}

	public static RollingFileAppender createRollingFileAppender(
		String filePath) {

		RollingFileAppender.Builder rollingFileAppenderBuilder =
			RollingFileAppender.newBuilder();

		LoggerContext loggerContext = (LoggerContext)LogManager.getContext();

		rollingFileAppenderBuilder.setConfiguration(
			loggerContext.getConfiguration());

		File file = new File(filePath);

		String fileName = file.getName();

		String filePattern =
			filePath.substring(0, filePath.length() - fileName.length()) +
				fileName.replace(".", ".%d{yyyy-MM-dd}.");;

		rollingFileAppenderBuilder.withFilePattern(filePattern);

		TimeBasedTriggeringPolicy.Builder timeBasedTriggeringPolicyBuilder =
			TimeBasedTriggeringPolicy.newBuilder();

		rollingFileAppenderBuilder.withPolicy(
			timeBasedTriggeringPolicyBuilder.build());

		DirectWriteRolloverStrategy.Builder directWriteRolloverStrategyBuilder =
			DirectWriteRolloverStrategy.newBuilder();

		directWriteRolloverStrategyBuilder.withConfig(
			loggerContext.getConfiguration());

		rollingFileAppenderBuilder.withStrategy(
			directWriteRolloverStrategyBuilder.build());

		PatternLayout.Builder patternLayoutBuilder =  PatternLayout.newBuilder();

		patternLayoutBuilder.withConfiguration(
			loggerContext.getConfiguration());
		patternLayoutBuilder.withPattern("%d - %m%n");

		rollingFileAppenderBuilder.setLayout(patternLayoutBuilder.build());
		rollingFileAppenderBuilder.setName(filePath);

		RollingFileAppender rollingFileAppender =
			rollingFileAppenderBuilder.build();

		rollingFileAppender.start();

		return rollingFileAppender;
	}

}
/* @generated */