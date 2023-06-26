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
import com.liferay.source.formatter.processor.FTLSourceProcessor;
import com.liferay.source.formatter.processor.JSPSourceProcessor;
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

		if (!sourceFormatterArgs.isFormatCurrentBranch()) {
			return content;
		}

		List<String> replacedTaglibs = null;

		if (sourceProcessor instanceof FTLSourceProcessor) {
			replacedTaglibs = getAttributeValues(
				_FTL_REPLACED_TAGLIBS_KEY, absolutePath);
		}
		else if (sourceProcessor instanceof JSPSourceProcessor) {
			replacedTaglibs = getAttributeValues(
				_JSP_REPLACED_TAGLIBS_KEY, absolutePath);
		}

		if (ListUtil.isEmpty(replacedTaglibs)) {
			return content;
		}

		String currentBranchFileDiff = GitUtil.getCurrentBranchFileDiff(
			sourceFormatterArgs.getBaseDirName(),
			sourceFormatterArgs.getGitWorkingBranchName(), absolutePath);

		for (String line : StringUtil.splitLines(currentBranchFileDiff)) {
			if (!line.startsWith(StringPool.PLUS)) {
				continue;
			}

			for (String replacedTaglib : replacedTaglibs) {
				String[] replacedTaglibArray = StringUtil.split(
					replacedTaglib, "->");

				if (replacedTaglibArray.length != 2) {
					continue;
				}

				if (line.endsWith("<" + replacedTaglibArray[0]) ||
					line.contains("<" + replacedTaglibArray[0] + " ")) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Use <", replacedTaglibArray[1],
							"> tag instead of <", replacedTaglibArray[0], ">"));

					break;
				}
			}
		}

		return content;
	}

	private static final String _FTL_REPLACED_TAGLIBS_KEY =
		"ftlReplacedTaglibs";

	private static final String _JSP_REPLACED_TAGLIBS_KEY =
		"jspReplacedTaglibs";

}