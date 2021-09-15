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

package com.liferay.dispatch.talend.archive;

import com.liferay.dispatch.talend.TalendArchiveUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class TalendArchiveParserUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		ReflectionTestUtil.setFieldValue(
			FileUtil.class, "_file", FileImpl.getInstance());
		ReflectionTestUtil.setFieldValue(
			FastDateFormatFactoryUtil.class, "_fastDateFormatFactory",
			new FastDateFormatFactoryImpl());
	}

	@Test
	public void testParse() throws Exception {
		TalendArchive talendArchive = TalendArchiveParserUtil.parse(
			TalendArchiveUtil.getInputStream());

		Assert.assertNotNull(talendArchive);

		String jobDirectory = talendArchive.getJobDirectory();

		Assert.assertNotNull(jobDirectory);

		StringBundler sb = new StringBundler();

		for (String classPathEntry : _LIB_JARS) {
			sb.append(jobDirectory);
			sb.append(File.separator);
			sb.append("lib");
			sb.append(File.separator);
			sb.append(classPathEntry);
			sb.append(File.pathSeparator);
		}

		String classPath = talendArchive.getClassPath();

		Assert.assertTrue(classPath.startsWith(sb.toString()));

		Properties contextProperties = talendArchive.getContextProperties();

		Assert.assertNotNull(contextProperties);
		Assert.assertEquals(
			"2011", contextProperties.getProperty("multiplier"));
		Assert.assertEquals("Liferay", contextProperties.getProperty("prefix"));

		Assert.assertEquals(
			jobDirectory + _JOB_JAR_PATH, talendArchive.getJobJarPath());

		String jobMainClassFQN = talendArchive.getJobMainClassFQN();

		Assert.assertTrue(jobMainClassFQN.endsWith(_JOB_NAME));

		Assert.assertNotNull(talendArchive.getJVMOptions());
		Assert.assertEquals(
			"-Xms256M -Xmx1024M", talendArchive.getJVMOptions());
		Assert.assertTrue(talendArchive.hasJVMOptions());
	}

	@Test
	public void testUpdateUnicodeProperties() throws Exception {
		UnicodeProperties unicodeProperties = new UnicodeProperties();

		TalendArchiveParserUtil.updateUnicodeProperties(
			TalendArchiveUtil.getInputStream(), unicodeProperties);

		Assert.assertEquals(
			"2011 (Automatic Copy)",
			unicodeProperties.getProperty("multiplier"));
		Assert.assertEquals(
			"Liferay (Automatic Copy)",
			unicodeProperties.getProperty("prefix"));
		Assert.assertEquals(
			"-Xms256M -Xmx1024M", unicodeProperties.getProperty("JAVA_OPTS"));

		unicodeProperties.put("multiplier", "4444");
		unicodeProperties.remove("prefix");
		unicodeProperties.put("JAVA_OPTS", "-Dtest");

		TalendArchiveParserUtil.updateUnicodeProperties(
			TalendArchiveUtil.getInputStream(), unicodeProperties);

		Assert.assertEquals(
			"4444", unicodeProperties.getProperty("multiplier"));
		Assert.assertEquals(
			"Liferay (Automatic Copy)",
			unicodeProperties.getProperty("prefix"));
		Assert.assertEquals(
			"-Xms256M -Xmx1024M -Dtest",
			unicodeProperties.getProperty("JAVA_OPTS"));
	}

	private static final String _JOB_JAR_PATH = StringBundler.concat(
		File.separator, "etl_talend_context_printer_sample", File.separator,
		"etl_talend_context_printer_sample_1_0.jar");

	private static final String _JOB_NAME = "context_printer_sample";

	private static final String[] _LIB_JARS = {
		"dom4j-1.6.1.jar", "log4j-1.2.17.jar", "routines.jar",
		"talend_file_enhanced_20070724.jar"
	};

}