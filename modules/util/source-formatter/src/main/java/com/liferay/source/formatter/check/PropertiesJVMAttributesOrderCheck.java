/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

import java.util.Arrays;

/**
 * @author Alan Huang
 */
public class PropertiesJVMAttributesOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (Validator.isBlank(line)) {
					sb.append(StringPool.NEW_LINE);

					continue;
				}

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.startsWith(StringPool.POUND)) {
					sb.append(line);
					sb.append(StringPool.NEW_LINE);

					continue;
				}

				int pos = line.indexOf(CharPool.EQUAL);

				if (pos == -1) {
					sb.append(line);
					sb.append(StringPool.NEW_LINE);

					continue;
				}

				String leadingSpace = SourceUtil.getLeadingSpaces(line);

				String name = line.substring(leadingSpace.length(), pos);

				if (!name.endsWith(".jvm.args") && !name.endsWith(".jvm.mem") &&
					!name.endsWith(".jvmargs")) {

					sb.append(line);
					sb.append(StringPool.NEW_LINE);

					continue;
				}

				sb.append(leadingSpace);
				sb.append(name);
				sb.append(StringPool.EQUAL);
				sb.append(_sortAttributes(line.substring(pos + 1)));
				sb.append(StringPool.NEW_LINE);
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		String newContent = sb.toString();

		if (!content.equals(newContent)) {
			return newContent;
		}

		return content;
	}

	private String _sortAttributes(String s) {
		if (Validator.isBlank(s)) {
			return StringPool.BLANK;
		}

		String[] attributes = s.split(StringPool.SPACE);

		Arrays.sort(attributes);

		return StringUtil.merge(attributes, StringPool.SPACE);
	}

}