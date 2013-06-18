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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Hugo Huijser
 */
public class JSSourceProcessor extends BaseSourceProcessor {

	@Override
	protected void doFormat() throws Exception {
		String basedir = "./";

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);

		String[] excludes = {
			"**\\js\\aui\\**", "**\\js\\editor\\**", "**\\js\\misc\\**",
			"**\\tools\\**", "**\\VAADIN\\**"
		};

		excludes = ArrayUtil.append(excludes, getExcludes());

		directoryScanner.setExcludes(excludes);

		directoryScanner.setIncludes(new String[] {"**\\*.js"});

		List<String> fileNames = getSourceFormatterHelper().scanForFiles(
			directoryScanner);

		for (String fileName : fileNames) {
			File file = new File(basedir + fileName);

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			String content = getFileUtil().read(file);

			String newContent = trimContent(content, false);

			newContent = StringUtil.replace(
				newContent,
				new String[] {
					"else{", "for(", "function (", "if(", "while(", "){\n",
					"= new Array();", "= new Object();"
				},
				new String[] {
					"else {", "for (", "function(", "if (", "while (", ") {\n",
					"= [];", "= {};"
				});

			Pattern pattern = Pattern.compile("\t+var \\w+\\, ");

			for (;;) {
				Matcher matcher = pattern.matcher(newContent);

				if (!matcher.find()) {
					break;
				}

				String match = matcher.group();

				int pos = match.indexOf("var ");

				StringBundler sb = new StringBundler(4);

				sb.append(match.substring(0, match.length() - 2));
				sb.append(StringPool.SEMICOLON);
				sb.append("\n");
				sb.append(match.substring(0, pos + 4));

				newContent = StringUtil.replace(
					newContent, match, sb.toString());
			}

			if (newContent.endsWith("\n")) {
				newContent = newContent.substring(0, newContent.length() - 1);
			}

			checkLanguageKeys(fileName, newContent, getLanguageKeyPattern());

			if ((newContent != null) && !content.equals(newContent)) {
				getFileUtil().write(file, newContent);

				getSourceFormatterHelper().printError(fileName, file);
			}
		}
	}

}