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

package com.liferay.portal.kernel.search;

import com.liferay.portal.util.PortletKeys;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author Michael C. Han
 */
public class QueryIndexingHitsProcessor implements HitsProcessor {

	@Override
	public boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		float[] scores = hits.getScores();

		if ((scores != null) && (scores.length != 0)) {
			Arrays.sort(scores);

			if (scores[0] >= _scoringThreshold) {
				indexKeywordQuery(
					searchContext.getCompanyId(), searchContext.getKeywords(),
					searchContext.getLocale());
			}
		}

		return true;
	}

	public void setDocumentPrototype(Document documentPrototype) {
		_documentPrototype = documentPrototype;
	}

	public void setScoringThreshold(float scoringThreshold) {
		_scoringThreshold = scoringThreshold;
	}

	protected void indexKeywordQuery(
			long companyId, String keywords, Locale locale)
		throws SearchException {

		Document document = (Document) _documentPrototype.clone();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.KEYWORD_SEARCH, keywords);
		document.addKeyword(Field.LOCALE, locale.toString());
		document.addKeyword(Field.PORTLET_ID, PortletKeys.SEARCH);

		SearchEngineUtil.addDocument(
			SearchEngineUtil.getDefaultSearchEngineId(), companyId, document);
	}

	private static final float _DEFAULT_SCORING_THRESHOLD = 0.5f;

	private Document _documentPrototype;
	private float _scoringThreshold = _DEFAULT_SCORING_THRESHOLD;

}