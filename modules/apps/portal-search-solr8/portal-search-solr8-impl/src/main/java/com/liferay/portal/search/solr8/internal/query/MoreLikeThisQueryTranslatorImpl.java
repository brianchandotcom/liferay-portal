/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.generic.MoreLikeThisQuery;

import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = MoreLikeThisQueryTranslator.class)
public class MoreLikeThisQueryTranslatorImpl
	implements MoreLikeThisQueryTranslator {

	@Override
	public Query translate(MoreLikeThisQuery moreLikeThisQuery) {
		List<String> fields = moreLikeThisQuery.getFields();

		org.apache.lucene.queries.mlt.MoreLikeThisQuery
			luceneMoreLikeThisQuery =
				new org.apache.lucene.queries.mlt.MoreLikeThisQuery(
					moreLikeThisQuery.getLikeText(),
					fields.toArray(new String[0]), new KeywordAnalyzer(),
					fields.get(0));

		if (moreLikeThisQuery.getMaxQueryTerms() != null) {
			luceneMoreLikeThisQuery.setMaxQueryTerms(
				moreLikeThisQuery.getMaxQueryTerms());
		}

		if (moreLikeThisQuery.getMinDocFrequency() != null) {
			luceneMoreLikeThisQuery.setMinDocFreq(
				moreLikeThisQuery.getMinDocFrequency());
		}

		if (moreLikeThisQuery.getMinTermFrequency() != null) {
			luceneMoreLikeThisQuery.setMinTermFrequency(
				moreLikeThisQuery.getMinTermFrequency());
		}

		Set<String> stopWords = moreLikeThisQuery.getStopWords();

		if (!stopWords.isEmpty()) {
			luceneMoreLikeThisQuery.setStopWords(stopWords);
		}

		if (!moreLikeThisQuery.isDefaultBoost()) {
			return new BoostQuery(
				luceneMoreLikeThisQuery, moreLikeThisQuery.getBoost());
		}

		return luceneMoreLikeThisQuery;
	}

}