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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class PoshiIndentationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int level = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				sb.append(_fixIndentation(line, level));
				sb.append("\n");

				level += getLevel(
					line, new String[] {"(", "{"}, new String[] {")", "}"});
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _fixIndentation(String line, int level) {
		String trimmedLine = StringUtil.trim(line);

		if (Validator.isNull(trimmedLine)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < level; i++) {
			if ((i == (level - 1)) &&
				(trimmedLine.startsWith(")") || trimmedLine.startsWith("}"))) {

				break;
			}

			sb.append(CharPool.TAB);
		}

		sb.append(trimmedLine);

		return sb.toString();
	}

}