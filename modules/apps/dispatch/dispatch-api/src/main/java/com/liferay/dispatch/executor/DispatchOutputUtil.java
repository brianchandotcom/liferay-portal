/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.executor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Igor Beslic
 */
public class DispatchOutputUtil {

	public static String truncate(
		int beginningLinesCount, int endingLinesCount, String output) {

		return truncate(
			beginningLinesCount, endingLinesCount,
			StringBundler.concat(
				"-----------------", StringPool.NEW_LINE,
				"Output was truncated for performance reasons.",
				StringPool.NEW_LINE, "-----------------"),
			output);
	}

	public static String truncate(
		int beginningLinesCount, int endingLinesCount, String message,
		String output) {

		if (Validator.isNull(output)) {
			return output;
		}

		String[] lines = output.split(System.lineSeparator());

		if (((beginningLinesCount + endingLinesCount) * 2) > lines.length) {
			return output;
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < lines.length; i++) {
			if (i < beginningLinesCount) {
				sb.append(lines[i]);
				sb.append(System.lineSeparator());
			}
			else if ((i == beginningLinesCount) &&
					 Validator.isNotNull(message)) {

				sb.append(message);
				sb.append(System.lineSeparator());
			}
			else if (i >= (lines.length - endingLinesCount)) {
				sb.append(lines[i]);

				if ((i + 1) < lines.length) {
					sb.append(System.lineSeparator());
				}
			}
		}

		return sb.toString();
	}

}