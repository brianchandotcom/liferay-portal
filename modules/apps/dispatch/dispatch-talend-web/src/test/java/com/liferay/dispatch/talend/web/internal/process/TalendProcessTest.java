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

import com.liferay.dispatch.talend.web.internal.BaseTalend;
import com.liferay.dispatch.talend.web.internal.archive.TalendArchive;
import com.liferay.dispatch.talend.web.internal.archive.TalendArchiveParser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class TalendProcessTest extends BaseTalend {

	@Test
	public void testBuilder() throws Exception {
		TalendProcess.Builder talendProcessBuilder =
			new TalendProcess.Builder();

		talendProcessBuilder.contextParam("parm1", "value1");
		talendProcessBuilder.contextParam("parm2", "value2");
		talendProcessBuilder.contextParam("parm3", "value3");

		long companyId = RandomTestUtil.randomLong();

		talendProcessBuilder.companyId(companyId);

		talendProcessBuilder.lastRunStartDate(null);

		TalendArchiveParser talendArchiveParser = new TalendArchiveParser();

		TalendArchive talendArchive = talendArchiveParser.parse(
			getTalendArchiveInputStream());

		Assert.assertNotNull(talendArchive);

		talendProcessBuilder.talendArchive(talendArchive);

		TalendProcess talendProcess = talendProcessBuilder.build();

		List<String> arguments = talendProcess.getArguments();

		Assert.assertNotNull(arguments);
		Assert.assertEquals(arguments.toString(), 10, arguments.size());

		String command = talendProcess.toString();

		Assert.assertTrue(command.contains(talendArchive.getJobMainClassFQN()));
		Assert.assertTrue(command.contains(talendArchive.getJobJARPath()));

		Assert.assertTrue(
			command.contains(
				String.format(
					TalendProcess.CONTEXT_PARM_COMPANY_ID_TPL, companyId)));

		Assert.assertTrue(
			command.contains(
				String.format(
					TalendProcess.CONTEXT_PARM_JOB_WORK_DIRECTORY_TPL,
					talendArchive.getJobDirectory())));

		Assert.assertFalse(
			command.contains(
				String.format(
					TalendProcess.CONTEXT_PARM_LAST_RUN_START_DATE_TPL,
					StringPool.BLANK)));

		ProcessBuilder processBuilder = new ProcessBuilder();

		processBuilder.directory(new File(talendArchive.getJobDirectory()));

		processBuilder.command(arguments);

		Process process = processBuilder.start();

		process.waitFor();

		Assert.assertTrue(
			Validator.isNull(getContent(process.getErrorStream())));

		String content = getContent(process.getInputStream());

		Assert.assertTrue(Validator.isNotNull(content));

		for (int i = 1; i < 6; i++) {
			String expected = String.format("Test Liferay Value %d", i);

			Assert.assertTrue(
				String.format("Contains '%s", expected),
				content.contains(expected));
		}
	}

}