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

package com.liferay.portal.search.lucene.internal.query;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.QueryPreProcessConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = LuceneQueryHelper.class)
public class LuceneQueryHelperImpl implements LuceneQueryHelper {

	@Override
	public void addExactTerm(
		BooleanQuery booleanQuery, String field, boolean value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addExactTerm(
		BooleanQuery booleanQuery, String field, double value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addExactTerm(
		BooleanQuery booleanQuery, String field, int value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addExactTerm(
		BooleanQuery booleanQuery, String field, long value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addExactTerm(
		BooleanQuery booleanQuery, String field, short value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addExactTerm(
		BooleanQuery booleanQuery, String field, String value) {

		addTerm(booleanQuery, field, value, false);
	}

	@Override
	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, Integer startValue,
		Integer endValue) {

		NumericRangeQuery<?> numericRangeQuery = NumericRangeQuery.newIntRange(
			field, startValue, endValue, true, true);

		booleanQuery.add(numericRangeQuery, BooleanClause.Occur.SHOULD);
	}

	@Override
	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, Long startValue,
		Long endValue) {

		NumericRangeQuery<?> numericRangeQuery = NumericRangeQuery.newLongRange(
			field, startValue, endValue, true, true);

		booleanQuery.add(numericRangeQuery, BooleanClause.Occur.SHOULD);
	}

	@Override
	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, short startValue,
		short endValue) {

		addNumericRangeTerm(
			booleanQuery, field, (long)startValue, (long)endValue);
	}

	@Override
	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, Short startValue,
		Short endValue) {

		addNumericRangeTerm(
			booleanQuery, field, GetterUtil.getLong(startValue),
			GetterUtil.getLong(endValue));
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #addNumericRangeTerm(BooleanQuery, String, Long, Long)}
	 */
	@Deprecated
	@Override
	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue) {

		addNumericRangeTerm(
			booleanQuery, field, GetterUtil.getLong(startValue),
			GetterUtil.getLong(endValue));
	}

	@Override
	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, int startValue, int endValue) {

		addRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	@Override
	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, long startValue,
		long endValue) {

		addRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	@Override
	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, Long startValue,
		Long endValue) {

		addRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	@Override
	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue) {

		boolean includesLower = true;

		if ((startValue != null) && startValue.equals(StringPool.STAR)) {
			includesLower = false;
		}

		boolean includesUpper = true;

		if ((endValue != null) && endValue.equals(StringPool.STAR)) {
			includesUpper = false;
		}

		TermRangeQuery termRangeQuery = new TermRangeQuery(
			field, startValue, endValue, includesLower, includesUpper);

		booleanQuery.add(termRangeQuery, BooleanClause.Occur.SHOULD);
	}

	@Override
	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, boolean value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, double value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, int value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, long value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, short value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value) {

		addRequiredTerm(booleanQuery, field, value, false);
	}

	@Override
	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		addRequiredTerm(booleanQuery, field, new String[] {value}, like);
	}

	@Override
	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String[] values,
		boolean like) {

		if (values == null) {
			return;
		}

		BooleanQuery query = new BooleanQuery();

		for (String value : values) {
			addTerm(query, field, value, like);
		}

		booleanQuery.add(query, BooleanClause.Occur.MUST);
	}

	@Override
	public void addTerm(BooleanQuery booleanQuery, String field, long value) {
		addTerm(booleanQuery, field, String.valueOf(value));
	}

	@Override
	public void addTerm(BooleanQuery booleanQuery, String field, String value) {
		addTerm(booleanQuery, field, value, false);
	}

	@Override
	public void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		addTerm(booleanQuery, field, value, like, BooleanClauseOccur.SHOULD);
	}

	@Override
	public void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like,
		BooleanClauseOccur booleanClauseOccur) {

		if (Validator.isNull(value)) {
			return;
		}

		if ((_queryPreProcessConfiguration != null) &&
			_queryPreProcessConfiguration.isSubstringSearchAlways(field)) {

			like = true;
		}

		if (like) {
			value = StringUtil.replace(
				value, StringPool.PERCENT, StringPool.BLANK);
		}

		try {
			QueryParser queryParser = new QueryParser(
				_version, field, _analyzer);

			Query query = queryParser.parse(value);

			BooleanClause.Occur occur = null;

			if (booleanClauseOccur.equals(BooleanClauseOccur.MUST)) {
				occur = BooleanClause.Occur.MUST;
			}
			else if (booleanClauseOccur.equals(BooleanClauseOccur.MUST_NOT)) {
				occur = BooleanClause.Occur.MUST_NOT;
			}
			else {
				occur = BooleanClause.Occur.SHOULD;
			}

			_includeIfUnique(booleanQuery, like, queryParser, query, occur);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	@Override
	public void addTerm(
		BooleanQuery booleanQuery, String field, String[] values,
		boolean like) {

		for (String value : values) {
			addTerm(booleanQuery, field, value, like);
		}
	}

	@Reference
	protected void setAnalyzer(Analyzer analyzer) {
		_analyzer = analyzer;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setQueryPreProcessConfiguration(
		QueryPreProcessConfiguration queryPreProcessConfiguration) {

		_queryPreProcessConfiguration = queryPreProcessConfiguration;
	}

	@Reference
	protected void setVersion(Version version) {
		_version = version;
	}

	protected void unsetQueryPreProcessConfiguration(
		QueryPreProcessConfiguration queryPreProcessConfiguration) {

		_queryPreProcessConfiguration = null;
	}

	private void _includeIfUnique(
		BooleanQuery booleanQuery, boolean like, QueryParser queryParser,
		Query query, BooleanClause.Occur occur) {

		if (query instanceof TermQuery) {
			Set<Term> terms = new HashSet<>();

			TermQuery termQuery = (TermQuery)query;

			termQuery.extractTerms(terms);

			for (Term term : terms) {
				String termValue = term.text();

				if (like &&
					Validator.equals(term.field(), queryParser.getField())) {

					termValue = termValue.toLowerCase(queryParser.getLocale());

					term = term.createTerm(
						StringPool.STAR.concat(termValue).concat(
							StringPool.STAR));

					query = new WildcardQuery(term);
				}
				else {
					query = new TermQuery(term);
				}

				query.setBoost(termQuery.getBoost());

				boolean included = false;

				for (BooleanClause booleanClause : booleanQuery.getClauses()) {
					if (query.equals(booleanClause.getQuery())) {
						included = true;
					}
				}

				if (!included) {
					booleanQuery.add(query, occur);
				}
			}
		}
		else if (query instanceof BooleanQuery) {
			BooleanQuery curBooleanQuery = (BooleanQuery)query;

			BooleanQuery containerBooleanQuery = new BooleanQuery();

			for (BooleanClause booleanClause : curBooleanQuery.getClauses()) {
				_includeIfUnique(
					containerBooleanQuery, like, queryParser,
					booleanClause.getQuery(), booleanClause.getOccur());
			}

			if (containerBooleanQuery.getClauses().length > 0) {
				booleanQuery.add(containerBooleanQuery, occur);
			}
		}
		else {
			boolean included = false;

			for (BooleanClause booleanClause : booleanQuery.getClauses()) {
				if (query.equals(booleanClause.getQuery())) {
					included = true;
				}
			}

			if (!included) {
				booleanQuery.add(query, occur);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LuceneQueryHelperImpl.class);

	private Analyzer _analyzer;
	private QueryPreProcessConfiguration _queryPreProcessConfiguration;
	private Version _version;

}