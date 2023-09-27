/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
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

		String replacement = sb.toString();

		if (StringUtil.count(replacement, "\\\n") == 1) {
			replacement = replacement.replaceFirst(
				"\\\\\n\t", StringPool.SPACE);
		}

		return replacement;
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

		capabilities = _sortFilters(capabilities);
		capabilities = _sortResourceBundleAggregate(capabilities);

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

		if (sb.index() != 0) {
			if (capability == null) {
				capability = new Capability(sb.toString());

				capabilitiesList.add(capability);

				return capabilitiesList;
			}

			capability.addProperty(sb.toString());

			capability.sortProperties();
		}

		capabilitiesList.add(capability);

		Collections.sort(capabilitiesList);

		return capabilitiesList;
	}

	private String _sortFilterConditions(String filter, String... operators) {
		for (String operator : operators) {
			Pattern pattern = Pattern.compile(
				"\\(\\" + operator + "((\\([^()\n]+\\)){2,})\\)");

			Matcher matcher = pattern.matcher(filter);

			while (matcher.find()) {
				String conditions = matcher.group(1);

				List<String> conditionsList = ListUtil.fromArray(
					StringUtil.split(
						conditions.substring(1, conditions.length() - 1),
						")("));

				Collections.sort(conditionsList);

				String sortedConditions = StringBundler.concat(
					StringPool.OPEN_PARENTHESIS,
					ListUtil.toString(conditionsList, StringPool.BLANK, ")("),
					StringPool.CLOSE_PARENTHESIS);

				filter = StringUtil.replaceFirst(
					filter, conditions, sortedConditions, matcher.start());
			}
		}

		return filter;
	}

	private String _sortFilters(String capabilities) {
		Matcher matcher = _filterPattern.matcher(capabilities);

		while (matcher.find()) {
			String filter = matcher.group(1);

			String sortedFilter = _sortFilterConditions(filter, "&", "|");

			if (!filter.equals(sortedFilter)) {
				return StringUtil.replaceFirst(
					capabilities, filter, sortedFilter, matcher.start());
			}
		}

		return capabilities;
	}

	private String _sortResourceBundleAggregate(String capabilities) {
		Matcher matcher = _resourceBundleAggregatePattern.matcher(capabilities);

		if (!matcher.find()) {
			return capabilities;
		}

		String resourceBundleAggregates = matcher.group(1);

		List<String> resourceBundleAggregatesList = ListUtil.fromArray(
			StringUtil.split(resourceBundleAggregates));

		Collections.sort(resourceBundleAggregatesList);

		String sortedResourceBundleAggregates = ListUtil.toString(
			resourceBundleAggregatesList, StringPool.BLANK, StringPool.COMMA);

		return StringUtil.replaceFirst(
			capabilities, resourceBundleAggregates,
			sortedResourceBundleAggregates, matcher.start());
	}

	private static final Pattern _filterPattern = Pattern.compile(
		"\tfilter:=\"(.*?)\"");
	private static final Pattern _resourceBundleAggregatePattern =
		Pattern.compile(
			"\tresource\\.bundle\\.aggregate:String=\"((\\([\\w.=]+\\),?)" +
				"{2,})\"");

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