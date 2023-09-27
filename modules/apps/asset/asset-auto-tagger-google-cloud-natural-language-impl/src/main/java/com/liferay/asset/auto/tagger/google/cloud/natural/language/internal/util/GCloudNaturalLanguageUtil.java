/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.google.cloud.natural.language.internal.util;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ContentTypes;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;

/**
 * @author Alicia García
 * @author Cristina González
 */
public class GCloudNaturalLanguageUtil {

	public static String getDocumentPayload(String content, String type) {
		return JSONUtil.put(
			"document",
			JSONUtil.put(
				"content", content
			).put(
				"type", type
			)
		).toString();
	}

	public static String getType(String mimeType) {
		if (ContentTypes.TEXT_HTML.equals(mimeType)) {
			return "HTML";
		}

		return "PLAIN_TEXT";
	}

	public static String truncateToSize(String content, int size) {
		byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

		if (bytes.length <= size) {
			return content;
		}

		bytes = Arrays.copyOf(bytes, size);

		int i = size - 1;

		while ((bytes[i] & 0x80) != 0) {
			i--;
		}

		if (i < (size - 1)) {
			bytes = Arrays.copyOf(bytes, i + 1);
		}

		return _truncateToWord(new String(bytes, StandardCharsets.UTF_8));
	}

	private static String _truncateToWord(String content) {
		int i = content.length() - 1;

		while ((i > 0) && !Character.isWhitespace(content.charAt(i))) {
			i--;
		}

		return content.substring(0, i);
	}

}