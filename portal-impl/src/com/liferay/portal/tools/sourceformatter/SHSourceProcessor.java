/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class SHSourceProcessor extends BaseSourceProcessor {

	@Override
	protected void format() throws Exception {
		format("ext/create.sh");
		format("hooks/create.sh");
		format("layouttpl/create.sh");
		format("portlets/create.sh");
		format("themes/create.sh");
	}

	@Override
	protected String format(String fileName) throws IOException {
		File file = new File(fileName);

		if (!file.exists()) {
			return null;
		}

		String content = fileUtil.read(new File(fileName), true);

		if (content.contains("\r")) {
			processErrorMessage(fileName, "Invalid new line character");

			if (isAutoFix()) {
				content = StringUtil.replace(content, "\r", "");

				fileUtil.write(fileName, content);
			}
		}

		return content;
	}

}