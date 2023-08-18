/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.GitUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.processor.SourceProcessor;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class CopyrightCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".tpl") && !fileName.endsWith(".vm")) {
			content = _fixCopyright(fileName, absolutePath, content);
		}

		return content;
	}

	private String _fixCopyright(
			String fileName, String absolutePath, String content)
		throws Exception {

		int x = content.indexOf("/**\n * SPDX-FileCopyrightText: (c) ");

		if (x == -1) {
			addMessage(fileName, "Missing copyright");

			return content;
		}

		String s = content.substring(x + 35, content.indexOf("\n", x + 35));

		if (!s.matches("\\d{4} Liferay, Inc\\. https://liferay\\.com")) {
			addMessage(fileName, "Missing copyright");

			return content;
		}

		if (!content.startsWith("/**\n * SPDX-FileCopyrightText: (c) ") &&
			!content.startsWith("<%--\n/**\n * SPDX-FileCopyrightText: (c) ") &&
			!content.startsWith(
				_XML_DECLARATION +
					"<!--\n/**\n * SPDX-FileCopyrightText: (c) ")) {

			addMessage(fileName, "File must start with copyright");

			return content;
		}

		SourceProcessor sourceProcessor = getSourceProcessor();

		SourceFormatterArgs sourceFormatterArgs =
			sourceProcessor.getSourceFormatterArgs();

		for (String currentBranchRenamedFileName :
				_getCurrentBranchRenamedFileNames(sourceFormatterArgs)) {

			if (absolutePath.endsWith(currentBranchRenamedFileName)) {
				return content;
			}
		}

		for (String currentBranchAddedFileNames :
				_getCurrentBranchAddedFileName(sourceFormatterArgs)) {

			if (absolutePath.endsWith(currentBranchAddedFileNames)) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy");

				String currentYear = simpleDateFormat.format(new Date());

				String year = s.substring(0, 4);

				if (!year.equals(currentYear)) {
					return StringUtil.replaceFirst(
						content, year, currentYear, x + 35);
				}
			}
		}

		return content;
	}

	private synchronized List<String> _getCurrentBranchAddedFileName(
			SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		if (_currentBranchAddedFileNames != null) {
			return _currentBranchAddedFileNames;
		}

		_currentBranchAddedFileNames = GitUtil.getCurrentBranchAddedFileNames(
			sourceFormatterArgs.getBaseDirName(),
			sourceFormatterArgs.getGitWorkingBranchName());

		return _currentBranchAddedFileNames;
	}

	private synchronized List<String> _getCurrentBranchRenamedFileNames(
			SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		if (_currentBranchRenamedFileNames != null) {
			return _currentBranchRenamedFileNames;
		}

		_currentBranchRenamedFileNames =
			GitUtil.getCurrentBranchRenamedFileNames(
				sourceFormatterArgs.getBaseDirName(),
				sourceFormatterArgs.getGitWorkingBranchName());

		return _currentBranchRenamedFileNames;
	}

	private static final String _XML_DECLARATION =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

	private static List<String> _currentBranchAddedFileNames;
	private static List<String> _currentBranchRenamedFileNames;

}