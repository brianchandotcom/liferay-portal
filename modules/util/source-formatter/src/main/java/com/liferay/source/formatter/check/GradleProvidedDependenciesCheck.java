/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.GradleSourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class GradleProvidedDependenciesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/build.gradle")) {
			return content;
		}

		String scope = _getScope(content);

		for (String block : _getBlocks(content)) {
			content = _format(content, block, scope);
		}

		return StringUtil.replace(
			content,
			new String[] {"configurations.provided", "extendsFrom provided"},
			new String[] {"configurations." + scope, "extendsFrom " + scope});
	}

	private String _format(String content, String dependencies, String scope) {
		String newDependencies = dependencies.replaceAll(
			"\\bprovided\\b", scope);

		return StringUtil.replace(content, dependencies, newDependencies);
	}

	private List<String> _getBlocks(String content) {
		List<String> blocks = new ArrayList<>();

		Matcher matcher = _blocksPattern.matcher(content);

		while (matcher.find()) {
			blocks.add(matcher.group());
		}

		return blocks;
	}

	private String _getScope(String content) {
		if (GradleSourceUtil.isSpringBootExecutable(content)) {
			return "compile";
		}

		return "compileOnly";
	}

	private static final Pattern _blocksPattern = Pattern.compile(
		"^(configurations|dependencies)\\s*(\\{\\s*\\}|\\{.*?\\n\\})",
		Pattern.DOTALL | Pattern.MULTILINE);

}