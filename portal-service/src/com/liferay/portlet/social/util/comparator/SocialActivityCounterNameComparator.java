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

package com.liferay.portlet.social.util.comparator;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityCounterNameComparator implements Comparator<String> {

	public SocialActivityCounterNameComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(
		String activityCounterName1, String activityCounterName2) {

		String name1 = LanguageUtil.get(
			_locale, "social.counter." + activityCounterName1);
		String name2 = LanguageUtil.get(
			_locale, "social.counter." + activityCounterName2);

		return name1.compareTo(name2);
	}

	private Locale _locale;

}