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

package com.liferay.xml.formatter;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Brian Wing Shun Chan
 */
public class XMLFormatter {

	public static void main(String[] args) {
		String fileName = System.getProperty("xml.formatter.file");
		boolean stripComments = GetterUtil.getBoolean(
			System.getProperty("xml.formatter.strip.comments"));

		if (Validator.isNull(fileName)) {
			throw new IllegalArgumentException();
		}

		try {
			new XMLFormatter(fileName, stripComments);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public XMLFormatter(String fileName, boolean stripComments)
		throws Exception {

		Path path = Paths.get(fileName);

		String xml = new String(Files.readAllBytes(path), StringPool.UTF8);

		if (stripComments) {
			xml = _stripComments(xml);
		}

		xml = com.liferay.util.xml.Dom4jUtil.toString(xml);

		Files.write(path, xml.getBytes(StringPool.UTF8));
	}

	private String _stripComments(String xml) {
		return StringUtil.stripBetween(xml, "<!--", "-->");
	}

}