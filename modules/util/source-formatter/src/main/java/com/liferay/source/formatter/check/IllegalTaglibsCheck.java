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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.GitUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.processor.SourceProcessor;

import java.util.List;

/**
 * @author Qi Zhang
 */
public class IllegalTaglibsCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		SourceProcessor sourceProcessor = getSourceProcessor();

		SourceFormatterArgs sourceFormatterArgs =
			sourceProcessor.getSourceFormatterArgs();

		List<String> replacedTaglibs = getAttributeValues(
			_REPLACED_TAGLIBS_KEY, absolutePath);

		if (sourceFormatterArgs.isFormatCurrentBranch() &&
			ListUtil.isNotEmpty(replacedTaglibs)) {

			String currentBranchFileDiff = GitUtil.getCurrentBranchFileDiff(
				sourceFormatterArgs.getBaseDirName(),
				sourceFormatterArgs.getGitWorkingBranchName(), absolutePath);

			for (String line : StringUtil.split(currentBranchFileDiff, "\n")) {
				if (!line.startsWith(StringPool.PLUS)) {
					continue;
				}

				for (String replacedTaglib : replacedTaglibs) {
					String[] replacedTaglibArray = StringUtil.split(
						replacedTaglib, "->");

					if (replacedTaglibArray.length != 3) {
						continue;
					}

					String fileType = replacedTaglibArray[0];
					String oldTaglib = replacedTaglibArray[1];

					if (((fileName.endsWith(".jsp") &&
						  StringUtil.equals(fileType, "jsp")) ||
						 (fileName.endsWith(".ftl") &&
						  StringUtil.equals(fileType, "ftl"))) &&
						line.contains("<" + oldTaglib)) {

						addMessage(
							fileName,
							StringBundler.concat(
								"Do not use following old taglib. '", oldTaglib,
								"' should be replaced by '",
								replacedTaglibArray[2], "', see LPS-179523"));
					}
				}
			}
		}

		return content;
	}

	private static final String _REPLACED_TAGLIBS_KEY = "replacedTaglibs";

}