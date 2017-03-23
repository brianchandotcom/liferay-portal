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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.WhitespaceCheck;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class SQLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			String previousLineSqlCommand = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (Validator.isNotNull(line) &&
					!line.startsWith(StringPool.TAB)) {

					String sqlCommand = StringUtil.split(
						line, CharPool.SPACE)[0];

					if (Validator.isNotNull(previousLineSqlCommand) &&
						!previousLineSqlCommand.equals(sqlCommand)) {

						sb.append("\n");
					}

					previousLineSqlCommand = sqlCommand;
				}
				else {
					previousLineSqlCommand = StringPool.BLANK;
				}

				String strippedQuotesLine = stripQuotes(
					line, CharPool.APOSTROPHE);

				if (strippedQuotesLine.contains(StringPool.QUOTE)) {
					line = StringUtil.replace(
						line, CharPool.QUOTE, CharPool.APOSTROPHE);
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(
			new String[0], filterIncludes(new String[] {"**/sql/*.sql"}));
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<FileCheck> getFileChecks() {
		return _fileChecks;
	}

	@Override
	protected void populateFileChecks() {
		_fileChecks.add(new WhitespaceCheck());
	}

	private static final String[] _INCLUDES = new String[] {"**/*.sql"};

	private final List<FileCheck> _fileChecks = new ArrayList<>();

}