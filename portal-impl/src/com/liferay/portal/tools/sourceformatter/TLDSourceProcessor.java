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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class TLDSourceProcessor extends BaseSourceProcessor {

	@Override
	protected void doFormat() throws Exception {
		String[] excludes = new String[] {
			"**\\classes\\**", "**\\bin\\**", "**\\WEB-INF\\tld\\**"
		};
		String[] includes = new String[] {"**\\*.tld"};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			File file = new File(BASEDIR + fileName);

			String content = fileUtil.read(file);

			String newContent = trimContent(content, false);

			if ((newContent != null) && !content.equals(newContent)) {
				fileUtil.write(file, newContent);

				fileName = StringUtil.replace(
					fileName, StringPool.BACK_SLASH, StringPool.SLASH);

				sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

}