/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.util.comparator;

import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.text.Collator;

import java.util.Locale;

/**
 * @author Petteri Karttunen
 */
public class SXPBlueprintTitleComparator
	extends OrderByComparator<SXPBlueprint> {

	public static final String ORDER_BY_ASC = "SXPBlueprint.title ASC";

	public static final String ORDER_BY_DESC = "SXPBlueprint.title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public SXPBlueprintTitleComparator() {
		this(false);
	}

	public SXPBlueprintTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	public SXPBlueprintTitleComparator(boolean ascending, Locale locale) {
		_ascending = ascending;
		_locale = locale;
	}

	@Override
	public int compare(SXPBlueprint sxpBlueprint1, SXPBlueprint sxpBlueprint2) {
		Collator collator = CollatorUtil.getInstance(_locale);

		String title1 = StringUtil.toLowerCase(sxpBlueprint1.getTitle(_locale));
		String title2 = StringUtil.toLowerCase(sxpBlueprint2.getTitle(_locale));

		int value = collator.compare(title1, title2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private static final long serialVersionUID = 1L;

	private final boolean _ascending;
	private Locale _locale;

}