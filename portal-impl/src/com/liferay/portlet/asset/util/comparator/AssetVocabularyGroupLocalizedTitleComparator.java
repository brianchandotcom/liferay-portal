/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.util.comparator;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Roberto Díaz
 */
public class AssetVocabularyGroupLocalizedTitleComparator
	implements Comparator<AssetVocabulary> {

	public AssetVocabularyGroupLocalizedTitleComparator(
		long groupId, Locale locale, boolean ascending) {

		_groupId = groupId;
		_locale = locale;
		_ascending = ascending;
	}

	@Override
	public int compare(
		AssetVocabulary assetVocabulary1, AssetVocabulary assetVocabulary2) {

		int value = 0;

		if ((assetVocabulary1.getGroupId() == _groupId) &&
			(assetVocabulary2.getGroupId() != _groupId)) {

			value = -1;
		}
		else if ((assetVocabulary1.getGroupId() != _groupId) &&
				 (assetVocabulary2.getGroupId() == _groupId)) {

			value = 1;
		}
		else if ((_groupId == 0) ||
				 (assetVocabulary1.getGroupId() !=
					 assetVocabulary2.getGroupId())) {

			value = Long.compare(
				assetVocabulary1.getGroupId(), assetVocabulary2.getGroupId());
		}

		if (value == 0) {
			Collator collator = CollatorUtil.getInstance(_locale);

			String assetVocabularyTitle1 = assetVocabulary1.getTitle(_locale);
			String assetVocabularyTitle2 = assetVocabulary2.getTitle(_locale);

			value = collator.compare(
				assetVocabularyTitle1, assetVocabularyTitle2);
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;
	private final long _groupId;
	private final Locale _locale;

}