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

package com.liferay.portal.xml.xpath;

import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;
import org.jaxen.Navigator;
import org.jaxen.function.StringFunction;

/**
 * @author Raymond Augé
 */
public class MatchesFunction implements Function {

	public static Boolean evaluate(
		Object stringArgument, Object regexArgument, Navigator navigator) {

		String string = StringFunction.evaluate(stringArgument, navigator);
		String regex = StringFunction.evaluate(regexArgument, navigator);

		return evaluate(string, regex, 0, navigator);
	}

	public static Boolean evaluate(
		Object stringArg, Object regexArgument, Object flagsArgument,
		Navigator navigator) {

		String string = StringFunction.evaluate(stringArg, navigator);
		String regex = StringFunction.evaluate(regexArgument, navigator);
		String flagsString = StringFunction.evaluate(flagsArgument, navigator);

		int flags = 0;

		for (int i = 0; i < flagsString.length(); i++) {
			if (flagsString.charAt(i) == 'i') {
				flags &= Pattern.CASE_INSENSITIVE;
			}
			else if (flagsString.charAt(i) == 'm') {
				flags &= Pattern.MULTILINE;
			}
			else if (flagsString.charAt(i) == 's') {
				flags &= Pattern.DOTALL;
			}
			else if (flagsString.charAt(i) == 'x') {
				flags &= Pattern.COMMENTS;
			}
		}

		return evaluate(string, regex, flags, navigator);
	}

	public static Boolean evaluate(
		String string, String regex, int flags, Navigator navigator) {

		ThreadLocalCache<Map<String, Pattern>> threadLocalPatterns =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.ETERNAL, MatchesFunction.class.getName());

		Map<String, Pattern> patterns = threadLocalPatterns.get(
			_THREAD_LOCAL_PATTERNS_KEY);

		if (patterns == null) {
			patterns = new HashMap<>();
		}

		Pattern pattern = patterns.get(regex);

		if (pattern != null) {
			Matcher matcher = pattern.matcher(string);

			return matcher.find();
		}

		pattern = Pattern.compile(regex, flags);

		patterns.put(regex, pattern);

		threadLocalPatterns.put(_THREAD_LOCAL_PATTERNS_KEY, patterns);

		Matcher matcher = pattern.matcher(string);

		return matcher.find();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object call(Context context, List arguments)
		throws FunctionCallException {

		if (arguments.size() == 2) {
			return evaluate(
				arguments.get(0), arguments.get(1), context.getNavigator());
		}
		else if ((arguments.size() == 3) && (arguments.get(2) != null)) {
			return evaluate(
				arguments.get(0), arguments.get(1), arguments.get(2),
				context.getNavigator());
		}

		throw new FunctionCallException(
			"matches() requires two or three arguments");
	}

	private static final String _THREAD_LOCAL_PATTERNS_KEY = "matches";

}