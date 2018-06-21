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

package com.liferay.commerce.dashboard.web.internal.display.context;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.dashboard.web.internal.configuration.CommerceDashboardForecastsChartPortletInstanceConfiguration;
import com.liferay.commerce.dashboard.web.internal.servlet.taglib.model.CommerceDashboardPredictiveChartConfig;
import com.liferay.commerce.forecast.model.CommerceForecastEntry;
import com.liferay.commerce.forecast.model.CommerceForecastEntryConstants;
import com.liferay.commerce.forecast.model.CommerceForecastValue;
import com.liferay.commerce.forecast.service.CommerceForecastEntryLocalService;
import com.liferay.commerce.forecast.service.CommerceForecastValueLocalService;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.frontend.taglib.chart.model.AxisY;
import com.liferay.frontend.taglib.chart.model.MixedDataColumn;
import com.liferay.frontend.taglib.chart.model.predictive.PredictiveChartConfig;
import com.liferay.ibm.icu.text.DateFormat;
import com.liferay.ibm.icu.text.DateFormat.Field;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.math.BigDecimal;

import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceDashboardForecastsChartDisplayContext
	extends CommerceDashboardDisplayContext {

	public CommerceDashboardForecastsChartDisplayContext(
			CommerceForecastEntryLocalService commerceForecastEntryLocalService,
			CommerceForecastValueLocalService commerceForecastValueLocalService,
			CommerceOrganizationService commerceOrganizationService,
			CompanyService companyService,
			ConfigurationProvider configurationProvider,
			CPInstanceService cpInstanceService, RenderRequest renderRequest)
		throws PortalException {

		super(configurationProvider, renderRequest);

		_commerceForecastEntryLocalService = commerceForecastEntryLocalService;
		_commerceForecastValueLocalService = commerceForecastValueLocalService;
		_commerceOrganizationService = commerceOrganizationService;
		_companyService = companyService;
		_cpInstanceService = cpInstanceService;

		PortletDisplay portletDisplay =
			commerceDashboardRequestHelper.getPortletDisplay();

		_commerceDashboardForecastsChartPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceDashboardForecastsChartPortletInstanceConfiguration.
					class);
	}

	@Override
	public int getPeriod() {
		int period =
			_commerceDashboardForecastsChartPortletInstanceConfiguration.
				period();

		if (period <= 0) {
			period = super.getPeriod();
		}

		return period;
	}

	public PredictiveChartConfig getPredictiveChartConfig()
		throws PortalException {

		List<ForecastChartEntry> forecastChartEntries =
			_getForecastChartEntries();

		if (forecastChartEntries.isEmpty()) {
			return null;
		}

		BigDecimal minValue = null;
		BigDecimal maxValue = null;
		Date predictionDate = null;
		Set<Date> timeseriesDates = new TreeSet<>();

		for (ForecastChartEntry forecastChartEntry : forecastChartEntries) {
			for (CommerceForecastValue commerceForecastValue :
					forecastChartEntry.commerceForecastValues) {

				BigDecimal lowerValue = commerceForecastValue.getLowerValue();
				BigDecimal value = commerceForecastValue.getValue();
				BigDecimal upperValue = commerceForecastValue.getUpperValue();

				if (minValue == null) {
					minValue = value;
				}
				else if (lowerValue != null) {
					minValue = minValue.min(lowerValue);
				}
				else {
					minValue = minValue.min(value);
				}

				if (maxValue == null) {
					maxValue = value;
				}
				else if (upperValue != null) {
					maxValue = maxValue.max(upperValue);
				}
				else {
					maxValue = maxValue.max(value);
				}

				timeseriesDates.add(_getTimeseriesDate(commerceForecastValue));

				if (!commerceForecastValue.isForecast()) {
					Date date = _getTimeseriesDate(commerceForecastValue);

					if ((predictionDate == null) ||
						date.after(predictionDate)) {

						predictionDate = date;
					}
				}
			}
		}

		CommerceDashboardPredictiveChartConfig
			commerceDashboardPredictiveChartConfig =
				new CommerceDashboardPredictiveChartConfig();

		List<Date> timeseriesDatesList = ListUtil.fromCollection(
			timeseriesDates);

		for (ForecastChartEntry forecastChartEntry : forecastChartEntries) {
			commerceDashboardPredictiveChartConfig.addDataColumn(
				forecastChartEntry.getMixedDataColumn(timeseriesDatesList));
		}

		commerceDashboardPredictiveChartConfig.setAxisXTickFormat(
			_getAxisXTickFormat());
		commerceDashboardPredictiveChartConfig.setColors(
			_getColors(forecastChartEntries));
		commerceDashboardPredictiveChartConfig.setLegend(
			Collections.singletonMap("show", false));

		if (predictionDate != null) {
			commerceDashboardPredictiveChartConfig.setPredictionDate(
				_dateFormat.format(predictionDate));
		}

		commerceDashboardPredictiveChartConfig.setTimeseries(
			_getTimeseries(timeseriesDatesList));

		AxisY axisY = commerceDashboardPredictiveChartConfig.getAxisY();

		axisY.setMin(minValue);
		axisY.setMax(maxValue);

		if (_commerceDashboardForecastsChartPortletInstanceConfiguration.
				target() == CommerceForecastEntryConstants.TARGET_REVENUE) {

			CommerceContext commerceContext =
				commerceDashboardRequestHelper.getCommerceContext();

			CommerceCurrency commerceCurrency =
				commerceContext.getCommerceCurrency();

			axisY.setLabel(commerceCurrency.getCode());
		}

		return commerceDashboardPredictiveChartConfig;
	}

	private String _getAxisXTickFormat() {
		String skeleton = null;

		int period = getPeriod();

		String dayFormat = "%-d";
		String monthFormat = "%b";
		String yearFormat = "%Y";

		if (getStartDateYear() == getEndDateYear()) {
			if (period == CommerceForecastEntryConstants.PERIOD_MONTHLY) {
				skeleton = DateFormat.ABBR_MONTH;
			}
			else {
				skeleton = DateFormat.ABBR_MONTH_DAY;
			}
		}
		else {
			if (period == CommerceForecastEntryConstants.PERIOD_MONTHLY) {
				skeleton = DateFormat.YEAR_ABBR_MONTH;
			}
			else {
				dayFormat = "%d";
				monthFormat = "%m";

				skeleton = DateFormat.YEAR_NUM_MONTH_DAY;
			}
		}

		DateFormat dateFormat = DateFormat.getInstanceForSkeleton(
			skeleton, commerceDashboardRequestHelper.getLocale());

		AttributedCharacterIterator attributedCharacterIterator =
			dateFormat.formatToCharacterIterator(new Date());

		StringBundler sb = new StringBundler();

		while (attributedCharacterIterator.current() !=
					AttributedCharacterIterator.DONE) {

			Map<Attribute, Object> attributesMap =
				attributedCharacterIterator.getAttributes();

			if (attributesMap.isEmpty()) {
				sb.append(attributedCharacterIterator.current());
			}
			else {
				Set<Attribute> attributes = attributesMap.keySet();

				Iterator<Attribute> iterator = attributes.iterator();

				Field field = (Field)iterator.next();

				int calendarField = field.getCalendarField();

				if (calendarField == Calendar.DATE) {
					sb.append(dayFormat);
				}

				if (calendarField == Calendar.MONTH) {
					sb.append(monthFormat);
				}
				else if (calendarField == Calendar.YEAR) {
					sb.append(yearFormat);
				}
			}

			attributedCharacterIterator.setIndex(
				attributedCharacterIterator.getRunLimit());
		}

		return sb.toString();
	}

	private Map<String, String> _getColors(
		List<ForecastChartEntry> forecastChartEntries) {

		Map<String, String> colors = new HashMap<>();

		for (ForecastChartEntry forecastChartEntry : forecastChartEntries) {
			colors.put(forecastChartEntry.id, forecastChartEntry.color);
		}

		return colors;
	}

	private List<ForecastChartEntry> _getForecastChartEntries()
		throws PortalException {

		List<CommerceForecastEntry> commerceForecastEntries = null;

		Calendar startCalendar = CalendarFactoryUtil.getCalendar(
			getStartDateYear(), getStartDateMonth(), getStartDateDay(), 0, 0, 0,
			0, commerceDashboardRequestHelper.getTimeZone());
		Calendar endCalendar = CalendarFactoryUtil.getCalendar(
			getEndDateYear(), getEndDateMonth(), getEndDateDay(), 23, 59, 59,
			990, commerceDashboardRequestHelper.getTimeZone());

		if (startCalendar.after(endCalendar)) {
			return Collections.emptyList();
		}

		long companyId = commerceDashboardRequestHelper.getCompanyId();
		int period = getPeriod();
		int target =
			_commerceDashboardForecastsChartPortletInstanceConfiguration.
				target();
		long customerId = getCustomerId();

		if (_commerceDashboardForecastsChartPortletInstanceConfiguration.
				filterBySKU()) {

			Map<Long, Boolean> cpInstanceIds = getCPInstanceIds();

			commerceForecastEntries = new ArrayList<>(cpInstanceIds.size());

			for (Map.Entry<Long, Boolean> entry : cpInstanceIds.entrySet()) {
				CommerceForecastEntry commerceForecastEntry = null;

				if (entry.getValue()) {
					long cpInstanceId = entry.getKey();

					try {
						_cpInstanceService.getCPInstance(cpInstanceId);
					}
					catch (PortalException pe) {
						if (pe instanceof NoSuchCPInstanceException ||
							pe instanceof PrincipalException) {

							continue;
						}

						throw pe;
					}

					commerceForecastEntry =
						_commerceForecastEntryLocalService.
							fetchCommerceForecastEntry(
								companyId, period, target, customerId,
								cpInstanceId);
				}

				commerceForecastEntries.add(commerceForecastEntry);
			}
		}
		else {
			CommerceForecastEntry commerceForecastEntry =
				_commerceForecastEntryLocalService.fetchCommerceForecastEntry(
					companyId, period, target, customerId, 0);

			commerceForecastEntries = Collections.singletonList(
				commerceForecastEntry);
		}

		List<ForecastChartEntry> forecastChartEntries = new ArrayList<>(
			commerceForecastEntries.size());

		Date startDate = CalendarUtil.getGTDate(startCalendar);
		Date endDate = CalendarUtil.getLTDate(endCalendar);

		long startTime = startDate.getTime();
		long endTime = endDate.getTime();

		for (int i = 0; i < commerceForecastEntries.size(); i++) {
			CommerceForecastEntry commerceForecastEntry =
				commerceForecastEntries.get(i);

			if (commerceForecastEntry == null) {
				continue;
			}

			List<CommerceForecastValue> commerceForecastValues =
				_commerceForecastValueLocalService.getCommerceForecastValues(
					commerceForecastEntry.getCommerceForecastEntryId(),
					startTime, endTime);

			if (commerceForecastValues.isEmpty()) {
				continue;
			}

			String color = getChartColor(i);

			forecastChartEntries.add(
				new ForecastChartEntry(
					commerceForecastEntry, commerceForecastValues, color));
		}

		return forecastChartEntries;
	}

	private List<String> _getTimeseries(List<Date> timeseriesDates) {
		List<String> timeseries = new ArrayList<>(timeseriesDates.size());

		for (Date date : timeseriesDates) {
			timeseries.add(_dateFormat.format(date));
		}

		return timeseries;
	}

	private Date _getTimeseriesDate(
		CommerceForecastValue commerceForecastValue) {

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			commerceForecastValue.getTime());

		int period = getPeriod();

		if (period == CommerceForecastEntryConstants.PERIOD_MONTHLY) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		else if (period == CommerceForecastEntryConstants.PERIOD_WEEKLY) {
			calendar.set(Calendar.DAY_OF_WEEK, getFirstDayOfWeek());
		}

		return calendar.getTime();
	}

	private static final Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");

	private final CommerceDashboardForecastsChartPortletInstanceConfiguration
		_commerceDashboardForecastsChartPortletInstanceConfiguration;
	private final CommerceForecastEntryLocalService
		_commerceForecastEntryLocalService;
	private final CommerceForecastValueLocalService
		_commerceForecastValueLocalService;
	private final CommerceOrganizationService _commerceOrganizationService;
	private final CompanyService _companyService;
	private final CPInstanceService _cpInstanceService;

	private class ForecastChartEntry {

		public ForecastChartEntry(
				CommerceForecastEntry commerceForecastEntry,
				List<CommerceForecastValue> commerceForecastValues,
				String color)
			throws PortalException {

			this.commerceForecastValues = commerceForecastValues;
			this.color = color;

			id = _getId(commerceForecastEntry);
		}

		public MixedDataColumn getMixedDataColumn(List<Date> timeseriesDates) {
			return new MixedDataColumn(
				id,
				_getMixedDataColumnValues(
					commerceForecastValues, timeseriesDates));
		}

		public final String color;
		public final List<CommerceForecastValue> commerceForecastValues;
		public final String id;

		private String _getId(CommerceForecastEntry commerceForecastEntry)
			throws PortalException {

			long cpInstanceId = commerceForecastEntry.getCPInstanceId();

			if (cpInstanceId > 0) {
				return String.valueOf(cpInstanceId);
			}

			long customerId = commerceForecastEntry.getCustomerId();

			if (customerId > 0) {
				try {
					Organization organization =
						_commerceOrganizationService.getOrganization(
							customerId);

					return organization.getName();
				}
				catch (NoSuchOrganizationException nsoe) {
					return String.valueOf(customerId);
				}
			}

			long companyId = commerceForecastEntry.getCompanyId();

			try {
				Company company = _companyService.getCompanyById(companyId);

				return company.getName();
			}
			catch (NoSuchCompanyException nsce) {
				return String.valueOf(companyId);
			}
		}

		private Collection<Object> _getMixedDataColumnValues(
			List<CommerceForecastValue> commerceForecastValues,
			List<Date> timeseriesDates) {

			Map<Date, Object> map = new TreeMap<>();

			for (Date date : timeseriesDates) {
				map.put(date, BigDecimal.ZERO);
			}

			for (CommerceForecastValue commerceForecastValue :
					commerceForecastValues) {

				Object value = commerceForecastValue.getValue();

				if (commerceForecastValue.isForecast()) {
					value = Arrays.asList(
						commerceForecastValue.getLowerValue(),
						commerceForecastValue.getValue(),
						commerceForecastValue.getUpperValue());
				}

				map.put(_getTimeseriesDate(commerceForecastValue), value);
			}

			return map.values();
		}

	}

}