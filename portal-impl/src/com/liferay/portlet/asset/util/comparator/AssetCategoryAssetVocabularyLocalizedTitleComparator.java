/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.util.comparator;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class AssetCategoryAssetVocabularyLocalizedTitleComparator
	implements Comparator<AssetCategory> {

	public AssetCategoryAssetVocabularyLocalizedTitleComparator(
		long vocabularyId, Locale locale, boolean ascending) {

		_vocabularyId = vocabularyId;
		_locale = locale;
		_ascending = ascending;
	}

	@Override
	public int compare(
		AssetCategory assetCategory1, AssetCategory assetCategory2) {

		int value = 0;

		if ((assetCategory1.getVocabularyId() == _vocabularyId) &&
			(assetCategory2.getVocabularyId() != _vocabularyId)) {

			value = -1;
		}
		else if ((assetCategory1.getVocabularyId() != _vocabularyId) &&
				 (assetCategory2.getVocabularyId() == _vocabularyId)) {

			value = 1;
		}
		else if ((_vocabularyId == 0) ||
				 (assetCategory1.getVocabularyId() !=
					 assetCategory2.getVocabularyId())) {

			value = Long.compare(
				assetCategory1.getVocabularyId(),
				assetCategory2.getVocabularyId());
		}

		if (value == 0) {
			Collator collator = CollatorUtil.getInstance(_locale);

			String assetCategoryTitle1 = assetCategory1.getTitle(_locale);
			String assetCategoryTitle2 = assetCategory2.getTitle(_locale);

			value = collator.compare(assetCategoryTitle1, assetCategoryTitle2);
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;
	private final Locale _locale;
	private final long _vocabularyId;

}