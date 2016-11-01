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

package com.liferay.source.formatter.checkstyle.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checkstyle.Checker;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.filters.SuppressionsLoader;

import java.io.File;
import java.io.OutputStream;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.xml.sax.InputSource;

/**
 * @author Hugo Huijser
 */
public class CheckStyleUtil {

	public static Set<SourceFormatterMessage> process(
			Set<File> files, List<File> suppressionsFiles, String baseDirName)
		throws Exception {

		_sourceFormatterMessages.clear();

		Checker checker = _getChecker(suppressionsFiles, baseDirName);

		checker.process(ListUtil.fromCollection(files));

		return _sourceFormatterMessages;
	}

	private static Checker _getChecker(
			List<File> suppressionsFiles, String baseDirName)
		throws Exception {

		Checker checker = new Checker();

		ClassLoader classLoader = CheckStyleUtil.class.getClassLoader();

		checker.setModuleClassLoader(classLoader);

		for (File suppressionsFile : suppressionsFiles) {
			checker.addFilter(
				SuppressionsLoader.loadSuppressions(
					suppressionsFile.getAbsolutePath()));
		}

		Configuration configuration = ConfigurationLoader.loadConfiguration(
			new InputSource(classLoader.getResourceAsStream("checkstyle.xml")),
			new PropertiesExpander(System.getProperties()), false);

		checker.configure(configuration);

		AuditListener listener = new SourceFormatterLogger(
			new UnsyncByteArrayOutputStream(), true, baseDirName);

		checker.addListener(listener);

		return checker;
	}

	private static final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new TreeSet<>();

	private static class SourceFormatterLogger extends DefaultLogger {

		public SourceFormatterLogger(
			OutputStream outputStream, boolean closeStreamsAfterUse,
			String baseDirName) {

			super(outputStream, closeStreamsAfterUse);

			_baseDirName = baseDirName;
		}

		@Override
		public void addError(AuditEvent auditEvent) {
			_sourceFormatterMessages.add(
				new SourceFormatterMessage(
					_getRelativizedFileName(auditEvent),
					auditEvent.getMessage(), auditEvent.getLine()));

			super.addError(auditEvent);
		}

		private Path _getAbsoluteNormalizedPath(String pathName) {
			Path path = Paths.get(pathName);

			path = path.toAbsolutePath();

			return path.normalize();
		}

		private String _getRelativizedFileName(AuditEvent auditEvent) {
			if (Validator.isNull(_baseDirName)) {
				return auditEvent.getFileName();
			}

			Path baseDirPath = _getAbsoluteNormalizedPath(_baseDirName);

			Path relativizedPath = baseDirPath.relativize(
				_getAbsoluteNormalizedPath(auditEvent.getFileName()));

			return _baseDirName +
				StringUtil.replace(
					relativizedPath.toString(), CharPool.BACK_SLASH,
					CharPool.SLASH);
		}

		private final String _baseDirName;

	}

}