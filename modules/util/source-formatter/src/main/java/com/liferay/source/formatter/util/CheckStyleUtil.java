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

package com.liferay.source.formatter.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.SourceFormatterMessage;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.io.File;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class CheckStyleUtil {

	public static List<SourceFormatterMessage> process(
			String configurationFileName, List<File> files,
			String baseDirAbsolutePath)
		throws Exception {

		Checker checker = _getChecker(
			configurationFileName, baseDirAbsolutePath);

		checker.process(files);

		return _sourceFormatterMessages;
	}

	private static Checker _getChecker(
		String configurationFileName, String baseDirAbsolutePath) {

		try {
			Checker checker = new Checker();

			checker.setModuleClassLoader(CheckStyleUtil.class.getClassLoader());

			Configuration configuration = ConfigurationLoader.loadConfiguration(
				configurationFileName,
				new PropertiesExpander(System.getProperties()), false);

			checker.configure(configuration);

			AuditListener listener = new SourceFormatterLogger(
				new UnsyncByteArrayOutputStream(), true, baseDirAbsolutePath);

			checker.addListener(listener);

			return checker;
		}
		catch (Exception e) {
			return null;
		}
	}

	private static class SourceFormatterLogger extends DefaultLogger {

		public SourceFormatterLogger(
			OutputStream outputStream, boolean closeStreamsAfterUse,
			String baseDirAbsolutePath) {

			super(outputStream, closeStreamsAfterUse);

			_baseDirAbsolutePath = baseDirAbsolutePath;
		}

		@Override
		public void addError(AuditEvent auditEvent) {
			String fileName = StringUtil.replace(
				auditEvent.getFileName(), StringPool.BACK_SLASH,
				StringPool.SLASH);

			if (fileName.startsWith(_baseDirAbsolutePath + "/")) {
				fileName = StringUtil.replaceFirst(
					fileName, _baseDirAbsolutePath + "/", StringPool.BLANK);
			}

			_sourceFormatterMessages.add(
				new SourceFormatterMessage(
					fileName, auditEvent.getMessage(), auditEvent.getLine()));

			super.addError(auditEvent);
		}

		private final String _baseDirAbsolutePath;

	}

	private static List<SourceFormatterMessage> _sourceFormatterMessages =
		new ArrayList<>();

}