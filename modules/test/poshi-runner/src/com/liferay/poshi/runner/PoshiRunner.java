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

package com.liferay.poshi.runner;

import java.io.File;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 * @author Karen Dang
 */
public class PoshiRunner extends TestCase {

	public void testPoshiRunner() throws Exception {
		File file = new File(
			"../../../portal-web/test/functional/com/liferay/portalweb" +
				"/development/tools/testinginfrastructure");

		_findFiles(file);

		System.out.println("Test " + file.exists());
	}

	private void _findFiles(File file) throws Exception {
		if (file.isDirectory()) {
			for (File childFile : file.listFiles()) {
				_findFiles(childFile);
			}
		}
		else {
			_parseFile(file);
		}
	}

	private void _parseFile(File file) throws Exception {
		SAXReader saxReader = new SAXReader();

		Document document = saxReader.read(file);
	}

}