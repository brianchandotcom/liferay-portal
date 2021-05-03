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

package com.liferay.dispatch.executor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Igor Beslic
 */
public class DispatchOutputUtil {

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