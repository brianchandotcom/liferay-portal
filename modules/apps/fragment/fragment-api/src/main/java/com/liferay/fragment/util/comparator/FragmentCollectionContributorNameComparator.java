/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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