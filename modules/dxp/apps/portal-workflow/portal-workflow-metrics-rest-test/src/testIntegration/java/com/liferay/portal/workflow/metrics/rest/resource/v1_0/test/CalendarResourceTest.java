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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Calendar;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendar;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
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
		Page<Calendar> calendarsPage = calendarResource.getCalendarsPage();

		List<Calendar> calendars = (List<Calendar>)calendarsPage.getItems();

		Assert.assertEquals(calendars.toString(), 1, calendars.size());

		assertEquals(_getDefaultCalendar(), calendars.get(0));

		_registerCustomCalendar();

		calendarsPage = calendarResource.getCalendarsPage();

		calendars = (List<Calendar>)calendarsPage.getItems();

		Assert.assertEquals(calendars.toString(), 2, calendars.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(_getDefaultCalendar(), _getCustomCalendar()),
			calendars);
	}

	@Override
	@Test
	public void testGraphQLGetCalendarsPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"calendars", graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject calendarsJSONObject = dataJSONObject.getJSONObject(
			"calendars");

		Assert.assertEquals(1, calendarsJSONObject.get("totalCount"));

		JSONArray itemsJSONArray = calendarsJSONObject.getJSONArray("items");

		equalsJSONObject(
			_getDefaultCalendar(), itemsJSONArray.getJSONObject(0));

		_registerCustomCalendar();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		calendarsJSONObject = dataJSONObject.getJSONObject("calendars");

		Assert.assertEquals(2, calendarsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(_getDefaultCalendar(), _getCustomCalendar()),
			calendarsJSONObject.getJSONArray("items"));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"defaultCalendar", "key", "title"};
	}

	private Calendar _getCustomCalendar() {
		return new Calendar() {
			{
				defaultCalendar = false;
				key = "custom";
				title = "Custom";
			}
		};
	}

	private Calendar _getDefaultCalendar() {
		return new Calendar() {
			{
				defaultCalendar = true;
				key = "default";
				title = "24/7";
			}
		};
	}

	private void _registerCustomCalendar() {
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