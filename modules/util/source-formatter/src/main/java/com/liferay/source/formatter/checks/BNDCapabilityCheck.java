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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.tools.ToolsUtil;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDCapabilityCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		content = _formatCapability(content, "Provide-Capability");
		content = _formatCapability(content, "Require-Capability");

		return new Tuple(content, Collections.emptySet());
	}

	private String _formatCapability(String content, String definitionKey) {
		Pattern pattern = Pattern.compile(
			"^" + definitionKey + ":[\\s\\S]*?([^\\\\]\n|\\Z)",
			Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String match = matcher.group();

		String replacement = StringUtil.replace(match, ": ", ":\\\n\t");

		outerLoop:
		while (true) {
			matcher = _capabilityLineBreakPattern1.matcher(replacement);

			while (matcher.find()) {
				if (!ToolsUtil.isInsideQuotes(replacement, matcher.start())) {
					replacement = StringUtil.replaceFirst(
						replacement, ",", ",\\\n\t", matcher.start());

					continue outerLoop;
				}
			}

			break;
		}

		outerLoop:
		while (true) {
			matcher = _capabilityLineBreakPattern2.matcher(replacement);

			while (matcher.find()) {
				if (!ToolsUtil.isInsideQuotes(replacement, matcher.start())) {
					replacement = StringUtil.replaceFirst(
						replacement, ";", ";\\\n\t\t", matcher.start());

					continue outerLoop;
				}
			}

			break;
		}

		return StringUtil.replace(content, match, replacement);
	}

	private final Pattern _capabilityLineBreakPattern1 = Pattern.compile(
		",[^\\\\]");
	private final Pattern _capabilityLineBreakPattern2 = Pattern.compile(
		";[^\\\\]");

}