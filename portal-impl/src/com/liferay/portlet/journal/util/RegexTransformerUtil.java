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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class RegexTransformerUtil {

	public static List<Pattern> getPatterns() {
		return _instance._patterns;
	}

	public static List<String> getReplacements() {
		return _instance._replacements;
	}

	private RegexTransformerUtil() {
		_patterns = new ArrayList<Pattern>();
		_replacements = new ArrayList<String>();

		for (int i = 0; i < 100; i++) {
			String regex = PropsUtil.get(
				"journal.transformer.regex.pattern." + i);
			String replacement = PropsUtil.get(
				"journal.transformer.regex.replacement." + i);

			if (Validator.isNull(regex) || Validator.isNull(replacement)) {
				break;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Pattern " + regex + " will be replaced with " +
						replacement);
			}

			_patterns.add(Pattern.compile(regex));
			_replacements.add(replacement);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RegexTransformerUtil.class);

	private static final RegexTransformerUtil _instance =
		new RegexTransformerUtil();

	private final List<Pattern> _patterns;
	private final List<String> _replacements;

}