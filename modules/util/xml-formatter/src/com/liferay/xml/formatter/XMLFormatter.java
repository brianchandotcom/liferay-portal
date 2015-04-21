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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
public class XMLFormatter {

	public static void main(String[] args) {
		ToolDependencies.wireBasic();

		String fileName = System.getProperty("xml.formatter.file");
		boolean stripComments = GetterUtil.getBoolean(
			System.getProperty("xml.formatter.strip.comments"));

		if (Validator.isNull(fileName)) {
			throw new IllegalArgumentException();
		}

		try {
			String xml = FileUtil.read(fileName);

			if (stripComments) {
				xml = HtmlUtil.stripComments(xml);
			}

			xml = com.liferay.util.xml.XMLFormatter.toString(xml);

			FileUtil.write(fileName, xml);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}