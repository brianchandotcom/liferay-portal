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

import com.liferay.dispatch.talend.web.internal.TalendArchiveUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class TalendArchiveParserUtilTest {

	@BeforeClass
	public static void setUpClass() {
		ReflectionTestUtil.setFieldValue(
			FileUtil.class, "_file", FileImpl.getInstance());
		ReflectionTestUtil.setFieldValue(
			FastDateFormatFactoryUtil.class, "_fastDateFormatFactory",
			new FastDateFormatFactoryImpl());
	}

	@Test
	public void testParse() throws IOException {
		TalendArchive talendArchive = TalendArchiveParserUtil.parse(
			TalendArchiveUtil.getInputStream());

		Assert.assertNotNull(talendArchive);

		String jobDirectory = talendArchive.getJobDirectory();

		Assert.assertNotNull(jobDirectory);

		StringBundler sb = new StringBundler();

		for (String classpathEntry : _CLASSPATH_ENTRIES) {
			sb.append(jobDirectory);
			sb.append(classpathEntry);
			sb.append(File.pathSeparator);
		}

		String classpath = talendArchive.getClasspath();

		Assert.assertTrue(classpath.startsWith(sb.toString()));

		Assert.assertEquals(
			jobDirectory + _JOB_JAR_PATH, talendArchive.getJobJarPath());

		String jobMainClassFQN = talendArchive.getJobMainClassFQN();

		Assert.assertTrue(jobMainClassFQN.endsWith(_JOB_NAME));
	}

	private static final String[] _CLASSPATH_ENTRIES = {
		"/lib/dom4j-1.6.1.jar", "/lib/log4j-1.2.17.jar", "/lib/routines.jar",
		"/lib/talend_file_enhanced_20070724.jar"
	};

	private static final String _JOB_JAR_PATH =
		"/etl_talend_context_printer_sample" +
			"/etl_talend_context_printer_sample_1_0.jar";

	private static final String _JOB_NAME = "context_printer_sample";

}