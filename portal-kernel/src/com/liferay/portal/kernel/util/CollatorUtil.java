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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.FileInputStream;
import java.io.InputStream;

import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Preston Crary
 */
public class CollatorUtil {

	public static Collator getInstance(Locale locale) {
		String rules = _rules.get(locale);

		if (rules == null) {
			String languageId = LocaleUtil.toLanguageId(locale);

			rules = PropsUtil.get("collator.rules", new Filter(languageId));

			if (Validator.isNull(rules)) {
				rules = StringPool.BLANK;
			}
			else if (rules.startsWith("file:")) {
				try {
					try (InputStream is = new FileInputStream(
							rules.substring(5))) {

						rules = StringUtil.read(is);
					}
				}
				catch (Exception e) {
					_log.error(e, e);

					rules = StringPool.BLANK;
				}
			}

			_rules.put(locale, rules);
		}

		if (!rules.equals(StringPool.BLANK)) {
			try {
				return new RuleBasedCollator(rules);
			}
			catch (ParseException pe) {
				_log.error(pe, pe);

				_rules.put(locale, StringPool.BLANK);
			}
		}

		return Collator.getInstance(locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(CollatorUtil.class);

	private static final Map<Locale, String> _rules = new ConcurrentHashMap<>();

}