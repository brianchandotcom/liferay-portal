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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CopyrightCheck extends BaseFileCheck {

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

		String gitWorkingBranchName =
			sourceFormatterArgs.getGitWorkingBranchName();

		if (gitWorkingBranchName.matches("release-\\d{4}\\.q[1-4]")) {
			return content;
		}

		if (!fileName.endsWith(".tpl") && !fileName.endsWith(".vm")) {
			content = _fixCopyright(
				fileName, absolutePath, content, sourceFormatterArgs);
		}

		return content;
	}

	private String _fixCopyright(
			String fileName, String absolutePath, String content,
			SourceFormatterArgs sourceFormatterArgs)
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

		if ((fileName.endsWith(".java") &&
			 !content.startsWith("/**\n * SPDX-FileCopyrightText: (c) ")) ||
			((fileName.endsWith(".jsp") || fileName.endsWith(".jspf")) &&
			 !content.startsWith(
				 "<%--\n/**\n * SPDX-FileCopyrightText: (c) "))) {

			addMessage(fileName, "File must start with copyright");

			return content;
		}

		for (String currentBranchRenamedFileName :
				sourceFormatterArgs.getCurrentBranchRenamedFileNames()) {

			if (absolutePath.endsWith(currentBranchRenamedFileName)) {
				return content;
			}
		}

		for (String currentBranchAddedFileNames :
				sourceFormatterArgs.getCurrentBranchAddedFileNames()) {

			if (absolutePath.endsWith(currentBranchAddedFileNames)) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy");

				String currentYear = simpleDateFormat.format(new Date());

				String year = s.substring(0, 4);

				if (!year.equals(currentYear)) {
					return StringUtil.replaceFirst(
						content, year, currentYear, x + 35);
				}

				return content;
			}
		}

		for (String currentBranchFileName :
				_getCurrentBranchFileNames(sourceFormatterArgs)) {

			if (!absolutePath.endsWith(currentBranchFileName)) {
				continue;
			}

			Matcher matcher = _copyrightPattern.matcher(
				GitUtil.getCurrentBranchFileDiff(
					sourceFormatterArgs.getBaseDirName(),
					sourceFormatterArgs.getGitWorkingBranchName(),
					absolutePath));

			List<String> years = new ArrayList<>();

			while (matcher.find()) {
				years.add(matcher.group(1));
			}

			if (years.size() != 2) {
				return content;
			}

			if (!StringUtil.equals(years.get(0), years.get(1))) {
				return StringUtil.replaceFirst(
					content,
					"SPDX-FileCopyrightText: (c) " + years.get(1) +
						" Liferay, Inc. https://liferay.com",
					"SPDX-FileCopyrightText: (c) " + years.get(0) +
						" Liferay, Inc. https://liferay.com");
			}
		}

		return content;
	}

	private synchronized List<String> _getCurrentBranchFileNames(
			SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		if (_currentBranchFileNames != null) {
			return _currentBranchFileNames;
		}

		_currentBranchFileNames = GitUtil.getCurrentBranchFileNames(
			sourceFormatterArgs.getBaseDirName(),
			sourceFormatterArgs.getGitWorkingBranchName());

		return _currentBranchFileNames;
	}

	private static final Pattern _copyrightPattern = Pattern.compile(
		"[\\+-] \\* SPDX-FileCopyrightText: \\(c\\) (\\d{4}) Liferay, Inc\\. " +
			"https://liferay\\.com");
	private static List<String> _currentBranchFileNames;

}