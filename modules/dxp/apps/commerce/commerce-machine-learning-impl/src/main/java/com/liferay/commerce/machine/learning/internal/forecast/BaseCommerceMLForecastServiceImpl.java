/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.forecast;

import com.liferay.commerce.machine.learning.forecast.CommerceMLForecast;
import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastField;
import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastPeriod;
import com.liferay.commerce.machine.learning.internal.search.api.CommerceMLIndexer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import java.text.DateFormat;
import java.text.ParseException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseCommerceMLForecastServiceImpl
	<T extends CommerceMLForecast> {

	protected T addCommerceMLForecast(T model) throws PortalException {
		Document document = toDocumentModel(model);

		document.addKeyword(Field.UID, String.valueOf(model.getForecastId()));

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			commerceMLIndexer.getIndexName(model.getCompanyId()), document);

		IndexDocumentResponse indexDocumentResponse =
			searchEngineAdapter.execute(indexDocumentRequest);

		if ((indexDocumentResponse.getStatus() < 200) ||
			(indexDocumentResponse.getStatus() >= 300)) {

			throw new PortalException(
				String.format(
					"Index request return status: %d",
					indexDocumentResponse.getStatus()));
		}

		return model;
	}

	protected BooleanQuery getBooleanQuery(
		String scope, String period, String target) {

		return new BooleanQueryImpl() {
			{
				setPreBooleanFilter(_getBooleanFilter(scope, period, target));
			}
		};
	}

	protected BooleanQuery getBooleanQuery(
			String scope, String period, String target, Date startDate,
			Date endDate)
		throws com.liferay.portal.kernel.search.ParseException {

		return new BooleanQueryImpl() {
			{
				BooleanFilter booleanFilter = _getBooleanFilter(
					scope, period, target);

				booleanFilter.add(
					new RangeTermFilter(
						CommerceMLForecastField.TIMESTAMP, true, true,
						_formatSearchDate(startDate),
						_formatSearchDate(endDate)),
					BooleanClauseOccur.MUST);

				setPreBooleanFilter(booleanFilter);
			}
		};
	}

	protected T getCommerceMLForecast(long companyId, long forecastId)
		throws PortalException {

		TermFilter termFilter = new TermFilter(
			CommerceMLForecastField.FORECAST_ID, String.valueOf(forecastId));

		List<T> searchResults = getSearchResults(
			getSearchSearchRequest(
				commerceMLIndexer.getIndexName(companyId),
				new BooleanQueryImpl() {
					{
						setPreBooleanFilter(
							new BooleanFilter() {
								{
									add(termFilter, BooleanClauseOccur.MUST);
								}
							});
					}
				},
				0, 1, getDefaultSort(true)));

		if (searchResults.isEmpty()) {
			return null;
		}

		return searchResults.get(0);
	}

	protected T getCommerceMLForecastModel(
		T commerceMLForecast, Document document) {

		commerceMLForecast.setActual(
			GetterUtil.getFloat(
				document.get(CommerceMLForecastField.ACTUAL), Float.MIN_VALUE));
		commerceMLForecast.setCompanyId(
			GetterUtil.getLong(document.get(Field.COMPANY_ID)));
		commerceMLForecast.setForecast(
			GetterUtil.getFloat(
				document.get(CommerceMLForecastField.FORECAST),
				Float.MIN_VALUE));
		commerceMLForecast.setForecastId(
			GetterUtil.getLong(
				document.get(CommerceMLForecastField.FORECAST_ID)));
		commerceMLForecast.setForecastLowerBound(
			GetterUtil.getFloat(
				document.get(CommerceMLForecastField.FORECAST_LOWER_BOUND)));
		commerceMLForecast.setForecastUpperBound(
			GetterUtil.getFloat(
				document.get(CommerceMLForecastField.FORECAST_UPPER_BOUND)));
		commerceMLForecast.setJobId(
			document.get(CommerceMLForecastField.JOB_ID));
		commerceMLForecast.setScope(
			document.get(CommerceMLForecastField.SCOPE));
		commerceMLForecast.setPeriod(
			document.get(CommerceMLForecastField.PERIOD));
		commerceMLForecast.setTarget(
			document.get(CommerceMLForecastField.TARGET));

		try {
			commerceMLForecast.setTimestamp(
				document.getDate(CommerceMLForecastField.TIMESTAMP));
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException);
			}
		}

		return commerceMLForecast;
	}

	protected long getCountResult(CountSearchRequest countSearchRequest) {
		CountSearchResponse countSearchResponse = searchEngineAdapter.execute(
			countSearchRequest);

		return countSearchResponse.getCount();
	}

	protected CountSearchRequest getCountSearchRequest(
		String indexName, Query query) {

		return new CountSearchRequest() {
			{
				setIndexNames(new String[] {indexName});
				setQuery(query);
			}
		};
	}

	protected Sort[] getDefaultSort(boolean reverse) {
		Sort sort = SortFactoryUtil.create(
			CommerceMLForecastField.TIMESTAMP.concat(SORTABLE_FIELD_SUFFIX),
			reverse);

		return new Sort[] {sort};
	}

	protected Document getDocument(T commerceMLForecast) {
		Document document = new DocumentImpl();

		document.addNumber(
			CommerceMLForecastField.ACTUAL, commerceMLForecast.getActual());
		document.addNumber(Field.COMPANY_ID, commerceMLForecast.getCompanyId());
		document.addNumber(
			CommerceMLForecastField.FORECAST, commerceMLForecast.getForecast());
		document.addNumber(
			CommerceMLForecastField.FORECAST_ID,
			commerceMLForecast.getForecastId());
		document.addNumber(
			CommerceMLForecastField.FORECAST_LOWER_BOUND,
			commerceMLForecast.getForecastLowerBound());
		document.addNumber(
			CommerceMLForecastField.FORECAST_UPPER_BOUND,
			commerceMLForecast.getForecastUpperBound());
		document.addText(
			CommerceMLForecastField.JOB_ID, commerceMLForecast.getJobId());
		document.addText(
			CommerceMLForecastField.PERIOD, commerceMLForecast.getPeriod());
		document.addText(
			CommerceMLForecastField.SCOPE, commerceMLForecast.getScope());
		document.addText(
			CommerceMLForecastField.TARGET, commerceMLForecast.getTarget());
		document.addDate(
			CommerceMLForecastField.TIMESTAMP,
			commerceMLForecast.getTimestamp());

		return document;
	}

	protected Date getEndDate(
		Date endDate, CommerceMLForecastPeriod commerceMLForecastPeriod,
		int stepCount) {

		LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(
			endDate.toInstant(), DEFAULT_ZONE_OFFSET);

		endLocalDateTime = endLocalDateTime.truncatedTo(ChronoUnit.DAYS);

		if (commerceMLForecastPeriod.equals(CommerceMLForecastPeriod.MONTH)) {
			endLocalDateTime = endLocalDateTime.with(
				ChronoField.DAY_OF_MONTH, 1);

			endLocalDateTime = endLocalDateTime.plusMonths(stepCount);
		}
		else {
			endLocalDateTime = endLocalDateTime.with(
				ChronoField.DAY_OF_WEEK, 1);

			endLocalDateTime = endLocalDateTime.plusWeeks(stepCount);
		}

		return _toDate(endLocalDateTime);
	}

	protected long getHash(Object... values) {
		StringBundler sb = new StringBundler(values.length);

		for (Object value : values) {
			sb.append(value);
		}

		return HashUtil.hash(values.length, sb.toString());
	}

	protected List<T> getSearchResults(
		SearchSearchRequest searchSearchRequest) {

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		return TransformUtil.transform(
			_getDocuments(searchSearchResponse.getHits()),
			this::toForecastModel);
	}

	protected SearchSearchRequest getSearchSearchRequest(
		String indexName, Query query, int start, int size, Sort[] sorts) {

		return new SearchSearchRequest() {
			{
				setIndexNames(new String[] {indexName});
				setQuery(query);
				setSize(Integer.valueOf(size));
				setSorts(sorts);
				setStart(Integer.valueOf(start));
				setStats(Collections.emptyMap());
			}
		};
	}

	protected Date getStartDate(
		Date startDate, CommerceMLForecastPeriod commerceMLForecastPeriod,
		int stepCount) {

		LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(
			startDate.toInstant(), DEFAULT_ZONE_OFFSET);

		startLocalDateTime = startLocalDateTime.truncatedTo(ChronoUnit.DAYS);

		if (commerceMLForecastPeriod.equals(CommerceMLForecastPeriod.MONTH)) {
			startLocalDateTime = startLocalDateTime.with(
				ChronoField.DAY_OF_MONTH, 1);

			startLocalDateTime = startLocalDateTime.minusMonths(stepCount);
		}
		else {
			startLocalDateTime = startLocalDateTime.with(
				ChronoField.DAY_OF_WEEK, 1);

			startLocalDateTime = startLocalDateTime.minusWeeks(stepCount);
		}

		return _toDate(startLocalDateTime);
	}

	protected abstract Document toDocumentModel(T model);

	protected abstract T toForecastModel(Document document);

	protected static final ZoneId DEFAULT_ZONE_OFFSET =
		ZoneOffset.systemDefault();

	protected static final String SORTABLE_FIELD_SUFFIX = "_sortable";

	@Reference(
		target = "(component.name=com.liferay.commerce.machine.learning.internal.forecast.search.index.CommerceMLForecastIndexer)"
	)
	protected volatile CommerceMLIndexer commerceMLIndexer;

	@Reference
	protected volatile SearchEngineAdapter searchEngineAdapter;

	private String _formatSearchDate(Date searchDate) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_INDEX_DATE_FORMAT_PATTERN);

		return dateFormat.format(searchDate);
	}

	private BooleanFilter _getBooleanFilter(
		String scope, String period, String target) {

		return new BooleanFilter() {
			{
				add(
					new TermFilter(CommerceMLForecastField.PERIOD, period),
					BooleanClauseOccur.MUST);
				add(
					new TermFilter(CommerceMLForecastField.SCOPE, scope),
					BooleanClauseOccur.MUST);
				add(
					new TermFilter(CommerceMLForecastField.TARGET, target),
					BooleanClauseOccur.MUST);
			}
		};
	}

	private List<Document> _getDocuments(Hits hits) {
		List<Document> list = new ArrayList<>(hits.toList());

		Map<String, Hits> groupedHits = hits.getGroupedHits();

		for (Map.Entry<String, Hits> entry : groupedHits.entrySet()) {
			list.addAll(_getDocuments(entry.getValue()));
		}

		return list;
	}

	private Date _toDate(LocalDateTime localDateTime) {
		ZonedDateTime zonedDateTime = localDateTime.atZone(DEFAULT_ZONE_OFFSET);

		return Date.from(zonedDateTime.toInstant());
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = "yyyyMMddHHmmss";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCommerceMLForecastServiceImpl.class);

}