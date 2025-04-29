/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.util;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Shin
 * @author Alan Huang
 */
public class YMLSourceUtil {

	public static boolean isBlockStyle(String line) {
		String trimmedLine = StringUtil.trimTrailing(line);

		if (trimmedLine.endsWith(">") || trimmedLine.endsWith(">+") ||
			trimmedLine.endsWith(">-") || trimmedLine.endsWith("|") ||
			trimmedLine.endsWith("|+") || trimmedLine.endsWith("|-")) {

			return true;
		}

		return false;
	}

	public static List<String> splitDocuments(String content) {
		List<String> documents = new ArrayList<>();

		content = content.trim();

		if (content.endsWith("\n---")) {
			content = content.substring(0, content.length() - 4);
		}

		if (content.startsWith("---\n")) {
			content = content.substring(4);
		}

		int x = -1;

		while (true) {
			x = content.lastIndexOf("\n---\n");

			if (x == -1) {
				break;
			}

			String s = content.substring(x + 5);

			documents.add(0, s);

			content = content.substring(0, x);
		}

		documents.add(0, content);

		return documents;
	}

}