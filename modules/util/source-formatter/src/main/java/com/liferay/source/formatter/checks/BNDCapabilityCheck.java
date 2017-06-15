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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDCapabilityCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatCapabilities(content, "Provide-Capability");
		content = _formatCapabilities(content, "Require-Capability");

		return content;
	}

	private String _fixLineBreaks(
		List<Capability> capabilitiesList, String definitionKey) {

		StringBundler sb = new StringBundler();

		sb.append(definitionKey);
		sb.append(":\\\n");

		for (Capability capability : capabilitiesList) {
			sb.append("\t");
			sb.append(capability.getName());
			sb.append(";\\\n");

			for (String property : capability.getProperties()) {
				sb.append("\t\t");
				sb.append(property);
				sb.append(";\\\n");
			}

			sb.setStringAt(",\\\n", sb.index() - 1);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private String _formatCapabilities(String content, String definitionKey) {
		Pattern pattern = Pattern.compile(
			"^" + definitionKey + ":([\\s\\S]*?([^\\\\]\n|\\Z))",
			Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String match = StringUtil.trim(matcher.group());

		String capabilities = StringUtil.trim(matcher.group(1));

		String singleLineCapabilities = StringUtil.replace(
			capabilities,
			new String[] {StringPool.TAB, definitionKey + ":\\\n", "\\\n"},
			new String[] {
				StringPool.BLANK, definitionKey + ": ", StringPool.BLANK
			});

		List<Capability> capabilitiesList = _sortCapabilities(
			singleLineCapabilities);

		String replacement = _fixLineBreaks(capabilitiesList, definitionKey);

		return StringUtil.replace(content, match, replacement);
	}

	private List<Capability> _sortCapabilities(String capabilities) {
		List<Capability> capabilitiesList = new ArrayList<>();

		Capability capability = null;

		StringBundler sb = new StringBundler();

		for (int i = 0; i < capabilities.length(); i++) {
			char c = capabilities.charAt(i);

			if (ToolsUtil.isInsideQuotes(capabilities, i)) {
				sb.append(c);

				continue;
			}

			if (c == CharPool.COMMA) {
				capability.addProperty(sb.toString());

				capability.sortProperties();

				capabilitiesList.add(capability);

				capability = null;
				sb.setIndex(0);
			}
			else if (c == CharPool.SEMICOLON) {
				if (capability == null) {
					capability = new Capability(sb.toString());
				}
				else {
					capability.addProperty(sb.toString());
				}

				sb.setIndex(0);
			}
			else {
				sb.append(c);
			}
		}

		capability.addProperty(sb.toString());

		capability.sortProperties();

		capabilitiesList.add(capability);

		Collections.sort(capabilitiesList);

		return capabilitiesList;
	}

	private class Capability implements Comparable<Capability> {

		public Capability(String name) {
			_name = name;
		}

		@Override
		public int compareTo(Capability capability) {
			return _name.compareTo(capability.getName());
		}

		protected void addProperty(String property) {
			_properties.add(property);
		}

		protected String getName() {
			return _name;
		}

		protected List<String> getProperties() {
			return _properties;
		}

		protected void sortProperties() {
			Collections.sort(_properties);
		}

		private final String _name;
		private final List<String> _properties = new ArrayList<>();

	}

}