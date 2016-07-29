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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hugo Huijser
 */
public class CheckStyleUtil {

	public CheckStyleUtil(String configurationFileName) {
		_checker = _getChecker(configurationFileName);
	}

	public List<SourceFormatterMessage> process(File file, String fileName)
		throws Exception {

		_checker.process(Arrays.asList(file));

		return _sourceFormatterMessagesMap.get(fileName);
	}

	private Checker _getChecker(String configurationFileName) {
		try {
			Checker checker = new Checker();

			checker.setModuleClassLoader(CheckStyleUtil.class.getClassLoader());

			Configuration configuration = ConfigurationLoader.loadConfiguration(
				configurationFileName,
				new PropertiesExpander(System.getProperties()), false);

			checker.configure(configuration);

			AuditListener listener = new SourceFormatterLogger(
				new UnsyncByteArrayOutputStream(), true);

			checker.addListener(listener);

			return checker;
		}
		catch (Exception e) {
			return null;
		}
	}

	private class SourceFormatterLogger extends DefaultLogger {

		public SourceFormatterLogger(
			OutputStream outputStream, boolean closeStreamsAfterUse) {

			super(outputStream, closeStreamsAfterUse);
		}

		@Override
		public void addError(AuditEvent auditEvent) {
			String fileName = StringUtil.replace(
				auditEvent.getFileName(), StringPool.BACK_SLASH,
				StringPool.SLASH);

			List<SourceFormatterMessage> sourceFormatterMessages =
				_sourceFormatterMessagesMap.get(fileName);

			if (sourceFormatterMessages == null) {
				sourceFormatterMessages = new ArrayList<>();
			}

			sourceFormatterMessages.add(
				new SourceFormatterMessage(
					fileName, auditEvent.getMessage(), auditEvent.getLine()));

			_sourceFormatterMessagesMap.put(fileName, sourceFormatterMessages);

			super.addError(auditEvent);
		}

	}

	private final Checker _checker;
	private Map<String, List<SourceFormatterMessage>>
		_sourceFormatterMessagesMap = new ConcurrentHashMap<>();

}