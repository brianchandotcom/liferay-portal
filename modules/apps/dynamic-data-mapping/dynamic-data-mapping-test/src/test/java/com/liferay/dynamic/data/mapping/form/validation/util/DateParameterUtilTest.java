/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.validation.util;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.DateFormatFactoryImpl;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rodrigo Paulino
 */
public class DateParameterUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpDateFormatFactory();
		_setUpJSONFactoryUtil();
	}

	@Test
	public void testGetLocalDate() {
		String dateString = "2021-10-28";

		LocalDate localDate = DateParameterUtil.getLocalDate(dateString);

		Assert.assertEquals(dateString, localDate.toString());

		Assert.assertNull(DateParameterUtil.getLocalDate(null));
		Assert.assertNull(DateParameterUtil.getLocalDate(StringPool.BLANK));
	}

	@Test
	public void testGetLocalDateTime() {
		Assert.assertEquals(
			"2021-10-28T01:00",
			String.valueOf(
				DateParameterUtil.getLocalDateTime("2021-10-28 1:00")));
		Assert.assertNull(DateParameterUtil.getLocalDateTime(null));
		Assert.assertNull(DateParameterUtil.getLocalDateTime(StringPool.BLANK));
	}

	@Test
	public void testGetParameterBlank() {
		Assert.assertEquals(
			StringPool.BLANK,
			DateParameterUtil.getParameter(null, null, null, null));
		Assert.assertEquals(
			StringPool.BLANK,
			DateParameterUtil.getParameter(null, null, StringPool.BLANK, null));
		Assert.assertEquals(
			StringPool.BLANK,
			DateParameterUtil.getParameter(
				null, null, StringPool.OPEN_CURLY_BRACE, null));
	}

	@Test
	public void testGetParameterBlankWithDateField() {
		Assert.assertEquals(
			StringPool.BLANK,
			DateParameterUtil.getParameter(
				null, "endsOn",
				_getParameter(
					"dateField", "Date12345678", "endsOn", "1", "dateField",
					"days"),
				null));
	}

	@Test
	public void testGetParameterCustomDate() {
		String dateFieldName = "Date";

		String timeZoneId = "GMT-18:00";

		LocalDate localDate = LocalDate.now(ZoneId.of(timeZoneId));

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
				dateFieldName, localDate.toString());

		DDMFormValues ddmFormValues = new DDMFormValues(null);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		_assertEquals(
			"dateField", dateFieldName, ddmFormValues, localDate.plusMonths(1),
			"1", timeZoneId, "customDate", "months");

		LocalDate utcLocalDate = LocalDate.now(ZoneId.of("UTC"));

		Value value = ddmFormFieldValue.getValue();

		value.addString(LocaleUtil.US, utcLocalDate.toString());

		_assertEquals(
			"dateField", dateFieldName, ddmFormValues,
			utcLocalDate.plusMonths(1), "1", null, "customDate", "months");
		_assertEquals(
			"responseDate", null, null, localDate.plusMonths(1), "1",
			timeZoneId, "customDate", "months");
		_assertEquals(
			"responseDate", null, null, utcLocalDate.plusMonths(1), "1", null,
			"customDate", "months");
	}

	@Test
	public void testGetParameterDateField() {
		String dateFieldName = "Date";

		String timeZoneId = "GMT-18:00";

		LocalDate localDate = LocalDate.now(ZoneId.of(timeZoneId));

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
				dateFieldName, localDate.toString());

		DDMFormValues ddmFormValues = new DDMFormValues(null);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		_assertEquals(
			null, dateFieldName, ddmFormValues, localDate, null, timeZoneId,
			"dateField", null);

		LocalDate utcLocalDate = LocalDate.now(ZoneId.of("UTC"));

		Value value = ddmFormFieldValue.getValue();

		value.addString(LocaleUtil.US, utcLocalDate.toString());

		_assertEquals(
			null, dateFieldName, ddmFormValues, utcLocalDate, null, null,
			"dateField", null);
	}

	@Test
	public void testGetParameterResponseDate() {
		String timeZoneId = "GMT-18:00";

		_assertEquals(
			null, null, null, LocalDate.now(ZoneId.of(timeZoneId)), null,
			timeZoneId, "responseDate", null);

		_assertEquals(
			null, null, null, LocalDate.now(ZoneId.of("UTC")), null, null,
			"responseDate", null);
	}

	private static void _setUpDateFormatFactory() {
		ReflectionTestUtil.setFieldValue(
			DateFormatFactoryUtil.class, "_fastDateFormatFactory",
			new DateFormatFactoryImpl());
	}

	private static void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _assertEquals(
		String date, String dateFieldName, DDMFormValues ddmFormValues,
		LocalDate expectedLocalDate, String quantity, String timeZoneId,
		String type, String unit) {

		String key = "endsOn";

		Assert.assertEquals(
			expectedLocalDate.toString(),
			DateParameterUtil.getParameter(
				ddmFormValues, key,
				_getParameter(date, dateFieldName, key, quantity, type, unit),
				timeZoneId));
	}

	private String _getParameter(
		String date, String dateFieldName, String key, String quantity,
		String type, String unit) {

		return JSONUtil.put(
			key,
			JSONUtil.put(
				"date", date
			).put(
				"dateFieldName", dateFieldName
			).put(
				"quantity", quantity
			).put(
				"type", type
			).put(
				"unit", unit
			)
		).toString();
	}

}