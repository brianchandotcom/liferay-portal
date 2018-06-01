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

package com.liferay.poshi.runner.prose;

import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

/**
 * @author Yi-Chen Tsai
 */
public class PoshiProseScenario {

	public PoshiProseScenario(String poshiProseScenarioString) {
		Matcher setupMatcher = _setupPattern.matcher(poshiProseScenarioString);

		if (setupMatcher.find()) {
			_scenarioContent = setupMatcher.group("content");
			_scenarioName = null;
			_type = Type.Setup;
		}
		else {
			Matcher teardownMatcher = _teardownPattern.matcher(
				poshiProseScenarioString);

			if (teardownMatcher.find()) {
				_scenarioContent = teardownMatcher.group("content");
				_scenarioName = null;
				_type = Type.Teardown;
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
			}
		}

		List<String> poshiProseStatementStrings = StringUtil.split(
			_scenarioContent, PoshiProseStatement.KEYWORDS);

		for (String poshiProseStatementString : poshiProseStatementStrings) {
			if (poshiProseStatementString.startsWith("#")) {
				continue;
			}

			_poshiProseStatements.add(
				new PoshiProseStatement(poshiProseStatementString));
		}
	}

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

		for (PoshiProseStatement poshiProseStatement : _poshiProseStatements) {
			commandElement.add(poshiProseStatement.toElement());
		}

		return commandElement;
	}

	protected static final String[] KEYWORDS =
		{"Setup", "Scenario", "Teardown"};

	protected enum Type {

		Scenario, Setup, Teardown

	}

	private final List<PoshiProseStatement> _poshiProseStatements =
		new ArrayList<>();
	private final String _scenarioContent;
	private final String _scenarioName;
	private final Pattern _scenarioPattern = Pattern.compile(
		"(?s)Scenario:\\s*(?<name>\\w([ \\w]*\\w)?)\\s*" +
			"(?<content>[^\\s].*)");
	private final Pattern _setupPattern = Pattern.compile(
		"(?s)Setup:\\s*(?<content>[^\\s].*)");
	private final Pattern _teardownPattern = Pattern.compile(
		"(?s)Teardown:\\s*(?<content>[^\\s].*)");
	private final Type _type;

}