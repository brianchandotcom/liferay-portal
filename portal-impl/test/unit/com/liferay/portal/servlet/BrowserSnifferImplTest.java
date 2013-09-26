/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BrowserSnifferImplTest {

	@Test
	public void testParseVersion() throws IOException {

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(getClass().getResourceAsStream(
				"dependencies/user_agents.csv")));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			line = line.trim();

			if (line.isEmpty() || (line.charAt(0) == CharPool.POUND)) {
				continue;
			}

			String[] parts = StringUtil.split(line, CharPool.COMMA);

			if (parts.length != 4) {
				continue;
			}

			String userAgent = parts[3].trim();

			Assert.assertEquals(
				parts[0].trim() + " version", parts[1].trim(),
				BrowserSnifferImpl.parseVersion(
					userAgent, BrowserSnifferImpl.versionLeadings,
					BrowserSnifferImpl.versionSeparators));

			Assert.assertEquals(
				parts[0].trim() + " revision", parts[2].trim(),
				BrowserSnifferImpl.parseVersion(
					userAgent, BrowserSnifferImpl.revisionLeadings,
					BrowserSnifferImpl.revisionSeparators));
		}

		unsyncBufferedReader.close();
	}

}