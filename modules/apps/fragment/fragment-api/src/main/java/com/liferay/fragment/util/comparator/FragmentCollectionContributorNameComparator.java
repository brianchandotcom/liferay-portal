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

package com.liferay.fragment.util.comparator;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class FragmentCollectionContributorNameComparator
	implements Comparator<FragmentCollectionContributor> {

	public FragmentCollectionContributorNameComparator(Locale locale) {
		_locale = locale;
		_ascending = true;
	}

	public FragmentCollectionContributorNameComparator(
		Locale locale, boolean ascending) {

		_locale = locale;
		_ascending = ascending;
	}

	@Override
	public int compare(
		FragmentCollectionContributor fragmentCollectionContributor1,
		FragmentCollectionContributor fragmentCollectionContributor2) {

		Collator collator = CollatorUtil.getInstance(_locale);

		String localizedValue1 = fragmentCollectionContributor1.getName(
			_locale);
		String localizedValue2 = fragmentCollectionContributor2.getName(
			_locale);

		int value = collator.compare(localizedValue1, localizedValue2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;
	private final Locale _locale;

}