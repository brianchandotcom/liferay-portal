/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.prose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Yoo
 */
public class PoshiProseMatcher {

	public static PoshiProseMatcher getPoshiProseMatcher(String poshiProse) {
		String key = _toString(poshiProse);

		return poshiProseMatcherMap.get(key);
	}

	public static void storePoshiProseMatcher(
			String poshiProse, String macroNamespacedClassCommandName)
		throws Exception {

		List<String> possiblePoshiProseStrings = _getPossiblePoshiProseStrings(
			poshiProse);

		for (String possiblePoshiProseString : possiblePoshiProseStrings) {
			String key = _toString(possiblePoshiProseString);

			PoshiProseMatcher ppm = poshiProseMatcherMap.get(key);

			if ((ppm != null) &&
				!macroNamespacedClassCommandName.equals(
					ppm.getMacroNamespacedClassCommandName())) {

				StringBuilder sb = new StringBuilder();

				sb.append("Duplicate prose '");
				sb.append(key);
				sb.append("' already exists for ");

				sb.append(ppm.getMacroNamespacedClassCommandName());

				sb.append("\n in ");
				sb.append(macroNamespacedClassCommandName);

				throw new RuntimeException(sb.toString());
			}

			poshiProseMatcherMap.put(
				key,
				new PoshiProseMatcher(
					poshiProse, macroNamespacedClassCommandName));
		}
	}

	public String getMacroNamespacedClassCommandName() {
		return _macroNamespacedClassCommandName;
	}

	public String getPoshiProse() {
		return _poshiProse;
	}

	public List<String> getVarNames() {
		return _varNames;
	}

	@Override
	public String toString() {
		return _toString(_poshiProse);
	}

	protected static final Map<String, PoshiProseMatcher> poshiProseMatcherMap =
		Collections.synchronizedMap(new HashMap<>());

	private static List<String> _getPossibleAlternateStrings(
		String proseString) {

		List<String> possibleAlternateStrings = new ArrayList<>();

		if (proseString == null) {
			return possibleAlternateStrings;
		}

		Matcher alternateTextMatcher = _alternateTextPattern.matcher(
			proseString);

		if (alternateTextMatcher.find()) {
			List<String> possiblePrefixes = new ArrayList<>();

			possiblePrefixes.add(
				alternateTextMatcher.group(1) +
					alternateTextMatcher.group("alternateText1"));

			possiblePrefixes.add(
				alternateTextMatcher.group(1) +
					alternateTextMatcher.group("alternateText2"));

			List<String> possiblePostfixes = _getPossibleAlternateStrings(
				alternateTextMatcher.group(4));

			for (String possiblePrefix : possiblePrefixes) {
				for (String possiblePostfix : possiblePostfixes) {
					possibleAlternateStrings.add(
						possiblePrefix + possiblePostfix);
				}
			}
		}
		else {
			possibleAlternateStrings.add(proseString);
		}

		return possibleAlternateStrings;
	}

	private static List<String> _getPossibleOptionalStrings(
		String proseString) {

		List<String> possibleOptionalStrings = new ArrayList<>();

		if (proseString == null) {
			return possibleOptionalStrings;
		}

		Matcher optionalTextMatcher = _optionalTextPattern.matcher(proseString);

		if (optionalTextMatcher.find()) {
			List<String> possiblePrefixes = new ArrayList<>();

			possiblePrefixes.add(optionalTextMatcher.group(1));

			possiblePrefixes.add(
				optionalTextMatcher.group(1) +
					optionalTextMatcher.group("optionalText"));

			List<String> possiblePostfixes = _getPossiblePoshiProseStrings(
				optionalTextMatcher.group(3));

			for (String possiblePrefix : possiblePrefixes) {
				for (String possibleSecondPartString : possiblePostfixes) {
					possibleOptionalStrings.add(
						possiblePrefix + possibleSecondPartString);
				}
			}
		}
		else {
			possibleOptionalStrings.add(proseString);
		}

		return possibleOptionalStrings;
	}

	private static List<String> _getPossiblePoshiProseStrings(
		String proseString) {

		List<String> possiblePoshiProseStrings = new ArrayList<>();

		if (proseString == null) {
			return possiblePoshiProseStrings;
		}

		List<String> possibleAlternateStrings = _getPossibleAlternateStrings(
			proseString);

		for (String possibleAlternateString : possibleAlternateStrings) {
			possiblePoshiProseStrings.addAll(
				_getPossibleOptionalStrings(possibleAlternateString));
		}

		return possiblePoshiProseStrings;
	}

	private static String _toString(String matchingString) {
		return matchingString.replaceAll("\\$\\{.+?\\}", "\"\"");
	}

	private PoshiProseMatcher(
		String poshiProse, String macroNamespacedClassCommandName) {

		_poshiProse = poshiProse;
		_macroNamespacedClassCommandName = macroNamespacedClassCommandName;

		Matcher matcher = _poshiProseVarPattern.matcher(_poshiProse);

		while (matcher.find()) {
			_varNames.add(matcher.group(1));
		}
	}

	private static final Pattern _alternateTextPattern = Pattern.compile(
		"(.*?)(?<alternateText1>\\w+)\\/(?<alternateText2>\\w+)(.*)");
	private static final Pattern _optionalTextPattern = Pattern.compile(
		"(.*?)\\((?<optionalText>.*?)\\)(.*)");
	private static final Pattern _poshiProseVarPattern = Pattern.compile(
		"\\$\\{(.+?)\\}");

	private final String _macroNamespacedClassCommandName;
	private final String _poshiProse;
	private final List<String> _varNames = new ArrayList<>();

}