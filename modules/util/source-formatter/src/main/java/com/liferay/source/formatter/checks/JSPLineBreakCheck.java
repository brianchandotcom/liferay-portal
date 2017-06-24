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
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Hugo Huijser
 */
public class JSPLineBreakCheck extends LineBreakCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			int lineCount = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				checkLineBreaks(line, previousLine, fileName, lineCount);

				previousLine = line;
			}
		}

		return fixRedundantCommaInsideArray(content);
	}

}