/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses strings into parameter maps and vice versa.
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.kernel.portlet.Route
 * @see    Pattern
 */
public class StringParser {

	/**
	 * Escapes the special characters in the string so that they will have no
	 * special meaning in a regular expression.
	 *
	 * <p>
	 * This method differs from {@link Pattern#quote(String)} by escaping each
	 * special character with a backslash, rather than enclosing the entire
	 * string in special quote tags. This allows the escaped string to be
	 * manipulated or have sections replaced with non-literal sequences.
	 * </p>
	 *
	 * @param  s the string to escape
	 * @return the escaped string
	 */
	public static String escapeRegex(String s) {
		Matcher matcher = _escapeRegexPattern.matcher(s);

		return matcher.replaceAll("\\\\$0");
	}

	/**
	 * Constructs a new string parser from the pattern.
	 *
	 * <p>
	 * The pattern can be any string containing named fragments in brackets. The
	 * following is a valid pattern for greeting:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * Hi {name}! How are you?
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * <p>
	 * This pattern would match the string &quot;Hi Tom! How are you?&quot;. The
	 * format of a fragment may optionally be specified by inserting a colon
	 * followed by a regular expression after the fragment name. For instance,
	 * <code>name</code> could be set to match only lower case letters with the
	 * following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * Hi {name:[a-z]+}! How are you?
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * <p>
	 * By default, a fragment will match anything except a forward slash or a
	 * period.
	 * </p>
	 *
	 * <p>
	 * If a string parser is set to encode fragments using a {@link
	 * StringEncoder}, an individual fragment can be specified as raw by
	 * prefixing its name with a percent sign, as shown below:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /view_page/{%path:.*}
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * <p>
	 * The format of the path fragment has also been specified to match anything
	 * using the pattern &quot;.*&quot;. This pattern could be used to parse the
	 * string:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /view_page/root/home/mysite/pages/index.htm
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * <p>
	 * <code>path</code> would be set to
	 * &quot;root/home/mysite/pages/index.htm&quot;, even if {@link
	 * URLStringEncoder} had been set as the string encoder.
	 * </p>
	 *
	 * <p>
	 * <b>Do not include capturing subgroups in the pattern.</b>
	 * </p>
	 *
	 * @param pattern the pattern string
	 */
	public StringParser(String pattern) {
		_builder = pattern;

		String regex = escapeRegex(pattern);

		Matcher matcher = _fragmentPattern.matcher(pattern);

		while (matcher.find()) {
			String chunk = matcher.group();

			StringParserFragment stringParserFragment =
				new StringParserFragment(chunk);

			_stringParserFragments.add(stringParserFragment);

			_builder = _builder.replace(chunk, stringParserFragment.getToken());

			regex = regex.replace(
				escapeRegex(chunk),
				StringPool.OPEN_PARENTHESIS.concat(
					stringParserFragment.getPattern().concat(
						StringPool.CLOSE_PARENTHESIS)));
		}

		_pattern = Pattern.compile(regex);
	}

	/**
	 * Builds a string from the parameter map if this parser is appropriate.
	 *
	 * <p>
	 * A parser is appropriate if each parameter matches the format of its
	 * accompanying fragment.
	 * </p>
	 *
	 * <p>
	 * If this parser is appropriate, all the parameters used in the pattern
	 * will be removed from the parameter map. If this parser is not
	 * appropriate, the parameter map will not be modified.
	 * </p>
	 *
	 * @param  parameters the parameter map to build the string from
	 * @return the string, or <code>null</code> if this parser is not
	 *         appropriate
	 */
	public String build(Map<String, String> parameters) {
		String s = _builder;

		for (StringParserFragment stringParserFragment :
				_stringParserFragments) {

			String value = parameters.get(stringParserFragment.getName());

			if (value == null) {
				return null;
			}

			if ((_stringEncoder != null) && !stringParserFragment.isRaw()) {
				value = _stringEncoder.encode(value);
			}

			if (!stringParserFragment.matches(value)) {
				return null;
			}

			s = s.replace(stringParserFragment.getToken(), value);
		}

		for (StringParserFragment stringParserFragment :
				_stringParserFragments) {

			parameters.remove(stringParserFragment.getName());
		}

		return s;
	}

	/**
	 * Populates the parameter map with values parsed from the string if this
	 * parser matches.
	 *
	 * @param  s the string to parse
	 * @param  parameters the parameter map to populate if this parser matches
	 *         the string
	 * @return <code>true</code> if this parser matches; <code>false</code>
	 *         otherwise
	 */
	public boolean parse(String s, Map<String, String> parameters) {
		Matcher matcher = _pattern.matcher(s);

		if (!matcher.matches()) {
			return false;
		}

		for (int i = 1; i <= _stringParserFragments.size(); i++) {
			StringParserFragment stringParserFragment =
				_stringParserFragments.get(i - 1);

			String value = matcher.group(i);

			if ((_stringEncoder != null) && !stringParserFragment.isRaw()) {
				value = _stringEncoder.decode(value);
			}

			parameters.put(stringParserFragment.getName(), value);
		}

		return true;
	}

	/**
	 * Sets the string encoder to use for parsing or building a string.
	 *
	 * <p>
	 * The string encoder will not be used for fragments marked as raw. A
	 * fragment can be marked as raw by prefixing its name with a percent sign.
	 * </p>
	 *
	 * @param stringEncoder the string encoder to use for parsing or building a
	 *        string
	 * @see   StringEncoder
	 */
	public void setStringEncoder(StringEncoder stringEncoder) {
		_stringEncoder = stringEncoder;
	}

	private static Pattern _escapeRegexPattern = Pattern.compile(
		"[\\{\\}\\(\\)\\[\\]\\*\\+\\?\\$\\^\\.\\#\\\\]");
	private static Pattern _fragmentPattern = Pattern.compile("\\{.+?\\}");

	private String _builder;
	private Pattern _pattern;
	private StringEncoder _stringEncoder;
	private List<StringParserFragment> _stringParserFragments =
		new ArrayList<StringParserFragment>();

}