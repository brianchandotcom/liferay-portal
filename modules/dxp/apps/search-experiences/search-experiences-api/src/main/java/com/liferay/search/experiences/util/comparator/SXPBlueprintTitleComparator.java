/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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