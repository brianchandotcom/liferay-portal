/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregationResult;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.RangeAggregationResult;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Histogram;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Metric;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.MetricResource;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Inácio Nery
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/metric.properties",
	scope = ServiceScope.PROTOTYPE, service = MetricResource.class
)
public class MetricResourceImpl extends BaseMetricResourceImpl {

	@Override
	public Metric getProcessMetric(
			Long processId, Date dateEnd, Date dateStart, String unit)
		throws Exception {

		Metric metric = new Metric();

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(
			dateEnd.toInstant(), ZoneId.of(_user.getTimeZoneId()));

		LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(
			dateStart.toInstant(), ZoneId.of(_user.getTimeZoneId()));

		DateRangeAggregation dateRangeAggregation = _createDateRangeAggregation(
			endLocalDateTime, startLocalDateTime);

		DateHistogramAggregation dateHistogramAggregation =
			_aggregations.dateHistogram("completionDate", "completionDate");

		dateHistogramAggregation.setDateHistogramInterval(
			_getDateHistogramInterval(unit));

		if (Objects.equals(unit, Metric.Unit.WEEKS.getValue())) {
			dateHistogramAggregation.setOffset(-86400000L);
		}

		dateRangeAggregation.addChildAggregation(dateHistogramAggregation);

		searchSearchRequest.addAggregation(dateRangeAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-instances");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", contextCompany.getCompanyId()),
				_queries.term("completed", Boolean.TRUE.toString()),
				_queries.term("deleted", Boolean.FALSE.toString()),
				_queries.term("processId", processId)));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		RangeAggregationResult rangeAggregationResult =
			(RangeAggregationResult)aggregationResultsMap.get("completionDate");

		Bucket bucket = rangeAggregationResult.getBucket("current");

		Map<String, AggregationResult> childrenAggregationResults =
			bucket.getChildrenAggregationResults();

		DateHistogramAggregationResult dateHistogramAggregationResult =
			(DateHistogramAggregationResult)childrenAggregationResults.get(
				"completionDate");

		Collection<Histogram> histograms = _createHistograms(
			dateHistogramAggregationResult.getBuckets(), endLocalDateTime,
			startLocalDateTime, unit);

		metric.setHistograms(histograms.toArray(new Histogram[0]));
		metric.setValue(
			_getMetricValue(
				bucket, histograms,
				TimeUnit.DAYS.convert(
					dateEnd.getTime() - dateStart.getTime(),
					TimeUnit.MILLISECONDS),
				unit));

		return metric;
	}

	private DateRangeAggregation _createDateRangeAggregation(
		LocalDateTime endLocalDateTime, LocalDateTime startLocalDateTime) {

		DateRangeAggregation dateRangeAggregation = _aggregations.dateRange(
			"completionDate", "completionDate");

		dateRangeAggregation.addRange(
			"current", _dateTimeFormatter.format(startLocalDateTime),
			_dateTimeFormatter.format(endLocalDateTime));

		return dateRangeAggregation;
	}

	private Histogram _createHistogram(LocalDateTime localDateTime) {
		return new Histogram() {
			{
				setKey(localDateTime.toString());
				setValue(0.0);
			}
		};
	}

	private Collection<Histogram> _createHistograms(
		Collection<Bucket> buckets, LocalDateTime endLocalDateTime,
		LocalDateTime startLocalDateTime, String unit) {

		Map<String, Histogram> histograms = _createHistograms(
			endLocalDateTime, startLocalDateTime, unit);

		for (Bucket bucket : buckets) {
			LocalDateTime localDateTime = LocalDateTime.parse(
				bucket.getKey(), _dateTimeFormatter);

			if (Objects.equals(unit, Metric.Unit.MONTHS.getValue()) ||
				Objects.equals(unit, Metric.Unit.WEEKS.getValue()) ||
				Objects.equals(unit, Metric.Unit.YEARS.getValue())) {

				localDateTime = _getHistogramLocalDateTime(
					localDateTime, startLocalDateTime);
			}

			Histogram histogram = histograms.get(localDateTime.toString());

			histogram.setKey(localDateTime.toString());
			histogram.setValue((double)bucket.getDocCount());

			histograms.put(localDateTime.toString(), histogram);
		}

		return histograms.values();
	}

	private Map<String, Histogram> _createHistograms(
		LocalDateTime endLocalDateTime, LocalDateTime startLocalDateTime,
		String unit) {

		Map<String, Histogram> histograms = new LinkedHashMap<>();

		if (Objects.equals(unit, Metric.Unit.HOURS.getValue())) {
			startLocalDateTime = startLocalDateTime.withMinute(
				LocalTime.MIN.getMinute());
			startLocalDateTime = startLocalDateTime.withNano(
				LocalTime.MIN.getNano());
			startLocalDateTime = startLocalDateTime.withSecond(
				LocalTime.MIN.getSecond());
		}
		else {
			startLocalDateTime = startLocalDateTime.with(LocalTime.MIDNIGHT);
		}

		while (endLocalDateTime.isAfter(startLocalDateTime) ||
			   endLocalDateTime.equals(startLocalDateTime)) {

			histograms.put(
				String.valueOf(startLocalDateTime),
				_createHistogram(startLocalDateTime));

			startLocalDateTime = startLocalDateTime.plus(
				1, ChronoUnit.valueOf(StringUtil.toUpperCase(unit)));

			if (Objects.equals(unit, Metric.Unit.MONTHS.getValue()) &&
				(startLocalDateTime.getDayOfMonth() != 1)) {

				startLocalDateTime = startLocalDateTime.withDayOfMonth(1);
			}
			else if (Objects.equals(unit, Metric.Unit.WEEKS.getValue()) &&
					 (startLocalDateTime.getDayOfWeek() != DayOfWeek.SUNDAY)) {

				startLocalDateTime = startLocalDateTime.minusWeeks(1);
				startLocalDateTime = startLocalDateTime.with(DayOfWeek.SUNDAY);
			}
			else if (Objects.equals(unit, Metric.Unit.YEARS.getValue()) &&
					 (startLocalDateTime.getDayOfYear() != 1)) {

				startLocalDateTime = startLocalDateTime.withDayOfYear(1);
			}
		}

		return histograms;
	}

	private String _getDateHistogramInterval(String unit) {
		if (Objects.equals(unit, Metric.Unit.DAYS.getValue())) {
			return "1d";
		}
		else if (Objects.equals(unit, Metric.Unit.HOURS.getValue())) {
			return "1h";
		}
		else if (Objects.equals(unit, Metric.Unit.MONTHS.getValue())) {
			return "1M";
		}
		else if (Objects.equals(unit, Metric.Unit.WEEKS.getValue())) {
			return "1w";
		}

		return "1y";
	}

	private LocalDateTime _getHistogramLocalDateTime(
		LocalDateTime localDateTime, LocalDateTime startLocalDateTime) {

		if (startLocalDateTime.isAfter(localDateTime)) {
			return startLocalDateTime;
		}

		return localDateTime;
	}

	private double _getMetricValue(
		Bucket bucket, Collection<Histogram> histograms, long timeRange,
		String unit) {

		double timeAmount = histograms.size();

		if (Objects.equals(unit, Metric.Unit.MONTHS.getValue())) {
			timeAmount = timeRange / 30.0;
		}
		else if (Objects.equals(unit, Metric.Unit.WEEKS.getValue())) {
			timeAmount = timeRange / 7.0;
		}
		else if (Objects.equals(unit, Metric.Unit.YEARS.getValue())) {
			timeAmount = timeRange / 365.0;
		}

		return (double)bucket.getDocCount() / timeAmount;
	}

	@Reference
	private Aggregations _aggregations;

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

	@Reference
	private Queries _queries;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Context
	private User _user;

}