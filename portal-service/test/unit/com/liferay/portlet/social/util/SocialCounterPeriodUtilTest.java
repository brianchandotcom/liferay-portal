package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class SocialCounterPeriodUtilTest extends PowerMockito {

	@Test
	public void testGetActivityDay() {
		_initMocks();

		Calendar calendar = new GregorianCalendar(2011, Calendar.JANUARY, 1);

		int activityDay = SocialCounterPeriodUtil.getActivityDay(calendar);

		Assert.assertEquals(0, activityDay);

		activityDay = SocialCounterPeriodUtil.getActivityDay(
			calendar.getTimeInMillis());

		Assert.assertEquals(0, activityDay);

		calendar = new GregorianCalendar(2011, Calendar.FEBRUARY, 1);

		activityDay = SocialCounterPeriodUtil.getActivityDay(calendar);

		Assert.assertEquals(31, activityDay);

		activityDay = SocialCounterPeriodUtil.getActivityDay(
			calendar.getTimeInMillis());

		Assert.assertEquals(31, activityDay);
	}

	@Test
	public void testGetDate() {
		_initMocks();

		Date activityDate = SocialCounterPeriodUtil.getDate(0);

		Calendar calendar = new GregorianCalendar(2011, Calendar.JANUARY, 1);

		Assert.assertEquals(calendar.getTime(), activityDate);

		calendar = new GregorianCalendar(2011, Calendar.FEBRUARY, 1);

		activityDate = SocialCounterPeriodUtil.getDate(31);

		Assert.assertEquals(calendar.getTime(), activityDate);
	}

	@Test
	public void testGetPeriodLength() {
		_initMocks();

		int periodLength = SocialCounterPeriodUtil.getPeriodLength();

		Assert.assertEquals(1, periodLength);

		periodLength = SocialCounterPeriodUtil.getPeriodLength(-1);

		Assert.assertEquals(1, periodLength);
	}

	@Test
	public void testGetStartPeriod() {
		_initMocks();

		Calendar calendar = new GregorianCalendar(2011, Calendar.JANUARY, 15);

		int offset = SocialCounterPeriodUtil.getStartPeriod(
			calendar.getTimeInMillis());

		Assert.assertEquals(14, offset);

		int currentStartPeriod = SocialCounterPeriodUtil.getStartPeriod();

		Assert.assertEquals(
			currentStartPeriod, SocialCounterPeriodUtil.getStartPeriod(0));

		Assert.assertEquals(
			currentStartPeriod - 1, SocialCounterPeriodUtil.getStartPeriod(-1));
	}

	private void _initMocks() {
		mockStatic(PropsUtil.class);

		when(
			PropsUtil.get(PropsKeys.SOCIAL_ACTIVITY_COUNTER_PERIOD_LENGTH)
		).thenReturn("1");
	}

}