/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.parser.GradleFile;

import java.util.Collection;
import java.util.Set;

/**
 * @author Peter Shin
 */
public class GradleBlockOrderCheck extends BaseGradleFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, GradleFile gradleFile,
		String content) {

		if (absolutePath.contains("/project-templates-")) {
			return content;
		}

		StringBundler sb = new StringBundler(16);

		sb.append(gradleFile.getImportsBlock());

		String buildScriptBlock = gradleFile.getBuildScriptBlock();

		if (Validator.isNotNull(buildScriptBlock)) {
			sb.append(buildScriptBlock);
			sb.append("\n\n");
		}

		String pluginsScriptBlock = gradleFile.getPluginsScriptBlock();

		if (Validator.isNotNull(pluginsScriptBlock)) {
			sb.append(pluginsScriptBlock);
			sb.append("\n\n");
		}

		Set<String> applyPlugins = gradleFile.getApplyPlugins();

		if (!applyPlugins.isEmpty()) {
			sb.append(_merge(applyPlugins, "\n"));
			sb.append("\n\n");
		}

		String extScriptBlock = gradleFile.getExtScriptBlock();

		if (Validator.isNotNull(extScriptBlock)) {
			sb.append(extScriptBlock);
			sb.append("\n\n");
		}

		String initializeBlock = gradleFile.getInitializeBlock();

		if (Validator.isNotNull(initializeBlock)) {
			sb.append(initializeBlock);
			sb.append("\n\n");
		}

		Set<String> tasks = gradleFile.getTasks();

		if (!tasks.isEmpty()) {
			sb.append(_merge(tasks, "\n"));
			sb.append("\n\n");
		}

		Set<String> properties = gradleFile.getProperties();

		if (!properties.isEmpty()) {
			sb.append(_merge(properties, "\n"));
			sb.append("\n\n");
		}

		sb.append(gradleFile.getBodyBlock());

		return StringUtil.trim(sb.toString());
	}

	private String _merge(Collection<String> lines, String delimiter) {
		if (lines == null) {
			return null;
		}

		if (lines.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * lines.size());

		for (String line : lines) {
			sb.append(line);
			sb.append(delimiter);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

}