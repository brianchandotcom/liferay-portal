/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public class DocumentToSynonymSetTranslatorUtil {

	public static SynonymSet translate(
		Document document, String synonymSetDocumentId) {

		return builder(
		).synonymSetDocumentId(
			synonymSetDocumentId
		).synonyms(
			document.getString(SynonymSetFields.SYNONYMS)
		).build();
	}

	public static SynonymSet translate(SearchHit searchHit) {
		return translate(searchHit.getDocument(), searchHit.getId());
	}

	public static List<SynonymSet> translateAll(List<SearchHit> searchHits) {
		return TransformUtil.transform(
			searchHits, DocumentToSynonymSetTranslatorUtil::translate);
	}

	public static List<SynonymSet> translateAll(SearchHits searchHits) {
		return translateAll(searchHits.getSearchHits());
	}

	protected static SynonymSet.SynonymSetBuilder builder() {
		return new SynonymSet.SynonymSetBuilder();
	}

}