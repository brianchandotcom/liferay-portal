/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletCategoryComparator
	implements Comparator<PortletCategory>, Serializable {

	public PortletCategoryComparator(Locale locale) {
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(
		PortletCategory portletCategory1, PortletCategory portletCategory2) {

		String name1 = portletCategory1.getName();

		if (name1.equals("category.highlighted")) {
			return -1;
		}

		String name2 = portletCategory2.getName();

		if (name2.equals("category.highlighted")) {
			return 1;
		}

		name1 = LanguageUtil.get(_locale, name1);
		name2 = LanguageUtil.get(_locale, name2);

		return _collator.compare(name1, name2);
	}

	private final Collator _collator;
	private final Locale _locale;

}