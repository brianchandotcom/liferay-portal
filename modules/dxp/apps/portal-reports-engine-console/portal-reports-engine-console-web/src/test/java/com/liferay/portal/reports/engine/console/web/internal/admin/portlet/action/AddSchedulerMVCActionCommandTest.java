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

package com.liferay.portal.reports.engine.console.web.internal.admin.portlet.action;

import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Marcellus Tavares
 */
public class AddSchedulerMVCActionCommandTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		setUpFastDateFormatFactoryUtil();
	}

	@Test
	public void testDailyEveryTwoDaysRecurrence() {
		ActionRequest actionRequest = Mockito.mock(ActionRequest.class);

		whenActionRequestGetParameter(actionRequest, "dailyInterval", "2");

		String actualCronText = _addSchedulerMVCActionCommand.getCronText(
			actionRequest, calendarOf(2017, 1, 1, 7, 0), true,
			Recurrence.DAILY);

		Assert.assertEquals("0 0 7 1/2 * ? *", actualCronText);
	}

	@Test
	public void testDailyEveryWeekdayRecurrence() {
		ActionRequest actionRequest = Mockito.mock(ActionRequest.class);

		whenActionRequestGetParameter(actionRequest, "dailyType", "2");

		String actualCronText = _addSchedulerMVCActionCommand.getCronText(
			actionRequest, calendarOf(2017, 1, 1, 7, 0), true,
			Recurrence.DAILY);

		Assert.assertEquals("0 0 7 ? * MON,TUE,WED,THU,FRI *", actualCronText);
	}

	@Test
	public void testMonthlyDay12OfEveryTwoMonthsRecurrence() {
		ActionRequest actionRequest = Mockito.mock(ActionRequest.class);

		whenActionRequestGetParameter(actionRequest, "monthlyType", "0");
		whenActionRequestGetParameter(actionRequest, "monthlyDay0", "12");
		whenActionRequestGetParameter(actionRequest, "monthlyInterval0", "2");

		String actualCronText = _addSchedulerMVCActionCommand.getCronText(
			actionRequest, calendarOf(2017, 1, 1, 13, 0), true,
			Recurrence.MONTHLY);

		Assert.assertEquals("0 0 13 12 1/2 ? *", actualCronText);
	}

	@Test
	public void testMonthlyThirdTuesdayOfEveryThreeMonthsRecurrence() {
		ActionRequest actionRequest = Mockito.mock(ActionRequest.class);

		whenActionRequestGetParameter(actionRequest, "monthlyType", "1");
		whenActionRequestGetParameter(actionRequest, "monthlyPos", "3");
		whenActionRequestGetParameter(actionRequest, "monthlyDay1", "3");
		whenActionRequestGetParameter(actionRequest, "monthlyInterval1", "3");

		String actualCronText = _addSchedulerMVCActionCommand.getCronText(
			actionRequest, calendarOf(2017, 1, 1, 14, 0), true,
			Recurrence.MONTHLY);

		Assert.assertEquals("0 0 14 ? 1/3 TUE#3 *", actualCronText);
	}

	@Test
	public void testWeeklyEverySundayRecurrence() {
		ActionRequest actionRequest = Mockito.mock(ActionRequest.class);

		whenActionRequestGetParameter(actionRequest, "weeklyDayPos1", "true");

		String actualCronText = _addSchedulerMVCActionCommand.getCronText(
			actionRequest, calendarOf(2017, 1, 1, 12, 0), true,
			Recurrence.WEEKLY);

		Assert.assertEquals("0 0 12 ? * SUN/1 *", actualCronText);
	}

	@Test
	public void testWeeklyMondayAndWednesdayRecurrence() {
		ActionRequest actionRequest = Mockito.mock(ActionRequest.class);

		whenActionRequestGetParameter(actionRequest, "weeklyDayPos2", "true");
		whenActionRequestGetParameter(actionRequest, "weeklyDayPos4", "true");
		whenActionRequestGetParameter(actionRequest, "weeklyInterval", "1");

		String actualCronText = _addSchedulerMVCActionCommand.getCronText(
			actionRequest, calendarOf(2017, 1, 1, 12, 0), true,
			Recurrence.WEEKLY);

		Assert.assertEquals("0 0 12 ? * MON,WED/1 *", actualCronText);
	}

	@Test
	public void testYearlyEveryTwoYearsThirdSundayOfMarchRecurrence() {
		ActionRequest actionRequest = Mockito.mock(ActionRequest.class);

		whenActionRequestGetParameter(actionRequest, "yearlyType", "1");
		whenActionRequestGetParameter(actionRequest, "yearlyDay1", "1");
		whenActionRequestGetParameter(actionRequest, "yearlyMonth1", "2");
		whenActionRequestGetParameter(actionRequest, "yearlyPos", "3");
		whenActionRequestGetParameter(actionRequest, "yearlyInterval1", "2");

		String actualCronText = _addSchedulerMVCActionCommand.getCronText(
			actionRequest, calendarOf(2017, 1, 1, 12, 0), true,
			Recurrence.YEARLY);

		Assert.assertEquals("0 0 12 ? 3 SUN#3 2017/2", actualCronText);
	}

	@Test
	public void testYearlyYearlyEveryTwoYearsJanuary12Recurrence() {
		ActionRequest actionRequest = Mockito.mock(ActionRequest.class);

		whenActionRequestGetParameter(actionRequest, "yearlyType", "0");
		whenActionRequestGetParameter(actionRequest, "yearlyDay0", "12");
		whenActionRequestGetParameter(actionRequest, "yearlyMonth0", "0");
		whenActionRequestGetParameter(actionRequest, "yearlyInterval0", "2");

		String actualCronText = _addSchedulerMVCActionCommand.getCronText(
			actionRequest, calendarOf(2017, 1, 1, 12, 0), true,
			Recurrence.YEARLY);

		Assert.assertEquals("0 0 12 12 1 ? 2017/2", actualCronText);
	}

	protected Calendar calendarOf(
		int year, int month, int date, int hour, int minute) {

		Calendar calendar = new GregorianCalendar(
			TimeZoneUtil.getDefault(), LocaleUtil.getDefault());

		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	protected void setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	protected void whenActionRequestGetParameter(
		ActionRequest actionRequest, String parameterName,
		String parameterValue) {

		Mockito.when(
			actionRequest.getParameter(Matchers.eq(parameterName))
		).thenReturn(
			parameterValue
		);
	}

	private final AddSchedulerMVCActionCommand _addSchedulerMVCActionCommand =
		new AddSchedulerMVCActionCommand();

}