/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.evaluatelog;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.FileUtil;

import com.liferay.portal.util.BaseTestCase;

/**
 * @author Brian Wing Shun Chan
 */
public class EvaluateLogTest extends BaseTestCase {

	public void testEvaluateLog() throws Exception {
		assertTrue(evaluateLog());
	}

	private boolean evaluateLog() throws Exception {
		String xml = FileUtil.read("log");

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(xml));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.contains("Exception") && !line.contains("SEVERE")) {
				continue;
			}

			if (line.contains("[antelope:post]")) {
				continue;
			}

			if (line.contains("[junit]")) {
				continue;
			}

			if (line.contains(
					"Resource Destroyer in BasicResourcePool.close()")) {

				continue;
			}

			if (line.matches(
					".*The web application \\[.*\\] appears to have started " +
						"a thread.*")) {

				if (line.contains("[AWT-Windows]")) {
					continue;
				}

				if (line.contains("[com.google.inject.internal.Finalizer]")) {
					continue;
				}
			}

			// LPS-23351

			if (line.contains(
					"user lacks privilege or object not found: LOCK_")) {

				continue;
			}

			System.out.println("\nException Line:\n\n" + line + "\n");

			return false;
		}

		return true;
	}

}