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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.PortletCategory;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletCategoryComparator
	implements Comparator<PortletCategory>, Serializable {

	public PortletCategoryComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(
		PortletCategory portletCategory1, PortletCategory portletCategory2) {

		String name1 = LanguageUtil.get(_locale, portletCategory1.getName());

		String name2 = LanguageUtil.get(_locale, portletCategory2.getName());

		return name1.compareTo(name2);
	}

	private Locale _locale;

}