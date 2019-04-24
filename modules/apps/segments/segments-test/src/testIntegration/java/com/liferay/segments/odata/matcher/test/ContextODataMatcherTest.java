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

package com.liferay.segments.odata.matcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.context.Context;
import com.liferay.segments.odata.matcher.ODataMatcher;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class ContextODataMatcherTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMatchesBooleanEquals() throws Exception {
		Context context = new Context() {
			{
				put(Context.SIGNED_IN, true);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.SIGNED_IN, " eq ", Boolean.TRUE, ")"),
				context));
	}

	@Test
	public void testMatchesCollectionWithAnyContains() throws Exception {
		Context context = new Context() {
			{
				put(
					Context.COOKIES,
					new String[] {"key1=value1", "key2=value2"});
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				Context.COOKIES + "/any(c:contains(c, 'key1'))", context));
	}

	@Test
	public void testMatchesCollectionWithAnyEquals() throws Exception {
		Context context = new Context() {
			{
				put(
					Context.COOKIES,
					new String[] {"key1=value1", "key2=value2"});
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				Context.COOKIES + "/any(c:c eq 'key1=value1')", context));
	}

	@Test
	public void testMatchesDateEquals() throws Exception {
		LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 1);

		Context context = new Context() {
			{
				put(Context.LOCAL_DATE, localDate);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LOCAL_DATE, " eq ",
					localDate.format(DateTimeFormatter.ISO_LOCAL_DATE), ")"),
				context));

		LocalDate tomorrowLocalDate = localDate.plusDays(1);

		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LOCAL_DATE, " eq ",
					tomorrowLocalDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
					")"),
				context));
	}

	@Test
	public void testMatchesDateGreater() throws Exception {
		LocalDate localDate = LocalDate.of(2018, Month.JANUARY, 1);

		Context context = new Context() {
			{
				put(Context.LOCAL_DATE, LocalDate.of(2019, Month.JANUARY, 1));
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LOCAL_DATE, " gt ",
					localDate.format(DateTimeFormatter.ISO_LOCAL_DATE), ")"),
				context));
	}

	@Test
	public void testMatchesDateGreaterOrEquals() throws Exception {
		LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 1);

		Context context = new Context() {
			{
				put(Context.LOCAL_DATE, localDate);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LOCAL_DATE, " ge ",
					localDate.format(DateTimeFormatter.ISO_LOCAL_DATE), ")"),
				context));

		LocalDate yesterdayLocalDate = localDate.minusDays(1);

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LOCAL_DATE, " ge ",
					yesterdayLocalDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
					")"),
				context));
	}

	@Test
	public void testMatchesDateLesser() throws Exception {
		LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 1);

		Context context = new Context() {
			{
				put(Context.LOCAL_DATE, LocalDate.of(2018, Month.JANUARY, 1));
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LOCAL_DATE, " lt ",
					localDate.format(DateTimeFormatter.ISO_LOCAL_DATE), ")"),
				context));
	}

	@Test
	public void testMatchesDateLesserOrEquals() throws Exception {
		LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 1);

		Context context = new Context() {
			{
				put(Context.LOCAL_DATE, localDate);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LOCAL_DATE, " le ",
					localDate.format(DateTimeFormatter.ISO_LOCAL_DATE), ")"),
				context));

		LocalDate tomorrowLocalDate = localDate.plusDays(1);

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LOCAL_DATE, " le ",
					tomorrowLocalDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
					")"),
				context));
	}

	@Test
	public void testMatchesDateTimeEquals() throws Exception {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			2019, 1, 1, 10, 30, 0, 0, ZoneOffset.UTC);

		Context context = new Context() {
			{
				put(Context.LAST_SIGN_IN_DATE_TIME, zonedDateTime);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LAST_SIGN_IN_DATE_TIME, " eq ",
					zonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")"),
				context));

		ZonedDateTime nextHourZonedDateTime = zonedDateTime.plusHours(1);

		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LAST_SIGN_IN_DATE_TIME, " eq ",
					nextHourZonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")"),
				context));
	}

	@Test
	public void testMatchesDateTimeGreater() throws Exception {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			2019, 1, 1, 10, 30, 0, 0, ZoneOffset.UTC);

		Context context = new Context() {
			{
				put(
					Context.LAST_SIGN_IN_DATE_TIME,
					ZonedDateTime.of(2019, 1, 1, 11, 30, 0, 0, ZoneOffset.UTC));
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LAST_SIGN_IN_DATE_TIME, " gt ",
					zonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")"),
				context));
	}

	@Test
	public void testMatchesDateTimeGreaterOrEquals() throws Exception {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			2019, 1, 1, 10, 30, 0, 0, ZoneOffset.UTC);

		Context context = new Context() {
			{
				put(Context.LAST_SIGN_IN_DATE_TIME, zonedDateTime);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LAST_SIGN_IN_DATE_TIME, " ge ",
					zonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")"),
				context));

		ZonedDateTime pastHourZonedDateTime = zonedDateTime.minusHours(1);

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LAST_SIGN_IN_DATE_TIME, " ge ",
					pastHourZonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")"),
				context));
	}

	@Test
	public void testMatchesDateTimeLesser() throws Exception {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			2019, 1, 1, 10, 30, 0, 0, ZoneOffset.UTC);

		Context context = new Context() {
			{
				put(
					Context.LAST_SIGN_IN_DATE_TIME,
					ZonedDateTime.of(2019, 1, 1, 9, 30, 0, 0, ZoneOffset.UTC));
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LAST_SIGN_IN_DATE_TIME, " lt ",
					zonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")"),
				context));
	}

	@Test
	public void testMatchesDateTimeLesserOrEquals() throws Exception {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			2019, 1, 1, 10, 30, 0, 0, ZoneOffset.UTC);

		Context context = new Context() {
			{
				put(Context.LAST_SIGN_IN_DATE_TIME, zonedDateTime);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LAST_SIGN_IN_DATE_TIME, " le ",
					zonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")"),
				context));

		ZonedDateTime nextHourZonedDateTime = zonedDateTime.plusHours(1);

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LAST_SIGN_IN_DATE_TIME, " le ",
					nextHourZonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")"),
				context));
	}

	@Test
	public void testMatchesDoubleEquals() throws Exception {
		Context context = new Context() {
			{
				put(Context.DEVICE_SCREEN_RESOLUTION_WIDTH, 1000D);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.DEVICE_SCREEN_RESOLUTION_WIDTH, " eq 1000.0)"),
				context));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.DEVICE_SCREEN_RESOLUTION_WIDTH, " eq 1001.0)"),
				context));
	}

	@Test
	public void testMatchesDoubleGreater() throws Exception {
		Context context = new Context() {
			{
				put(Context.DEVICE_SCREEN_RESOLUTION_WIDTH, 1000D);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.DEVICE_SCREEN_RESOLUTION_WIDTH, " gt 900.0)"),
				context));
	}

	@Test
	public void testMatchesDoubleGreaterOrEquals() throws Exception {
		Context context = new Context() {
			{
				put(Context.DEVICE_SCREEN_RESOLUTION_WIDTH, 1000D);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.DEVICE_SCREEN_RESOLUTION_WIDTH, " ge 900.0)"),
				context));
		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.DEVICE_SCREEN_RESOLUTION_WIDTH, " ge 1000.0)"),
				context));
	}

	@Test
	public void testMatchesDoubleLesser() throws Exception {
		Context context = new Context() {
			{
				put(Context.DEVICE_SCREEN_RESOLUTION_WIDTH, 1000D);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.DEVICE_SCREEN_RESOLUTION_WIDTH, " lt 1100.0)"),
				context));
	}

	@Test
	public void testMatchesDoubleLesserOrEquals() throws Exception {
		Context context = new Context() {
			{
				put(Context.DEVICE_SCREEN_RESOLUTION_WIDTH, 1000D);
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.DEVICE_SCREEN_RESOLUTION_WIDTH, " le 1100.0)"),
				context));
		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.DEVICE_SCREEN_RESOLUTION_WIDTH, " le 1000.0)"),
				context));
	}

	@Test
	public void testMatchesStringContains() throws Exception {
		Context context = new Context() {
			{
				put(Context.URL, "http://www.liferay.com");
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat("contains(", Context.URL, ", 'liferay')"),
				context));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat("contains(", Context.URL, ", 'google')"),
				context));
	}

	@Test
	public void testMatchesStringEquals() throws Exception {
		Context context = new Context() {
			{
				put(Context.URL, "http://www.liferay.com");
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.URL, " eq 'http://www.liferay.com')"),
				context));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.URL, " eq 'http://localhost')"),
				context));
	}

	@Test
	public void testMatchesStringEqualsWithUppercase() throws Exception {
		Context context = new Context() {
			{
				put(Context.LANGUAGE_ID, "de_DE");
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat("(", Context.LANGUAGE_ID, " eq 'de_DE')"),
				context));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat("(", Context.LANGUAGE_ID, " eq 'fr_FR')"),
				context));
	}

	@Test
	public void testMatchesStringNotContains() throws Exception {
		Context context = new Context() {
			{
				put(Context.URL, "http://www.liferay.com");
			}
		};

		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"not (contains(", Context.URL, ", 'liferay'))"),
				context));
		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"not (contains(", Context.URL, ", 'google'))"),
				context));
	}

	@Test
	public void testMatchesStringNotEquals() throws Exception {
		Context context = new Context() {
			{
				put(Context.LANGUAGE_ID, "en_US");
			}
		};

		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"not (", Context.LANGUAGE_ID, " eq 'en_US')"),
				context));
		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"not (", Context.LANGUAGE_ID, " eq 'fr_FR')"),
				context));
	}

	@Test
	public void testMatchesWithAnd() throws Exception {
		Context context = new Context() {
			{
				put(Context.LANGUAGE_ID, "en_US");
				put(Context.BROWSER, "chrome");
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LANGUAGE_ID, " eq 'en_US') and (",
					Context.BROWSER, " eq 'chrome')"),
				context));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LANGUAGE_ID, " eq 'en_US') and (",
					Context.BROWSER, " eq 'firefox')"),
				context));
	}

	@Test
	public void testMatchesWithOr() throws Exception {
		Context context = new Context() {
			{
				put(Context.LANGUAGE_ID, "en_US");
				put(Context.BROWSER, "chrome");
			}
		};

		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LANGUAGE_ID, " eq 'en_US') or (",
					Context.BROWSER, " eq 'firefox')"),
				context));
		Assert.assertTrue(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LANGUAGE_ID, " eq 'fr_FR') or (",
					Context.BROWSER, " eq 'chrome')"),
				context));
		Assert.assertFalse(
			_contextODataMatcher.matches(
				StringBundler.concat(
					"(", Context.LANGUAGE_ID, " eq 'fr_FR') and (",
					Context.BROWSER, " eq 'firefox')"),
				context));
	}

	@Inject(
		filter = "target.class.name=com.liferay.segments.context.Context",
		type = ODataMatcher.class
	)
	private ODataMatcher<Context> _contextODataMatcher;

}