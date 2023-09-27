/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.prose;

import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

/**
 * @author Yi-Chen Tsai
 */
public class PoshiProseScenario extends BasePoshiProse {

	public PoshiProseScenario(String poshiProseScenarioString) {
		poshiProseScenarioString = filterCommentLines(poshiProseScenarioString);

		Matcher setupMatcher = _setupPattern.matcher(poshiProseScenarioString);

		String tags = null;

		if (setupMatcher.find()) {
			_scenarioContent = setupMatcher.group("content");
			_scenarioName = null;
			_type = Type.Setup;
			tags = setupMatcher.group("tags");
		}
		else {
			Matcher teardownMatcher = _teardownPattern.matcher(
				poshiProseScenarioString);

			if (teardownMatcher.find()) {
				_scenarioContent = teardownMatcher.group("content");
				_scenarioName = null;
				_type = Type.Teardown;
				tags = teardownMatcher.group("tags");
			}
			else {
				Matcher scenarioMatcher = _scenarioPattern.matcher(
					poshiProseScenarioString);

				if (!scenarioMatcher.find()) {
					throw new RuntimeException(
						"Prose scenario does not match pattern " +
							_scenarioPattern.pattern() + "\n" +
								poshiProseScenarioString);
				}

				_scenarioContent = scenarioMatcher.group("content");
				_scenarioName = scenarioMatcher.group("name");
				_type = Type.Scenario;
				tags = scenarioMatcher.group("tags");
			}
		}

		if (tags != null) {
			Matcher tagMatcher = tagPattern.matcher(tags);

			while (tagMatcher.find()) {
				_tagMap.put(
					tagMatcher.group("tagName"), tagMatcher.group("tagValue"));
			}
		}

		List<String> poshiProseStatementStrings = StringUtil.split(
			_scenarioContent, PoshiProseStatement.KEYWORDS);

		for (String poshiProseStatementString : poshiProseStatementStrings) {
			if ((scenarioDescription == null) &&
				!_startsWithProseStatementKeyword(poshiProseStatementString)) {

				scenarioDescription = poshiProseStatementString;

				continue;
			}

			_poshiProseStatements.add(
				new PoshiProseStatement(poshiProseStatementString));
		}
	}

	@Override
	public Element toElement() {
		Element commandElement;

		if (_type == Type.Setup) {
			commandElement = Dom4JUtil.getNewElement("set-up");
		}
		else if (_type == Type.Teardown) {
			commandElement = Dom4JUtil.getNewElement("tear-down");
		}
		else {
			commandElement = Dom4JUtil.getNewElement(
				"command", null, new DefaultAttribute("name", _scenarioName));
		}

		for (Map.Entry<String, String> entry : _tagMap.entrySet()) {
			commandElement.add(
				Dom4JUtil.getNewElement(
					"property", null,
					new DefaultAttribute("name", entry.getKey()),
					new DefaultAttribute("value", entry.getValue())));
		}

		for (PoshiProseStatement poshiProseStatement : _poshiProseStatements) {
			commandElement.add(poshiProseStatement.toElement());
		}

		return commandElement;
	}

	protected static final String[] KEYWORDS = {
		"Setup", "Scenario", "Teardown"
	};

	protected String scenarioDescription;

	protected enum Type {

		Scenario, Setup, Teardown

	}

	private boolean _startsWithProseStatementKeyword(
		String poshiProseStatement) {

		for (String statementKeyWord : PoshiProseStatement.KEYWORDS) {
			if (poshiProseStatement.startsWith(statementKeyWord)) {
				return true;
			}
		}

		return false;
	}

	private static final Pattern _scenarioPattern = Pattern.compile(
		"(?s)Scenario:\\s*(?<name>\\w([ \\w]*\\w)?)\\s*" +
			"(?<tags>(@.*?\".*?\"\\s*)+)*(?<content>[^\\s].*)");
	private static final Pattern _setupPattern = Pattern.compile(
		"(?s)Setup:\\s*(?<tags>(@.*?\".*?\"\\s*)+)*(?<content>[^\\s].*)");
	private static final Pattern _teardownPattern = Pattern.compile(
		"(?s)Teardown:\\s*(?<tags>(@.*?\".*?\"\\s*)+)*(?<content>[^\\s].*)");

	private final List<PoshiProseStatement> _poshiProseStatements =
		new ArrayList<>();
	private final String _scenarioContent;
	private final String _scenarioName;
	private final Map<String, String> _tagMap = new LinkedHashMap<>();
	private final Type _type;

}