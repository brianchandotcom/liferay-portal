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

package com.liferay.portal.kernel.util;

import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringPool;

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

	public static StringParser create(String chunk) {
		StringParser stringParser = _stringParserCache.get(chunk);

		if (stringParser == null) {
			stringParser = new StringParser(chunk);

			_stringParserCache.put(chunk, stringParser);
		}

		return stringParser;
	}

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
		Builder builder = null;

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

			if (builder == null) {
				builder = _builderFactory.create();
			}

			builder.setTokenValue(value);
		}

		for (StringParserFragment stringParserFragment :
				_stringParserFragments) {

			parameters.remove(stringParserFragment.getName());
		}

		if (builder == null) {
			return _builderFactory._pattern;
		}

		return builder.toString();
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
	 * </pre></p>
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
	 * </pre></p>
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
	 * </pre></p>
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
	 * </pre></p>
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
	protected StringParser(String pattern) {
		String regex = escapeRegex(pattern);

		Matcher matcher = _fragmentPattern.matcher(pattern);

		_stringParserFragments = new ArrayList<>(matcher.groupCount());

		int pos = 0;

		List<String> builderParts = new ArrayList<>();

		String originalPattern = pattern;

		while (matcher.find()) {
			int start = matcher.start();

			if (pos < start) {
				builderParts.add(originalPattern.substring(pos, start));
			}

			pos = matcher.end();

			builderParts.add(null);

			String chunk = matcher.group();

			StringParserFragment stringParserFragment =
				StringParserFragment.create(chunk);

			_stringParserFragments.add(stringParserFragment);

			pattern = StringUtil.replace(
				pattern, chunk, stringParserFragment.getToken());

			String stringParserFragmentPattern =
				stringParserFragment.getPattern();

			regex = StringUtil.replace(
				regex, escapeRegex(chunk),
				StringPool.OPEN_PARENTHESIS.concat(
					stringParserFragmentPattern.concat(
						StringPool.CLOSE_PARENTHESIS)));
		}

		if (pos < originalPattern.length()) {
			builderParts.add(originalPattern.substring(pos));
		}

		_builderFactory = new BuilderFactory(pattern, builderParts);

		_pattern = Pattern.compile(regex);
	}

	private static final Pattern _escapeRegexPattern = Pattern.compile(
		"[\\{\\}\\(\\)\\[\\]\\*\\+\\?\\$\\^\\.\\#\\\\]");
	private static final Pattern _fragmentPattern = Pattern.compile(
		"\\{.+?\\}");
	private static final Map<String, StringParser> _stringParserCache =
		new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.SOFT_REFERENCE_FACTORY);

	private final BuilderFactory _builderFactory;
	private final Pattern _pattern;
	private StringEncoder _stringEncoder;
	private final List<StringParserFragment> _stringParserFragments;

	private static class Builder {

		public void setTokenValue(String value) {
			if (_parts[_index] == null) {
				_parts[_index++] = value;
			}
			else {
				_index++;

				_parts[_index++] = value;
			}
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(_parts);

			return sb.toString();
		}

		private Builder(String[] parts) {
			_parts = parts;
		}

		private int _index;
		private final String[] _parts;

	}

	private static class BuilderFactory {

		public Builder create() {
			return new Builder(_parts.clone());
		}

		private BuilderFactory(String pattern, List<String> parts) {
			_pattern = pattern;

			if (parts.isEmpty()) {
				_parts = null;
			}
			else {
				_parts = parts.toArray(new String[0]);
			}
		}

		private final String[] _parts;
		private final String _pattern;

	}

}