/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene.highlight;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.WeightedTerm;

/**
 * @author Shuyang Zhou
 */
public class QueryTermExtractor {

	public static WeightedTerm[] getTerms(
		Query query, boolean prohibited, String fieldName) {

		if (query == null) {
			return _emptyWeightedTermArray;
		}

		Set<WeightedTerm> weightedTerms = new HashSet<WeightedTerm>();

		Set<Term> terms = new HashSet<Term>();

		LinkedList<Query> queryStack = new LinkedList<Query>();

		Query lastQuery = query;

		while (lastQuery != null) {

			if (lastQuery instanceof BooleanQuery) {
				BooleanQuery booleanQuery = (BooleanQuery)lastQuery;

				BooleanClause[] booleanQueryClauses = booleanQuery.getClauses();
				for (BooleanClause booleanClause : booleanQueryClauses) {
					if (prohibited ||
						(booleanClause.getOccur() !=
							BooleanClause.Occur.MUST_NOT)) {

						Query clauseQuery = booleanClause.getQuery();

						if (clauseQuery != null) {
							queryStack.addFirst(clauseQuery);
						}
					}
				}

				lastQuery = queryStack.poll();
			}
			else if (lastQuery instanceof FilteredQuery) {
				FilteredQuery filteredQuery = (FilteredQuery)lastQuery;

				lastQuery = filteredQuery.getQuery();

				if (lastQuery == null) {
					lastQuery = queryStack.poll();
				}
			}
			else {
				Class<? extends Query> queryClass = lastQuery.getClass();

				if (!_queryClassBlackList.contains(queryClass)) {
					try {
						lastQuery.extractTerms(terms);

						for (Term term : terms) {
							if ((fieldName == null) ||
								term.field().equals(fieldName)) {

								WeightedTerm weightedTerm =
									new WeightedTerm(
										query.getBoost(), term.text());

								weightedTerms.add(weightedTerm);
							}
						}

						terms.clear();
					}
					catch (UnsupportedOperationException uoe) {
						_queryClassBlackList.addIfAbsent(queryClass);
					}
				}

				lastQuery = queryStack.poll();
			}
		}

		return weightedTerms.toArray(new WeightedTerm[weightedTerms.size()]);
	}

	private static final WeightedTerm[] _emptyWeightedTermArray =
		new WeightedTerm[0];

	private static final CopyOnWriteArrayList<Class<? extends Query>>
		_queryClassBlackList =
			new CopyOnWriteArrayList<Class<? extends Query>>();

}