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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Calendar;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.resource.v1_0.CalendarResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendar;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class CalendarResourceTest extends BaseCalendarResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseCalendarResourceTestCase.setUpClass();

		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowMetricsRESTTestHelper.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_serviceRegistration == null) {
			return;
		}

		_serviceRegistration.unregister();
	}

	@Override
	@Test
	public void testGetCalendarsPage() throws Exception {
		Page<Calendar> calendarsPage = CalendarResource.getCalendarsPage();

		List<Calendar> calendars = (List<Calendar>)calendarsPage.getItems();

		Assert.assertEquals(calendars.toString(), 1, calendars.size());

		Calendar calendar = calendars.get(0);

		Assert.assertEquals(true, calendar.getDefaultCalendar());
		Assert.assertEquals("default", calendar.getKey());
		Assert.assertEquals("24/7", calendar.getTitle());
	}

	@Test
	public void testGetCalendarsPageWithCustomCalendars() throws Exception {
		_registerCustomWorkflowMetricsSLACalendar();

		Page<Calendar> calendarsPage = CalendarResource.getCalendarsPage();

		List<Calendar> calendars = (List<Calendar>)calendarsPage.getItems();

		Assert.assertEquals(calendars.toString(), 2, calendars.size());

		for (Calendar calendar : calendars) {
			if (calendar.getDefaultCalendar()) {
				continue;
			}

			Assert.assertEquals(false, calendar.getDefaultCalendar());
			Assert.assertEquals("custom", calendar.getKey());
			Assert.assertEquals("Custom", calendar.getTitle());
		}
	}

	private void _registerCustomWorkflowMetricsSLACalendar() {
		_serviceRegistration = _bundleContext.registerService(
			WorkflowMetricsSLACalendar.class,
			new WorkflowMetricsSLACalendar() {

				@Override
				public Duration getDuration(
					LocalDateTime startLocalDateTime,
					LocalDateTime endLocalDateTime) {

					return Duration.ZERO;
				}

				@Override
				public LocalDateTime getOverdueLocalDateTime(
					LocalDateTime nowLocalDateTime,
					Duration remainingDuration) {

					return nowLocalDateTime;
				}

				@Override
				public String getTitle(Locale locale) {
					return "Custom";
				}

			},
			new HashMapDictionary<String, String>() {
				{
					put("sla.calendar.key", "custom");
				}
			});
	}

	private static BundleContext _bundleContext;

	private ServiceRegistration<WorkflowMetricsSLACalendar>
		_serviceRegistration;

}