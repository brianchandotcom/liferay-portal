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

package com.liferay.dispatch.talend.web.internal.archive;

import com.liferay.dispatch.talend.web.internal.BaseTalendTestCase;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class TalendArchiveParserTest extends BaseTalendTestCase {

	@Test
	public void testParse() {
		TalendArchiveParser talendArchiveParser = new TalendArchiveParser();

		TalendArchive talendArchive = talendArchiveParser.parse(
			getTalendArchiveInputStream());

		Assert.assertNotNull(talendArchive);

		List<String> talendArchiveClasspathEntries =
			talendArchive.getClasspathEntries();

		Assert.assertNotNull(talendArchiveClasspathEntries);

		for (String classpathEntry : _CLASSPATH_ENTRIES) {
			Assert.assertTrue(
				classpathEntry,
				talendArchiveClasspathEntries.contains(classpathEntry));
		}

		Assert.assertNotNull(talendArchive.getJobDirectory());

		Assert.assertEquals(_JOB_JAR_PATH, talendArchive.getJobJARPath());

		String jobMainClassFQN = talendArchive.getJobMainClassFQN();

		Assert.assertTrue(jobMainClassFQN.endsWith(_JOB_NAME));
	}

	private static final String[] _CLASSPATH_ENTRIES = {
		"/lib/dom4j-1.6.1.jar", "/lib/log4j-1.2.17.jar", "/lib/routines.jar",
		"/lib/talend_file_enhanced_20070724.jar"
	};

	private static final String _JOB_JAR_PATH =
		"./etl_talend_context_printer_sample" +
			"/etl_talend_context_printer_sample_1_0.jar";

	private static final String _JOB_NAME = "context_printer_sample";

}