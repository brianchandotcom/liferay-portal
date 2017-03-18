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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.util.Collections;

/**
 * @author Hugo Huijser
 */
public class FTLWhitespaceCheck extends WhitespaceCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		content = _formatWhitespace(fileName, content);

		return new Tuple(content, Collections.emptySet());
	}

	private String _formatWhitespace(String fileName, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = trimLine(fileName, line);

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.startsWith("<#assign ")) {
					line = formatWhitespace(line, trimmedLine, true);

					line = formatIncorrectSyntax(line, "=[", "= [", false);
					line = formatIncorrectSyntax(line, "+[", "+ [", false);
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

}