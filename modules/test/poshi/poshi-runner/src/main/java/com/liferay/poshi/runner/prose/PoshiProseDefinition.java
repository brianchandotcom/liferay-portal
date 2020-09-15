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
import com.liferay.poshi.runner.util.Validator;

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
public class PoshiProseDefinition extends BasePoshiProse {

	public PoshiProseDefinition(String fileName, String fileContent) {
		_fileName = fileName;

		fileContent = filterCommentLines(fileContent);

		Matcher matcher = _definitionPattern.matcher(fileContent);

		if (matcher.find()) {
			String tags = matcher.group("tags");

			if (Validator.isNotNull(tags)) {
				Matcher tagMatcher = tagPattern.matcher(tags);

				while (tagMatcher.find()) {
					_tagMap.put(
						tagMatcher.group("tagName"),
						tagMatcher.group("tagValue"));
				}
			}

			String scenarios = matcher.group("scenarios");

			List<String> poshiProseScenarioStrings = StringUtil.split(
				scenarios, PoshiProseScenario.KEYWORDS);

			for (String poshiProseScenarioString : poshiProseScenarioStrings) {
				_poshiProseScenarios.add(
					new PoshiProseScenario(poshiProseScenarioString));
			}
		}
	}

	public String getFileName() {
		return _fileName;
	}

	@Override
	public Element toElement() {
		Element definitionElement = Dom4JUtil.getNewElement("definition");

		for (Map.Entry<String, String> entry : _tagMap.entrySet()) {
			definitionElement.add(
				Dom4JUtil.getNewElement(
					"property", null,
					new DefaultAttribute("name", entry.getKey()),
					new DefaultAttribute("value", entry.getValue())));
		}

		for (PoshiProseScenario poshiProseScenario : _poshiProseScenarios) {
			definitionElement.add(poshiProseScenario.toElement());
		}

		return definitionElement;
	}

	private static final Pattern _definitionPattern = Pattern.compile(
		"(?s)(?<tags>\\@.*?)?(?<feature>Feature:.*?)?" +
			"(?<scenarios>(Setup|Teardown|Scenario).*)");

	private final String _fileName;
	private final List<PoshiProseScenario> _poshiProseScenarios =
		new ArrayList<>();
	private final Map<String, String> _tagMap = new LinkedHashMap<>();

}