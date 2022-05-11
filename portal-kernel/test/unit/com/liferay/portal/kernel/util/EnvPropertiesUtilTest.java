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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.util.List;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class EnvPropertiesUtilTest {

	@Test
	public void testDecode() {

		// Nothing to decode

		Assert.assertEquals("abcDEF", _decode("abcDEF"));

		// Incompleted encoded content

		Assert.assertEquals("abc_DEF", _decode("abc_DEF"));

		// Encoded with CharPool chars

		Assert.assertEquals(
			"abc:D,^E[F]g_H",
			_decode(
				"abc_COLON_D_COMMA__CARET_E_OPENBRACKET_F_CLOSEBRACKET_" +
					"_LOWERCASEG__UNDERLINE__UPPERCASEH_"));

		// Encoded with unicode chars

		Assert.assertEquals(
			"abc:D,^E[F]", _decode("abc_58_D_44__94_E_91_F_93_"));

		// Encoded with illegal content

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				EnvPropertiesUtil.class.getName(), Level.WARNING)) {

			String s = "abc_xyz_D_-1__DEF__GH";

			Assert.assertEquals(s, _decode(s));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 3, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to decode part \"xyz\" from \"" + s +
					"\", preserve it literally",
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"Unable to decode part \"-1\" from \"" + s +
					"\", preserve it literally",
				logEntry.getMessage());

			logEntry = logEntries.get(2);

			Assert.assertEquals(
				"Unable to decode part \"DEF\" from \"" + s +
					"\", preserve it literally",
				logEntry.getMessage());
		}
	}

	private String _decode(String s) {
		return ReflectionTestUtil.invoke(
			EnvPropertiesUtil.class, "_decode", new Class<?>[] {String.class},
			s);
	}

}