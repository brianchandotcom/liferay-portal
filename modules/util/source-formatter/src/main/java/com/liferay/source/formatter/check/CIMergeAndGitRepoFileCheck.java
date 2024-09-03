/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.processor.SourceProcessor;

/**
 * @author Alan Huang
 */
public class CIMergeAndGitRepoFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		SourceProcessor sourceProcessor = getSourceProcessor();

		SourceFormatterArgs sourceFormatterArgs =
			sourceProcessor.getSourceFormatterArgs();

		if (sourceFormatterArgs.isFormatCurrentBranch()) {
			addMessage(
				fileName, "Do not add/modify ci-merge and .gitrepo files");
		}

		return content;
	}

}