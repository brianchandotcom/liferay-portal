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

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.processor.SourceProcessor;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Hugo Huijser
 */
public class CopyrightCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith(".tpl") && !fileName.endsWith(".vm")) {
			content = _fixCopyright(fileName, absolutePath, content);
		}

		return content;
	}

	private String _fixCopyright(
			String fileName, String absolutePath, String content)
		throws IOException {

		int x = content.indexOf("/**\n * SPDX-FileCopyrightText: © ");

		if (x == -1) {
			addMessage(fileName, "Missing copyright");

			return content;
		}

		String s = content.substring(31, content.indexOf("\n", 31));

		if (!s.matches("© \\d{4} Liferay, Inc\\. https://liferay\\.com")) {
			addMessage(fileName, "Missing copyright");

			return content;
		}

		if (!content.startsWith("/**\n * SPDX-FileCopyrightText: © ") &&
			!content.startsWith("<%--\n/**\n * SPDX-FileCopyrightText: © ") &&
			!content.startsWith(
				_XML_DECLARATION +
					"<!--\n/**\n * SPDX-FileCopyrightText: © ")) {

			addMessage(fileName, "File must start with copyright");

			return content;
		}

		SourceProcessor sourceProcessor = getSourceProcessor();

		SourceFormatterArgs sourceFormatterArgs =
			sourceProcessor.getSourceFormatterArgs();

		if (sourceFormatterArgs.isFormatCurrentBranch()) {
			String rootDirName = SourceUtil.getRootDirName(absolutePath);

			if (Validator.isNull(rootDirName) ||
				Validator.isNotNull(
					getPortalContent(
						absolutePath.substring(rootDirName.length()),
						absolutePath, true))) {

				return content;
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

			String currentYear = simpleDateFormat.format(new Date());

			String year = content.substring(33, 37);

			if (!year.equals(currentYear)) {
				content = StringUtil.replaceFirst(
					content, year, currentYear, 33);
			}
		}

		return content;
	}

	private static final String _XML_DECLARATION =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

}