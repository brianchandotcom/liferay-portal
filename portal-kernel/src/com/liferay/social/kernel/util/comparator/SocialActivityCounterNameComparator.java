/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.util.comparator;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityCounterNameComparator implements Comparator<String> {

	public SocialActivityCounterNameComparator(Locale locale) {
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(
		String activityCounterName1, String activityCounterName2) {

		String name1 = LanguageUtil.get(
			_locale, "social.counter." + activityCounterName1);
		String name2 = LanguageUtil.get(
			_locale, "social.counter." + activityCounterName2);

		return _collator.compare(name1, name2);
	}

	private final Collator _collator;
	private final Locale _locale;

}