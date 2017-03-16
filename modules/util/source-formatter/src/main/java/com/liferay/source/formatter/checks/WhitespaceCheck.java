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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.util.Collections;

/**
 * @author Hugo Huijser
 */
public class WhitespaceCheck extends BaseFileCheck {

	public WhitespaceCheck() {
		this(false);
	}

	public WhitespaceCheck(boolean allowLeadingSpaces) {
		_allowLeadingSpaces = allowLeadingSpaces;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				sb.append(_trimLine(fileName, line));
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return new Tuple(content, Collections.emptySet());
	}

	protected boolean isAllowLeadingSpaces(String fileName) {
		return _allowLeadingSpaces;
	}

	private String _trimLine(String fileName, String line) {
		if (line.trim().length() == 0) {
			return StringPool.BLANK;
		}

		line = StringUtil.trimTrailing(line);

		if (isAllowLeadingSpaces(fileName) || line.startsWith(" *")) {
			return line;
		}

		while (line.matches("^\t*" + StringPool.FOUR_SPACES + ".*")) {
			line = StringUtil.replaceFirst(
				line, StringPool.FOUR_SPACES, StringPool.TAB);
		}

		while (line.startsWith(StringPool.SPACE)) {
			line = StringUtil.replaceFirst(
				line, CharPool.SPACE, StringPool.BLANK);
		}

		return line;
	}

	private final boolean _allowLeadingSpaces;

}