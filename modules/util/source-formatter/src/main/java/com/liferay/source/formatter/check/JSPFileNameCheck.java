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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

/**
 * @author Hugo Huijser
 */
public class JSPFileNameCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith(".jsp") && !fileName.endsWith(".jspf")) {
			return content;
		}

		int x = absolutePath.lastIndexOf(CharPool.SLASH);
		int y = absolutePath.lastIndexOf(CharPool.PERIOD);

		String shortFileName = absolutePath.substring(x + 1, y);

		if (shortFileName.endsWith("-compat") ||
			shortFileName.endsWith("-ext")) {

			return content;
		}

		for (char c : shortFileName.toCharArray()) {
			if (!Character.isLetterOrDigit(c) && (c != CharPool.UNDERLINE)) {
				addMessage(
					fileName,
					StringBundler.concat("Do not use '", c, "' in file name"));
			}
		}

		return content;
	}

}