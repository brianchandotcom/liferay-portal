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

package com.liferay.pmd;

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ci.AutoBalanceTestCase;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import net.sourceforge.pmd.ant.Formatter;
import net.sourceforge.pmd.ant.PMDTask;
import net.sourceforge.pmd.ant.SourceLanguage;
import net.sourceforge.pmd.lang.java.typeresolution.ClassTypeResolver;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class PMDTest extends AutoBalanceTestCase {

	@Before
	public void setUp() throws IOException {
		try (FileReader fileReader = new FileReader(
				new File(_PROJECT_DIR, "/tools/sdk/build.properties"))) {

			_buildProperties.load(fileReader);
		}

		try (FileReader fileReader = new FileReader(
				new File(_PROJECT_DIR, "build.properties"))) {

			_buildProperties.load(fileReader);
		}

		_pmdTask.setProject(new Project());
		_pmdTask.setShortFilenames(true);
		_pmdTask.setThreads(
			GetterUtil.get(_buildProperties.getProperty("pmd.threads"), 2));

		FileSet fileSet = new FileSet();

		fileSet.setDir(new File(_PROJECT_DIR));
		fileSet.setIncludes(_buildProperties.getProperty("pmd.java.includes"));
		fileSet.setExcludes(_buildProperties.getProperty("pmd.java.excludes"));

		if (isCIMode()) {
			DirectoryScanner directoryScanner = fileSet.getDirectoryScanner(
				_pmdTask.getProject());

			String[] slicedIncludedFiles = slice(
				directoryScanner.getIncludedFiles());

			fileSet = new FileSet();

			fileSet.setDir(new File(_PROJECT_DIR));
			fileSet.setIncludes(
				StringUtil.merge(slicedIncludedFiles, StringPool.COMMA));
		}

		_pmdTask.addFileset(fileSet);

		Formatter formatter = new Formatter();

		_logFilePath = Files.createTempFile(null, null);

		formatter.setToFile(_logFilePath.toFile());
		formatter.setType("text");

		_pmdTask.addFormatter(formatter);

		_pmdTask.setRuleSetFiles(
			StringUtil.replace(
				_buildProperties.getProperty("pmd.java.ruleset"), "${sdk.dir}",
				_PROJECT_DIR + "/tools/sdk"));
	}

	@After
	public void tearDown() throws IOException {
		Files.delete(_logFilePath);
	}

	@Test
	public void testPMDJava() throws IOException {
		SourceLanguage sourceLanguage = new SourceLanguage();

		sourceLanguage.setName("java");
		sourceLanguage.setVersion(
			_buildProperties.getProperty("ant.build.javac.source"));

		_pmdTask.addConfiguredSourceLanguage(sourceLanguage);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClassTypeResolver.class.getName(), Level.SEVERE);

		try {
			_pmdTask.execute();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			if (!logRecords.isEmpty()) {
				AssertionError assertionError = new AssertionError(
					"PMD Java log error");

				for (LogRecord logRecord : logRecords) {
					assertionError.addSuppressed(
						new Throwable(
							logRecord.getMessage(), logRecord.getThrown()));
				}

				throw assertionError;
			}

			List<String> list = Files.readAllLines(
				_logFilePath, Charset.defaultCharset());

			Assert.assertTrue(list.toString(), list.isEmpty());
		}
		finally {
			captureHandler.close();
		}
	}

	private static final String _PROJECT_DIR = "../../../";

	private final Properties _buildProperties = new Properties();
	private Path _logFilePath;
	private final PMDTask _pmdTask = new PMDTask();

}