/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.search.filter;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.headless.delivery.dynamic.data.mapping.DDMStructureField;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.DateRangeTermFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterVisitor;
import com.liferay.portal.kernel.search.filter.GeoBoundingBoxFilter;
import com.liferay.portal.kernel.search.filter.GeoDistanceFilter;
import com.liferay.portal.kernel.search.filter.GeoDistanceRangeFilter;
import com.liferay.portal.kernel.search.filter.GeoPolygonFilter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
import com.liferay.portal.kernel.search.filter.PrefixFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.search.generic.TermRangeQueryImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;

import java.util.List;
import java.util.function.Function;

/**
 * @author Javier de Arcos
 */
public class FilterUtil {

	public static Filter processFilter(DDMIndexer ddmIndexer, Filter filter) {
		if ((filter == null) || ddmIndexer.isLegacyDDMIndexFieldsEnabled()) {
			return filter;
		}

		return filter.accept(_ddmFilterVisitor);
	}

	private static final Log _log = LogFactoryUtil.getLog(FilterUtil.class);

	private static final FilterVisitor<Filter> _ddmFilterVisitor =
		new DDMFilterVisitor();

	private static class DDMFilterVisitor implements FilterVisitor<Filter> {

		@Override
		public Filter visit(BooleanFilter booleanFilter1) {
			BooleanFilter booleanFilter2 = new BooleanFilter();

			_addBooleanClauses(
				booleanFilter1.getMustBooleanClauses(), BooleanClauseOccur.MUST,
				booleanFilter2);
			_addBooleanClauses(
				booleanFilter1.getMustNotBooleanClauses(),
				BooleanClauseOccur.MUST_NOT, booleanFilter2);
			_addBooleanClauses(
				booleanFilter1.getShouldBooleanClauses(),
				BooleanClauseOccur.SHOULD, booleanFilter2);

			return booleanFilter2;
		}

		@Override
		public Filter visit(DateRangeTermFilter dateRangeTermFilter) {
			return dateRangeTermFilter;
		}

		@Override
		public Filter visit(ExistsFilter existsFilter) {
			return existsFilter;
		}

		@Override
		public Filter visit(GeoBoundingBoxFilter geoBoundingBoxFilter) {
			return geoBoundingBoxFilter;
		}

		@Override
		public Filter visit(GeoDistanceFilter geoDistanceFilter) {
			return geoDistanceFilter;
		}

		@Override
		public Filter visit(GeoDistanceRangeFilter geoDistanceRangeFilter) {
			return geoDistanceRangeFilter;
		}

		@Override
		public Filter visit(GeoPolygonFilter geoPolygonFilter) {
			return geoPolygonFilter;
		}

		@Override
		public Filter visit(MissingFilter missingFilter) {
			return missingFilter;
		}

		@Override
		public Filter visit(PrefixFilter prefixFilter) {
			return prefixFilter;
		}

		@Override
		public Filter visit(QueryFilter queryFilter) {
			Query query = queryFilter.getQuery();

			if (query instanceof TermQuery) {
				TermQuery termQuery = (TermQuery)query;

				QueryTerm queryTerm = termQuery.getQueryTerm();

				return _createNestedQueryFilter(
					queryTerm.getField(), queryFilter,
					nestedFieldName -> new TermQueryImpl(
						nestedFieldName, queryTerm.getValue()));
			}
			else if (query instanceof TermRangeQuery) {
				TermRangeQuery termRangeQuery = (TermRangeQuery)query;

				return _createNestedQueryFilter(
					termRangeQuery.getField(), queryFilter,
					nestedFieldName -> new TermRangeQueryImpl(
						nestedFieldName, termRangeQuery.getLowerTerm(),
						termRangeQuery.getUpperTerm(),
						termRangeQuery.includesLower(),
						termRangeQuery.includesUpper()));
			}
			else if (query instanceof WildcardQuery) {
				WildcardQuery wildcardQuery = (WildcardQuery)query;

				QueryTerm queryTerm = wildcardQuery.getQueryTerm();

				return _createNestedQueryFilter(
					queryTerm.getField(), queryFilter,
					nestedFieldName -> new WildcardQueryImpl(
						nestedFieldName, queryTerm.getValue()));
			}

			return queryFilter;
		}

		@Override
		public Filter visit(RangeTermFilter rangeTermFilter) {
			return _createNestedQueryFilter(
				rangeTermFilter.getField(), rangeTermFilter,
				nestedFieldName -> new TermRangeQueryImpl(
					nestedFieldName, rangeTermFilter.getLowerBound(),
					rangeTermFilter.getUpperBound(),
					rangeTermFilter.isIncludesLower(),
					rangeTermFilter.isIncludesUpper()));
		}

		@Override
		public Filter visit(TermFilter termFilter) {
			return _createNestedQueryFilter(
				termFilter.getField(), termFilter,
				nestedFieldName -> new TermQueryImpl(
					nestedFieldName, termFilter.getValue()));
		}

		@Override
		public Filter visit(TermsFilter termsFilter) {
			return termsFilter;
		}

		private void _addBooleanClauses(
			List<BooleanClause<Filter>> booleanClauses,
			BooleanClauseOccur booleanClauseOccur,
			BooleanFilter booleanFilter) {

			for (BooleanClause<Filter> booleanClause : booleanClauses) {
				Filter filter = booleanClause.getClause();

				booleanFilter.add(filter.accept(this), booleanClauseOccur);
			}
		}

		private Filter _createNestedQueryFilter(
			String fieldName, Filter originalQueryFilter,
			Function<String, Query> queryFunction) {

			if (!fieldName.startsWith(DDMIndexer.DDM_FIELD_PREFIX)) {
				return originalQueryFilter;
			}

			try {
				BooleanQuery booleanQuery = new BooleanQueryImpl();

				DDMStructureField ddmStructureField = DDMStructureField.from(
					fieldName);

				booleanQuery.add(
					queryFunction.apply(
						ddmStructureField.
							getDDMStructureNestedTypeSortableFieldName()),
					BooleanClauseOccur.MUST);
				booleanQuery.addRequiredTerm(
					StringBundler.concat(
						DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
						DDMIndexer.DDM_FIELD_NAME),
					ddmStructureField.getDDMStructureFieldName());

				return new QueryFilter(
					new NestedQuery(DDMIndexer.DDM_FIELD_ARRAY, booleanQuery));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				return originalQueryFilter;
			}
		}

	}

}