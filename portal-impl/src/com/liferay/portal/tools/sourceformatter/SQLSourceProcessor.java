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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class SQLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected void doFormat() throws Exception {
		String[] includes = new String[] {"**\\sql\\*.sql"};

		List<String> fileNames = getFileNames(new String[0], includes);

		for (String fileName : fileNames) {
			File file = new File(BASEDIR + fileName);

			String content = fileUtil.read(file);

			String newContent = formatSQL(content);

			if ((newContent != null) && !content.equals(newContent)) {
				fileUtil.write(file, newContent);

				fileName = StringUtil.replace(
					fileName, StringPool.BACK_SLASH, StringPool.SLASH);

				sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	protected String formatSQL(String content) throws IOException {
		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		String line = null;

		String previousLineSqlCommand = StringPool.BLANK;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			line = trimLine(line, false);

			if (Validator.isNotNull(line) && !line.startsWith(StringPool.TAB)) {
				String sqlCommand = StringUtil.split(line, CharPool.SPACE)[0];

				if (Validator.isNotNull(previousLineSqlCommand) &&
					!previousLineSqlCommand.equals(sqlCommand)) {

					sb.append("\n");
				}

				previousLineSqlCommand = sqlCommand;
			}
			else {
				previousLineSqlCommand = StringPool.BLANK;
			}

			sb.append(line);
			sb.append("\n");
		}

		unsyncBufferedReader.close();

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

}