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

package com.liferay.dispatch.talend.web.internal.process;

import com.liferay.dispatch.talend.web.internal.BaseTalendTestCase;
import com.liferay.dispatch.talend.web.internal.archive.TalendArchive;
import com.liferay.dispatch.talend.web.internal.archive.TalendArchiveParser;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class TalendProcessTest extends BaseTalendTestCase {

	@Test
	public void testBuilder() throws Exception {
		TalendProcess.Builder talendProcessBuilder =
			new TalendProcess.Builder();

		long companyId = RandomTestUtil.randomLong();

		talendProcessBuilder.companyId(companyId);

		talendProcessBuilder.contextParam("JAVA_OPTS", "-Xms2G -Xmx512M -Xint");

		UnicodeProperties unicodeProperties =
			RandomTestUtil.randomUnicodeProperties(
				RandomTestUtil.randomInt(5, 10),
				RandomTestUtil.randomInt(5, 10),
				RandomTestUtil.randomInt(5, 10));

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			talendProcessBuilder.contextParam(entry.getKey(), entry.getValue());
		}

		talendProcessBuilder.lastRunStartDate(null);
		talendProcessBuilder.liferayLibGlobalDirectory(_getLibraryPath());

		TalendArchiveParser talendArchiveParser = new TalendArchiveParser();

		TalendArchive talendArchive = talendArchiveParser.parse(
			getTalendArchiveInputStream());

		talendProcessBuilder.talendArchive(talendArchive);

		TalendProcess talendProcess = talendProcessBuilder.build();

		List<String> arguments = talendProcess.getMainMethodArguments();

		Assert.assertEquals(
			arguments.toString(), unicodeProperties.size() + 3,
			arguments.size());

		Assert.assertTrue(
			arguments.contains(
				"--context_param companyId=".concat(
					String.valueOf(companyId))));

		Assert.assertTrue(
			arguments.contains(
				"--context_param jobWorkDirectory=".concat(
					talendArchive.getJobDirectory())));

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			Assert.assertTrue(
				arguments.contains(
					StringBundler.concat(
						"--context_param ", entry.getKey(), StringPool.EQUAL,
						entry.getValue())));
		}

		Stream<String> stream = arguments.stream();

		Assert.assertTrue(
			stream.noneMatch(
				argument -> argument.startsWith(
					"--context_param lastRunStartDate=")));

		ProcessConfig processConfig = talendProcess.getProcessConfig();

		List<String> processConfigArguments = processConfig.getArguments();

		Assert.assertEquals(
			processConfigArguments.toString(), 3,
			processConfigArguments.size());

		Assert.assertTrue(processConfigArguments.contains("-Xint"));
		Assert.assertTrue(processConfigArguments.contains("-Xms2G"));
		Assert.assertTrue(processConfigArguments.contains("-Xmx512M"));

		Assert.assertTrue(
			Validator.isNotNull(processConfig.getRuntimeClassPath()));

		Date date = RandomTestUtil.nextDate();

		talendProcessBuilder.lastRunStartDate(date);

		talendProcess = talendProcessBuilder.build();

		processConfigArguments = talendProcess.getMainMethodArguments();

		stream = processConfigArguments.stream();

		Assert.assertTrue(
			stream.anyMatch(
				argument -> argument.startsWith(
					"--context_param lastRunStartDate=")));
	}

	private String _getLibraryPath() {
		Class<? extends TalendProcessTest> clazz = getClass();

		ProtectionDomain protectionDomain = clazz.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL locationURL = codeSource.getLocation();

		return locationURL.getPath();
	}

}