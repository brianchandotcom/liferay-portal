/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public interface DocumentToSynonymSetTranslator {

	public SynonymSet translate(Document document, String id);

	public SynonymSet translate(SearchHit searchHit);

	public List<SynonymSet> translateAll(List<SearchHit> searchHits);

	public List<SynonymSet> translateAll(SearchHits searchHits);

}