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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alan Huang
 */
public class CSSSelectorsOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String trimmedLine = StringUtil.trim(line);

				if (trimmedLine.startsWith(StringPool.AT) ||
					trimmedLine.startsWith(StringPool.DOLLAR) ||
					!trimmedLine.endsWith(StringPool.OPEN_CURLY_BRACE) ||
					!trimmedLine.contains(StringPool.COMMA)) {

					sb.append(line);
					sb.append("\n");

					continue;
				}

				trimmedLine = trimmedLine.replaceAll(" *, *", StringPool.COMMA);
				trimmedLine = trimmedLine.substring(
					0, trimmedLine.length() - 1);

				List<String> selecters = ListUtil.fromString(
					trimmedLine, StringPool.COMMA);

				List<String> oldSelecters = new ArrayList<>(selecters);

				Collections.sort(selecters, new NaturalOrderStringComparator());

				if (oldSelecters.equals(selecters)) {
					sb.append(line);
					sb.append("\n");

					continue;
				}

				sb.append(line.replaceFirst("^([\t ]*).+", "$1"));
				sb.append(ListUtil.toString(selecters, StringPool.BLANK, ", "));
				sb.append(" {\n");
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

}