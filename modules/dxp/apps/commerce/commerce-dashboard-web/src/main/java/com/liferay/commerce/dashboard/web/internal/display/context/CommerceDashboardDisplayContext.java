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

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.dashboard.web.internal.configuration.CommerceDashboardCompanyConfiguration;
import com.liferay.commerce.dashboard.web.internal.display.context.util.CommerceDashboardRequestHelper;
import com.liferay.commerce.dashboard.web.internal.util.CommerceDashboardUtil;
import com.liferay.commerce.forecast.model.CommerceForecastEntryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.Map;

import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceDashboardDisplayContext {

	public CommerceDashboardDisplayContext(
			ConfigurationProvider configurationProvider,
			RenderRequest renderRequest)
		throws PortalException {

		commerceDashboardRequestHelper = new CommerceDashboardRequestHelper(
			renderRequest);

		Layout layout = commerceDashboardRequestHelper.getLayout();

		if (layout.isTypeControlPanel()) {
			_customerId = CommerceDashboardUtil.getSessionValue(
				renderRequest, "customerId", 0L);
		}
		else {
			CommerceContext commerceContext =
				(CommerceContext)renderRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			Organization organization = commerceContext.getOrganization();

			if (organization != null) {
				_customerId = organization.getOrganizationId();
			}
			else {
				_customerId = commerceDashboardRequestHelper.getUserId();
			}
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			commerceDashboardRequestHelper.getTimeZone(),
			commerceDashboardRequestHelper.getLocale());

		calendar.add(Calendar.MONTH, 6);

		_endDateDay = CommerceDashboardUtil.getSessionValue(
			renderRequest, "endDateDay", calendar.get(Calendar.DATE));
		_endDateMonth = CommerceDashboardUtil.getSessionValue(
			renderRequest, "endDateMonth", calendar.get(Calendar.MONTH));
		_endDateYear = CommerceDashboardUtil.getSessionValue(
			renderRequest, "endDateYear", calendar.get(Calendar.YEAR));

		calendar.add(Calendar.YEAR, -1);

		_startDateDay = CommerceDashboardUtil.getSessionValue(
			renderRequest, "startDateDay", calendar.get(Calendar.DATE));
		_startDateMonth = CommerceDashboardUtil.getSessionValue(
			renderRequest, "startDateMonth", calendar.get(Calendar.MONTH));
		_startDateYear = CommerceDashboardUtil.getSessionValue(
			renderRequest, "startDateYear", calendar.get(Calendar.YEAR));

		_firstDayOfWeek = calendar.getFirstDayOfWeek();

		_period = CommerceDashboardUtil.getSessionValue(
			renderRequest, "period",
			CommerceForecastEntryConstants.PERIOD_MONTHLY);
		_cpInstanceIds = CommerceDashboardUtil.getSessionMap(
			renderRequest, "cpInstanceIds");

		_commerceDashboardCompanyConfiguration =
			configurationProvider.getCompanyConfiguration(
				CommerceDashboardCompanyConfiguration.class,
				commerceDashboardRequestHelper.getCompanyId());
	}

	public String getChartColor(int index) {
		String[] chartColors =
			_commerceDashboardCompanyConfiguration.chartColors();

		return chartColors[index % chartColors.length];
	}

	public Map<Long, Boolean> getCPInstanceIds() {
		return _cpInstanceIds;
	}

	public long getCustomerId() {
		return _customerId;
	}

	public int getEndDateDay() {
		return _endDateDay;
	}

	public int getEndDateMonth() {
		return _endDateMonth;
	}

	public int getEndDateYear() {
		return _endDateYear;
	}

	public int getFirstDayOfWeek() {
		return _firstDayOfWeek;
	}

	public int getPeriod() {
		return _period;
	}

	public int getStartDateDay() {
		return _startDateDay;
	}

	public int getStartDateMonth() {
		return _startDateMonth;
	}

	public int getStartDateYear() {
		return _startDateYear;
	}

	protected final CommerceDashboardRequestHelper
		commerceDashboardRequestHelper;

	private final CommerceDashboardCompanyConfiguration
		_commerceDashboardCompanyConfiguration;
	private final Map<Long, Boolean> _cpInstanceIds;
	private final long _customerId;
	private final int _endDateDay;
	private final int _endDateMonth;
	private final int _endDateYear;
	private final int _firstDayOfWeek;
	private final int _period;
	private final int _startDateDay;
	private final int _startDateMonth;
	private final int _startDateYear;

}