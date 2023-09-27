/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					try (InputStream inputStream = new FileInputStream(
							rules.substring(5))) {

						rules = StringUtil.read(inputStream);
					}
				}
				catch (Exception exception) {
					_log.error(exception);

					rules = StringPool.BLANK;
				}
			}

			_rules.put(locale, rules);
		}

		if (!rules.equals(StringPool.BLANK)) {
			try {
				return new RuleBasedCollator(rules);
			}
			catch (ParseException parseException) {
				_log.error(parseException);

				_rules.put(locale, StringPool.BLANK);
			}
		}

		return Collator.getInstance(locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(CollatorUtil.class);

	private static final Map<Locale, String> _rules = new ConcurrentHashMap<>();

}