/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.GitUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.processor.SourceProcessor;

/**
 * @author Marco Leo
 */
public class JSPLiferayUICSPTagCheck extends BaseFileCheck {

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

		String[] lines = StringUtil.splitLines(
			GitUtil.getCurrentBranchFileDiff(
				sourceFormatterArgs.getBaseDirName(),
				sourceFormatterArgs.getGitWorkingBranchName(), absolutePath));

		for (String line : lines) {
			if (!line.contains("<liferay-ui:csp") ||
				!line.startsWith(StringPool.PLUS)) {

				continue;
			}

			addMessage(
				fileName,
				"Do not use <liferay-ui:csp> tag, use a React component " +
					"instead");
		}

		return content;
	}

}